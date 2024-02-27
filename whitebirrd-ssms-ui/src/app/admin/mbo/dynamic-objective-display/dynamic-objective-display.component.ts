import { Component, OnInit, Input } from '@angular/core';
import { ObjectiveModel } from '../models/ObjectiveModel';
import { ObjectiveStatus } from '../models/ObjectiveStatus';
import { trigger, state, style, transition, animate } from '@angular/animations';
import { DynamicProjectDisplayComponent } from '../dynamic-project-display/dynamic-project-display.component';
import { Constants } from '../../../constants';
import { HttpParams } from '@angular/common/http';
import { RemoteService } from '../../../services/remote.service';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';

import { TimeLineModel, TimeLineViewComponent } from '../time-line-view/time-line-view.component';
import { FormGroup } from '@angular/forms';
import { PopupViewComponent } from '../../popup-view/popup-view.component';
import { PopupDataArgs } from '../../popup-view/PopupDataArgs';
import { PopupType } from '../../popup-view/PopupType';
import { FormList } from '../../dynamic-form/FormList';
import { ObjectiveComponent } from '../project/ObjectiveComponent';
import { environment } from '../../../../environments/environment';
import { ObjectivePriority } from '../models/ObjectivePriority';

@Component({
   selector: 'app-objective-item',
   templateUrl: './dynamic-objective-display.component.html',
   styleUrls: ['./dynamic-objective-display.component.scss'],
   animations: [
      trigger('objectiveEditor', [
         state('hide', style({
            marginTop: '-100%'
         })),
         state('show', style({
            marginTop: '0px'
         })),
         transition('hide => show', animate('0ms ease-in')),
         transition('show => hide', animate('300ms ease-in')),
      ]),
   ]
})
export class DynamicObjectiveDisplayComponent implements OnInit {


   objStatus = ObjectiveStatus;
   priority = ObjectivePriority;

   ShowObjectiveCreator = 'hide';

   IsAssignedToMe: boolean;
   AmIModerator: boolean;
   ICanOnlySeeIt: boolean;
   IAmOwner: boolean;
   ICanEdit: boolean;

   @Input() Data: ObjectiveModel;
   @Input() Controller: ObjectiveComponent;
   @Input() ParentProject: DynamicProjectDisplayComponent;

   constructor(private remote: RemoteService, private dialog: MatDialog, private snack: MatSnackBar) { }

   ngOnInit(): void {
      this.AssignUserRoles();
   }

   AssignUserRoles() {
      let MyId = '';
      const user = JSON.parse(sessionStorage.getItem(Constants.LOGGED_USER)).loginSuccess;
      if (user) {
         MyId = user.userGUID.toLowerCase();
      }
      this.IsAssignedToMe = this.Data.assignedTo.toLowerCase() === MyId;
      if (this.ParentProject.Access) {
         this.AmIModerator = this.ParentProject.Access.some(t => t.canApprove && t.userId.toLowerCase() === MyId);
         this.ICanEdit = this.ParentProject.Access.some(t => t.canEdit && !t.canApprove && t.userId.toLowerCase() === MyId)
            || this.AmIModerator;
         this.ICanOnlySeeIt = this.ParentProject.Access
            .some(t => t.canView && !t.canEdit && !t.canApprove && t.userId.toLowerCase() === MyId);
      }
      this.IAmOwner = this.Data.createdBy.toLowerCase() === MyId;
      if (!environment.production) {
         this.WhatAmI();
      }
   }

   private WhatAmI() {
      this.IsAssignedToMe = true;
      this.AmIModerator = true;
   }

   ToggleObjectiveEditor() {
      this.ShowObjectiveCreator = (this.ShowObjectiveCreator === 'show' ? 'hide' : 'show');
      if (this.ShowObjectiveCreator === 'show') {
         this.Controller.GetObjectiveForm(this.Data.projectItemId, this.ParentProject.Access);
      }
   }

   SubmitEditorForm(data: FormGroup) {
      this.Controller.SubmitEditedFormData(data, this.DataEditedSuccessfully, this);
   }

   DataEditedSuccessfully(data: ObjectiveModel | ObjectiveModel[], that?: DynamicObjectiveDisplayComponent) {
      if (Array.isArray(data)) {
         data.forEach(d => {
            if (that.Data.id === d.id) {
               that.Data = new ObjectiveModel(d);
            } else {
               that.ParentProject.Data.objectives.push(new ObjectiveModel(d));
            }
         });
      } else {
         that.Data = new ObjectiveModel(data);
      }
      that.ShowObjectiveCreator = 'hide';
   }

   LockMOB() {
      const patchData: any = {};
      patchData.id = this.Data.id;
      patchData.status = ObjectiveStatus[ObjectiveStatus.Locked];
      this.PatchStatus(patchData, this);
   }
   ApproveMBO() {
      const patchData: any = {};
      patchData.id = this.Data.id;
      patchData.status = ObjectiveStatus[ObjectiveStatus.Approved];
      this.PatchStatus(patchData, this);
   }

   DenyMBO() {
      this.fetchDenialForm(this.CallDenialForm, ObjectiveStatus[ObjectiveStatus.Denied]);
   }

