import {
  Component, OnInit, Input, OnDestroy, HostBinding, ElementRef, Optional,
  Self, ViewChild, Output, EventEmitter
} from '@angular/core';
import { MatFormFieldControl } from '@angular/material/form-field';
import { coerceBooleanProperty } from '@angular/cdk/coercion';
import { NgControl, ControlValueAccessor } from '@angular/forms';
import { Subject } from 'rxjs';
import { FocusMonitor } from '@angular/cdk/a11y';
import { LanguageService } from '../../services/language.service';

@Component({
  selector: 'app-mat-file-upload-control',
  templateUrl: './mat-file-upload-control.component.html',
  styleUrls: ['./mat-file-upload-control.component.scss'],
  providers: [{ provide: MatFormFieldControl, useExisting: MatFileUploadControlComponent }]
})
export class MatFileUploadControlComponent implements MatFormFieldControl<any>, OnDestroy, ControlValueAccessor, OnInit {

  //#region Static Variables
  static nextId = 0;
  //#endregion

  //#region Events Emitters
  @Output() UploadClicked = new EventEmitter<File | File[]>();
  @Output() Cleared = new EventEmitter();
  @Output() FileSelected = new EventEmitter<File | File[]>();
  //#endregion

  //#region Event Variables
  onChange;
  onClear;
  //#endregion

  //#region Html References
  @ViewChild('uploader', {static: false}) FileControl: ElementRef<HTMLInputElement>;
  //#endregion

  //#region Input Variables
  @Input() Accept: string;



  @Input() UploadURL: string;

  @Input()
  get placeholder() {
    return this._placeholder;
  }
  set placeholder(plh) {
    this._placeholder = plh;
    this.stateChanges.next();
  }
  private _placeholder: string;

  @Input()
  get required() {
    return this._required;
  }
  set required(req) {
    this._required = coerceBooleanProperty(req);
    this.stateChanges.next();
  }
  private _required = false;

  @Input()
  get multiple() {
    return this._multiple;
  }
  set multiple(mul) {
    this._multiple = coerceBooleanProperty(mul);
    this.stateChanges.next();
  }
  private _multiple = false;

  @Input()
  get disabled() {
    return this._disabled;
  }
  set disabled(dis) {
    this._disabled = coerceBooleanProperty(dis);
    this.stateChanges.next();
  }
  private _disabled = false;

  //#endregion

  //#region Plain Variables
  public Hint: string;

  public Progress: number;

  public get value(): string | string[] | File | File[] {
    if (this.FileControl != null) {
      const f = this.FileControl.nativeElement.files;
      if (this.UploadURL != null) {
        // needs manual uploading
        if (f.length > 0) {
          const val = (this.ServerPath == null || this.ServerPath.length === 0) ? '' : this.ServerPath;
          return val;
        }
        return null;
      } else {
        // automatic upload on form submit
        return this.LocalValue;
      }
    }
    return null;
  }


  public set value(v: string | string[] | File | File[]) {
    if (this.UploadURL != null) {
      if (typeof v === 'string') {
        this.ServerPath = v;
      } else if (Array.isArray(v) && typeof v[0] === 'string') {
        this.ServerPath = (v as string[]);
      }
    }
  }

  stateChanges = new Subject<void>();

  focused = false;

  private ServerPath: string | string[];

  errorState: boolean;

  controlType = 'mat-file-upload';

  autofilled?: boolean;

  get empty() {
    return this.value == null;
  }

  //#endregion

  //#region Host Binding
  @HostBinding() id = 'mat-file-upload-' + MatFileUploadControlComponent.nextId++;

  @HostBinding('class.floating')
  get shouldLabelFloat() {
    return this.focused || !this.empty;
  }

  @HostBinding('attr.aria-describedby') describedBy = '';

  //#endregion

  //#region Matformfieldcontrol methods

  setDescribedByIds(ids: string[]) {
    this.describedBy = ids.join(' ');
  }

  onContainerClick(event: MouseEvent): void {
    if ((event.target as Element).tagName.toLowerCase() !== 'input') {
      this.elRef.nativeElement.querySelector('input').focus();
    }
  }

  //#endregion

