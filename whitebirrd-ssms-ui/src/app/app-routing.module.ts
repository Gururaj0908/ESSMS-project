import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginFormComponent } from './login-form/login-form.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'login-form', component: LoginFormComponent },
  { path: 'ecommerce', loadChildren: () => import('../app/ecommerce/ecommerce.module').then(m => m.EcommerceModule)},
  { path: 'admin', loadChildren: () => import('../app/admin/admin.module').then(m => m.AdminModule)},
  { path: 'register', component: RegisterComponent },
  { path: '', redirectTo: '/ecommerce/home', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash:true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }


