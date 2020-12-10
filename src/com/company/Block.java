package com.company;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Random;

import static java.nio.charset.StandardCharsets.UTF_8;


public class Block extends Blockchain {
    //-	Hash of the previous block, an important part to build the blockchain
    private String previousHash;
    //-	The hash of this block, calculated based on other data
    private String curHash;
    //-	The actual data, any information having value, like a banking, real estate transaction. In this project, we will keep track of the provenance of artefacts so data should be of type Transaction.
    private Transaction data;
    //-	The timestamp of the creation of this block
    private long timeStamp;
    //-	A nonce, which is an arbitrary (i.e. random) number used in cryptography
    private int nonce;

    public Block(){
        previousHash=null;
        curHash=null;
        data=new Transaction();
        timeStamp= 0;
        nonce=0;
    }
//this is the only constructor called so focus on this one
    public Block(Transaction data, String previousHash, long timeStamp){
        this.previousHash=previousHash;
        this.data = new Transaction(data);
        this.timeStamp=timeStamp;
        //allow for random number to be calculated
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
    //the code below does the following:
    //-	We concatenate different parts of the block to generate a hash from
    //-	We get an instance of the SHA-256 hash function from MessageDigest
    //-	We generate the hash value of our input data, which is a byte array
    //-	We transform the byte array into a hexadecimal string, a hash is typically represented as a 32-digit hex number
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
    //it accepts the defined prefix that we desire to find
    public void mineBlock(int prefix){
    // if method treaty SC returns true then do mine method and set the current hash the value of the mine method.
        if(treatySC(this.data)){
            //call the method mine and set current hash to the one with the prefix desired
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
        //we call here the method calculateBlockHash
        String counterHash = calculateBlockHash();
        //it sets the prefix
        String prefixString = new String(new char[prefix]).replace('\0', '0');
        //-	Check whether you've found the solution
        //-	If not, increment the nonce and calculate the hash in a loop
        //-	The loop goes on until you hit the jackpot-	The loop goes on until you hit the jackpot
        while(!counterHash.substring(0,prefix).equals(prefixString)){
            this.nonce=this.nonce+1;
            counterHash = this.calculateBlockHash();//add nonce
            }
        System.out.println("CounterHash from mine: "+counterHash);
        //it returns the counter hash
        return counterHash;
    }

//this method implement the agreement between the stakeholders
    public boolean treatySC(Transaction t){
       
        double priceVal = t.getPrice();//sold price of Artifact
        double auctionHouseCurBal = t.getAuctionHouse().getBalance();
        double ArtifactCountryCurBal = t.getArtifact().getCurrent_owner().getBalance();//getOwner instead of getCountry since country is type String not Stakeholder and has no balance
        double ArtifactSellerCurBal = t.getSeller().getBalance();
        //	10% are given to the auction house
        //	20% are given to the country of origin of the sold artefact
        //	70% are given to the seller
        //if statement if buyer can afford it
        if (t.getBuyer().getBalance()>=priceVal){
                t.getAuctionHouse().setBalance(auctionHouseCurBal +( .1 * (priceVal)));//10% given to auction house
                t.getArtifact().getCurrent_owner().setBalance(ArtifactCountryCurBal +( .2 * (priceVal)));//20% given to country of origin
                t.getSeller().setBalance(ArtifactSellerCurBal +( .7 * (priceVal)));//70% given to seller
            return true;
        }
        return false;
    }
    
}



