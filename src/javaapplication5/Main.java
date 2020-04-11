package javaapplication5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        
        List<String> data = null; 
        try { data = Files.readAllLines(Paths.get("graphs.txt"));
        } catch (IOException ex) {
            System.out.println("le problème est :" + ex);
        }
        
        for (String line : data) {              // we moove from a line to another
            char[] thing = line.toCharArray();  // we get each char from the line
            for(char ch : thing){
                if(ch != ' ')
                    System.out.println(ch);
            }
        }
    }
}
