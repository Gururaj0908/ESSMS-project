import { ValidatorFn, AbstractControl, FormGroup, AsyncValidatorFn } from '@angular/forms';
import { RemoteService } from '../../../services/remote.service';
import { map, debounceTime } from 'rxjs/operators';
import { of } from 'rxjs';

// @Directive({
//   selector: '[appRemoteValidation]',
//   providers: [{ provide: NG_VALIDATORS, useExisting: RemoteValidationDirective, multi: true }]
// })
// export class RemoteValidationDirective implements AsyncValidator {

//   @Input() RemoteUrl: string;
//   @Input() Form: FormGroup;
//   @Input() Key: string;

//   validate(c: AbstractControl): Observable<{ [key: string]: any; }> {
//     RemoteValidatorAsync(this.RemoteUrl, this.remote, this.Form, this.Key);
//     return null;
//   }
//   registerOnValidatorChange?(fn: () => void): void {
//     throw new Error('Method not implemented.');
//   }
//   constructor(private remote: RemoteService) { }
// }


export function RemoteValidatorAsync(url: string, remote: RemoteService, form: FormGroup, formKey: string): AsyncValidatorFn {
  return (control: AbstractControl) => {
    if (control.value == null) {
      return of(null);
    } else {
      const data = GetPostBodyForOptions(url, form);
      data[formKey] = control.value;
      return remote.post(url.split('|')[0], data, form, null, true).pipe(
        debounceTime(500),
        map(result => result ? { invalid: true } : null)
      );
    }
  };
}


export function Compare(control: AbstractControl, compareTo: AbstractControl): ValidatorFn {
  return (): { [key: string]: any } => {
    const err = control.value !== compareTo.value;
    if (err) {
      const error = { 'compare': { value: control.value } };
      control.setErrors(error);
      return error;
    }
    return null;
  };
}

function GetPostBodyForOptions(optionsURL: string, form: FormGroup): any {
  const url = optionsURL.split('|');
  const data = {};
  if (url.length > 1) {
    const parentkeys = url[1].split(',');
    parentkeys.forEach(k => {
      const ctrl = form.get(k);
      if (ctrl) {
        data[k] = ctrl.value;
        if (data[k] != null && data[k].formEditor) {
          data[k] = data[k].formEditor.initialValue;
        }
      }
    });
  }
  return data;
}
