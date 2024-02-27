import { Component, OnInit, Input } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog } from '@angular/material/dialog';

import { trigger, transition, style, animate, state } from '@angular/animations';
import { ProjectEntry } from '../models/ProjectEntry';
import { AdditionalProperty } from '../models/AdditionalProperty';
import { ProjectComponent } from '../project/project.component';
import { ProjectStage } from '../enum/ProjectStage';
import { FormGroup } from '@angular/forms';
import { ObjectiveModel } from '../models/ObjectiveModel';
import { ShowTime } from '../../dynamic-form/ShowTime';
import { FormList } from '../../dynamic-form/FormList';
import { RemoteService } from '../../../services/remote.service';
import { AccessSelector } from '../models/AccessSelector';
import { environment } from '../../../../environments/environment';
import { HttpParams } from '@angular/common/http';
import { TimeLineViewComponent, TimeLineModel } from '../time-line-view/time-line-view.component';
import { Constants } from '../../../constants';
import { PopupViewComponent } from '../../popup-view/popup-view.component';
import { PopupType } from '../../popup-view/PopupType';
import { PopupDataArgs } from '../../popup-view/PopupDataArgs';
import { getTreeControlMissingError } from '../../../../../node_modules/@angular/cdk/tree';
import { Router } from '../../../../../node_modules/@angular/router';
import { PopupData, SelectorPopupComponent } from '../selector-popup/selector-popup.component';
import { RequirementModel } from '../models/RequirementModel';
import { IssuesModel } from '../models/IssueModel';

@Component({
   selector: 'app-project-item',
   templateUrl: './dynamic-project-display.component.html',
   styleUrls: ['./dynamic-project-display.component.scss'],
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
      ]),

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

      trigger('selfChildren', [
         state('hide', style({
            marginTop: '-100%'
         })),
         state('show', style({
            marginTop: '0px'
         })),
         transition('hide => show', animate('0ms ease-in')),
         transition('show => hide', animate('300ms ease-in')),
      ]),

      trigger('analysis', [
         state('hide', style({
            marginTop: '-100%'
         })),
         state('show', style({
            marginTop: '0px'
         })),
         transition('hide => show', animate('0ms ease-in')),
         transition('show => hide', animate('300ms ease-in')),
      ]),

      trigger('requirement', [
         state('hide', style({
            marginTop: '-100%'
         })),
         state('show', style({
            marginTop: '0px'
         })),
         transition('hide => show', animate('0ms ease-in')),
         transition('show => hide', animate('300ms ease-in')),
      ]),

      trigger('issue', [
         state('hide', style({
            marginTop: '-100%'
         })),
         state('show', style({
            marginTop: '0px'
         })),
         transition('hide => show', animate('0ms ease-in')),
         transition('show => hide', animate('300ms ease-in')),
      ]),

      trigger('childEditor', [
         state('hide', style({
            marginTop: '-100%'
         })),
         state('show', style({
            marginTop: '0px'
         })),
         transition('hide => show', animate('0ms ease-in')),
         transition('show => hide', animate('300ms ease-in')),
      ]),

      trigger('expand', [
         state('hide', style({
            transform: 'rotate(0deg)'
         })),
         state('show', style({
            transform: 'rotate(90deg)'
         })),
         transition('hide => show', animate('200ms ease-in')),
         transition('show => hide', animate('200ms ease-in')),
      ])
   ]
})
export class DynamicProjectDisplayComponent implements OnInit {

   public projectStage = ProjectStage;
   public IsNotMilestone: boolean;
   public Loading = false;
   public Access: AccessSelector[];
   Showtime = ShowTime;
   IsApprover = false;
   Analysis;

   @Input() Data: ProjectEntry;
   @Input() Controller: ProjectComponent;
   @Input() FormGroup: FormGroup;
   @Input() ExtraColumn: string[];
   @Input() ParentItem: ProjectEntry;
   @Input() ParentComponent: DynamicProjectDisplayComponent;

   ExtraCol = [];
   private _IsSearchMode: boolean;
   @Input()
   public get IsSearchMode(): boolean {
      return this._IsSearchMode;
   }
   public set IsSearchMode(v: boolean) {
      this._IsSearchMode = v;
      this.ShowSelfChildren = v ? 'show' : 'hide';
   }

