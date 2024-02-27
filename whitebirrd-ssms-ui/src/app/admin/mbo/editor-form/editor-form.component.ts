import { Component, Input, OnInit } from '@angular/core';
import { FormGroup, FormArray, FormBuilder } from '@angular/forms';
import { FormEditorModel } from '../../dynamic-form/FormEditor';
import { DisplayMode } from '../../dynamic-form/DisplayMode';
import { FormList } from '../../dynamic-form/FormList';
import { FormEditorType } from '../../dynamic-form/FormEditorType';
import { CoreFormOps } from '../form-area/core-form-ops';
import { RemoteService } from '../../../services/remote.service';
import { SelectionEventArgs } from '../../dynamic-form/dynamic-form.component';
import { HyperlinkEventArgs } from '../../dynamic-form/HyperlinkEventArgs';
import { HttpParams } from '@angular/common/http';
import { VisibilityOperators } from '../enum/VisibilityOperators';
import { PopupDataArgs } from '../../popup-view/PopupDataArgs';
import { PopupType } from '../../popup-view/PopupType';
import { PopupViewComponent } from '../../popup-view/popup-view.component';
import { RouteType } from '../../model/RouteType';
import { MatDialog } from '@angular/material/dialog';
import { AdminListComponent } from '../../admin-list/admin-list.component';

@Component({
  selector: 'app-editor-form',
  templateUrl: './editor-form.component.html',
  styleUrls: ['./editor-form.component.scss']
})

/**
 * The UI representation of Individual FORMGROUP element.
 */
export class EditorFormComponent implements OnInit {


  // this form-group
  @Input() FormGroupItem: FormGroup;
  private _formListModel: FormList;

  // the form_list from which this group is being created
  @Input()
  public get FormListModel(): FormList {
    return this._formListModel;
  }
  public set FormListModel(v: FormList) {
    this._formListModel = v;
    this.ngOnInit();
  }


  // the values this group must be pre-filled with
  @Input() PrefilledValue: any;

  // the variable representation of DisplayMode enum
  displayMode = DisplayMode;
  ArrayFormListSet: Map<string, FormList[]>;

  private ConsiderPrefilledValue = true;

  // the constructor
  constructor(private dialog: MatDialog, private fb: FormBuilder, private remote: RemoteService) {
  }

  // called once when the form is rendered
  ngOnInit(): void {
    // Load the form array
    if (this.FormListModel.subForms) {
      this.ArrayFormListSet = new Map();
      this.FormListModel.subForms.forEach(form => {
        if (form.isArray && form.isDynamic) {
          // get size of array
          let size = 1;
          if (this.PrefilledValue != null) {
            size = this.PrefilledValue[form.groupOrArrayName].length;
          }
          this.ArrayFormListSet.set(form.groupOrArrayName, [new FormList(form.formLists[0])]);
          for (let ind = 1; ind < size; ind++) {
            this.AddMore(form.groupOrArrayName, form.formLists[0]);
          }
        }
      });
    }
    // check if pre-filled value is not null
    if (this.PrefilledValue) {
      // patch the form with the passed pre-filled data
      this.FormGroupItem.patchValue(this.PrefilledValue);
      const keys = Object.keys(this.PrefilledValue);
      keys.forEach(key => {
        if (this.PrefilledValue[key].formEditor) {
          const fc = this.FormGroupItem.get(key);
          if (fc != null) {
            fc.setValue(this.PrefilledValue[key].formEditor.initialValue);
          }
        }
      });
    }
  }

  // protected GetEditor(template: FormEditorModel): FormEditorModel {
  //   return new FormEditorModel(template);
  // }

  // get the form_array from this form_group by the array name and return the FormArray
  // this is now deprecated
  public GetFormArraysForArrayCreationFromArrayName(name: string): FormArray {
    // initialize a size count variable to 0
    let size = 0;

    // check if the pre-filled_value is not null and the array name exists
    if (this.PrefilledValue && this.PrefilledValue[name] && this.ConsiderPrefilledValue) {
      // set the size variable to the length of the array in data
      size = this.PrefilledValue[name].length;
      this.ConsiderPrefilledValue = false;
    }

    // get the FormArray for this array name. Its length is always going to be 1
    const array = this.GetFormArrayFromArrayName(name);

    // check if the length of array in data is greater than 1 and FormArray group's
    // count is smaller than the length of the array in data
    if (size > 1 && array.controls.length < size) {
      // get the FormList corresponding to the array editor
      const formList = this.FormListModel.subForms.find(t => t.groupOrArrayName === name).formLists[0];
      // create an empty form for this FormArray Group
      const group = new CoreFormOps(this.fb, this.remote, this.PrefilledValue == null)
        .CreatFormGroup(formList);

      // since the array is already having 1 group in it, we will add rest number of same group again by starting loop from 1
      for (let index = 1; index < size; index++) {
        array.controls.push(group);
      }
    }

    // now return the FormArray
    return array;
  }

