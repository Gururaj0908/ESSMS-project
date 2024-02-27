import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { environment } from '../../../../environments/environment';
import { Constants } from '../../../constants';
import { ConfirmDialogComponent, DialogData, DialogType } from '../../../helpers/confirm-dialog/confirm-dialog.component';
import { GlobalService } from '../../../services/global.service';
import { RemoteService } from '../../../services/remote.service';
import { FormList } from '../../dynamic-form/FormList';
import { MenuHelperService } from '../../services/menu.helper.service';
import { DynamicProjectDisplayComponent } from '../dynamic-project-display/dynamic-project-display.component';
import { ProjectEntry } from '../models/ProjectEntry';
import { ObjectiveComponent } from './ObjectiveComponent';
import { ObjectiveModel } from '../models/ObjectiveModel';
import { AdditionalProperty } from '../models/AdditionalProperty';
import { map } from 'rxjs/operators';

@Component({
   selector: 'app-project',
   templateUrl: './project.component.html',
   styleUrls: ['./project.component.scss']
})
export class ProjectComponent implements OnInit {

   ObjectiveController: ObjectiveComponent;
   Data: ProjectEntry[] = [];
   ProjectEditor: FormList;
   ProjectCreator: FormList;
   ModuleEditor: FormList;
   ModuleCreator: FormList;
   SubModuleEditor: FormList;
   SubModuleCreator: FormList;
   MilestoneEditor: FormList;
   MilestoneCreator: FormList;
   AnalysisEditor: FormList;
   RequirementCreator: FormList;
   RequirementEditor: FormList;
   IssueCreator: FormList;
   IssueEditor: FormList;
   FilterFG: FormGroup;
   CanCreateProject: boolean;
   CanCreateModule: boolean;
   CanCreateSubModule: boolean;
   CanCreateMilestone: boolean;
   CanCreateObjective: boolean;
   CanMangePermission: boolean;
   CanAddDevelopers: boolean;
   CanAddApprovers: boolean;
   CanCreateRequirement: boolean;
   CanCreateIssue: boolean;
   CanEditProject: boolean;
   CanDeleteProject: boolean;
   CanEditModule: boolean;
   CanDeleteModule: boolean;
   CanEditSubModule: boolean;
   CanDeleteSubModule: boolean;
   CanEditMilestone: boolean;
   CanDeleteMilestone: boolean;
   CanEditRequirement: boolean;
   CanDeleteRequirement: boolean;
   CanEditIssue: boolean;
   CanDeleteIssue: boolean;
   FilterListForm: FormList;
   order = false;
   DisplayLoading: boolean;
   ShowEditor: boolean;
   ShowFilterButton = false;
   SearchMode = false;
   EditLoading = false;
   ExtraCol: string[] = [];
   MinHeight = window.innerHeight - (60 + 49 + 48 + 22);
   CurrentTab: String;
   Departments:String[]=[];

   constructor(public globalV: GlobalService, private snackbar: MatSnackBar, private dialog: MatDialog,
      public menuHelperService: MenuHelperService, private Remote: RemoteService) {
      this.ObjectiveController = new ObjectiveComponent(globalV, snackbar, dialog, Remote);
   }

   GetData() {
      this.DisplayLoading = true;
      if (this.CurrentTab==null) {
         this.CurrentTab = "IT";
      }
      this.Remote.get('/bms-objective/ProjectItem/Item?tab='+this.CurrentTab).subscribe(
         data => {
            if (Array.isArray(data.item1)) {
               this.Data = [];
               data.item1.forEach(d => {
                  this.Data.push(new ProjectEntry(d));
               });
            }
            this.ColumnNames();
            this.DisplayLoading = false;
            this.SearchMode = false;
         },
         errorg => {
            this.Remote.logInformationToConsole(true, true, errorg);
            this.DisplayLoading = false;
         }
      );
   }

   GetChildren(item: ProjectEntry, context: DynamicProjectDisplayComponent) {
      context.Loading = true;
      this.Remote.get('/bms-objective/ProjectItem/Item?ParentId=' + item.id).subscribe(
         data => {
            item.children = [];
            data.item1.forEach(d => {
               item.children.push(new ProjectEntry(d));
            });
            item.objectives = [];
            data.item2.forEach(d => {
               item.objectives.push(new ObjectiveModel(d));
            });
            context.Loading = false;
            context.ColumnNames();
         },
         errorg => {
            context.Loading = false;
            this.Remote.logInformationToConsole(true, true, errorg);
         }
      );
   }

