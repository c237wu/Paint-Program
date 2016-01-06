import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import java.lang.NumberFormatException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;

public class DrawFrame extends JFrame {
    
    private JDesktopPane desktop;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem newMenuItem;
    private JMenuItem aboutMenuItem;
    private JMenuItem exitMenuItem;
    private JMenuItem prefsMenuItem;
    
    private final String COLOR_NAMES[] =
    {"Black","Blue","Cyan","Dark gray","Gray","Light gray","Green","Magenta",
        "Orange","Pink","Red","White","Yellow"};
    private final Color COLORS[] = 
    {Color.BLACK,Color.BLUE,Color.CYAN,Color.DARK_GRAY,Color.GRAY,Color.LIGHT_GRAY,Color.GREEN,Color.MAGENTA,
    Color.ORANGE,Color.PINK,Color.RED,Color.WHITE,Color.YELLOW};
    
    private final String SHAPE_NAMES[] = {"Line","Rectangle","Oval"};
    
    private int windowNum = 0;
    
    // constructor
    public DrawFrame(){
        super("Paint Program");
        setLayout(new BorderLayout());
        
        desktop = new JDesktopPane(); // create a JDesktopPane object
        
        // create a JMenuBar object, JMenu object and JMenuItem objects
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        aboutMenuItem = new JMenuItem("About");
        exitMenuItem = new JMenuItem("Exit");
        newMenuItem = new JMenuItem("New");
        prefsMenuItem = new JMenuItem("Prefs");
        
        // add ActionListener for exitMenuItem
        exitMenuItem.addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent event){
                    System.exit(0); 
                }
            }
        ); // end addActionListener for exitMenuItem
        
        aboutMenuItem.addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent event){
                    String info = "Name: Cherrie Wu\nClass: ICS4U\nTeacher: Mr.Rao\n\nA paint program with basic drawing functions. Written with tears (and love).";
                    JOptionPane.showMessageDialog(null,info,"About",JOptionPane.INFORMATION_MESSAGE);
                }
            }
                                        );
        
        // add ActionListener for prefsMenuItem
        prefsMenuItem.addActionListener(
            new ActionListener(){
                
                public void actionPerformed(ActionEvent event){
                    
                    PrefsPanel prefsPanel = new PrefsPanel(); // create a PrefsPanel object
                    
                    int result = JOptionPane.showConfirmDialog(null,prefsPanel,"Preferences",JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION){
                        String fileName = "data.txt";
                        
                        try{
                            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName));
                            bufferedWriter.write(prefsPanel.getPrefFirstColor()); // write in the index of the first color in the COLORS list
                            bufferedWriter.newLine();
                            bufferedWriter.write(prefsPanel.getPrefSecondColor()); // write in the index of the second color in the COLORS list
                            bufferedWriter.newLine();
                            bufferedWriter.write(prefsPanel.getPrefDefaultShape()); // write in the index of the shape in the SHAPE_NAMES list
                            bufferedWriter.newLine();
                            bufferedWriter.write(prefsPanel.getPrefFilled()); // write in the value of prefFilled
                            bufferedWriter.newLine();
                            bufferedWriter.write(prefsPanel.getPrefGradient()); // write in the value of prefGradient
                            bufferedWriter.newLine();
                            bufferedWriter.write(prefsPanel.getPrefDashed()); // write in the value of prefDashed
                            bufferedWriter.newLine();
                            bufferedWriter.write(prefsPanel.getPrefStrokeWidth()); // write in the value of prefStrokeWidth
                            bufferedWriter.newLine();
                            bufferedWriter.write(prefsPanel.getPrefDashLength()); // write in the value of prefDashLength
                            
                            bufferedWriter.close(); // close the file
                        }
                        catch(IOException e){
                            JOptionPane.showMessageDialog(null,"Error writing to file: "+fileName, "Error",
                                                          JOptionPane.WARNING_MESSAGE);
                        }
                    }
                
                }
        }
                                       ); // end addActionListener for prefsMenuItem

        // add ActionListener for newMenuItem
        newMenuItem.addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent event){
                    windowNum += 1;
                    
                    JInternalFrame newWindow = new JInternalFrame("Drawing #"+windowNum,true,true,true,true); // create a JInternalFrame with all the properties
                    newWindow.setLayout(new BorderLayout());
                    
                    JLabel statusBar = new JLabel(); // label that displays the coordinates of the mouse
                    DrawPanel drawPanel = new DrawPanel(statusBar);                    

                    // Create toolBar                    
                    JToolBar toolBar = new JToolBar("ToolBar");
                    toolBar.setOrientation(JToolBar.HORIZONTAL);
                    toolBar.setRollover(true);
                    toolBar.setFloatable(true);
                    
                    toolBar.add(statusBar); // statusBar is added to toolBar
                    
                    JPanel buttonPanel = new JPanel(); // a panel containing all the buttons
                    buttonPanel.setLayout(new FlowLayout());
                    
                    // create buttons
                    JButton undoButton = new JButton("Undo");
                    JButton redoButton = new JButton("Redo");
                    JButton clearButton = new JButton("Clear");
                    JButton saveButton = new JButton("Save");
                    
                    undoButton.addActionListener( // add listener for undoButton
                        new ActionListener() { 
                            public void actionPerformed(ActionEvent event){
                                drawPanel.clearLastShape();
                            }
                        }
                               );
                    redoButton.addActionListener( // add listener for redoButton
                        new ActionListener() { 
                            public void actionPerformed(ActionEvent event){
                                drawPanel.redoLastShape();
                            }
                        }
                               );
                    clearButton.addActionListener( // add listener for clearButton
                        new ActionListener() { 
                            public void actionPerformed(ActionEvent event){
                                drawPanel.clearDrawing();
                            }
                        }
                                                 );
                    
                    saveButton.addActionListener( // add listener for saveButton
                         new ActionListener(){
                            public void actionPerformed(ActionEvent event){
                                drawPanel.saveDrawing();
                            }
                         }
                                                );
                    
                    // buttons are added to buttonPanel
                    buttonPanel.add(undoButton);
                    buttonPanel.add(redoButton);
                    buttonPanel.add(clearButton);
                    buttonPanel.add(saveButton);
                    
                    toolBar.addSeparator();
                    toolBar.add(buttonPanel); // buttonPanel is added to toolBar
                    
                    JPanel comboBoxPanel = new JPanel(); // a panel containing comboBoxes
                    comboBoxPanel.setLayout(new FlowLayout());
        
                    JComboBox colorJComboBox = new JComboBox(COLOR_NAMES);
                    colorJComboBox.setMaximumRowCount(13);
                    colorJComboBox.setSelectedIndex(drawPanel.getCurrentShapeColorIndex()); // set the item to be displayed
                    colorJComboBox.addItemListener(  // add listener for color combobox
                        new ItemListener(){
                            public void itemStateChanged(ItemEvent event){
                                if (event.getStateChange() == ItemEvent.SELECTED)
                                    drawPanel.setCurrentShapeColor(COLORS[colorJComboBox.getSelectedIndex()]);
                                }   
                        }
                                       );
        
                    JComboBox secondColorJComboBox = new JComboBox(COLOR_NAMES);
                    secondColorJComboBox.setMaximumRowCount(13);
                    secondColorJComboBox.setSelectedIndex(drawPanel.getSecondColorIndex());
                    secondColorJComboBox.addItemListener(  // add listener for seconondColor combobox
                        new ItemListener(){
                            public void itemStateChanged(ItemEvent event){
                                if (event.getStateChange() == ItemEvent.SELECTED)
                                    drawPanel.setSecondColor(COLORS[secondColorJComboBox.getSelectedIndex()]);
                                }   
                        }
                                       );
                    
                    JComboBox shapeJComboBox = new JComboBox(SHAPE_NAMES);
                    shapeJComboBox.setMaximumRowCount(3);
                    shapeJComboBox.setSelectedIndex(drawPanel.getCurrentShapeType());
                    shapeJComboBox.addItemListener(  // add listener for shape combobox
                    new ItemListener(){
                        public void itemStateChanged(ItemEvent event){
                            if (event.getStateChange() == ItemEvent.SELECTED)
                                drawPanel.setCurrentShapeType(shapeJComboBox.getSelectedIndex());
                            }
                        }
                                       );
                   
                    // combo boxes are added to comboBoxPanel
                    comboBoxPanel.add(colorJComboBox);
                    comboBoxPanel.add(secondColorJComboBox);
                    comboBoxPanel.add(shapeJComboBox);
  
                    toolBar.addSeparator();
                    toolBar.add(comboBoxPanel); // comboBoxPanel is added to toolBar
                    
                    JPanel checkBoxPanel = new JPanel(); // a panel for check boxes
                    checkBoxPanel.setLayout(new FlowLayout());
                    
                    JCheckBox shapeFilled = new JCheckBox("Filled Shape");
                    shapeFilled.setSelected(drawPanel.getCurrentShapeFilled());
                    shapeFilled.addItemListener( // add listener for the filled check box
                        new ItemListener(){
                            public void itemStateChanged(ItemEvent event){
                                drawPanel.setCurrentShapeFilled(shapeFilled.isSelected());
                            }
                        }
                                    );
                     
                    JCheckBox gradient = new JCheckBox("Gradient");
                    gradient.setSelected(drawPanel.getGradient());
                    gradient.addItemListener( // add listener for the gradient check box
                        new ItemListener(){
                            public void itemStateChanged(ItemEvent event){
                                drawPanel.setGradient(gradient.isSelected());
                            }
                        }
                                             );
                                      
                    JCheckBox dashed = new JCheckBox("Dashed");
                    dashed.setSelected(drawPanel.getDashed());
                    dashed.addItemListener( // add listener for the gradient check box
                        new ItemListener(){
                            public void itemStateChanged(ItemEvent event){
                                drawPanel.setDashed(dashed.isSelected());
                            }
                        }
                                             );
                    
                    // check boxes are added to checkBoxPanel
                    checkBoxPanel.add(shapeFilled);
                    checkBoxPanel.add(gradient);
                    checkBoxPanel.add(dashed);
                                        
                    toolBar.addSeparator();
                    toolBar.add(checkBoxPanel); // checkBoxPanel is added to toolBar
                    
                    JPanel textFieldPanel = new JPanel(); // a panel for JTextField objects
                    textFieldPanel.setLayout(new FlowLayout());
                    
                    JLabel strokeWidthLabel = new JLabel("Stroke Length:");
                    
                    JTextField strokeWidth = new JTextField(5);
                    strokeWidth.setText(Float.toString(drawPanel.getStrokeWidth()));
                    strokeWidth.addActionListener( // add ActionListener for strokeWidth
                        new ActionListener(){
                            public void actionPerformed( ActionEvent event){
                                try {
                                    drawPanel.setStrokeWidth(Math.abs(Float.parseFloat(event.getActionCommand())));
                                }
                                catch (NumberFormatException e){ // if the user enters letters instead of numbers
                                    JOptionPane.showMessageDialog(null,"Please enter a number!", "Incorrect Input",JOptionPane.WARNING_MESSAGE);
                                }
                        }
                    }
                                                  );
                    
                    JLabel strokeDashLabel = new JLabel("Dash Length:");
                    
                    JTextField dashLength = new JTextField(5);
                    dashLength.setText(Float.toString(drawPanel.getDashLength()));
                    dashLength.addActionListener( // add ActionListener for dashLength
                        new ActionListener(){
                            public void actionPerformed( ActionEvent event){
                                try {
                                    drawPanel.setDashLength(Math.abs(Float.parseFloat(event.getActionCommand())));
                                }
                                catch (NumberFormatException e){ // if the user enters letters instead of numbers
                                    JOptionPane.showMessageDialog(null,"Please enter a number!", "Incorrect Input",JOptionPane.WARNING_MESSAGE);
                                }
                        }
                    }
                                                  );
                    
                    // labels and JTextField objects are added to textFieldPanel
                    textFieldPanel.add(strokeWidthLabel);
                    textFieldPanel.add(strokeWidth);
                    textFieldPanel.add(strokeDashLabel);
                    textFieldPanel.add(dashLength);
                    
                    toolBar.addSeparator();
                    toolBar.add(textFieldPanel); // textFieldPanel is added to toolBar
                    
                    newWindow.add(toolBar,BorderLayout.SOUTH); // toolBar is added to the south of the new frame
                    newWindow.add(drawPanel,BorderLayout.CENTER); // drawPanel is added to the new frame
                    
                    newWindow.setVisible(true);
                    newWindow.setSize(825,500);
                    desktop.add(newWindow); // the frame just created is added to the desktop
                    newWindow.moveToFront(); // the new frame is moved to the front
                }
            }
                              ); // end addActionListener for newMenuItem
        
        // menu items are added to JMenu
        fileMenu.add(newMenuItem);
        fileMenu.add(prefsMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(aboutMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);
        
        menuBar.add(fileMenu); //add fileMenu to the main menu bar
        setJMenuBar(menuBar); //put the menu bar on the JFrame
        add(desktop,BorderLayout.CENTER);
    } // end constructor
} // end class
