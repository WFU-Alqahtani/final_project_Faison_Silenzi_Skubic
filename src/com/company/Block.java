package com.company;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Random;

import static java.nio.charset.StandardCharsets.UTF_8;


public class Block extends Blockchain {
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

    public int RandomNumberGen(){
        SecureRandom rand = new SecureRandom();
        return rand.nextInt();
    }

// mine method that find an hash starting with the prefix required
    public String mine(int prefix){
        int sizeOfPrefix = (""+prefix).length();
        String counterHash = calculateBlockHash();

        String prefixString = new String(new char[prefix]).replace('\0', '0');

        while(!counterHash.substring(0,prefix).equals(prefixString)){
            this.nonce=this.nonce+1;
            counterHash = this.calculateBlockHash();//add nonce
            }
        System.out.println("CounterHash from mine: "+counterHash);
        return counterHash;
    }

//this method implement the agreement between the stakeholders
    public boolean treatySC(Transaction t){
       
        double priceVal = t.getPrice();//sold price of Artifact
        double auctionHouseCurBal = t.getAuctionHouse().getBalance();
        double ArtifactCountryCurBal = t.getArtifact().getCurrent_owner().getBalance();//getOwner instead of getCountry since country is type String not Stakeholder and has no balance
        double ArtifactSellerCurBal = t.getSeller().getBalance();
        
        if (t.getBuyer().getBalance()>=priceVal){
                t.getAuctionHouse().setBalance(auctionHouseCurBal +( .1 * (priceVal)));//10% given to auction house
                t.getArtifact().getCurrent_owner().setBalance(ArtifactCountryCurBal +( .2 * (priceVal)));//20% given to country of origin
                t.getSeller().setBalance(ArtifactSellerCurBal +( .7 * (priceVal)));//70% given to seller
            return true;
        }
        return false;
    }
    
}



