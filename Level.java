import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * This abstract class is used to provide general functionality for all the game's levels (and any other screens that have interactivity). This is done
 * by implementing user input, having a timer() method for all the various time-based calculations in all 3 levels, and providing abstract methods for
 * drawing output and playing each level that will then be detailed in child classes. It also extends the JPanel class, so that each level can be directly
 * added to the JFrame.
 *
 * <p>
 * Version 1 (30 mins, by Dorian Groza, on 2024/05/19):
 * Worked out and implemented user input.
 * Added the abstract play() and draw() methods. 
 * </p>
 *
 * <p>
 * Version 2 (5 mins, by Dorian Groza, on 2024/06/11):
 * Moved the timer() method to this class, since all 3 level classes had their own timer() method with the same functionality.
 * </p>
 *
 * <p>
 * Citations:
 * </p>
 *
 * <p>
 * 1) We learned how to get lightweight keyboard input from
      https://stackoverflow.com/questions/13791987/keyboard-input-stops-working-in-swing-application-a-calculator-after-clicking.
 * </p> 
 *
 * <p>
 * 2) We used the technique shown at https://stackoverflow.com/questions/10820033/make-a-simple-timer-in-java to create the timer() method.
 * </p>
 *
 * <h2>Course Info:</h2>
 * Course: ICS4U0, Teacher: Ms. Krasteva
 *
 * @author Dorian Groza
 * @version 2.0
 */

public abstract class Level extends JPanel
{  
   /**
    * Stores the most recent keyboard input from the user in the form of a corresponding integer. 1 represents pressing 'W',
    * 2 represents pressing 'A', 3 represents pressing 'S', 4 represents pressing 'D', and 5 represents pressing the spacebar. It is then used for all of
    * the user input calculations throughout the game.
    */
   protected int lastAction;
   /** Used to store the X position of the player, for calculating collision and drawing the image of AIden at the correct location. */
   protected int playerX;
   /** Used to store the Y position of the player, for calculating collision and drawing the image of AIden at the correct location. */
   protected int playerY;

   /**
    * This constructor sets up user input using KeyBindings, and more speciically using the getInputMap() and getActionMap() methods as described in the
    * citation above. This way, every subclass will have access to keyboard input, but they can each use it in different ways.
    */
   public Level()
   {
      getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), "Up");
   
      getActionMap().put("Up", 
         new AbstractAction()
         {
            public void actionPerformed(ActionEvent ae)
            {
               lastAction = 1;
            }
         });
      
      getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"), "Left");
   
      getActionMap().put("Left", 
         new AbstractAction()
         {
            public void actionPerformed(ActionEvent ae)
            {
               lastAction = 2;
            }
         });
      
      getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), "Down");
   
      getActionMap().put("Down", 
         new AbstractAction()
         {
            public void actionPerformed(ActionEvent ae)
            {
               lastAction = 3;
            }
         });
      
      getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"), "Right");
   
      getActionMap().put("Right", 
         new AbstractAction()
         {
            public void actionPerformed(ActionEvent ae)
            {
               lastAction = 4;
            }
         });
      
      getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SPACE"), "Interact");
   
      getActionMap().put("Interact", 
         new AbstractAction()
         {
            public void actionPerformed(ActionEvent ae)
            {
               lastAction = 5;
            }
         });
   }
   
   /**
    * This method keeps track of the time elapsed since the start parameter, in seconds. It is used for the time-based calculations in each level.
    * @param start A long variable that represents the starting time, in milliseconds, that the elapsed time should be based on.
    * @return The amount of seconds that have passed since the time represented by the parameter.
    */
   protected double timer (long start)
   {
      return (System.currentTimeMillis () - start) / 1000.0;
   }

   /**
    * This abstract method will draw the output for each level/screen onto the JFrame.
    *
    * @param g A Graphics variable passed to the method by default in order to draw on the JPanel component(which is then attached to the JFrame) using its
    *          methods. 
    */
   public abstract void paint(Graphics g);   
   
   /**
    * This abstract method will be overriden in each subclass and then called in the Main class in order to run the child class and draw its output.
    */
   public abstract void play();
}