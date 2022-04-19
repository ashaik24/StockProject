import {Component, OnInit} from '@angular/core';
import {Stock} from "../dataObjects/Stock";
import {DataService} from "../services/data.service";
import {UtilityService} from "../services/utility.service";
import {repeat} from "rxjs";

/** @title Simple form field */
@Component({
  selector: 'admin-dashboard',
  templateUrl: 'admindashboard.component.html',
  styleUrls: ['admindashboard.component.css'],
})
export class AdmindashboardComponent implements OnInit{

  stock:Stock;
  stocks:any;
  constructor(private dataService:DataService,private utilityService:UtilityService) {
    this.stock = new Stock();
    this.getUpdatedStocks();
  }

  addStock(){
    if(this.utilityService.isEmptyOrNull(this.stock.initialPrice) || this.utilityService.isEmptyOrNull(this.stock.stockTicker)
    || this.utilityService.isEmptyOrNull(this.stock.companyName) || this.utilityService.isEmptyOrNull(this.stock.volume))
    {
      this.utilityService.showDialog("Invalid Inputs","Please provide valid inputs to add stock.","close");
       return;
    }
    this.dataService.addStock(this.stock).subscribe((response)=>{
      let title = "Failed";
      let content = "Something went wrong please try again."
      if(response != null) {
        this.stocks = response;
        this.stock = new Stock();
        title = "Success";
        content="Stock Added successfully"
      }
    })
  }

  getUpdatedStocks()
  {
    this.dataService.stocksObserver.subscribe(response => {
      this.stocks = response;
    });
  }

  ngOnInit(): void {
    this.getUpdatedStocks();
  }

}
