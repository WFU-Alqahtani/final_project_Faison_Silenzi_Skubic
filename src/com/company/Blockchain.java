package com.company;

import com.company.Artefact;
import com.company.Block;
import com.company.Stakeholder;
import com.company.Transaction;

import java.time.LocalDateTime;
import java.util.ArrayList;
//for unit testing
import java.time.LocalDateTime;
import java.util.Date;

//important for integrity to keep Blockchain object as immutable via 'final' and make it abstract
public class Blockchain {

    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    public static ArrayList<Block> getChain(){
        return blockchain;
    }


    //remove all constructors

    //Returns an arraylist that does something??
    //what this method does is to retrieve all the transactions in the blockchain for the artefact identified by its ID
    public ArrayList<Block> retrieveProvenance(String id){
        ArrayList<Block> searchResults= new ArrayList<Block>();
        for (int i=0; i<this.blockchain.size();i++){

            //TODO id is a string so it should .equalsto and not ==
            if (id.equalsIgnoreCase(blockchain.get(i).getData().getArtefact().getId())){
                searchResults.add(blockchain.get(i));
            }
            else{continue;
            }
        }
        //return an arraylist with all the transactions of one specific artefact
        return searchResults;
    }

//same method as before but with the difference that now we want a specific time to check
    public ArrayList<Block> retrieveProvenance(String id, long timestamp){
        ArrayList<Block> searchResults= new ArrayList<Block>();
        for (int i=0; i<this.blockchain.size();i++){
            // TODO check that timestamp works ex... year or month or other way to express time stamp
            if (id==blockchain.get(i).getData().getArtefact().getId()&& blockchain.get(i).getTimeStamp()<timestamp){
                searchResults.add(blockchain.get(i));
            }
            else{continue;
            }
        }
        return searchResults;
    }
//check all the difference conditions for treatySC so it uses different methods, shown before that allow to check for different conditions
    public boolean twoTransactionsPerArtefact(Artefact a){
        ArrayList<Block> results=new ArrayList<Block>();
        for (int i=0; i< retrieveProvenance(a.getId()).size();i++){
            results.add(i,retrieveProvenance(a.getId()).get(i));
        }
        //check is the count of artefact after 2001 is bigger than 2
        int count=0;
        for (int i=0; i< results.size();i++){

            //TODO: Verify that the year works?
            //getData pulls the transaction data from the block.
            int year = results.get(i).getData().getTimestamp().getYear();

            if (year>2001){
                count=count +1;
            }//TODO: check if it is year or other way to calculate time
        }
        if ( count >= 2){
            return true;
        }

        else {
            return false;
        }
    }


    // Figure out what the issue is with Static references; when we make this static, we can't reference anything with "this"
    //verify every block in the blockchain
    public static boolean verifyBlockchain(int prefix){
        int sizeOfPrefix = (""+prefix).length();

        //Blockchain method Size() needs to be made to return int that shows the size of the BC
        //getCurHash is preformed on a Block datatype and returns the current hash of a block

        if (blockchain.size() == 0) {
            //just add the first block bc it's the genesis block
            return true;
        }

            //TODO: Make a for loop starting at 1 and wrap the if statement in it.

            //TODO: Modify this so that it calls the blockchain class itself rather than an ArrayList (BC) that doesn't exist

            //going inside the class, inside the blockchain arrayList, to the last Block in the arraylist, to get the current hash
            //then comparing it (ignoring case) by using the mine method (which doesn't actually mine but calculates the hash).

            //separate this out into 3 if statements; if any of them break, return false;
        System.out.println("CurHash: "+blockchain.get(blockchain.size()-1).getCurHash());
        System.out.println("Calculate1: "+blockchain.get(blockchain.size()-1).calculateBlockHash());
        System.out.println("Calculate2: "+blockchain.get(blockchain.size()-1).calculateBlockHash());
        System.out.println(blockchain.get(blockchain.size()-1).getNonce());

        if (!blockchain.get(blockchain.size()-1).getCurHash().equalsIgnoreCase(blockchain.get(blockchain.size()-1).calculateBlockHash())) {
            System.out.println("Stored current != calculated current hash");
            return false;
        }
        if (!blockchain.get(blockchain.size()-2).getCurHash().equalsIgnoreCase(blockchain.get(blockchain.size()-1).getPreviousHash())) {
            System.out.println("previous hash != calculated previous hash");
            return false;

        }
        if (!blockchain.get(blockchain.size()-1).getCurHash().substring(0,sizeOfPrefix).equals(Integer.toString(prefix))){
            System.out.println("Block has not been mined yet. Third ifStmt");
            return false;
        }

        return true;
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


    }

