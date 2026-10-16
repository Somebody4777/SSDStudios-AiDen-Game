import java.awt.*;
import java.awt.geom.AffineTransform;
import javax.swing.*;
import java.io.*;

/**
 * This class makes the main menu of the program, where the user can choose to play the game, view instructions, see the credits, or exit the program.
 * 
 * <p>
 * Version 1 (25 min, by Dorian Groza, on 2024/05/19):
 * Drew the text menu.
 * Implemented basic functionality and user input.
 * </p>
 *
 * <p>
 * Version 2 (15 min, by Soham Malla, on 2024/05/28):
 * Attached and drew background image.
 * Drew, formatted, and correctly positioned text on the screen.
 * Rotated title using code from Stack Overflow.
 * </p>
 *
 * <p>
 * Version 3 (20 mins, by Soham Malla, on 2024/06/11):
 * Added code to implememnt the custom Lobster font.
 * </p>
 *
 * <p>
 * Citations:
 * </p>
 *
 * <p>
 * 1) The code for rotating text was taken from https://stackoverflow.com/questions/10083913/how-to-rotate-text-with-graphics2d-in-java.
 * </p> 
 *
 * <p>
 * 2) The code for implementing a custom font (Lobster) was taken from https://stackoverflow.com/questions/5652344/how-can-i-use-a-custom-font-in-java.
 * </p> 
 *
 * <h2>Course Info:</h2>
 * Course: ICS4U0, Teacher: Ms. Krasteva
 *
 * @author Dorian Groza and Soham Malla
 * @version 2.0
 */
public class MainMenu extends Level
{
   /** An instance of the Menu class that is used to store the text menu. */
   private Menu menu;
   /**
    * This int stores the currently selected menu choice, to determine what screen to go to after the main menu. The initial value is -1 so that the while
    * loop in play() will keep running until the user chooses an option.
    */
   private int menuChoice;

   /** 
    * This constructor of the MainMenu class initializes the menu variable, passing to it the options in the main menu. It also has the effects of the Level
    * constructor, and sets menuChoice to -1 for the reason mentioned above.
    */
   public MainMenu()
   {
      super();
      menu = new Menu(new String[]{"Play!", "Instructions", "Credits", "Exit"}, false);
      menuChoice = -1;
   }
   
   /**
    * This method draws the main menu on the window using an imported background, custom fonts, and rotation, and highlights
    * the menu option selected. It also takes in user input so that the user can move left and right on the menu, and select their choice.
    * @param g A Graphics variable passed to the method by default in order to draw on the JPanel component(which is then attached to the JFrame) using its
    *          methods. 
    */
   public void paint(Graphics g)
   {
      Font lobsterBold = null;
      Font lobsterSmall = null;

      try 
      {
         lobsterBold = Main.readFont("LobsterTwo-Bold.ttf").deriveFont(40f);
         lobsterSmall = Main.readFont("LobsterTwo-Bold.ttf").deriveFont(30f);
         GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
         ge.registerFont(lobsterBold);
         ge.registerFont(lobsterSmall);
      }  
      catch (IOException | FontFormatException e) 
      {
      }
      
      int dist = 0;

      Image background = (Main.readImage("mainmenublackboard.png"));
      g.drawImage(background, 0, 0, null);

      AffineTransform affineTransform = new AffineTransform();
      affineTransform.rotate(Math.toRadians(-12), 0, 0);
      Font rotatedFont = lobsterBold.deriveFont(affineTransform);
      g.setFont(rotatedFont);
      g.setColor(Color.WHITE);
      g.drawString("Classroom AI-dventure!",150,200);

      g.setFont(lobsterSmall);
   
      String[] options = menu.getOptions();
      for(int i = 0; i<options.length; i++)
      {
         if(i == menu.getChoice())
         {
            g.setColor(new Color(255,206,42));
         }
         else
         {
            g.setColor(Color.white);
         }
            
         g.drawString(((i == menu.getChoice())?"> ":"* ") + options[i],100 + dist, 350);
         
         dist += (i == 0)?140:(i == 1)?230:160;
      }
   
   
      if(lastAction == 2)
      {
         menu.setChoice(lastAction);
      }
      else if(lastAction == 4)
      {
         menu.setChoice(lastAction);
      }
      else if(lastAction == 5)
      {
         menuChoice = menu.getChoice();
      }
      
      lastAction = 0;
   }
   
   
   /**
    * This is a getter method that returns the current menu choice. It is used in Main so that the program knows what screen to display after this one.
    * @return The option on the menu that the user is currently on.
    */ 
   public int getMenuChoice()
   {
      return menuChoice;
   }
   
   /** 
    * This method keeps displaying the main menu until an option is selected. It is used to run the MainMenu class from the Main class. 
    */
   public void play()
   {
      while(menuChoice == -1)
      {
         repaint();
      }
   }
}