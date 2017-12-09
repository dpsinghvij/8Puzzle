package ai.assignment.first.ai;

import ai.assignment.first.models.Board;

import java.lang.reflect.Method;

/**
 * Created by davinder on 6/2/17.
 * Gives information about Goal whether it is Single Goal or Action.
 * It also stores information regarding reflection methods and there arguments
 */
public class Goal {



    public enum GoalType{
        COMPOUND_GOAL, SINGLE_GOAL,ACTION
    }

    private GoalType goalType;      // Goal type
    private Method method;          // method for reflection
    private String methodName;
    private int[] arguments;


    public Goal(GoalType goalType, String methodName, int[] arguments) {
        this.goalType = goalType;
        this.methodName = methodName;
        this.arguments = arguments;
    }

    /**
     * A very important method to initialize Reflection Method of Board object
     * @param board
     */
    public void initializeGoal(Board board){
        Method[] methods = board.getClass().getMethods();
        for (Method method :methods
             ) {
            if(method.getName().equals(methodName)){
                this.method= method;
            }
        }
    }

    public GoalType getGoalType() {
        return goalType;
    }

    public void setGoalType(GoalType goalType) {
        this.goalType = goalType;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public int[] getArguments() {
        return arguments;
    }

    public void setArguments(int[] arguments) {
        this.arguments = arguments;
    }


}
