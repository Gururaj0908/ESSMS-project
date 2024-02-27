import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AdminComponent } from './admin.component';
import { FooterComponent } from './footer/footer.component';
import { HeaderComponent } from './header/header.component';
import { SSMSAdminRoutes } from './admin.routes';
import { MaterialModule } from '../material.module';
import { SideNavigationComponent } from './side-navigation/side-navigation.component';
import { NavigationService } from './services/navigation.service';
import { AdminListComponent } from './admin-list/admin-list.component';
import { CrudService } from './services/crud.service';
import { DynamicFormComponent } from './dynamic-form/dynamic-form.component';
import { RemoteService } from '../services/remote.service';
import { MenuHelperService } from './services/menu.helper.service';
import { ProjectComponent } from './mbo/project/project.component';
import { DynamicObjectiveDisplayComponent } from './mbo/dynamic-objective-display/dynamic-objective-display.component';
import { DynamicProjectDisplayComponent } from './mbo/dynamic-project-display/dynamic-project-display.component';
import { UserPipe } from './mbo/user.pipe';
import { FormAreaComponent } from './mbo/form-area/form-area.component';
import { EditorFormComponent } from './mbo/editor-form/editor-form.component';
import { TreeviewModule } from '@yberion/ngx-treeview';
import { UserListComponent } from './user/user-list/user-list.component';
import { RoleListComponent } from './user/role-list/role-list.component';
import { UserPermissionComponent } from './user/user-permission/user-permission.component';
import { RolePermissionComponent } from './user/role-permission/role-permission.component';
import { TreeviewService } from './services/treeview.service';
import { EditorFormTestComponent } from './mbo/editor-form-test/editor-form-test.component';
import { NamifyPipe } from './mbo/namify.pipe';
import { AdminFormComponent } from './admin-form/admin-form.component';
import { SelectorPopupComponent } from './mbo/selector-popup/selector-popup.component';
import { TableViewComponent } from './table-view/table-view.component';
import { PopupViewComponent } from './popup-view/popup-view.component';
import { PdfViewerModule } from 'ng2-pdf-viewer';
import { MatFileUploadControlComponent } from './mat-file-upload-control/mat-file-upload-control.component';
import { LinkedSelectListComponent } from './linked-select-list/linked-select-list.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { TreeViewComponent } from './mbo/tree-view/tree-view.component';
import { TechnicalProcessComponent } from './mbo/technical-process/technical-process.component';
import { EditorModule } from '@tinymce/tinymce-angular';
import { ProcessComponent } from './mbo/process/process.component';
import { TreeAreaComponent } from './tree-area/tree-area.component';
import { AdminTreeComponent } from './admin-tree/admin-tree.component';
import { TimeLineViewComponent } from './mbo/time-line-view/time-line-view.component';
import { MultiLevelSelectComponent } from './mbo/multi-level-select/multi-level-select.component';
import { StageSelectionComponent } from './mbo/stage-selection/stage-selection.component';
import { StepperFormComponent } from './stepper-form/stepper-form.component';
import { FocusModule } from 'angular2-focus';
import { DynamicRequirementComponent } from './mbo/dynamic-requirement/dynamic-requirement.component';
import { DynamicIssueComponent } from './mbo/dynamic-issue/dynamic-issue.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { PriceSelectorComponent } from './price-selector/price-selector.component';
import { DetailModelDisplayComponent } from './popup-view/detail-model-display/detail-model-display.component';
import { MultiCheckBoxComponent } from './multi-check-box/multi-check-box.component';
import { NewTimeAgoPipe } from './NewTimeAgoPipe';
import { MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';

@NgModule({
  declarations: [
    AdminComponent,
    FooterComponent,
    HeaderComponent,
    DashboardComponent,
    SideNavigationComponent,
    AdminListComponent,
    DynamicFormComponent,
    ProjectComponent,
    DynamicProjectDisplayComponent,
    DynamicObjectiveDisplayComponent,
    UserPipe,
    FormAreaComponent,
    EditorFormComponent,
    UserListComponent,
    RoleListComponent,
    UserPermissionComponent,
    RolePermissionComponent,
    EditorFormTestComponent,
    NamifyPipe,
    AdminFormComponent,
    SelectorPopupComponent,
    TableViewComponent,
    PopupViewComponent,
    MatFileUploadControlComponent,
    LinkedSelectListComponent,
    NotFoundComponent,
    TreeViewComponent,
    TechnicalProcessComponent,
    ProcessComponent,
    TreeAreaComponent,
    AdminTreeComponent,
    TimeLineViewComponent,
    NewTimeAgoPipe,
    MultiLevelSelectComponent,
    StageSelectionComponent,
    StepperFormComponent,
    DynamicRequirementComponent,
    DynamicIssueComponent,
    PriceSelectorComponent,
    DetailModelDisplayComponent,
    MultiCheckBoxComponent,
  ],
  imports: [
    BrowserModule,
    SSMSAdminRoutes,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    PdfViewerModule,
    HttpClientModule,
    TreeviewModule.forRoot(),
    EditorModule,
    FocusModule.forRoot(),
    MatDatepickerModule,
    MatNativeDateModule
  ],
  providers: [
    NavigationService,
    CrudService,
    RemoteService,
    DynamicObjectiveDisplayComponent,
    DynamicProjectDisplayComponent,
    FormAreaComponent,
    EditorFormComponent,
    MenuHelperService,
    TableViewComponent,
    TreeAreaComponent,
    DynamicIssueComponent,
    DynamicRequirementComponent,
    DetailModelDisplayComponent,
    TreeviewService],
  bootstrap: [DashboardComponent]
})
export class AdminModule { }
