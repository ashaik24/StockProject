import {Component, OnInit, TemplateRef} from "@angular/core";
import {BsModalRef, BsModalService} from "ngx-bootstrap/modal";
import {DataService} from "../services/data.service";
import {UtilityService} from "../services/utility.service";

@Component({
  selector: 'user-amount',
  templateUrl: 'amount.component.html',
  styleUrls:['../userDashboard/user.dashboard.component.css']
})
export class AmountComponent implements OnInit{
  modalRef!: BsModalRef;
  amount:number;
  withDrawAmount:number;
  availableBalance:number = 0;
  hasError:boolean;
  constructor(private dataService: DataService, private utilityService: UtilityService,private modalService: BsModalService) {
    this.amount=0;
    this.withDrawAmount=0;
    this.hasError=false;
    if(utilityService.isLoggedIn() && utilityService.isUser())
      this.getCurrentBalance();
  }

  addMoney(){
    let data ={'amount':this.amount,'username':this.utilityService.getUser()}
    this.dataService.addMoneyToWallet(data).subscribe((response:any)=>{
      this.modalRef.hide();
      this.utilityService.showDialog("Success","Money add to your wallet successfully","close");
    })
  }

  withDrawMoney(){
    if(this.availableBalance < this.withDrawAmount){
      this.hasError=true;
    }else{
      this.hasError=false;
      let data ={'amount':this.withDrawAmount,'username':this.utilityService.getUser()}
      this.dataService.withdrawMoneyFromWallet(data).subscribe((response:any)=>{
        this.modalRef.hide();
        this.utilityService.showDialog("Success","Money withdrawn from  your wallet successfully","close");
      });
    }
  }

  openModal(template: TemplateRef<any>) {
    this.modalRef = this.modalService.show(template);
  }

  getCurrentBalance()
  {
    this.dataService.userBalanceObserver.subscribe(
      (response) => {
        this.availableBalance = <any>response;
      })
  }

  ngOnInit(): void {
      this.getCurrentBalance();;
  }

}
