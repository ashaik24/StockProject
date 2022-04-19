import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatButtonModule} from "@angular/material/button";
import {RegisterComponent} from "./register/register.component";
import {LoginComponent} from "./login/login.component";
import {MatFormField, MatFormFieldControl, MatFormFieldModule} from "@angular/material/form-field";
import {MatCardModule} from "@angular/material/card";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {MatInputModule} from "@angular/material/input";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {AdmindashboardComponent} from "./adminStockDashboard/admindashboard.component";
import {UserDashboardComponent} from "./userDashboard/user.dashboard.component";
import {StockTransactionComponent} from "./stockTransaction/stock.transaction.component";
import {BsDropdownModule} from "ngx-bootstrap/dropdown";
import {AmountComponent} from "./wallet/amount.component";
import {BsModalService, ModalModule} from "ngx-bootstrap/modal";
import {MyStockDetails} from "./userProfile/my.stock.details";
import { ConfirmationDialogComponent } from './confirmation-dialog/confirmation-dialog.component';
import {MatDialog, MatDialogModule} from "@angular/material/dialog";
import {MatCheckboxModule} from '@angular/material/checkbox';
import {TransactionsComponent} from "./transactions/transactions.component";
import {MarketHoursComponent} from "./marketHours/market.hours.component";
import { MatTimepickerModule } from 'mat-timepicker';
import {ScheduledTransactionComponent} from "./scheduledTransactions/scheduled.transaction.component";
import {RouterModule} from "@angular/router";
import {MatIconModule} from "@angular/material/icon";

@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    LoginComponent,
    AdmindashboardComponent,
    UserDashboardComponent,
    StockTransactionComponent,
    AmountComponent,
    MyStockDetails,
    ConfirmationDialogComponent,
    TransactionsComponent,
    MarketHoursComponent,
    ScheduledTransactionComponent
  ],
  imports: [
    BsDropdownModule,
    BrowserModule,
    MatDialogModule,
    ReactiveFormsModule,
    ModalModule,
    HttpClientModule,
    AppRoutingModule,
    MatToolbarModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatFormFieldModule,
    MatCardModule,
    FormsModule,
    MatInputModule,
    MatCheckboxModule,
    MatTimepickerModule,
    MatIconModule
  ],
  providers: [ BsModalService],
  bootstrap: [AppComponent]
})
export class AppModule { }
