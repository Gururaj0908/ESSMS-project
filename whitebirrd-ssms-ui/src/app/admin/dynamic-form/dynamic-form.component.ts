import { Component, OnInit, Input, Output, EventEmitter, AfterViewInit, ViewChild, Renderer2, ElementRef } from '@angular/core';
import { FormEditorModel } from './FormEditor';
import { FormList } from './FormList';
import { FormEditorOption } from './FormEditorOption';
import { FormEditorType } from './FormEditorType';
import { FormGroup, AbstractControl, FormControl } from '@angular/forms';
import { environment } from '../../../environments/environment.prod';
import { RemoteService } from '../../services/remote.service';
import { ExpressionSolver } from './ExpressionSolver';
import { MatFileUploadControlComponent } from '../mat-file-upload-control/mat-file-upload-control.component';
import { HttpEventType, HttpClient, HttpRequest, HttpResponse, HttpParams } from '@angular/common/http';
import { GlobalService } from '../../services/global.service';
import { map, tap, last, catchError } from 'rxjs/operators';
import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatMenuTrigger } from '@angular/material/menu';
import { MatSelectChange } from '@angular/material/select';
import { MatRadioChange } from '@angular/material/radio';

import { PopupViewComponent } from '../popup-view/popup-view.component';
import { PopupType } from '../popup-view/PopupType';
import { PopupDataArgs } from '../popup-view/PopupDataArgs';
import { DetailModel } from '../popup-view/DetailModel';
import { EventObj } from '@tinymce/tinymce-angular/editor/Events';
import { RouteType } from '../model/RouteType';
import { KeyValue } from '@angular/common';
import { HyperlinkEventArgs } from './HyperlinkEventArgs';
import { MenuItems } from './MenuItems';
import { LanguageService } from '../../services/language.service';

@Component({
  selector: 'app-dynamic-form',
  templateUrl: './dynamic-form.component.html',
  styleUrls: ['./dynamic-form.component.scss']
})
export class DynamicFormComponent implements OnInit, AfterViewInit {
  //#region Variables
  public formEditor = FormEditorType;
  public key: string;
  private Control: AbstractControl;
  public fileError: string;
  private Solver: ExpressionSolver;
  public FormSaveUrl: string;
  //#endregion

  //#region Events
  @Output() SelectionChanged = new EventEmitter<SelectionEventArgs>();
  @Output() HyperlinkClick = new EventEmitter<HyperlinkEventArgs>();
  @Output() SameControlRepeat = new EventEmitter<FormEditorModel>();
  //#endregion

  //#region Input Variables
  @Input() formEditorModel: FormEditorModel;
  @Input() form: FormGroup;
  @Input() ControlClass: string;
  @Input() Style: string;
  @Input() FormClass: string;

  //#endregion

  @ViewChild(MatMenuTrigger) trigger: MatMenuTrigger;
  //#region Constructor
  constructor(
    private remote: RemoteService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar,
    private http: HttpClient,
    private gs: GlobalService,
    public l: LanguageService,
  ) {  }

  //#endregion

  //#region Initialization Begin
  ngOnInit() {
    this.key = this.formEditorModel.key;
    this.Control = this.GetThisFormControl();
  }


  ngAfterViewInit(): void {
    // check of url field
    if (this.formEditorModel.optionsURL) {
      // check if parent-key is set means if this form control's options depends on parent value
      if (this.formEditorModel.parentKey) {
        // check if parent value is selected
        if (
          this.form.value[this.formEditorModel.parentKey] != null ||
          this.key === this.formEditorModel.parentKey
        ) {
          // check if its not the forbidden type
          if (
            this.formEditorModel.formEditorType !== FormEditorType.Hyperlink &&
            this.formEditorModel.formEditorType !== FormEditorType.Anchor &&
            this.formEditorModel.formEditorType !== FormEditorType.File &&
            this.formEditorModel.formEditorType !== FormEditorType.MultiFile
          ) {
            // load the values into its options
            const data = this.GetParamForOptions(
              this.formEditorModel.optionsURL
            );
            this.formEditorModel.LoadOptions(this.remote, data, this.form);
          }
        }
      } else {
        if (
          this.formEditorModel.formEditorType !== FormEditorType.Hyperlink &&
          this.formEditorModel.formEditorType !== FormEditorType.Anchor &&
          this.formEditorModel.formEditorType !== FormEditorType.File &&
          this.formEditorModel.formEditorType !== FormEditorType.MultiFile
        ) {
          // so just load the values into its options from remote
          this.formEditorModel.LoadOptions(this.remote, null, this.form);
        }
      }
    }
    if (this.formEditorModel && this.formEditorModel.formulae) {
      this.Solver = new ExpressionSolver(this.form);
    }
    if (this.form) {
      this.form.valueChanges.subscribe(() => this.UpdateCalculation());
    }

  }



