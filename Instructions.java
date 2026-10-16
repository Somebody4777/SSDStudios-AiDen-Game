import java.awt.*;
import java.io.*;
import javax.swing.*;

/**
 * <p>
 * This class makes the Instructions screen, that explains the user input.
 * </p>
 *
 * <p>
 * Version 1 (40 mins, by Soham Malla, on 2024/05/21):
 * Implemented user input for the screen, loading and drawing the background image, and displaying title and instructions, as well as bugfixing for custom
 * fonts.
 * </p>
 *
 * <p>
 * Version 2 (20 mins, by Soham Malla, on 2024/06/11):
 * Added code to implememnt the custom Lobster font.
 * </p>
 *
 * <p>
 * Version 3 (40 mins, by Dorian Groza, on 2024/06/11):
 * Modified the text displayed to be more accurate.
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
 * @author Soham Malla
 * @version 2.0
 */

public class Instructions extends Level
{   
   /**
    * This method draws the instructions screen on the window. It loads the background image, draws it, and then draws the text 
    * on top of the image. 
    *
    * @param g A Graphics variable passed to the method by default in order to draw on the JPanel component(which is then attached to the JFrame) using its
    *          methods.
    */
   public void paint(Graphics g)
   {
      Font lobsterBold = null;
      Font lobsterRegular = null;

      try
      {
         lobsterBold = Main.readFont("LobsterTwo-Bold.ttf").deriveFont(40f);
         lobsterRegular = Main.readFont("LobsterTwo-Regular.ttf").deriveFont(20f);
         GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
         ge.registerFont(lobsterBold);
         ge.registerFont(lobsterRegular);
      }
      catch (IOException|FontFormatException e)
      {
      }

      Image background = Main.readImage("blackboard.png");
      g.drawImage(background, 0, 0, null);
      g.setFont(lobsterBold);
      g.setColor(Color.WHITE);
      g.drawString("Instructions", 320, 110);
      g.setFont(lobsterRegular);
      String[] instructions = {"     This game is mainly controlled using keyboard input. Use W,A,S, and D to", "       move the character around the room and select different options on menus.", "   Use the spacebar to select options on menus, and to continue to the next set of", "     dialogue, if there is any. Each level has different rules on how to progress,", "              which will be explained before each level begins. Additional tips", "             and important information can also be found in the user manual."};
      for(int i=0; i<instructions.length; i++){
         g.drawString(instructions[i], 100, 175+(30*i));
      }
      g.drawString("Please press the spacebar to return to the main menu.", 210, 400);
   }
   
   /**
    * This method is called in the Main class in order to draw the instructions screen. 
    * It will continuously repaint the instructions screen until the user presses the spacebar to exit using a while loop.
    */
   public void play()
   {
      while(lastAction!=5)
      {
         repaint();
      }
   }
}