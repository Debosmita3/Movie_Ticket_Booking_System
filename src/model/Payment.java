package model;

import interfaces.Payable;

public class Payment implements Payable{

    private double amount;
    private String paymentMode;
    private String status;

    public Payment(double amount,String paymentMode){
        this.amount=amount;
        this.paymentMode=paymentMode;
        this.status="Pending";
    }

    public double getAmount(){
        return this.amount;
    }

    public String paymentMode(){
        return this.paymentMode;
    }
    
    public String getStatus(){
        return this.status;
    }

    @Override
    public void pay(){
        this.status="Paid";
        System.out.println("Payment Successfull!");
    }

}
