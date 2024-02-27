import { FormList } from '../dynamic-form/FormList';
import { DetailModel } from './DetailModel';
import { PopupType } from './PopupType';
import { FormGroup } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { PopupViewComponent } from './popup-view.component';
import { RouteType } from '../model/RouteType';
export class PopupDataArgs {
  constructor(
    public Url: string,
    public Data: DetailModel[],
    public Title: string,
    public Type: PopupType,
    public Form?: FormList,
    public callBack?: (formData, parameter, formgroup: FormGroup, dialogRef: MatDialogRef<PopupViewComponent>) => void,
    public cb_parm?,
    public prefilled_form_data?,
    public route?: RouteType
  ) { }
}