    public static void main(String[] args) {
        //Main method copied over from Main.java


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

        System.out.println(data1);

        //TestData for Data2
        Stakeholder owner2 = new Stakeholder("stakeHolderID", "StakeHolderName","StakeHolderAddress",0.0);
        Artefact a2 = new Artefact("id","name","address",owner2);
        LocalDateTime time2 = LocalDateTime.now();
        Stakeholder seller2 = new Stakeholder("stakeHolderID", "StakeHolderName","StakeHolderAddress",0.0);
        Stakeholder buyer2 = new Stakeholder("stakeHolderID", "StakeHolderName","StakeHolderAddress",0.0);
        Stakeholder auctionHouse2 = new Stakeholder("stakeHolderID", "StakeHolderName","StakeHolderAddress",0.0);
        double price2 = 0.0;
        Transaction data2 = new Transaction(a2,time2,seller2,buyer2,price2,auctionHouse2);

        //TestData for Data 3
        Stakeholder owner3 = new Stakeholder("stakeHolderID", "StakeHolderName","StakeHolderAddress",0.0);
        Artefact a3 = new Artefact("id","name","address",owner3);
        LocalDateTime time3 = LocalDateTime.now();
        Stakeholder seller3 = new Stakeholder("stakeHolderID", "StakeHolderName","StakeHolderAddress",0.0);
        Stakeholder buyer3 = new Stakeholder("stakeHolderID", "StakeHolderName","StakeHolderAddress",0.0);
        Stakeholder auctionHouse3 = new Stakeholder("stakeHolderID", "StakeHolderName","StakeHolderAddress",0.0);
        double price3 = 0.0;
        Transaction data3 = new Transaction(a3,time3,seller3,buyer3,price3,auctionHouse3);



        Blockchain aa = new Blockchain();
        aa.verifyBlockchain(7);


        //What do we pass through as the initial hash for the genisis Block?
        //Should it just be 0 -- started with blockchain.get(blockchain.size() - 1).getCurHash();
        //Make it 0
        Block genesisBlock = new Block(data1,"0", new Date().getTime());
        //newBlock.mineBlock(prefix); -- from Alqhatani's code

        //Mining the first block and addin it to the chain
        genesisBlock.mineBlock(prefix);

        if (genesisBlock.getCurHash().substring(0, prefix).equals(prefixString) && verifyBlockchain(prefix)){
            blockchain.add(genesisBlock);
            System.out.println("Genesis Block found and added");}
        else
            System.out.println("Malicious block. Genesis block not added to the chain");

        //Making the second Block
        Block secondBlock = new Block(data2, blockchain.get(blockchain.size() - 1).getCurHash(),new Date().getTime());

        //Mining the second block
        secondBlock.mineBlock(prefix);
        //Add the second block to the chain if everything is verified
        if (secondBlock.getCurHash().substring(0, prefix).equals(prefixString) &&  verifyBlockchain(prefix)) {
            blockchain.add(secondBlock);
            System.out.println("Second block found and added");
        }
        else
            System.out.println("Malicious block. Second block not added to the chain");

        //Make the third block
        Block thirdBlock = new Block(data3,blockchain.get(blockchain.size() - 1).getCurHash(), new Date().getTime());
        //Mine the thirdBlock
        thirdBlock.mineBlock(prefix);

        //If the third block verifies, add it to the list
        if (thirdBlock.getCurHash().substring(0, prefix).equals(prefixString) &&  verifyBlockchain(prefix)) {
            blockchain.add(thirdBlock);
            System.out.println("Third block found and added");
        }
        else
            System.out.println("Malicious block. Third block not added to the chain");


    //end of main
    }
//end of class
}