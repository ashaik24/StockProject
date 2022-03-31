import {Injectable} from "@angular/core";
import {ConfirmationDialogComponent} from "../confirmation-dialog/confirmation-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class UtilityService{

  private username = 'username';
  private useremail = 'useremail';
  private admin = "is_admin";
  constructor(private dialog:MatDialog,private router:Router) {
  }
  isLoggedIn(){
    return this.getUser()!=null?true:false;
  }

  isAdmin(){
    return this.isLoggedIn() && this.getIsAdmin();
  }

  isUser()
  {
    return this.isLoggedIn() && !this.isAdmin();
  }

  getUser(){
    return localStorage.getItem(this.username);
  }

  getUserEmail()
  {
    return localStorage.getItem(this.useremail);
  }

  getIsAdmin(){
    return localStorage.getItem(this.admin) == true.toString()
  }

  isEmptyOrNull(value:any)
  {
    return value == null || value === "";
  }

  showDialog(title:string,content:string,noValue:any,yesValue:any = null,confirm:boolean = false)
  {
    return this.dialog.open(ConfirmationDialogComponent, {
      width: '250px',
      data: {title:title,content: content, noValue:noValue,yesValue:yesValue,confirm:confirm}
    });
  }

  redirectTo(path:any)
  {
    return this.router.navigateByUrl(path);
  }
}
