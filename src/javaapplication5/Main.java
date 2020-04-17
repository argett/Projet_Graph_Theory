package javaapplication5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // faire un changement pour push sur la branche Matthieu
        ArrayList<State> theGraph = new ArrayList<>();
        
        List<String> data = null; 
        try { data = Files.readAllLines(Paths.get("1.txt"));
        } catch (IOException ex) {
            System.out.println("le problème est :" + ex);
        }
        
        // var to initialize graph's length and input and output
        boolean total = true, inputs = false, outputs = false;  
        int length = 0, Input = 0, Output = 0;      
        String state = "";
        
        // var to initialize the vertices
        String sFrom = "", sTo = "", sWeight = "";
        boolean readingFrom = true, readingTo = false, readingWeight = false;
        int iFrom = 0, iTo = 0, iWeight = 0;
        
        for (String line : data) {              // we moove from a line to another
            char[] thing = line.toCharArray();  // we get each char from the line
            for(char ch : thing){               // check character by character the .txt
                // create the empty entire graph 
                if(total){                        
                    if(ch == '.'){
                        length = string_to_int(length, state, "Length of the graph is not a number : ");

                        for(int i = 0; i < length; i++){
                            State newState = new State(i);
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
                        Input = string_to_int(Input, state, "An input state of the graph is not a number : ");                        
                        theGraph.get(Input).setInput(true);
                        state = "";         // reinitialize before reusing it 
                        
                        inputs = false;      
                        outputs = true;    // the next step is to initialize the outputs
                        
                    } else if (ch == ' '){
                        Input = string_to_int(Input, state, "An input state of the graph is not a number : ");                        
                        theGraph.get(Input).setInput(true);
                        state = "";         // reinitialize before reusing it 
                    } else {
                        state = state.concat(ch+"");
                    }
                // now initialize the outputs
                } else if(outputs){
                    if(ch == '.'){
                        Output = string_to_int(Output, state, "An output state of the graph is not a number : ");                        
                        theGraph.get(Output).setOutput(true);                          
                        state = "";         // reinitialize before reusing it    
                        
                        outputs = false;    // the next state is to fill the vertices
                        
                    } else if (ch == ' '){
                        Output = string_to_int(Output, state, "An output state of the graph is not a number : ");                        
                        theGraph.get(Output).setOutput(true);
                        state = "";         // reinitialize before reusing it 
                    } else {
                        state = state.concat(ch+"");
                    }
                // we create now all the vertices
                } else {
                    if(ch == '.'){ // this is obviously the end of readingWeight
                        
                        iWeight = string_to_int(iWeight, sWeight, "An input state of the graph is not a number : ");
                        
                        theGraph.get(iFrom).setWeight(iWeight);
                        theGraph.get(iFrom).addSuccessors(theGraph.get(iTo));
                        theGraph.get(iTo).addPredecessors(theGraph.get(iFrom));
                        
                        sFrom = sTo = sWeight = "";
                        iFrom = iTo = iWeight = 0;
                        readingFrom = true;
                        readingTo = readingWeight = false;
                    } else if (ch == ' '){                        
                        if(readingFrom){
                            readingFrom = false;
                            readingTo = true;
                            
                            iFrom = string_to_int(iFrom, sFrom, "A from state of a vertice isn't a number : ");
                            
                        } else { // this is obviously readingTo
                            readingTo = false;
                            readingWeight = true;
                            
                            iTo = string_to_int(iTo, sTo, "A to state of a vertice isn't a number : ");
                        }
                        
                    } else {
                        if(readingFrom)
                            sFrom = sFrom.concat(ch+"");
                        else if(readingTo)
                            sTo = sTo.concat(ch+"");
                        else
                            sWeight = sWeight.concat(ch+"");
                    }
                }
            }
        }
        
        System.out.println("\nOn vérifie le graph");
        for(int i = 0; i < theGraph.size(); i++){
            System.out.println("Le state n° " + theGraph.get(i).getStateNB() + " est un input : " + theGraph.get(i).isInput() + " et est un output :" + theGraph.get(i).isOutput());
        }
        
        System.out.println("Le state n° " + theGraph.get(0).getStateNB() + " a comme vertice " + theGraph.get(0).printSuccessors());
        System.out.println("Le state n° " + theGraph.get(11).getStateNB() + " a comme predecessor " + theGraph.get(11).printPredecessors());
    }
    
    static private int string_to_int(int integer, String string, String message){
        try {
            integer = Integer.parseInt(string);
        }
        catch (NumberFormatException ex)
        {
            System.out.println(message + ex);
            System.exit(1);
        }
        return integer;
    }
}