  //#endregion

  //#region Template Methods
  get isValid() {
    return this.Control.valid;
  }

  GetFormError(): string {
    let data = '';
    if (this.Control) {
      const name = this.formEditorModel.label;
      if (this.Control.hasError('required')) {
        data = this.l.GetString('Required', name);
      } else if (this.Control.hasError('min')) {
        data = this.l.GetString('MinimumVldtr', name, this.formEditorModel.min);
      } else if (this.Control.hasError('max')) {
        data = this.l.GetString('MaximumVldtr', name, this.formEditorModel.max);
      } else if (this.Control.hasError('email')) {
        data = this.l.GetString('EmailVldtr', name);
      } else if (this.Control.hasError('minlength')) {
        data = this.l.GetString('MinLengthVldtr', name, this.formEditorModel.minLength);
      } else if (this.Control.hasError('maxlength')) {
        data = this.l.GetString('MaxLengthVldth', name, this.formEditorModel.maxLength);
      } else if (this.Control.hasError('pattern')) {
        data = ` ${this.formEditorModel.regexError}`;
      } else if (this.Control.hasError('remote')) {
        data = this.Control.errors["remote"];
      } else if (this.Control.hasError('compare')) {
        data = this.l.GetString('CompareVldtr', name, this.formEditorModel.accept);
      }
      if (data !== '' && !environment.production) {
        console.log('Validation Failed', data);
      }
    }
    return data;
  }

  GetHint() {
    let data = '';
    if (this.Control) {
      if (this.formEditorModel.maxLength != null) {
        if (this.Control.value != null) {
          data =
            (this.Control.value == null ? 0 : this.Control.value.length) +
            '/' +
            this.formEditorModel.maxLength;
        }
      } else if (this.formEditorModel.accept != null && this.formEditorModel.accept !== '') {
        if (this.formEditorModel.formEditorType === FormEditorType.File) {
          data = this.l.GetString('OneFileAllow', this.formEditorModel.accept);
        } else if (this.formEditorModel.formEditorType === FormEditorType.MultiFile) {
          data = this.l.GetString('MultipleFilesAllow', this.formEditorModel.accept);
        }

      }
    }
    return data;
  }

  RenewAutoSuggestList(event: any) {
    if (
      this.formEditorModel.optionsURL != null &&
      this.formEditorModel.tag !== true
    ) {
	  var filterValue = (event.target as HTMLInputElement).value;
      let data = this.GetParamForOptions(this.formEditorModel.optionsURL);
      data = data.append(this.key, filterValue + '');
      this.formEditorModel.LoadOptions(this.remote, data, this.form);
    }
  }

  private GetParamForOptions(optionsURL: string): HttpParams {
    const url = optionsURL.split('|');
    const data = {};
    if (url.length > 1) {
      const parentkeys = url[1].split(',');
      parentkeys.forEach(k => {
        const val = this.form.get(k).value;
        if (val != null) {
          data[k] = val;
          if (data[k] != null && data[k].formEditor) {
            data[k] = data[k].formEditor.initialValue;
          }
        } else {
          data[k] = '';
        }
      });
    } else if (this.formEditorModel.parentKey != null) {
      data[this.formEditorModel.parentKey] = this.form.get(
        this.formEditorModel.parentKey
      ).value;
    }
    const param = new HttpParams({ fromObject: data });
    return param;
  }
  //#endregion

  //#region Helper Methods
  private GetThisFormControl(): AbstractControl {
    return this.form.get(this.key);
  }

