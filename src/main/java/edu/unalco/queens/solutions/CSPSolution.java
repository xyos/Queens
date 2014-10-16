package edu.unalco.queens.solutions;

import edu.unalco.queens.enviroment.Board;

public class CSPSolution implements Solution{
    private int n;
    private int backtracks;
    private Board b;
    public CSPSolution(){
        this.b = new Board();
        this.backtracks = 0;
        this.b.setEmpty();
        this.n = b.size();
    }

    Board placeQueenOnBoard(int qi) {
        if (qi == n && b.getFitness() == 0) {// a valid configuration found.
            System.out.println("solucion encontrada despues de " + backtracks + " backtracks \n");
            System.out.println(b.toString());
            System.exit(0);
            return b;
        } else {
            //start at the middle on first queen;
            if(qi == 0){
                int col;
                if (n % 2 == 0) {
                    col = n / 2;
                }
                else {
                    col = (n + 1) / 2;
                }
                b.setQueen(qi, col);
                placeQueenOnBoard(qi + 1);
            }
            for (int column = 0; column < n; column++) {
                if (b.getFitness() == 0 && qi != n) {
                    b.setQueen(qi, column);
                    //then place remaining queens.
                    placeQueenOnBoard(qi + 1);
                    backtracks++;
                    b.setQueen(qi, -1);
                }
            }
        }
        return null;
    }

    @Override
    public Board search() {
        return placeQueenOnBoard(0);
    }
}