   ColCount = 9;

   ShowEditor = 'hide';
   ShowChildEditor = 'hide';
   ShowRequirementCreator = 'hide';
   ShowIssueCreator = 'hide';
   ShowSelfChildren = 'hide';
   ShowObjectiveCreator = 'hide';
   ShowAnalysis = 'hide';
   Properties: AdditionalProperty[];

   ChildName: string;
   PlainData: ProjectEntry;

   ChildFormGroup: FormList;
   RequirementCreatorData: any = {};
   IssueFormList: FormList;
   IssueCreatorData: any = {};

   ObjectiveEditorData: any = {};
   ChildEditorData: any = {};
   ChildrenTypeList: any[] = [];

   constructor(
      public dialog: MatDialog,
      private remote: RemoteService,
      private route: Router,
      private snack: MatSnackBar) { }

   ngOnInit(): void {
      if (this.Data != null) {
         this.RequirementCreatorData.projectItemId = this.Data.id;
         this.IssueCreatorData.projectItem = this.Data.id;
         this.Properties = [];
         if (this.ExtraColumn) {
            this.ExtraColumn.forEach(col => {
               if (this.Data.moreProperties) {
                  this.Properties.push(this.Data.moreProperties.find(r => r.propertyInfo && r.propertyInfo.name === col));
               }
            });
         }
         this.Access = this.Data.accessControl;
         let MyId = '';
         const user = JSON.parse(sessionStorage.getItem(Constants.LOGGED_USER)).loginSuccess;
         if (user) {
            MyId = user.userGUID.toLowerCase();
         }
         if (this.Access) {
            this.IsApprover = this.Access.some(t => t.canApprove && t.userId.toLowerCase() === MyId);
         }
      }
      this.ObjectiveEditorData.projectItemId = this.Data.id;
      switch (this.Data.stage) {
         case ProjectStage.Project:
            this.ChildrenTypeList.push(ProjectStage.Module);
            this.ChildrenTypeList.push(ProjectStage.SubModule);
            this.ChildrenTypeList.push(ProjectStage.Milestone);
            this.IsNotMilestone = true;
            break;
         case ProjectStage.Module:
            this.ChildrenTypeList.push(ProjectStage.SubModule);
            this.ChildrenTypeList.push(ProjectStage.Milestone);
            this.IsNotMilestone = true;
            break;
         case ProjectStage.SubModule:
            this.ChildrenTypeList.push(ProjectStage.Milestone);
            this.IsNotMilestone = true;
            break;
         default:
            this.IsNotMilestone = false;
            break;
      }
      if (!environment.production) {
         // this.IsApprover = true;
      }
   }

   ColumnNames(): string[] {
      this.ExtraCol = [];
      if (this.Data.children && this.Data.children.length > 0) {
         this.Data.children.forEach(d => {
            if (d.moreProperties) {
               d.moreProperties.forEach(mp => {
                  const name = mp.propertyInfo.name;
                  if (!this.ExtraCol.some(r => r === name)) {
                     this.ExtraCol.push(name);
                  }
               });
            }
         });
      }
      return this.ExtraCol;
   }

   MenuButtonClicked() {
      if (this.ShowChildEditor === 'show') {
         this.ToggleChildCreator(this.Data.stage);
      }
   }

   DeleteProjectEntry() {
      this.Controller.DeleteProjectItem(this.Data, this.ParentItem);
   }

   ToggleEditor() {
      this.PlainData = this.GetPlainData();
      this.ShowEditor = (this.ShowEditor === 'show' ? 'hide' : 'show');
   }

   ToggleChildCreator(stage?: ProjectStage) {
      this.ChildEditorData.parentId = this.Data.id;
      this.ShowChildEditor = (this.ShowChildEditor === 'show' ? 'hide' : 'show');
      if (this.ShowChildEditor === 'show') {
         this.ChildFormGroup = this.GetEditor(stage);
      } else {
         this.ChildFormGroup = null;
      }
   }

   /**
    * GetPlainData
    */
   public GetPlainData(): any {
      return JSON.parse(JSON.stringify(this.Data));
   }