  //#endregion

  //#region Event Handlers
  onChange(event) {
    this.SelectionChanged.emit({ Sender: this, EventArgs: event, Value: this.Control.value });
  }

  displayFn(option?: FormEditorOption) {
    if (option) {
      if (option.label) {
        return option.label;
      }
      return option;
    }
    return option;
  }

  onDropDownChange(event: MatSelectChange) {
    const EventArgs = { Sender: this, EventArgs: event, Value: this.Control.value };
    this.SelectionChanged.emit(EventArgs);
    if (this.key === this.formEditorModel.parentKey) {
      this.SameControlRepeat.emit(this.formEditorModel);
    }
    if (!Array.isArray(event.source.selected)) {
      const ds = event.source.selected._getHostElement().dataset['item'];
      if (ds) {
        this.SetsecondaryField(JSON.parse(ds).fieldMap);
      }
    }
  }
  
  onRadioChange(event: MatRadioChange) {
    const EventArgs = { Sender: this, EventArgs: event, Value: this.Control.value };
    this.SelectionChanged.emit(EventArgs);
    if (this.key === this.formEditorModel.parentKey) {
      this.SameControlRepeat.emit(this.formEditorModel);
    }/* commented by Rakesh 
    if (!Array.isArray(event.source.checked)) {
      const ds = event.source.checked._getHostElement().dataset['item'];
      if (ds) {
        this.SetsecondaryField(JSON.parse(ds).fieldMap);
      }
    }*/
  }

  onCheckboxChanged(event) {
    this.SelectionChanged.emit({ Sender: this, EventArgs: event, Value: event.checked });
  }

  uploadFiles(files: FileList | File) {
    if (this.formEditorModel.optionsURL == null) {
      let formData;
      if (files instanceof FileList) {
        formData = [];
        for (let i = 0; i < files.length; i++) {
          const file = files[i];
          formData.push(file);
        }
      } else {
        formData = files;
      }
      this.form.get(this.key).setValue(formData);
    } else {
      if (files instanceof FileList) {
        for (let i = 0; i < files.length; i++) {
          const file = files[i];
          if (
            (this.formEditorModel.max && file.size > this.formEditorModel.max) ||
            (this.formEditorModel.min && file.size < this.formEditorModel.min)
          ) {
            this.fileError = this.l.GetString('FileSizeVldtr', this.formEditorModel.min / 1024, this.formEditorModel.max / 1024);
          } else {
            this.form.get(this.key).setValue(null);
            const formData = new FormData();
            formData.append(this.formEditorModel.key, file, file.name);
            this.remote.post(this.formEditorModel.optionsURL, formData, this.form, null, true).subscribe(
              data => {
                let val = this.form.get(this.key).value;
                if (val == null) {
                  val = [];
                }
                val.push(data.path);
                this.form.get(this.key).setValue(val);
              },
              error => {
                this.remote.logInformationToConsole(
                  true,
                  true,
                  'File Upload Error',
                  error
                );
                this.fileError = this.l.GetString('UploadError');
              }
            );
          }
        }
      }
    }
  }

  private _getFilesSize(files: File[]): number {
    if (files == null) {
      return 0;
    }
    let size = 0;
    for (let i = 0; i < files.length; i++) {
      const f = files[i];
      size += f.size;
    }
    return size;
  }

  private _getSizeVerb(size: number) {
    if (size === 0) {
      return '0 Byte';
    } else if (size < 1000) {
      return `${size} Bytes`;
    } else if (size < 1000 * 1024) {
      return `${(size / 1024).toFixed(2)} KB`;
    } else if (size < 1000 * 1024 * 1024) {
      return `${(size / (1024 * 1024)).toFixed(2)} MB`;
    } else if (size < 1000 * 1024 * 1024 * 1024) {
      return `${(size / (1024 * 1024 * 1024)).toFixed(2)} GB`;
    } else {
	  return '0 Byte';
	}
  }

