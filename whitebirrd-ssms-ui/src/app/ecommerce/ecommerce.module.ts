import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { EcommerceComponent } from './ecommerce.component';
import { SSMSEcommerceRoutes } from './ecommerce.routes'
import { MaterialModule } from './../material.module';
import { HomeComponent } from './home/home.component';
import { ProductCategoryComponent } from './product-category/product-category.component';
import { BookProductRepairComponent } from './book-product-repair/book-product-repair.component';
import { FakeProductIdentificationComponent } from './fake-product-identification/fake-product-identification.component';
import { ProductDetailsComponent } from './product-details/product-details.component';
import {FlexLayoutModule, BREAKPOINT} from '@angular/flex-layout';
import { UserProfileModule } from './user-profile/user-profile.module';
//import { AgmCoreModule } from '@agm/core';
//third party library
import { OwlModule } from 'ngx-owl-carousel';
import { AddProductComponent } from './add-product/add-product.component';
import { ProductDataServiceService } from "./product-data-service.service";
import { ProductComponent } from './product/product.component';
import { ProductCartComponent } from './product/product-cart/product-cart.component';
import { ProductOfferComponent } from './product/product-offer/product-offer.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { MobileHeaderComponent } from './mobile-header/mobile-header.component';
import { ShoppingCartComponent } from './product/shopping-cart/shopping-cart.component';
import { DeliveryDetailsComponent } from './product/delivery-details/delivery-details.component';
import { CardDetailsComponent } from './product/card-details/card-details.component';
import { ProductRepairStatusComponent } from './product-repair-status/product-repair-status.component';



const PRINT_BREAKPOINTS = [{
  alias: 'xs.print',
  suffix: 'XsPrint',
  mediaQuery: 'print and (max-width: 297px)',
  overlapping: false
}];
@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    HttpClientModule,
    SSMSEcommerceRoutes,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    FlexLayoutModule,
    UserProfileModule,
    OwlModule,
 //   AgmCoreModule.forRoot({
      // please get your own API key here:
      // https://developers.google.com/maps/documentation/javascript/get-api-key?hl=en
 //     apiKey: 'AIzaSyAvcDy5ZYc2ujCS6TTtI3RYX5QmuoV8Ffw'
 //   })
  ],
  declarations: [EcommerceComponent, HomeComponent, ProductCategoryComponent, BookProductRepairComponent, FakeProductIdentificationComponent, ProductDetailsComponent, AddProductComponent, ProductComponent, ProductCartComponent, ProductOfferComponent, HeaderComponent, FooterComponent, MobileHeaderComponent, ShoppingCartComponent, DeliveryDetailsComponent, CardDetailsComponent, ProductRepairStatusComponent],
  exports:[FlexLayoutModule,HeaderComponent],
  providers: [
    ProductDataServiceService,
    {provide: BREAKPOINT, useValue: PRINT_BREAKPOINTS, multi: true}
  ],

})
export class EcommerceModule { }
