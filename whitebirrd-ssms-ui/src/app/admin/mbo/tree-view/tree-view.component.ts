import { TreeViewActionEventArgs } from './TreeViewActionEventArgs';
import { Itreeable } from './Itreeable';
import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { FormList } from '../../dynamic-form/FormList';
import { ConfirmDialogComponent, DialogType, DialogData } from '../../../helpers/confirm-dialog/confirm-dialog.component';
import { PopupViewComponent } from '../../popup-view/popup-view.component';
import { PopupType } from '../../popup-view/PopupType';
import { PopupDataArgs } from '../../popup-view/PopupDataArgs';
import { FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatCheckboxChange } from '@angular/material/checkbox';

@Component({
   selector: 'app-tree-view',
   templateUrl: './tree-view.component.html',
   styleUrls: ['./tree-view.component.scss']
})
export class TreeViewComponent implements OnInit {


   // the data in tree structure

   @Input() TreeData: Itreeable;

   // The parent of tree data
   @Input() TreeParent: Itreeable;

   // whether it is allowed for tree nodes to be selected
   @Input() Selectable: boolean;

   // whether the node is editable
   @Input() Editable: boolean;

   // whether one can add child node
   @Input() Extendable: boolean;

   // whether the node is delatable
   @Input() Deletable: boolean;

   // whether to show number of children in the badge
   @Input() ShowChildrenCount: boolean;

   // readonly
   @Input() Disabled: boolean;

   // whether the tree nodes are interconnected
   @Input() InterConnectedNodes: boolean;

   // the editor form for creating/deleting a node
   @Input() Editor: FormList;

   // the key of Title
   @Input() Title: string;

   // the key of the subtitle
   @Input() Subtitle: string;

   // the key of the tooltip
   @Input() Tooltip: string;

   // the key of the parent id
   @Input() ParentId: string;

   // raised when a the checkbox is toggled
   @Output() CheckedChanged = new EventEmitter<boolean>();

   // raised when delete command is called
   @Output() DeleteInitiated = new EventEmitter<TreeViewActionEventArgs>();

   // raised when new item is there to insert
   @Output() InsertInitiated = new EventEmitter<TreeViewActionEventArgs>();

   // raised when existing node needs to be updated
   @Output() UpdateInitiated = new EventEmitter<TreeViewActionEventArgs>();

   // view model property to force children what happend here
   public ChildrenSelection: boolean;



   // whether this node is open
   public IsOpen = true;

   private _internalopen = true;
   @Input()
   public get _internalOpen(): boolean {
      return this._internalopen;
   }
   public set _internalOpen(v: boolean) {
      this._internalopen = v;
      this.IsOpen = v;
   }


   public get SelectedIds(): any[] {
      let items = [];
      if (this.TreeData.selected) {
         items.push(this.TreeData.id);
      }
      if (this.TreeData.children != null) {
         items = items.concat(this.GetSelectedItems(this.TreeData.children));
      }
      return items;
   }




   @Input() ParentView: TreeViewComponent;
   ChildrenView: TreeViewComponent[];

   // used to know which direction the selection flow going
   private _isSelectionUpward: boolean;

   private _selected: boolean;
   // whether this node is selected
   @Input()
   public get Selected(): boolean {
      return this._selected;
   }
   public set Selected(v: boolean) {
      // update the model accordingly
      this.TreeData.selected = v;
      this._selected = v;
      if (this.InterConnectedNodes && !this._isSelectionUpward) {
         this.ChildrenSelection = v;
      }
   }

   // constructor
   constructor(public dialog: MatDialog) { }

   ngOnInit(): void {
      if (this.TreeData != null) {
         if (this.TreeData.selected === true && (this.TreeData.children == null || this.TreeData.children.length === 0)) {
            this._selected = true;
         } else {
            this._selected = false;
         }
      }
   }

   // called when checkbox is toggled
   public Checked(val: MatCheckboxChange) {
      // set the viewmodel property accordingly
      this.Selected = val.checked;
      this._isSelectionUpward = false;
      if (this.InterConnectedNodes) {
         // tell the children what happend to me
         this.ChildrenSelection = val.checked;
         // also tell the parent about what happend to me
         this.CheckedChanged.emit(val.checked);
      }
   }

   private GetSelectedItems(data: any[]): any[] {
      let retdata = [];
      data.forEach(d => {
         if (d.selected) {
            retdata.push(d.id);
         }
         if (d.children != null) {
            retdata = retdata.concat(this.GetSelectedItems(d.children));
         }
      });
      return retdata;
   }

