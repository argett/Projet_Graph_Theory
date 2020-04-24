package javaapplication5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static javaapplication5.State.getInput;
import static javaapplication5.State.getOutput;

public class Main {
    public static void main(String[] args) {      
        boolean continu = false;
        int choice;
        ArrayList<State> theGraph = new ArrayList<>();
        
        do{
            String graph = "";
            Scanner sc = new Scanner(System.in);
            
            System.out.println("Which graph du you want to try ? 1,2,3...");
            graph = sc.nextLine();
            graph = graph.concat(".txt");
            theGraph.clear();
            theGraph = fillGraph(theGraph, graph);
            
            // matrixes
            adjencyMatrix(theGraph);
            valueMatrix(theGraph);
            // we make the ranks
            theGraph.set(0, makeRanks(theGraph.get(0)));
            printRanks(theGraph);
            
            if(isSchedulingGraph(theGraph)){
                getInput(theGraph).modifInput(computeMaxDist(getInput(theGraph)));   // we put the new Graph with the distance instead of the old graph --> used for ealiestDate
                //getOutput(theGraph).modifInput(computeMinDist(getOutput(theGraph)));   // used for latest date
                
                printEarly(earlyDate(theGraph));
                //printLatest(lateDate(theGraph));
                
            }
                
            else 
                System.out.println("The graph can't be scheduled");
            
            System.out.println("Do you want to try another graph ? 0/1");
            choice = sc.nextInt();
            if(choice == 1)
                continu = true;
            else 
                continu = false;
            System.out.println("\n\n\n\n\n\n\n\n\n\n");
            
        }while(continu);
        
    }
    
