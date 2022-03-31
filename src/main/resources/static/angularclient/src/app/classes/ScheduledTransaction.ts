export class ScheduledTransaction
{
    id!:bigint;
    isSelling!:boolean;
    desiredPrice!:number;
    desiredUnits!:number;
    expiryDate!:Date;
    companyName!:string;
}
