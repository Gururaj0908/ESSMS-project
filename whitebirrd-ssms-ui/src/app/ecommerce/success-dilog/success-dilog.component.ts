import { Component, OnInit, Inject} from '@angular/core';
import {MAT_SNACK_BAR_DATA} from '@angular/material/snack-bar';
import { SuccessMessageService } from '../../ecommerce/success-message.service';

@Component({
  selector: 'app-success-dilog',
  templateUrl: './success-dilog.component.html',
  styleUrls: ['./success-dilog.component.scss']
})
export class SuccessDilogComponent implements OnInit {

  constructor( @Inject(MAT_SNACK_BAR_DATA) public data: any) { }

  ngOnInit() {
    console.log(this.data);
    // this.SuccessMessageService.successMsg.subscribe(message => {
    // console.log(message);
    // });
  }


}
