import {Component, OnInit} from '@angular/core';
import {DataService} from "../services/data.service";
import {UtilityService} from "../services/utility.service";
import {ScheduledTransaction} from "../dataObjects/ScheduledTransaction";

@Component({
  selector: 'scheduled-transaction-component',
  templateUrl: 'scheduled.transaction.component.html',
  styleUrls: ['../userDashboard/user.dashboard.component.css'],
})
export class ScheduledTransactionComponent implements OnInit {

  scheduledTransactions!: ScheduledTransaction[];

  constructor(private dataService:DataService,private utilityService:UtilityService) {
    this.getAllTransaction();
  }

  ngOnInit(): void {

  }

  getAllTransaction()
  {
    this.dataService.getScheduledTransactions(this.utilityService.getUser()).subscribe((response:any)=>{
      this.scheduledTransactions = response;
    })
  }

  getTransactionMode(transaction: ScheduledTransaction)
  {
    return transaction.isSelling?"Selling":"Buying"
  }

  deleteScheduledTransaction(transaction:ScheduledTransaction)
  {
    this.utilityService.showDialog("Confirmation","Are you sure","No","Yes",false).afterClosed().subscribe(
      (result)=>{
        if(result)
        {
          this.dataService.deleteScheduledTransaction(transaction.id).subscribe((response)=>{
            if(response)
            {
              this.getAllTransaction();
            }
            else {
              this.utilityService.showDialog("Error",
                "Something went wrong, couldn't cancel the transaction. Please try again!","close");
            }
          });
        }
      }
    )

  }


}
