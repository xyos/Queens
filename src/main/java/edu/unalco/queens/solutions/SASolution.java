package edu.unalco.queens.solutions;

import edu.unalco.queens.enviroment.Board;

import java.util.Random;

public class SASolution implements Solution {
    private Scheduler scheduler;
    private Board currentState;
    private Board nextState;
    private int maxRuns;

    public SASolution(Board board, int maxRuns){
        this.currentState = board;
        this.maxRuns = maxRuns;
        this.scheduler = new Scheduler(5, 0.005, maxRuns);
    }

    boolean probabilityFunction(double temperature, int deltaE){
        if (deltaE < 0 ){
            return true;
        }
        double c = Math.exp(-deltaE/temperature);
        double r = new Random().nextDouble();
        if(r < c) {
            return true;
        }
        return false;
    }
    public Board search () {
        int runs = 0;
        while(runs < maxRuns){
            double temperature = scheduler.getTemp(++runs);
            if (temperature == 0 || currentState.getFitness() == 0) {
                if(currentState.getFitness() == 0){
                    System.out.println("solucion encontrada despues de " + runs + " iteraciones \n");
                    System.out.println(currentState.toString());
                    break;
                }else{
                    System.out.println("solucion no encontrada");
                    break;
                }
            } else {
                nextState = currentState.generateNeighbor();
                int deltaE = nextState.getFitness() - currentState.getFitness();
                if(probabilityFunction(temperature,deltaE)) {
                    currentState = nextState;
                }
            }
        }
        return currentState;
    }
}