   ngOnInit(): void {
      this.GetData();
      this.RunEditorFetcher();
      // this.GetUser('/essms-auth/option/userbyroles?roleGUIDs=c13c54d4-f23b-4c98-a343-26050ba49ad2', false);
      const businessObjects = JSON.parse(sessionStorage.getItem(Constants.MENU_ITEMS));
      const menuTree = this.menuHelperService.getMenuTree(businessObjects);
      //const currentMenuItem = this.menuHelperService.getMenuNode(menuTree, 12);
      //const currentMenuChildren = businessObjects;

      const currentMenuItem = this.menuHelperService.getMenuNode(menuTree, 1200);
      const currentMenuChildren = this.menuHelperService.getMenuForParent(menuTree, currentMenuItem.id);
      for (let i = 0; i < currentMenuChildren.length; i++) {
         if (currentMenuChildren[i].objectName.toUpperCase() === 'CREATE PROJECT') {
            this.CanCreateProject = true;
         }
         if (currentMenuChildren[i].objectName.toUpperCase() === 'CREATE MODULE') {
            this.CanCreateModule = true;
         }
         if (currentMenuChildren[i].objectName.toUpperCase() === 'CREATE SUBMODULE') {
            this.CanCreateSubModule = true;
         }
         if (currentMenuChildren[i].objectName.toUpperCase() === 'CREATE MILESTONE') {
            this.CanCreateMilestone = true;
         }
         if (currentMenuChildren[i].objectName.toUpperCase() === 'EDIT PROJECT') {
            this.CanEditProject = true;
         }
         if (currentMenuChildren[i].objectName.toUpperCase() === 'EDIT MODULE') {
            this.CanEditModule = true;
         }
         if (currentMenuChildren[i].objectName.toUpperCase() === 'EDIT SUBMODULE') {
            this.CanEditSubModule = true;
         }
         if (currentMenuChildren[i].objectName.toUpperCase() === 'EDIT MILESTONE') {
            this.CanEditMilestone = true;
         }
         if (currentMenuChildren[i].objectName.toUpperCase() === 'DELETE PROJECT') {
            this.CanDeleteProject = true;
         }
         if (currentMenuChildren[i].objectName.toUpperCase() === 'DELETE MODULE') {
            this.CanDeleteModule = true;
         }
         if (currentMenuChildren[i].objectName.toUpperCase() === 'DELETE SUBMODULE') {
            this.CanDeleteSubModule = true;
         }
         if (currentMenuChildren[i].objectName.toUpperCase() === 'DELETE MILESTONE') {
            this.CanDeleteMilestone = true;
         }
         if (currentMenuChildren[i].objectName.toUpperCase() === 'CREATE OBJECTIVE') {
            this.CanCreateObjective = true;
         }
         if (currentMenuChildren[i].objectName.toUpperCase() === 'ADD DEVELOPERS') {
            this.CanAddDevelopers = true;
         }
         if (currentMenuChildren[i].objectName.toUpperCase() === 'ADD APPROVERS') {
            this.CanAddApprovers = true;
         }
         if (currentMenuChildren[i].objectName.toUpperCase() === 'MANAGE ACCESS') {
            this.CanMangePermission = true;
         }
         if (currentMenuChildren[i].objectName.toUpperCase() === 'CREATE ISSUE') {
            this.CanCreateIssue = true;
         }
         if (currentMenuChildren[i].objectName.toUpperCase() === 'CREATE REQUIREMENT') {
            this.CanCreateRequirement = true;
         }

         if (currentMenuChildren[i].objectName.toUpperCase() === 'DELETE ISSUE') {
            this.CanDeleteIssue = true;
         }
         if (currentMenuChildren[i].objectName.toUpperCase() === 'DELETE REQUIREMENT') {
            this.CanDeleteRequirement = true;
         }

         if (currentMenuChildren[i].objectName.toUpperCase() === 'EDIT ISSUE') {
            this.CanEditIssue = true;
         }
         if (currentMenuChildren[i].objectName.toUpperCase() === 'EDIT REQUIREMENT') {
            this.CanEditRequirement = true;
         }
      }
      /*
      if (!environment.production) {
         this.allp();
      }*/
   }

