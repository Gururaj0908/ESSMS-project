import { Component, OnInit } from '@angular/core';
import { GlobalService } from '../../services/global.service';
@Component({
   selector: 'app-product-details',
   templateUrl: './product-details.component.html',
   styleUrls: ['./product-details.component.scss']
})
export class ProductDetailsComponent implements OnInit {
   productData = JSON.parse(sessionStorage.getItem('productDetails'));

   textFeature = this.productData.textFeature;
   constructor(public globalService: GlobalService) { }

   ngOnInit() {
      console.log(this.productData);
   }

}
