package ai.assignment.first.ai;

import ai.assignment.first.models.Board;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davinder on 7/2/17.
 * A singleton class to initialize goals and Strips
 */
public class GameSingleton {

    public List<Goal> goals = new ArrayList<>();
    private static GameSingleton ourInstance ;
    private StripsStack stripsStack;
    private Board board;
    public static GameSingleton getInstance(Board board) {
        if(ourInstance==null){
           ourInstance = new GameSingleton(board);
        }
        return ourInstance;
    }

    /**
     * Constructor to initialize to goal state
     * @param board
     */
    private GameSingleton(Board board) {
        this.board=board;
        goals.add(new Goal(Goal.GoalType.SINGLE_GOAL,"on",new int[]{1,0,0}));
        goals.add(new Goal(Goal.GoalType.SINGLE_GOAL,"on",new int[]{2,0,1}));
        goals.add(new Goal(Goal.GoalType.SINGLE_GOAL,"on",new int[]{3,0,2}));
        goals.add(new Goal(Goal.GoalType.SINGLE_GOAL,"on",new int[]{8,1,0}));
        goals.add(new Goal(Goal.GoalType.SINGLE_GOAL,"on",new int[]{0,1,1}));
        goals.add(new Goal(Goal.GoalType.SINGLE_GOAL,"on",new int[]{4,1,2}));
        goals.add(new Goal(Goal.GoalType.SINGLE_GOAL,"on",new int[]{7,2,0}));
        goals.add(new Goal(Goal.GoalType.SINGLE_GOAL,"on",new int[]{6,2,1}));
        goals.add(new Goal(Goal.GoalType.SINGLE_GOAL,"on",new int[]{5,2,2}));
        for (Goal goal:
                goals) {
            goal.initializeGoal(board);
        }
        stripsStack= new StripsStack(board);
        stripsStack.pushGoals(goals);
    }


}
