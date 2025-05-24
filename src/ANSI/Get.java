/*
 * ChangeDetector -  detects modifications, deletions, or creations of files and folders.
 * Copyright (C) 2025 KUKHUA
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package ANSI;

final public class Get {
    
    public static String setBack(int colorCode){
        return "\u001B[48;5;" + colorCode + "m";
    }
    
    public static String unsetBack(){
        return "\u001B[49m";
    }
    
    
    public static String setFront(int colorCode){
        return "\u001B[38;5;" + colorCode + "m";
    }
    
    public static String unsetFront(){
        return "\u001B[39m";
    }
    
    
    public static String resetAll(){
        return "\u001B[0m";
    }
    
    
    public static String setBold(){
        return "\u001B[1m";   
    }
    
    public static String unsetBold(){
        return "\u001B[22m";   
    }
    
    
    public static String setDim(){
        return "\u001B[2m";   
    }
    
    public static String unsetDim(){
        return "\u001B[22m"; 
    }
    
    
    public static String setUnderLine(){
        return "\u001B[21m"; 
    }
    
    public static String unsetUnderLine(){
        return "\u001B[24m"; 
    }
    
    
    public static String setItalic(){
        return "\u001B[3m"; 
    }
    
    public static String unsetItalic(){
        return "\u001B[23m"; 
    }
    
    
    public static String setHidden(){
        return "\u001B[8m"; 
    }
    
    public static String unsetHidden(){
        return "\u001B[28m"; 
    }
    
    
    public static String setGraphicCode(String graphicCode){
        return "\u001B[" + graphicCode;
    }
    
    
    public static String clearScreen(){
        return setGraphicCode("H") + setGraphicCode("2J");
    }
}