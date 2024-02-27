import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { PopupDataArgs } from '../popup-view/PopupDataArgs';
import { RemoteService } from '../../services/remote.service';
import { GlobalService } from '../../services/global.service';

@Component({
  selector: 'app-price-selector',
  templateUrl: './price-selector.component.html',
  styleUrls: ['./price-selector.component.scss']
})
export class PriceSelectorComponent implements OnInit {

  public Data = [];
  public Loading = false;
  constructor(
    public dialogRef: MatDialogRef<PriceSelectorComponent>,
    @Inject(MAT_DIALOG_DATA) public data: PriceSelectorDataArgs,
    private remote: RemoteService, public gst: GlobalService
  ) {
    if (data != null) {
      remote.get(data.Url).subscribe(
        resp => {
          if (Array.isArray(resp)) {
            resp.forEach(r => this.Data.push(r));
          }
        }
      );
    }
  }

  ngOnInit() {
  }

}

export class PriceSelectorDataArgs {
  constructor(public Url: string, public multipleSelection = false) {

  }
}