   private fetchDenialForm(call: (p, c) => void, param) {
      if (this.Controller.ExtensionForm == null) {
         this.remote.get('/bms-objective/Objective/GetDenialForm').subscribe(
            data => {
               this.Controller.DenialForm = new FormList(data);
               call(param, this);
            },
            error => {
               this.remote.logInformationToConsole(true, true, 'Error getting extension form', error);
            }
         );
      } else {
         call(param, this);
      }
   }

   private CallDenialForm(param, that: DynamicObjectiveDisplayComponent) {
      that.dialog.open(PopupViewComponent, {
         width: '320px',
         data: new PopupDataArgs(null, null, `${param} for ${that.Data.title}`, PopupType.Form,
            that.Controller.DenialForm, that.PostDeny, { context: that, parameter: param })
      });
   }

   PostDeny(data, con, fg, diag: MatDialogRef<PopupViewComponent>) {
      const that: DynamicObjectiveDisplayComponent = con.context;
      const patchData: any = {};
      patchData.Comment = data.Comment;
      patchData.id = that.Data.id;
      patchData.status = con.parameter;
      that.PatchStatus(patchData, that);
      diag.close();
   }
   StartWorkingMBO() {
      const patchData: any = {};
      patchData.id = this.Data.id;
      patchData.status = ObjectiveStatus[ObjectiveStatus.WorkInProgress];
      this.PatchStatus(patchData, this);
   }
   SubmitMBO() {
      const patchData: any = {};
      patchData.id = this.Data.id;
      patchData.status = ObjectiveStatus[ObjectiveStatus.InReview];
      this.PatchStatus(patchData, this);
   }
   AcceptMBO() {
      const patchData: any = {};
      patchData.id = this.Data.id;
      patchData.status = ObjectiveStatus[ObjectiveStatus.Complete];
      this.PatchStatus(patchData, this);
   }

   AskExtensionMBO() {
      if (this.Controller.ExtensionForm == null) {
         this.remote.get('/bms-objective/Objective/GetExtensionForm').subscribe(
            data => {
               this.Controller.ExtensionForm = new FormList(data);
               this.CallExtensionForm();
            },
            error => {
               this.remote.logInformationToConsole(true, true, 'Error getting extension form', error);
            }
         );
      } else {
         this.CallExtensionForm();
      }
   }

   private CallExtensionForm() {
      this.dialog.open(PopupViewComponent, {
         width: '320px',
         data: new PopupDataArgs(null, null, `Extension for ${this.Data.title}`, PopupType.Form,
            this.Controller.ExtensionForm, this.SaveExtension, this)
      });
   }

   private SaveExtension(data, that: DynamicObjectiveDisplayComponent, fg: FormGroup, dialog: MatDialogRef<PopupViewComponent>) {
      const patchData: any = {};
      patchData.Comment = data.Comment;
      patchData.Process = data.Process;
      patchData.id = that.Data.id;
      patchData.status = ObjectiveStatus[ObjectiveStatus.Extension];
      that.PatchStatus(patchData, that);
      dialog.close();
   }

   GiveUpMBO() {
      this.fetchDenialForm(this.CallDenialForm, ObjectiveStatus[ObjectiveStatus.GaveUp]);
   }

   AllowExtensionMBO() {
      const patchData: any = {};
      patchData.id = this.Data.id;
      patchData.status = ObjectiveStatus[ObjectiveStatus.Approved];
      this.PatchStatus(patchData, this);
   }
   RejectOrDenyExtensionMBO() {
      this.fetchDenialForm(this.CallDenialForm, ObjectiveStatus[ObjectiveStatus.Rejected]);
   }
   DontExceptMBO() {
      this.fetchDenialForm(this.CallDenialForm, ObjectiveStatus[ObjectiveStatus.Unacceptable]);
   }
   ReAssignMBO() {
      const patchData: any = {};
      patchData.id = this.Data.id;
      patchData.status = ObjectiveStatus[ObjectiveStatus.Created];
      this.PatchStatus(patchData, this);
   }

   PatchStatus(data: any, that: DynamicObjectiveDisplayComponent) {
      this.Controller.SubmitPatchFormData(data, that.DataEditedSuccessfully, that);
   }

   ShowTimeline() {
      const obj = { 'id': this.Data.id };
      const param = new HttpParams({ fromObject: obj });
      this.remote.get('/bms-objective/Default/Objective', param).subscribe(
         data => {
            const dd = [];
            data.forEach(d => {
               dd.push(new TimeLineModel(d));
            });
            this.dialog.open(TimeLineViewComponent, { width: '600px', data: dd });
         },
         error => {
            this.remote.logInformationToConsole(true, true, 'Error while getting timelines', error);
            this.snack.open('Could not get data from server', null, { duration: 2000 });
         }
      );
   }

   RefreshData() {
      this.remote.get('/bms-objective/ProjectItem/RefreshO/' + this.Data.id).subscribe(
         d => this.Data = new ObjectiveModel(d),
         e => this.remote.logInformationToConsole(true, true, 'Could not refresh', e)
      );
   }

   NeedExtentionAfterRejection(): boolean {
      if (this.Data.dueDate != null) {
         const t = new Date(this.Data.dueDate);
         t.setDate(t.getDate() + 2);
         return t < new Date();
      }
      return false;
   }

}
