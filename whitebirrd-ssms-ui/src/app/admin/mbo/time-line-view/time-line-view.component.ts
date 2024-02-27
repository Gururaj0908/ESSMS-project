import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
   selector: 'app-time-line-view',
   templateUrl: './time-line-view.component.html',
   styleUrls: ['./time-line-view.component.scss']
})
export class TimeLineViewComponent implements OnInit {
   operationType = OperationType;
   constructor(
      public dialogRef: MatDialogRef<TimeLineViewComponent>,
      @Inject(MAT_DIALOG_DATA) public data: TimeLineModel[]
   ) { }

   ngOnInit() {
   }

}

export class TimeLineModel {
   event: Date;
   title: string;
   changes: Updates[];
   type: OperationType;
   user: string;
   comment: string;

   constructor(t?: TimeLineModel) {
      if (t) {
         this.event = t.event;
         this.title = t.title;
         this.changes = t.changes;
         this.comment = t.comment;
         this.user = t.user;
         if (t.type != null) {
            if (typeof t.type === 'string') {
               this.type = (<any>OperationType)[t.type];
            } else {
               this.type = t.type;
            }
         } else {
            this.type = OperationType.Other;
         }
      }
   }
}

export class Updates {
   // Key (Diplay name)
   k: string;
   // Old value
   o: string;
   // new value
   n: string;
}

export enum OperationType {
   Created = 0,
   Updated = 1,
   Trashed = 2,
   Restored = 3,
   Deleted = 4,
   Activated = 5,
   Deactivated = 6,
   Other = 127
}
