package ai.assignment.first.models;

/**
 * Created by davinder on 20/2/17.
 */
public class Position{
    enum MOVETYPE{
        UP,DOWN,LEFT,RIGHT;
    }
    public int x;
    public int y;

    /**
     * Finds the position where swap could be made
     * @param movetype
     * @return
     */
    Position getPosition(Position.MOVETYPE movetype){
        Position p= new Position();
        switch (movetype){
            case UP:
                if(y==0){
                    return null;
                }
                p.x= x;
                p.y=y-1;
                return p;
            case DOWN:
                if(y==2){
                    return null;
                }
                p.x= x;
                p.y= y+1;
                return p;
            case LEFT:
                if(x==0){
                    return null;
                }
                p.x=x-1;
                p.y=y;
                return p;
            case RIGHT:
                if(x==2){
                    return null;
                }
                p.x=x+1;
                p.y=y;
                return p;
            default:
                return null;
        }
    }

}
