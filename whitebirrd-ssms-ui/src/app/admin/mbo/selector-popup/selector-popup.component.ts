import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ConfirmDialogComponent } from '../../../helpers/confirm-dialog/confirm-dialog.component';
import { RemoteService } from '../../../services/remote.service';
import { FormEditorOption } from '../../dynamic-form/FormEditorOption';
import { AccessSelector } from '../models/AccessSelector';
import { Observable } from 'rxjs';
import { FormControl, FormGroup } from '@angular/forms';
import { startWith } from 'rxjs/operators';
import { map } from 'rxjs/operators';

@Component({
   selector: 'app-selector-popup',
   templateUrl: './selector-popup.component.html',
   styleUrls: ['./selector-popup.component.scss']
})
export class SelectorPopupComponent implements OnInit {

   AllOption: FormEditorOption[];
   SelectedOption: AccessSelector[] = [];
   FilteredOption: Observable<FormEditorOption[]>;
   TypedText = '';
   myControl = new FormControl();

   constructor(
      public dialogRef: MatDialogRef<ConfirmDialogComponent>,
      @Inject(MAT_DIALOG_DATA) public data: PopupData, private remote: RemoteService) {
      if (data.Url) {
         this.LoadRemoteData();
      } else {
         this.AllOption = data.PreSetData;
      }
      if (data.PreFilledData) {
         this.SelectedOption = data.PreFilledData;
      }
   }

   ngOnInit() {
      this.FilteredOption = this.myControl.valueChanges
         .pipe(
            startWith<string | FormEditorOption>(''),
            map(value => typeof value === 'string' ? value : value.label),
            map(name => name ? this.filter(name) : this.AllOption.slice())
         );
   }

   private LoadRemoteData() {
      this.remote.get(this.data.Url).subscribe(d => {
         this.AllOption = d;
      }, e => { this.remote.logInformationToConsole(true, true, e); });
   }

   UserAdded(user) {
      const hasUser = this.SelectedOption.some(t => t.userId === user.option.value.value);
      if (!hasUser) {
         const access = new AccessSelector();
         access.userId = user.option.value.value;
         access.projectItemId = this.data.ItemId;
         access.objectiveId = this.data.ItemId;
         this.SelectedOption.push(access);
      }
   }

   filter(name: string): FormEditorOption[] {
      return this.AllOption.filter(option =>
         option.label.toLowerCase().indexOf(name.toLowerCase()) === 0);
   }

   displayFn(user?: FormEditorOption): string | undefined {
      return user ? user.label : undefined;
   }

   RemoveAccess(access: AccessSelector) {
      const index = this.SelectedOption.indexOf(access);
      this.SelectedOption.splice(index, 1);
   }

   Save() {
      const savedata = { 'projectId': this.data.ItemId, 'access': [] };
      this.SelectedOption.forEach(o => {
         const d = { 'userId': o.userId, 'view': o.canView, 'work': o.canEdit, 'approve': o.canApprove };
         savedata.access.push(d);
      });
      this.remote.post('/bms-objective/ProjectItem/SaveAccess', savedata).subscribe(
         d => {
            this.remote.logInformationToConsole(false, false, 'access saved');
         },
         e => { this.remote.logInformationToConsole(true, true, e); });
   }

}

export class PopupData {
   constructor(
      public ItemId: string,
      public Title: string,
      public Url?: string,
      public PreSetData?: FormEditorOption[],
      public PreFilledData?: AccessSelector[]) { }
}

