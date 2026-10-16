import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.*;
import javax.swing.*;

/**
 * <p>
 * The Credits class is responsible for displaying the credits screen, showing what each memeber's role was.
 * </p>
 *
 * <p>
 * Version 1 (25 min, by Soham Malla, on 2024/05/21):
 * Attached and drew background image.
 * Drew, formatted, and correctly positioned text(including title, names, and roles) on the screen.
 * Rotated title using code from Stack Overflow.
 * </p>
 *
 * <p>
 * Version 2 (20 mins, by Soham Malla, on 2024/06/11):
 * Added code to implememnt the custom Lobster font.
 * </p>
 
 * <h2>Course Info:</h2>
 * Course: ICS4U0, Teacher: Ms. Krasteva
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
 * @author Soham Malla
 * @version 2.0
 */
public class Credits extends Level
{
    /**
     * This method draws the credits screen on the window. It loads the background image,
     * creates a new rotated font, and draws the rotated title along with the formatted credits text.
     *
     * @param g A Graphics variable passed to the method by default in order to draw on the JPanel component (which is then attached to the JFrame) using its
    *          methods.
     */
    public void paint(Graphics g)
    {
        Font lobsterBold = null;
        Font lobsterSmall = null;

        try
        {
            lobsterBold = Main.readFont("LobsterTwo-Bold.ttf").deriveFont(50f);
            lobsterSmall = Main.readFont("LobsterTwo-Bold.ttf").deriveFont(25f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(lobsterBold);
            ge.registerFont(lobsterSmall);
        }
        catch (IOException | FontFormatException e)
        {
        }

        Image background = Main.readImage("blackboard.png");
        g.drawImage(background, 0, 0, null);
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.rotate(Math.toRadians(-15), 0, 0);
        Font rotatedFont = lobsterBold.deriveFont(affineTransform);
        g.setFont(rotatedFont);
        g.setColor(Color.WHITE);
        g.drawString("Credits",150,160);

        g.setFont(lobsterSmall);
        g.drawString("Dorian Groza ------------------------------ Main Programmer", 120, 225);
        g.drawString("Soham Malla -------------------------- Assistant Programmer", 120, 255);
        g.drawString("Sam Otazo -------- Assistant Programmer, and Main Artist", 120, 285);
        g.drawString("Please press the spacebar to continue. ", 230, 410);

    }

    /**
     * This method is called in the Main class in order to draw the credits screen.
     * It keeps repainting the credits screen until the user presses the spacebar to exit, using a while loop.
     */
    public void play()
    {
        while(lastAction!=5)
        {
            repaint();
        }
    }
}
