import { ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { EcommerceComponent } from './ecommerce.component';
import { HomeComponent } from './home/home.component';
import { ProductCategoryComponent } from './product-category/product-category.component';
import { BookProductRepairComponent } from './book-product-repair/book-product-repair.component';
import { FakeProductIdentificationComponent } from './fake-product-identification/fake-product-identification.component';
import { ProductDetailsComponent } from './product-details/product-details.component';
import { AddProductComponent } from './add-product/add-product.component';
import { ShoppingCartComponent } from './product/shopping-cart/shopping-cart.component';
import { DeliveryDetailsComponent } from './product/delivery-details/delivery-details.component';
import { CardDetailsComponent } from './product/card-details/card-details.component';
import { ProductRepairStatusComponent } from './product-repair-status/product-repair-status.component';
export const ecommercerouter: Routes = [
  {
    path: 'ecommerce', component: EcommerceComponent, children: [
      { path: '', redirectTo: 'ecommerce/home', pathMatch: 'full' },
      { path: 'home', component: HomeComponent },
      { path: 'product-category', component: ProductCategoryComponent },
      { path: 'book-product-repair', component: BookProductRepairComponent },
      { path: 'product-details', component: ProductDetailsComponent },
      { path: 'fake-product-identification', component: FakeProductIdentificationComponent },
      { path: 'add-product', component: AddProductComponent },
      { path: 'user-profile', loadChildren: () => import('./user-profile/user-profile.module').then(m => m.UserProfileModule) },
      { path: 'shopping-cart', component: ShoppingCartComponent },
      { path: 'delivery-details', component: DeliveryDetailsComponent },
      { path: 'card-details', component: CardDetailsComponent },
      { path: 'repair-status', component: ProductRepairStatusComponent },
    ]
  }
];

export const SSMSEcommerceRoutes: ModuleWithProviders<any> = RouterModule.forChild(ecommercerouter);
