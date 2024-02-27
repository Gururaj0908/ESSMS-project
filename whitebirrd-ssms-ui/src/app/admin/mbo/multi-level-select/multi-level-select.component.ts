import { Component, OnInit, OnDestroy, HostBinding, Input, Optional, Self, ElementRef, EventEmitter, Output } from '@angular/core';
import { MatSelectChange } from '@angular/material/select';
import { MatFormFieldControl } from '@angular/material/form-field';
import { Subject } from 'rxjs';
import { NgControl, ControlValueAccessor, FormBuilder } from '@angular/forms';
import { FocusMonitor } from '@angular/cdk/a11y';
import { coerceBooleanProperty } from '@angular/cdk/coercion';
import { FormEditorOption } from '../../dynamic-form/FormEditorOption';
import { RemoteService } from '../../../services/remote.service';
import { HttpParams } from '@angular/common/http';

@Component({
   selector: 'app-multi-level-select',
   templateUrl: './multi-level-select.component.html',
   styleUrls: ['./multi-level-select.component.scss'],
   providers: [{ provide: MatFormFieldControl, useExisting: MultiLevelSelectComponent }]
})
export class MultiLevelSelectComponent implements MatFormFieldControl<any>, OnDestroy, ControlValueAccessor, OnInit {



   static nextId = 0;
   onChange;

   @Input() optionUrl: string;
   @Input() parentKey: string;
   @Input() Label: string;
   @Input() OriginalLabel: string;
   @Input() ParentValue: any;
   @Input() Error: string;
   @Output() ValueUpdated = new EventEmitter<any>();
   @Input() DisplayStyle: string;

   ShowChild = false;
   SelectedText: string;

   private _value: any;
   public get value(): any {
      return this._value;
   }
   public set value(v: any) {
      this._value = v;
      this.stateChanges.next();
      this.ValueUpdated.emit(v);
   }


   private _internalValue: any;
   public get internalValue(): any {
      return this._internalValue;
   }
   public set internalValue(v: any) {
      this._internalValue = v;
      this.stateChanges.next();
      this.ValueUpdated.emit(v);
   }



   stateChanges = new Subject<void>();


   @HostBinding() id = `app-multi-level-select-component-${MultiLevelSelectComponent.nextId++}`;

   @Input()
   get placeholder() {
      return this._placeholder;
   }
   set placeholder(plh) {
      this._placeholder = plh;
      this.stateChanges.next();
   }
   private _placeholder: string;


   focused = false;
   get empty() {
      return this.value == null;
   }

   @HostBinding('class.floating')
   get shouldLabelFloat() {
      return this.focused || !this.empty;
   }

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
   get disabled() {
      return this._disabled;
   }
   set disabled(dis) {
      this._disabled = coerceBooleanProperty(dis);
      this.stateChanges.next();
   }
   private _disabled = false;

   errorState = false;

   controlType = 'app-multi-level-select-component';

   autofilled = false;

   @HostBinding('attr.aria-describedby') describedBy = '';

   Options: FormEditorOption[] = [];

   setDescribedByIds(ids: string[]) {
      this.describedBy = ids.join(' ');
   }


   onContainerClick(event: MouseEvent) {
      if ((event.target as Element).tagName.toLowerCase() !== 'select') {
         this.elRef.nativeElement.querySelector('select').focus();
      }
   }

   constructor(@Optional() @Self() public ngControl: NgControl,
      private fm: FocusMonitor,
      private elRef: ElementRef,
      private remote: RemoteService
   ) {
      if (this.ngControl != null) {
         this.ngControl.valueAccessor = this;
      }
      fm.monitor(elRef.nativeElement, true).subscribe(origin => {
         this.focused = !!origin;
         this.stateChanges.next();
      });
      // ng control to work with formcontrol/formgroup
      if (this.ngControl != null) {
         this.ngControl.valueAccessor = this;
      }
   }

   ngOnDestroy(): void {
      this.stateChanges.complete();
      this.fm.stopMonitoring(this.elRef.nativeElement);
   }

   writeValue(obj: any): void {
      this._value = obj;
   }
   registerOnChange(fn: any): void {
      this.onChange = fn;
   }
   registerOnTouched(): void {
      this.errorState = this.ngControl.errors !== null;
   }
   setDisabledState?(isDisabled: boolean): void {
      this.disabled = isDisabled;
   }

   ngOnInit(): void {
      if (this.optionUrl) {
         const param = {};
         if (this.ParentValue != null) {
            param[this.parentKey] = this.ParentValue;
         }
         this.remote.get(this.optionUrl, new HttpParams({ fromObject: param })).subscribe(
            data => {
               this.Options = data;
            },
            error => this.remote.logInformationToConsole(true, true, 'Error getting options for multi-level-select', error)
         );
      }
   }

   clear() {
      this.internalValue = null;
      this.ShowChild = false;
   }

   onDropDownChange(event: MatSelectChange) {
      const val = event.value;
      this.SelectedText = event.source.triggerValue;
      this.internalValue = val;
      this.value = val;
      this.ShowChild = false;
      if (val != null) {
         const param = {};
         param[this.parentKey] = val;
         this.remote.get(this.optionUrl, new HttpParams({ fromObject: param })).subscribe(
            data => {
               if (data.length > 0) {
                  this.ShowChild = true;
                  this.value = null;
                  // this.Child.Options = data;
               }
            },
            error => this.remote.logInformationToConsole(true, true, 'Error getting options for multi-level-select', error)
         );
      }
   }

   ValueUpdateReceived(event) {
      this.internalValue = event;
   }



}
