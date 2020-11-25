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
    public Block(String previousHash, Transaction data, long timeStamp, int nonce){
        this.previousHash=previousHash;
        this.curHash= calculateBlockHash();// initial hash no zeros
        this.data=new Transaction(data);
        this.timeStamp=timeStamp;
        this.nonce=nonce;
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

    public String calculateBlockHash() {
        String dataToHash = previousHash
                + Long.toString(timeStamp)
                + Integer.toString(nonce)
                + data.toString();

        MessageDigest digest = null;
        byte[] bytes = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            bytes = digest.digest(dataToHash.getBytes(UTF_8));
            throw new UnsupportedEncodingException("ciao"); //give real error message later
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            System.out.println("The encoding is not supported");
        }

        StringBuffer buffer = new StringBuffer();
        for (byte b : bytes) {
            buffer.append(String.format("%02x", b));
        }
        return buffer.toString();
    }

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
            throw new UnsupportedEncodingException("ciao"); //give real error message later
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            System.out.println("The encoding is not supported");
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

    public void mineBlock(int prefix){
        if(treatySC(this.data)==true){
            this.setCurHash(mine(prefix));
        }
        else{
            System.out.println("The transaction did not meet the stakeholders' agreement.");
        }
    }

    public String mine(int prefix){
        int sizeOfPrefix = (""+prefix).length();
        String counterHash = "";
        if(this.curHash!=null){//try and catch
            counterHash = this.curHash;
        }
        else{
            System.out.println("There was an error with the minBlock() method");
        }

        SecureRandom secureRandom = new SecureRandom();
        int randomNumForNonce = secureRandom.nextInt();
        this.nonce = randomNumForNonce;
// TODO: check if currhash starts with 5 0s
        while(counterHash.substring(0,sizeOfPrefix).equals(Integer.toString(prefix))==false){
            counterHash = this.calculateBlockHash();//add nonce
            this.nonce++;
        }
        return counterHash;
    }


    public boolean treatySC(Transaction t){
        double priceVal = t.getPrice();//sold price of artefact

        //use these values to increase code readability
        double auctionHouseCurBal = t.getAuctionHouse().getBalance();
        double artefactCountryCurBal = t.getArtefact().getCurrent_owner().getBalance();//getOwner instead of getCountry since country is type String not Stakeholder and has no balance
        double artefactSellerCurBal = t.getSeller().getBalance();

        Blockchain blockchainInstance= new Blockchain();
        //checks for 2 transactions for the artefact *after 2001* and if the buyer can afford the artefact
        if(blockchainInstance.twoTransactionsPerArtefact(t.getArtefact())==true && t.getBuyer().getBalance()>=priceVal){
            t.getAuctionHouse().setBalance(auctionHouseCurBal + .1*(priceVal));//10% given to auctionhouse
            t.getArtefact().getCurrent_owner().setBalance(artefactCountryCurBal + .2*(priceVal));//20% given to country of origin
            t.getSeller().setBalance(artefactSellerCurBal + .7*(priceVal));//70% given to seller
            return true;
        }
        return false;
    }








}



