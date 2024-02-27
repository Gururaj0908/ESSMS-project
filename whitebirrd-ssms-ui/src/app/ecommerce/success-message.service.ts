import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import {MatSnackBar} from '@angular/material/snack-bar';
import { SuccessDilogComponent } from './success-dilog/success-dilog.component';

@Injectable({
  providedIn: 'root'
})
export class SuccessMessageService {
  private messageSource = new BehaviorSubject('0');
  successMsg = this.messageSource.asObservable();

  constructor(private snackBar:MatSnackBar) { }

  updateSuccessMsg(messageString: string) {
    this.messageSource.next(messageString);
    this.snackBar.openFromComponent(SuccessDilogComponent, {
      duration: 1500,
      data: messageString
    });
  }
}
