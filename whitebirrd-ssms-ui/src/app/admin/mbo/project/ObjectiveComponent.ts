import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog } from '@angular/material/dialog';
import { RemoteService } from '../../../services/remote.service';
import { GlobalService } from '../../../services/global.service';
import { ObjectiveModel } from '../models/ObjectiveModel';
import { FormList } from '../../dynamic-form/FormList';
import { FormEditorOption } from '../../dynamic-form/FormEditorOption';
import { FormGroup } from '@angular/forms';
import { AccessSelector } from '../models/AccessSelector';
import { DynamicProjectDisplayComponent } from '../dynamic-project-display/dynamic-project-display.component';
import { DynamicObjectiveDisplayComponent } from '../dynamic-objective-display/dynamic-objective-display.component';

export class ObjectiveComponent {
   EditLoading: boolean;
   ExtensionForm: FormList;
   DenialForm: FormList;
   ObjectiveEditor: FormList;
   constructor(public globalV: GlobalService, private snackbar: MatSnackBar, dialog: MatDialog, private Remote: RemoteService) {
      // this.RunEditorFetcher();
   }
   ColumnNames(): string[] {
      return [];
   }
   // GetFormData(string?: any): ObjectiveModel {
   //   return new ObjectiveModel(JSON.stringify(this.EditorForm.value));
   // }
   private RunEditorFetcher() {
      this.Remote.get('/bms-objective/Objective/GetForm').subscribe(data => {
         this.ObjectiveEditor = new FormList(data);
      }, errors => {
         this.Remote.logInformationToConsole(true, true, errors);
      });
   }
   // SubmitFormData
   public SubmitFormData(form: FormGroup, cb: (a: ObjectiveModel[], b: DynamicProjectDisplayComponent) => void,
      that: DynamicProjectDisplayComponent) {
      this.Remote.post('/bms-objective/Objective/create', form.getRawValue(), form).subscribe(
         d => cb(d, that),
         e => {
            this.snackbar.open('An error occurred while creating objective', null, { verticalPosition: 'top', duration: 2500 });
            this.Remote.logInformationToConsole(true, true, 'Objective Insert Failed', e);
         });
   }
   public SubmitEditedFormData(form: FormGroup, cb: (a: ObjectiveModel, b: DynamicObjectiveDisplayComponent) => void,
      that: DynamicObjectiveDisplayComponent) {
      this.Remote.put('/bms-objective/Objective/update', form.getRawValue(), form).subscribe(d => cb(d, that), e => {
         this.snackbar.open('An error occurred while updating', null, { verticalPosition: 'top', duration: 2500 });
         this.Remote.logInformationToConsole(true, true, 'Objective Update Failed', e);
      });
   }
   public SubmitPatchFormData(data: any, cb: (a: ObjectiveModel, b: DynamicObjectiveDisplayComponent) => void,
      that: DynamicObjectiveDisplayComponent) {
      this.Remote.patch('/bms-objective/Objective/statusUpdate', data).subscribe(d => cb(d, that), e => {
         this.snackbar.open('An error occurred while updating', null, { verticalPosition: 'top', duration: 2500 });
         this.Remote.logInformationToConsole(true, true, 'Objective Patch Failed', e);
      });
   }
   public SetEditorForm(id: string) {
      this.Remote.get('/bms-objective/ProjectItem/GetDevelopersForProjectItem/' + id).subscribe(d => {
         this.ObjectiveEditor.forms.forEach(form => {
            if (form.key === 'assignedTo') {
               form.options = d;
            } else if (form.key === 'ProjectItemId') {
               form.initialValue = id;
            }
         });
      });
   }
   public GetObjectiveForm(id: string, access: AccessSelector[]) {
      this.ObjectiveEditor.forms.forEach(form => {
         if (form.key === 'assignedTo') {
            form.options = this.MakeOptions(access);
         } else if (form.key === 'ProjectItemId') {
            form.initialValue = id;
         }
      });
   }
   private MakeOptions(access: AccessSelector[]): FormEditorOption[] {
      const options = new Array<FormEditorOption>();
      access.forEach(t => {
         if (t.canEdit) {
            const opt = new FormEditorOption();
            opt.label = t.userId;
            opt.value = t.userId;
            options.push(opt);
         }
      });
      return options;
   }
}