   // handles create event of project items
   public SubmitFormData(form: FormGroup, cb: (a: ProjectEntry, b: DynamicProjectDisplayComponent) => void,
      that: DynamicProjectDisplayComponent) {
      this.EditLoading = true;
      this.Remote.post('/bms-objective/ProjectItem/create', form.getRawValue(), form).subscribe(
         d => {
            if (cb) {
               cb(new ProjectEntry(d), that);
            } else {
               if (this.Data == null) {
                  this.Data = [];
               }
               this.Data.push(new ProjectEntry(d));
               this.HideForm(form);
            }
            this.EditLoading = false;
         },
         e => {
            this.snackbar.open('An error occurred while creating project item', null, { verticalPosition: 'top', duration: 2500 });
            this.Remote.logInformationToConsole(true, true, 'Objective Insert Failed', e);
            this.EditLoading = false;
         }
      );
   }

   public HideForm(fg: FormGroup) {
      fg.reset();
      this.ShowEditor = false;
   }


   public SubmitEditedFormData(form: FormGroup, cb: (a: ProjectEntry, b: DynamicProjectDisplayComponent) => void,
      that: DynamicProjectDisplayComponent) {
      this.EditLoading = true;
      this.Remote.put('/bms-objective/ProjectItem/update', form.getRawValue(), form).subscribe(
         d => {
            this.EditLoading = false;
            cb(d, that);
         },
         e => {
            this.EditLoading = false;
            this.snackbar.open('An error occurred while editing peoject item', null, { verticalPosition: 'top', duration: 2500 });
            this.Remote.logInformationToConsole(true, true, 'Objective Insert Failed', e);
         }
      );
   }

   allp() {
      this.CanCreateProject = true;
      this.CanCreateModule = true;
      this.CanCreateSubModule = true;
      this.CanCreateMilestone = true;
      this.CanCreateObjective = true;
      this.CanMangePermission = true;
      this.CanAddDevelopers = true;
      this.CanAddApprovers = true;
      this.CanCreateRequirement = true;
      this.CanCreateIssue = true;
      this.CanEditProject = true;
      this.CanDeleteProject = true;
      this.CanEditModule = true;
      this.CanDeleteModule = true;
      this.CanEditSubModule = true;
      this.CanDeleteSubModule = true;
      this.CanEditMilestone = true;
      this.CanDeleteMilestone = true;
      this.CanEditRequirement = true;
      this.CanDeleteRequirement = true;
      this.CanEditIssue = true;
      this.CanDeleteIssue = true;
   }

   public SearchClicked(filter: FormGroup) {
      this.DisplayLoading = true;
      this.Remote.logInformationToConsole(false, false, 'search clicked', filter);
      this.Remote.post('/bms-objective/ProjectItem/Item', filter.getRawValue()).subscribe(
         data => {
            this.SearchMode = true;
            this.Data = [];
            if (Array.isArray(data)) {
               data.forEach(d => {
                  this.Data.push(new ProjectEntry(d));
               });
            }
            this.DisplayLoading = false;
         },
         error => {
            this.Remote.logInformationToConsole(true, true, 'Search Error: ', error);
            this.DisplayLoading = false;
         }
      );
   }

   public ResetSearch(filter: FormGroup) {
      filter.reset();
      this.GetData();
   }

