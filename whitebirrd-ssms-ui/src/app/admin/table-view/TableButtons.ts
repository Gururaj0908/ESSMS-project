import { FormList } from '../dynamic-form/FormList';
import { RouteType } from '../model/RouteType';
export class TableButtons {
  public label: string;
  public url: string;
  public method: string;
  public navigate: boolean;
  public inlineBody: any;
  public editor: FormList;
  public noValidate: boolean;
  public leftIcon: string;
  public rightIcon: string;
  public routeType: RouteType;
  public buttonOps: 'File' | 'Print';
  public defaultDisabled: boolean;

  constructor(d?: TableButtons) {
    if (d != null) {
      this.label = d.label;
      this.url = d.url;
      this.defaultDisabled = d.defaultDisabled;
      this.buttonOps = d.buttonOps;
      this.method = d.method;
      this.navigate = d.navigate;
      this.inlineBody = d.inlineBody;
      if (d.editor != null) {
        this.editor = new FormList(d.editor);
      }
      this.noValidate = d.noValidate;
      this.leftIcon = d.leftIcon;
      this.rightIcon = d.rightIcon;
      if (d.routeType != null) {
        if (typeof d.routeType === 'string') {
          this.routeType = (<any>RouteType)[d.routeType];
        } else {
          this.routeType = d.routeType;
        }
      } else {
        this.routeType = RouteType.Form;
      }
    }
  }
}
