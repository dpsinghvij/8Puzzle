package ai.assignment.first.ai;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davinder on 6/2/17.
 */
public class StripsMethod {

    private List<Goal> preconditions;
    private List<Goal> addList;
    private List<Goal> deleteList;

    public StripsMethod(List<Goal> preconditions, List<Goal> addList, List<Goal> deleteList) {
        this.preconditions = preconditions;
        this.addList = addList;
        this.deleteList = deleteList;
    }

    public List<Goal> searchAddList(Goal goal) {
        List<Goal> goals = new ArrayList<>();
        for (Goal addGoal :
                addList) {
            if (addGoal.getMethod().getName().equals(goal.getMethod().getName())) {
                goals.addAll(addList);
                goals.addAll(preconditions);
                return goals;
            }
        }
        return goals;
    }
}
