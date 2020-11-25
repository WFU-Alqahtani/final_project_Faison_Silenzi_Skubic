package com.company;

public class Stakeholder {
    private String Id;
    private String Name;
    private String Address;
    private Double Balance;

    public Stakeholder(){
        Id=null;
        Name=null;
        Address=null;
        Balance=0.0;
    }
    public Stakeholder( String Id, String Name, String Address, Double Balance){
        this.Balance=Balance;
        this.Id=Id;
        this.Name=Name;
        this.Address=Address;
    }
    public Stakeholder(Stakeholder object){
        this.Balance=object.getBalance();
        this.Address=object.getAddress();
        this.Name=object.getName();
        this.Id=object.getId();

    }


    public void setName(String name) {
        Name = name;
    }

    public String getName() {
        return Name;
    }

    public Double getBalance() {
        return Balance;
    }
    public void setBalance(Double Balance){
        this.Balance= Balance;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getAddress() {
        return Address;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
    @Override
    public String toString(){
        return"Id= "+ getId()+" Name= "+ getName()+" Address= "+ getAddress()+ " Balance= "+ getBalance();
    }
}
