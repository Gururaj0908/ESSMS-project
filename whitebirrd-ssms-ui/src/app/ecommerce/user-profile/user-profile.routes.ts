import { ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { EcommerceComponent } from '../ecommerce.component';
import { AccountComponent } from './account/account.component';
import { PaymentsComponent } from './payments/payments.component';
import { MyStuffComponent } from './my-stuff/my-stuff.component';
import { MyOrderComponent } from './my-order/my-order.component';
import { UserProfileComponent } from './user-profile.component';
import { ManageAddressComponent } from './account/manage-address/manage-address.component';
import { NotificationPreferenceComponent } from './account/notification-preference/notification-preference.component';
import { ResetPasswordComponent } from './account/reset-password/reset-password.component';
import { ThemeSettingsComponent } from './theme-settings/theme-settings.component';
export const userProfilerouter: Routes = [

  {
    path: 'ecommerce/user-profile', component: UserProfileComponent,
    // children: [{ path: ' ', redirectTo: 'ecommerce', pathMatch: 'full' }]
  
    children: [{ path: ' ', redirectTo: 'ecommerce/user-profile/account', pathMatch: 'full' },
    { path: 'account', component: AccountComponent },
    { path: 'manage-address', component: ManageAddressComponent },
    { path: 'save-cards', component: PaymentsComponent },
    { path: 'notification-preference', component: NotificationPreferenceComponent },
    { path: 'reviews-rating', component: MyStuffComponent },
    { path: 'reset-password', component: ResetPasswordComponent },
    { path: 'theme-settings', component: ThemeSettingsComponent }
    
  ]
  }
];

export const SSMSEcommerceRoutes: ModuleWithProviders<any> = RouterModule.forChild(userProfilerouter);
