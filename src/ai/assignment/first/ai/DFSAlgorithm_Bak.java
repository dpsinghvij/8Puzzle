package ai.assignment.first.ai;

import ai.assignment.first.models.PuzzleState;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by davinder on 20/2/17.
 */
public class DFSAlgorithm_Bak {

    int[][] input;
    Queue<PuzzleState> stateQueue;
    Queue<PuzzleState> exploredQueue;
    private int[][] goal= {
            {1,2,3},
            {8,0,4},
            {7,6,5}
    };
    PuzzleState goalState;
    PuzzleState initialState;

    public DFSAlgorithm_Bak(int[][] initial) {
        initialState= new PuzzleState(initial);
        goalState=new PuzzleState(goal);
        stateQueue= new LinkedList<>();
        stateQueue.add(initialState);
        exploredQueue= new LinkedList<>();
    }

    public boolean dfs(PuzzleState state, int depth){
        if(state.equals(goalState)){
            System.out.println("Solution reached \n"+state);
            return true;
        }
        if(depth==0){
            return false;
        }
        List<PuzzleState> newStates = state.getNewStates();
        boolean cutOffOccured= false;
        for (PuzzleState child :newStates
             ) {
         //   child.display();
            boolean cutoff=dfs(child,depth-1);
            if(!cutoff){
                cutOffOccured=true;
            }
        }
        if(cutOffOccured){
            return false;
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
