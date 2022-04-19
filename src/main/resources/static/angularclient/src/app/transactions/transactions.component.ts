import {Component, OnInit} from '@angular/core';
import {UserTransaction} from "../dataObjects/UserTransaction";
import {DataService} from "../services/data.service";
import {UtilityService} from "../services/utility.service";

/** @title Simple form field */
@Component({
  selector: 'transaction-component',
  templateUrl: 'transactions.component.html',
  styleUrls: ['../userDashboard/user.dashboard.component.css'],
})
export class TransactionsComponent implements OnInit {

  userTransactions!: any[];

  constructor(private dataService:DataService,private utilityService:UtilityService) {
    this.dataService.getTransactions(this.utilityService.getUser()).subscribe((response:any)=>{
      this.userTransactions=response;
    })
  }

  ngOnInit(): void {

  }

  getTransactionMode(transaction: UserTransaction)
  {
    return transaction.isSold?"Sold":"Bought"
  }

}
