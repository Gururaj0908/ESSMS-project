import { FormEditorModel } from './FormEditor';
import { DisplayMode } from './DisplayMode';
import { SubForm } from './SubForm';
import { TableButtons } from '../table-view/TableButtons';
export class FormList {
  // list of main forms
  public forms: FormEditorModel[];
  // list of connected forms or forms arrays
  public subForms: SubForm[];
  // if true then the form will be rendered in a horizontal way
  public displayStyle: DisplayMode;
  // the url where this form will be submitted
  public url: string;
  // the method to be used for request
  public method: string;
  // whether to reset the form after submitting
  public isReset: boolean;
  // whether it is a stepper
  public stepperHeader: string;
  public formButtons: TableButtons[];
  constructor(f?: FormList) {
    if (f) {
      if (f.forms) {
        if (this.forms == null) {
          this.forms = [];
        }
        f.forms.forEach(ff => {
          this.forms.push(new FormEditorModel(ff));
        });
      }
      if (f.subForms) {
        if (this.subForms == null) {
          this.subForms = [];
        }
        f.subForms.forEach(sf => {
          this.subForms.push(new SubForm(sf));
        });
      }
      this.url = f.url;
      if (f.displayStyle != null) {
        if (typeof f.displayStyle === 'string') {
          this.displayStyle = (<any>DisplayMode)[f.displayStyle];
        } else {
          this.displayStyle = f.displayStyle;
        }
      } else {
        this.displayStyle = DisplayMode.StrictlyVertical;
      }
      this.method = f.method;
      this.isReset = f.isReset;
      this.stepperHeader = f.stepperHeader;
      if (f.formButtons) {
        this.formButtons = [];
        f.formButtons.forEach(fb => {
          this.formButtons.push(new TableButtons(fb));
        });
      }
    }
  }
}
