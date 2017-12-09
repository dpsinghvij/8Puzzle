package ai.assignment.first.models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by davinder on 20/2/17.
 * It helps in implementing following functions
 * a. Saves the state of Puzzle
 * b. Generates new child nodes from the parent nodes
 * c. implements Heuristics
 */
public class PuzzleState implements Cloneable, Comparator<PuzzleState>{



    public enum Heuristics{
        NO_HEURISTICS, OUT_OF_PLACE, MIN_MOVES, MANHATTAN
    }
    // board state is saved
    private int numbers[][]= new int[3][3];
    private int level=1;
    private int h=0;
    private int g=0;
    private int f=0;
    List<PuzzleState> prevStates;

    // Goal state is initialized
    private int[][] goal= {
            {1,2,3},
            {8,0,4},
            {7,6,5}
    };

    public PuzzleState(int[][] numbers) {
        this.numbers = numbers;
        prevStates= new ArrayList<>();
    }


    public PuzzleState(){

    }

    /**
     * Find the current position of element
     * @param x- number whose position needs to be calculated
     * @return Position array with first element x index and second as y index
     */
    public int[] currentPosition(int x){
        int[] nums= new int[2];
        int i=0,j=0;
        outerloop:
        for (i = 0; i < 3; i++) {
            for (j = 0; j < 3; j++) {
                if(numbers[i][j]==x){
                    break outerloop;
                }
            }
        }
        nums[0]=i;
        nums[1]=j;
        return nums;
    }

