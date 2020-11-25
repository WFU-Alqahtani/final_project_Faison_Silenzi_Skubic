package com.company;

import com.company.Artefact;
import com.company.Block;
import com.company.Stakeholder;
import com.company.Transaction;

import java.time.LocalDateTime;
import java.util.ArrayList;
//for unit testing
import java.time.LocalDateTime;

//important for intregrity to keep Blockchain object as immutable via 'final' and make it abstract
public class Blockchain {


    private final ArrayList<Block> blockchain;
    public final ArrayList<Block> getChain(){
        return blockchain;
    }
    public Blockchain() {
        this.blockchain = new ArrayList<Block>();
    }
    public Blockchain(ArrayList<Block> blockchain) {
        this.blockchain = new ArrayList<Block>();
        for (int i = 0; i < blockchain.size(); i++) {
            this.blockchain.add(blockchain.get(i));
        }
    }
    public Blockchain (Blockchain a){
        this.blockchain= new ArrayList<Block>();
        for (int i =0; i<a.getChain().size(); i++) {
            this.blockchain.add(a.getChain().get(i));
        }

    }

    public ArrayList<Block> retrieveProvenance(String id){
        ArrayList<Block> searchResults= new ArrayList<Block>();
        for (int i=0; i<this.blockchain.size();i++){
            if (id==blockchain.get(i).getData().getArtefact().getId()){
                searchResults.add(blockchain.get(i));
            }
            else{continue;
            }
        }
        return searchResults;
    }
    public ArrayList<Block> retrieveProvenance(String id, long timestamp){
        ArrayList<Block> searchResults= new ArrayList<Block>();
        for (int i=0; i<this.blockchain.size();i++){
            if (id==blockchain.get(i).getData().getArtefact().getId()&& blockchain.get(i).getTimeStamp()<timestamp){
                searchResults.add(blockchain.get(i));
            }
            else{continue;
            }
        }
        return searchResults;
    }

    public boolean twoTransactionsPerArtefact(Artefact a){
        ArrayList<Block> results=new ArrayList<Block>();
        for (int i=0; i< retrieveProvenance(a.getId()).size();i++){
            results.add(i,retrieveProvenance(a.getId()).get(i));
        }
        int count=0;
        for (int i=0; i< results.size();i++){
            if (results.get(i).getTimeStamp()>2001){
                count=count +1;
            }// check if it is year or other way to calculate time
        }
        if ( count >= 2){
            return true;
        }

        else {
            return false;
        }
    }


    public boolean verifyBlockchain(ArrayList<Block> bc,int prefix){
        int sizeOfPrefix = (""+prefix).length();
        if (bc.get(bc.size()-1).getCurHash().equalsIgnoreCase(bc.get(bc.size()-1).mine(prefix))&&
                bc.get(bc.size()-2).getCurHash().equalsIgnoreCase(bc.get(bc.size()-1).getPreviousHash())&&
                bc.get(bc.size()-1).getCurHash().substring(0,sizeOfPrefix).equals(Integer.toString(prefix)))//TODO: check it can be out of bound and ask what if the first 5 0s is the first option
        {
return true;
        }
        else{
            return false;
        }
    }



    public void unitTestBlockChainSingleBlock(){
        Stakeholder owner = new Stakeholder("stakeHolderID", "StakeHolderName","StakeHolderAddress",0.0);
        Artefact a = new Artefact("id","name","address",owner);

        LocalDateTime time = LocalDateTime.now();
        Stakeholder seller = new Stakeholder("stakeHolderID", "StakeHolderName","StakeHolderAddress",0.0);
        Stakeholder buyer = new Stakeholder("stakeHolderID", "StakeHolderName","StakeHolderAddress",0.0);
        Stakeholder auctionHouse = new Stakeholder("stakeHolderID", "StakeHolderName","StakeHolderAddress",0.0);
        double price = 0.0;
        Transaction t = new Transaction(a,time,seller,buyer,price,auctionHouse);

        Block tester = new Block("",t,1,1);

        ArrayList<Block> testArrayList = new ArrayList<>();
        testArrayList = getChain();
        testArrayList.add(tester);
        System.out.println(testArrayList);
    }


}