   public GetEditor(stage): FormList {
      switch (stage) {
         case ProjectStage.Module:
            this.ChildName = 'Module';
            return JSON.parse(JSON.stringify(this.Controller.ModuleCreator));
         case ProjectStage.SubModule:
            this.ChildName = 'Sub Module';
            return JSON.parse(JSON.stringify(this.Controller.SubModuleCreator));
         case ProjectStage.Milestone:
            this.ChildName = 'Milestone';
            return JSON.parse(JSON.stringify(this.Controller.MilestoneCreator));
         default:
            this.ChildName = 'Project';
            return JSON.parse(JSON.stringify(this.Controller.ProjectCreator));
      }
   }


   ToggleSelfChildren() {
      this.ShowSelfChildren = (this.ShowSelfChildren === 'show' ? 'hide' : 'show');

      if (this.ShowSelfChildren === 'show' && this.Data.children == null) {
         // call the children fetcher
         this.Controller.GetChildren(this.Data, this);
      }
      if (this.ShowSelfChildren !== 'show') {
         this.ShowAnalysis = 'hide';
         this.ShowObjectiveCreator = 'hide';
         this.ShowChildEditor = 'hide';
         this.ShowEditor = 'hide';
      }
   }

   ToggleAnalysis() {
      this.ShowAnalysis = (this.ShowAnalysis === 'show' ? 'hide' : 'show');
      if (this.ShowAnalysis === 'show' && this.Analysis == null) {
         this.Controller.GetAnalysis(this.Data.id).subscribe(t => {
            this.Analysis = t;
         });
      }
   }

   ToggleObjectiveEditor() {
      this.ShowObjectiveCreator = (this.ShowObjectiveCreator === 'show' ? 'hide' : 'show');
      if (this.ShowObjectiveCreator === 'show') {
         this.Controller.ObjectiveController.GetObjectiveForm(this.Data.id, this.Access);
      }
   }

   OpenRequirements() {
      this.route.navigate(['admin/details/1201']);
   }

   ToggleRequirementCreator() {
      this.ShowRequirementCreator = (this.ShowRequirementCreator === 'show' ? 'hide' : 'show');
   }

   CreateRequirement(form: FormGroup) {
      this.Controller.EditLoading = true;
      this.remote.post('/bms-objective/Requirement/create', form.getRawValue(), form).subscribe(
         data => {
            this.ToggleRequirementCreator();
            const req = new RequirementModel(data);
            if (this.Data.requirements == null) {
               this.Data.requirements = [];
            }
            this.Data.requirements.push(req);
            this.Controller.EditLoading = false;
         }
      );
   }

   ToggleIssueCreator() {
      this.ShowIssueCreator = (this.ShowIssueCreator === 'show' ? 'hide' : 'show');
   }

   CreateIssue(form: FormGroup) {
      this.Controller.EditLoading = true;
      this.remote.post('/bms-objective/Issues/create', form.getRawValue(), form).subscribe(
         data => {
            this.ToggleIssueCreator();
            const issu = new IssuesModel(data);
            if (this.Data.issues == null) {
               this.Data.issues = [];
            }
            this.Data.issues.push(issu);
            this.Controller.EditLoading = false;
         }
      );
   }

   OpenIssue() {
      this.route.navigate(['admin/details/1202']);
   }

   GetAnalysisFormEditor() {
      const formeditor = this.Controller.AnalysisEditor;
      const parent = formeditor.forms.find(t => t.key === 'milestoneId');
      parent.initialValue = this.Data.id;
      return formeditor;
   }

   ExtraLength() {
      if (this.Data.moreProperties) {
         return this.Data.moreProperties.length;
      } else {
         return 0;
      }
   }

   SubmitForm(data: any) {
      // const self = this;
      this.Controller.SubmitFormData(data, this.DataSavedSuccessfully, this);
   }

   SubmitEditorForm(data: any) {
      this.Controller.SubmitEditedFormData(data, this.DataEditedSuccessfully, this);
   }

   SubmitObjectiveForm(data: any) {
      this.Controller.ObjectiveController.SubmitFormData(data, this.ObjectiveSavedSuccessfully, this);
   }


