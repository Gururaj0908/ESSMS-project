import { Component, OnInit, Inject, ViewChild } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { HttpClientModule, HTTP_INTERCEPTORS, HttpClient } from '@angular/common/http';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { RemoteService } from '../../../services/remote.service';
import { ErrorValidatorService } from '../../error-validator/errorValidatorService';
import { Constants } from '../../../constants';
import { SuccessMessageService } from '../../success-message.service';
@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.scss']
})
export class AccountComponent implements OnInit {

  showBusinessProfile=false;
  public colorConstant: any;
  userData:any;
  toppingList = ['Extra cheese', 'Mushroom', 'Onion', 'Pepperoni', 'Sausage', 'Tomato'];
  public RegisterForm = this.fb.group({
              emailId: [{value: '', disabled: true}, [ErrorValidatorService.requiredValidator, ErrorValidatorService.emailValidator]],
              name: [{value: '', disabled: true}, [ErrorValidatorService.requiredValidator]],
              birthDate: ["", [ErrorValidatorService.requiredValidator]],
              genderType: ["", [ErrorValidatorService.requiredValidator]],
              mobileNo: [{value: '', disabled: true}, [ErrorValidatorService.requiredValidator,ErrorValidatorService.phoneNoValidator]],
              materialStatus: ["", [ErrorValidatorService.requiredValidator]],
              regNo: [{value: '', disabled: true}, [ErrorValidatorService.requiredValidator]],
              // pinCode: ["", [ErrorValidatorService.requiredValidator,ErrorValidatorService.zipCodeValidator]],
              // contactPerson:["", [ErrorValidatorService.requiredValidator]],
              // landlineNo:["", [ErrorValidatorService.requiredValidator]],
              // alternateNo:["", [ErrorValidatorService.requiredValidator]],
              // websiteUrl:["", [ErrorValidatorService.requiredValidator]],
              // billngNo:["", [ErrorValidatorService.requiredValidator]],
              // designation:["", [ErrorValidatorService.requiredValidator]],
              // department:["", [ErrorValidatorService.requiredValidator]],
          });

  constructor(private remoteService: RemoteService, public fb: FormBuilder, private successMessage: SuccessMessageService) { }
  ngOnInit() {
    const userData = JSON.parse(sessionStorage.getItem(Constants.LOGGED_USER));
    this.getUserData();
    // if(userData){
    //   if(userData.loginSuccess.access_token){
     
    //   }else{
      
    //   }
    // }
  }


  updateProfile(post){
  
    this.userData.emailId=post.emailId,
   this.userData.registerUserModel.emailId=post.emailId,
   this.userData.registerUserModel.name=post.name,
   this.userData.customerAddress= null;
   
   this.userData.endCustomerModel.genderType=post.genderType,
   this.userData.endCustomerModel.maritalStatus=post.maritalStatus,
   this.userData.endCustomerModel.regNo=post.regNo,
   console.log(this.userData);
    this.remoteService.put('/essms-admin/endcustomer/update',  this.userData).subscribe(
      data => {
        this.successMessage.updateSuccessMsg("Profile Information Updated Successfully");
      },
      error => {
      });
  }
  openBusinessProfile(){
    this.showBusinessProfile=true;
  }
  
  getUserData(){
    const userData = JSON.parse(sessionStorage.getItem(Constants.LOGGED_USER));
    const guId = userData.loginSuccess.userGUID;
 
    this.remoteService.get('/essms-admin/endcustomer/get?entityGUID=' + guId).subscribe(
      data => {
        this.userData = data;
        console.log(this.userData);
        this.RegisterForm.setValue({
          emailId: this.userData.registerUserModel.emailId,
          name:   this.userData.registerUserModel.name,
          birthDate:   this.userData.endCustomerModel.birthDate,
          mobileNo:   this.userData.registerUserModel.mobileNo,
          genderType:    this.userData.endCustomerModel.genderType,
          materialStatus: this.userData.endCustomerModel.maritalStatus,
          regNo: this.userData.endCustomerModel.regNo,
          // pinCode: userData.registerUserModel.cityId,
        });
      },
      error => {
      });
  }
}
