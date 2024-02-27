import { Component, OnInit, Inject, ViewChild } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { HttpClientModule, HTTP_INTERCEPTORS, HttpClient } from '@angular/common/http';
import { FormGroup, FormControl, Validators, FormBuilder, AbstractControl } from '@angular/forms';
import { RemoteService } from '../../services/remote.service';
import { ErrorValidatorService } from '../error-validator/errorValidatorService';
import { Constants } from '../../constants';
import { SuccessMessageService } from './../success-message.service';
import { Compare } from '../../admin/mbo/form-area/remote-validation.directive';
@Component({
  selector: 'app-book-product-repair',
  templateUrl: './book-product-repair.component.html',
  styleUrls: ['./book-product-repair.component.scss']
})
export class BookProductRepairComponent implements OnInit {
  @ViewChild('fileInputFront', {static: false})
  fileInputFront: any;
  @ViewChild('fileInputBack', {static: false})
  fileInputBack: any;
  @ViewChild('fileInputSide', {static: false})
  fileInputSide: any;
  @ViewChild('fileInputWarrantyCard', {static: false})
  fileInputWarrantyCard: any;
  geolocationPosition: any;
  accessaryDetails: any;
  phenomenanDetails: any;
  phenomenanDetailsRight: any;
  productCategory: any;
  stateList: any;
  cityList: any;
  areaList: any;
  AdditionalBrandName: any;
  displayRegistration = true;
  bookRepairModel: any = {};
  copyBookRepairModel: any = {};
  selectedValue: string;
  blockId: any;
  ticketNumber: any;
  guId: any;
  addressData: any;
  addNewAddress: boolean;
  displayOtherProduct = false;
  itemCategory: any;
  selectedAddress: any;
  validateEmail: string;
  addressType = [{ 'id': 0, 'value': 'HOME' }, { 'id': 1, 'value': 'OFFICE' }, { 'id': 2, 'value': 'BILLING' },
  { 'id': 3, 'value': 'OTHER' }];
  public BookRepair = this.fb.group({
  });
  public productFormGroup = this.fb.group({
    brandId: [''],
    brandName: [''],
    itemCategoryId: [''],
    modelNo: ['', Validators.required],
    claimedModelName: [''],
    serialNo: [''],
    custProductRefNo: [''],
    caliberNo: [''],
    backcoverNo: [''],
  });
  public otherFormGroup = this.fb.group({
    phenomenanIdLeft: ['', Validators.required],
    phenomenonIds: ['', Validators.required],
    warrantyRemarks: [''],
    accessoriesIds: [[]],
  });
  public personalFormGroup = this.fb.group({
    name: ['', Validators.required],
    mobileNo: ['', [Validators.required]],
    username: ['', [Validators.required]],
    emailId: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]],
    confirmPassword: [''],
  });
  public addressFormGroup = this.fb.group({
    addressLine1: [''],
    addressLine2: [''],
    stateId: [''],
    cityId: [''],
    areaId: [''],
    faxNo: [''],
    nearestLandMark: [''],
    phoneNo: [''],
    pinCode: [''],
    selectedAddress: [''],
    addressType: ['']
  });


  constructor(private remoteService: RemoteService, public fb: FormBuilder, private successMessage: SuccessMessageService) { }
  ngOnInit() {
    this.personalFormGroup.setValidators([Compare(this.personalFormGroup.controls['confirmPassword'], this.personalFormGroup.controls['password'])]);
    this.getFieldDetails();
    const userData = JSON.parse(sessionStorage.getItem(Constants.LOGGED_USER));
    if (userData) {
      this.addNewAddress = false;
      this.guId = userData.loginSuccess.userGUID;
      if (this.guId) {
        this.getAddressData();
      }
      if (userData.loginSuccess.access_token) {
        this.displayRegistration = false;
      } else {
        this.displayRegistration = true;
      }
    } else {
      this.addNewAddress = true;
    }
  }



  getAddressData() {
    this.remoteService.get('/essms-admin/endcustomer/get?entityGUID=' + this.guId).subscribe(
      data => {
        this.addressData = data.customerAddress;

        console.log(this.addressData);
      },
      error => {
      });
  }
  validateEmailAddress() {
    const validateEmailId = {
      emailId: this.personalFormGroup.value.emailId
    };
    this.remoteService.post('/essms-auth/public/validate/email/unique', validateEmailId).subscribe(
      data => {
        this.validateEmail = '';
      },
      error => {
        if (error.status === 406) {
          this.validateEmail = error.error[0].errorMsg;
          console.log(this.validateEmail);
          //  this.successMessage.updateSuccessMsg(error.error[0].errorMsg);
        }

      });
    // this.remoteService.post('essms-auth/user/validate/emailId', validateEmail).subscribe(
    //   data => {

    //   },
    //   error => {
    //     if(error.status===406){
    //       this.successMessage.updateSuccessMsg(error.error[0].errorMsg);
    //     }
    //   });
  }

  getErrorMessage(control: AbstractControl) {
    for (const propertyName in control.errors) {
      if (control.errors.hasOwnProperty(propertyName)) {
        return ErrorValidatorService.getValidatorErrorMessage(propertyName, control.errors[propertyName]);
      }
    }
    return null;
  }

  getLocation() {
    if (window.navigator && window.navigator.geolocation) {
      window.navigator.geolocation.getCurrentPosition(
        position => {
          this.geolocationPosition = position;
        },
        error => {
          switch (error.code) {
            case 1:
              console.log('Permission Denied');
              break;
            case 2:
              console.log('Position Unavailable');
              break;
            case 3:
              console.log('Timeout');
              break;
          }
        }
      );
    }
  }
  getFieldDetails() {
    this.remoteService.get('/essms-repair/option/accessories').subscribe(
      data => {
        this.accessaryDetails = data;
      },
      error => {
      });
    this.remoteService.get('/essms-repair/option/block').subscribe(
      data => {
        this.phenomenanDetails = data;
      },
      error => {
      });
    this.remoteService.get('/essms-inventory/option/company').subscribe(
      data => {
        this.productCategory = data;
      },
      error => {
      });
    this.remoteService.get('/essms-admin/option/state').subscribe(
      data => {
        this.stateList = data;
      },
      error => {
      });
    this.remoteService.get('/essms-inventory/option/itemcategory').subscribe(
      data => {
        this.itemCategory = data;
      },
      error => {
      });
  }

  getPhenomenan() {
    this.blockId = '';
    for (let i = 0; i < this.otherFormGroup.value.phenomenanIdLeft.length; i++) {
      if ((i + 1) === this.otherFormGroup.value.phenomenanIdLeft.length) {
        this.blockId += 'blockIds=' + this.otherFormGroup.value.phenomenanIdLeft[i];
      } else {
        this.blockId += 'blockIds=' + this.otherFormGroup.value.phenomenanIdLeft[i] + '&';
      }
    }
    console.log(this.blockId);
    this.remoteService.get('/essms-repair/option/phenomenon?' + this.blockId).subscribe(
      data => {
        this.phenomenanDetailsRight = data;
      },
      error => {
      });
  }
  setAddress(addressData) {
    console.log(addressData);
  }
  getProduct() {
    let postData;
    if (this.personalFormGroup) {
      postData = Object.assign(this.bookRepairModel, this.productFormGroup.value,
        this.otherFormGroup.value, this.addressFormGroup.value, this.personalFormGroup.value);
    } else {
      postData = Object.assign(this.bookRepairModel, this.productFormGroup.value,
        this.otherFormGroup.value, this.addressFormGroup.value, this.personalFormGroup.value);
    }
    this.bookRepairModel.endCustomerAddressModel = {};
    this.bookRepairModel.endCustomerAddressModel.addressModel = {};
    this.bookRepairModel.registerUserModel = {};
    // if (this.bookRepairModel.brandId == -1) {
    //   this.bookRepairModel.brandName = this.bookRepairModel.brandNameOther;
    // }
    const userData = JSON.parse(sessionStorage.getItem('currentUser'));

    if (userData) {
      if (userData.loginSuccess.access_token) {
        this.bookRepairModel.branchGUID = userData.loginSuccess.branchGUID;
        this.bookRepairModel.customerGUID = userData.loginSuccess.userGUID;
        this.bookRepairModel.registerUserModel = null;
        this.displayRegistration = false;
        if (this.selectedAddress) {
          this.bookRepairModel.endCustomerAddressModels = this.selectedAddress;
        } else {
          this.bookRepairModel.endCustomerAddressModels = this.addressFormGroup.value;
          this.bookRepairModel.endCustomerAddressModel.addressModel.addressLine1 = postData.addressLine1;
          this.bookRepairModel.endCustomerAddressModel.addressModel.addressLine2 = postData.addressLine2;
          this.bookRepairModel.endCustomerAddressModel.addressModel.areaId = postData.areaId;
          this.bookRepairModel.endCustomerAddressModel.addressModel.cityId = postData.cityId;
          this.bookRepairModel.endCustomerAddressModel.addressModel.stateId = postData.stateId;
          this.bookRepairModel.endCustomerAddressModel.addressModel.pinCode = postData.pinCode;
          this.bookRepairModel.endCustomerAddressModel.addressModel.faxNo = postData.faxNo;
          this.bookRepairModel.endCustomerAddressModel.addressModel.nearestLandMark = postData.nearestLandMark;
          this.bookRepairModel.endCustomerAddressModel.addressType = postData.addressType;
          this.bookRepairModel.endCustomerAddressModel.id = 0;
          this.bookRepairModel.endCustomerAddressModel.isDefault = true;
          this.bookRepairModel.endCustomerAddressModels.push(this.bookRepairModel.endCustomerAddressModel);
          this.bookRepairModel.endCustomerAddressModels = [];
        }
        // this.bookRepairModel.endCustomerAddressModels = this.addressData;
      }
    } else {
      this.bookRepairModel.customerGUID = null;
      this.bookRepairModel.branchGUID = null;
      // adding register user model data
      this.bookRepairModel.registerUserModel.roleIds = null;
      this.bookRepairModel.registerUserModel.emailId = postData.emailId;
      this.bookRepairModel.registerUserModel.mobileNo = postData.mobileNo;
      this.bookRepairModel.registerUserModel.name = postData.name;
      this.bookRepairModel.registerUserModel.password = postData.password;
      this.bookRepairModel.registerUserModel.confirmPassword = postData.confirmPassword;
      this.bookRepairModel.registerUserModel.phoneNo = postData.phoneNo;
      this.bookRepairModel.registerUserModel.username = postData.username;

      // adding endCustomerAddressModel data
      this.displayRegistration = true;
      this.bookRepairModel.endCustomerAddressModel.addressModel.id = 0;
      this.bookRepairModel.endCustomerAddressModel.addressModel.latitude = 0;
      this.bookRepairModel.endCustomerAddressModel.addressModel.longitude = 0;
      this.bookRepairModel.endCustomerAddressModel.addressModel.addressLine1 = postData.addressLine1;
      this.bookRepairModel.endCustomerAddressModel.addressModel.addressLine2 = postData.addressLine2;
      this.bookRepairModel.endCustomerAddressModel.addressModel.areaId = postData.areaId;
      this.bookRepairModel.endCustomerAddressModel.addressModel.cityId = postData.cityId;
      this.bookRepairModel.endCustomerAddressModel.addressModel.stateId = postData.stateId;
      this.bookRepairModel.endCustomerAddressModel.addressModel.pinCode = postData.pinCode;
      this.bookRepairModel.endCustomerAddressModel.addressModel.faxNo = postData.faxNo;
      this.bookRepairModel.endCustomerAddressModel.addressModel.nearestLandMark = postData.nearestLandMark;
      this.bookRepairModel.endCustomerAddressModel.addressType = postData.addressType;
      this.bookRepairModel.endCustomerAddressModel.id = 0;
      this.bookRepairModel.endCustomerAddressModel.isDefault = true;
      this.bookRepairModel.endCustomerAddressModels = [];
      this.bookRepairModel.endCustomerAddressModels.push(this.bookRepairModel.endCustomerAddressModel);
    }

    this.copyBookRepairModel = Object.assign({}, this.bookRepairModel);

    this.bookRepairModel.productGUID = null;
    delete this.copyBookRepairModel.selectedAddress;
    delete this.copyBookRepairModel.addressLine1;
    delete this.copyBookRepairModel.addressLine2;
    delete this.copyBookRepairModel.areaId;
    delete this.copyBookRepairModel.cityId;
    delete this.copyBookRepairModel.stateId;
    delete this.copyBookRepairModel.pinCode;
    delete this.copyBookRepairModel.faxNo;
    delete this.copyBookRepairModel.nearestLandMark;

    delete this.copyBookRepairModel.emailId;
    delete this.copyBookRepairModel.mobileNo;
    delete this.copyBookRepairModel.name;
    delete this.copyBookRepairModel.password;
    delete this.copyBookRepairModel.phoneNo;
    delete this.copyBookRepairModel.username;
    delete this.copyBookRepairModel.confirmPassword;
    delete this.copyBookRepairModel.phenomenanIdLeft;
    delete this.copyBookRepairModel.endCustomerAddressModel;
    this.remoteService.post('/essms-repair/public/book/product', this.copyBookRepairModel).subscribe(
      data => {
        this.ticketNumber = data.generatedRecordNo;
        this.successMessage.updateSuccessMsg('Request updated successfully');
      },
      error => {
        if (error.status === 406) {
          this.successMessage.updateSuccessMsg(error.error[0].errorMsg);
        } else {
          this.successMessage.updateSuccessMsg(error.errorMsg);
        }
        console.log(JSON.stringify(error));

      });
  }

  uploadFrontImage() {
    const fileBrowser = this.fileInputFront.nativeElement;
    if (fileBrowser.files && fileBrowser.files[0]) {
      const formData = new FormData();
      formData.append('productFrontPath', fileBrowser.files[0]);
      const body = formData;
      this.remoteService.post('/essms-repair/public/upload/productfront', formData).subscribe(
        data => {
          console.log(data);
          this.bookRepairModel.productFrontPath = data.path;
        },
        error => {
          console.log(JSON.stringify(error));

        });
    }
  }

  uploadBackImage() {
    const fileBrowser = this.fileInputBack.nativeElement;
    if (fileBrowser.files && fileBrowser.files[0]) {
      const formData = new FormData();
      formData.append('productBackPath', fileBrowser.files[0]);
      const body = formData;
      this.remoteService.post('/essms-repair/public/upload/productback', formData).subscribe(
        data => {
          console.log(data);
          this.bookRepairModel.productBackPath = data.path;
        },
        error => {
          console.log(JSON.stringify(error));

        });
    }
  }

  uploadSideImage() {
    const fileBrowser = this.fileInputSide.nativeElement;
    if (fileBrowser.files && fileBrowser.files[0]) {
      const formData = new FormData();
      formData.append('productSidePath', fileBrowser.files[0]);
      const body = formData;
      this.remoteService.post('/essms-repair/public/upload/productside', formData).subscribe(
        data => {
          console.log(data);
          this.bookRepairModel.productSidePath = data.path;
        },
        error => {
          console.log(JSON.stringify(error));

        });
    }
  }

  uploadwarrentyImage() {
    const fileBrowser = this.fileInputWarrantyCard.nativeElement;
    if (fileBrowser.files && fileBrowser.files[0]) {
      const formData = new FormData();
      formData.append('warrantyPath', fileBrowser.files[0]);
      const body = formData;
      this.remoteService.post('/essms-repair/public/upload/warrantycard', formData).subscribe(
        data => {
          console.log(data);
          this.bookRepairModel.warrantyPath = data.path;
        },
        error => {
          console.log(JSON.stringify(error));

        });
    }
  }

  checkOther() {
    console.log(this.productFormGroup.value.brandId);
    if (this.productFormGroup.value.brandId === "-1") {
      this.displayOtherProduct = true;
      // this.bookRepairModel.brandId = 0;
    } else {
      this.displayOtherProduct = false;
      // this.bookRepairModel.brandId = -1;
    }
  }

  productWarrantyStatus(value) {
    if (value === 'yes') {
      this.bookRepairModel.isWarranty = true;
    } else {
      this.bookRepairModel.isWarranty = false;
    }
  }
  productReRepairStatus(value) {
    if (value === 'yes') {
      this.bookRepairModel.isReRepair = true;
    } else {
      this.bookRepairModel.isReRepair = false;
    }
  }

  productCompanyError() {
    return this.BookRepair.hasError('required', ['ProductCompany']) ? 'Product Company is Required' : '';
  }

  getCityBystateId() {
    this.remoteService.get('/essms-admin/option/citybystate?stateId=' + this.addressFormGroup.value.stateId).subscribe(
      data => {
        this.cityList = data;
      },
      error => {
      });
  }

  getAreaByCityId() {
    this.remoteService.get('/essms-admin/option/areabycity?cityId=' + this.addressFormGroup.value.cityId).subscribe(
      data => {
        this.areaList = data;
      },
      error => {
      });
  }



}
