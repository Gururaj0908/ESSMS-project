import { FormEditorModel } from '../../dynamic-form/FormEditor';
import { ShowTime } from '../../dynamic-form/ShowTime';
import { FormList } from '../../dynamic-form/FormList';
import { FormEditorType } from '../../dynamic-form/FormEditorType';
import { FormGroup, ValidatorFn, Validators, FormControl, FormArray, FormBuilder, AsyncValidatorFn } from '@angular/forms';
import { RemoteService } from '../../../services/remote.service';
import { RemoteValidatorAsync, Compare } from './remote-validation.directive';

export class CoreFormOps {


  constructor(public fb: FormBuilder, private remote: RemoteService, private isNewData: boolean) { }

  // Creating a form group object from a FormList Object. FormList is a Unit group which can have multiple sub-formlist
  public CreatFormGroup(forms: FormList): FormGroup {
    // Create the form group from the base forms array
    const fg = this.GetTheGroup(forms.forms);
    // check if it has any sub-forms
    if (forms.subForms) {
      // iterate over each sub-form
      forms.subForms.forEach(form => {
        // is the sub-form of type array
        if (form.isArray) {
          // generate array
          fg.addControl(form.groupOrArrayName, this.GroupArray(form.formLists));
        } else {
          // generate group for each forms in sub-form
          form.formLists.forEach(fr => {
            fg.addControl(form.groupOrArrayName, this.CreatFormGroup(fr));
          });
        }
      });
    }
    // return the form group
    return fg;
  }

  // generate a form group from an array of form editor model object
  public GetTheGroup(controls: FormEditorModel[]): FormGroup {
    const formGroup = this.fb.group({});
    const groupValidator: ValidatorFn[] = [];
    // null check for parameter
    if (controls) {
      // loop through all the controls
      for (const control of controls) {
        let Ctrl: FormControl;
        if ((control.formEditorType === FormEditorType.MultiFile || control.formEditorType === FormEditorType.File)
          && control.optionsURL == null) {
          formGroup.addControl('_#$%^%$#_', new FormControl());
        }
        if (control.formEditorType === FormEditorType.Separator) {
          continue;
        }
        // create and initialize an array of validator functions
        const validatorFunction: ValidatorFn[] = [];
        const asyncValidatorFunction: AsyncValidatorFn[] = [];
        // check if the control value is required
        if (control.required) {
          // add the required validation function
          validatorFunction.push(Validators.required);
        }
        // check if control has regex pattern
        if (control.regex && control.formEditorType !== FormEditorType.Hyperlink
          && control.formEditorType !== FormEditorType.Anchor && control.formEditorType !== FormEditorType.PriceSelector) {
          // add the regex validation function
          validatorFunction.push(Validators.pattern(control.regex));
        }
        // check if the string length has a maximum limit
        if (control.maxLength && control.maxLength !== null) {
          // add the max-length validation function
          validatorFunction.push(Validators.maxLength(control.maxLength));
        }
        // check if the string length has a minimum limit
        if (control.minLength && control.minLength !== null) {
          // add the min-length validation function
          validatorFunction.push(Validators.minLength(control.minLength));
        }
        // check if the control is of type number/range
        if (control.formEditorType === FormEditorType.Number || control.formEditorType === FormEditorType.RangeSlider) {
          // check if the control has minimum value limit
          if (control.min && control.min != null) {
            // add the min validation function
            validatorFunction.push(Validators.min(Number.parseFloat(control.min)));
          }
          // check if the control has maximum value limit
          if (control.max && control.max != null) {
            // add the min validation function
            validatorFunction.push(Validators.max(Number.parseFloat(control.max)));
          }
        } else if (control.formEditorType === FormEditorType.Date
          || control.formEditorType === FormEditorType.DateTime
          || control.formEditorType === FormEditorType.Time) {
          // check if the control has minimum value limit
          if (control.min && control.min != null) {
            // add the min validation function
            validatorFunction.push(Validators.min(Date.parse(control.min)));
          }
          // check if the control has maximum value limit
          if (control.max && control.max != null) {
            // add the min validation function
            validatorFunction.push(Validators.max(Date.parse(control.max)));
          }
        }
        // check if the control is of type email
        if (control.formEditorType === FormEditorType.Email) {
          // add the email validation function
          validatorFunction.push(Validators.email);
        }
        if (control.validationURL) {
          asyncValidatorFunction.push(RemoteValidatorAsync(control.validationURL, this.remote, formGroup, control.key));
        }
        // create and add form control using the control and validator functions to group
        if (control.formEditorType === FormEditorType.Label ||
          (control.formEditorType === FormEditorType.RemoteText && !control.autoFocus)) {
          Ctrl = new FormControl({ value: control.initialValue, disabled: true }, validatorFunction);
        } else if (control.validationURL) {
          Ctrl = new FormControl(control.initialValue, validatorFunction, asyncValidatorFunction);
        } else {
          Ctrl = new FormControl(control.initialValue, validatorFunction);
        }
        // compare validation added
        if (this.notFileAnchorNorLabel(control) && control.accept) {
          groupValidator.push(Compare(Ctrl, formGroup.get(control.accept as string)));
        }
        // check if the control should be hidden
        if (control.displayStage != null) {
          switch (control.displayStage) {
            case ShowTime.NewDataOnly:
              if (!this.isNewData) {
                control.hidden = true;
                Ctrl.disable();
              }
              break;
            case ShowTime.EditDataOnly:
              if (this.isNewData) {
                control.hidden = true;
                Ctrl.disable();
              }
              break;
            case ShowTime.BothPlaces:
            default:
              // default is to render at both places so don't skip
              break;
          }
        }
        if (control.visibilityDependent && control.parentKey) {
          const parent = formGroup.get(control.parentKey);
          if (parent != null && parent.value !== control.visibleOnParentValue) {
            // Added this to hide the control when they need to be hidden. Exmple is receive -> walkin <-> adress
            control.hidden = true;
            Ctrl.disable();
          }
        }
        formGroup.addControl(control.key, Ctrl);
      }
    }
    if (groupValidator.length > 0) {
      formGroup.setValidators(groupValidator);
    }
    return formGroup;
  }

  // generate a form array from array of formlist object
  private GroupArray(forms: FormList[]): FormArray {
    // get all the form groups from list of formlist
    const allGroups = forms.map(form => this.CreatFormGroup(form));
    // return all groups as an array
    return this.fb.array(allGroups);
  }

  private notFileAnchorNorLabel(control: FormEditorModel) {
    return control.formEditorType !== FormEditorType.File &&
      control.formEditorType !== FormEditorType.Anchor &&
      control.formEditorType !== FormEditorType.Label &&
      control.formEditorType !== FormEditorType.MultiFile;
  }
}
