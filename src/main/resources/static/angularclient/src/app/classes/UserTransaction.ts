export class UserTransaction {
  stockId!: bigint;
  companyName!:string;
  units!:number;
  stockPrice!:number;
  increase!:boolean;
  isSold!:boolean;
  transactionDate!:Date;
}