  uploadFile(files: File | File[], fu?: MatFileUploadControlComponent) {
    this.form.get(this.key).setValue(null);
    const multiple = !(files instanceof File);
    const size = multiple ? this._getFilesSize(files as File[]) : (files as File).size;
    if (this.formEditorModel.min && size < this.formEditorModel.min) {
      this.form.get(this.key).setErrors({ '': true });
      this.fileError = this.l.GetString('MinFileSize', this._getSizeVerb(this.formEditorModel.min));
    } else if (this.formEditorModel.max && size > this.formEditorModel.max) {
      this.form.get(this.key).setErrors({ '': true });
      this.fileError = this.l.GetString('MaxFileSize', this._getSizeVerb(this.formEditorModel.max));
    } else {
      this.form.get(this.key).setValue(null);
      this.form.get(this.key).setErrors(null);
      if (multiple) {
        const allfile = files as File[];
        this._uploadFile(allfile, 1, allfile.length, fu);
      } else {
        const file = files as File;
        this._uploadFile(new Array<File>(1).fill(file), 1, 1, fu);
      }
    }
  }

  private _uploadFile(files: File[], index: number, total: number, fu?: MatFileUploadControlComponent) {
    const file = files[0];
    const formData = new FormData();
    formData.append(this.formEditorModel.key, file, file.name);
    const req = new HttpRequest(
      'POST',
      this.gs.API_BASE_URL + this.formEditorModel.optionsURL,
      formData,
      {
        reportProgress: true
      }
    );

    const response = this.http.request(req).pipe(
      map(event => {
        if (fu != null) {
          switch (event.type) {
            case HttpEventType.Sent:
              fu.Progress = 0;
              fu.Hint = this.l.GetString('UploadStart');
              break;
            case HttpEventType.UploadProgress:
              const uploaded = (event.loaded * 100) / event.total;
              fu.Progress = uploaded;
              fu.Hint = this.l.GetString('Uploading', index, total, uploaded.toFixed(2));
              break;
            case HttpEventType.ResponseHeader:
            case HttpEventType.Response:
              return event;
            default:
              break;
          }
        }
        return null;
      }),
      tap(() => { }),
      last(),
      catchError(err => {
        throw err;
      })
    );

    response.subscribe(
      data => {
        if (data instanceof HttpResponse) {
          if (data.ok) {
            const body: any = data.body;
            fu.SetServerPath(body.path);
            if (files.length === 1) {
              if (fu != null) {
                fu.Hint = this.l.GetString('UploadDone', total, total === 1 ? this.l.GetString('File') : this.l.GetString('Files'));
                fu.Progress = 100;
              }
              this.form.get(this.key).setErrors(null);
            } else {
              files.splice(0, 1);
              this._uploadFile(files, index + 1, total, fu);
            }
          }
        }
      },
      error => {
        if (fu != null) {
          fu.Progress = 0;
          fu.Hint = error.error.message ? error.error.message : error.message;
        }
        this.form.get(this.key).setErrors({ '': true });
        this.fileError = error.error.message;
        console.error('error: ', error);
      }
    );
  }

  optionSelectionDone(event: MatAutocompleteSelectedEvent) {
    const d: DetailModel = event.option.value.detailModel;
    if (d != null && d.value != null) {
      this.formEditorModel.accept = d;
      const formcontrol = this.form.get(this.key);
      formcontrol.setValue(event.option.value.value);
      formcontrol.updateValueAndValidity();
    }
    const ds = event.option._getHostElement().dataset['item'];
    if (ds) {
      this.SetsecondaryField(JSON.parse(ds).fieldMap);
    }
    this.SelectionChanged.emit({ Sender: this, EventArgs: event, Value: event.option.value });
  }

  displayWith(detail: DetailModel) {
    if (detail) {
      return detail.value;
    }
  }

  GetString(data) {
    return JSON.stringify(data);
  }

  private SetsecondaryField(m: KeyValue<string, any>[]) {
    if (m) {
      const val = {};
      m.forEach(item => {
        val[item.key] = item.value;
      });
      this.form.patchValue(val);
    }
  }


