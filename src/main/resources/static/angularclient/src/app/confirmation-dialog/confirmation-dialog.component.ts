import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";


export interface DialogData {
  title: string;
  content: string;
  yesValue: string;
  noValue: string;
  confirm:boolean;
}

@Component({
  selector: 'app-confirmation-dialog',
  templateUrl: './confirmation-dialog.component.html',
  styleUrls: ['./confirmation-dialog.component.css']
})
export class ConfirmationDialogComponent{

  constructor(
    public dialogRef: MatDialogRef<ConfirmationDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
  ) {
    dialogRef.beforeClosed().subscribe(() => dialogRef.close(this.data.confirm));

  }

  onNoClick(): void {

    this.data.confirm = false;
    this.dialogRef.close();
  }

  onYesClick(): void {
    this.data.confirm = true;
    this.dialogRef.close();
  }
}