   ColumnNames(): string[] {
      this.ExtraCol = [];
      if (this.Data && this.Data.length > 0) {
         this.Data.forEach(d => {
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


   RunEditorFetcher() {
      this.Remote.get('/bms-objective/ProjectItem/GetForm').subscribe(
         data => {
            this.ProjectEditor = new FormList(data.find(t => t.item1 === 'Project').item2);
            this.ModuleEditor = new FormList(data.find(t => t.item1 === 'Module').item2);
            this.SubModuleEditor = new FormList(data.find(t => t.item1 === 'SubModule').item2);
            this.MilestoneEditor = new FormList(data.find(t => t.item1 === 'Milestone').item2);
            this.RequirementEditor = new FormList(data.find(t => t.item1 === 'Requirement').item2);
            this.IssueEditor = new FormList(data.find(t => t.item1 === 'Issue').item2);
            this.ProjectCreator = new FormList(data.find(t => t.item1 === 'Project').item2);
            this.ModuleCreator = new FormList(data.find(t => t.item1 === 'Module').item2);
            this.SubModuleCreator = new FormList(data.find(t => t.item1 === 'SubModule').item2);
            this.MilestoneCreator = new FormList(data.find(t => t.item1 === 'Milestone').item2);
            this.RequirementCreator = new FormList(data.find(t => t.item1 === 'Requirement').item2);
            this.IssueCreator = new FormList(data.find(t => t.item1 === 'Issue').item2);
            this.ObjectiveController.ObjectiveEditor = new FormList(data.find(t => t.item1 === 'ObjectiveModel').item2);
            this.FilterListForm = new FormList(data.find(t => t.item1 === 'Search').item2);
            this.AnalysisEditor = new FormList(data.find(t => t.item1 === 'Analysis').item2);
         },
         errors => {
            this.Remote.logInformationToConsole(true, true, errors);
         }
      );
      // this.Remote.get('/bms-objective/Analysis/Form').subscribe(
      //    data => this.AnalysisEditor = new FormList(data),
      //    er => this.Remote.logInformationToConsole(true, true, 'error fetching analysis editor', er));
   }

   private RunFilterFetcher() {
      this.Remote.get('/bms-objective/ProjectItem/GetFilter').subscribe(
         data => {
            this.FilterListForm = new FormList(data);
         },
         errors => {
            this.Remote.logInformationToConsole(true, true, errors);
         }
      );
   }

   DeleteProjectItem(data: ProjectEntry, parent?: ProjectEntry) {
      this.openDialog('Delete?', `Are you sure to delete ${data}?`, 'Delete It', `Don't Delete`,
         DialogType.Alert, (s) => this.TrashProjectItem(data, parent), data.id);
   }

   TrashProjectItem(data: ProjectEntry, parent?: ProjectEntry) {
      this.DisplayLoading = true;
      this.Remote.logInformationToConsole(false, false, 'Trashing Data', data.id);
      this.Remote.delete('/bms-objective/ProjectItem/CascadeDel/' + data.id, null, false).subscribe(
         resp => {
            if (parent) {
               const old = parent.children.findIndex(t => t.id === data.id);
               parent.children.splice(old, 1);
            } else {
               const oldLoc = this.Data.findIndex(t => t.id === data.id);
               if (oldLoc > -1) {
                  this.Data.splice(oldLoc, 1);
               }
            }
            const snackref = this.snackbar.open('Item Deleted.', '', {
               duration: 5000
            });
            this.DisplayLoading = false;
         },
         error => {
            this.Remote.logInformationToConsole(true, true, error);
            this.DisplayLoading = false;
         }
      );
   }

   PostAnalysis(fg: FormGroup) {
      if (fg.get('id').value == null) {
         this.Remote.post('/bms-objective/Analysis/create', fg.getRawValue(), fg).subscribe(
            resp => {
               this.snackbar.open('Analysis Updated', null, { duration: 1500 });
               fg.get('Documents').setValue(null);
            },
            err => this.Remote.logInformationToConsole(true, true, 'Error Saving Analysis', err)
         );
      } else {
         this.Remote.put('/bms-objective/Analysis/update', fg.getRawValue(), fg).subscribe(
            resp => {
               this.snackbar.open('Analysis Updated', null, { duration: 1500 });
               fg.get('Documents').setValue(null);
            },
            err => this.Remote.logInformationToConsole(true, true, 'Error Updating Analysis', err)
         );
      }
   }

   GetAnalysis(id: string) {
      return this.Remote.get('/bms-objective/Analysis/GetAnalysis/' + id).pipe(map(
         data => data,
         er => this.Remote.logInformationToConsole(true, true, 'Error fetching Analysis', er)
      ));
   }

   OrderBy(name: string) {
      this.order = !this.order;
      this.Data = this.Data.sort((a, b) => {
         let start, end;
         if (name.substring(0, 1) === name.toLowerCase().substring(0, 1)) {
            start = a[name];
            end = b[name];
         } else {
            const A = a.moreProperties.find(t => t.propertyInfo.name === name);
            const B = b.moreProperties.find(t => t.propertyInfo.name === name);
            start = this.GetValue(A);
            end = this.GetValue(B);
         }
         if (start != null && end == null) {
            return this.order ? 1 : -1;
         } else if (start == null && end != null) {
            return this.order ? -1 : 1;
         } else if (start === end == null) {
            return 0;
         }
         if (typeof start === 'string' || typeof end === 'string') {
            if (start && end) {
               return start.toUpperCase() > end.toUpperCase() ? (this.order ? 1 : -1) : (this.order ? -1 : 1);
            }
         } else {
            return start > end ? (this.order ? 1 : -1) : (this.order ? -1 : 1);
         }
		 return -1;
      });
   }
   GetValue(val: AdditionalProperty): any {
      if (val.bitValue != null) {
         return val.bitValue;
      } else if (val.dateValue != null) {
         return val.dateValue;
      } else if (val.decimalValue != null) {
         return val.decimalValue;
      } else {
         return val.textValue;
      }
   }

   ChangeProjectTab(tabId:String) {
      this.CurrentTab = tabId;
      this.GetData();
   }
}