  //#region constructor
  constructor(public l: LanguageService,
    private fm: FocusMonitor,
    @Optional() @Self() public ngControl: NgControl,
    private elRef: ElementRef) {

    // focus manager
    fm.monitor(elRef.nativeElement, true).subscribe(origin => {
      this.focused = !!origin;
      if (!this.ngControl.touched) {
        this.errorState = ngControl.errors !== null && ngControl.touched && !this.focused;
      } else {
        this.errorState = ngControl.errors !== null;
      }
      this.TouchedInput(origin);
      this.stateChanges.next();
    });
    // ng control to work with formcontrol/formgroup
    if (this.ngControl != null) {
      this.ngControl.valueAccessor = this;
    }
  }
  //#endregion

  //#region Content Value Accessor methods
  writeValue(obj: any): void {
    if (obj != null) {
      this.SetServerPath(obj);
    }
  }

  /**
   * SetServerPath
path: string    */
  public SetServerPath(path: string) {
    if (this.multiple) {
      if (this.ServerPath == null) {
        this.ServerPath = [];
      }
      (this.ServerPath as string[]).push(path);
    } else {
      this.ServerPath = path;
    }
    this.onChange(this.value);
    this.stateChanges.next();
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
    this.onClear = fn;
  }
  registerOnTouched(fn: any): void {
    this.errorState = this.ngControl.errors !== null;
    this.TouchedInput = fn;
  }
  setDisabledState?(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }
  //#endregion

  //#region On Init
  ngOnInit(): void {
    this.ngControl.statusChanges.subscribe(t => {
      if (t === 'INVALID') {
        this.errorState = this.ngControl.errors !== null;
      }
    });
  }
  //#endregion

  //#region On Destroy Methods

  ngOnDestroy(): void {
    this.stateChanges.complete();
    this.fm.stopMonitoring(this.elRef.nativeElement);
  }

  //#endregion

  //#region Methods

  private getFileSize(files: File[]) {
    let size = 0;
    for (let i = 0; i < files.length; i++) {
      const file = files[i];
      size += file.size;
    }
    if (size < 1000) {
      return size + 'B';
    }
    size = size / 1024;
    if (size < 1000) {
      return size.toFixed(2) + 'KB';
    }
    size = size / 1024;
    if (size < 1000) {
      return size.toFixed(2) + 'MB';
    }
    size = size / 1024;
    if (size < 1000) {
      return size.toFixed(2) + 'GB';
    }
    size = size / 1024;
    if (size < 1000) {
      return size.toFixed(2) + 'TB';
    }
    return null;
  }

  private GetFileList(): File[] {
    const fl: File[] = [];
    if (this.FileControl != null) {
      const f = this.FileControl.nativeElement.files;
      for (let i = 0; i < f.length; i++) {
        const file = f.item(i);
        fl.push(file);
      }
    }
    return fl;
  }


  private get LocalValue(): File | File[] {
    const f = this.FileControl.nativeElement.files;
    if (f.length === 0) {
      return null;
    }
    if (this.multiple) {
      // multiple file
      const ff: File[] = [];
      for (let i = 0; i < f.length; i++) {
        const file = f.item(i);
        ff.push(file);
      }
      return ff;
    } else {
      // single file
      return f.item(0);
    }
  }


  //#endregion

  //#region UI Event Handlers
  public Changed(event) {
    const files = this.GetFileList();
    this.onChange(this.value);
    this.Hint = files.length === 0 ? '' : this.l.GetString('FileSize') + ' : ' + this.getFileSize(files);
    if (files.length > 1) {
      this.Hint += ` - (${files.length} files)`;
    }
    this.stateChanges.next();
    this.errorState = this.ngControl.errors !== null;
    if (this.UploadURL == null) {
      this.FileSelected.emit(this.value as any);
    }
  }

  public TouchedInput(event) {

  }


  public Upload() {
    this.ServerPath = null;
    this.UploadClicked.emit(this.LocalValue);
    this.Hint = this.l.GetString('UploadInit');
  }


  public Clear() {
    this.onClear(null);
    this.FileControl.nativeElement.value = null;
    this.Hint = '';
    this.stateChanges.next();
    this.errorState = this.ngControl.errors !== null;
    this.Cleared.emit();
  }


  public PickFile() {
    this.FileControl.nativeElement.click();
  }

  //#endregion

  get DisplayName(): string {
    if (this.FileControl != null) {
      const f = this.FileControl.nativeElement.files;
      if (f.length === 0) {
        return null;
      }
      let output = '';
      for (let i = 0; i < f.length; i++) {
        const file = f.item(i);
        output += file.name + ' | ';
      }
      output = output.slice(0, output.length - 2);
      return output;
    }
    return null;
  }


}
