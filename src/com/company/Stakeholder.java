package com.company;
//Project requirement 1.
public class Stakeholder {
    //-	Id to identify each stakeholder
    private String Id;
    //-	Name of the stakeholder
    private String Name;
    //-	Address of the stakeholder
    private String Address;
    //-	Balance to keep track of the stakeholder’s balance
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
// list of setters and getters for stakeholder class

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
    //ToString method
    @Override
    public String toString(){
        return"Id= "+ getId()+" Name= "+ getName()+" Address= "+ getAddress()+ " Balance= "+ getBalance();
    }
}