    /**
     * Find the current position of element
     * @param board array of ints
     * @param x number whose position needs to be calculated
     * @return position
     */
    public Position positionOfNumber(int[][] board,int x){
        Position pos= new Position();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(board[i][j]==x){
                    pos.x=i;
                    pos.y=j;
                    return pos;
                }
            }
        }
        return null;
    }

    /**
     * Returns co-ordinates of the number
     * @param i
     * @param j
     * @return number
     */
    public int numberAtPos(int i,int j){
        return numbers[i][j];
    }

    /**
     * Finds the positions where blank space or zero could be moved
     * @return List of positions
     */
    public List<Position> getNumberPosAdjacentZero(){
        // get current position of zero
        int[] currentPosZero= currentPosition(0);
        List<Position> positions= new ArrayList<>();
        Position zeroPos= new Position();
        zeroPos.x= currentPosZero[0];
        zeroPos.y= currentPosZero[1];

        // checks whether it can be moved in up/ down/ left/ right direction.
        // If it can be moved then the position to which it can be moved is
        // saved in an array
        Position adjPos;
        adjPos = zeroPos.getPosition(Position.MOVETYPE.UP);
        if (adjPos != null) {
            positions.add(adjPos);
        }
        adjPos=null;
        adjPos = zeroPos.getPosition(Position.MOVETYPE.DOWN);
        if (adjPos != null) {
            positions.add(adjPos);
        }
        adjPos=null;
        adjPos = zeroPos.getPosition(Position.MOVETYPE.LEFT);
        if (adjPos != null) {
            positions.add(adjPos);
        }
        adjPos=null;
        adjPos = zeroPos.getPosition(Position.MOVETYPE.RIGHT);
        if (adjPos != null) {
            positions.add(adjPos);
        }
        return positions;
    }


    /**
     * Function used to generate new puzzle state given a puzzle states
     * @param puzzleState old puzzle state
     * @param position change of the position of blank
     * @return
     * @throws CloneNotSupportedException
     */
    private int[][] getNewState(PuzzleState puzzleState,Position position) throws CloneNotSupportedException {
        int[] currentPosZero= puzzleState.currentPosition(0);
        int numberToBeSwappedWith = numberAtPos(position.x,position.y);
        // a copy is made so that values don't overwrite old values
        PuzzleState clone= puzzleState.clonePuzzle();

      //  int[][] boardState = clone.numbers;
        // swap zero with the element at position to generate new puzzle state
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(i==position.x && j==position.y){
                    clone.numbers[i][j]=0;
                    clone.numbers[currentPosZero[0]][currentPosZero[1]]=numberToBeSwappedWith;
                    break;
                }
            }
        }
        return clone.numbers;
    }

    /**
     * generates new puzzle state without Heuristics
     * @return
     */
    public List<PuzzleState> getNewStates(){

        return getNewStates(Heuristics.NO_HEURISTICS);
    }


    /**
     * generates new puzzle state with Heuristics
     * @param heuristics It could be minimum move heuristic, out of space or one specified in assignment
     * @return
     */
    public List<PuzzleState> getNewStates(Heuristics heuristics){
        // find possible positions where zero can be swapped
        List<Position> numberPosAdjacentZero = getNumberPosAdjacentZero();
        List<PuzzleState> puzzleStates= new ArrayList<>();
        for (Position pos :
                numberPosAdjacentZero) {
            int[][] newState = new int[0][];
            try {
                // generate new child state from parent state
                newState = getNewState(this,pos);
                PuzzleState e = new PuzzleState(newState);
                // increase its level by 1
                e.level= this.level+1;
                e.calculateHeuristics(heuristics);
                List<PuzzleState> prevStates= new ArrayList<>();
                // add States of all the parents of the parent
                prevStates.addAll(this.prevStates);
                // add parent puzzle state
                prevStates.add(this);
                e.setPrevStates(prevStates);
                puzzleStates.add(e);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }

        }
        return puzzleStates;
    }

    /**
     * Method to calculate heuristics
     * @param heuristics
     */
    private void calculateHeuristics(Heuristics heuristics) {
        g=level;
        switch (heuristics){

            case NO_HEURISTICS:
                break;
            case OUT_OF_PLACE:
                h= getOutOfPlace();
                break;
            case MIN_MOVES:
                h= getMinimumMoves();
                break;
            case MANHATTAN:
                h= getManhattanDistance()+3*calculateSeq();
                break;
        }
    }

    private int getMinimumMoves() {
        return getManhattanDistance();
    }

    /**
     * Calculates manhattan distances
     * @return
     */
    private int getManhattanDistance() {
        int dist=0;
        for (int i = 1; i <=8 ; i++) {
            Position pos = positionOfNumber(numbers, i);
            Position goalpos = positionOfNumber(goal, i);
            dist+= Math.abs(pos.x- goalpos.x);
            dist+= Math.abs(pos.y- goalpos.y);
        }
        return dist;
    }


    /**
     * Calculates out of place heuristic
     * @return
     */
    private int getOutOfPlace()
    {
        int countOutOfPlace=0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                if(numbers[i][j]!= goal[i][j] && numbers[i][j]!=0){
                    countOutOfPlace++;
                }
            }
        }
        return countOutOfPlace;
    }

    /**
     * Two compare Function F such F= g+h
     * so that priority queue could be sorted with lowest F at front of queue
     * @param o1
     * @param o2
     * @return
     */
    @Override
    public int compare(PuzzleState o1, PuzzleState o2) {
        return o1.getF() - o2.getF();
    }

    /**
     * Method to compare two puzzle states
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj  instanceof PuzzleState){
            PuzzleState puzzleState = (PuzzleState) obj;
            boolean isEqual=true;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {

                    if(numbers[i][j]!= puzzleState.numbers[i][j]){
                        isEqual=false;
                        break;
                    }
                }
            }
            return isEqual;
        }
        return false;
    }

    public void display(){

       /* if(!validate(convert2dTo1d(numbers))){
            return;
        }*/
        System.out.println("LEVEL IS "+level+" and function is "+ h);
        for (int i = 0; i < 3; i++) {
            String line="";
            for (int j = 0; j < 3; j++) {
                line+=numbers[i][j]+" ";
            }
            System.out.println(line);
        }
    }

    @Override
    public String toString() {
        String line="";
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                line+=numbers[i][j]+" ";
            }
            line+="\n";
        }
        return line;
    }

    protected PuzzleState clonePuzzle() {
        int[][] nums = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                nums[i][j]= numbers[i][j];
            }
        }
        return new PuzzleState(nums);
    }

    public int getF() {
        f=g+h;
        return f;
    }


    public int calculateSeq(){
        int total=0;
        for (int i = 1; i <=8; i++) {
            Position position = positionOfNumber(numbers, i);
            // if central tile then add 1
            if(position.x==1&&position.y==1){
                total+=1;
            }else if(!checkNumberAndProperSuccessor(goal,i,position)){
                total+=2;
            }
        }
        return total;
    }

    /**
     * helper method for heuristic calculation
     * @param numbers
     * @param i
     * @param position
     * @return
     */
    public boolean checkNumberAndProperSuccessor(int[][] numbers, int i,Position position) {
        int successor = 0;
        if(position.x==0&& position.y<2){
            successor= numbers[position.x][position.y+1];
        }else if(position.y==2 && position.x<2){
            successor= numbers[position.x+1][position.y];
        }else if(position.x==2 && position.y>0){
            successor= numbers[position.x][position.y-1];
        }else if(position.y==0 && position.x> 0){
            successor= numbers[position.x-1][position.y];
        }else if(position.x==1&&position.y==0){
            successor=8;
        }

        return i + 1 == successor || (successor == 1 && i == 8);
    }

    public int getLevel() {
        return level;
    }

    public void setPrevStates(List<PuzzleState> prevStates) {
        this.prevStates = prevStates;
    }

    public void displaySolutionStates(){
        for (PuzzleState state :
                prevStates) {
            state.display();
        }
        display();
    }
}
