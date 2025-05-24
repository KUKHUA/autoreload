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

/*
 -  set/unset: Back(int colorCode), Front(int colorCode), Bold, Dim, UnderLine, Italic, Hidden, setGraphicCode(String graphicCode) and resetAll();
 - parmarters (if any) are only for set
 - scroll down on this page to find color codes https://en.wikipedia.org/wiki/ANSI_escape_code#8-bit
 - 
*/

final public class Print {
    
    public static void setBack(int colorCode){
        System.out.print("\u001B[48;5;" + colorCode + "m");
    }
    
    public static void unsetBack(){
        System.out.print("\u001B[49m");
    }
    
    
    public static void setFront(int colorCode){
        System.out.print("\u001B[38;5;" + colorCode + "m");
    }
    
    public static void unsetFront(){
        System.out.print("\u001B[39m");
    }
    
    
    public static void resetAll(){
        System.out.print("\u001B[0m");
    }
    
    
    public static void setBold(){
        System.out.print("\u001B[1m");   
    }
    
    public static void unsetBold(){
        System.out.print("\u001B[22m");   
    }
    
    
    public static void setDim(){
        System.out.print("\u001B[2m");   
    }
    
    public static void unsetDim(){
        System.out.print("\u001B[22m"); 
    }
    
    
    public static void setUnderLine(){
        System.out.print("\u001B[21m"); 
    }
    
    public static void unsetUnderLine(){
        System.out.print("\u001B[24m"); 
    }
    
    
    public static void setItalic(){
        System.out.print("\u001B[3m"); 
    }
    
    public static void unsetItalic(){
        System.out.print("\u001B[23m"); 
    }
    
    
    public static void setHidden(){
        System.out.print("\u001B[8m"); 
    }
    
    public static void unsetHidden(){
        System.out.print("\u001B[28m"); 
    }
    
    
    public static void setGraphicCode(String graphicCode){
        System.out.print("\u001B[" + graphicCode);
    }
    
    
    public static void clearScreen(){
        setGraphicCode("H");
        setGraphicCode("2J");
        System.out.flush();
    }
}