    // INITIALIZATION
    static private ArrayList<State> fillGraph(ArrayList<State> theGraph, String file){
        List<String> data = null; 
        try { data = Files.readAllLines(Paths.get(file));
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
                        
                        theGraph.get(iFrom).addSuccessors(theGraph.get(iTo));
                        theGraph.get(iFrom).addWeightSucc(iWeight);
                        theGraph.get(iTo).addPredecessors(theGraph.get(iFrom));
                        theGraph.get(iTo).addWeightPred(iWeight);
                        
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
        return theGraph;
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
    
    // VERIFICATION
    static private void inputOutput(ArrayList<State> graph){
        System.out.println("\nOn vérifie le graph");
        for(int i = 0; i < graph.size(); i++){
            System.out.println("Le state n° " + graph.get(i).getStateNB() + " est un input : " + graph.get(i).isInput() + " et est un output :" + graph.get(i).isOutput());
        }
        
        System.out.println("Le state n° " + graph.get(0).getStateNB() + " a comme vertice " + graph.get(0).printSuccessors());
        System.out.println("Le state n° " + graph.get(11).getStateNB() + " a comme predecessor " + graph.get(11).printPredecessors());
    }
    // END INITIALIZATION - VERIFICATION
    
    
    // PART I - 2)
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
                    if(graph.get(i).getSuccessor(k).getStateNB() == graph.get(j).getStateNB())  // check is lines has successors equal to the columns
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
        
        int value = -1;
        for(int i=0; i< graph.size(); i++){                                      // lignes
            
            // to get a proper diplayed matrix 
            if(i<10)
                System.out.print(" "+i+" ");
            else
                System.out.print(" "+i);
            
            
            for(int j=0; j< graph.size(); j++){                                  // columns
                for(int k=0; k< graph.get(i).getSuccessorsLength(); k++){        // successors of the line
                    if(graph.get(i).getSuccessor(k).getStateNB() == graph.get(j).getStateNB())  // check is lines has successors equal to the columns
                        value = k;
                }
                if(value != -1){
                    if(graph.get(i).getWeightSucc(value) < -9)
                        System.out.print(graph.get(i).getWeightSucc(value));                        
                    else if (graph.get(i).getWeightSucc(value) < 0 || graph.get(i).getWeightSucc(value)>=10)
                        System.out.print(" " + graph.get(i).getWeightSucc(value));
                    else 
                        System.out.print(" " + graph.get(i).getWeightSucc(value) + " ");
                    value = -1;
                } else  {
                    System.out.print(" * ");
                }
            }
            System.out.print("\n");
        }
        System.out.println("----------------------------------------");
    }
    // END PART I - 2)
    
    
    
    // PART I - 4)
    static private State makeRanks(State state){
        boolean ranked = true;
        int max_previous_rank = -1; // the maximal rank of the rank of all the predecessors
        for(State prev : state.getPredecessors()){ // we check each precessors : does they have a rank ? 
            if(prev.getRank() == -1)
                ranked = false; // if at least 1 predecessor doesn't have a rank, we can't set the rank of the actual state
            else{
                if(max_previous_rank < prev.getRank()) // else, we take the maximal ranl the all predecessors
                    max_previous_rank = prev.getRank();
                            
            }
        }
        if(ranked)
            state.setRank(max_previous_rank+1); // we set the rank of the state
        
        for(int i = 0; i<state.getSuccessorsLength(); i++){ // we call the function for all successors
            state.setSuccessor(makeRanks(state.getSuccessor(i)),i);
        }
        
        return state;
    }
    
    static private void printRanks(ArrayList<State> graph){
        for(State next : graph){
            System.out.println("The state " + next.getStateNB() + " has the rank " + next.getRank());            
        }
    }
    // END PART I - 4)
    
    
    // PART II - 5)
    static private boolean isSchedulingGraph(ArrayList<State> graph){
        if(oneInput(graph) && oneOutput(graph) && nonNegative(graph) && sameWeight(graph) && zeroEntry(graph))
            return true;
        else
            return false;
    }
    
    static private boolean oneInput(ArrayList<State> graph){
        int nb_input = 0;
        for(State temp : graph){
            if(temp.isInput())
                nb_input ++;
        }
        
        if(nb_input == 1)
            return true;
        else {
            System.out.println("There is more than one input");
            return false;            
        }
    }
    
    static private boolean oneOutput(ArrayList<State> graph){
        int nb_output = 0;
        for(State temp : graph){
            if(temp.isOutput())
                nb_output ++;
        }
        
        if(nb_output == 1)
            return true;
        else {
            System.out.println("There is more than one output");
            return false;            
        }
    }
    
    static private boolean nonNegative(ArrayList<State> graph){
        boolean negative = false;
        for(State temp : graph){
            for(int value : temp.getWeightsSucc()){
                if(value < 0){
                    System.out.println("There is a negative arc");
                    return false;
                }
            }
        }
        
        return true;
    }
    
    static private boolean sameWeight(ArrayList<State> graph){
        for(State temp : graph){
            for(int value : temp.getWeightsSucc()){
                int refValue = temp.getWeightSucc(0);
                if(value != refValue){
                    System.out.println("At least 2 vertex of a same state doesn't have the same weight");
                    return false;                    
                }
            }
        }
        return true;
    }
    
    static private boolean zeroEntry(ArrayList<State> graph){
        for(State temp : graph){
            if(temp.isInput()){
                for(int value : temp.getWeightsSucc()){
                    if(value != 0){
                        System.out.println("An entry vertice doesn't have a weight of 0");
                        return false;                    
                    }
                }
            }
        }
        
        return true;
    }
    // END PART II - 5)
    
    
    // PART II - 6)
    static private ArrayList<State> earlyDate(ArrayList<State> graph){
        ArrayList<State> dateSorted = new ArrayList<>();
        State debut = getInput(graph);
        State fin = getOutput(graph);
        
        State curr = fin;  
        dateSorted.add(curr);
        while(curr != debut){
            for(State prev : curr.getPredecessors()){
                if(prev == curr.getMaxPrev()){                    
                    dateSorted.add(prev);
                    curr = prev;
                }
            }
        }
        return dateSorted;
    }
    /*
    static private ArrayList<State> lateDate(ArrayList<State> graph){
        ArrayList<State> dateSorted = new ArrayList<>();
        State debut = getInput(graph);
        State fin = getOutput(graph);
        
        State curr = debut;
        dateSorted.add(curr);
        while(curr != fin){
            for(State next : curr.getSuccessors()){
                if(next == curr.getMinSucc()){                    
                    dateSorted.add(next);
                    curr = next;
                }
            }
        }
        return dateSorted;
    }
    */
    
    static private State computeMaxDist(State state){ // compute the maximal distance bewteen a state and the input (=all predecessors must have finished)
        int max_previous_dist = -1; // the maximal distance from the state to the input of all the predecessors
        for(State prev : state.getPredecessors()){
            
            if((max_previous_dist < prev.getMaxDistFromInput() + prev.getWeightsOfSucc(state))){ // if the path is longer than another path
                int weightState = prev.getWeightsOfSucc(state);  
                int weightVector = prev.getMaxDistFromInput();
                max_previous_dist =  weightState + weightVector;                
                state.setMaxPrev(prev);
            }
        }
        
        if(state.getMaxDistFromInput() < max_previous_dist) // after having checked all predecessors, we set the distance of the actual length as the sum of the distance of the predecessor + the edge
            state.setMaxDistFromInput(max_previous_dist);
        
        for(int i = 0; i<state.getSuccessorsLength(); i++){
            state.setSuccessor(computeMaxDist(state.getSuccessor(i)),i);
        }
        return state;
    }
    /*
    static private State computeMinDist(State state){ // compute the maximal distance bewteen a state and the input (all predecessors must have finished)
        int min_next_dist = state.getMaxDistFromInput(); // the maximal distance from the state to the input of all the predecessors
        for(State next : state.getSuccessors()){    
            if((min_next_dist > next.getMaxDistFromInput() - state.getWeightsOfSucc(next))){
                int weightState = next.getWeightsOfPred(state);  
                int weightVector = next.getMinDistFromInput();
                min_next_dist =  weightState + weightVector;                
                state.setMinSucc(next);
            }
        }
        
        if(state.getMinDistFromInput() > min_next_dist)
            state.setMinDistFromInput(min_next_dist);
        
        for(int i = 0; i<state.getPredecessorsLength(); i++){
            state.setPredecessor(computeMinDist(state.getPredecessor(i)),i);
        }
        return state;
    }
    */
    static private void printEarly(ArrayList<State> graph){
        System.out.println("\nThe shortest time is : ");
        for(State temp : graph)
            System.out.println("Le state " + temp.getStateNB() + " et sa distance est de " + temp.getMaxDistFromInput());
    }
    /*
    static private void printLatest(ArrayList<State> graph){
        System.out.println("\nThe longest time is : ");
        for(State temp : graph)
            System.out.println("Le state " + temp.getStateNB() + " et sa distance est de " + temp.getMaxDistFromInput());
    }
    */
    // END PART II - 6)
}
