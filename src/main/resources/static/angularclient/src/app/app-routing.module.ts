import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {RegisterComponent} from "./register/register.component";
import {LoginComponent} from "./login/login.component";
import {AdmindashboardComponent} from "./AdminStockDashboard/admindashboard.component";
import {StockTransactionComponent} from "./stockTransaction/stock.transaction.component";
import {UserDashboardComponent} from "./UserDashboard/user.dashboard.component";
import {AmountComponent} from "./wallet/amount.component";
import {MyStockDetails} from "./userProfile/my.stock.details";
import {TransactionsComponent} from "./transactions/transactions.component";
import {MarketHoursComponent} from "./marketHours/market.hours.component";
import {ScheduledTransactionComponent} from "./scheduledTransactions/scheduled.transaction.component";

const routes: Routes = [
  {path:'',redirectTo:'userDashboard',pathMatch:'full'},
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path:'addStock',component:AdmindashboardComponent},
  {path:'buyStock',component:StockTransactionComponent},
  {path:'userDashboard',component:UserDashboardComponent},
  {path:'balance',component:AmountComponent},
  {path:'userProfile',component:MyStockDetails},
  {path:'transactions',component:TransactionsComponent},
  {path:'scheduleTransactions',component:ScheduledTransactionComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes,{useHash:true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
