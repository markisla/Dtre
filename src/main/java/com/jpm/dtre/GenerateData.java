/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpm.dtre;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Generates Data. Will be triggered when application is executed
 *  to pre-generate data
 * 
 * @author Mark Isla
 */
public class GenerateData {
    
    private final String[] entities = {"ALL","JPM","AMC","ABC","ABS","XYZ","JEF","CAR","ZOB","MAB","MAR"};
    private final char[] flags = {'B','S'};
    private final String[] currencies = {"AED","SAR","CNY","PHP","MXP","AUD","JPY","MYR","SGD","IDR"};
    private final double minFx = 0.01;
    private final double maxFx = 0.99;
    private final double minPricePerUnit = 100.01;
    private final double maxPricePerUnit = 999.99;
    private final int startDate = (int) LocalDate.of(2016, 1, 1).toEpochDay();
    private final int endDate = (int) LocalDate.of(2016, 1, 8).toEpochDay();
    private final int numberOfdata;     
    private final Random r = new Random();    

    /**
     * Constructor that sets the number of data to be generated
     * 
     * @param numberOfData number of data to be generated
     */    
    public GenerateData(int numberOfData) {
        this.numberOfdata = numberOfData;
    }

    /**
     * Generate sample instructions for the report
     * 
     * @return List of Instructions
     */
    public List<Instruction> generateData() {
        List <Instruction> ins = new ArrayList<>();
        for(int i=0; i<numberOfdata; i++ ){
            Instruction in = new Instruction();
            in.setEntity(generateEntity());
            in.setFlag(generateFlag());
            in.setAgreedFx(Utility.round(generateAgreedFx(),2));
            in.setCurrency(generateCurrency());
            in.setInstructionDate(generateInstructionDate());
            in.setSettlementDate(generateSettlementDate(in.getCurrency(), in.getInstructionDate()));
            in.setUnits(generateUnits());
            in.setPricePerUnit(Utility.round(generatePricePerUnit(),2));
            ins.add(in);
        }                
        return ins;        
    }
    
    /**
     * Generate random entity based on the given entities
     * 
     * @return entity of type String
     */
    private String generateEntity(){
        return entities[(int)(Math.random() * entities.length)];
    }
    
    /**
     * Generate random flag (Buy or Sell)
     * 
     * @return a character B or S
     */
    private char generateFlag(){
        return flags[(int)(Math.random() * flags.length)];
    }
    
    /**
     * Generate random double from 0.01 to 0.99 for AgreedFx
     * 
     * @return AgreedFx of type double
     */
    private double generateAgreedFx(){
        return minFx + (maxFx - minFx) * r.nextDouble();
    }
    
    /**
     * Generate random currency based on the given currencies
     * 
     * @return currency of type String
     */
    private String generateCurrency(){
        return currencies[(int)(Math.random() * currencies.length)];
    }
    
    /**
     * Generate random instruction date based on a predefined range
     * 
     * @return random instruction date based on a predefined range
     */
    private Date generateInstructionDate(){
        long rDate = startDate + r.nextInt(endDate - startDate);
        LocalDate randomDate = LocalDate.ofEpochDay(rDate);
        return Date.from(randomDate.atStartOfDay(ZoneId.systemDefault()).toInstant());                      
    }
    
    /**
     * Generate random settlement date based on a predefined range
     *  Should be greater than or equal to the 
     *  instruction date previously generated
     * 
     * @param currency the random currency generated
     * @param instructionDate random instruction date generated
     * @return random settlement date based on a predefined range
     */
    private Date generateSettlementDate(String currency, Date instructionDate){
        Date genDate;
        boolean validDate = false;
        
        do{
            long rDate = startDate + r.nextInt(endDate - startDate);
            LocalDate randomDate = LocalDate.ofEpochDay(rDate);
            genDate = Date.from(randomDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            
            if(currency.equalsIgnoreCase("AED") || currency.equalsIgnoreCase("SAR")){
                validDate = (randomDate.getDayOfWeek().compareTo(DayOfWeek.FRIDAY)!=0) 
                        && (randomDate.getDayOfWeek().compareTo(DayOfWeek.SATURDAY)!=0)
                        && !(genDate.compareTo(instructionDate) < 0);
            }
            else{
                validDate = (randomDate.getDayOfWeek().compareTo(DayOfWeek.SATURDAY)!=0) 
                        && (randomDate.getDayOfWeek().compareTo(DayOfWeek.SUNDAY)!=0)
                        && !(genDate.compareTo(instructionDate) < 0);
            }
            
        }while(!validDate);
        
        return genDate;       
    }
    
    /**
     * Generate random units
     * 
     * @return random units of type int
     */
    private int generateUnits(){
        return (int)(Math.floor(Math.random() * ( (10 - 1) + 1) + 1)) * 50;
    }

    /**
     * Generate random price per unit
     * 
     * @return random price per unit of type double
     */
    private double generatePricePerUnit(){
        return minPricePerUnit + (maxPricePerUnit - minPricePerUnit) * r.nextDouble();
    }
    
    
}
