import { ExpressionNode } from './ExpressionNode';
import { ShowTime } from './ShowTime';
import { FormEditorOption } from './FormEditorOption';
import { FormEditorType } from './FormEditorType';
import { RemoteService } from '../../services/remote.service';
import { HttpParams } from '@angular/common/http';
import { DetailModel } from '../popup-view/DetailModel';
import { FormGroup } from '@angular/forms';
import { VisibilityOperators } from '../mbo/enum/VisibilityOperators';

export class FormEditorModel {

  //#region fields
  // the display label for field
  public label: string;

  // the name of the field
  public key: string;

  // the hint used to display for field
  public placeHolder: string;

  // it focuses automatically
  // if true on remotetext then makes it editable else readonly
  // needs to be true for checkbox/radio button for them to select the row in a table
  public autoFocus: boolean;

  // is it required field
  public required: boolean;

  // the initial value to render at form load
  public initialValue: any;

  // the key to the element on whose value this control will get its options
  public parentKey: any;

  // the url from where this control will fetch its options
  // for hyperlink optionurl will act as form editor.
  public optionsURL: string;

  // the options which will be displayed to select values from (used in select-list, checkbox-list, autocomplete list)
  public options: FormEditorOption[];

  // the minimum length of characters allowed in the fexport ield
  public minLength: number;

  // the maximum number of characters allowed in field
  public maxLength: number;

  // the minimum value allowed in the field
  public min: any;

  // the maximum value allowed in the field
  public max: any;

  // shows or hides the form control
  public hidden: boolean;

  // the regex value for the field for custom validation
  // hyperlink will have it as route type
  public regex: string;

  // custom validation for regex error
  public regexError: string;

  // kind of files this input accepts
  // will be used as key for compare validation
  // will be used as target for anchor
  // will be used as detailModel for label
  public accept: string | DetailModel;

  // the type of editor this form is going to be rendered as
  public formEditorType: FormEditorType;

  // when is this control being rendered and presented in the from group
  public displayStage: ShowTime;

  // is the visibility dependent on the value of the control having key as parent-key
  public visibilityDependent: boolean;

  // the parent control's value which will make this appear
  public visibleOnParentValue: any;

  public validationURL: string;

  public formulae: ExpressionNode;

  public hint: string;

  public formURL: string;

  public visibilityOperator: VisibilityOperators;

  // will be boolean for autosuggest. True means don't call api for each key stroke
  // todo: will make anchor and hyperlink as icon if this tag is true and will use Label as icon name and hint will be tooltip
  public tag: any;
  //#endregion

  /**
   * the method to be called for loading options from remote
   * @param this the FormEditorModel on which all the operation needs to be done
   * @param remote The remote service for get operation
   * @param form export the Form group this model belongs to
   * @param param the url parameter which can to be passed to url route
   */
  public LoadOptions(remote: RemoteService, getParam: HttpParams, formGroup: FormGroup) {
    const context = this;
    const url = this.optionsURL.split('|');
    // call the post method
    remote.get(url[0], getParam, true).subscribe(data => {
      if (Array.isArray(data)) {
        // context.options = [];
        const op: FormEditorOption[] = [];
        data.forEach(d => {
          op.push(new FormEditorOption(d));
        });
        context.options = op;
        const selected = context.options.find(t => t.selected === true);
        if (selected != null && formGroup != null) {
          formGroup.get(this.key).setValue(selected.value);
        }
      } else {
        // received data is of type array of formeditoroption
        formGroup.get(this.key).setValue(data.value);
      }
      // log any errors in get the requexport est
    }, error => remote.logInformationToConsole(true, true, error));
  }

  constructor(f?: FormEditorModel, o?: boolean) {
    if (f) {
      this.label = f.label;
      this.key = f.key;
      this.placeHolder = f.placeHolder;
      this.autoFocus = f.autoFocus;
      this.required = f.required;
      this.initialValue = f.initialValue;
      this.parentKey = f.parentKey;
      this.optionsURL = f.optionsURL;
      if (o != null || f.options == null) {
        this.options = f.options;
      } else {
        if (f.options) {
          const option = [];
          f.options.forEach(t => {
            option.push(new FormEditorOption(t));
          });
          this.options = option;
        }
      }
      this.minLength = f.minLength;
      this.min = f.min;
      this.maxLength = f.maxLength;
      this.max = f.max;
      this.hidden = f.hidden;
      this.regex = f.regex;
      this.regexError = f.regexError;
      this.accept = f.accept;
      this.visibilityDependent = f.visibilityDependent;
      this.visibleOnParentValue = f.visibleOnParentValue;
      this.validationURL = f.validationURL;
      this.formURL = f.formURL;
      this.hint = f.hint;
      this.tag = f.tag;
      if (f.formulae != null) {
        this.formulae = new ExpressionNode(f.formulae);
      }
      if (f.formEditorType != null) {
        if (typeof f.formEditorType === 'string') {
          this.formEditorType = (<any>FormEditorType)[f.formEditorType];
        } else {
          this.formEditorType = f.formEditorType;
        }
      } else {
        this.formEditorType = FormEditorType.Text;
      }
      if (f.visibilityOperator != null) {
        if (typeof f.visibilityOperator === 'string') {
          this.visibilityOperator = (<any>VisibilityOperators)[f.visibilityOperator];
        } else {
          this.visibilityOperator = f.visibilityOperator;
        }
      } else {
        this.visibilityOperator = VisibilityOperators.EQUALS;
      }
      if (f.displayStage != null) {
        if (typeof f.displayStage === 'string') {
          this.displayStage = (<any>ShowTime)[f.displayStage];
        } else {
          this.displayStage = f.displayStage;
        }
      }
    }
  }
}
