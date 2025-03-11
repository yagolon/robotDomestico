import jason.environment.grid.GridWorldModel;
import jason.environment.grid.Location;

/** class that implements the Model of Domestic Robot application */
public class HouseModel extends GridWorldModel {

    // constants for the grid objects
    public static final int FRIDGE = 16;
    public static final int OWNER  = 32;

    // the grid size
    public static final int GSize = 7;

    boolean fridgeOpen   = false; // whether the fridge is open
    boolean carryingBeer = false; // whether the robot is carrying beer
    int sipCount        = 0; // how many sip the owner did
    int availableBeers  = 2; // how many beers are available

    Location lFridge = new Location(0,0);
    Location lOwner  = new Location(GSize-1,GSize-1);

    public HouseModel() {
        // create a 7x7 grid with one mobile agent
        super(GSize, GSize, 2);

        // initial location of robot (column 3, line 3)
        // ag code 0 means the robot
        setAgPos(0, GSize/2, GSize/2);
        setAgPos(1, GSize/2, GSize/2);

        // initial location of fridge and owner
        add(FRIDGE, lFridge);
        add(OWNER, lOwner);
    }

    boolean openFridge() {
        if (!fridgeOpen) {
            fridgeOpen = true;
            return true;
        } else {
            return false;
        }
    }

    boolean closeFridge() {
        if (fridgeOpen) {
            fridgeOpen = false;
            return true;
        } else {
            return false;
        }
    }

    boolean moveTowards(Location dest) {
        Location r1 = getAgPos(0);
        Location r2 = getAgPos(1);
        /*TESTS MOVIMIENTO 
            if(r1.x == dest.x && r1.y == dest.y){
            setAgPos(0, r2);
                if(r1.x++!=6) r1.x++;
                else if (r1.y++!=6) r1.y++;
                else r1.x--;
            }
        else 
        */ 
        
        
        if (r1.x+1 < dest.x)        r1.x++;
        else if (r1.x-1 > dest.x)   r1.x--;

        if (r1.y+1 < dest.y)        r1.y++;
        else if (r1.y-1 > dest.y)   r1.y--;

        if (r2.x < dest.x)        r2.x++;
        else if (r2.x > dest.x)   r2.x--;

        if (r2.y < dest.y)        r2.y++;
        else if (r2.y > dest.y)   r2.y--;
        if(r2.x == dest.x && r2.y == dest.y){
            setAgPos(1, r2);
        }

        setAgPos(0, r1); // move the robot in the grid

        
        // repaint the fridge and owner locations
        if (view != null) {
            view.update(lFridge.x,lFridge.y);
            view.update(lOwner.x,lOwner.y);
        }
        return true;
    }

    boolean getBeer() {
        if (fridgeOpen && availableBeers > 0 && !carryingBeer) {
            availableBeers--;
            carryingBeer = true;
            if (view != null)
                view.update(lFridge.x,lFridge.y);
            return true;
        } else {
            return false;
        }
    }

    boolean addBeer(int n) {
        availableBeers += n;
        if (view != null)
            view.update(lFridge.x,lFridge.y);
        return true;
    }

    boolean handInBeer() {
        if (carryingBeer) {
            sipCount = 10;
            carryingBeer = false;
            if (view != null)
                view.update(lOwner.x,lOwner.y);
            return true;
        } else {
            return false;
        }
    }

    boolean sipBeer() {
        if (sipCount > 0) {
            sipCount--;
            if (view != null)
                view.update(lOwner.x,lOwner.y);
            return true;
        } else {
            return false;
        }
    }
}