  // get the array of formlist for dynamic array generation in ui
  public GetFormArrayForSize(name: string): FormList[] {
    if (this.ArrayFormListSet == null) {
      this.ngOnInit();
    }
    return this.ArrayFormListSet.get(name);
  }

  /**
   * Gets the Value object of this control name ( can be a simple FormControl or connected FormGroup or explicit FormArray)
   * @param name the name of the from control
   * @param index the index from where to get the values of Pre-filled data if its an array
   */
  public GetPrefilledValue(name: string, index?: any): any {
    // check if pre-filled value is not null
    if (this.PrefilledValue == null || this.PrefilledValue === undefined) {
      return null;
    }
    // get the value from the Pre-Defined values using the key name
    const val = this.PrefilledValue[name];
    // check if val is not null
    if (val == null || val === undefined) {
      return null;
    }
    // check if the value is an array or just an object
    if (index === null || index === undefined) {
      // if its an object then return it
      return val;
    } else {
      // else return the object at index of the array
      return val[index];
    }
  }

  /**
   * Gets the FormArray from this FormGroup by the provided name
   * @param name the name of the array
   */
  public GetFormArrayFromArrayName(name: string): FormArray {
    // returns the Abstract control from the Form_Group using the name and cast it to FormArray
    return this.FormGroupItem.get(name) as FormArray;
  }

  /**
   * returns the FormGroup by name of FormArray and index in FormArray
   * @param name the name of the FormArray
   * @param index the index where the FormGroup appears in the FormArray
   */
  public GetFormGroupFromArrayName(name: string, index?: number): FormGroup {
    // get the FormArray from this FormGroup using its name and cast it to FormArray
    const array = this.FormGroupItem.get(name) as FormArray;
    // check if Index value is not null or not undefined or not 0
    if (index) {
      // return the FormGroup at the placed Index in FormArray
      return array.at(index) as FormGroup;
    } else {
      // return the very first FormGroup in the FormArray
      return array.at(0) as FormGroup;
    }
  }

  /**
   * Returns the FormGroup with specified name from this FormGroup
   * @param name the name of the group
   */
  public GetFormGroupFromGroupName(name: string): FormGroup {
    // get the Abstract control as specified by its name and cast it to FormGroup
    const group = this.FormGroupItem.get(name) as FormGroup;
    // return the FormGroup
    return group;
  }

  /**
   * Adds a new FormArrayItem in the form array
   * @param name the name of the FormArray
   * @param groupList The FormList
   */
  public AddMore(name: string, groupList: FormList) {
    // push a new item in array
    this.ArrayFormListSet.get(name).push(this.GetFormlistCopy(groupList));
    // get the FormArray by its array name in this FormGroup
    const array = this.GetFormArrayFromArrayName(name);
    // generate the FormGroup object using the FormList model
    const group = new CoreFormOps(this.fb, this.remote, !this.PrefilledValue).CreatFormGroup(groupList);
    // add the generated group into the array of controls in this FormArray
    array.controls.push(group);
  }

  /**
   * Removes the FormGroup from the FormArray control set
   * @param name the name of FormArray
   * @param index Position of FormGroup in the controls array of FormArray
   */
  public RemoveArray(name: string, index: number) {
    // Get the FormArray from this FormGroup using the form name
    const array = this.GetFormArrayFromArrayName(name);
    // remove a FormGroup from the array of abstract controls in the FormArray
    array.controls.splice(index, 1);
    this.ArrayFormListSet.get(name).splice(index, 1);
  }

