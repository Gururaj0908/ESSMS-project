import { FormGroup } from '@angular/forms';
import { RouteType } from '../model/RouteType';
export class OperationButtonEventArgs {
  public Url: string;
  public Data: any;
  public HttpMethod: string;
  public Navigate: boolean;
  public Form: FormGroup;
  public IsSingleSelection: boolean;
  public RouteType: RouteType;
  public ButtonOperation: 'File' | 'Print';
}
