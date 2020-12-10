package com.company;

import com.company.Artifact;
import com.company.Block;
import com.company.Stakeholder;
import com.company.Transaction;

import java.time.LocalDateTime;
import java.util.*;
//for unit testing
import java.time.LocalDateTime;
import java.util.regex.Pattern;

//important for integrity to keep Blockchain object as immutable via 'final' and make it abstract
public class Blockchain{

    private static final ArrayList<Block> blockchain = new ArrayList<>();
    public static ArrayList<Block> getChain(){
        return blockchain;
    }

//what this method does is to retrieve all the transactions in the blockchain for the artifact identified by its ID
    public ArrayList<Block> retrieveProvenance(String id){
        ArrayList<Block> searchResults= new ArrayList<>();
        for ( Block block : blockchain ) {
            if (id.equalsIgnoreCase(block.getData().getArtifact().getId())) searchResults.add(block);
        }
        //return an arraylist with all the transactions of one specific artifact
        return searchResults;
    }

//same method as before but with the difference that now we want a specific time to check
    public ArrayList<Block> retrieveProvenance(String id, long timestamp){
        ArrayList<Block> searchResults= new ArrayList<Block>();
        for ( Block block : blockchain ) {
            if (Objects.equals(id, block.getData().getArtifact().getId()) && block.getTimeStamp() < timestamp) {
                searchResults.add(block);
            }
        }
        return searchResults;
    }
    //check with professor because in class she told us that we do not need to implement this
    public boolean twoTransactionsPerArtifact(Artifact a){
        ArrayList<Block> results= new ArrayList<>();
        for (int i=0; i< retrieveProvenance(a.getId()).size();i++){
            results.add(i,retrieveProvenance(a.getId()).get(i)); 
        }
        //check is the count of artifact after 2001 is bigger than 2
        int count=0;
        for ( Block result : results ) {
            //getData pulls the transaction data from the block.
            int year = result.getData().getTimestamp().getYear();
            if (year > 2001) count = count + 1;
         }
        return count >= 2;
    }


    //verify every block in the blockchain
    //it validates that a blockchain is valid
    public static boolean verifyBlockchain(int prefix) {
        //used the same algorythm as before to play with the prefix
        String prefixString = new String(new char[prefix]).replace('\0', '0');
       //if the blockchain is composed of 0 blocks (it is for the first block)
        if (blockchain.size() == 0) {
            return true;
        }
        //into the four loop in a way that checks for all the previous blocks
        for (int i = 1; i < blockchain.size(); i++) {
            //-	Verify that the stored hash of the current block is actually what it calculates
            if (!blockchain.get(i).getCurHash().equals(blockchain.get(i).calculateBlockHash(blockchain.get(i).getNonce()))) {
                System.out.println("Stored Current Hash != Calculated current hash");
                System.out.println(blockchain.get(i).getCurHash());
                System.out.println(blockchain.get(i).calculateBlockHash());
                return false;
                }
            //-	Verify that the hash of the previous block stored in the current block is the hash of the previous block
            if (!blockchain.get(i-1).getCurHash().equalsIgnoreCase(blockchain.get(i).getPreviousHash())) {
            System.out.println("Previous Hash != Calculated Previous Hash");
            return false;
                }
           // -	Verify that the current block has been mined
            if (!blockchain.get(i).getCurHash().substring(0, prefix).equals(prefixString)) {
                System.out.println("Block has not been mined yet.");
                return false;
                }
            return true;
        }
        return true;
    }


