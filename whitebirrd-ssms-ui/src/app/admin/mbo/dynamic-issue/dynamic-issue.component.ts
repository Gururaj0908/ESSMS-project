import { Component, OnInit, Input } from '@angular/core';
import { trigger, state, style, transition, animate } from '@angular/animations';
import { ProjectComponent } from '../project/project.component';
import { ProjectEntry } from '../models/ProjectEntry';
import { DynamicProjectDisplayComponent } from '../dynamic-project-display/dynamic-project-display.component';
import { RemoteService } from '../../../services/remote.service';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { Constants } from '../../../constants';
import { DialogType, ConfirmDialogComponent, DialogData } from '../../../helpers/confirm-dialog/confirm-dialog.component';
import { FormGroup } from '@angular/forms';
import { FormList } from '../../dynamic-form/FormList';
import { FormEditorModel } from '../../dynamic-form/FormEditor';
import { FormEditorType } from '../../dynamic-form/FormEditorType';
import { PopupViewComponent } from '../../popup-view/popup-view.component';
import { PopupDataArgs } from '../../popup-view/PopupDataArgs';
import { PopupType } from '../../popup-view/PopupType';
import { IssuesModel } from '../models/IssueModel';
import { TrackingStatus } from '../models/RequirementModel';

@Component({
   selector: 'app-dynamic-issue',
   templateUrl: './dynamic-issue.component.html',
   styleUrls: ['./dynamic-issue.component.scss'],
   animations: [
      trigger('editor', [
         state('hide', style({
            marginTop: '-100%'
         })),
         state('show', style({
            marginTop: '0px'
         })),
         transition('hide => show', animate('0ms ease-in')),
         transition('show => hide', animate('300ms ease-in')),
      ])
   ]
})
export class DynamicIssueComponent implements OnInit {

   trackingStatus = TrackingStatus;
   ShowEditor = 'hide';
   IsBA = false;
   IsTH = false;

   @Input() Data: IssuesModel;
   EditData: IssuesModel;
   @Input() Controller: ProjectComponent;
   @Input() ParentItem: ProjectEntry;
   @Input() ParentComponent: DynamicProjectDisplayComponent;

   constructor(private remote: RemoteService, private dialog: MatDialog) { }

   ngOnInit() {
      this.EditData = JSON.parse(JSON.stringify(this.Data));
      const user = JSON.parse(sessionStorage.getItem(Constants.LOGGED_USER));
      const roles: string = user.loginSuccess.roleGUIDs;
      if (roles.indexOf('a96f60b2-48e7-4687-a650-a3969beefbcf') >= 0) {
         this.IsBA = true;
      }
      if (roles.indexOf('4304e12f-5bde-46ea-af3c-26309193c18f') >= 0) {
         this.IsTH = true;
      }
   }

   openDialog(title: string, message: string, affirmText: string, closeText: string, type: DialogType,
      callback: (id: string) => void, callback_parameter: string): void {
      const dialogRef = this.dialog.open(ConfirmDialogComponent, {
         width: '350px',
         data: new DialogData(title, message, affirmText, closeText, type)
      });
      dialogRef.afterClosed().subscribe(result => {
         if (result) {
            callback(callback_parameter);
         }
      });
   }

   ToggleEditor() {
      this.ShowEditor = (this.ShowEditor === 'show' ? 'hide' : 'show');
   }

   SubmitEditorForm(form: FormGroup) {
      this.Controller.EditLoading = true;
      this.remote.put('/bms-objective/Issues/update', form.getRawValue(), form).subscribe(
         d => {
            this.ToggleEditor();
            this.Data.description = form.value.description;
            this.Data.title = form.value.title;
            this.Controller.EditLoading = false;
         });
   }
   DeleteItem() {
      this.openDialog('Delete?', `Are you sure to delete ${this.Data}?`, 'Delete It', `Don't Delete`,
         DialogType.Alert, (s) => this.TrashIssues(), this.Data.id);
   }

   TrashIssues() {
      this.remote.delete('/bms-objective/Issues/delete', [this.Data.id], true).subscribe(
         d => {
            const index = this.ParentItem.issues.findIndex(t => t.id === this.Data.id);
            this.ParentItem.issues.splice(index, 1);
         }
      );
   }

   CallStatusChange(status: TrackingStatus) {
      switch (status) {
         case TrackingStatus.Closed:
         case TrackingStatus.Denied:
            const denialform = new FormList();
            denialform.forms = [];
            const Description = new FormEditorModel();
            Description.autoFocus = true;
            Description.formEditorType = FormEditorType.MultilineText;
            Description.key = 'description';
            Description.label = 'Reason';
            Description.maxLength = 1024;
            Description.hint = 'Will appear in history';
            Description.placeHolder = 'Enter reason for this action';
            Description.required = true;
            denialform.forms.push(Description);
            const id = new FormEditorModel();
            id.key = 'id';
            id.formEditorType = FormEditorType.Hidden;
            id.initialValue = this.Data.id;
            denialform.forms.push(id);
            this.dialog.open(PopupViewComponent, {
               width: '320px',
               data: new PopupDataArgs(null, null, 'Enter Reason Please', PopupType.Form, denialform,
                  this.ChangeStatus, { status: status, context: this })
            });
            break;
         default:
            this.ChangeStatus({ id: this.Data.id }, { status: status, context: this }, null);
            break;
      }
   }

   ChangeStatus(formData: any, callback_parameter: any, formgroup: FormGroup, dialog?: MatDialogRef<PopupViewComponent>) {
      const status = callback_parameter.status;
      const that = callback_parameter.context;
      that.Controller.DisplayLoading = true;
      const url = `/bms-objective/Issues/update-status?Status=${status}`;
      that.remote.patch(url, formData, formgroup).subscribe(
         data => {
            that.Controller.DisplayLoading = false;
            that.Data.currentStatus = status;
            if (dialog != null) {
               dialog.close();
            }
         }
      );
   }


}
