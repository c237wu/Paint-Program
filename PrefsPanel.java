import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
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
import javax.swing.JTextField;
import java.lang.NumberFormatException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;

public class PrefsPanel extends JPanel {
    
    private String prefFirstColor = "0";
    private String prefSecondColor = "0";
    private String prefDefaultShape = "0";
    private String prefFilled = "false";
    private String prefGradient = "false";
    private String prefDashed = "false";
    private String prefStrokeWidth = "1";
    private String prefDashLength = "1";    
    private final String COLOR_NAMES[] =
    {"Black","Blue","Cyan","Dark gray","Gray","Light gray","Green","Magenta",
        "Orange","Pink","Red","White","Yellow"};
    private final String SHAPE_NAMES[] = {"Line","Rectangle","Oval"};
    
    // constructor
    public PrefsPanel(){
        setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        JPanel comboBoxPanel = new JPanel(); // a panel containing comboBoxes
        comboBoxPanel.setLayout(new GridLayout(3,2));
        
        JLabel note = new JLabel("Please note: the change will be applied to all drawings created after OK is clicked.");
        add(note,gbc);
        
        gbc.gridy++;
        
        JLabel firstColor = new JLabel("First Colour: ");
        JComboBox colorJComboBox = new JComboBox(COLOR_NAMES);
        colorJComboBox.setMaximumRowCount(13);
        colorJComboBox.addItemListener(
            new ItemListener(){ // add listener for color combobox
                public void itemStateChanged(ItemEvent event){
                    if (event.getStateChange() == ItemEvent.SELECTED)
                        prefFirstColor = Integer.toString(colorJComboBox.getSelectedIndex());
                }
            }
                                        );
        
        JLabel secondColor = new JLabel("Second Colour: ");
        JComboBox secondColorJComboBox = new JComboBox(COLOR_NAMES);
        secondColorJComboBox.setMaximumRowCount(13);
        secondColorJComboBox.addItemListener(
            new ItemListener(){ // add listener for seconondaryColor combobox
                public void itemStateChanged(ItemEvent event){
                    if (event.getStateChange() == ItemEvent.SELECTED)
                        prefSecondColor = Integer.toString(secondColorJComboBox.getSelectedIndex());
                }
            }
                                            );
        
        JLabel shape = new JLabel("Default Shape: ");
        JComboBox shapeJComboBox = new JComboBox(SHAPE_NAMES);
        shapeJComboBox.setMaximumRowCount(3);
        shapeJComboBox.addItemListener(
            new ItemListener(){ // add listener for shape combobox
                public void itemStateChanged(ItemEvent event){
                    if (event.getStateChange() == ItemEvent.SELECTED)
                        prefDefaultShape = Integer.toString(shapeJComboBox.getSelectedIndex());
                    }
                }
                                      );
        
        // combo boxes are added to comboBoxPanel
        comboBoxPanel.add(firstColor);
        comboBoxPanel.add(colorJComboBox);
        comboBoxPanel.add(secondColor);
        comboBoxPanel.add(secondColorJComboBox);
        comboBoxPanel.add(shape);
        comboBoxPanel.add(shapeJComboBox);
        
        add(comboBoxPanel,gbc); // comboBoxPanel is added to prefsPanel
        
        gbc.gridy++;
        
        JPanel checkBoxPanel = new JPanel(); // a panel for check boxes
        checkBoxPanel.setLayout(new FlowLayout());
        
        JCheckBox shapeFilled = new JCheckBox("Filled Shape");
        shapeFilled.addItemListener( // add listener for the filled check box
                                    new ItemListener(){
            public void itemStateChanged(ItemEvent event){
                if (event.getStateChange() == ItemEvent.SELECTED)
                    prefFilled = Boolean.toString(shapeFilled.isSelected());
            }
        }
        );
        
        JCheckBox gradient = new JCheckBox("Gradient");
        gradient.addItemListener( // add listener for the gradient check box
            new ItemListener(){
                public void itemStateChanged(ItemEvent event){
                    prefGradient = Boolean.toString(gradient.isSelected());
                }
            }
                                );
        
        JCheckBox dashed = new JCheckBox("Dashed");
        dashed.addItemListener( // add listener for the gradient check box
            new ItemListener(){
                public void itemStateChanged(ItemEvent event){
                    prefDashed = Boolean.toString(dashed.isSelected());
                }
            }
                              );
        
        // check boxes are added to checkBoxPanel
        checkBoxPanel.add(shapeFilled);
        checkBoxPanel.add(gradient);
        checkBoxPanel.add(dashed);
        
        add(checkBoxPanel,gbc); // checkBoxPanel is added to prefsPanel
        
        gbc.gridy++;
        
        JPanel textFieldPanel = new JPanel(); // a panel that contains text fields
        textFieldPanel.setLayout(new FlowLayout());
        
        JLabel strokeWidthLabel = new JLabel("Default Stroke Length:");
        
        JTextField strokeWidth = new JTextField(5);
        strokeWidth.addActionListener(
            new ActionListener(){
                public void actionPerformed( ActionEvent event){
                    try {
                            prefStrokeWidth = Float.toString(Math.abs(Float.parseFloat(event.getActionCommand())));
                    }
                    catch (NumberFormatException e){
                        JOptionPane.showMessageDialog(null,"Please enter a number!", "Incorrect Input",JOptionPane.WARNING_MESSAGE);
                    }
               }
            }
                                );
        
        JLabel strokeDashLabel = new JLabel("Default Stroke Dash Length:");
        
        JTextField dashLength = new JTextField(5);
        dashLength.addActionListener(
            new ActionListener(){
                public void actionPerformed( ActionEvent event){
                    try {
                        prefDashLength = Float.toString(Math.abs(Float.parseFloat(event.getActionCommand())));
                    }
                    catch (NumberFormatException e){
                        JOptionPane.showMessageDialog(null,"Please enter a number!", "Incorrect Input",JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
                                    );
        // labels and text fields are added to textFieldPanel
        textFieldPanel.add(strokeWidthLabel);
        textFieldPanel.add(strokeWidth); 
        textFieldPanel.add(strokeDashLabel);
        textFieldPanel.add(dashLength);
        
        add(textFieldPanel,gbc); // textFieldPanel is added to prefsPanel
    }
    
    // accessor method for prefFirstColor
    public String getPrefFirstColor(){
        return prefFirstColor;
    }
    
    // accessor method for prefSecondColor
    public String getPrefSecondColor(){
        return prefSecondColor;
    }
    // accessor method for prefDefaultShape
    public String getPrefDefaultShape(){
        return prefDefaultShape;
    }
    
    // accessor method for prefFilled
    public String getPrefFilled(){
        return prefFilled;
    }
    // accessor method for prefGradient
    public String getPrefGradient(){
        return prefGradient;
    }
    
    // accessor method for prefDashed
    public String getPrefDashed(){
        return prefDashed;
    }
    
    // accessor method for prefStrokeWidth
    public String getPrefStrokeWidth(){
        return prefStrokeWidth;
    }
    
    // accessor method for prefDashLength
    public String getPrefDashLength(){
        return prefDashLength;
    }
    
}
