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
                        
                        iWeight = string_to_int(iWeight, sWeight, "The weight of a vertice of the graph is not a number : ");
                        
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
        
        adjencyMatrix(theGraph);
        valueMatrix(theGraph);
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
    
    static private void  adjencyMatrix(ArrayList<State> graph){
        System.out.println("----------- adjency matrix -------------");
        System.out.print("   ");
        for(int i=0; i< graph.size(); i++){
            if(i<10)
                System.out.print(" "+i+" ");
            else
                System.out.print(" "+i);
        }
        System.out.print("\n");
        
        boolean find = false;
        for(int i=0; i< graph.size(); i++){                                      // lignes
            
            // to get a proper diplayed matrix 
            if(i<10)
                System.out.print(" "+i+" ");
            else
                System.out.print(" "+i);
            
            
            for(int j=0; j< graph.size(); j++){                                  // columns
                for(int k=0; k< graph.get(i).getSuccessorsLength(); k++){        // successors of the line
                    if(graph.get(i).getSuccessors(k).getStateNB() == graph.get(j).getStateNB())  // check is lines has successors equal to the columns
                        find = true;
                }
                if(find){
                    System.out.print(" T ");
                    find = false;
                } else  {
                    System.out.print(" F ");
                }
            }
            System.out.print("\n");
        }
        System.out.println("----------------------------------------");
    }
    
    static public void valueMatrix(ArrayList<State> graph){
     System.out.println("------------- value matrix -------------");
        System.out.print("   ");
        for(int i=0; i< graph.size(); i++){
            if(i<10)
                System.out.print(" "+i+" ");
            else
                System.out.print(" "+i);
        }
        System.out.print("\n");
        
        boolean find = false;
        for(int i=0; i< graph.size(); i++){                                      // lignes
            
            // to get a proper diplayed matrix 
            if(i<10)
                System.out.print(" "+i+" ");
            else
                System.out.print(" "+i);
            
            
            for(int j=0; j< graph.size(); j++){                                  // columns
                for(int k=0; k< graph.get(i).getSuccessorsLength(); k++){        // successors of the line
                    if(graph.get(i).getSuccessors(k).getStateNB() == graph.get(j).getStateNB())  // check is lines has successors equal to the columns
                        find = true;
                }
                if(find){
                    if(graph.get(i).getWeight() < 0 || graph.get(i).getWeight()>=10)
                        System.out.print(" " + graph.get(i).getWeight());
                    else 
                        System.out.print(" " + graph.get(i).getWeight() + " ");
                    find = false;
                } else  {
                    System.out.print(" * ");
                }
            }
            System.out.print("\n");
        }
        System.out.println("----------------------------------------");
    }
    
    static private void inputOutput(ArrayList<State> graph){
        System.out.println("\nOn vérifie le graph");
        for(int i = 0; i < graph.size(); i++){
            System.out.println("Le state n° " + graph.get(i).getStateNB() + " est un input : " + graph.get(i).isInput() + " et est un output :" + graph.get(i).isOutput());
        }
        
        System.out.println("Le state n° " + graph.get(0).getStateNB() + " a comme vertice " + graph.get(0).printSuccessors());
        System.out.println("Le state n° " + graph.get(11).getStateNB() + " a comme predecessor " + graph.get(11).printPredecessors());
    }
}
