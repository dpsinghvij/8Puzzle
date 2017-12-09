package ai.assignment.first.ai;

import ai.assignment.first.models.PuzzleState;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by davinder on 24/2/17.
 */
public class AstarAlgorithm {

    Queue<PuzzleState> stateQueue;
    Queue<PuzzleState> exploredQueue;
    private int[][] goal= {
            {1,2,3},
            {8,0,4},
            {7,6,5}
    };
    PuzzleState goalState;
    PuzzleState initialState;
    PuzzleState.Heuristics heuristics;
    private int nodesCounter=0;
    private int exploredNodes=0;

    public AstarAlgorithm(int[][] initial, PuzzleState.Heuristics heuristics) {
        initialState= new PuzzleState(initial);
        goalState=new PuzzleState(goal);
        PuzzleState puzzleState= new PuzzleState();
        stateQueue= new PriorityQueue<PuzzleState>(20,puzzleState);
        stateQueue.add(initialState);
        exploredQueue= new LinkedList<>();
        this.heuristics=heuristics;
    }

    /**
     * Contains the A star algorithm
     * @return
     */
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
            List<PuzzleState> newStates = node.getNewStates(heuristics);
            for (PuzzleState state
                    :newStates) {

                if (checkIfStatePresentInExplored(state) || checkIfStatePresentInStateQueue(state)) {
                    continue;
                }
                System.out.println("----------");
                state.display();
                nodesCounter++;
                // check for the goal state
                if(goalState.equals(state)){
                    System.out.println("Goal State Achieved---> Here is solution \\|/");
                    state.displaySolutionStates();
                    System.out.println("Number of nodes generated were "+nodesCounter);
                    System.out.println("Explored nodes are "+exploredNodes);
                    return true;
                }
                // add node in queue
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
