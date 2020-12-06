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
import java.util.Scanner;

//important for integrity to keep Blockchain object as immutable via 'final' and make it abstract
public class Blockchain {

    private static ArrayList<Block> blockchain = new ArrayList<>();
    public static ArrayList<Block> getChain(){
        return blockchain;
    }


    //remove all constructors

    //Returns an arraylist that does something??
    //what this method does is to retrieve all the transactions in the blockchain for the artefact identified by its ID
    public ArrayList<Block> retrieveProvenance(String id){
        ArrayList<Block> searchResults= new ArrayList<>();
        for ( Block block : blockchain ) {
            if (id.equalsIgnoreCase(block.getData().getArtefact().getId())) searchResults.add(block);
            else continue;
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
        ArrayList<Block> results= new ArrayList<>();
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
    public static boolean verifyBlockchain(int prefix) {
        String prefixString = new String(new char[prefix]).replace('\0', '0');

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
        for (int i = 1; i < blockchain.size(); i++) {

            if (!blockchain.get(i).getCurHash().equals(blockchain.get(i).calculateBlockHash())) {
                System.out.println("Stored current != calculated current hash");
                System.out.println(blockchain.get(i).getCurHash());
                System.out.println(blockchain.get(i).calculateBlockHash());


                return false;
            }
        if (!blockchain.get(i-1).getCurHash().equalsIgnoreCase(blockchain.get(i).getPreviousHash())) {
            System.out.println("previous hash != calculated previous hash");
            return false;
            }


            if (!blockchain.get(i).getCurHash().substring(0, prefix).equals(prefixString)) {
                System.out.println("Block has not been mined yet. Third ifStmt");
                return false;
            }
            return true;

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
ArrayList<Transaction> dataList = new ArrayList<>();

Scanner scnr = new Scanner(System.in); //Scanner for reading user input
        int prefix = 4;   //we want our hash to start with four zeroes
        String prefixString = new String(new char[prefix]).replace('\0', '0');

        //data1-data3 should be filled by the user with Datatype Transaction
        //    public Transaction(Artefact artefact, LocalDateTime timestamp, Stakeholder seller,Stakeholder buyer, Double price, Stakeholder auctionHouse){

//reading user input for first 3 blocks
    for(int i=1; i<4; i++) {
        System.out.println("Enter Stakeholder ID: ");
            String stakeHolderID= scnr.nextLine();
        System.out.println("Enter Stakeholder Name: ");
        String stakeHolderName=scnr.nextLine();
        System.out.println("Enter Stakeholder Address: ");
        String stakeHolderAddress = scnr.nextLine();
        System.out.println("Enter Stakeholder Bank Balance (include cents): ");
        double balance;
        balance = scnr.nextDouble();
        scnr.nextLine();    //this resets the scanner cursor to the next line, scnr was skipping lines w/o this, do not delete!!

    //adding user input to Stakeholder class
        Stakeholder owner = new Stakeholder(stakeHolderID, stakeHolderName, stakeHolderAddress, balance);
        Stakeholder seller = new Stakeholder(stakeHolderID, stakeHolderName, stakeHolderAddress, balance);
        Stakeholder buyer = new Stakeholder(stakeHolderID, stakeHolderName, stakeHolderAddress, balance);
        Stakeholder auctionHouse = new Stakeholder(stakeHolderID, stakeHolderName, stakeHolderAddress, balance);

        System.out.println("Enter Artefact ID: ");
        String aID = scnr.nextLine();
        System.out.println("Enter Artefact Name: ");
        String aName= scnr.nextLine();
        System.out.println("Enter Artefact Country of Origin: ");
        String aOrigin = scnr.nextLine();
        System.out.println("Enter Artefact Price (include cents): ");
        Double price = scnr.nextDouble();
        scnr.nextLine();        //this resets the scanner cursor to the next line, scnr was skipping lines w/o this, do not delete!!

    //adding all input info to classes and data to the ArrayList of data
        Artefact a = new Artefact(aID, aName, aOrigin, owner);
        LocalDateTime time = LocalDateTime.now();
        Transaction data = new Transaction(a, time, seller, buyer, price, auctionHouse);
        dataList.add(data);

   //confirmation dialogue that data was successfully added
        if(i==1)
            System.out.println("\n"+i +" Transaction Added to System.\n");
        else
            System.out.println("\n"+i+" Transactions Added to System.\n");
    }

//        //TestData for Data2
//        Stakeholder owner2 = new Stakeholder("stakeHolderID", "StakeHolderName","StakeHolderAddress",0.0);
//        Artefact a2 = new Artefact("id","name","address",owner2);
//        LocalDateTime time2 = LocalDateTime.now();
//        Stakeholder seller2 = new Stakeholder("stakeHolderID", "StakeHolderName","StakeHolderAddress",0.0);
//        Stakeholder buyer2 = new Stakeholder("stakeHolderID", "StakeHolderName","StakeHolderAddress",0.0);
//        Stakeholder auctionHouse2 = new Stakeholder("stakeHolderID", "StakeHolderName","StakeHolderAddress",0.0);
//        double price2 = 0.0;
//        Transaction data2 = new Transaction(a2,time2,seller2,buyer2,price2,auctionHouse2);
//
//        //TestData for Data 3
//        Stakeholder owner3 = new Stakeholder("stakeHolderID", "StakeHolderName","StakeHolderAddress",0.0);
//        Artefact a3 = new Artefact("id","name","address",owner3);
//        LocalDateTime time3 = LocalDateTime.now();
//        Stakeholder seller3 = new Stakeholder("stakeHolderID", "StakeHolderName","StakeHolderAddress",0.0);
//        Stakeholder buyer3 = new Stakeholder("stakeHolderID", "StakeHolderName","StakeHolderAddress",0.0);
//        Stakeholder auctionHouse3 = new Stakeholder("stakeHolderID", "StakeHolderName","StakeHolderAddress",0.0);
//        double price3 = 0.0;
//        Transaction data3 = new Transaction(a3,time3,seller3,buyer3,price3,auctionHouse3);


//genesisBlock needed separate code since it doesn't have a previous hash and I didn't wanna nest an if statement in the for loop below
        Block genesisBlock = new Block(dataList.get(0), "0", new Date().getTime());

        //Mining the first block and adding it to the chain
        genesisBlock.mineBlock(prefix);

        if (genesisBlock.getCurHash().substring(0, prefix).equals(prefixString) && verifyBlockchain(prefix)) {
            blockchain.add(genesisBlock);
            System.out.println("Block found and added");
        } else
            System.out.println("Malicious block. Block not added to the chain");

//looping twice through the to verify the blocks, accesses the ArrayList<Transaction> to get the data for each stakeholder
    for (int i=1;i<3;i++) {
            Block block = new Block(dataList.get(i),blockchain.get(blockchain.size() - 1).getCurHash(), new Date().getTime());
            //newBlock.mineBlock(prefix); -- from Alqhatani's code

            //Mining block and adding it to the chain
            block.mineBlock(prefix);

            if (block.getCurHash().substring(0, prefix).equals(prefixString) && verifyBlockchain(prefix)) {
                blockchain.add(block);      //all blocks will be under the same variable name, maybe good for security?? may also be annoying for us idk
                System.out.println("Block found and added");
            } else
                System.out.println("Malicious block. Block not added to the chain");
        }

//FIXME throws NullPointerException when stakeholder < artefact price

//        //Making the second Block
//        Block secondBlock = new Block(data2, blockchain.get(blockchain.size() - 1).getCurHash(),new Date().getTime());
//
//        //Mining the second block
//        secondBlock.mineBlock(prefix);
//        System.out.println("this is the nonce of the second block:="+secondBlock.getNonce());
//        //Add the second block to the chain if everything is verified
//        if (secondBlock.getCurHash().substring(0, prefix).equals(prefixString) &&  verifyBlockchain(prefix)) {
//            blockchain.add(secondBlock);
//            System.out.println("Second block found and added");
//        }
//        else {
//            System.out.println("Malicious block. Second block not added to the chain");
//            System.out.println("first condition: "+secondBlock.getCurHash().substring(0, prefix).equals(prefixString));
//            System.out.println("second condition: "+verifyBlockchain(prefix));
//        }
//
//
//        //Make the third block
//        Block thirdBlock = new Block(data3,blockchain.get(blockchain.size() - 1).getCurHash(), new Date().getTime());
//        //Mine the thirdBlock
//        thirdBlock.mineBlock(prefix);
//
//        //If the third block verifies, add it to the list
//        if (thirdBlock.getCurHash().substring(0, prefix).equals(prefixString) &&  verifyBlockchain(prefix)) {
//            blockchain.add(thirdBlock);
//            System.out.println("Third block found and added");
//        }
//        else
//            System.out.println("Malicious block. Third block not added to the chain");


    //end of main
    }
//end of class
}