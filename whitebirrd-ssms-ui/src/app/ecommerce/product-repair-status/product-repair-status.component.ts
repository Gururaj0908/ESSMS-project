import { Component, OnInit } from '@angular/core';
import { RemoteService } from '../../services/remote.service';
import { MediaObserver, MediaChange } from '@angular/flex-layout';
import { Observable } from 'rxjs';
import { map, filter } from 'rxjs/operators';
import { Constants } from '../../constants';
import { LanguageService } from '../../services/language.service';
@Component({
  selector: 'app-product-repair-status',
  templateUrl: './product-repair-status.component.html',
  styleUrls: ['./product-repair-status.component.scss']
})
export class ProductRepairStatusComponent implements OnInit {
  panelOpenState = false;
  repairStatus: any;
  repairDetails: any;
  columnNum = 4;
  showCardForm = false;

  constructor(public l: LanguageService, private remoteService: RemoteService, media: MediaObserver) {
    media.asObservable().pipe(
      filter((changes: MediaChange[]) => changes.length > 0),
      map((changes: MediaChange[]) => changes[0])
      ).subscribe((change: MediaChange) => {
      if (change.mqAlias === 'xs') {
        this.columnNum = 1;
      } else if (change.mqAlias === 'sm') {
        this.columnNum = 2;
      } else {
        this.columnNum = 4;
      }
    });
  }

  ngOnInit() {
    const userData = JSON.parse(sessionStorage.getItem(Constants.LOGGED_USER));
    this.getCardData();
  }
  getCardData() {

    this.remoteService.get('/essms-repair/repair/pending/assign?includeDeActive=true').subscribe(
      data => {
        this.repairStatus = data.entityList;
      },
      error => {
      });
  }
  getCardDetails(serviceRecordNo) {
    console.log(serviceRecordNo);
    this.remoteService.get('/essms-repair/repair/detail/' + serviceRecordNo).subscribe(
      data => {
        this.showCardForm = true;
        this.repairDetails = data;
      },
      error => {
      });
  }
  closecardForm() {
    this.showCardForm = !this.showCardForm;
  }
}
