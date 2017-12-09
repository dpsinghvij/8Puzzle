package ai.assignment.first.ai;

import ai.assignment.first.models.Board;
import ai.assignment.first.models.PuzzleState;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by davinder on 20/2/17.
 */
public class DFSAlgorithm {

    int[][] input;
    Stack<PuzzleState> stateQueue;
    Queue<PuzzleState> exploredQueue;
    int nodesCounter=0;
    private int exploredNodes=0;
    private int[][] goal= {
            {1,2,3},
            {8,0,4},
            {7,6,5}
    };
    PuzzleState goalState;
    PuzzleState initialState;
    int depth;

    public DFSAlgorithm(int[][] initial,int depth) {
        initialState= new PuzzleState(initial);
        goalState=new PuzzleState(goal);
        stateQueue= new Stack<>();
        stateQueue.add(initialState);
        exploredQueue= new LinkedList<>();
        this.depth=depth;
    }

    // execute algorithm
    public boolean execute(){
        while (!stateQueue.isEmpty()){
            PuzzleState node = stateQueue.pop();
            exploredNodes++;
            if(goalState.equals(node)){
                System.out.println("Goal State Achieved");
                return true;
            }

            exploredQueue.add(node);
            // generate new nodes
            List<PuzzleState> newStates = node.getNewStates();
            for (PuzzleState state
                    :newStates) {

                if (checkIfStatePresentInExplored(state) || checkIfStatePresentInStateQueue(state)) {
                    continue;
                }

                if(state.getLevel()>depth){
                    break;
                }
                System.out.println("----------");
                state.display();
                nodesCounter++;
                // check if goal is achieved
                if(goalState.equals(state)){
                    System.out.println("Goal State Achieved---> Here is solution \\|/");
                    state.displaySolutionStates();
                    System.out.println("Number of nodes generated were "+nodesCounter);
                    System.out.println("Explored nodes are "+exploredNodes);
                    return true;
                }
                stateQueue.push(state);
            }
        }
        return false;
    }

    private boolean checkIfStatePresentInExplored(PuzzleState state) {
        return checkIfStatePresent(exploredQueue,state);
    }

    private boolean checkIfStatePresentInStateQueue(PuzzleState state) {
        return checkIfStatePresent(stateQueue,state);
    }

    private boolean checkIfStatePresent(Queue<PuzzleState> list,PuzzleState state) {
        for (PuzzleState puzzleState
                :list
                ) {
            if(puzzleState.equals(state)){
                return true;
            }
        }
        return false;
    }

    private boolean checkIfStatePresent(Stack<PuzzleState> list,PuzzleState state) {
        for (PuzzleState puzzleState
                :list
                ) {
            if(puzzleState.equals(state)){
                return true;
            }
        }
        return false;
    }
}
