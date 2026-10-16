import java.awt.*;
import javax.swing.*;

/**
 * <p>
 * This class draws and animates the splash screen seen when opening the game.
 * </p>
 *
 * <p>
 * Version 1 (20 mins, by Soham Malla, on 2024/05/22):
 * Added the text and created the fade out effect.
 * </p>
 *
 * <p>
 * Version 2 (30 min, by Soham Malla, on 2024/05/27):
 * Added animation to text and implemented a fade in effect for logo.
 * </p>
 *
 * <h2>Course Info:</h2>
 * Course: ICS4U0, Teacher: Ms. Krasteva
 *
 * @author Soham Malla
 * @version 2.0
 */

public class SplashScreen extends Level
{
   /** Stores and controls the opacity of a rectangle covering the logo, thereby indirectly controlling the opacity of the logo.*/
   private int opacity=255;
   /** Stores and controls the opacity of the rectangle used to fade out at the end of the animation.*/
   private int opacity2;
   /** Stores and controls the x-position of each word in the splash screen. It is changed using a loop to animated them flying onto the screen.*/
   private int[] posX = {-200, 800, 800, -200, 800};

   /** 
    * This method draws the splash screen, with text and images.
    * @param g A Graphics variable passed to the method by default in order to draw on the JPanel component(which is then attached to the JFrame) using its
    *          methods.
    */
   public void paint(Graphics g)
   {
      Image logo = Main.readImage("logo.png");
      if (logo != null) logo = logo.getScaledInstance(200, 200, Image.SCALE_DEFAULT);
      g.setColor(new Color(18,32,38));
      g.fillRect(0,0,800,500);
      
      g.setColor(Color.white);
      g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,40));
      g.drawString("A",posX[0],100);
      g.drawString("Game",posX[1],100);
      g.drawString("By",posX[2],100);
      g.drawString("SSD",posX[3],400);
      g.drawString("Studios",posX[4],400);

      g.drawImage(logo, 325, 130, null);
      g.setColor(new Color(18,32,38, opacity));
      g.fillRect(325,130, 200, 200);

      g.setColor(new Color(0,0,0,opacity2));
      g.fillRect(0,0,800,500);
        
   }
   
   /** A mutator method that sets the opacity of various objects. It is used to create fade in and fade out effects when used in combination with loops.
    *
    * @param choice Determines what to set the opacity of. 1 is for the rectangle over the logo, and 2 is for the rectangle covering the whole screen.
    * @param value The specific value that the chosen opacity should be set to.
    */
   public void setOpacity(int choice, int value)
   {
      if(value>=0 && value<=255)
      {
         if(choice==1)
         {
            opacity = value;
         }
         else if(choice==2)
         {
            opacity2=value;
         }
      }
   }

   /**
    * This method is called in the Main class in order to draw and animate the splash screen.
    * It is responsible for the animation by increasing the postion of the letters or changing the opacity
    * of the logo and screen frame by frame.
    */
   public void play()
   {
      for(int i = 0; i<815; i++)
      {
         repaint();
         if(posX[0]<300)
         {
            posX[0]+=5;
         } 
         else if(posX[1]>350)
         {
            posX[1]-=5;
         }
         else if(posX[2]>490)
         {
            posX[2]-=5;
         }
         else if(posX[3]<300)
         {
            posX[3]+=5;
         }
         else if(posX[4]>400)
         {
            posX[4]-=5;
         }
         else if(i<560)
         {
            setOpacity(1, 255-(i-432)*2);
            
            if(opacity==0)
            {
               try
               {
                  Thread.sleep(3000);
               }
               catch(InterruptedException e)
               {
               }
            }
         }
         else
         {
            setOpacity(2, i-560);
         }

         try
         {
            Thread.sleep(15);
         }
         catch(InterruptedException e)
         {
         }
      }
   }
}