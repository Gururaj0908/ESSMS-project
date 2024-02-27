import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AccountComponent } from './account/account.component';
import { PaymentsComponent } from './payments/payments.component';
import { MyStuffComponent } from './my-stuff/my-stuff.component';
import { MyOrderComponent } from './my-order/my-order.component';
import { SSMSEcommerceRoutes } from './user-profile.routes'
import { UserProfileComponent } from './user-profile.component';
import { MaterialModule } from '../../material.module';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FlexLayoutModule, BREAKPOINT } from '@angular/flex-layout';
import { ManageAddressComponent } from './account/manage-address/manage-address.component';
import { ErrorValidatorService } from '../../ecommerce/error-validator/errorValidatorService';
import { SuccessMessageService } from '../../ecommerce/success-message.service';
import { ErrorValidatorComponent } from '../../ecommerce/error-validator/error-validator.component';
import { NotificationPreferenceComponent } from './account/notification-preference/notification-preference.component';
import { ConfirmationDilogComponent } from '../confirmation-dilog/confirmation-dilog.component';
import { ResetPasswordComponent } from './account/reset-password/reset-password.component';
import { SuccessDilogComponent } from '../success-dilog/success-dilog.component';
import { ProfileHeaderComponent } from '../profile-header/profile-header.component';
import { ThemeSettingsComponent } from './theme-settings/theme-settings.component';
@NgModule({
  imports: [
    CommonModule,
    BrowserModule,
    FormsModule,
    HttpClientModule,
    SSMSEcommerceRoutes,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    FlexLayoutModule,
  ],
  declarations: [AccountComponent, PaymentsComponent, MyStuffComponent, MyOrderComponent, UserProfileComponent, ManageAddressComponent, NotificationPreferenceComponent, ErrorValidatorComponent, ConfirmationDilogComponent, ResetPasswordComponent, SuccessDilogComponent, ProfileHeaderComponent, ThemeSettingsComponent],
  providers: [ErrorValidatorService, SuccessMessageService],
  exports: [ErrorValidatorComponent, ConfirmationDilogComponent, SuccessDilogComponent, ManageAddressComponent],
  entryComponents: [ConfirmationDilogComponent, SuccessDilogComponent]
})
export class UserProfileModule { }