    public static void main(String[] args) {
        //it creates an arraylist called datalist
ArrayList<Transaction> dataList = new ArrayList<>();

        Scanner scnr = new Scanner(System.in); //Scanner for reading user input
        int prefix = 4;   //we want our hash to start with four zeroes
        String prefixString = new String(new char[prefix]).replace('\0', '0');

boolean stillInput = true;
int i = 1;
    //it is going to ask all the info to the user
    while(stillInput) {
        System.out.println("\nEnter Stakeholder ID: ");
        String stakeHolderID = scnr.nextLine();
        if (stakeHolderID.equalsIgnoreCase("")) {
            while (stakeHolderID.equalsIgnoreCase("")) {
                System.out.println("!No Entry Was Made!\nEnter Stakeholder ID: ");
                stakeHolderID = scnr.nextLine();
            }
        }
        System.out.println("Enter Stakeholder Name: ");
        String stakeHolderName = scnr.nextLine();
        if (stakeHolderName.equalsIgnoreCase("")) {
            while (stakeHolderName.equalsIgnoreCase("")) {
                System.out.println("!No Entry Was Made!\nEnter Stakeholder Name: ");
                stakeHolderName = scnr.nextLine();
            }
        }
        System.out.println("Enter Stakeholder Address: ");
        String stakeHolderAddress = scnr.nextLine();
        if (stakeHolderAddress.equalsIgnoreCase("")) {
            while (stakeHolderAddress.equalsIgnoreCase("")) {
                System.out.println("!No Entry Was Made!\nEnter Stakeholder Address: ");
                stakeHolderAddress = scnr.nextLine();
            }
        }
        System.out.println("Enter Buyer Bank Balance (include cents): ");
        double balance= 0.0;
        String input =scnr.nextLine();
        outerloop:try {
           balance = Double.parseDouble(input);
        }catch (NumberFormatException e){
            System.out.println("\n!An Entry With An Incorrect Format Was Made!\nEnter Buyer Bank Balance (include cents): ");
           input =scnr.nextLine();
            if (!Pattern.matches("\\d+\\.\\d\\d(?!\\d)", input)) {
                while (!Pattern.matches("\\d+\\.\\d\\d(?!\\d)", input)) {
                    System.out.println("\n!An Entry With An Incorrect Format Was Made!\nEnter Buyer Bank Balance (include cents): ");
                    input =scnr.nextLine();
                    if (Pattern.matches("\\d+\\.\\d\\d(?!\\d)", input)) {
                        balance = Double.parseDouble(input);
                        break outerloop;
                    }
                }
            }
        }

        scnr.nextLine(); //this resets the scanner cursor to the next line, scnr was skipping lines w/o this, do not delete!!

        //adding user input to Stakeholder class
        Stakeholder owner = new Stakeholder(stakeHolderID, stakeHolderName, stakeHolderAddress, balance);
        Stakeholder seller = new Stakeholder(stakeHolderID, stakeHolderName, stakeHolderAddress, balance);
        Stakeholder buyer = new Stakeholder(stakeHolderID, stakeHolderName, stakeHolderAddress, balance);
        Stakeholder auctionHouse = new Stakeholder(stakeHolderID, stakeHolderName, stakeHolderAddress, balance);

        System.out.println("\nEnter Artifact ID: ");
        String aID = scnr.nextLine();
        if (aID.equalsIgnoreCase("")) {
            while (aID.equalsIgnoreCase("")) {
                System.out.println("!No Entry Was Made!\nEnter Artifact ID: ");
                aID = scnr.nextLine();
            }
        }
            System.out.println("Enter Artifact Name: ");
            String aName = scnr.nextLine();
            if (aName.equalsIgnoreCase("")) {
                while (aName.equalsIgnoreCase("")) {
                    System.out.println("!No Entry Was Made!\nEnter Artifact Name: ");
                    aName = scnr.nextLine();
                }
            }
                System.out.println("Enter Artifact Country of Origin: ");
                String aOrigin = scnr.nextLine();
                if (aOrigin.equalsIgnoreCase("")) {
                    while (aOrigin.equalsIgnoreCase("")) {
                        System.out.println("!No Entry Was Made!\nEnter Artifact Origin: ");
                        aOrigin = scnr.nextLine();
                    }
                }

                System.out.println("Enter Artifact Price (include cents): ");
                String input1 = scnr.nextLine();
                Double price = 0.0;
        outerloop:try {
            price = Double.parseDouble(input1);
        }catch (NumberFormatException e){
            System.out.println("\n!An Entry With An Incorrect Format Was Made!\nEnter Artifact Price (include cents): ");
            input1 =scnr.nextLine();
            if (!Pattern.matches("\\d+\\.\\d\\d(?!\\d)", input1)) {
                while (!Pattern.matches("\\d+\\.\\d\\d(?!\\d)", input1)) {
                    System.out.println("\n!An Entry With An Incorrect Format Was Made!\nEnter Artifact Price (include cents): ");
                    input1 =scnr.nextLine();
                    if (Pattern.matches("\\d+\\.\\d\\d(?!\\d)", input1)) {
                       price = Double.parseDouble(input1);
                        break outerloop;
                    }
                }
            }
        }

        scnr.nextLine();        //this resets the scanner cursor to the next line, scnr was skipping lines w/o this, do not delete!!

        //adding all input info to classes and data to the ArrayList of data
        Artifact a = new Artifact(aID, aName, aOrigin, owner);
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

//looping through user input arraylist to verify data and create blocks
                for ( i = 0; i < dataList.size(); i++ ) {
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
                        }
                    } else {
                        Block block = new Block(dataList.get(i), blockchain.get(blockchain.size() - 1).getCurHash(), new Date().getTime());
                        //Mining block and adding it to the chain
                        block.mineBlock(prefix);
                        try {
                            if (block.getCurHash().substring(0, prefix).equals(prefixString) && verifyBlockchain(prefix)) {
                                blockchain.add(block);
                                System.out.println("Block found and added");
                            } else
                                System.out.println("Malicious block. Block not added to the chain");
                        } catch (NullPointerException ignore) {
                            block.setCurHash(block.getPreviousHash());
                            blockchain.remove(block);
                        }
                    }
                }
                //end of main
            }

//end of class
}