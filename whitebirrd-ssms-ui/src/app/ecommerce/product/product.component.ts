import { Component, OnInit, Input } from '@angular/core';
import { MediaObserver, MediaChange } from '@angular/flex-layout';
import { Observable } from 'rxjs';
import { map, filter } from 'rxjs/operators';

declare var $: any;
import { ProductDataServiceService } from '../product-data-service.service';
import { SuccessMessageService } from '../success-message.service';
import { RemoteService } from './../../services/remote.service';
import { GlobalService } from './../../services/global.service';
import { ErrorValidatorService } from './../error-validator/errorValidatorService';
import { Router } from '@angular/router';
@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.scss']
})
export class ProductComponent implements OnInit {
	
  @Input() data: any[];
  //modified by Rakesh
  //@Input() data: object;
  public productImage: any;
  public productStyle1 = true;
  public productStyle2 = false;
  columnNum = 3;
  public cols: Observable<number>;
  constructor(
    // matIconRegistry: MatIconRegistry,
    // sanitizer: DomSanitizer,

    private productDataServiceService: ProductDataServiceService,
    private successMessage: SuccessMessageService,
    private remoteService: RemoteService,
    private _globalService: GlobalService,
    private router: Router,
    public media: MediaObserver
  ) {
    media.asObservable().pipe(
      filter((changes: MediaChange[]) => changes.length > 0),
      map((changes: MediaChange[]) => changes[0])
      ).subscribe((change: MediaChange) => {
      // alert(change.mqAlias);
      console.log(change.mqAlias);
      if (change.mqAlias === 'xs') {
        this.columnNum = 1;
      } else if (change.mqAlias === 'sm') {
        this.columnNum = 2;
      } else {
        this.columnNum = 3;
      }
    });
    // matIconRegistry
    //   .addSvgIcon('github',
    //     sanitizer.bypassSecurityTrustResourceUrl('/assets/img/github.svg'))
    //   .registerFontClassAlias('fontawesome', 'fa');
  }

  ngOnInit() {


  }
  navigateTodetails(productdetails) {
    const prod = JSON.stringify(productdetails);
    sessionStorage.setItem('productDetails', prod);
    this.router.navigate(['/ecommerce/product-details']);

  }
  getProductlist(imagePath) {
    this.remoteService.get('essms-inventory/public/file/view?absolutePath=' + imagePath).subscribe(
       data => {
        // this.successMessage.updateSuccessMsg("Address added successfully");
        console.log(data);
        return data;
      },
      error => {
        console.log(JSON.stringify(error));
      });
      }

      addTocart(cartObject) {
        // let addTocartObj=[];
        // let cartDataString;
        // this.cartData.push(cartObject);
        // if(this.cartData){

        // }
        // cartDataString=JSON.stringify(this.cartData);
        // this.ProductDataServiceService.updateCartData(cartDataString);
      //  const cartData=cartObject;
       const cartData = {
        'branchStockGUID': cartObject.branchStockGUID,
        'quantity': 1
      };
        this.remoteService.post('/essms-inventory/cartitem/add/SHOPPING', cartData).subscribe(
          data => {
            this.successMessage.updateSuccessMsg('Product added successfully');
            // console.log(data);
            this.productDataServiceService.updateCartData(cartObject);
           // this.productList=data.entityList;
          },
          error => {
            console.log(JSON.stringify(error));
          });

      }
      
    public get globalService(): GlobalService {
        return this._globalService;
    }
}
