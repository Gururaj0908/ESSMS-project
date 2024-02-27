import { ContentType } from './ContentType';
import { Formatter } from './Formatter';
export class DetailModel {
  label: string;
  value: any;
  formatter: Formatter;
  applyFormatter: 'Label' | 'Value' | 'Both';
  contentType: ContentType;
  constructor(d?: DetailModel) {
    if (d) {
      this.label = d.label;
      this.value = d.value;
      this.formatter = new Formatter(d.formatter);
      this.contentType = (<any>ContentType)[d.contentType];
      this.applyFormatter = d.applyFormatter;
    }
  }
}
