import { Component } from '@angular/core';
import {User} from "../classes/User";
import {Router} from "@angular/router";
import { EncryptDecryptService } from '../services/encrypt-decrypt.service';
import {DataService} from "../services/data.service";
import {UtilityService} from "../services/utility.service";


@Component({
  selector: 'login-form',
  templateUrl: './login.component.html',
  styleUrls: ['../register/register.component.css']
})
export class LoginComponent {
  user:User
  constructor(private dataService:DataService,public utilityService: UtilityService,private _encryptDecrypt:EncryptDecryptService) {
    this.user = new User();
  }

  login(){
    this.dataService.tryLogin(this.user.username).subscribe(
      response => this.validateUser(<User>response)
    );
  }

  validateUser(user:User){

    let dialog_title = "Invalid User"
    let dialog_content = "Please check the credentials!!";
    let gotError = false;
    if(this.utilityService.isEmptyOrNull(this.user.username) || this.utilityService.isEmptyOrNull(this.user.password))
    {
      gotError = true;
      dialog_content = "Please provided valid login details!!"
    }

    if(gotError) {
      this.utilityService.showDialog(dialog_title, dialog_content, "close")
      return;
    }
    if(user != null && this._encryptDecrypt.decrypt(user.password) == this.user.password){
      if(user.admin == this.user.admin) {
        localStorage.setItem('username', user.username);
        localStorage.setItem('is_admin', user.admin.toString());
        localStorage.setItem('useremail',user.email);
        this.utilityService.redirectTo('userDashboard').then(r => {});
        return
      }
      else
      {
        dialog_content = this.user.admin? "No admin found with this credentials!!":"No user found with this credentials";
        dialog_title = this.user.admin?"Invalid Admin":"Invalid User";
      }
    }
      this.utilityService.showDialog(dialog_title,dialog_content,"close");
  }
}