   DataSavedSuccessfully(data: ProjectEntry, context?: any) {
      if (!context.Data.children) {
         context.Data.children = new Array<ProjectEntry>();
      }
      context.Data.children.push(data);
      context.ToggleChildCreator();
      context.ShowSelfChildren = 'show';
   }

   DataEditedSuccessfully(data: ProjectEntry, context?: any) {
      context.Data.completedOn = data.completedOn;
      context.Data.description = data.description;
      context.Data.moreProperties = data.moreProperties;
      context.Data.startDate = data.startDate;
      context.Data.title = data.title;
      context.ToggleEditor();
   }

   ObjectiveSavedSuccessfully(data: ObjectiveModel[], context?: any) {
      if (!context.Data.objectives) {
         context.Data.objectives = new Array<ObjectiveModel>();
      }
      data.forEach(dd => {
         context.Data.objectives.push(new ObjectiveModel(dd));
      });
      context.ToggleObjectiveEditor();
      context.ShowSelfChildren = 'show';
   }

   ShowUserAccess() {
      if (this.Access) {
         this.displayAccess();
      }
   }


   private displayAccess() {
      const users = JSON.parse(sessionStorage.getItem(Constants.APPLICATION_USERS));
      const dialogRef = this.dialog.open(SelectorPopupComponent, {
         width: '500px',
         data: new PopupData(this.Data.id, `${this.Data.title} User Access`, null, users, this.Access)
      });
   }

   UpdateAnalysisStatus(analysis, status) {
      this.remote.patch('/bms-objective/Analysis/UpdateStatus', { 'id': analysis.id, 'stage': status }).subscribe(
         d => this.Analysis = d,
         e => {
            this.remote.logInformationToConsole(true, true, 'Could not update status', e);
            this.snack.open('Could not save status', null, { verticalPosition: 'top', duration: 2400 });
         }
      );
   }

   RefreshData() {
      this.remote.get('/bms-objective/ProjectItem/RefreshP/' + this.Data.id).subscribe(
         d => {
            this.Data = new ProjectEntry(d);
            this.ShowSelfChildren = 'hide';
         },
         e => this.remote.logInformationToConsole(true, true, 'Could not refresh', e)
      );
   }

   OpenFiles(analysis) {
      const url = `/bms-objective/Default/files?id=${analysis.id}&table=Analysis`;
      this.dialog.open(PopupViewComponent, {
         // width: '80%',
         data: new PopupDataArgs(url, null, analysis.title, PopupType.Remote)
      });
   }

   ShowTimeline() {
      const obj = { 'id': this.Data.id };
      const param = new HttpParams({ fromObject: obj });
      this.remote.get('/bms-objective/Default/ProjectItem', param).subscribe(
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

   GetDisplayStyle(): string {
      const color = [];
      if (this.Data.currentStatus != null) {
         const total = this.Data.currentStatus.reduce((a, b) => a + b, 0);
         if (total > 0) {
            for (let i = 0; i < this.Data.currentStatus.length; i++) {
               const val = this.Data.currentStatus[i];
               if (val > 0) {
                  color.push({ 'Color': this.getColor(i), 'Percent': val * 100 / total });
               }
            }
         }
      }
      let pStyle = `linear-gradient(to bottom`;
      let StartPoint = 0;
      for (let index = 0; index < color.length; index++) {
         const element = color[index];
         StartPoint += element.Percent;
         pStyle += `, ${element.Color} ${StartPoint}%`;
      }
      pStyle += ')';
      return pStyle;
   }
   private getColor(index: number): string {
      switch (index) {
         case 0: // Initiated
            return '#ffffff';
         case 1: // Pending Approval
            return '#00ecec';
         case 2: // Approved
            return '#d39400';
         case 3: // Denied
            return '#fd404e';
         case 4: // Work in Progress
            return '#bdca00';
         case 5: // Pending Review
            return '#949494';
         case 6: // Pending Extension
            return '#934fdd';
         case 7: // Gave Up
            return '#696969';
         case 8: // Rejected
            return '#dd00dd';
         case 9: // Un-Acceptable
            return '#ce3e1a';
         case 10: // Complete
            return '#4de626';
         default:
            return '#000000';
      }
   }
}

