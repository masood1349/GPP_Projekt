package interfaces;

import java.awt.Point;
import java.util.ArrayList;

/**
 * This interface just needs to be able to return an array of points, referring
 * to the grid-positions of the chosen seats.
 * This class will be the one showing the representation of the seats and lets 
 * the user choose a seating.
 * This will probably be done with a GUI, with an attached MouseListener.
 * @author Jakob Lautrup Nysom (jaln@itu.dk)
 * @version 21-Nov-2013
 */
public interface ISeatChooser {
    public void setFlight(IFlight flight); // Sets the flight to choose a 
    // seating on.
    public ArrayList<Point> getSeats(int numberOfSeats); // Returns the 
}
