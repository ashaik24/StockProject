import {Injectable} from "@angular/core";
import {Stock} from "../classes/Stock";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {MarketHours} from "../classes/MarketHours";

@Injectable({
  providedIn: 'root'
})
export class DataService {

  constructor(private http:HttpClient) {
  }
  private hostAddress : string = "http://localhost:8080/";
  private addStocksUrl : string = 'addStocks';
  private getStocksUrl : string = 'getStocks';
  private getStockDetailsUrl : string = 'getStockDetails';
  private loginUrl : string = 'login';
  private registerUrl : string = 'register';
  private getTransactionsUrl : string = 'getTransactions';
  private getUserStocksUrl : string = 'getUserStocks';
  private addMoneyUrl : string = 'addMoney';
  private withdrawMoneyUrl : string = 'withDrawMoney';
  private getBalanceUrl : string = 'getBalance';
  private getMarketHoursUrl : string = 'getMarketHours';
  private updateMarketHoursUrl : string = 'updateMarketHours';
  private getUserScheduledTransactionsUrl :string = 'getUserScheduledTransactions';
  private deleteScheduledTransactionUrl: string = 'deleteScheduledTransaction';
  private headers = new HttpHeaders()
    .append(
      'Content-Type',
      'application/json'
    );

  private basicGetWithParameter(urlExtension:string,data:any)
  {
    return this.http.get(this.hostAddress+urlExtension+"/"+data);
  }

  private basicGet(urlExtension:string)
  {
    return this.http.get(this.hostAddress+urlExtension);
  }

  private basicPost(urlExtension:string,data : any)
  {
    return this.http.post(this.hostAddress + urlExtension, JSON.stringify(data),{
      headers: this.headers
    });
  }

  getStocks(){
    return this.http.get(this.hostAddress + this.getStocksUrl);
  }

  addStock(stock: Stock)
  {
    return this.http.post(this.hostAddress + this.addStocksUrl,stock);
  }

  buyOrSellStock(urlExtension:string,data:any){
    return this.basicPost(urlExtension,data);
  }

  getUserStocks(username : any)
  {
    return this.basicGetWithParameter(this.getUserStocksUrl,username);
  }

  getStockDetails(id : any)
  {
    return this.basicGetWithParameter(this.getStockDetailsUrl,id);
  }

  tryLogin(username: any)
  {
    return this.basicGetWithParameter(this.loginUrl,username);
  }

  registerUser(user:any)
  {
    return this.basicPost(this.registerUrl, user);
  }

  getTransactions(username:any){
    return this.basicGetWithParameter(this.getTransactionsUrl,username);
  }

  addMoneyToWallet(data:any)
  {
    return this.basicPost(this.addMoneyUrl,data);
  }

  withdrawMoneyFromWallet(data:any)
  {
    return this.basicPost(this.withdrawMoneyUrl,data);
  }

  getUserBalance(username:any)
  {
    return this.basicGetWithParameter(this.getBalanceUrl,username);
  }

  getMarketHours()
  {
    return this.basicGet(this.getMarketHoursUrl);
  }

  updateMarketHours(data:MarketHours)
  {
    return this.basicPost(this.updateMarketHoursUrl,data);
  }

  getScheduledTransactions(username:any){
    return this.basicGetWithParameter(this.getUserScheduledTransactionsUrl,username)
  }

  deleteScheduledTransaction(id: any) {
    return this.basicGetWithParameter(this.deleteScheduledTransactionUrl,id);
  }
}
