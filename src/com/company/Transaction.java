package com.company;

import java.time.LocalDateTime;

public class Transaction {private Artefact artefact;
    private LocalDateTime timestamp;
    private Stakeholder seller;
    private Stakeholder buyer;
    private Double price;
    private Stakeholder auctionHouse;
    public Transaction(){
        artefact=new Artefact();
        timestamp=null;
        seller=new Stakeholder();
        buyer =new Stakeholder();
        price=0.0;
        auctionHouse=new Stakeholder();
    }
    public Transaction(Artefact artefact, LocalDateTime timestamp, Stakeholder seller,Stakeholder buyer, Double price, Stakeholder auctionHouse){
        this.artefact=new Artefact(artefact);
        this.timestamp= timestamp;
        this.seller= new Stakeholder(seller);
        this.buyer=new Stakeholder(buyer);
        this.price=price;
        this.auctionHouse=new Stakeholder(auctionHouse);
    }
    public Transaction(Transaction tra){
        this.artefact=new Artefact(tra.getArtefact());
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

    public Artefact getArtefact() {
        return artefact;
    }

    public void setArtefact(Artefact artefact) {
        this.artefact = artefact;
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

    @Override
    public String toString() {
        return "Artefact: " + artefact.toString() + " time stamp: " + timestamp.toString() + " seller: " + seller.toString() + "buyer: " + buyer.toString() + " auctionhouse: " + auctionHouse.toString() + " price: " + price;
    }
    }