   // to know if some child node are selected but not all of them are selected
   public IsPartialChildrenSelected() {
      if (this.InterConnectedNodes) {
         // check that some are selected AND some are not selected
         return (
            this.TreeData.children.some(t => t.selected) &&
            this.TreeData.children.some(t => !t.selected)
         );
      }
      return false;
   }

   // one of the child just got toggled in selection
   public ChildSelectionChanged(selection: boolean) {
      this._isSelectionUpward = true;
      if (this.InterConnectedNodes) {
         if (selection) {
            this.Selected = selection;
         } else {
            this.Selected =
               this.TreeData.children == null
                  ? false
                  : this.IsPartialChildrenSelected();
         }
         this.CheckedChanged.emit(selection);
      }
   }

   /**
    * delete button is clicked. Show only the confirm dialog here
    * @param data the node which needs to be deleted
    */
   public DeleteClicked(data: Itreeable) {
      this.openDialog(
         'Delete Node?',
         `Are you sure to delete ${data[this.Title]}?`,
         'Delete it',
         'Cancel',
         DialogType.Alert,
         this.RaiseDelete,
         new TreeViewActionEventArgs(this, data)
      );
   }

   /**
    * opens a confirm dialog
    * @param title the title of the dialog
    * @param message the message in the dialog body
    * @param affirmText the text on the confirm button
    * @param closeText the text on the cancel button
    * @param type the type of the dialog
    * @param callback the function to be executed when confirm button is clicked
    * @param callback_parameter the parameter to be passed in the callback function
    */
   private openDialog(
      title: string,
      message: string,
      affirmText: string,
      closeText: string,
      type: DialogType,
      callback: (id: any) => void,
      callback_parameter: any
   ): void {
      const dialogRef = this.dialog.open(ConfirmDialogComponent, {
         width: '350px',
         data: new DialogData(title, message, affirmText, closeText, type)
      });
      dialogRef.afterClosed().subscribe(result => {
         if (result) {
            callback(callback_parameter);
         }
      });
   }

   /**
    * this will be used to raise the delete event to be handled by the caller
    * @param data the data to be deleted along with the current context
    */
   private RaiseDelete(data: TreeViewActionEventArgs) {
      data.Sender.DeleteInitiated.emit(data);
   }

   /**
    * is called when add child node button is clicked against this node
    * @param data the node to which new node should be added
    */
   public NewChildNode(data: Itreeable) {
      this.dialog.open(PopupViewComponent, {
         data: new PopupDataArgs(
            null,
            null,
            'Child Node for ' + data[this.Title],
            PopupType.Form,
            this.Editor,
            this.SaveData,
            this
         )
      });
   }

   /**
    * this is called when edit button is clicked against any node
    * @param data the existing node data to be edited
    */
   public EditExistingNode(data: Itreeable) {
      this.dialog.open(PopupViewComponent, {
         data: new PopupDataArgs(
            null,
            null,
            'Edit ' + data[this.Title],
            PopupType.Form,
            this.Editor,
            this.UpdateData,
            this,
            data
         )
      });
   }

   /**
    * raises the insert event
    * @param data the node which needs to be added
    * @param context the context from which this method is called
    */
   public SaveData(
      data: Itreeable,
      context: TreeViewComponent,
      formGroup: FormGroup,
      diag
   ) {
      // set the parent id on the newly generated node
      formGroup.patchValue({ [context.ParentId]: context.TreeData.id });
      data[context.ParentId] = context.TreeData.id;
      context.InsertInitiated.emit(
         new TreeViewActionEventArgs(context, data, formGroup, diag)
      );
   }

   /**
    * raises the event to update the node
    * @param data the data to be updated
    * @param context the context from which this event is being called
    */
   public UpdateData(
      data: Itreeable,
      context: TreeViewComponent,
      formGroup: FormGroup,
      diag
   ) {
      context.UpdateInitiated.emit(
         new TreeViewActionEventArgs(context, data, formGroup, diag)
      );
   }

   // relegate function to relay the update event to parent
   public ChildUpdateInitiated(event) {
      this.UpdateInitiated.emit(event);
   }

   // relegate function to relay the insert event to parent
   public ChildInsertInitiated(event) {
      this.InsertInitiated.emit(event);
   }

   // relegate function to relay the delete event to parent
   public ChildDeleteInitiated(event) {
      this.DeleteInitiated.emit(event);
   }


}



//#endregion
