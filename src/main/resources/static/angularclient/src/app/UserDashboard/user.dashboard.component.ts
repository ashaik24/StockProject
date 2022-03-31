import {Component, Input, OnInit} from '@angular/core';
import {Stock} from "../classes/Stock";
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
    setInterval(()=>{
        this.getCurrentBalance();
    },1000);
  }

  ngOnInit(): void {
    this.updateStocks();
    setInterval(()=>{this.updateStocks()},1000)
  }

  increase(stock:any)
  {
    return stock.initialPrice < stock.targetPrice;
  }

  updateStocks(){
    this.dataService.getStocks().subscribe((response)=>{
      this.stocks = response;
    })
  }

  getCurrentBalance()
  {
    if(this.utilityService.isLoggedIn() && this.utilityService.isUser()) {
      this.dataService.getUserBalance(this.utilityService.getUser()).subscribe(
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
