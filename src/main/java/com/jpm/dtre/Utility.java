/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpm.dtre;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Class for utility methods
 * @author Mark Isla
 */
public class Utility {
            
    /**
     * Utility method to round up double with preferred decimal places
     * 
     * @param val value of type double
     * @param decimal decimal places
     * @return a double with given decimal places rounded up
     */
    public static double round(double val, int decimal) {
        if (decimal < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(val);
        bd = bd.setScale(decimal, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    /**
     * Utility method to pad spaces to the left.
     *  Used to display amount on the console.
     * 
     * @param str string to be padded
     * @param num number of spaces to be padded
     * @return padded string
     */
    public static String leftPad(String str, int num){
        return String.format("%1$" + num + "s", str);
    }
}
