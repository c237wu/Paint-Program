import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GradientPaint;
import java.awt.BasicStroke;
import java.awt.geom.Line2D;
 
public class MyLine extends MyShape {  
    
    // constructor without input values
    public MyLine() {
        super();
    }
    
    // constructor with input values
    public MyLine( int x1, int y1, int x2, int y2, float strokeWidth, float dashLength, Color color, Color color2,
                  boolean gradient,boolean dashed ) {
        super(x1, y1, x2, y2, strokeWidth, dashLength, color, color2, gradient, dashed);
    }
     
    
    // Actually draws the line
    public void draw( Graphics g ) {

        Graphics2D g2 = ( Graphics2D )g;
        if (getGradient()){ // if gradient option is selected
            GradientPaint gradientPaint = new GradientPaint(getX1(),getY1(), getColor(),getX2(),getY2(),getColor2());
            g2.setPaint(gradientPaint);
        }
        else
            g2.setPaint(getColor());

        if (getDashed()){ // if dashed line option is selected
            float dash1[] = {10.0f};
            BasicStroke dashedLine = new BasicStroke(getDashLength(),BasicStroke.CAP_BUTT,
                                                                  BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
            g2.setStroke(dashedLine);
        }
        else {
            g2.setStroke(new BasicStroke(getStrokeWidth()));
        }
        
        g2.draw( new Line2D.Float( getX1(), getY1(), getX2(), getY2()) );
        
    }
}
