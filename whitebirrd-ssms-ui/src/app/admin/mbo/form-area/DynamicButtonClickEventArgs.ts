import { TableButtons } from '../../table-view/TableButtons';
import { HttpResponse } from '@angular/common/http';
export class DynamicButtonClickEventArgs {
  constructor(public Data: HttpResponse<any>, public Button: TableButtons) { }
}
