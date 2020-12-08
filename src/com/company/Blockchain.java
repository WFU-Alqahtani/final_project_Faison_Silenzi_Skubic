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
import java.util.Objects;
import java.util.Scanner;

//important for integrity to keep Blockchain object as immutable via 'final' and make it abstract
public class Blockchain {

    private static final ArrayList<Block> blockchain = new ArrayList<>();
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
        }
        //return an arraylist with all the transactions of one specific artefact
        return searchResults;
    }

//same method as before but with the difference that now we want a specific time to check
    public ArrayList<Block> retrieveProvenance(String id, long timestamp){
        ArrayList<Block> searchResults= new ArrayList<Block>();
        for ( Block block : blockchain ) {
            // TODO check that timestamp works ex... year or month or other way to express time stamp
            if (Objects.equals(id, block.getData().getArtefact().getId()) && block.getTimeStamp() < timestamp) {
                searchResults.add(block);
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
        for ( Block result : results ) {
            //TODO: Verify that the year works?
            //getData pulls the transaction data from the block.
            int year = result.getData().getTimestamp().getYear();
            if (year > 2001) count = count + 1; //TODO: check if it is year or other way to calculate time
         }
        return count >= 2;
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
            //for why
        //TODO: Modify this so that it calls the blockchain class itself rather than an ArrayList (BC) that doesn't exist

        //going inside the class, inside the blockchain arrayList, to the last Block in the arraylist, to get the current hash
        //then comparing it (ignoring case) by using the mine method (which doesn't actually mine but calculates the hash).

        //separate this out into 3 if statements; if any of them break, return false;
        for (int i = 1; i < blockchain.size(); i++) {
                int j = i;
            if (!blockchain.get(j).getCurHash().equals(blockchain.get(j).calculateBlockHash())) {
                System.out.println("Stored current != calculated current hash");
                System.out.println(blockchain.get(i).getCurHash());
                System.out.println(blockchain.get(i).calculateBlockHash());


                return false;
            }
        if (!blockchain.get(j-1).getCurHash().equalsIgnoreCase(blockchain.get(j).getPreviousHash())) {
            System.out.println("previous hash != calculated previous hash");
            return false;
            }


            if (!blockchain.get(j).getCurHash().substring(0, prefix).equals(prefixString)) {
                System.out.println("Block has not been mined yet.");
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

boolean stillInput = true;
boolean flag = true;
int i = 1;

    while(stillInput) {
        System.out.println("Enter Stakeholder ID: ");
        String stakeHolderID = scnr.nextLine();
        System.out.println("Enter Stakeholder Name: ");
        String stakeHolderName = scnr.nextLine();
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
        String aName = scnr.nextLine();
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
        if (i == 1)
            System.out.println("\n" + i + " Transaction Added to System.\n");
        else
            System.out.println("\n" + i + " Transactions Added to System.\n");
        i++;

        System.out.println("\n Add Another Stakeholder? Y/N");
            String answer = scnr.nextLine();
            if (!answer.equalsIgnoreCase("y")) stillInput = false; //break if user wants to stop entering data
    }




//looping twice through the to verify the blocks, accesses the ArrayList<Transaction> to get the data for each stakeholder
    for (i=0;i< dataList.size();i++) {
        if (i == 0) {
            Block genesisBlock = new Block(dataList.get(0), "0", new Date().getTime());
            genesisBlock.mineBlock(prefix);
            try {
                if (genesisBlock.getCurHash().substring(0, prefix).equals(prefixString) && verifyBlockchain(prefix)) {
                    blockchain.add(genesisBlock);
                    System.out.println("Block found and added");
                } else
                    System.out.println("Malicious block. Block not added to the chain");
            } catch (NullPointerException ignore) {
                dataList.add(0, dataList.get(1)); //invalid genesis transaction data gets overwritten by a copy of next data entry (only works if >1 entry exists)
                dataList.remove(1); //remove original copy of next data (list is now .size()-1)
                i--; //i reset to 0 to check new genesis block in new iteration
                System.out.println(dataList);
            }
        }
    else {
        Block block = new Block(dataList.get(i), blockchain.get(blockchain.size() - 1).getCurHash(), new Date().getTime());

        //Mining block and adding it to the chain
        block.mineBlock(prefix);
        try {
            if (block.getCurHash().substring(0, prefix).equals(prefixString) && verifyBlockchain(prefix)) {
                blockchain.add(block);      //all blocks will be under the same variable name, maybe good for security?? may also be annoying for us idk
                System.out.println("Block found and added");
                }else
                    System.out.println("Malicious block. Block not added to the chain");
            }catch (NullPointerException ignore) {
                block.setCurHash(block.getPreviousHash());
                blockchain.remove(block);
            }
        }
    }


/*
!! throws NullPointerException when stakeholder balance < artefact price
        ^^ problem was not in the comparison of the balance and the price, problem was when the buyer was found to not have enough for the artefact,
            the currHash would not be set to anything new so it would get reset to its default declaration of null on line 21 of Block class
        to fix-> since transaction is not valid, throw out entire block form blockchain by setting currHash to prevHash (maybe?, unsure if this is correct)
            -M

            **fixed with removing invalid block and resetting currHash (see line 221 and 222)
 */



    //end of main
    }
//end of class
}