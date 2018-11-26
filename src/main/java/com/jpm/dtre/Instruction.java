/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpm.dtre;

import java.util.Date;

/**
 * Defines the structure of a trade instruction
 * Getters and setters method are also defined.
 * 
 * @author Mark Isla
 */
public class Instruction {
    
    /**
     * A financial entity whose shares are to be bought or sold
     */
    private String entity;
    
    /**
     * Single character that can be B for buy, or S for sell
     */
    private char flag;
    
    /**
     * Foreign exchange rate with respect to USD that was agreed
     */
    private double agreedFx;
    
    /**
     * Currency of the instruction
     */
    private String currency;
    
    /**
     * Date on which the instruction was sent to JP Morgan by various clients
     */
    private Date instructionDate;
    
    /**
     * Date on which the client wished for the instruction to be settled with respect to Instruction Date
     */
    private Date settlementDate;
    
    /**
     * Number of units bought or sold 
     */
    private int units;
    
    /**
     * Price of 1 unit sold
     */
    private double pricePerUnit;
    
    /**
     * Trade amount computed as 
     *  Price Per Unit * Units * AgreedFx
     */    
    private double amount;

    /* getters and setters */
    
    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public char getFlag() {
        return flag;
    }

    public void setFlag(char flag) {
        this.flag = flag;
    }

    public double getAgreedFx() {
        return agreedFx;
    }

    public void setAgreedFx(double agreedFx) {
        this.agreedFx = agreedFx;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Date getInstructionDate() {
        return instructionDate;
    }

    public void setInstructionDate(Date instructionDate) {
        this.instructionDate = instructionDate;
    }

    public Date getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(Date settlementDate) {
        this.settlementDate = settlementDate;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }
    
    public double getAmount(){
        return pricePerUnit * units * agreedFx;
    }
    
}
