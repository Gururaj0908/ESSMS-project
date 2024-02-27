import { Component, OnInit, Inject, ViewChild } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { HttpClientModule, HTTP_INTERCEPTORS, HttpClient } from '@angular/common/http';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { RemoteService } from './../../../services/remote.service';
import { ErrorValidatorService } from './../../error-validator/errorValidatorService';
import { MediaObserver } from '@angular/flex-layout';
import { Observable } from 'rxjs';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ConfirmationDilogComponent } from './../../confirmation-dilog/confirmation-dilog.component';
import { Constants } from './../../../constants';
import { SuccessMessageService } from './../../success-message.service';

@Component({
  selector: 'app-delivery-details',
  templateUrl: './delivery-details.component.html',
  styleUrls: ['./delivery-details.component.scss']
})
export class DeliveryDetailsComponent implements OnInit {
  model: any = {};
  cityList: any = [];
  areaList: any = [];
  stateList: any = [];
  addressData: any = [];
  showAddressForm: boolean | false;
  address: any;
  action: string;
  ischecked = true;
  private guId;
  private formId: number | 0;
  // productList:any;
  public cols: Observable<number>;
  addressType = [{ 'id': 1, 'value': 'HOME' }, { 'id': 2, 'value': 'OFFICE' },
  { 'id': 3, 'value': 'BILLING' }, { 'id': 3, 'value': 'OTHER' }];
  public ManageAddressForm = this.fb.group({
    // addressName: ["", [ErrorValidatorService.requiredEmailValidator]],
    phoneNo: ['', [ErrorValidatorService.requiredValidator]],
    pinCode: ['', [ErrorValidatorService.requiredValidator]],
    addressLine1: ['', [ErrorValidatorService.requiredValidator]],
    addressLine2: ['', [ErrorValidatorService.requiredValidator]],
    // locality: ["", [ErrorValidatorService.requiredUsernNameValidator]],
    cityId: ['', [ErrorValidatorService.requiredUsernNameValidator]],
    stateId: ['', [ErrorValidatorService.requiredUsernNameValidator]],
    nearestLandMark: ['', [ErrorValidatorService.requiredUsernNameValidator]],
    // alternatePhone: ["", [ErrorValidatorService.requiredUsernNameValidator]],
    addressType: ['', [ErrorValidatorService.requiredUsernNameValidator]],
    faxNo: ['', [ErrorValidatorService.requiredUsernNameValidator]],
    areaId: ['', [ErrorValidatorService.requiredUsernNameValidator]],
  });



  constructor(private remoteService: RemoteService, private successMessage: SuccessMessageService,
    public dialog: MatDialog, public fb: FormBuilder, private observableMedia: MediaObserver, ) { }
  ngOnInit() {
    this.getAddressData();
    this.remoteService.get('/essms-admin/option/state').subscribe(
      data => {
        this.stateList = data;
        console.log(this.stateList);
      },
      error => {
      });
  }

