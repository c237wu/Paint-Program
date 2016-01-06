import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.lang.Exception;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.lang.NullPointerException;

public class DrawPanel extends JPanel {
    
    private DynamicStack<MyShape> shapeObjects;
    private int currentShapeType;
    private MyShape currentShapeObject;
    private Color currentShapeColor;
    private Color secondColor;
    private boolean currentShapeFilled;
    private boolean gradient;
    private boolean dashed;
    private JLabel statusLabel;
    private ArrayList<MyShape> clearedShapes;
    private float strokeWidth;
    private float dashLength;
    private final int LINE = 0;
    private final int RECTANGLE = 1;
    private final int OVAL = 2;
    private final Color COLORS[] = 
    {Color.BLACK,Color.BLUE,Color.CYAN,Color.DARK_GRAY,Color.GRAY,Color.LIGHT_GRAY,Color.GREEN,Color.MAGENTA,
    Color.ORANGE,Color.PINK,Color.RED,Color.WHITE,Color.YELLOW};
    private final String COLOR_NAMES[] =
    {"Black","Blue","Cyan","Dark gray","Gray","Light gray","Green","Magenta",
        "Orange","Pink","Red","White","Yellow"};
    
    // constructor
    public DrawPanel(JLabel label) {
        super();
        shapeObjects = new DynamicStack<MyShape>();
        clearedShapes = new ArrayList<MyShape>();
        setBackground(Color.WHITE);
        statusLabel = label;
        
        String fileName = "data.txt";
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            ArrayList<String> records = new ArrayList<String>();
            String oneLine = null;
            while ((oneLine = bufferedReader.readLine()) != null){ // if there is a line to be read
                records.add(oneLine); // the line is saved into records
            }
            bufferedReader.close(); // close the file
            
            // records are applied in order
            currentShapeColor = COLORS[Integer.parseInt(records.get(0))];
            secondColor = COLORS[Integer.parseInt(records.get(1))];
            currentShapeType = Integer.parseInt(records.get(2));
            currentShapeFilled = Boolean.parseBoolean(records.get(3));
            gradient = Boolean.parseBoolean(records.get(4));
            dashed = Boolean.parseBoolean(records.get(5));
            strokeWidth = Float.parseFloat(records.get(6));
            dashLength = Float.parseFloat(records.get(7));
        }
        catch (FileNotFoundException e){ // if this file has not been created i.e. the user hasn't accessed the preference window yet
            currentShapeColor = Color.BLACK;
            secondColor = Color.BLACK;
            currentShapeType = LINE;
            currentShapeFilled = false;
            gradient = false;
            dashed = false;
            strokeWidth = 1;
            dashLength = 1;
        }
        catch (IOException e){
            JOptionPane.showMessageDialog(null,"Error reading to file: "+fileName, "Error",JOptionPane.WARNING_MESSAGE);
        }