  addNewLinkClick(title?: string) {
    this.remote.get(this.formEditorModel.formURL).subscribe(
      data => {
        const form = new FormList(data);
        this.FormSaveUrl = form.url;
        this.dialog.open(PopupViewComponent, {
          data: new PopupDataArgs(null, null, title != null ? title : this.l.GetString('AddNew', this.formEditorModel.label),
            PopupType.Form, form, this.CreateOption, this)
        });
      },
      () => {
        this.snackBar.open(this.l.GetString('AddNewFail', this.formEditorModel.label),
          null,
          { duration: 2000, verticalPosition: 'top' }
        );
      }
    );
  }

  CreateOption(data, that, fg, diag) {
    that.remote.post(that.FormSaveUrl, data, fg).subscribe(
      body => {
        if (that.formEditorModel.options) {
          const option = new FormEditorOption(body);
          that.formEditorModel.options.push(option);
          if (option.fieldMap != null) {
            option.fieldMap.forEach((v, k) => {
              const formctrl = that.form.get(k);
              if (formctrl) {
                formctrl.setValue(v);
              }
            });
          }
          that.form.get(that.key).setValue(option.value);
        }
        diag.close();
      },
      () => {
        that.snackBar.open(
          this.l.GetString('AddNewFail', this.formEditorModel.label),
          null,
          { duration: 2000, verticalPosition: 'top' }
        );
      }
    );
  }
  hyperlinkClicked(data: string | DetailModel, detail?: string | DetailModel[], returnVal?: boolean) {
    if (detail != null && Array.isArray(detail)) {
      const display: DetailModel[] = [];
      detail.forEach(dd => {
        display.push(new DetailModel(dd));
      });
      this.dialog.open(PopupViewComponent, {
        data: new PopupDataArgs(null, display, null, PopupType.Local)
      });
    }
    if (data == null) {
      return;
    }
    if (typeof data === 'string') {
      const args = new HyperlinkEventArgs();
      args.Url = data;
      args.Control = this.Control;
      if (detail != null && typeof detail === 'string') {
        args.Url = data + '/' + detail;
      }
      if (this.formEditorModel.regex != null) {
        args.Route = (<any>RouteType)[this.formEditorModel.regex];
      }
      if (returnVal) {
        args.ReturnValue = true;
      }
      this.HyperlinkClick.emit(args);
    } else {
      const d = new DetailModel(data);
      if (d != null) {
        this.dialog.open(PopupViewComponent, {
          data: new PopupDataArgs(null, [d], d.label, PopupType.Local)
        });
      }
    }
  }
  //#endregion

  public UpdateCalculation() {
    if (this.Solver) {
      this.Solver.ClearValue(this.formEditorModel.formulae);
      const exp = this.Solver.SolveExpression(this.formEditorModel.formulae);
      if (exp.value !== this.form.get(this.key).value) {
        const val: any = {};
        val[this.key] = exp.value;
        this.form.patchValue(val);
      }
    }
  }

  UpdateMultiLevelSelect(val) {
    this.form.get(this.key).setValue(val);
  }

