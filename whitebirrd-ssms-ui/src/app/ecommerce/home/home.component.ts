import { Component, OnInit, ElementRef, Renderer2, ViewChild } from '@angular/core';
// import { DomSanitizer } from '@angular/platform-browser';
// import { MatIconRegistry } from '@angular/material';
import { DragScrollDirective } from '../../../dragscroll/ngx-drag-scroll';
import { MediaChange, MediaObserver } from '@angular/flex-layout';
import { Observable} from 'rxjs';
declare var $: any;
import { ProductDataServiceService } from '../product-data-service.service';
import { SuccessMessageService } from '../success-message.service';
import { RemoteService } from './../../services/remote.service';
import { ErrorValidatorService } from './../error-validator/errorValidatorService';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  hideScrollbar;
  disabled;
  xDisabled;
  yDisabled;
  productlistBackground = [];
  leftNavDisabled = false;
  rightNavDisabled = false;
  productList: any;
  dragScrollDom: any;
  dragScrollRef: ElementRef;
  dragScroll: DragScrollDirective;
  private cartData = [];
  // public cols: number = 2;
  public cols: Observable<number>;
  @ViewChild('nav', { read: DragScrollDirective, static: false }) ds: DragScrollDirective;

  constructor(
    // matIconRegistry: MatIconRegistry,
    // sanitizer: DomSanitizer,
    private element: ElementRef,
    private observableMedia: MediaObserver,
    private renderer: Renderer2,
    private productDataServiceService: ProductDataServiceService,
    private successMessage: SuccessMessageService,
    private remoteService: RemoteService,

  ) {

    // matIconRegistry
    //   .addSvgIcon('github',
    //     sanitizer.bypassSecurityTrustResourceUrl('/assets/img/github.svg'))
    //   .registerFontClassAlias('fontawesome', 'fa');

  }

  colorConstant: any;
  ngOnInit() {
   this.getCartData();
   this.getColorConstants();
    $(document).ready(function() {
      $('.banner').owlCarousel({
        autoHeight: false,
        center: true,
        nav: true,
        items: 1,
        margin: 30,
        loop: false,
        autoplay: true,
        autoplayTimeout: 3000,
        autoplayHoverPause: true
      });
    });
    this.getProductlist();


  }
  getCartData() {

    // this.ProductDataServiceService.updateCart.subscribe(message => {
    //  this.cartData=JSON.parse(message);
    //   console.log(this.cartData);
    // });

  }

  getProductlist() {

this.remoteService.get('/essms-inventory/public/product/list').subscribe(
  data => {

    // this.successMessage.updateSuccessMsg("Address added successfully");
    console.log(data);
    this.productList = data.entityList;
  },
  error => {
    console.log(JSON.stringify(error));
  });
  }
  getColorConstants() {
    const PrimaryColor = '#1976d2';
    const SecondaryColor = '#fff';
    this.colorConstant = {};
    this.colorConstant.container1 = {};
    this.colorConstant.container2 = {};
    this.colorConstant.container1.background = PrimaryColor;
    this.colorConstant.container1.padding = '0 48px';
    this.colorConstant.container2.background = SecondaryColor;
    this.colorConstant.container2.padding = '0 48px';
    // this.colorConstant.header.color="#7b1fa2";
    // this.colorConstant.Button= {
    //   'background-color': "#67148a",
    //   'color': '#fff',
    //   'border-bottom':'1px solid #fff',
    //   'margin-right': '10px'
    //  };

  }
  clickItem(item) {
    console.log('itmen clicked');
  }



  toggleHideSB() {
    this.hideScrollbar = !this.hideScrollbar;
  }

  toggleDisable() {
    this.disabled = !this.disabled;
  }
  toggleXDisable() {
    this.xDisabled = !this.xDisabled;
  }
  toggleYDisable() {
    this.yDisabled = !this.yDisabled;
  }

  moveLeft() {
    this.ds.moveLeft();
  }

  moveRight() {
    this.ds.moveRight();
  }

  leftBoundStat(reachesLeftBound: boolean) {
    this.leftNavDisabled = reachesLeftBound;
  }

  rightBoundStat(reachesRightBound: boolean) {
    this.rightNavDisabled = reachesRightBound;
  }
  
  

}
