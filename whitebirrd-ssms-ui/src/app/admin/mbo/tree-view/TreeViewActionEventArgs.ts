import { Itreeable } from './Itreeable';
import { MatDialogRef } from '@angular/material/dialog';
import { PopupViewComponent } from '../../popup-view/popup-view.component';
import { FormGroup } from '@angular/forms';
import { TreeViewComponent } from './tree-view.component';
export class TreeViewActionEventArgs {
   constructor(
      public Sender: TreeViewComponent,
      public NodeItem: Itreeable,
      public Form?: FormGroup,
      public DialogRef?: MatDialogRef<PopupViewComponent>
   ) { }
}
