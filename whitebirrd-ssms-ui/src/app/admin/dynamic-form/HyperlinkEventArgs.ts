import { AbstractControl } from '@angular/forms';
import { DetailModel } from '../popup-view/DetailModel';
import { RouteType } from '../model/RouteType';
export class HyperlinkEventArgs {
  public Url: string;
  public Route: RouteType;
  public Detail: DetailModel;
  public ReturnValue: boolean;
  public Control: AbstractControl;
}
