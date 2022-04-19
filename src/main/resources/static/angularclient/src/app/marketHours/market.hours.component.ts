import {Component, Injectable} from '@angular/core';
import {MarketHours} from "../dataObjects/MarketHours";
import {DataService} from "../services/data.service";
import {UtilityService} from "../services/utility.service";

@Component({
  selector: 'market-hours',
  templateUrl: './market.hours.component.html',
  styleUrls: ['../adminStockDashboard/admindashboard.component.css'],
})

export class MarketHoursComponent {

    marketHours:MarketHours;
    constructor(private dataService:DataService,private utilityService:UtilityService) {
      this.marketHours = new MarketHours();
      this.dataService.getMarketHours().subscribe((response:any)=>
      {
          console.log("Current market hours ", response);
          this.setMarketHours(response,false)
      })
    }

    setMarketHours(response:any,alert:boolean)
    {
      if (response != null) {
        this.marketHours = new MarketHours();
        this.marketHours.id = response.id;
        this.marketHours.startTime = new Date(response.startTime);
        this.marketHours.endTime = new Date(response.endTime);

        if(alert)
        {
          this.utilityService.showDialog("Success","Market start and end time are updated successfully","close");
        }
      }
    }

    updateMarketHours(){
      if(this.utilityService.isEmptyOrNull(this.marketHours.startTime) || this.utilityService.isEmptyOrNull(this.marketHours.endTime))
      {
        this.utilityService.showDialog("Invalid inputs","Please provide start and end time for the stock cycle",'close');
        return;
      }
      this.dataService.updateMarketHours(this.marketHours).subscribe((response:any)=>{
        this.setMarketHours(response,true)
      });
    }
}
