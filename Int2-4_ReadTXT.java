
package javaapplication5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class Text_File {
    
    public static List<String> readAllLines(String fileName){
        BufferedReader fluxEntree = null;
        String lignRead;
        List<String> lignes = new ArrayList<>();
        
        try{
            fluxEntree = new BufferedReader(new FileReader(fileName));
            lignRead = fluxEntree.readLine();
            while( lignRead != null){
                lignes.add(lignRead);
                lignRead = fluxEntree.readLine();
            }
        }
        catch(IOException exc){
            System.out.print("Problem reading file : " + exc);
        }
        
        return lignes;
    }

    public static void fileWrite(String fileName, List<String> data){
        Writer flux=null;
        //PrintWriter writer = new PrintWriter(fileName);
        try{
            flux = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
            for(int i=0;i<data.size()-1;i++){
                flux.write(data.get(i)+"\n");
            }
            flux.write(data.get(data.size()-1));
        }
        catch(IOException exc){
            System.out.println("Problem writing in files :" + exc);
        }
        try{
            flux.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
