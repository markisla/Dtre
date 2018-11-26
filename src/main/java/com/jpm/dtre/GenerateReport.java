/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpm.dtre;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Generates/shows report
 * 
 * @author Mark Isla
 */
public class GenerateReport {

    private final List <Instruction> instructions;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
    private final DecimalFormat df = new DecimalFormat("#,###.00");
    private final int padding = 15;
    
    /**
     * Constructor that sets the list of instructions
     * 
     * @param instructions list of instructions generated
     */    
    public GenerateReport(List <Instruction> instructions) {
        this.instructions = instructions;
    }
    
    /**
     * Prepares the report in a string builder
     * 
     * @return string containing the formatted report
     */
    public String genReport(){
        StringBuilder rep = new StringBuilder();
        
        rep.append("\nAmount in USD settled incoming everyday");
        rep.append("\nDate\t\t\t|\tUSD Amount");
        getUsdAmountPerDay('S').forEach((k,v)->
                rep.append("\n").append(sdf.format(k))
                .append("\t\t| ").append(Utility.leftPad(df.format(Utility.round(v,2)),padding)));
        
        rep.append("\n");
        
        rep.append("\nAmount in USD settled outgoing everyday");
        rep.append("\nDate\t\t\t|\tUSD Amount");
        getUsdAmountPerDay('B').forEach((k,v)->
                rep.append("\n").append(sdf.format(k))
                .append("\t\t| ").append(Utility.leftPad(df.format(Utility.round(v,2)),padding)));
        
        rep.append("\n");
        
        rep.append("\nRank of Incoming Entities");
        rep.append("\nEntity\t\t|\tUSD Amount");
        getRank('S').forEach(instruction -> 
                rep.append("\n").append(instruction.getEntity())
                .append("\t\t| ").append(Utility.leftPad(df.format(Utility.round(instruction.getAmount(),2)),padding)));
        
        rep.append("\n");
        
        rep.append("\nRank of Outgoing Entities");
        rep.append("\nEntity\t\t|\tUSD Amount");
        getRank('B').forEach(instruction -> 
                rep.append("\n").append(instruction.getEntity())
                .append("\t\t| ").append(Utility.leftPad(df.format(Utility.round(instruction.getAmount(),2)),padding)));        
        
        return rep.toString();
    }   
    
    /**
     * Get USD Amount per day given the flag
     * B - Buy - Outgoing
     * S - Sell - Incoming
     * 
     * Sorts the result based on Settlement Date
     * 
     * @param flag can be B for Buy or S for Sell
     * @return a map with Settlement Date as key and sum of USD amount per day as value
     */
    private Map<Date, Double> getUsdAmountPerDay(char flag){
        Map<Date, Double> usdAmountPerDayRaw = instructions.stream()
                .filter(Instruction -> Instruction.getFlag() == flag)
                .collect(Collectors.groupingBy(Instruction::getSettlementDate,Collectors.summingDouble(Instruction::getAmount)));
        
        return usdAmountPerDayRaw.entrySet().stream().sorted(Map.Entry.comparingByKey()).
                collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
                
    }
    
    /**
     * Get Rank given the flag
     * B - Buy - Outgoing
     * S - Sell - Incoming
     * 
     * Ranks the result by USD amount
     * 
     * @param flag can be B for Buy or S for Sell
     * @return 
     */
    private Collection<Instruction> getRank(char flag){
        Collection<Instruction> getEntityRankRaw
        = instructions.stream()                
                .filter(Instruction -> Instruction.getFlag() == flag)
                //.sorted( (i1, i2) -> Double.compare(i1.getAmount(),i2.getAmount()) )
                .collect(Collectors.toMap(Instruction::getEntity, Function.identity(),
                        BinaryOperator.maxBy(Comparator.comparingDouble(Instruction::getAmount))))
                .values();
        
        return getEntityRankRaw.stream().sorted((i1, i2) -> Double.compare(i2.getAmount(),i1.getAmount())).collect(Collectors.toList());
    
    }
    
}
