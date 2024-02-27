import { Component, OnInit, Inject, ViewChild } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { HttpClientModule, HTTP_INTERCEPTORS, HttpClient } from '@angular/common/http';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { RemoteService } from '../../services/remote.service';
import { ErrorValidatorService } from '../error-validator/errorValidatorService';
import {FlatTreeControl} from '@angular/cdk/tree';
import { ConfirmationDilogComponent } from '../confirmation-dilog/confirmation-dilog.component';
import { Constants } from '../../constants';
import { SuccessMessageService } from '../success-message.service';
// import {Component, Injectable} from '@angular/core';
import {MatTreeFlatDataSource, MatTreeFlattener} from '@angular/material/tree';
import {BehaviorSubject, Observable, of as observableOf} from 'rxjs';
@Component({
  selector: 'app-product-category',
  templateUrl: './product-category.component.html',
  styleUrls: ['./product-category.component.scss']
})
export class ProductCategoryComponent implements OnInit {
  guId:any;
  endCustomer:any;
  categoryList:any;
  productList:any;
  constructor(private remoteService: RemoteService,private successMessage: SuccessMessageService,) { }

  ngOnInit() {
    this.getCategoryListData();
    this.getProductlist();
  }
  getCategoryListData(){
    const userData = JSON.parse(sessionStorage.getItem(Constants.LOGGED_USER));
    this.guId = userData.loginSuccess.userGUID;
    //this.cardData.customercardModels =[];
    //this.cardData.userGUID = this.guId;
    this.endCustomer={'endCustomer.guid':this.guId};
    const jsonQueryString=JSON.stringify(this.endCustomer);
    this.remoteService.get('/essms-inventory/option/itemcategory').subscribe(
      data => {
        console.log(data);
        this.categoryList=data;
      },
      error => {
      });
  }
  getProductlist() {
    this.remoteService.get('/essms-inventory/public/product/list').subscribe(
      data => {
        console.log(data)
        this.productList=data.entityList;
      },
      error => {
        console.log(JSON.stringify(error));
      })
      }
}
