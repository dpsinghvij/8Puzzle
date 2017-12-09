package ai.assignment.first.ai;

import ai.assignment.first.models.Board;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by davinder on 6/2/17.
 * StripsStack implements the STRIPS logic, it check for the actions in action list and loads goals
 */
public class StripsStack {

    private Board board;            // Board model
    private Stack<Goal> goalStack;  // a stack of goals

    public StripsStack(Board board) {
        this.board = board;
        goalStack= new Stack<>();
    }


    /**
     * Pop goal from stack
     * @return
     */
    private Goal popGoal(){
        return goalStack.pop();
    }

    /**
     * Push goal in stack
     * @param goal
     */
    public void pushGoal(Goal goal){
        goalStack.push(goal);
    }

    /**
     * Push multiple goals in stac
     * @param goals
     */
    public void pushGoals(List<Goal> goals){
        goalStack.addAll(goals);
    }

    /**Checks if goal condition is satisfied
     * @param goal
     * @return
     */
    private boolean isGoalSatisfied(Goal goal){
        return executeGoal(goal);
    }

    /**
     * Check the goal at top of the stack without popping it out from stack
     * @return
     */
    private Goal peekGoal(){
       return goalStack.peek();
    }


    /**
     * Strips implementation
     * @return
     */
    public boolean runStrips(){
        // check if goal stack is empty
        while (!goalStack.isEmpty()) {

            // Take out one goal
            Goal goal = peekGoal();

            //Check type of goal
            switch (goal.getGoalType()) {
                case SINGLE_GOAL:
                    // check if the goal is satisfied
                    if(isGoalSatisfied(goal)){
                        // remove goal if it is satisfied
                        popGoal();
                    }else {
                        // load the actions and preconditions as goal
                        if(goal.getMethod().getName().equals("on")){
                            addActionAndPrecondition(goal);
                        }else {
                            // Strip fails if action is not found
                            return false;
                        }
                    }
                    break;
                case COMPOUND_GOAL:
                    break;
                case ACTION:
                    // if goal is action type then we will execute goal
                    popGoal();
                    executeGoal(goal);
                    break;
            }


        }

        return true;
    }

    /**
     * Executes goal and return true if it is successfully executed
     * @param goal
     * @return
     */
    private boolean executeGoal(Goal goal){
        Method method= goal.getMethod();
        try {
            // reflection apis are used here to make code more generic
            Object invoke = method.invoke(board, goal.getArguments());
            return (boolean) invoke;
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * Loads actions and preconditions for a given goal
     * @param goal
     */
    private void addActionAndPrecondition(Goal goal){

        popGoal();
        int[] arguments= goal.getArguments();
        int x= arguments[0];
        int k= arguments[1];
        int l= arguments[2];
        int[] ints = board.currentPosition(x);
        int i=ints[0];
        int j=ints[1];

        Goal clear;
        if(x!=0) {
            clear = new Goal(Goal.GoalType.ACTION, "clearMove", new int[]{i, j});
        }else {
            // STRIPS have been tweaked a little bit for certain scenarios in which
            // we have to move 0. As in such scenario we would need to preserve old value
            // so that it could be moved instead of clearing it
            int oldvalue=board.numberAtPos(k,l);
            clear = new Goal(Goal.GoalType.ACTION, "onmove", new int[]{oldvalue,i, j});
        }
        clear.initializeGoal(board);
        goalStack.push(clear);
        Goal onmove = new Goal(Goal.GoalType.ACTION, "onmove", new int[]{x, k, l});
        onmove.initializeGoal(board);
        goalStack.push(onmove);
        Goal on = new Goal(Goal.GoalType.SINGLE_GOAL, "on", new int[]{x, i, j});
        on.initializeGoal(board);
        goalStack.push(on);
        // for x==0 clear check will always fails, so strips have been tweaked here
        if(x!=0) {
            Goal clear1 = new Goal(Goal.GoalType.SINGLE_GOAL, "clear", new int[]{k, l});
            clear1.initializeGoal(board);
            goalStack.push(clear1);
        }

        Goal adj = new Goal(Goal.GoalType.SINGLE_GOAL, "adj", new int[]{i, j, k, l});
        adj.initializeGoal(board);
        goalStack.push(adj);
    }
}
