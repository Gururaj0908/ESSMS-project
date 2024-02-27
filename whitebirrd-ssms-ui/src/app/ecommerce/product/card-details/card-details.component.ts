import { Component, OnInit, Inject, ViewChild, ViewEncapsulation } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { HttpClientModule, HTTP_INTERCEPTORS, HttpClient } from '@angular/common/http';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { RemoteService } from '../../../services/remote.service';
import { ErrorValidatorService } from './../../error-validator/errorValidatorService';
import { MediaObserver } from '@angular/flex-layout';
import { Observable } from 'rxjs';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ConfirmationDilogComponent } from '../../confirmation-dilog/confirmation-dilog.component';
import { Constants } from '../../../constants';
import { SuccessMessageService } from '../../success-message.service';
import { MomentDateAdapter } from '@angular/material-moment-adapter';
import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material/core';
import { MatDatepicker } from '@angular/material/datepicker';

// Depending on whether rollup is used, moment needs to be imported differently.
// Since Moment.js doesn't have a default export, we normally need to import using the `* as`
// syntax. However, rollup creates a synthetic default module and we thus need to import it using
// the `default as` syntax.
import * as _moment from 'moment';
// eslint-disable-next-line no-duplicate-imports
import { Moment } from 'moment';

const moment = _moment;

// See the Moment.js docs for the meaning of these formats:
// https://momentjs.com/docs/#/displaying/format/
export const MY_FORMATS = {
  parse: {
    dateInput: 'MM/YYYY',
  },
  display: {
    dateInput: 'MM/YYYY',
    monthYearLabel: 'MMM YYYY',
    dateA11yLabel: 'LL',
    monthYearA11yLabel: 'MMMM YYYY',
  },
};

@Component({
  selector: 'app-card-details',
  templateUrl: './card-details.component.html',
  styleUrls: ['./card-details.component.scss'],
  encapsulation: ViewEncapsulation.None,
  providers: [
    // `MomentDateAdapter` can be automatically provided by importing `MomentDateModule` in your
    // application's root module. We provide it at the component level here, due to limitations of
    // our example generation script.
    { provide: DateAdapter, useClass: MomentDateAdapter, deps: [MAT_DATE_LOCALE] },

    { provide: MAT_DATE_FORMATS, useValue: MY_FORMATS },
  ],
})
export class CardDetailsComponent implements OnInit {
  model: any = {};
  cityList: any = [];
  areaList: any = [];
  stateList: any = [];
  cardData: any = [];
  showCardForm: boolean | false;
  endCustomer: any = {};
  card: any;
  action: string;
  private guId;
  private formId: number | 0;
  // productList:any;
  public cols: Observable<number>;
  addressType = [{ 'id': 1, 'value': 'HOME' }, { 'id': 2, 'value': 'OFFICE' },
  { 'id': 3, 'value': 'BILLING' }, { 'id': 3, 'value': 'OTHER' }];
  public ManageCardForm = this.fb.group({
    cardNo: ['', [ErrorValidatorService.requiredValidator]],
    // expriryDate: ["", [ErrorValidatorService.requiredValidator]],
    nameOfCard: ['', [ErrorValidatorService.requiredValidator]],
    nameOnCard: ['', [ErrorValidatorService.requiredValidator]],
  });
  expriryDate = new FormControl();// moment());

  chosenYearHandler(normalizedYear: Moment) {
    const ctrlValue = this.expriryDate.value;
    ctrlValue.year(normalizedYear.year());
    this.expriryDate.setValue(ctrlValue);
  }

  chosenMonthHandler(normlizedMonth: Moment, datepicker: MatDatepicker<Moment>) {
    const ctrlValue = this.expriryDate.value;
    ctrlValue.month(normlizedMonth.month());
    this.expriryDate.setValue(ctrlValue);
    datepicker.close();
  }

  // chosenYearHandler(normalizedYear: Moment) {
  //   const ctrlValue = this.ManageCardForm.value.expriryDate;
  //   ctrlValue.year(normalizedYear.year());
  //   this.ManageCardForm.setValue({expriryDate:ctrlValue});
  // }

