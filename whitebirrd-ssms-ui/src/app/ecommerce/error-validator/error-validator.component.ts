// import { Component, OnInit } from '@angular/core';

// @Component({
//   selector: 'app-error-validator',
//   templateUrl: './error-validator.component.html',
//   styleUrls: ['./error-validator.component.scss']
// })
// export class ErrorValidatorComponent implements OnInit {

//   constructor() { }

//   ngOnInit() {
//   }

// }
import { Component,Input} from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { ErrorValidatorService } from './errorValidatorService';

@Component({
  selector: 'control-messages',
  template: `<mat-error  style="position: absolute;top: 40px;font-size: 12px;" *ngIf="errorMessage !== null">{{errorMessage}}</mat-error>`
})
export class ErrorValidatorComponent {
  @Input() control: FormControl;
  @Input() user: string;
  constructor() {
    console.log(this.user);
   }

  get errorMessage() {
    for (const propertyName in this.control.errors) {
      if (this.control.errors.hasOwnProperty(propertyName) && this.control.touched) {

        return ErrorValidatorService.getValidatorErrorMessage(propertyName, this.control.errors[propertyName]);
      }
    }
    return null;
  }
}
