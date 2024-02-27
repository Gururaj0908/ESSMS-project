import { DetailModel } from '../popup-view/DetailModel';
import { KeyValue } from '@angular/common';
import { KeyValuePair } from '../dashboard/DashboardItem';

export class FormEditorOption {
  public label: string;
  public value: any;
  public selected: boolean;
  public disabled: boolean;
  public detailModel: DetailModel;
  public fieldMap: KeyValue<string, any>[];
  constructor(f?: FormEditorOption) {
    if (f) {
      this.label = f.label;
      this.value = f.value;
      if (f.selected != null) {
        this.selected = f.selected;
      }
      if (f.disabled != null) {
        this.disabled = f.disabled;
      }
      if (f.detailModel) {
        this.detailModel = new DetailModel(f.detailModel);
      }
      if (f.fieldMap) {
        this.fieldMap = [];
        const keys = Object.keys(f.fieldMap);
        keys.forEach(k => {
          this.fieldMap.push(new KeyValuePair(k, f.fieldMap[k]));
        });
      }
    }
  }
}
