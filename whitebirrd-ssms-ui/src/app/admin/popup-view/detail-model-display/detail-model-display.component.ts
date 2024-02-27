import { Component, OnInit, Input } from '@angular/core';
import { DetailModel } from '../DetailModel';
import { ContentType } from '../ContentType';
import { GlobalService } from '../../../services/global.service';

@Component({
  selector: 'app-detail-model-display',
  templateUrl: './detail-model-display.component.html',
  styleUrls: ['./detail-model-display.component.scss']
})
export class DetailModelDisplayComponent implements OnInit {

  @Input() Model: DetailModel;
  contentType = ContentType;
  constructor(public gst: GlobalService) { }


  ngOnInit() {
  }

  public GetContentType(ct): ContentType {
    if (typeof ct === 'string') {
        return (<any>ContentType)[ct];
    } else {
        return ct;
    }
}
}
