import java.awt.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.*;

/** 
 * <p>
 * The Main class is responsible for running the game. It creates instances of the other classes, and then adds them to the window to display all the
 * different screens of the game one by one.
 * </p>
 *
 * <p>
 * Version 1 (2 hours, by Dorian Groza, on 2024/05/19):
 * Created the readImage() method.
 * Intialized the JFrame in constructor.
 * Implemented program flow and worked out how to draw levels on the main JFrame.
 * Drew the main menu, splash screen, instructions, and goodbye screen on the JFrame.
 * </p>
 *
 * <p>
 * Version 2 (15 min, by Soham Malla, on 2024/05/20):
 * Moved the code responsible for rendering each frame of the Splash Screen animation to the SplashScreen class.
 * Added the code to display the instructions and credits screen in the main() method.
 * </p>
 *
 * <p>
 * Version 3 (5 min, by Dorian Groza, on 2024/06/06):
 * Added conditional statements around the code running levels 2 and 3, so that the user can return to the main menu after level 1 and level 2, if they
 * wish.
 * </p>
 *
 * <h2>Course Info:</h2>
 *
 * <p>
 * Course: ICS4U0, Teacher: Ms. Krasteva
 * </p>
 *
 * @author Dorian Groza and Soham Malla
 * @version 2.0
 */

public class Main
{
   /** Stores the main graphical interface of the game. This JFrame is the program window that everythinbg else is then drawn on. */
   private static JFrame window;
   /** Stores the level that is being currently displayed. Will be used to attach the current level to the JFrame and run its code. */
   private static Level currentLevel;
   /** Used to control the flow of the game in the while loop in main(). Stores the choice from the main menu. */
   private static int titleChoice;
   
   /**
    * The constructor of the Main class. Intializes the JFrame, gives it a name, sets a permanent size, makes it visible, and ends the program once the
    * window is closed.
    */
   public Main()
   {
      window = new JFrame("Classroom AI-dventure");
      window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      window.setSize(800,500);
      window.setResizable(false);
      window.setVisible(true);
   }
   
   /**
    * This method returns the image with the file name of the parameter, represented as an instance of the Image class. It is used to makeit easier to
    * intialize the various images of the game, since try and catch will no longer be needed around each image.
    *
    * @param fileName The name/location of the desired image file in the folder.
    * @return The Image that was found at the given location.
    */
   public static Image readImage(String fileName)
   {    
      try (InputStream input = Main.class.getResourceAsStream("/" + fileName))
      {
         if (input == null)
         {
            System.err.println("Could not find image resource: /" + fileName);
            return null;
         }
         Image image = ImageIO.read(input);
         if (image == null)
            System.err.println("Resource is not a supported image: /" + fileName);
         return image;
      }
      catch (IOException e)
      {
         System.err.println("Could not load image resource /" + fileName + ": " + e.getMessage());
         return null;
      }
   }

   /** Loads a TrueType font bundled with the application, independent of the working directory. */
   public static Font readFont(String fileName) throws IOException, FontFormatException
   {
      try (InputStream input = Main.class.getResourceAsStream("/" + fileName))
      {
         if (input == null)
            throw new FileNotFoundException("Could not find font resource: /" + fileName);
         return Font.createFont(Font.TRUETYPE_FONT, input);
      }
   }

   /**
    * The main method that runs the game.
    *
    * @param args Stores strings from the command line when file is executed. 
    */
   public static void main(String[] args)
   {
      Main m = new Main();
      currentLevel = new SplashScreen();
      window.add(currentLevel);
      window.revalidate();
      currentLevel.play();
      window.remove(currentLevel);
      
      while(true)
      {
         currentLevel = new MainMenu();
         window.add(currentLevel);
         window.revalidate();
         currentLevel.play();
         titleChoice = ((MainMenu)currentLevel).getMenuChoice();
         window.remove(currentLevel);
         
         if(titleChoice == 0)
         {
            currentLevel = new Level1();
            window.add(currentLevel);    
            window.revalidate();
            currentLevel.play();
            window.remove(currentLevel);
            
            
            if(((Level1)currentLevel).getMenuChoice() == 1)
            {
               currentLevel = new Level2();
               window.add(currentLevel);    
               window.revalidate();
               currentLevel.play();
               window.remove(currentLevel);
               
               if(((Level2)currentLevel).getContinueNext()!=5)
               {
                  currentLevel = new Level3();
                  window.add(currentLevel);
                  window.revalidate();
                  currentLevel.play();
                  window.remove(currentLevel);
               }
            }
         }
         else if(titleChoice == 1)
         {
            currentLevel= new Instructions();
            window.add(currentLevel);
            window.revalidate();
            currentLevel.play();
            window.remove(currentLevel);
            
         }
         else if(titleChoice == 2)
         {
            currentLevel= new Credits();
            window.add(currentLevel);
            window.revalidate();
            currentLevel.play();
            window.remove(currentLevel);
         }
         else if(titleChoice == 3)
         {
            window.add(new Goodbye());
            window.revalidate();

            try
            {
               Thread.sleep(5000);
            }
            catch(InterruptedException e)
            {
            }
            
            System.exit(0);
         }
      }
   }
}