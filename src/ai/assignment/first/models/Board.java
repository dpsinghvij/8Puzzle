package ai.assignment.first.models;

import com.sun.deploy.util.ArrayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davinder on 4/2/17.
 * A class to model Board
 * It takes input the numbers in board in 1d array and converts it into 2d array
 * It also implements strips methods given in assignment as Strips Precondition, Actions
 */
public class Board {

    private int numbers[][]= new int[3][3];

    public Board(int[] input) {
        convert2d(input);
    }

    /**
     * Convert 1d array to 2d array
     * @param input 1d array
     */
    private void convert2d(int[] input) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                numbers[i][j]= input[3*i+j];
            }
        }

    }


    /**
     * Validate input 1d array, Validation will fail if
     * 1. Input is less than 9
     * 2. Input has duplicates
     * 3. Input has more than one zero
     * 4. no number is greater than 9
     * @param array
     * @return
     */
    public static boolean validate(int[] array){
        int count= array.length;

        if(count<9){
            return false;
        }
        for (int i = 0; i < count - 1; i++) {
            for (int j = i + 1; j < count; j++) {

                if (array[i] == array[j]) {
                    return false;
                }

            }
        }

        for (int anArray : array) {
            if (anArray >9) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if the index is 0 or not
     * @param array int array, with index 0 as i and index 1 as j co-ordinate, If you want to check
     *              for location (1,1) pass array as [1,1]
     * @return true if there is 0 at particular location
     */
    public boolean clear(int[] array){

        int i=array[0];
        int j=array[1];
        return numbers[i][j] == 0;

    }

    /**
     * Clears board at the given index
     * @param coordinates int array, with index 0 as i and index 1 as j co-ordinate, If you want to clear
     *              location (1,1) pass array as [1,1]
     * @return true if it was able to clear
     */
    public boolean clearMove(int[] coordinates){

        int i=coordinates[0];
        int j=coordinates[1];
        numbers[i][j] = 0;
        String moveFormat = "move: action Clear(%s,%s)";
        moveFormat= String.format(moveFormat,i,j);
        System.out.println(moveFormat);
        displayBoard();
        return numbers[i][j] == 0;

    }

    /**
     * Checks whether the input co-ordinates are adjacent or not
     * @param array int array, with first two elements as i,j and last two elements as k,l. So for
     *              input [1,0,1,1] suggest that i=1, j=0 and k=1, l=1
     * @return
     */
    public boolean adj(int[] array){

        int i=array[0];
        int j=array[1];
        int k=array[2];
        int l=array[3];
        int tempX=0,tempY=0;
        tempX= i+1;
        tempY=j;
        if(equalChk(k, l, tempX, tempY))
            return true;

        tempX= i-1;
        tempY=j;
        if(equalChk(k, l, tempX, tempY))
            return true;

        tempX= i;
        tempY=j-1;
        if(equalChk(k, l, tempX, tempY))
            return true;

        tempX= i;
        tempY=j+1;
        return equalChk(k, l, tempX, tempY);

    }

    /**
     * Checks if the index contains the input number or not
     * @param array int array, with index 0 as number and index 1&2  as i,j co-ordinates, If you want to
     *              check for location (1,1) pass array as [1,1]
     * @return true if there is 0 at particular location
     */
    public boolean on(int[] array){
        int x=array[0];
        int i=array[1];
        int j=array[2];
        return numbers[i][j]==x;
    }

    /**
     * On action- sets the co-ordinates i,j with the number x as input
     * @param array int array, with index 0 as number and index 1&2  as i,j co-ordinates, If you want to
     *              set location (1,1) as 5, then pass array as [5,1,1]
     * @return
     */
    public boolean onmove(int[] array){
        int x=array[0];
        int i=array[1];
        int j=array[2];
        numbers[i][j]=x;
        String moveFormat = "move: action On(%s,%s,%s)";
        moveFormat= String.format(moveFormat,x,i,j);
        System.out.println(moveFormat);
        displayBoard();
        return numbers[i][j]==x;
    }

    /**
     * Helper method for adjacency check
     */
    private boolean equalChk(int k, int l, int tempX, int tempY) {
        return tempX == k && tempY == l;
    }

    /*public void move(int x, int i, int j, int k, int l){
        // check preconditions
        if(on(x,i,j)&&clear(k,l)&& adj(i,j,k,l)){
            // take actions
            // set k,l
            numbers[k][l]=x;
            numbers[i][j]=0;
            displayBoard();
        }else {
            System.out.println("choose correct number");
        }
    }
*/
    /*public void move(int x, int k,int l){
        int i=0,j=0;
        outerloop:
        for (i = 0; i < 3; i++) {
            for (j = 0; j < 3; j++) {
                if(numbers[i][j]==x){
                    break outerloop;
                }
            }
        }

        move(x,i,j,k,l);
    }*/

    /**
     * Display Board as output
     */
    public void displayBoard(){

       /* if(!validate(convert2dTo1d(numbers))){
            return;
        }*/

        for (int i = 0; i < 3; i++) {
            String line="";
            for (int j = 0; j < 3; j++) {
                line+=numbers[i][j]+" ";
            }
            System.out.println(line);
        }
    }



    public int[] convert2dTo1d(int[][] numbers){
        int[] oneDArray= new int[9];
        int index=0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                oneDArray[index]= numbers[i][j];
                index++;
            }
        }
        return oneDArray;
    }
    /*public boolean isPuzzleSolved(){
        return on(1,0,0)&& on(2,0,1)&&on(3,0,2)
                &&on(8,1,0)&&on(0,1,1)&&on(4,1,2)
                &&on(7,2,0)&&on(6,2,1)&&on(5,2,2);
    }*/

    /**
     * Get i,j co-ordinates of number
     * @param x  is the number for which i,j is required
     * @return an array of i,j elements
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
     * Returns co-ordinates of the number
     * @param i
     * @param j
     * @return number
     */
    public int numberAtPos(int i,int j){
        return numbers[i][j];
    }

    //TODO
    // Find adjacent numbers to zero element
    // Make a move such that zero is moved
    // check whether the move is not being used in previous state

    private static class Position{
        enum MOVETYPE{
            UP,DOWN,LEFT,RIGHT;
        }
        int x;
        int y;

        Position getPosition(MOVETYPE movetype){
            Position p= new Position();
            switch (movetype){
                case UP:
                    if(y==0){
                        return null;
                    }
                    p.x= x;
                    p.y=--y;
                    return p;
                case DOWN:
                    if(y==2){
                        return null;
                    }
                    p.x= x;
                    p.y= ++y;
                    return p;
                case LEFT:
                    if(x==0){
                        return null;
                    }
                    p.x=--x;
                    p.y=y;
                    return p;
                case RIGHT:
                    if(x==2){
                        return null;
                    }
                    p.x=++x;
                    p.y=y;
                    return p;
                default:
                    return null;
            }
        }

    }

    public List<Position> getNumberPosAdjacentZero(){
        int[] currentPosZero= currentPosition(0);
        List<Position> positions= new ArrayList<>();
        Position zeroPos= new Position();
        zeroPos.x= currentPosZero[0];
        zeroPos.y= currentPosZero[1];

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


    private int[][] getNewState(Position position){
        int[] currentPosZero= currentPosition(0);
        int numberToBeSwappedWith = numberAtPos(position.x,position.y);
        int[][] boardState = numbers;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(i==position.x && j==position.y){
                    boardState[i][j]=0;
                    boardState[currentPosZero[0]][currentPosZero[1]]=numberToBeSwappedWith;
                    break;
                }
            }
        }
        return boardState;
    }

    public int[][] getNumbers() {
        return numbers;
    }
}