  keypress(val: EventObj<KeyboardEvent>) {
    if (val.event.shiftKey === true) {
      if (val.event.keyCode === 36) {
        val.editor.fire('contextmenu', {
            target: val.event.srcElement,
            height: 0,
            isPrimary: false,
            pointerId: 0,
            pointerType: '',
            pressure: 0,
            tangentialPressure: 0,
            tiltX: 0,
            tiltY: 0,
            twist: 0,
            width: 0,
            getCoalescedEvents: function(): PointerEvent[] {
                throw new Error('Function not implemented.');
            },
            getPredictedEvents: function(): PointerEvent[] {
                throw new Error('Function not implemented.');
            },
            altKey: false,
            button: 0,
            buttons: 0,
            clientX: 0,
            clientY: 0,
            ctrlKey: false,
            metaKey: false,
            movementX: 0,
            movementY: 0,
            offsetX: 0,
            offsetY: 0,
            pageX: 0,
            pageY: 0,
            relatedTarget: undefined,
            screenX: 0,
            screenY: 0,
            shiftKey: false,
            x: 0,
            y: 0,
            getModifierState: function(keyArg: string): boolean {
                throw new Error('Function not implemented.');
            },
            initMouseEvent: function(typeArg: string, canBubbleArg: boolean, cancelableArg: boolean, viewArg: Window, detailArg: number, screenXArg: number, screenYArg: number, clientXArg: number, clientYArg: number, ctrlKeyArg: boolean, altKeyArg: boolean, shiftKeyArg: boolean, metaKeyArg: boolean, buttonArg: number, relatedTargetArg: EventTarget): void {
                throw new Error('Function not implemented.');
            },
            detail: 0,
            view: undefined,
            which: 0,
            initUIEvent: function(typeArg: string, bubblesArg?: boolean, cancelableArg?: boolean, viewArg?: Window, detailArg?: number): void {
                throw new Error('Function not implemented.');
            },
            bubbles: false,
            cancelBubble: false,
            cancelable: false,
            composed: false,
            currentTarget: undefined,
            defaultPrevented: false,
            eventPhase: 0,
            isTrusted: false,
            returnValue: false,
            srcElement: undefined,
            timeStamp: 0,
            type: '',
            composedPath: function(): EventTarget[] {
                throw new Error('Function not implemented.');
            },
            initEvent: function(type: string, bubbles?: boolean, cancelable?: boolean): void {
                throw new Error('Function not implemented.');
            },
            preventDefault: function(): void {
                throw new Error('Function not implemented.');
            },
            stopImmediatePropagation: function(): void {
                throw new Error('Function not implemented.');
            },
            stopPropagation: function(): void {
                throw new Error('Function not implemented.');
            },
            AT_TARGET: 0,
            BUBBLING_PHASE: 0,
            CAPTURING_PHASE: 0,
            NONE: 0
        }, true);
      }
    }
  }


  MceInit(): any {
    const option: any = {};
    option['plugins'] = 'contextmenu';
    option['contextmenu'] = this.GetMenuName();
    option['contextmenu_never_use_native'] = true;
    option['nowrap'] = true;
    option['elementpath'] = false;
    option['height'] = 400;
    option['menubar'] = false;
    option['resize'] = 'both';
    option['setup'] = (e) => this.InsertTool(e, this);
    return option;
  }

  private GetTools(that: DynamicFormComponent): MenuItems[] {
    const s: MenuItems[] = [];
    if (that.formEditorModel.options != null) {
      for (let index = 0; index < that.formEditorModel.options.length; index++) {
        const element = that.formEditorModel.options[index];
        s.push(new MenuItems(index.toString(), element));
      }
    }
    return s;
  }

  private GetMenuName(): string {
    let ret = '';
    const arr = this.GetTools(this);
    arr.forEach(a => {
      ret = ret += a.command + ' ';
    });
    return ret.trim();
  }

  private InsertTool(editor, that: DynamicFormComponent) {
    const menus = this.GetTools(that);
    menus.forEach(m => {
      editor.addMenuItem(m.command, {
        text: m.label,
        onclick: function () {
          editor.insertContent(m.label);
          that.__appendValue(m.value, that);
        }
      });
    });
  }

  private __appendValue(val, that: DynamicFormComponent) {
    let ctrl = that.form.get(that.formEditorModel.placeHolder);
    if (ctrl == null) {
      if (that.formEditorModel.placeHolder != null) {
        that.form.addControl(that.formEditorModel.placeHolder, new FormControl());
        ctrl = that.form.get(that.formEditorModel.placeHolder);
        ctrl.setValue([]);
      } else {
        return;
      }
    }
    const value: any[] = ctrl.value;
    if (value.some(r => r === val)) {
      return;
    }
    value.push(val);
    ctrl.setValue(value);
  }

  ClearValue() {
    this.form.controls[this.formEditorModel.key].reset();
    if (this.formEditorModel.optionsURL != null &&
      this.formEditorModel.formEditorType !== FormEditorType.File &&
      this.formEditorModel.formEditorType !== FormEditorType.MultiFile) {
      this.formEditorModel.accept = null;
    }
  }

  PriceSelectionClicked() {
    console.log('price selector clicked');
  }

  ShowClearButton() {
    return this.form.controls[this.key].enabled && this.form.controls[this.key].value;
  }
}

export class SelectionEventArgs {
  public Sender: DynamicFormComponent;
  public EventArgs: any;
  public Value: any;
}
