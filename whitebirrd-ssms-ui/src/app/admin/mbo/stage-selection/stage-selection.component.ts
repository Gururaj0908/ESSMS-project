import {
   Component, OnInit, Input, OnDestroy, Output, EventEmitter, HostBinding, Optional, Self,
   ElementRef, ViewChild
} from '@angular/core';
import {  MatSelectChange, MatSelect } from '@angular/material/select';
import { MatFormFieldControl } from '@angular/material/form-field';
import { ControlValueAccessor, FormBuilder, NgControl } from '@angular/forms';
import { Subject } from 'rxjs';
import { coerceBooleanProperty } from '@angular/cdk/coercion';
import { FormEditorOption } from '../../dynamic-form/FormEditorOption';
import { FocusMonitor } from '@angular/cdk/a11y';
import { RemoteService } from '../../../services/remote.service';
import { HttpParams } from '@angular/common/http';

@Component({
   selector: 'app-stage-selection',
   templateUrl: './stage-selection.component.html',
   styleUrls: ['./stage-selection.component.scss'],
   providers: [{ provide: MatFormFieldControl, useExisting: StageSelectionComponent }]
})
export class StageSelectionComponent implements MatFormFieldControl<any>, OnDestroy, ControlValueAccessor, OnInit {



   static nextId = 0;
   onChange;

   @Input() optionUrl: string;
   @Input() parentKey: string;
   @Input() ParentValue: any;
   @Input() Error: string;
   @Output() ValueUpdated = new EventEmitter<any>();

   @ViewChild('project', {static: false}) ProjectDdl: MatSelect;
   @ViewChild('module', {static: false}) ModuleDdl: MatSelect;
   @ViewChild('submodule', {static: false}) SubmoduleDdl: MatSelect;
   @ViewChild('milestone', {static: false}) MilestoneDdl: MatSelect;

   private _value: any;
   public get value(): any {
      return this._value;
   }
   public set value(v: any) {
      this._value = v;
      this.stateChanges.next();
      this.ValueUpdated.emit(v);
   }

   stateChanges = new Subject<void>();


   @HostBinding() id = `app-stage-selection-component-${StageSelectionComponent.nextId++}`;

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

   controlType = 'app-stage-selection-component';

   autofilled = false;

   @HostBinding('attr.aria-describedby') describedBy = 'Object Level selection';

   ProjectOptions: FormEditorOption[] = [];
   ModuleOptions: FormEditorOption[] = [];
   SubmoduleOptions: FormEditorOption[] = [];
   MilestoneOptions: FormEditorOption[] = [];

   setDescribedByIds(ids: string[]) {
      this.describedBy = ids.join(' ');
   }


   onContainerClick(event: MouseEvent) {
      if ((event.target as Element).tagName.toLowerCase() !== 'select') {
         this.elRef.nativeElement.querySelector('select').focus();
      }
   }

   constructor(fb: FormBuilder,
      @Optional() @Self() public ngControl: NgControl,
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
      if (obj != null) {
         this.remote.get('/bms-objective/ProjectItem/ProjectStack/' + obj).subscribe(
            d => {
               const keys = Object.keys(d);
               keys.forEach(k => {
                  const val = d[k];
                  const opt: FormEditorOption[] = [];
                  val.item1.forEach(item => {
                     opt.push(new FormEditorOption(item));
                  });
                  switch (k) {
                     case 'milestone':
                        this.MilestoneOptions = opt;
                        this.MilestoneDdl.value = val.item2;
                        break;
                     case 'subModule':
                        this.SubmoduleOptions = opt;
                        this.SubmoduleDdl.value = val.item2;
                        break;
                     case 'module':
                        this.ModuleOptions = opt;
                        this.ModuleDdl.value = val.item2;
                        break;
                     case 'project':
                        this.ProjectOptions = opt;
                        this.ProjectDdl.value = val.item2;
                        break;
                     default:
                        break;
                  }
               });
            },
            e => this.remote.logInformationToConsole(true, true, 'error getting values', e)
         );
      }
      this._value = obj;
   }
   registerOnChange(fn: any): void {
      this.onChange = fn;
   }
   registerOnTouched(fn: any): void {
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
               this.ProjectOptions = data;
            },
            error => this.remote.logInformationToConsole(true, true, 'Error getting options for multi-level-select', error)
         );
      }
   }


   onDropDownChange(event: MatSelectChange, level: number) {
      const val = event.value;
      if (val != null) {
         this.value = val;
         const param = {};
         param[this.parentKey] = val;
         if (level === 3) {
            return;
         }
         if (level <= 2) {
            this.MilestoneOptions = [];
         }
         if (level <= 1) {
            this.SubmoduleOptions = [];
         }
         if (level === 0) {
            this.ModuleOptions = [];
         }
         this.remote.get(this.optionUrl, new HttpParams({ fromObject: param })).subscribe(
            data => {
               if (data != null && Array.isArray(data)) {
                  data.forEach(d => {
                     switch (d.stage) {
                        case 'Project':
                           this.ProjectOptions.push(new FormEditorOption(d));
                           break;
                        case 'Module':
                           this.ModuleOptions.push(new FormEditorOption(d));
                           break;
                        case 'SubModule':
                           this.SubmoduleOptions.push(new FormEditorOption(d));
                           break;
                        case 'Milestone':
                           this.MilestoneOptions.push(new FormEditorOption(d));
                           break;
                        default:
                           break;
                     }
                  });
               }
            },
            error => this.remote.logInformationToConsole(true, true, 'Error getting options for multi-level-select', error)
         );
      }
   }
}
