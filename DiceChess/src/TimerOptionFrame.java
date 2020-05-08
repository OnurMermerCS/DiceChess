//This class creates the panel in the beginnig of the to set time of the game
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TimerOptionFrame extends JFrame
{
  private JPanel panel;
  private JLabel label;
  private JTextField textfield;
  private JButton button1;
  private JButton button2;
  
  public TimerOptionFrame()
  {
    createComponents();
    setSize(565,215);
    setLocation(710, 290);
    setTitle("Timer Option");
  }
  //creates the timer panel
  private void createComponents()
  {
    setLayout(null);
    
    panel = new JPanel();
    label = new JLabel("Please enter a time (in minutes) to play Chess with timer. (To play without timer enter \"0\")");
    textfield = new JTextField();
    button1 = new JButton("OK");
    button2 = new JButton("Cancel");
    
    ActionListener listener1 = new ClickListenerOk();
    button1.addActionListener(listener1);
    ActionListener listener2 = new ClickListenerCancel();
    button2.addActionListener(listener2);
    
    panel.setBounds(0, 0, 550, 200);
    panel.setLayout(null);
    
    label.setBounds(20, 15, 525, 25);
    textfield.setBounds(60, 50, 420, 25);
    button1.setBounds(125, 100, 85, 50);
    button2.setBounds(340, 100, 85, 50);
    
    panel.add(label);
    panel.add(textfield);
    panel.add(button1);
    panel.add(button2);
    
    add(panel);
  }
  
  class ClickListenerOk implements ActionListener
  {
    public void actionPerformed(ActionEvent event)
    {
      String textString = textfield.getText();
      boolean isNumeric = true;
      int textInteger;
      for (char c : textString.toCharArray())
      {
        if (!Character.isDigit(c))
        {
          isNumeric = false;
        }
      }
      if (isNumeric && !(textString.equals("")))
      {
        textInteger = Integer.parseInt(textString);
        if (textInteger < 0)
        {
          textfield.setText("Enter a positive integer."); 
        }
        else if (textInteger == 0)
        {
          dispose();
          new GameFrame().setVisible(true);
        }
        else if (textInteger > 0)
        {
          dispose();
          new GameFrame(textInteger).setVisible(true);
        }
      }
      else
      {
       textfield.setText("Enter a numeric in correct format."); 
      }
    }
  }
  
  class ClickListenerCancel implements ActionListener
  {
    public void actionPerformed(ActionEvent event)
    {
      dispose();
      new MainFrame().setVisible(true);
    }
  }
  
  public static void main(String[] args)
  {
    JFrame frameExample = new TimerOptionFrame();
    frameExample.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frameExample.setVisible(true);
  }
}