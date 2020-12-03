package com.company;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class Main {

    public static void main(String[] args) {




        ArrayList<Block> blockchain = new ArrayList<Block>();
        int prefix = 4;   //we want our hash to start with four zeroes
        String prefixString = new String(new char[prefix]).replace('\0', '0');

        //data1-data3 should be filled by the user with Datatype Transaction
        //    public Transaction(Artefact artefact, LocalDateTime timestamp, Stakeholder seller,Stakeholder buyer, Double price, Stakeholder auctionHouse){

        //TestData for Data1
        Stakeholder owner = new Stakeholder("stakeHolderID", "StakeHolderName","StakeHolderAddress",0.0);
        Artefact a = new Artefact("id","name","address",owner);
        LocalDateTime time = LocalDateTime.now();
        Stakeholder seller = new Stakeholder("stakeHolderID", "StakeHolderName","StakeHolderAddress",0.0);
        Stakeholder buyer = new Stakeholder("stakeHolderID", "StakeHolderName","StakeHolderAddress",0.0);
        Stakeholder auctionHouse = new Stakeholder("stakeHolderID", "StakeHolderName","StakeHolderAddress",0.0);
        double price = 0.0;
        Transaction data1 = new Transaction(a,time,seller,buyer,price,auctionHouse);

        //TestData for Data2
        Stakeholder owner2 = new Stakeholder("stakeHolderID", "StakeHolderName","StakeHolderAddress",0.0);
        Artefact a2 = new Artefact("id","name","address",owner);
        LocalDateTime time2 = LocalDateTime.now();
        Stakeholder seller2 = new Stakeholder("stakeHolderID", "StakeHolderName","StakeHolderAddress",0.0);
        Stakeholder buyer2 = new Stakeholder("stakeHolderID", "StakeHolderName","StakeHolderAddress",0.0);
        Stakeholder auctionHouse2 = new Stakeholder("stakeHolderID", "StakeHolderName","StakeHolderAddress",0.0);
        double price2 = 0.0;
        Transaction data2 = new Transaction(a,time,seller,buyer,price,auctionHouse);

        //TestData for Data 3
        Stakeholder owner3 = new Stakeholder("stakeHolderID", "StakeHolderName","StakeHolderAddress",0.0);
        Artefact a3 = new Artefact("id","name","address",owner);
        LocalDateTime time3 = LocalDateTime.now();
        Stakeholder seller3 = new Stakeholder("stakeHolderID", "StakeHolderName","StakeHolderAddress",0.0);
        Stakeholder buyer3 = new Stakeholder("stakeHolderID", "StakeHolderName","StakeHolderAddress",0.0);
        Stakeholder auctionHouse3 = new Stakeholder("stakeHolderID", "StakeHolderName","StakeHolderAddress",0.0);
        double price3 = 0.0;
        Transaction data3 = new Transaction(a,time,seller,buyer,price,auctionHouse);




        //What do we pass through as the initial hash for the genisis Block?
        //Should it just be 0 -- started with blockchain.get(blockchain.size() - 1).getCurHash();
        Block genesisBlock = new Block(data1, blockchain.get(blockchain.size() - 1).getCurHash(), new Date().getTime());
        //newBlock.mineBlock(prefix); -- from Alqhatani's code

        //Mining the first block and addin it to the chain
        genesisBlock.mineBlock(prefix);

        if (genesisBlock.getCurHash().substring(0, prefix).equals(prefixString) &&  Blockchain.verifyBlockchain(ArrayList<Block> blockchain))
            blockchain.add(genesisBlock);
        else
            System.out.println("Malicious block, not added to the chain");

        //Making the second Block
        Block secondBlock = new Block(data2, blockchain.get(blockchain.size() - 1).getCurHash(),new Date().getTime());

        //Mining the second block
        secondBlock.mineBlock(prefix);
        //Add the second block to the chain if everything is verified
        if (secondBlock.getCurHash().substring(0, prefix).equals(prefixString) &&  Blockchain.verifyBlockchain(ArrayList<Block> blockchain))
            blockchain.add(secondBlock);
        else
            System.out.println("Malicious block, not added to the chain");

        //Make the third block
        Block thirdBlock = new Block(data3,blockchain.get(blockchain.size() - 1).getCurHash(), new Date().getTime());
        //Mine the thirdBlock
        thirdBlock.mineBlock(prefix);

        //If the third block verifies, add it to the list
        if (thirdBlock.getCurHash().substring(0, prefix).equals(prefixString) &&  Blockchain.verifyBlockchain(ArrayList<Block> blockchain))
            blockchain.add(thirdBlock);
        else
            System.out.println("Malicious block, not added to the chain");
    }
}
