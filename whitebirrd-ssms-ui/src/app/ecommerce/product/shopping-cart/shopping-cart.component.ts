import { Component, OnInit, EventEmitter } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AuthService } from '../../../services/auth.service';
import { Router, ActivatedRoute } from '@angular/router';
import { ProductDataServiceService } from "../../product-data-service.service";
import { RemoteService } from '../../../services/remote.service';
import { ErrorValidatorService } from '../../error-validator/errorValidatorService';
import { Constants } from '../../../constants';
import { GlobalService } from '../../../services/global.service';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.scss']
})
export class ShoppingCartComponent implements OnInit {
	
  userData: any;
  cartData: any;
  cart: any;
  colorConstant: any;
  public total:any;
  constructor(public dialog: MatDialog,  private _globalService: GlobalService, private router: Router, private remoteService: RemoteService,
    private authService: AuthService, private ProductDataServiceService: ProductDataServiceService) { }

  ngOnInit() {
    this.ProductDataServiceService.updateCart.subscribe(message => {
      console.log(message);
      this.cart = { 'cart.cartType': "SHOPPING" };
      const jsonQueryString = JSON.stringify(this.cart);
      this.remoteService.get('/essms-inventory/cartitem/list?jsonQueryString=' + encodeURI(jsonQueryString)).subscribe(
        data => {
          console.log(data)
          this.cartData = data.entityList;
         let sum=0
          this.cartData.forEach(function (value) {
             sum= sum + Number(value.price.price);
           
          });
          this.total =sum;
        },
        error => {
          console.log(JSON.stringify(error));
        })
    });
    this. getCartData();
  }
  // getSum(index: number) : number {
  //   let sum = 0;
  //   for(let i = 0; i < this.items.length; i++) {
  //     sum += this.items[i][index];
  //   }
  //   return sum;
  // }
  getCartData() {
    //this.ProductDataServiceService.updateCart.subscribe(message => this.cartData = message);
    this.cart = { 'cart.cartType': "SHOPPING" };
    const jsonQueryString = JSON.stringify(this.cart);
    this.remoteService.get('/essms-inventory/cartitem/list' + encodeURI(jsonQueryString)).subscribe(
      data => {
        //this.successMessage.updateSuccessMsg("Address added successfully");
        console.log(data)
        this.cartData = data.entityList;
      },
      error => {
        console.log(JSON.stringify(error));
      })
  
  }
  getColorConstants() {
    this.colorConstant = {};
    this.colorConstant.header = {};
    this.colorConstant.header.background = "#1976d2";
    this.colorConstant.header.color = "#1976d2";
    this.colorConstant.Button = {
      // 'background-color': "#1976d2",
       'color': '#fff',
      'font-size': '14px'
      // 'border-bottom': '1px solid #fff',
      // 'margin-right': '10px'
    };

  }
  getUserData() {
    // console.log(sessionStorage.getItem(Constants.LOGGED_USER));
    this.userData = JSON.parse(sessionStorage.getItem(Constants.LOGGED_USER));
  }
  
  public get globalService(): GlobalService {
      return this._globalService;
  }
}