  // called when a value is changed on any control in form
  // this  method is there to trigger any children to load their values based on value of this control
  selectionChanged(event: SelectionEventArgs, formsList: FormEditorModel[], form: FormGroup) {
    for (let i = 0; i < formsList.length; i++) {
      const model = formsList[i];
      if (model.parentKey === event.Sender.key || (model.optionsURL != null &&
        model.optionsURL.indexOf('|') > -1 && model.optionsURL.split('|')[1].indexOf(event.Sender.key) > -1)) {
        if (model.formEditorType !== FormEditorType.File &&
          model.optionsURL && event.Value != null ) {
          const data = this.GetParamForOptions(model.optionsURL, model.parentKey, form);
          // if (model.tag === true) {
          // model.options = []; do not uncomment this because mce will not work then
          form.get(model.key).setValue(null);
          // }
          model.LoadOptions(this.remote, data, form);
        }
        if (model.visibilityDependent) {
          let hide: boolean;
          switch (model.visibilityOperator) {
            case VisibilityOperators.EQUALS:
              hide = !(event.Value === model.visibleOnParentValue);
              break;
            case VisibilityOperators.NOT_EQUALS:
              hide = !(event.Value !== model.visibleOnParentValue);
              break;
            case VisibilityOperators.GREATER_THAN:
              hide = !(event.Value > model.visibleOnParentValue);
              break;
            case VisibilityOperators.GREATER_THAN_EQUALS:
              hide = !(event.Value >= model.visibleOnParentValue);
              break;
            case VisibilityOperators.LESS_THAN:
              hide = !(event.Value < model.visibleOnParentValue);
              break;
            case VisibilityOperators.LESS_THAN_EQUALS:
              hide = !(event.Value <= model.visibleOnParentValue);
              break;
            default:
              break;
          }

          model.hidden = hide;
          const fc = form.get(model.key);
          if (hide) {
            fc.disable();
          } else {
            fc.enable();
          }
        }
      }
    }
  }

  AddMoreControl(formCtrl: FormEditorModel) {
    this.FormListModel.forms.push(formCtrl);
  }

  private GetParamForOptions(optionsURL: string, parentKey: string, form: FormGroup): any {
    const url = optionsURL.split('|');
    const data = {};
    if (url.length > 1) {
      const parentkeys = url[1].split(',');
      parentkeys.forEach(k => {
        data[k] = form.get(k).value;
      });
    } else {
      data[parentKey] = form.get(parentKey).value;
    }
    const param = new HttpParams({ fromObject: data });
    return param;
  }

  GetStyle(editor: FormEditorModel): any {
    const styleObj: any = {};
    if (editor.hidden || editor.formEditorType === FormEditorType.Hidden) {
      styleObj.width = 0;
      styleObj.padding = 0;
      return styleObj;
    }
    if (this.FormListModel.displayStyle && this.FormListModel.displayStyle === DisplayMode.StrictlyHorizontal) {
      const visibleEditors = this.FormListModel.forms.filter(t => !t.hidden && t.formEditorType !== FormEditorType.Hidden);
      styleObj.width = `${(100 / visibleEditors.length) - 1}%`;
      return styleObj;
    }
  }

  GetDisplayModeForAppDynamicForm(displayStyle: any, editor: FormEditorModel): DisplayMode {
    if (editor && editor.formEditorType === FormEditorType.Separator
      || editor.formEditorType === FormEditorType.RichText
      || editor.formEditorType === FormEditorType.MultiLevelSelect) {
      return null;
    }
    if (displayStyle == null || displayStyle === DisplayMode.StrictlyVertical) {
      return DisplayMode.StrictlyVertical;
    } else if (displayStyle === DisplayMode.StrictlyHorizontal) {
      return DisplayMode.StrictlyHorizontal;
    } else {
      return DisplayMode.DistributeEvenly;
    }
  }

  GetClassForDisplayMode(dm: DisplayMode): string {
    if (dm == null) {
      return '';
    }
    switch (dm) {
      case DisplayMode.StrictlyVertical:
        return DisplayMode[DisplayMode.StrictlyVertical];
      case DisplayMode.StrictlyHorizontal:
        return DisplayMode[DisplayMode.StrictlyHorizontal];
      case DisplayMode.DistributeEvenly:
        return DisplayMode[DisplayMode.DistributeEvenly];
      default:
        return DisplayMode[DisplayMode.StrictlyVertical];
    }
  }

  GetFormlistCopy(formList: FormList): FormList {
    return new FormList(formList);
  }

  OpenDialog(args: HyperlinkEventArgs) {
    if (args.Route === RouteType.List) {
      this.dialog.open(AdminListComponent, { width: '80%', data: args });
    } else {
      this.dialog.open(PopupViewComponent, {
        // width: '80%',
        data: new PopupDataArgs(args.Url, null, null, PopupType.Remote, null, null, null, null, args.Route)
      });
    }
  }
}
