package com.company;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Random;

import static java.nio.charset.StandardCharsets.UTF_8;


public class Block {
    private String previousHash;
    private String curHash;
    private Transaction data;
    private long timeStamp;
    private int nonce;

    public Block(){
        previousHash=null;
        curHash=null;
        data=new Transaction();
        timeStamp= 0;
        nonce=0;

    }
    // transaction, current hash, timestamp)


    public Block(Transaction data, String previousHash, long timeStamp){
        this.previousHash=previousHash;
        //this.curHash= calculateBlockHash();// initial hash no zeros

        //somehow Data is throwing a null pointer exception and not accessing the transaction.
        this.data = new Transaction(data);
        this.timeStamp=timeStamp;
        this.nonce = RandomNumberGen();

    }
    public Block(Block blo){
        this.previousHash=blo.getPreviousHash();
        this.nonce=blo.getNonce();
        this.timeStamp=blo.getTimeStamp();
        this.data=new Transaction(blo.getData());
        this.curHash=blo.getCurHash();
    }

    public void setData(Transaction data) {
        this.data = data;
    }

    public Transaction getData() {
        return data;
    }

    public int getNonce() {
        return nonce;
    }

    public void setNonce(int nonce) {
        this.nonce = nonce;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getCurHash() {
        return curHash;
    }

    public void setCurHash(String curHash) {
        this.curHash = curHash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    //calculate blockhash with No Nonce provided
    public String calculateBlockHash() {
        String dataString = this.data.toString();

        String dataToHash = previousHash
                + Long.toString(timeStamp)
                + Integer.toString(nonce)
                + dataString;

        MessageDigest digest = null;
        byte[] bytes = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            bytes = digest.digest(dataToHash.getBytes(UTF_8));
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("UTF_8 is not supported");
        }

        StringBuffer buffer = new StringBuffer();
        for (byte b : bytes) {
            buffer.append(String.format("%02x", b));
        }
        return buffer.toString();
    }

    //calculate blockhash but the nonce has been provided
    public String calculateBlockHash(int nonce) {


        String dataToHash = previousHash
                + Long.toString(timeStamp)
                + Integer.toString(nonce)
                + data.toString();

        MessageDigest digest = null;
        byte[] bytes = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            bytes = digest.digest(dataToHash.getBytes(UTF_8));
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("UTF_8 is not supported");
        }

        StringBuffer buffer = new StringBuffer();
        for (byte b : bytes) {
            buffer.append(String.format("%02x", b));
        }
        return buffer.toString();
    }

//    public String mineBlock(int prefix) {
//        //  if (Treaties(t).fornow()){}
//        String counterHash=null;
//        if (treatySC(this.data)==true) {
//            if (this.curHash != null) {
//                counterHash = this.getCurHash();
//            } else {
//                System.out.println("the hash was null");
//            }
//            SecureRandom secureRandom = new SecureRandom();
//            int randomnumberfornonce = secureRandom.nextInt();
//            while (!counterHash.substring(0, 5).equalsIgnoreCase(Integer.toString(prefix))) {
//                counterHash = calculateBlockHash(randomnumberfornonce);
//                randomnumberfornonce++;
//            }
//            this.setCurHash(counterHash); //check this one
//            return counterHash;
//        }
//        else{
//            System.out.println( "empty hash wrong something");
//
//        }
//    }


    //this method mines the block
    public void mineBlock(int prefix){
        // if method treaty SC returns true then do mine method and set the current hash the value of the mine method.
        if(treatySC(this.data)){
            this.setCurHash(mine(prefix));
        }
        else{
            System.out.println("\nA Transaction Did Not Meet The Stakeholder's Agreement And Was Therefore Removed From The System.\n");
        }
    }
// it just allows to create random numbers
    public int RandomNumberGen(){
        SecureRandom rand = new SecureRandom();
        return rand.nextInt();
    }

// mine method that find an hash starting with the prefix required
    public String mine(int prefix){
        int sizeOfPrefix = (""+prefix).length();
        String counterHash = calculateBlockHash();
       /*
        if(this.curHash!=null){//try and catch
            counterHash = this.curHash;
        }
        else{
            System.out.println("There was an error with the minBlock() method");
        }
        */

//        SecureRandom secureRandom = new SecureRandom();
//        int randomNumForNonce = secureRandom.nextInt();
//        this.nonce = randomNumForNonce;


        String prefixString = new String(new char[prefix]).replace('\0', '0');

        while(!counterHash.substring(0,prefix).equals(prefixString)){
            this.nonce=this.nonce+1;
            counterHash = this.calculateBlockHash();//add nonce
            //System.out.println(counterHash);
        }
        System.out.println("CounterHash from mine: "+counterHash);
        return counterHash;
    }



//this method implement the agreement between the stakeholders
    public boolean treatySC(Transaction t){
        double priceVal = t.getPrice();//sold price of artefact

        //use these values to increase code readability
        double auctionHouseCurBal = t.getAuctionHouse().getBalance();
        double artefactCountryCurBal = t.getArtefact().getCurrent_owner().getBalance();//getOwner instead of getCountry since country is type String not Stakeholder and has no balance
        double artefactSellerCurBal = t.getSeller().getBalance();

        Blockchain blockchainInstance= new Blockchain(); //why this

        //checks for 2 transactions for the artefact *after 2001* and if the buyer can afford the artefact by going to the method twotransactionsperartefact located in the blockchain class
        if (t.getBuyer().getBalance()>=priceVal){
                t.getAuctionHouse().setBalance(auctionHouseCurBal +( .1 * (priceVal)));//10% given to auction house
                t.getArtefact().getCurrent_owner().setBalance(artefactCountryCurBal +( .2 * (priceVal)));//20% given to country of origin
                t.getSeller().setBalance(artefactSellerCurBal +( .7 * (priceVal)));//70% given to seller
            return true;
        }
        return false;
    }








}



