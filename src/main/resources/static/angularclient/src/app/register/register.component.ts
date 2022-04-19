import {Component, Injectable} from '@angular/core';
import {HttpClient,HttpParams,HttpHeaders} from "@angular/common/http";
import {User} from "../dataObjects/User";
import { Observable, throwError } from 'rxjs';
import { map, catchError} from 'rxjs/operators';
import {EncryptDecryptService} from "../services/encrypt-decrypt.service";
import {ConfirmationDialogComponent} from "../confirmation-dialog/confirmation-dialog.component";
import {MatDialog, MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import {Router} from "@angular/router";
import {DataService} from "../services/data.service";
import {UtilityService} from "../services/utility.service";

@Component({
  selector: 'register-form',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})

export class RegisterComponent {
  password!: string;
  retype!: string;
  user: User;
  constructor(private dataService:DataService,private _encryptDecrypt : EncryptDecryptService,private utilityService:UtilityService) {
    this.user = new User();
  }

  registerUser(){

    let error_title = "Invalid Input";
    let error_content = "";
    let gotError = false;
    if(this.utilityService.isEmptyOrNull(this.user.username) || this.utilityService.isEmptyOrNull(this.password)
      || this.utilityService.isEmptyOrNull(this.user.email) || this.utilityService.isEmptyOrNull(this.user.name))
    {
       gotError = true;
       error_content = "Not all inputs are provided"
    }

    if(gotError) {
      this.utilityService.showDialog(error_title, error_content, "close")
      return;
    }
    this.dataService.tryLogin(this.user.username).subscribe(
      response => {
        let dialog_title = "User already exists!!";
        let dialog_content = "User details given already exists! Do you want to login?"
        let success = false;
        let dialog !: MatDialogRef<any>;
        if(response == null) {
          if (this.retype == this.password)
          {
            this.user.password = this._encryptDecrypt.encrypt(this.password);
            this.dataService.registerUser(this.user).subscribe((response) => {
              dialog_title = "Success";
              dialog_content = "Registered successfully !!";
              success = true;
              dialog = this.utilityService.showDialog(dialog_title,dialog_content,"close","login");
              dialog.afterClosed().subscribe((result)=>
              {
                this.utilityService.redirectTo('login').then(()=>{});
              });
            });
          }
          else
          {
            dialog_title = "Password mismatch";
            dialog_content = "Password and retype password doesn't match";
            dialog = this.utilityService.showDialog(dialog_title,dialog_content,"close");
          }
        }
        else {
          dialog = this.utilityService.showDialog(dialog_title,dialog_content,"close","login");
          dialog.afterClosed().subscribe((result)=>
          {
            if(result) {
              this.utilityService.redirectTo('login').then(() => {
              });
            }
          });
        }

      });

  }
}
