import {Component, OnInit} from "@angular/core";
import {Stock} from "../dataObjects/Stock";
import {BsModalRef, BsModalService} from "ngx-bootstrap/modal";
import {UserTransaction} from "../dataObjects/UserTransaction";
import {DataService} from "../services/data.service";
import {UtilityService} from "../services/utility.service";

@Component({
  selector: 'my-stock',
  templateUrl: 'my.stock.details.html',
  styleUrls: ['../userDashboard/user.dashboard.component.css'],
})
export class MyStockDetails implements OnInit{
  userStocks!:UserTransaction[];
  modalRef!: BsModalRef;
  stockCount: number;
  currentStock!:Stock;
  constructor(private dateService:DataService,private modalService: BsModalService,private utilityService:UtilityService) {
    this.stockCount=0;
  }

  ngOnInit(): void {
    setInterval(()=>{this.updateProfileStocks()},1000)
  }

  updateProfileStocks()
  {
    this.dateService.getUserStocks(this.utilityService.getUser()).subscribe((response:any)=>{
      this.userStocks = response;
    })
  }

  sellStock(stock:UserTransaction){
    this.dateService.getStockDetails(stock.stockId).subscribe(response =>{
      let _stock = <Stock>response;
      _stock.volume = stock.units;
      this.currentStock = _stock;
    });
  }

  updateCurrentStock(stock:Stock)
  {
    this.currentStock = stock;
  }

}
