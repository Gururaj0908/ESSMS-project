import { Component, OnInit } from '@angular/core';
import { TreeViewActionEventArgs } from "../tree-view/TreeViewActionEventArgs";
import { Itreeable } from "../tree-view/Itreeable";
import { RemoteService } from '../../../services/remote.service';
import { FormList } from "../../dynamic-form/FormList";
import { FormGroup } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-process',
  templateUrl: './process.component.html',
  styleUrls: ['./process.component.scss']
})
export class ProcessComponent implements OnInit {

  Data: ProcessModel;
  Form: FormList;

  constructor(private remote: RemoteService, private snackbar: MatSnackBar) { }

  ngOnInit() {
    this.LoadAllData();
    this.LoadForm();
  }

  private LoadAllData() {
    this.remote.get('/bms-objective/Process/GetProcess').subscribe(
      data => { this.Data = data; },
      err => { this.remote.logInformationToConsole(true, true, `couldn't get process data`, err); }
    );
  }

  private LoadForm() {
    this.remote.get('/bms-objective/Process/GetForm').subscribe(
      data => { this.Form = new FormList(data); },
      err => { this.remote.logInformationToConsole(true, true, `couldn't get process form`, err); }
    );
  }

  public SaveChild(e: TreeViewActionEventArgs) {
    this.remote.post('/bms-objective/Process/create', e.NodeItem, e.Form).subscribe(
      data => {
        if (e.Sender.TreeData.children == null) {
          e.Sender.TreeData.children = [];
        }
         e.Sender.TreeData.children.push(data);
         e.DialogRef.close();
       },
       err => this.remote.logInformationToConsole(true, true, 'Error Creating Process', err)
    );
  }

  public UpdateProcess(e: TreeViewActionEventArgs) {
    this.remote.put('/bms-objective/Process/update', e.NodeItem, e.Form).subscribe(
      data => {
         e.Sender.TreeData = data;
         e.DialogRef.close();
      },
      err => this.remote.logInformationToConsole(true, true, 'Error Updating Process', err)
    );
  }

  public DeleteProcess(e: TreeViewActionEventArgs) {
    this.remote.delete('/bms-objective/Process/delete', [e.NodeItem.id], true).subscribe(
      data => {
        const index = e.Sender.TreeParent.children.indexOf(e.NodeItem);
         e.Sender.TreeParent.children.splice(index, 1);
         e.DialogRef.close();
        this.snackbar.open('Process Deleted', null, { duration: 2000 });
      },
      err => {
        this.remote.logInformationToConsole(true, true, 'Could not delete process', err);
        this.snackbar.open('Process Could not be deleted', null, { duration: 2000, verticalPosition: 'top' });
      }
    );
  }

  public SaveProcess(fg: FormGroup) {
    this.remote.post('/bms-objective/Process/create', fg.getRawValue()).subscribe(
      data => {
        this.Data = data;
      }
    );
  }

}

export class ProcessModel implements Itreeable {
  parent: ProcessModel;
  children: ProcessModel[];
  id: any;
  name: string;
  minutes: number;
  description: string;
  selected: boolean;
  selectable: boolean;
  parentProcessId: string;
}