        MouseHandler handler = new MouseHandler();
        addMouseListener(handler);
        addMouseMotionListener(handler);
    }
    
    // call paintComponent to draw shapes
    public void paintComponent(Graphics g){
        
        super.paintComponent(g);
        
        MyShape[] tempList = new MyShape[shapeObjects.size()];
        MyShape tempObject;
        
        // the objects in the stack is moved to the temporary list
        for (int i = 0; i<tempList.length;i++){
            tempList[i] = shapeObjects.pop();
        }
        
        // shapes in the list are drawn starting from the end and then put back into the list
        for (int i = tempList.length-1; i>=0 ;i--){
            tempList[i].draw(g);
            shapeObjects.push(tempList[i]);
        }
        
        // if there is a currentShapeObject, draw it too
        if (currentShapeObject != null)
            currentShapeObject.draw(g);
    }
    
    // clear the last shape drawn, if there is one
    public void clearLastShape(){
        if (!shapeObjects.isEmpty()){
            clearedShapes.add(shapeObjects.pop());
            repaint();
        }
    }
    
    // redraw the last shape cleared, if there was one
    public void redoLastShape(){
        if (!clearedShapes.isEmpty()){
            shapeObjects.push(clearedShapes.get(clearedShapes.size()-1));
            clearedShapes.remove(clearedShapes.size()-1);
        }
        repaint();
    }
    
    // accessor method for currentShapyType
    public int getCurrentShapeType(){
        return currentShapeType;
    }
    
    // accessor method for the index of currentShapeColor in the COLORS list
    public int getCurrentShapeColorIndex(){
        for (int i = 0; i<COLORS.length;i++){
            if (currentShapeColor == COLORS[i])
                    return i;
        }
        return -1;
    }
    
    // accessor method for the index of second gradient shape color in the COLORS list
    public int getSecondColorIndex(){
        for (int i = 0; i<COLORS.length;i++){
            if (secondColor == COLORS[i])
                    return i;
        }
        return -1;
    }
    
    // accessor method for currentShapeFilled
    public boolean getCurrentShapeFilled(){
        return currentShapeFilled;
    }
    
    // accessor method for gradient
    public boolean getGradient(){
        return gradient;
    }
    
    // accessor method for stroke width
    public float getStrokeWidth(){
        return strokeWidth;
    }
    
    // accessor method for stroke dash length
    public float getDashLength(){
        return dashLength;
    }
    
    // mutator method for dashed
    public boolean getDashed(){
        return dashed;
    }    
    
    // mutator method for currentShapeType
    public void setCurrentShapeType(int type){
        currentShapeType = type;
    }
    
    // mutator method for currentShapeColor
    public void setCurrentShapeColor(Color color){
        currentShapeColor = color;
    }
    
    // mutator method for secondaryColor
    public void setSecondColor(Color color){
        secondColor = color;
    }
    
    // mutator method for currentShapeFilled
    public void setCurrentShapeFilled(boolean filled){
        currentShapeFilled = filled;
    }
    
    // mutator method for gradient
    public void setGradient(boolean gradient){
        this.gradient = gradient;
    }
    
    // mutator method for stroke width
    public void setStrokeWidth(float strokeWidth){
        this.strokeWidth = strokeWidth;
    }
    
    // mutator method for stroke dash length
    public void setDashLength(float dashLength){
        this.dashLength = dashLength;
    }
    
    // mutator method for dashed
    public void setDashed(boolean dashed){
        this.dashed = dashed;
    }
    
    // clear all shapes; no redo
    public void clearDrawing(){
        while (!shapeObjects.isEmpty()){
            shapeObjects.pop();
        }
        
        clearedShapes.clear();
        
        repaint();
    }
    
    // capture drawing
    public void saveDrawing(){
        try {
            BufferedImage image = new BufferedImage(getSize().width,getSize().height, BufferedImage.TYPE_INT_RGB);
            paint(image.createGraphics());
            String name = JOptionPane.showInputDialog(null,"Please enter a name for the file:","Saving",
                                        JOptionPane.INFORMATION_MESSAGE);
            ImageIO.write(image,"jpg",new File(name+".jpg"));
        }
        catch(IOException e){
            JOptionPane.showMessageDialog(null,"Error saving to file", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    // inner class that handles mouse events
    private class MouseHandler extends MouseAdapter {
        
        // when the mouse is pressed
        public void mousePressed(MouseEvent event){
            
            clearedShapes.clear(); // when a new shape is being drawn, delete all shapes stored in clearedShapes
            
            try {
                
                if (currentShapeType == LINE){ // if the shape type is a line
                    currentShapeObject = new MyLine(event.getX(),event.getY(),event.getX(),event.getY(), strokeWidth,
                                                    dashLength, currentShapeColor, secondColor, gradient, dashed);
                }
                else if (currentShapeType == RECTANGLE){ // if the shape type is a rectangle
                    currentShapeObject = new MyRectangle(event.getX(),event.getY(),event.getX(),event.getY(),
                                                         strokeWidth, dashLength, currentShapeColor, secondColor,
                                                         currentShapeFilled, gradient, dashed);
                }
                else if (currentShapeType == OVAL){ // if the shape type is an oval
                    currentShapeObject = new MyOval(event.getX(),event.getY(),event.getX(),event.getY(), strokeWidth,
                                                    dashLength, currentShapeColor, secondColor, currentShapeFilled,
                                                    gradient, dashed);
                }
            }
            catch (NullPointerException e){ // exception handler for when two mouse buttons are pressed together
            }
        }
        
        // when the mouse is released
        public void mouseReleased(MouseEvent event){
            try{
                currentShapeObject.setX2(event.getX());
                currentShapeObject.setY2(event.getY());
                shapeObjects.push(currentShapeObject); // the object that is just drawn is put into the stack
                currentShapeObject = null; // reset
                repaint();
            }
            catch (NullPointerException e){  // exception handler for when two mouse buttons are pressed together
            }
        }
        
        // when the mouse is moving
        public void mouseMoved(MouseEvent event){
            statusLabel.setText(event.getX() + ", " + event.getY());
        }
        
        // when the mouse is dragged
        public void mouseDragged(MouseEvent event){
            try{
                currentShapeObject.setX2(event.getX());
                currentShapeObject.setY2(event.getY());
                repaint();
                statusLabel.setText(event.getX() + ", " + event.getY());
            }
            catch (NullPointerException e){  // exception handler for when two mouse buttons are pressed together
            }
        }
    } // end private class MouseHandler
} // end class DrawPanel
