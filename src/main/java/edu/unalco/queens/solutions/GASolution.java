package edu.unalco.queens.solutions;

import edu.unalco.queens.enviroment.Board;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;


public class GASolution implements Solution{
    int populationNumber;
    double mutationRate;
    private Board[] population;
    private Board[] newPopulation;
    int nQueens;
    Random random = new Random();


    public GASolution(int populationNumber, double mutationRate){
        this.populationNumber = populationNumber;
        this.mutationRate = mutationRate;
        population = new Board[populationNumber];
        newPopulation = new Board[populationNumber];
        for (int i = 0; i < populationNumber; i++) {
            population[i] = new Board();
            newPopulation[i] = new Board();
        }
        this.nQueens = newPopulation[0].size();
    }

    public void mutate(){
        int index;
        for (int i = 0; i < populationNumber*mutationRate; i++) {
            index = (random.nextInt(nQueens));
            newPopulation[index] = newPopulation[index].generateNeighbor();
        }
    }

    public void setNewGeneration(){
        for (int i = 0; i < populationNumber; i++) {
            population[i].setQueens(newPopulation[i].toString());
        }
    }


    private Board select(){
        Board b;
        int rank = random.nextInt(populationNumber);
        double rankProb = (populationNumber-rank)/(populationNumber*(populationNumber+1.0)/2.0);
        if(random.nextDouble() < rankProb){
            return population[rank];
        }else{
            return select();
        }


    }
    public Board bestBoard(){
        Board best = population[0];
        int bestFitness = population[0].getFitness();
        for (int i = 1; i < populationNumber; i++) {
            if(population[i].getFitness()<bestFitness) best = population[i];
        }
        return best;
    }

    public Board nextGeneration(){
        rank();
        int r;
        Board c;
        HashSet<Board> newHash = new HashSet<Board>();

        double replaceRate = 0.6;
        double probC;
        while (newHash.size()<(1-replaceRate)*populationNumber){
            r=(int) (Math.random()*populationNumber);
            c=population[r];
            probC= (populationNumber-r) / ( populationNumber*(populationNumber+1.0)/2.0 );
            if(random.nextDouble() <= probC){
                newHash.add(c);
            }
        }
        while (newHash.size() < populationNumber) {
            Board x = select();
            Board y = select();
            Board child = x.reproduce(x,y);
            //mutation
            if (random.nextDouble() <= this.mutationRate) {
                child = child.mutate();
            }
            newHash.add(child);
        }
        newHash.toArray(newPopulation);
        population = newPopulation;
        return bestBoard();
    }

    private void rank() {
        Arrays.sort(population);
    }

    public Board search(){
        Board bestIndividual;
        int cnt = 0;
        do {
            bestIndividual = nextGeneration();
            cnt++;
        } while (bestIndividual.getFitness() != 0);
        System.out.println("solucion encontrada despues de " + cnt + " generaciones \n");
        bestIndividual.printSolution();
        return bestIndividual;
    }
}
