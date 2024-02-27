import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { ShowTime } from '../../dynamic-form/ShowTime';
import { FormList } from '../../dynamic-form/FormList';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { CoreFormOps } from './core-form-ops';
import { environment } from '../../../../environments/environment';
import { RemoteService } from '../../../services/remote.service';
import { TableButtons } from '../../table-view/TableButtons';
import { DynamicButtonClickEventArgs } from './DynamicButtonClickEventArgs';
import { LanguageService } from '../../../services/language.service';

@Component({
  selector: 'app-form-area',
  templateUrl: './form-area.component.html',
  styleUrls: ['./form-area.component.scss']
})
export class FormAreaComponent implements OnInit {

  private _FormsList: FormList;
  @Input()
  public get FormsList(): FormList {
    return this._FormsList;
  }
  public set FormsList(v: FormList) {
    if (v) {
      this._FormsList = v;
      this.ngOnInit();
    }
  }


  @Input() ShowCommand = true;
  @Input() FormMarking: ShowTime;
  public Buttons: TableButtons[];

  private _PrefilledValue: any;
  @Input()
  public get PrefilledValue(): any {
    return this._PrefilledValue;
  }
  public set PrefilledValue(v: any) {
    this._PrefilledValue = v;
    if (this.FORM_GROUP != null && v != null) {
      this.FORM_GROUP.patchValue(v);
    }
  }

  @Input() ShowRawJson: boolean;

  @Input() SubmitButtonText: string;
  @Input() ResetButtonText: string;

  @Output() OnSubmitButtonClick = new EventEmitter<FormGroup>();
  @Output() OnCancelButtonClick = new EventEmitter<FormGroup>();
  @Output() OnDynamicButtonClick = new EventEmitter<DynamicButtonClickEventArgs>();

  FORM_GROUP: FormGroup;
  IsDevelopment: boolean;
  invalidControls: string[];

  constructor(private fb: FormBuilder, private remote: RemoteService, public l: LanguageService) { }

  // calls on initialization has completed
  ngOnInit() {
    if (this.FormsList != null) {
      this.Buttons = this.FormsList.formButtons;

      let isNewForm = true;
      if (this.FormMarking == null) {
        isNewForm = !this.PrefilledValue;
      } else {
        isNewForm = this.FormMarking === ShowTime.NewDataOnly;
      }
      const formInitPlayer = new CoreFormOps(this.fb, this.remote, isNewForm);
      // start generating form
      this.FORM_GROUP = formInitPlayer.CreatFormGroup(this.FormsList);
      if (!environment.production) {
        console.log(this.FORM_GROUP);
      }
      // this.FORM_GROUP.errors
      this.IsDevelopment = !environment.production;
    }
  }

  Submit() {
    if (this.GetInvalidForms().length === 0) {
      this.OnSubmitButtonClick.emit(this.FORM_GROUP);
    } else {
      this.ValidateForm(this.FORM_GROUP);
    }
  }

  Clicked(button: TableButtons) {
    if (button.noValidate) {
      this.submitForm(button);
    } else {
      if (this.GetInvalidForms().length === 0) {
        this.submitForm(button);
      } else {
        this.ValidateForm(this.FORM_GROUP);
      }
    }
  }

  private submitForm(but: TableButtons) {
    if (but.url == null) {
      this.OnSubmitButtonClick.emit(this.FORM_GROUP);
    } else {
      const isDownload = but.buttonOps === 'File' || but.buttonOps === 'Print';
      this.remote.execute(but.method, but.url, this.FORM_GROUP.getRawValue(), null, this.FORM_GROUP, isDownload).subscribe(
        data => this.OnDynamicButtonClick.emit(new DynamicButtonClickEventArgs(data, but)),
        error => { }
      );
    }
  }

  public ValidateForm(ctrl: any) {
    if (ctrl.controls) {
      if (Array.isArray(ctrl.controls)) {
        ctrl.controls.forEach(c => {
          this.ValidateForm(c);
        });
      } else {
        Object.keys(ctrl.controls).forEach(c => {
          const ct = ctrl.get(c);
          this.ValidateForm(ct);
        });
      }
    } else {
      ctrl.markAsTouched();
    }
  }



  public GetInvalidForms(): string[] {
    this.invalidControls = [];
    this.GetInvalidFormControls(this.FORM_GROUP);
    return this.invalidControls;
  }

  private GetInvalidFormControls(ctrl: any) {
    if (Array.isArray(ctrl.controls)) {
      ctrl.controls.forEach(c => {
        if (c instanceof FormGroup) {
          this.GetInvalidFormControls(c);
        }
      });
    } else {
      Object.keys(ctrl.controls).forEach(c => {
        const ct = ctrl.get(c);
        if (ct != null) {
          if (ct instanceof FormControl) {
            if (ct.invalid && ct.enabled) {
              this.invalidControls.push(c);
            }
          } else {
            this.GetInvalidFormControls(ct);
          }
        }
      });
    }
  }

  Cancel() {
    this.FORM_GROUP.reset();
    this.OnCancelButtonClick.emit(this.FORM_GROUP);
  }
}
