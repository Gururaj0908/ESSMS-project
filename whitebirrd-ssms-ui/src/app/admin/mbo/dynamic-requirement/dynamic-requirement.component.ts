import { Component, OnInit, Input } from '@angular/core';
import { RequirementModel, TrackingStatus } from '../models/RequirementModel';
import { ProjectComponent } from '../project/project.component';
import { ProjectEntry } from '../models/ProjectEntry';
import { DynamicProjectDisplayComponent } from '../dynamic-project-display/dynamic-project-display.component';
import { trigger, state, style, transition, animate } from '@angular/animations';
import { FormGroup } from '@angular/forms';
import { RemoteService } from '../../../services/remote.service';
import { DialogType, DialogData, ConfirmDialogComponent } from '../../../helpers/confirm-dialog/confirm-dialog.component';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { Constants } from '../../../constants';
import { PopupViewComponent } from '../../popup-view/popup-view.component';
import { PopupDataArgs } from '../../popup-view/PopupDataArgs';
import { PopupType } from '../../popup-view/PopupType';
import { FormList } from '../../dynamic-form/FormList';
import { FormEditorModel } from '../../dynamic-form/FormEditor';
import { FormEditorType } from '../../dynamic-form/FormEditorType';

@Component({
   selector: 'app-dynamic-requirement',
   templateUrl: './dynamic-requirement.component.html',
   styleUrls: ['./dynamic-requirement.component.scss'],
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
export class DynamicRequirementComponent implements OnInit {

   trackingStatus = TrackingStatus;
   ShowEditor = 'hide';
   IsBA = false;
   IsTH = false;

   @Input() Data: RequirementModel;
   EditData: RequirementModel;
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
      this.remote.put('/bms-objective/Requirement/update', form.getRawValue(), form).subscribe(
         d => {
            this.ToggleEditor();
            this.Data.description = form.value.description;
            this.Data.title = form.value.title;
            this.Controller.EditLoading = false;
         });
   }
   DeleteItem() {
      this.openDialog('Delete?', `Are you sure to delete ${this.Data}?`, 'Delete It', `Don't Delete`,
         DialogType.Alert, (s) => this.TrashRequirement(), this.Data.id);
   }

   TrashRequirement() {
      this.remote.delete('/bms-objective/Requirement/delete', [this.Data.id], true).subscribe(
         d => {
            const index = this.ParentItem.requirements.findIndex(t => t.id === this.Data.id);
            this.ParentItem.requirements.splice(index, 1);
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
      const url = `/bms-objective/Requirement/update-status?Status=${status}`;
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
