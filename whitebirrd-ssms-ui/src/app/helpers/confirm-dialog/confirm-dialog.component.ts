import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-confirm-dialog',
  templateUrl: './confirm-dialog.component.html',
  styleUrls: ['./confirm-dialog.component.scss']
})
export class ConfirmDialogComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<ConfirmDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData) { }

  ngOnInit() {
  }

  CloseButtonClick(): void {
    this.dialogRef.close();
  }

  GetIconValue(): string {
    switch (this.data.Type) {
      case DialogType.Alert:
        return 'error';
      case DialogType.Info:
        return 'info_outline';
      case DialogType.Warning:
        return 'warning';
      case DialogType.Question:
        return 'help_outline';
      default:
        return 'info_outline';
    }
  }

}

export class DialogData {
  constructor(
    public Title: string,
    public Message: string,
    public PrimaryButtonText = 'Ok',
    public CloseButtonText = 'Cancel',
    public Type = DialogType.Info, ) { }
}

export enum DialogType {
  Info, Alert, Warning, Question
}

