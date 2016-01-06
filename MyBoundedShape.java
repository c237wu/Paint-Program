import java.awt.Color;
import java.awt.Graphics;

abstract public class MyBoundedShape extends MyShape {

    private boolean filledF; // flag for showing whether the shape is filled or not

    // constructor without input values; initialize the flag to false
    public MyBoundedShape( ) {
        super();
        filledF = false;
    }
    
    // constructor with input values
    public MyBoundedShape( int x1, int y1, int x2, int y2, float strokeWidth, float dashLength, Color color1,
                          Color color2, boolean filledF, boolean gradient, boolean dashed ) {
        super(x1, y1, x2, y2, strokeWidth, dashLength, color1, color2, gradient, dashed);
        this.filledF = filledF;

    }
    
    // return the smallest value of x1 and x2
    public int getUpperLeftX(){
        return Math.min(getX1(),getX2());
    }
    
    // return the smallest value of y1 and y2
    public int getUpperLeftY(){
        return Math.min(getY1(),getY2());
    }
    
    // calculate the width of the shape and return it
    public int getWidth() {
        return Math.abs(getX2() - getX1());
    }
    
    // calculate the height of the shape and return it
    public int getHeight() {
        return Math.abs(getY2() - getY1());
    }
    
    // mutator method for the filled flag
    public void setFilledF(boolean filledF) {
        this.filledF = filledF;
    }
    
    // accessor method for the filled flag
    public boolean getFilledF() {
        return filledF;
    }   
    
    // abstract draw method for the individual shapes to complete
    abstract public void draw(Graphics g);   
}
