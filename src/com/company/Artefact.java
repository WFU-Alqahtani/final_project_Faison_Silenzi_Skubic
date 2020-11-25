package com.company;

public class Artefact {
    private String Id;
    private String Name;
    private String Country_of_Origin;
    private Stakeholder Current_owner;

    public Artefact(){
        Id=null;
        Name=null;
        Country_of_Origin=null;
        Current_owner=new Stakeholder();
    }
    public Artefact( String Id, String Name, String Country_of_Origin, Stakeholder Current_owner){
        this.Id=Id;
        this.Name=Name;
        this.Country_of_Origin=Country_of_Origin;
        this.Current_owner= new Stakeholder(Current_owner);
    }
    public Artefact(Artefact object){
        this.Id=object.getId();
        this.Name=object.getName();
        this.Country_of_Origin=object.getCountry_of_Origin();
        this.Current_owner=new Stakeholder(object.Current_owner);
    }

    public void setId(String id) {
        Id = id;
    }

    public String getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Stakeholder getCurrent_owner() {
        return Current_owner;
    }

    public void setCurrent_owner(Stakeholder current_owner) {
        Current_owner = current_owner;
    }

    public String getCountry_of_Origin() {
        return Country_of_Origin;
    }

    public void setCountry_of_Origin(String country_of_Origin) {
        Country_of_Origin = country_of_Origin;
    }
    @Override
    public String toString()

    {
        return "Id= " + getId() + " Name= " + getName() + " Country of Origin= " + getCountry_of_Origin() + " Current Owner= " + getCurrent_owner();
    }


}
