import { Component, Inject, OnInit, EventEmitter } from '@angular/core';
import {MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
@Component({
  selector: 'app-confirmation-dilog',
  templateUrl: './confirmation-dilog.component.html',
  styleUrls: ['./confirmation-dilog.component.scss']
})
export class ConfirmationDilogComponent implements OnInit {

  constructor( public dialogRef: MatDialogRef<ConfirmationDilogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) {
      
     }
     onAdd = new EventEmitter();

     onButtonClick() {
       this.onAdd.emit();
     }
  ngOnInit() {
  }
  onNoClick(): void {
    this.dialogRef.close();
  }
}
