import { Component, OnInit, Inject, ViewChild } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { HttpClientModule, HTTP_INTERCEPTORS, HttpClient } from '@angular/common/http';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { RemoteService } from '../../../../services/remote.service';
import { ErrorValidatorService } from '../../../error-validator/errorValidatorService';
import { MediaObserver } from '@angular/flex-layout';
import { Observable } from 'rxjs';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ConfirmationDilogComponent } from '../../../confirmation-dilog/confirmation-dilog.component';
import { Constants } from '../../../../constants';

@Component({
  selector: 'app-notification-preference',
  templateUrl: './notification-preference.component.html',
  styleUrls: ['../account.component.scss']
})
export class NotificationPreferenceComponent implements OnInit {

  constructor(private remoteService: RemoteService, public dialog: MatDialog, public fb: FormBuilder,
    private observableMedia: MediaObserver) { }
public guId: string;
private endCustomer: any;
public notificationdata: any;
  public ManageNotificationForm = this.fb.group({
    promotionalSMSPreference: [''],
    promotionalEmailPreference: [''],
    isMarketingNewsletterEnabled: [''],
    isOfferNewsletterEnabled: [''],
    isReviewEnabled: [''],
    isSurveyEnabled: [''],
  });
  ngOnInit() {
    this.getCardData();
  }


  getCardData() {
    const userData = JSON.parse(sessionStorage.getItem(Constants.LOGGED_USER));
    this.guId = userData.loginSuccess.userGUID;
    // this.cardData.customercardModels =[];
    // this.cardData.userGUID = this.guId;
    this.endCustomer = {'endCustomer.guid': this.guId};
    const jsonQueryString = JSON.stringify(this.endCustomer);
    this.remoteService.get('/essms-admin/endcustomer/preference/list?jsonQueryString=' + encodeURI(jsonQueryString)).subscribe(
      data => {
      const notificationValue = data.entityList[0];
      this.notificationdata = data;
        this.ManageNotificationForm.setValue({
          promotionalSMSPreference: notificationValue.promotionalSMSPreference,
    promotionalEmailPreference: notificationValue.promotionalEmailPreference,
    isMarketingNewsletterEnabled: notificationValue.isMarketingNewsletterEnabled,
    isOfferNewsletterEnabled: notificationValue.isOfferNewsletterEnabled,
    isReviewEnabled: notificationValue.isReviewEnabled,
    isSurveyEnabled: notificationValue.isSurveyEnabled,
        });


      },
      error => {
      });
  }
  updateNotificationSettings(postData) {
    const settingsData = postData;
    settingsData.userGUID = this.guId;
    settingsData.id = 1;
   console.log(settingsData);
   if (this.notificationdata) {
    this.remoteService.put('/essms-admin/endcustomer/preference/update', settingsData).subscribe(
      data => {
      },
      error => {
      });
   } else {
    this.remoteService.post('/essms-admin/endcustomer/preference/create', settingsData).subscribe(
      data => {
      },
      error => {
      });
   }


  }
}
