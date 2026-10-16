import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

/**
 * This class makes the Goodbye screen, displayed when exiting the game.
 *
 * <p>
 * Version 1 (20 mins, by Dorian Groza, on 2024/05/19):
 * Drew the goodbye screen using built-in drawing methods, mainly text along with colour and a rectangle for the background.
 * </p>
 *
 * <p>
 * Version 2 (25 min, by Soham Malla, on 2024/05/27):
 * Imported and drew the background image, formatted and adjusted text to be in the correct position, and imported logo and added text to the bottom right of
 * the screen to show company name.
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
 * 1) The code for implementing a custom font (Lobster) was taken from https://stackoverflow.com/questions/5652344/how-can-i-use-a-custom-font-in-java.
 * </p> 
 *
 * <h2>Course Info:</h2>
 * Course: ICS4U0, Teacher: Ms. Krasteva
 *
 * @author Dorian Groza and Soham Malla
 * @version 2.0
 */

public class Goodbye extends JPanel
{
    /**
    * This method draws the goodbye screen on the window.
    *
    * @param g A Graphics variable passed to the method by default in order to draw on the JPanel component(which is then attached to the JFrame) using its
    *          methods.
    */
   public void paint(Graphics g)
   {
      Font lobsterBold = null;
      Font lobsterSmall = null;
      Font lobsterRegular = null;

      try 
      {
         lobsterBold = Main.readFont("LobsterTwo-Bold.ttf").deriveFont(35f);
         lobsterSmall = Main.readFont("LobsterTwo-Bold.ttf").deriveFont(23f);
         lobsterRegular = Main.readFont("LobsterTwo-Regular.ttf").deriveFont(19f);
         GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
         ge.registerFont(lobsterBold);
         ge.registerFont(lobsterSmall);
         ge.registerFont(lobsterRegular);
      } 
      catch (IOException | FontFormatException e) 
      {
      }
      
      Image background = Main.readImage("blackboard.png");
      g.drawImage(background, 0, 0, null);
      g.setFont(lobsterBold);
      g.setColor(Color.WHITE);
      g.drawString("Goodbye, and thank you for playing!", 140, 110);
      g.setFont(lobsterSmall);

      g.drawString("We hope you learned about the pros and cons of AI in education", 105, 180);
      g.drawString("and enjoyed!", 350, 210);

      Image logo = Main.readImage("logo.png");
      if (logo != null) logo = logo.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
      g.drawImage(logo, 540, 335, null);
      g.setFont(lobsterRegular);
      g.drawString("An SSD", 625, 360);
      g.drawString("Studios", 625, 380);
      g.drawString("Production", 625, 400);
   }
}