  // chosenMonthHandler(normlizedMonth: Moment, datepicker: MatDatepicker<Moment>) {
  //   const ctrlValue = this.ManageCardForm.value.expriryDate;
  //   ctrlValue.month(normlizedMonth.month());
  //   this.ManageCardForm.setValue({expriryDate:ctrlValue});
  //   datepicker.close();
  // }
  constructor(private remoteService: RemoteService, public dialog: MatDialog, public fb: FormBuilder,
    private observableMedia: MediaObserver, private successMessage: SuccessMessageService) { }
  ngOnInit() {
    this.getCardData();
  }
  addnewCard() {
    this.showCardForm = true;
    this.ManageCardForm = this.fb.group({
      cardNo: ['', [ErrorValidatorService.requiredValidator]],
      // expriryDate: ["", [ErrorValidatorService.requiredValidator]],
      nameOfCard: ['', [ErrorValidatorService.requiredValidator]],
      nameOnCard: ['', [ErrorValidatorService.requiredValidator]],
    });
  }
  getCardData() {
    const userData = JSON.parse(sessionStorage.getItem(Constants.LOGGED_USER));
    this.guId = userData.loginSuccess.userGUID;
    // this.cardData.customercardModels =[];
    // this.cardData.userGUID = this.guId;
    this.endCustomer = { 'endCustomer.guid': this.guId };
    const jsonQueryString = JSON.stringify(this.endCustomer);
    this.remoteService.get('/essms-admin/endcustomer/card/list?jsonQueryString=' + encodeURI(jsonQueryString)).subscribe(
      data => {
        this.cardData = data.entityList;
        console.log(this.cardData);
      },
      error => {
      });
  }

  openDialog(cardvalue): void {
    const dialogRef = this.dialog.open(ConfirmationDilogComponent, {
      width: '250px',
      data: {}
    });
    const sub = dialogRef.componentInstance.onAdd.subscribe(() => {
      // do something
      this.deleteCard(cardvalue);
    });
    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');

      /// endcustomer/address/delete
      // this.animal = result;
    });
  }
  closecardForm() {
    this.showCardForm = !this.showCardForm;
    this.action = '';
    this.getCardData();
  }
  deleteCard(cardvalue) {
    this.formId = cardvalue.id;
    this.remoteService.deleteWithParam('/essms-admin/endcustomer/card/delete?hardDelete=true&id=' + this.formId).subscribe(
      data => {
        this.successMessage.updateSuccessMsg('Card details removed successfully');
        this.getCardData();
      },
      error => {
        console.log(JSON.stringify(error));
      });

  }

  editcard(cardvalue) {
    console.log(cardvalue);
    this.showCardForm = true;
    this.action = 'update';
    this.formId = cardvalue.id;

    this.ManageCardForm.setValue({
      cardNo: cardvalue.cardNo,
      // expriryDate: cardvalue.expriryDate,
      nameOfCard: cardvalue.nameOfCard,
      nameOnCard: cardvalue.cardNo,
    });
    const expdate = cardvalue.expriryDate.moment('MM Do YY');
    console.log(expdate);
    this.expriryDate.setValue(cardvalue.expriryDate);
  }

  updateCard(post) {
    const FormData = post;
    this.card = {};
    this.card = Object.assign({}, FormData);
    // this.card.cardModel.cardNo = FormData.cardNo;
    // this.card.cardModel.expriryDate =FormData.expriryDate;
    // this.card.cardModel.nameOfCard =FormData.expriryDate;
    // this.card.cardModel.nameOnCard = FormData.addressType;
    this.card.userGUID = this.guId;
    this.card.id = this.formId;
    this.card.expriryDate = this.expriryDate.value;

    if (this.action === 'update') {
      this.remoteService.put('/essms-admin/endcustomer/card/update', this.card).subscribe(
        data => {
          this.successMessage.updateSuccessMsg('Card details updated successfully');
        },
        error => {
          console.log(JSON.stringify(error));
        });
    } else {
      this.remoteService.post('/essms-admin/endcustomer/card/create', this.card).subscribe(
        data => {
          this.successMessage.updateSuccessMsg('Card details added successfully');
        },
        error => {
          console.log(JSON.stringify(error));
        });
    }

  }



}
