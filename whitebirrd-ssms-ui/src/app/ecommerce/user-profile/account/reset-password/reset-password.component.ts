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
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['../account.component.scss']
})
export class ResetPasswordComponent implements OnInit {

  constructor(private remoteService: RemoteService, public dialog: MatDialog,
    public fb: FormBuilder, private observableMedia: MediaObserver) { }
  public resetData: any;
  public guId: any;
  public ResetPasswordForm = this.fb.group({
    // addressName: ["", [ErrorValidatorService.requiredEmailValidator]],
    oldPassword: ['', [ErrorValidatorService.requiredValidator]],
    newPassword: ['', [ErrorValidatorService.requiredValidator]],
    confirmPassword: ['', [ErrorValidatorService.requiredValidator]],
  });

  ngOnInit() {
    const userData = JSON.parse(sessionStorage.getItem(Constants.LOGGED_USER));
    this.guId = userData.loginSuccess.userGUID;
  }



  resetPassword(postData) {
    this.resetData = {};
    this.resetData.userGUID =   this.guId;
    this.resetData.newPassword = postData.newPassword;
    this.resetData.oldPassword = postData.oldPassword;
    this.remoteService.post('/essms-auth/password/change', this.resetData).subscribe(
      data => {
        console.log(data);
      },
      error => {
        console.log(JSON.stringify(error));
      });
  }

}
