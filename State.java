/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication5;

import java.util.ArrayList;

/**
 *
 * @author lilian
 */
public class State {    					// a state = a vertice
    private ArrayList<State> predecessors = new ArrayList<>(); 	// the list of all predecessors
    private ArrayList<Integer> weightPred = new ArrayList<>(); 	// the weight of all predecessors
    private ArrayList<State> successors = new ArrayList<>(); 	// the list of all successors
    private ArrayList<Integer> weightSucc = new ArrayList<>(); 	// the weight of all successors
    private boolean input;					// if the state an input ?
    private boolean output;					// if the state an output?
    private int rank;						// the rank of the state
    private int stateNB; 					// the "name" of the state
    private int maxDistFromInput; 				// the maximum distance bewteen the input and the state
    private State maxPrev; 					// the place of the predecessor state from where comes from the maximal distance with the input --> used for ealriest date
    
    State(int i){ 
        input = false;
        output = false;
        rank = -1;
        stateNB = i;
        maxDistFromInput = 0;
    }

    public State getSuccessor(int i) {
        return successors.get(i);
    }    
    public State setSuccessor(State state, int rank) {
        return successors.set(rank, state);
    }    
    public ArrayList<State> getSuccessors() {
        return successors;
    }    
    public int getSuccessorsLength() {
        return successors.size();
    }
    public void addSuccessors(State next) {
        this.successors.add(next);
    }    
    public String printSuccessors() {
        String succ = "";
        for(int i = 0; i<this.successors.size(); i++){
            succ = succ.concat(String.valueOf(this.successors.get(i).getStateNB()) + " ");
        }
        return succ;
    }
    
    public int getWeightSucc(int i) {
        return weightSucc.get(i);
    }    
    public int getWeightPred(int i) {
        return weightPred.get(i);
    }
    public ArrayList<Integer> getWeightsSucc() {
        return weightSucc;
    }    
    public int getWeightSuccLength() {
        return weightSucc.size();
    }
    public void addWeightSucc(int i) {
        this.weightSucc.add(i);
    }   
    public void addWeightPred(int i) {
        this.weightPred.add(i);
    } 
    public Integer getWeightsOfSucc(State state){
        int rank = -1;
        for(State temp : this.successors){
            rank++;
            if(temp == state)
                return this.getWeightSucc(rank);
        }
        System.out.println("Probleme dans la liste des successor de weight");
        return null;
    }    
    public Integer getWeightsOfPred(State state){
        int rank = -1;
        for(State temp : this.predecessors){
            rank++;
            if(temp == state)
                return this.getWeightPred(rank);
        }
        return null;
    }    
    public String printWeight() {
        String succ = "";
        for(int i = 0; i<this.weightSucc.size(); i++){
            succ = succ.concat(String.valueOf(this.weightSucc.get(i)) + " ");
        }
        return succ;
    }    
    
    public State getPredecessor(int i) {
        return predecessors.get(i);
    }    
    public State setPredecessor(State state, int rank) {
        return predecessors.set(rank, state);
    }  
    public ArrayList<State> getPredecessors() {
        return predecessors;
    }    
    public String printPredecessors() {
        String succ = "";
        for(int i = 0; i<this.predecessors.size(); i++){
            succ = succ.concat(String.valueOf(this.predecessors.get(i).getStateNB()) + " ");
        }
        return succ;
    }    
    public int getPredecessorsLength() {
        return predecessors.size();
    }    
    public void addPredecessors(State prev) {
        this.predecessors.add(prev);
    }

    public boolean isInput() {
        return input;
    }
    public void setInput(boolean isInput) {
        this.input = isInput;
    }    
    public static State getInput(ArrayList<State> graph){
        for(State input : graph)
            if(input.isInput())
                return input;
        return null;
    }    
    public State modifInput(State newInput){
        return newInput;
    }

    public boolean isOutput() {
        return output;
    }
    public void setOutput(boolean isOutput) {
        this.output = isOutput;
    }
    public static State getOutput(ArrayList<State> graph){
        for(State output : graph)
            if(output.isOutput())
                return output;
        return null;
    } 
    
    public int getRank() {
        return rank;
    }
    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getStateNB() {
        return stateNB;
    }
    public void setStateNB(int stateNB) {
        this.stateNB = stateNB;
    } 
    
    public int getMaxDistFromInput() {
        return maxDistFromInput;
    }
    public void setMaxDistFromInput(int dist) {
        this.maxDistFromInput = dist;
    }
    
    public State getMaxPrev() {
        return maxPrev;
    }
    public void setMaxPrev(State prev) {
        this.maxPrev = prev;
    }
}
