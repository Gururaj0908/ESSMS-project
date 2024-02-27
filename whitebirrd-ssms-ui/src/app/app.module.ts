import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { LoginFormComponent } from './login-form/login-form.component';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AdminModule } from './admin/admin.module';
import { EcommerceModule } from './ecommerce/ecommerce.module';

import { MaterialModule } from './material.module';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { GlobalService } from './services/global.service';
import { RemoteService } from './services/remote.service';
import { JwtInterceptor } from './helpers/jwt.interceptor';
import { AuthService } from './services/auth.service';
import { AuthGuard } from './guards/auth.guard';

import { ConfirmDialogComponent } from './helpers/confirm-dialog/confirm-dialog.component';
import { SelectorPopupComponent } from './admin/mbo/selector-popup/selector-popup.component';
import { PopupViewComponent } from './admin/popup-view/popup-view.component';
import { TimeLineViewComponent } from './admin/mbo/time-line-view/time-line-view.component';
import { AdminListComponent } from './admin/admin-list/admin-list.component';

import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';



@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    LoginFormComponent,
    RegisterComponent,
    ConfirmDialogComponent,
  ],
  imports: [
	AdminModule,
    EcommerceModule,
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MaterialModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [
	AuthGuard,
    GlobalService,
    AuthService,
    RemoteService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: JwtInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent],
  entryComponents: [ConfirmDialogComponent, SelectorPopupComponent, PopupViewComponent, TimeLineViewComponent, AdminListComponent]
})
export class AppModule { }
