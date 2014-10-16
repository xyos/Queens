package edu.unalco.queens;

import edu.unalco.queens.enviroment.Board;
import edu.unalco.queens.solutions.CSPSolution;
import edu.unalco.queens.solutions.GASolution;
import edu.unalco.queens.solutions.SASolution;


public class Queens {
    public static void main(String[] args) {
        Board saSolution = new SASolution(new Board(),100000).search();
        Board gaSolution = new GASolution(20,0.2).search();
        Board CSPSolution = new CSPSolution().search();


    }
}
