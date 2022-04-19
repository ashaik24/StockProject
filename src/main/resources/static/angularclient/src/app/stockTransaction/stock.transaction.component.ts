import {Component, EventEmitter, Input, Output} from "@angular/core";
import {Stock} from "../dataObjects/Stock";
import {DataService} from "../services/data.service";
import {UtilityService} from "../services/utility.service";

/** @title Simple form field */
@Component({
  selector: 'stock-transaction',
  templateUrl: 'stock.transaction.component.html'
})
export class StockTransactionComponent {
  url!:string;
  private _currentStock!:any;
  @Input()
  set currentStock(value: any) {
    this._currentStock = value;
  }

  get currentStock(): any {
    return this._currentStock;
  }
  @Output() currentStockChanged : EventEmitter<Stock> = new EventEmitter<Stock>();
  @Input() toSell!:boolean;
  units:number;
  expiry!:Date;
  scheduleThis:boolean = false;
  desiredPrice!:number;
  currentPrice!:number;

  constructor(private dataService:DataService,private utilityService:UtilityService) {
    this.units=0;
    if(this.currentStock != null)
      this.desiredPrice = this.currentStock.initialPrice;
    setInterval(()=>{
      if(this.currentStock != null && !this.utilityService.isEmptyOrNull(this.currentStock.id)) {
        this.dataService.getStockDetails(this.currentStock.id).subscribe((response:any)=>{
          this.currentPrice=response.initialPrice;
        });
      }
    },1000);
  }

  canBuy()
  {
    return !this.utilityService.isEmptyOrNull(this.units) && (!this.scheduleThis || (!this.utilityService.isEmptyOrNull(this.desiredPrice)
      && !this.utilityService.isEmptyOrNull(this.expiry))) && this.units > 0;
  }

  buyStock(){
    if(!this.canBuy())
    {
      return;
    }
    if(this.units > this.currentStock.volume)
    {
      this.utilityService.showDialog("Insufficient units","You don't have desired amount of stocks to "+this.getSellorBuy()+".","close");
      return;
    }

    this.dataService.getUserBalance(this.utilityService.getUser()).subscribe((response)=> {
      let requiredFunds : number =  this.units * (this.scheduleThis? this.desiredPrice: this.currentPrice);
      if(<number>response < requiredFunds && !this.toSell)
      {
        this.utilityService.showDialog("Insufficient funds","Your wallet doesn't have required amount of funds. Need $"+(requiredFunds-<number>response).toFixed(2),"close");
      }
      else {
        let data =
        {
          stockId:this.currentStock.id,
          units:this.units,
          username:localStorage.getItem('username'),
          expiry:this.expiry,
          desirePrice:this.desiredPrice,
          isSell:this.toSell
        };
        this.dataService.buyOrSellStock(this.toSell?"sellStocks":"buyStocks",data).subscribe((response:any)=>{
          if(response == null)
          {

            this.utilityService.showDialog("Error!","Something went wrong couldn't "+this.getSellorBuy()+" this stock at the moment","close");

          }else{
            this.utilityService.showDialog("Success", "Transaction is successful","close");
            this.currentStock = null;
            this.desiredPrice = 0;
            this.units = 0;
            this.scheduleThis = false;
            this.expiry = new Date();
            this.currentPrice = 0;
            this.currentStockChanged.emit(this.currentStock);
          }
        })
      }
    })

  }

  getSellorBuy(){
    return this.toSell?"Sell":"Buy";
  }

}
