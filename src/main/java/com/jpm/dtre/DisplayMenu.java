/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpm.dtre;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;


/**
 * Main class that shows the menu containing the following
 *  (1) Show Data
 *  (2) Generate New Data
 *  (3) Generate Report
 *  (4) Exit Menu
 * 
 * @author Mark Isla
 */
public class DisplayMenu {
    
    /**
     * Main method to display the menu.
     * Display will loop until menu 4 is chosen.
     * 
     * @param args will not be used in this method as input param is not needed
     */
    public static void main(String[] args){
        
        Scanner input = new Scanner(System.in);
        String tempInp;
        int choice;
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        
        GenerateData gd = new GenerateData(50);
        GenerateReport gr;
        List <Instruction> instructions = gd.generateData();
        
        do{
            /* */
            System.out.println("Daily Trade Reporting Menu");
            System.out.println("(1) Show Data");
            System.out.println("(2) Generate New Data");
            System.out.println("(3) Generate Report");
            System.out.println("(4) Exit");
            System.out.println("Enter Your Menu Choice: ");
            tempInp = input.next();
            try{
                choice = Integer.parseInt(tempInp);
            }catch(NumberFormatException | NullPointerException e){
                choice = 99;
            }         
                        
            switch(choice){
                case 1: /* Show existing data if there is any */
                    System.out.println("Below are the existing data.");
                    System.out.println("Entity\t| B/S\t| Fx\t| Curr\t| Ins Date\t| Sett Date\t| Units\t| Price Per Unit");
                    for(Instruction in: instructions){
                        System.out.println( in.getEntity() + "\t| " +
                                in.getFlag() + "\t| " +
                                in.getAgreedFx() + "\t| " +
                                in.getCurrency() + "\t| " + 
                                sdf.format(in.getInstructionDate()) + "\t| " + 
                                sdf.format(in.getSettlementDate()) + "\t| " + 
                                in.getUnits() + "\t| " + 
                                in.getPricePerUnit());
                                
                    }
                    System.out.println(" ");
                    System.out.println("End of data. Going back to Menu.");
                    System.out.println(" ");
                    break;
                
                case 2: /* Generate new data */
                    instructions = gd.generateData();
                    System.out.println("New data generated.");
                    System.out.println(" ");
                    System.out.println("Going back to Menu."); 
                    System.out.println(" ");
                    break;
                
                case 3: /* Show report */
                    gr = new GenerateReport(instructions);
                    System.out.println(gr.genReport());
                    System.out.println(" ");
                    System.out.println("Going back to Menu.");
                    System.out.println(" ");
                    break;
                
                case 4: /* Exit menu */
                    System.out.println("Closing menu...");
                    System.out.println(" ");
                    System.exit(0);
                    break;
                
                default:
                    System.out.println("Invalid menu option.");
                    System.out.println(" ");
                    System.out.println("Going back to Menu.");
                    System.out.println(" ");
                    break;
            }
        }
        while(choice != 4); /* Exit loop is menu 4 is chosen */
        
    }
    
}
