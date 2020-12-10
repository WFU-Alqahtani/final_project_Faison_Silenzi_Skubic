package com.company;

import java.time.LocalDateTime;

public class Transaction {
    //-	Artefact of type Artefact class
    private Artifact Artifact;
    //-	Timestamp of the transaction on the artefact identified with id including the date and time (hint: use LocalDateTime datatype)
    private LocalDateTime timestamp;
    //-	Seller; the seller in this current transaction of type Stakeholder
    private Stakeholder seller;
    //-	Buyer; the buyer in this current transaction of type Stakeholder
    private Stakeholder buyer;
    //-	Price; the price of the artefact in this current transaction
    private Double price;
    //-	AuctionHouse of type Stakeholder
    private Stakeholder auctionHouse;

    //constructors
    public Transaction(){
        Artifact=new Artifact();
        timestamp=null;
        seller=new Stakeholder();
        buyer =new Stakeholder();
        price=0.0;
        auctionHouse=new Stakeholder();
    }
    public Transaction(Artifact Artifact, LocalDateTime timestamp, Stakeholder seller,Stakeholder buyer, Double price, Stakeholder auctionHouse){
        this.Artifact=new Artifact(Artifact);
        this.timestamp= timestamp;
        this.seller= new Stakeholder(seller);
        this.buyer=new Stakeholder(buyer);
        this.price=price;
        this.auctionHouse=new Stakeholder(auctionHouse);
    }
    public Transaction(Transaction tra){
        this.Artifact=new Artifact(tra.getArtifact());
        this.price=tra.getPrice();
        this.buyer=new Stakeholder(tra.getBuyer());
        this.seller=new Stakeholder(tra.getSeller());
        this.timestamp=tra.getTimestamp();
        this.auctionHouse=new Stakeholder(tra.getAuctionHouse());

    }

    public Stakeholder getAuctionHouse() {
        return auctionHouse;
    }

    public void setAuctionHouse(Stakeholder auctionHouse) {
        this.auctionHouse = auctionHouse;
    }

    public Artifact getArtifact() {
        return Artifact;
    }

    public void setArtifact(Artifact Artifact) {
        this.Artifact = Artifact;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Stakeholder getSeller() {
        return seller;
    }

    public void setSeller(Stakeholder seller) {
        this.seller = seller;
    }

    public Stakeholder getBuyer() {
        return buyer;
    }

    public void setBuyer(Stakeholder buyer) {
        this.buyer = buyer;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
//ToString
    @Override
    public String toString() {
        return "Artifact: " + Artifact.toString() + " time stamp: " + timestamp.toString() + " seller: " + seller.toString() + "buyer: " + buyer.toString() + " auctionhouse: " + auctionHouse.toString() + " price: " + price;
    }
    }
