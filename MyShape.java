import java.awt.Color;
import java.awt.Graphics;

abstract public class MyShape {
    
    private int x1;
    private int x2;
    private int y1;
    private int y2;
    private Color myColor;
    private Color myColor2;
    private float strokeWidth;
    private float dashLength;
    private boolean dashed;
    private boolean gradient;
    
    // constructor without input values
    public MyShape( ) {
        x1 = 0;
        y1 = 0;
        x2 = 0;
        y2 = 0;
        strokeWidth = 1;
        dashLength = 1;
        myColor = Color.BLACK;
        myColor2 = Color.BLACK;
        dashed = false;
        gradient = false;
    }
    
    // constructor with input values; overloaded
    public MyShape( int x1, int y1, int x2, int y2, float strokeWidth, float dashLength, Color color, Color color2,
                   boolean gradient, boolean dashed ) {
        this.x1 = Math.min(x1,x2);
        this.x2 = Math.max(x1,x2);
        this.y1 = Math.min(y1,y2);
        this.y2 = Math.max(y1,y2);
        myColor = color;
        myColor2 = color2;
        this.strokeWidth = strokeWidth;
        this.dashLength = dashLength;
        this.dashed = dashed;
        this.gradient = gradient;
    }
    
    // mutator method for x1
    public void setX1(int x1) {
        this.x1 = x1;
    }
    
    // mutator method for x2
    public void setX2(int x2) { 
        this.x2 = x2;
    }
    
    // mutator method for y1
    public void setY1(int y1) {
        this.y1 = y1;
    }
    
    // mutator method for y2
    public void setY2(int y2) {
        this.y2 = y2;
    }
    
    // mutator method for first color
    public void setColor(Color color) {
        myColor = color;
    }
    
    // mutator method for second color
    public void setColor2(Color color) {
        myColor2 = color;
    }
    
    // mutator method for gradient
    public void setGradient(boolean gradient){
        this.gradient = gradient;
    }
    
    // mutator method for dashed
    public void setDashed(boolean dashed){
        this.dashed = dashed;
    }
    
    // mutator method for storkeWidth
    public void setStrokeWidth(int strokeWidth){
        this.strokeWidth = strokeWidth;
    }
    
      // mutator method for storkeWidth
    public void setDashLength(int dashLength){
        this.dashLength = dashLength;
    }
    
    // accessor method for x1
    public int getX1() {
        return x1;
    }
    
    // accessor method for x2
    public int getX2() {
        return x2;
    }
    
    // accessor method for y1
    public int getY1() {
        return y1;
    }
    
    // accessor method for y2
    public int getY2() {
        return y2;
    }
    
    // accessor method for color
    public Color getColor() {
        return myColor;
    } 
    
    // accessor method for color
    public Color getColor2() {
        return myColor2;
    } 
    
    // accessor method for strokeWidth
    public float getStrokeWidth() {
        return strokeWidth;
    }

    // accessor method for dashLength
    public float getDashLength() {
        return dashLength;
    } 
    
    // accessor method for dashed
    public boolean getDashed(){
        return dashed;
    }
    
    // accessor method for gradient
    public boolean getGradient(){
        return gradient;
    }
    
    // for subclasses to fill out for its individual shape
    abstract public void draw(Graphics g);
    
}// end class MyShape
