package com.example.dg_bank;

public class Card {
    public String cardNumber, cvv, expiryDate, cardHolderName, cardType;
    public boolean lock;
    Card(String cardNumber, String cvv, String expiryDate, boolean lock, String cardHolderName, String cardType){
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
        this.lock = lock;
        this.cardHolderName = cardHolderName;
        this.cardType = cardType;
    }
}
