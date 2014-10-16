package edu.unalco.queens.enviroment;

import java.util.Random;


public class Board implements Comparable<Board>{
    final static int nQueens = 20;
    int l = (int)(Math.log10(nQueens)+1);
    public int getFitness() {
        return fitness;
    }

    int fitness;
    private int[] queens;
    private Random rand = new Random();

    public Board() {
        this.queens = new int[nQueens];
        setRandomQueens();
        computeFitness();
    }

    public Board(int[] queens){
        this.queens = queens;
        moveQueen();
    }

    public Board(String name){
        this.queens = new int[nQueens];
        this.setQueens(name);
        computeFitness();
    }

    private void moveQueen(){
        int r = rand.nextInt(nQueens);//random column
        int q = queens[r];//position of queen
        int n = rand.nextInt(nQueens);//new position
        if(n == q){
            moveQueen();
        }else{
            queens[r] = n;
            computeFitness();
        }
    }

    public Board generateNeighbor(){
        return new Board(queens.clone());
    }

    public void computeFitness() {
        int x, y, x1, y1, c;
        int sum = 0;
        for (int i = 0; i < nQueens; i++) {
            x = i;
            y = queens[i];
            if (y != -1) {
                for (int j = 0; j < nQueens; j++) {
                    if (j != i) {
                        x1 = j;
                        y1 = queens[j];
                        if (y == y1){
                            sum++;//same row
                        }
                        else {//diagonals
                            if(y1 != -1) {
                                int max = Math.max(
                                        Math.max(nQueens - y1, y1),
                                        Math.max(nQueens - y1, y1)
                                );
                                for (int k = 0; k < max + 1; k++) {
                                    if (x1 + k == x && y1 + k == y) sum++; //right-down
                                    else if (x1 - k == x && y1 - k == y) sum++; //left-up
                                    else if (x1 + k == x && y1 - k == y) sum++; //right-up
                                    else if (x1 - k == x && y1 + k == y) sum++;//left-down
                                }
                            }
                        }
                    }
                }
            }
        }
        fitness = sum;
    }

    public void setRandomQueens() {
        for (int i = 0; i < nQueens; i++) {
            queens[i] = rand.nextInt(nQueens);
        }
    }

    public void setEmpty() {
        for (int i = 0; i < nQueens; i++) {
            queens[i] = -1;
        }
        computeFitness();
    }
    @Override
    public int compareTo(Board o) {
        int comparator = o.getFitness();
        return this.fitness- comparator;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nQueens; i++) {
            String queen = Integer.toString(queens[i]);
            while (queen.length() < l){
                queen = "0" + queen;
            }
            sb.append(queen);
        }
        return sb.toString();
    }

    public int size() {
        return nQueens;
    }

    public void setQueens(String name) {
        for (int i = 0; i < nQueens; i ++){
            int q = Integer.parseInt(name.substring(i*l,(i*l)+l));
            queens[i] = q;
        }
        computeFitness();
    }

    public Board reproduce(Board parent1, Board parent2) {
        int c = (new Random().nextInt(nQueens));
        String name1 = parent1.toString().substring(0,(c*l));
        String name2 = parent2.toString().substring(c*l);
        return new Board(name1 + name2);
    }

    public Board mutate() {
        return generateNeighbor();
    }

    public void setQueen(int qi, int column) {
        queens[qi] = column;
        computeFitness();
    }
}
