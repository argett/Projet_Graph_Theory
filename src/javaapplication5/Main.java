package javaapplication5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        
        ArrayList<State> theGraph = new ArrayList<>();
        
        List<String> data = null; 
        try { data = Files.readAllLines(Paths.get("1.txt"));
        } catch (IOException ex) {
            System.out.println("le problème est :" + ex);
        }
        
        boolean total = true, inputs = false, outputs = false;        
        String state = "";
        int length = 0, Input = 0, Output = 0;
        
        for (String line : data) {              // we moove from a line to another
            char[] thing = line.toCharArray();  // we get each char from the line
            for(char ch : thing){               // check character by character the .txt
                // create the empty entire graph 
                if(total){                        
                    if(ch == '.'){
                        try {
                           length = Integer.parseInt(state);
                        }
                        catch (NumberFormatException ex)
                        {
                            System.out.println("Length of the graph is not a number : " + ex);
                            System.exit(1);
                        }
                        System.out.println("length = " + length);

                        for(int i = 0; i < length; i++){
                            State newState = new State();
                            theGraph.add(newState);
                        }

                        total = false;      // we know the total length
                        inputs = true;      // the next step is to initialize the inputs
                        state = "";         // reinitialize before reusing it 
                    } else {
                        state = state.concat(ch+"");
                    } 
                // initialize the inputs
                } else if(inputs){ 
                    if(ch == '.'){
                        try {
                           Input = Integer.parseInt(state);
                        }
                        catch (NumberFormatException ex)
                        {
                            System.out.println("An input state of the graph is not a number : " + ex);
                            System.exit(1);
                        }
                        
                        theGraph.get(Input).setInput(true);
                        
                        inputs = false;      
                        outputs = true;    // the next step is to initialize the outputs
                        state = "";         // reinitialize before reusing it 
                    } else if (ch == ' '){
                        try {
                           Input = Integer.parseInt(state);
                        }
                        catch (NumberFormatException ex)
                        {
                            System.out.println("An input state of the graph is not a number : " + ex);
                            System.exit(1);
                        }
                        
                        theGraph.get(Input).setInput(true);
                        state = "";         // reinitialize before reusing it 
                    } else {
                        state = state.concat(ch+"");
                    }
                // now initialize the outputs
                } else if(outputs){
                    if(ch == '.'){
                        try {
                           Output = Integer.parseInt(state);
                        }
                        catch (NumberFormatException ex)
                        {
                            System.out.println("An input state of the graph is not a number : " + ex);
                            System.exit(1);
                        }
                        
                        theGraph.get(Output).setOutput(true);
                             
                        outputs = false;    // the next state is to fill the vertices
                        state = "";         // reinitialize before reusing it 
                    } else if (ch == ' '){
                        try {
                           Output = Integer.parseInt(state);
                        }
                        catch (NumberFormatException ex)
                        {
                            System.out.println("An input state of the graph is not a number : " + ex);
                            System.exit(1);
                        }
                        
                        theGraph.get(Output).setOutput(true);
                        state = "";         // reinitialize before reusing it 
                    } else {
                        state = state.concat(ch+"");
                    }
                }
            }
        }
        
        System.out.println("On vérifie le graph");
        for(int i = 0; i < theGraph.size(); i++){
            System.out.println("Le state n° " + i + " est un input : " + theGraph.get(i).isInput() + " et est un output :" + theGraph.get(i).isOutput());
        }
    }
}
