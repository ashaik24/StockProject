import {Component, Input, OnInit} from '@angular/core';
import {Stock} from "../dataObjects/Stock";
import {Router} from "@angular/router";
import {UtilityService} from "../services/utility.service";
import {DataService} from "../services/data.service";

/** @title Simple form field */
@Component({
  selector: 'admin-dashboard',
  templateUrl: 'user.dashboard.component.html',
  styleUrls: ['user.dashboard.component.css'],
})
export class UserDashboardComponent implements OnInit{

  stock:Stock;
  stocks:any;
  currentStock:any;
  availableBalance:any;
  constructor(private dataService:DataService,private router:Router,public utilityService:UtilityService) {
    this.stock = new Stock();
    this.getCurrentBalance();
  }

  ngOnInit(): void {
    this.getStocks();
  }

  increase(stock:any)
  {
    return stock.initialPrice < stock.targetPrice;
  }

  getStocks(){
    this.dataService.stocksObserver.subscribe((response)=>{
      this.stocks = response;
    })
  }

  getCurrentBalance()
  {
    if(this.utilityService.isLoggedIn() && this.utilityService.isUser()) {
      this.dataService.userBalanceObserver.subscribe(
        (response) => {
          this.availableBalance = <any>response;
        })
    }
  }

  buyStock(addedStocks:Stock){
    this.currentStock = addedStocks;
  }

  UpdateCurrentStock(stock:Stock)
  {
    this.currentStock = stock;
  }

}
