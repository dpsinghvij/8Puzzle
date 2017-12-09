package ai.assignment.first.ai;

import ai.assignment.first.models.Board;
import ai.assignment.first.models.PuzzleState;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by davinder on 20/2/17.
 */
public class BFSAlgorithm {

    int[][] input;
    Queue<PuzzleState> stateQueue;    // queue for implementation of bfs
    Queue<PuzzleState> exploredQueue; // the elements which have been explored
    int nodesCounter=0;
    private int[][] goal= {
            {1,2,3},
            {8,0,4},
            {7,6,5}
    };
    PuzzleState goalState;
    PuzzleState initialState;
    private int exploredNodes=0;

    // initialize states
    public BFSAlgorithm(int[][] initial) {
        initialState= new PuzzleState(initial);
        goalState=new PuzzleState(goal);
        stateQueue= new LinkedList<>();
        stateQueue.add(initialState);
        exploredQueue= new LinkedList<>();
    }

    // execute algorithm
    public boolean execute(){
        while (!stateQueue.isEmpty()){
            PuzzleState node = stateQueue.poll();
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
                System.out.println("----------");
                state.display();
                nodesCounter++;
                // check if goal is achieved
                if(goalState.equals(state)){
                    System.out.println("Goal State Achieved");
                    state.displaySolutionStates();
                    System.out.println("Number of nodes generated were "+nodesCounter);
                    System.out.println("Explored nodes are "+exploredNodes);
                    return true;
                }
                stateQueue.add(state);
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
}