  setAsDefault(addressdata, event) {
    const address = addressdata;
    address.userGUID = this.guId;
    address.isDefault = event.checked;
    this.remoteService.put('/essms-admin/endcustomer/address/update', address).subscribe(
      data => {
        this.successMessage.updateSuccessMsg('Selected address has been set as default');
        this.getAddressData();
      },
      error => {
        console.log(JSON.stringify(error));
      });
  }
  addnewAddress() {
    this.showAddressForm = true;
    this.ManageAddressForm = this.fb.group({
      phoneNo: ['', [ErrorValidatorService.requiredValidator]],
      pinCode: ['', [ErrorValidatorService.requiredValidator]],
      addressLine1: ['', [ErrorValidatorService.requiredValidator]],
      addressLine2: ['', [ErrorValidatorService.requiredValidator]],
      cityId: ['', [ErrorValidatorService.requiredUsernNameValidator]],
      stateId: ['', [ErrorValidatorService.requiredUsernNameValidator]],
      nearestLandMark: ['', [ErrorValidatorService.requiredUsernNameValidator]],
      addressType: ['', [ErrorValidatorService.requiredUsernNameValidator]],
      faxNo: ['', [ErrorValidatorService.requiredUsernNameValidator]],
      areaId: ['', [ErrorValidatorService.requiredUsernNameValidator]],
    });
  }
  getAddressData() {
    const userData = JSON.parse(sessionStorage.getItem(Constants.LOGGED_USER));
    this.guId = userData.loginSuccess.userGUID;
    // this.addressData.customerAddressModels =[];
    this.addressData.userGUID = this.guId;
    this.remoteService.get('/essms-admin/endcustomer/get?entityGUID=' + this.guId).subscribe(
      data => {
        this.addressData = data.customerAddress;

        console.log(this.addressData);
      },
      error => {
      });
  }
  openDialog(addressvalue): void {
    const dialogRef = this.dialog.open(ConfirmationDilogComponent, {
      width: '250px',
      data: {}
    });
    const sub = dialogRef.componentInstance.onAdd.subscribe(() => {
      // do something
      this.deleteAddress(addressvalue);
    });
    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');

      /// endcustomer/address/delete
      // this.animal = result;
    });
  }
  closeAddressForm() {
    this.showAddressForm = !this.showAddressForm;
    this.action = '';
    this.getAddressData();
  }
  deleteAddress(addressvalue) {
    this.formId = addressvalue.id;
    this.remoteService.deleteWithParam('/essms-admin/endcustomer/address/delete?hardDelete=true&id=' + this.formId).subscribe(
      data => {
        this.successMessage.updateSuccessMsg('Address removed successfully');
        this.getAddressData();
      },
      error => {
        console.log(JSON.stringify(error));
      });

  }

  editAddress(addressvalue) {
    console.log(addressvalue);
    this.showAddressForm = true;
    this.action = 'update';
    this.getAreaByCityId(addressvalue.addressModel.cityId);
    this.getCityBystateId(addressvalue.addressModel.stateId);
    this.formId = addressvalue.id;
    this.ManageAddressForm.setValue({
      phoneNo: addressvalue.addressModel.phoneNo,
      pinCode: addressvalue.addressModel.pinCode,
      addressLine1: addressvalue.addressModel.addressLine1,
      addressLine2: addressvalue.addressModel.addressLine2,
      cityId: addressvalue.addressModel.cityId,
      stateId: addressvalue.addressModel.stateId,
      nearestLandMark: addressvalue.addressModel.nearestLandMark,
      addressType: addressvalue.addressType,
      faxNo: addressvalue.addressModel.faxNo,
      areaId: addressvalue.addressModel.areaId,

    });
  }

  updateAddress(post) {
    const FormData = post;
    this.address = {};
    this.address.addressModel = {};
    this.address.addressModel = Object.assign({}, FormData);
    this.address.addressModel.id = 1;
    this.address.addressModel.latitude = 0;
    this.address.addressModel.longitude = 0;
    this.address.addressType = FormData.addressType;
    this.address.userGUID = this.guId;
    delete this.address.addressModel.addressType;
    console.log(this.formId);
    if (this.formId) {
      this.address.id = this.formId;
    }
    this.address.isDefault = false;
    if (this.action === 'update') {
      this.remoteService.put('/essms-admin/endcustomer/address/update', this.address).subscribe(
        data => {
          this.successMessage.updateSuccessMsg('Address updated successfully');
        },
        error => {
          console.log(JSON.stringify(error));
        });
    } else {
      this.remoteService.post('/essms-admin/endcustomer/address/create', this.address).subscribe(
        data => {
          this.successMessage.updateSuccessMsg('Address added successfully');
        },
        error => {
          console.log(JSON.stringify(error));
        });
    }

  }
  getCityBystateId(getStateId) {
    console.log(getStateId);
    this.remoteService.get('/essms-admin/option/citybystate?stateId=' + getStateId).subscribe(
      data => {
        this.cityList = data;
      },
      error => {
      });
  }
  getAreaByCityId(getCityId) {
    this.remoteService.get('/essms-admin/option/areabycity?cityId=' + getCityId).subscribe(
      data => {
        this.areaList = data;
      },
      error => {
      });
  }



}
