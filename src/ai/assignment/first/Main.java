package ai.assignment.first;

import ai.assignment.first.ai.AstarAlgorithm;
import ai.assignment.first.ai.BFSAlgorithm;
import ai.assignment.first.ai.DFSAlgorithm;
import ai.assignment.first.ai.GameSingleton;
import ai.assignment.first.models.Board;
import ai.assignment.first.models.PuzzleState;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Scanner s = new Scanner(System.in);
        //int[] numbers= new int[]{1,2,3,8,6,4,7,5,0};
      //  int[] numbers= new int[]{1,2,3,8,4,5,7,6,0};
      //  int[] numbers= new int[]{1,2,3,7,8,4,0,6,5};

      //  int[] numbers= new int[]{0,8,6,2,1,4,5,7,3};
      //    int[] numbers= new int[]{2,1,6,4,0,8,7,5,3};
       //  int[] numbers= new int[]{2,1,6,4,0,8,7,5,3};
       //   int[] numbers= new int[]{1,3,4,8,0,2,7,6,5};
        int[] numbers= new int[]{2,8,3,1,6,4,7,0,5};
        // Take input
        /*int[] numbers=new int[9];
        System.out.println("Please enter numbers for 8-Puzzle");
        for (int i = 0; i < 9; i++) {
            if(s.hasNextInt()){
                numbers[i]= s.nextInt();
                System.out.println("Please enter next number");
            }else {
                System.out.println("Please enter an integer");
            }
        }*/
       // Validate input
        if(!Board.validate(numbers)) {
            System.err.println("Invalidate input, Please re-enter data");
          return;
        }
        Board board = new Board(numbers);
        System.out.println("Initial Configuration of Board");
        board.displayBoard();
        /*BFSAlgorithm bfsAlgorithm= new BFSAlgorithm(board.getNumbers());
        bfsAlgorithm.execute();*/
        /*DFSAlgorithm dfsAlgorithm= new DFSAlgorithm(board.getNumbers());
        dfsAlgorithm.execute();*/
        /*AstarAlgorithm astarAlgorithm= new AstarAlgorithm(board.getNumbers(), PuzzleState.Heuristics.MANHATTAN);
        astarAlgorithm.execute();*/
        /*PuzzleState puzzleState= new PuzzleState(board.getNumbers());
        System.out.println(puzzleState.calculateSeq());*/
        Scanner scanner= new Scanner(System.in);
        int option = 0;
        do {
            System.out.println("Select following options\n" +
                    "1. depth first search.\n" +
                    "2. breath first search.\n" +
                    "best first search with each of the following heuristics :\n" +
                    "3. depth in the search space + number of tiles out of place.\n" +
                    "4. depth in the search space + minimum number of moves to reach the goal state.\n" +
                    "5. depth in the search space + the heuristic H(mandist +3*totSeq)\n" +
                    "6. Exit the program");
            System.out.println("Please select your option?");

            if(!scanner.hasNextInt()){
                scanner.next();
                continue;
            }
            switch (scanner.nextInt()){
                case 1:
                    System.out.println("Please also enter the depth?");
                    int depth= scanner.nextInt();
                    DFSAlgorithm dfsAlgorithm= new DFSAlgorithm(board.getNumbers(),depth);
                    if(!dfsAlgorithm.execute()){
                        message("sorry it can be solved using DFS");
                    }
                    break;
                case 2:
                    BFSAlgorithm bfsAlgorithm= new BFSAlgorithm(board.getNumbers());
                    if(!bfsAlgorithm.execute()){
                        message("sorry it can be solved using BFS");
                    }
                    break;
                case 3:
                    AstarAlgorithm astarAlgorithmOutOfMov= new AstarAlgorithm(board.getNumbers(), PuzzleState.Heuristics.OUT_OF_PLACE);
                    if(!astarAlgorithmOutOfMov.execute()){
                        message("sorry it can be solved using Out of Move Heuristics");
                    }
                    break;
                case 4:
                    AstarAlgorithm astarMinDis= new AstarAlgorithm(board.getNumbers(), PuzzleState.Heuristics.MIN_MOVES);
                    if(!astarMinDis.execute()){
                        message("sorry it can be solved using Minimum Distance Heuristics");
                    }
                    break;
                case 5:
                    AstarAlgorithm astarWithCustomHeuristic =new AstarAlgorithm(board.getNumbers(), PuzzleState.Heuristics.MANHATTAN);
                    if(!astarWithCustomHeuristic.execute()){
                        message("sorry it can be solved using the given Heuristic");
                    }
                    break;
                default:
                    option=6;
                    break;
            }

        }while (option!=6);

    }

    private static void message(String s) {
        System.out.println(s);
    }
}
