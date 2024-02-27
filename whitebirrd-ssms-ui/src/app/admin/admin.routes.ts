import { AdminComponent } from './admin.component';
import { ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AdminListComponent } from './admin-list/admin-list.component';
import { ProjectComponent } from './mbo/project/project.component';
import { UserListComponent } from './user/user-list/user-list.component';
import { RoleListComponent } from './user/role-list/role-list.component';
import { UserPermissionComponent } from './user/user-permission/user-permission.component';
import { RolePermissionComponent } from './user/role-permission/role-permission.component';
import { EditorFormTestComponent } from './mbo/editor-form-test/editor-form-test.component';
import { AdminFormComponent } from './admin-form/admin-form.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { ProcessComponent } from './mbo/process/process.component';
import { AdminTreeComponent } from './admin-tree/admin-tree.component';
import { DashboardComponent } from './dashboard/dashboard.component';

export const adminrouter: Routes = [
  {
    path: 'admin', component: AdminComponent,
    children: [{ path: '', component: NotFoundComponent, pathMatch: 'full' },
    { path: 'dashboard', component: DashboardComponent },
    { path: 'mbo/project', component: ProjectComponent },
    { path: 'formeditor/test', component: EditorFormTestComponent },
    { path: 'details/:id', component: AdminListComponent, },
    { path: 'details', component: AdminListComponent, },
    { path: 'form/:id', component: AdminFormComponent, },
    { path: 'tree/:id', component: AdminTreeComponent, },
    { path: 'form', component: AdminFormComponent, },
    { path: 'user/list', component: UserListComponent },
    { path: 'role/list', component: RoleListComponent },
    { path: 'mbo/process', component: ProcessComponent },
    { path: 'user/permission/:id', component: UserPermissionComponent },
    { path: 'role/permission/:id', component: RolePermissionComponent },
    { path: '404', component: NotFoundComponent },
    { path: '**', redirectTo: '404' }]
  }
];

export const SSMSAdminRoutes: ModuleWithProviders<any> = RouterModule.forChild(adminrouter);
