import { Component } from '@angular/core';
import {ConfirmationDialogComponent} from "./confirmation-dialog/confirmation-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {Router} from "@angular/router";
import {UtilityService} from "./services/utility.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'angularclient';
  constructor(private dialog: MatDialog,private router:Router,public utilityService:UtilityService) {
  }


  logout(){
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      width: '250px',
      data: {title:"Logout",content: "Are you sure?",yesValue:"Yes",noValue:"No",confirm:false},
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result)
      {
        localStorage.clear();
        this.router.navigateByUrl('');
      }
    });
  }
}

