import { Component, OnInit, EventEmitter } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { AuthService } from '../services/auth.service';
import { Router, ActivatedRoute } from '@angular/router';
import { LoginComponent } from '../login/login.component';
import { ProductDataServiceService } from './product-data-service.service';
import { RemoteService } from './../services/remote.service';
import { MediaChange, MediaObserver } from '@angular/flex-layout';
import { ErrorValidatorService } from './error-validator/errorValidatorService';
import { Constants } from '../constants';
import { LanguageService } from '../services/language.service';
@Component({
  selector: 'app-ecommerce',
  templateUrl: './ecommerce.component.html',
  styleUrls: ['./ecommerce.component.scss']
})
export class EcommerceComponent implements OnInit {
  onSignOut = new EventEmitter();
  navLinks = [
    {
      'label': this.l.GetString('Home'),
      'path': '/ecommerce/home'
    },
    {
      'label': this.l.GetString('RepairStatus'),
      'path': '/ecommerce/product-category'
    },
    {
      'label': this.l.GetString('BookRepair'),
      'path': '/ecommerce/book-product-repair'
    },
    {
      'label': this.l.GetString('FakeProduct'),
      'path': '/ecommerce/fake-product-identification'
    },

  ];
  theme = 'my-theme';
  userData: any;
  cartData: any;
  cart: any;
  colorConstant: any;
  columnNum = 0;
  constructor(public l: LanguageService,public dialog: MatDialog, private router: Router, private remoteService: RemoteService,
    private authService: AuthService, private productDataServiceService: ProductDataServiceService,  public media: MediaObserver) { }

  ngOnInit() {

    this.productDataServiceService.updateCart.subscribe(message => {
      console.log(message);
      this.cart = { 'cart.cartType': 'SHOPPING' };
      const jsonQueryString = JSON.stringify(this.cart);
      this.remoteService.get('/essms-inventory/cartitem/list?jsonQueryString=' + encodeURI(jsonQueryString)).subscribe(
        data => {
          console.log(data);
          this.cartData = data.entityList;
        },
        error => {
          console.log(JSON.stringify(error));
        });
    });
    this.getUserData();

    this.getColorConstants();
    this.authService.updateSignOut.subscribe(message => {
      this.getUserData();
    });
  }
  getCartData() {
    // this.ProductDataServiceService.updateCart.subscribe(message => this.cartData = message);
    this.cart = { 'cart.cartType': 'SHOPPING' };
    const jsonQueryString = JSON.stringify(this.cart);
    this.remoteService.get('/essms-inventory/cartitem/list' + encodeURI(jsonQueryString)).subscribe(
      data => {
        // this.successMessage.updateSuccessMsg("Address added successfully");
        console.log(data);
        this.cartData = data.entityList;
      },
      error => {
        console.log(JSON.stringify(error));
      });

  }
  getColorConstants() {
    this.colorConstant = {};
    this.colorConstant.header = {};
    this.colorConstant.header.background = '#1976d2';
    this.colorConstant.header.color = '#1976d2';
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


  signOut() {

    this.authService.signOut();
    this.authService.updateSignOutData();
    this.router.navigate(['ecommerce/home']);
  }
}
