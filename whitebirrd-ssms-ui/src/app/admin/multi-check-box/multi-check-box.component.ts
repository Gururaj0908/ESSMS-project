import { Component, Input, OnDestroy, HostBinding, Optional, Self, ElementRef, ViewChild, Output, EventEmitter } from '@angular/core';
import { FormEditorOption } from '../dynamic-form/FormEditorOption';
import { Subject } from 'rxjs';
import { NgControl, ControlValueAccessor } from '@angular/forms';
import { FocusMonitor } from '@angular/cdk/a11y';
import { coerceBooleanProperty } from '@angular/cdk/coercion';
import { MatFormFieldControl } from '@angular/material/form-field';
import { MatSelectionList, MatSelectionListChange } from '@angular/material/list';

@Component({
  selector: 'app-multi-check-box',
  templateUrl: './multi-check-box.component.html',
  styleUrls: ['./multi-check-box.component.scss'],
  providers: [{ provide: MatFormFieldControl, useExisting: MultiCheckBoxComponent }]
})
export class MultiCheckBoxComponent implements OnDestroy, ControlValueAccessor, MatFormFieldControl<any[]> {

  static nextId = 0;

  @ViewChild('field', {static: false})
  Control: MatSelectionList;

  private _src: FormEditorOption[];
  _onChange: (_: any) => void;
  _onTouch: any;

  @Input()
  public get DataSource(): FormEditorOption[] {
    return this._src;
  }
  public set DataSource(v: FormEditorOption[]) {
    this._src = v;
    if (v != null) {
      this.value = v.filter(t => t.selected).map(t => t.value);
    }
  }

  @Output()
  public change: EventEmitter<any> = new EventEmitter();

  val: any[];
  stateChanges = new Subject<void>();

  public set value(v: any[]) {
    this.val = v;
    this.stateChanges.next();
  }

  public get value(): any[] {
    return this.val;
  }


  @HostBinding() id = `multi-check-box-${MultiCheckBoxComponent.nextId++}`; placeholder: string;
  focused = false;
  get empty() {
    return this.value != null && this.value.length > 0;
  }
  shouldLabelFloat = true;
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
  get disabled(): boolean { return this._disabled; }
  set disabled(value: boolean) {
    this._disabled = coerceBooleanProperty(value);
    this.stateChanges.next();
  }
  private _disabled = false;
  errorState: boolean;
  controlType = 'multi-check-box';
  autofilled?: boolean;
  @HostBinding('attr.aria-describedby') describedBy = '';

  setDescribedByIds(ids: string[]) {
    this.describedBy = ids.join(' ');
  }
  onContainerClick(event: MouseEvent): void {
  }

  SelectionChanged(event: MatSelectionListChange) {
    this.value = this.Control.selectedOptions.selected.map(t => t.value);
    this._onChange(this.value);
    this.change.emit(this.value);
  }

  constructor(@Optional() @Self() public ngControl: NgControl, private fm: FocusMonitor, private elRef: ElementRef<HTMLElement>) {
    if (this.ngControl != null) {
      this.ngControl.valueAccessor = this;
    }
    fm.monitor(elRef.nativeElement, true).subscribe(origin => {
      this.focused = !!origin;
      this.stateChanges.next();
    });
  }
  ngOnDestroy() {
    this.stateChanges.complete();
    this.fm.stopMonitoring(this.elRef.nativeElement);
  }
  writeValue(obj: any): void {

  }
  registerOnChange(fn: any): void {
    this._onChange = fn;
  }
  registerOnTouched(fn: any): void {
    this._onTouch = fn;
  }
  setDisabledState?(isDisabled: boolean): void {

  }
}
