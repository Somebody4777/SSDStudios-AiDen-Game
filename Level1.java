import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.awt.geom.Rectangle2D;

/**
 * This class makes level 1 of the game, that being the dialogue screens showing the positives and 
 * negatives of AI in education and feedback.
 *
 * <p>
 * Version 1 (20 mins, by Samuel Otazo, on 2024/05/29):
 * Imported all images necessary for the level.
 * Added all images to the code of the class in the variable declaration and in the constructor.
 * </p>
 *
 * <p>
 * Version 2 (1 hour 30 mins, by Samuel Otazo, on 2024/05/30):
 * Placed all images for level 1 on the window, resizing as necessary.
 * Added arrays for the student images an array for the big student heads needed for dialogue.
 * </p>
 *
 * <p>
 * Version 3 (1 hour, by Samuel Otazo, on 2024/05/31):
 * Resized desks, chairs, AI-Den after feedback from group members.
 * Added an array for student names to facilitate making the dialogue later on.
 * Removed methods of user input other than the spacebar, as player input is not needed in this level.
 * </p>
 *
 * <p>
 * Version 4 (1 hour 30 mins, by Samuel Otazo, on 2024/06/02):
 * Started working on the dialogue maker, removing anything associated with the menu, as this was 
 * pasted over from Level 3 and is unnecessary in this level.
 * Added a system to regulate when user input is locked (off) and unlocked (on). This is done with 
 * the boolean variable lock, and the spacebar input only works when the lock variable is false.
 * </p>
 *
 * <p>
 * Version 5 (2 hours, by Samuel Otazo, on 2024/06/03):
 * Added the method cutSentence(String sentence) that separates a long line of text into smaller 
 * sections, each one 60 characters long.
 * Started the implementation of the cutSentence() method.
 * Added the variables gameState, personNum, isHappy, the String array text, and the Century Gothic 
 * font centuryGothic. These variables help with the creation of the dialogue and have if-statements
 * checking for their numerous values in the paint() method.
 * Completed the dialogue maker by having a template in the play() method.
 * </p>
 *
 * <p>
 * Version 6 (3 hours 30 min, by Samuel Otazo, on 2024/06/04):
 * Completed the cutSentence() method, fixing an error with the implementation of the array 
 * separation.
 * Completed the dialogue by adding 20 possible values for the gameState and printing out dialogue 
 * accordingly.
 * Completely revamped the paint() method, removing unnecessary if-statements as they were 
 * associated with the menu.
 * Changed the paint() method so that Mr. Avetsark and his desk are only displayed when gameState is 
 * between 0 and 8 inclusive and 18 and 20 inclusive.
 * Changed the paint() method so that AI-Den is only displayed when the gameState is between 9 and 
 * 16 inclusive. This accounts for the part of the story where AI-Den replaces Mr. Avetsark.
 * </p>
 *
 * <p>
 * Version 7 (3 hours, by Dorian Groza, on 2024/06/06):
 * Removed lock, isHappy, and personNum.
 * Added the currentFace and currentName as part of a rework of paint().
 * Reworked the paint() method to always draw currentFace and currentName instead of having a large if structure based on gameState.
 * Added the methods getMenuChoice and drawMenu() tto implement a menu at the end of the level.
 * Added a buffer of 0.5 seconds between dialogue lines with the startTime and timer variables, so that dialogue could not be skipped.
 * </p>
 * 
 * <p>
 * Version 8 (1 hour 10 mins, by Samuel Otazo, on 2024/06/11):
 * Added the methods getStringDetails and drawCentered (both by Alex Yan) to fix the problem of the names not being centered.
 * Changed the implementation of the paint() method to replace drawString with drawCentered()
 * Replaced the bigStudentHeads array with individual variables for each student head used in Level 1 
 * </p>
 *
 * <p>
 * Citations:
 * </p>
 *
 * <p>
 * 1) The getStringDetails() and drawTextCentered() methods were originally by classmate Alex Yan.
 * </p>
 *
 * <h2>Course Info:</h2>
 * Course: ICS4U0, Teacher: Ms. Krasteva
 * 
 * @author Samuel Otazo and Dorian Groza
 * @version 2.0
 */
public class Level1 extends Level
{
   /**
    * Stores the image of the main character, AI-Den, to be displayed during the level.
    */
   Image aiden;
   /**
    * Stores the image of the background of the classroom to be displayed during the level.
    */
   Image backgroundClassroom; 
   /**
    * Stores the image of the desk to be displayed above the students during the level.    
    */
   Image desk; 
   /**
    * Stores the image of the chair that the students sit on.
    */
   Image chair; 
   /**
    * Stores the image of Mr Avetsark, to be displayed when he is present in the classroom.
    */
   Image mrAvetsarkBody; 
   /**
    * Stores the image of the desk where Mr Avetsark sits in the classroom.
    */
   Image mrAvetsarkDesk;
   /**
    * An array that stores the images of the students, used when drawing the level.
    */
   Image[] studentImages;
   /**
    * An array that stores the faces of al the students that speak in this level, so that they can be displayed on the dialgoue box.
    */
   Image[] bigStudentHeads;
   /**
    * Stores each student's name, to be used when drawing the dialoge box to say who is speaking.    
    */
   String[] studentNames;
   /**
    * Stores the text that is displayed in the dialogue. Each element in the array is at most 65 characters long.
    */
   String[] text;
   /**
    * Stores the image of Mr Avetsark's face that is displayed during the dialogue.
    */
   Image mrAvetsarkHead;
   /**
    * Stores the image of AI-Den's face to be used during dialogue.
    */
   Image aidenHead;
   /**
    * Stores the image the dialogue screen to be used as the background of the dialogue.
    */
   Image dialogueScreen;
   /**
    * Stores the font that is used in Level 1.
    */
   Font centuryGothic;
   /**
    * Stores the current image to be displayed during the dialogue. Keeps track of which face to display.
    */
   private Image currentFace;
   /**
    * Stores the current name to be displayed during the dialogue. Keeps track of which name to display.
    */
   private String currentName;
   /**
    * An integer that stores the state of the game. It is used to determine which dialogue screen to print out, as well as when to lock input, show
    * different characters on the screen, and display the final menu.
    */
   private int gameState;
   /**
    * A Menu variable that stores the menu that is currently active. For this level, that would only be the menu at the end of the level.
    */
   private Menu currentMenu;
   /**
    * Stores the current time, in milliseconds, at certian points throughout the program. It is used so that the program knows when to end the buffer period
    * of 0.5 seconds between sets of dialoge.
    */
   private long startTime;
   
   /**
    * Initializes every declared variable of the Level 1 class, including the playerX and playerY variables, which are inherited from the Level class.
    */
   public Level1()
   {
      super();
      
      studentImages = new Image[]
      {
         Main.readImage("student1body.png"), Main.readImage("student2body.png"), Main.readImage("student3body.png"), Main.readImage("student4body.png"), Main.readImage("student5body.png"), Main.readImage("student6body.png"), Main.readImage("student7body.png"), Main.readImage("student8body.png"), Main.readImage("student9body.png"), Main.readImage("student10body.png"),
      };
      
      bigStudentHeads = new Image[]{Main.readImage("student7sad.png"), Main.readImage("student7happy.png"), Main.readImage("student6sad.png"), Main.readImage("student2happy.png"), Main.readImage("student4sad.png"), Main.readImage("student9sad.png")};
      
      studentNames = new String[]{"James", "Charlie", "John", "Alex", "Nikolas", "Ming", "Amanda", "Khloe", "Dalia", "Mary"};
      
      for(int i = 0;i<10;i++)
      {
         if(i == 5)
         {
            bigStudentHeads[i] = bigStudentHeads[i].getScaledInstance(66, 60, Image.SCALE_DEFAULT);
         }
         else if(i < 6)
         {
            bigStudentHeads[i] = bigStudentHeads[i].getScaledInstance(54, 60, Image.SCALE_DEFAULT);
         }
         studentImages[i] = studentImages[i].getScaledInstance(33, 84, Image.SCALE_DEFAULT);
      }
      
      aiden = Main.readImage("aiden_forward.png").getScaledInstance(45, 82, Image.SCALE_DEFAULT);
      backgroundClassroom = Main.readImage("backgroundclassroom.png").getScaledInstance(784, 461, Image.SCALE_DEFAULT); 
      desk = Main.readImage("desk.png").getScaledInstance(61, 56, Image.SCALE_DEFAULT); 
      chair = Main.readImage("chair.png").getScaledInstance(37, 65, Image.SCALE_DEFAULT); 
      mrAvetsarkBody = Main.readImage("mravetsarkbody.png");
      mrAvetsarkDesk = Main.readImage("mravetsarkdesk.png");
      mrAvetsarkHead = Main.readImage("mravetsarkhead.png").getScaledInstance(54, 60, Image.SCALE_DEFAULT);
      aidenHead = Main.readImage("aidenhead.png").getScaledInstance(68, 44, Image.SCALE_DEFAULT);
      dialogueScreen = Main.readImage("dialoguescreen.png");
            
      gameState = -1;
      centuryGothic = new Font(Font.SANS_SERIF, Font.PLAIN, 17);
      playerX = -30;
      playerY = 200;
   }
   
   /**
    * This method is the primary method responsible for drawing the level, using other helper methods. Draws the background, chairs, students, desks, 
    * AI-Den, and Mr. Avetsark, and determines what to draw in the dialogue box depending on the value of the gameState variable.
    * @param g A Graphics variable passed to the method by default in order to draw on the JPanel component(which is then attached to the JFrame) using its
    *          methods.
    */
   public void paint(Graphics g)
   {
      g.setFont(centuryGothic);
      g.drawImage(backgroundClassroom, 0, 0, null);
      
      for(int i = 0;i < 5;i++)
      {
         g.drawImage(chair, 100+110*i, 128, null);
         g.drawImage(studentImages[i], 100+110*i, 110, null);
         g.drawImage(desk, 88+110*i, 150, null);    
         g.drawImage(chair, 100+110*i, 278, null);
         g.drawImage(studentImages[i+5], 100+110*i, 260, null);
         g.drawImage(desk, 88+110*i, 300, null);  
      }
      
      if(gameState < 9 || (gameState > 18 && gameState < 27))
      {
         g.drawImage(mrAvetsarkBody, 550, 220, null);
      }
      if(gameState > -1 && gameState < 27)
      {
         g.drawImage(mrAvetsarkDesk, 550, 220, null);
      }
      
      
      g.setColor(Color.black);
      if((gameState >= 9 && gameState < 18) || (gameState >= 23 && gameState < 27))
      {
         g.drawImage(aiden, playerX, playerY, null);
      }
      if(gameState != -1 && gameState < 27)
      {
         if(gameState != 9 && gameState != 23)
         {
            g.drawImage(dialogueScreen, 0, 250, null);
         }
         
         if(currentFace != null)
         {
            g.drawImage(currentFace,50,270,null);
            g.setColor(Color.white);
            drawCentered(g, currentName, centuryGothic, 152, 380, 5);
         }
                  
         g.setColor(Color.white);
         if(text[0] != null && gameState != 9 && gameState != 23 && gameState != 26)
         {
            for(int i = 0;i<text.length;i++)
            {
               g.drawString(text[i], 200, 280 + 25 * i);
            }
         }
         if(gameState == 3 || gameState == 7 || gameState == 13 || gameState == 18 || gameState == 26)
         {
            g.setColor(Color.black);
            g.fillRect(50, 254, 150, 143);
            g.setColor(Color.white);
            g.fillRect(50, 250, 150, 4);
            g.fillRect(50, 397, 150, 4);
         }
      }
      
      if(gameState == 26)
      {
         g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,20));
         g.drawString("You've reached the end of level 1!",220,310);
         
         g.setFont(centuryGothic);
         drawMenu(g);
      }
      
      if(gameState != 9 && gameState != 23 && lastAction == 5 && timer(startTime) > 0.5)
      {
         gameState++;
      }
      
      if((lastAction == 2 || lastAction == 4) && currentMenu != null)
      {
         currentMenu.setChoice(lastAction);
      }
      
      lastAction = 0;
   }
   
   /**
    * This method is used to draw the Level 1 class from the Main class, and contains the logic for the execution of the level.
    * It repeatedly sets the text, face, and name to be displayed, and then redraws the panel until space is pressed. It also animates AI-den moving around
    * the room at various parts of the level.
    */
   public void play()
   {
      gameState = 0;
      text = cutSentence("Hey class! Today we'll be talking about trigonometric identities!");
      currentFace = mrAvetsarkHead;
      currentName = "Mr. Avetsark";
      
      startTime = System.currentTimeMillis();
      
      while(gameState == 0)
      {
         repaint();      
      }
      
      text = cutSentence("That sounds so boring…");
      currentFace = bigStudentHeads[0];
      currentName = studentNames[6];
      
      startTime = System.currentTimeMillis();
      
      while(gameState == 1)
      {
         repaint();
      }
      
      text = cutSentence("Don't worry, they are surprisingly exciting to learn about! Let's start with proving the Pythagorean identity.");
      currentFace = mrAvetsarkHead;
      currentName = "Mr. Avetsark";
      
      startTime = System.currentTimeMillis();
      
      while(gameState == 2)
      {
         repaint();
      }
      
      text = cutSentence("Lesson continues…");
      currentFace = null;
      
      startTime = System.currentTimeMillis();
      
      while(gameState == 3)
      {
         repaint();
      }
      text = cutSentence("Wow, that was actually really interesting!");
      currentFace = bigStudentHeads[1];
      currentName = studentNames[6];
      
      startTime = System.currentTimeMillis();
      
      while(gameState == 4)
      {
         repaint();
      }
      text = cutSentence("Phew... I’m exhausted. Answering so many questions is getting tiring. It seems like I’m forgetting some of this stuff as time goes on. Maybe I should consider getting someone to help… But who will be good enough to teach my students? We don’t have enough substitute teachers around here. Hmmmmm…");
      currentFace = mrAvetsarkHead;
      currentName = "Mr. Avetsark";
      
      startTime = System.currentTimeMillis();
      
      while(gameState == 5)
      {
         repaint();
      }
      text = cutSentence("AHA! I’ll just buy one of the new AI teacher robots that are so popular these days. With all these advancements recently, they must be pretty good.");
      
      startTime = System.currentTimeMillis();
      
      while(gameState == 6)
      {
         repaint();
      }
      text = cutSentence("The next day…");
      currentFace = null;
      
      startTime = System.currentTimeMillis();
      
      while(gameState == 7)
      {
         repaint();
      }
      text = cutSentence("Good morning class! I'll be gone for the rest of the week on vacation. Don't worry though, I bought a state-of-the-art teacher AI to replace me. Say hello to AI-den!");
      currentFace = mrAvetsarkHead;
      currentName = "Mr. Avetsark";
      
      startTime = System.currentTimeMillis();
      
      while(gameState == 8)
      {
         repaint();
      }
      currentFace = null;
      repaint();
      for(int i = 0;i<60;i++)
      {
         playerX++;
         try
         {
            Thread.sleep(5);
         }
         catch(Exception e)
         {
         }
         repaint();
      }
      for(int i = 0;i<180;i++)
      {
         playerY--;
         try
         {
            Thread.sleep(5);
         }
         catch(Exception e)
         {
         }
         repaint();
      }
      for(int i = 0;i<570;i++)
      {
         playerX++;
         try
         {
            Thread.sleep(5);
         }
         catch(Exception e)
         {
         }
         repaint();
      }
      for(int i = 0;i<130;i++)
      {
         playerY++;
         try
         {
            Thread.sleep(5);
         }
         catch(Exception e)
         {
         }
         repaint();
      }
      gameState++;
      text = cutSentence("Hello class. I hope that we have a lot of fun together!");
      currentFace = aidenHead;
      currentName = "AI-den";
      
      startTime = System.currentTimeMillis();
      
      while(gameState == 10)
      {
         repaint();
      }
      text = cutSentence("As you can see, cos^2(a)+sin^2(a)=1. Use this formula whenever you encounter these types of problems… You can also use other formulas from this to solve this problem, like  sin A/2 = ±√[(1 - cos A) / 2], cos A/2 = ±√[(1 + cos A) / 2], and tan A/2 = ±√[1 - cos A] / [1 + cos A].");
      
      startTime = System.currentTimeMillis();
      
      while(gameState == 11)
      {
         repaint();
      }
      text = cutSentence("This is so boring now, it’s just spewing out information. It doesn’t feel human at all…");
      currentFace = bigStudentHeads[2];
      currentName = studentNames[5];
      
      startTime = System.currentTimeMillis();
      
      while(gameState == 12)
      {
         repaint();
      }
      text = cutSentence("2 minutes later…");
      currentFace = null;
      
      startTime = System.currentTimeMillis();
      
      while(gameState == 13)
      {
         repaint();
      }
      text = cutSentence("Okay class, I marked all of your assignments and updated your grades.");
      currentFace = aidenHead;
      currentName = "AI-den";
      
      startTime = System.currentTimeMillis();
      
      while(gameState == 14)
      {
         repaint();
      }
      text = cutSentence("Wow, already!? That was fast.");
      currentFace = bigStudentHeads[3];
      currentName = studentNames[1];
      
      startTime = System.currentTimeMillis();
      
      while(gameState == 15)
      {
         repaint();
      }
      text = cutSentence("I have also marked your quadratics tests. Time to give you feedback on it. I understand that quadratics is a hard subject, but make sure to double-check your work next time and don’t forget the clues for a perfect square trinomial. I will now hand out your tests.");
      currentFace = aidenHead;
      currentName = "AI-den";
      
      startTime = System.currentTimeMillis();
      
      while(gameState == 16)
      {
         repaint();
      }
      text = cutSentence("That doesn't help at all!!! I'm failing this course, and all you can tell me is to double-check my work!?!? Man, this AI helper sucks…");
      currentFace = bigStudentHeads[4];
      currentName = studentNames[3];
      
      startTime = System.currentTimeMillis();
      
      while(gameState == 17)
      {
         repaint();
      }
      text = cutSentence("The next week…");
      currentFace = null;
      
      startTime = System.currentTimeMillis();
      
      while(gameState == 18)
      {
         repaint();
      }
      text = cutSentence("I'm back class! How was your experience with AI-den?");
      currentFace = mrAvetsarkHead;
      currentName = "Mr. Avetsark";
      
      startTime = System.currentTimeMillis();
      
      while(gameState == 19)
      {
         repaint();
      }
      text = cutSentence("Terrible! It's good at providing information and managing data and numbers, but it’s so bad at giving constructive feedback that tells us what we need to improve on without making us sad. It’s also terrible at being empathetic and making connections with the class.");
      currentFace = bigStudentHeads[5];
      currentName = studentNames[8];
      
      startTime = System.currentTimeMillis();
      
      while(gameState == 20) 
      {
         repaint();
      }
      text = cutSentence("Hmmmm, if only there was a way that I could improve him… I know, I'll retrain the AI from scratch!");
      currentFace = mrAvetsarkHead;
      currentName = "Mr. Avetsark";
      
      startTime = System.currentTimeMillis();
      
      while(gameState == 21)
      {
         repaint();
      }
      text = cutSentence("Hey AI-den!");
      currentFace = mrAvetsarkHead;
      currentName = "Mr. Avetsark";
      
      startTime = System.currentTimeMillis();
      
      while(gameState == 22)
      {
         repaint();
      }
      
      currentFace = null;
      playerX = -50;
      playerY = 200;
      repaint();
      for(int i = 0;i<100;i++)
      {
         playerX++;
         try
         {
            Thread.sleep(5);
         }
         catch(Exception e)
         {
         }
         repaint();
      }
      for(int i = 0;i<50;i++)
      {
         playerY--;
         try
         {
            Thread.sleep(5);
         }
         catch(Exception e)
         {
         }
         repaint();
      }
      for(int i = 0;i<550;i++)
      {
         playerX++;
         try
         {
            Thread.sleep(5);
         }
         catch(Exception e)
         {
         }
         repaint();
      }
      gameState++;
      text = cutSentence("Yes, Mr. Avetsark?");
      currentFace = aidenHead;
      currentName = "AI-den";
      
      startTime = System.currentTimeMillis();
      
      while(gameState == 24)
      {
         repaint();
      }
      text = cutSentence("I need you to be more compassionate towards students, and provide more constructive feedback by highlighting the positives as well as the areas of improvement for each student. We will be working on these qualities in the coming days to improve your skills in non-quantitative areas.");
      currentFace = mrAvetsarkHead;
      currentName = "Mr. Avetsark";
      
      startTime = System.currentTimeMillis();
      
      while(gameState == 25)
      {
         repaint();
      }
      
      currentFace = null;
      currentMenu = new Menu(new String[]{"Return to main menu", "Continue to Level 2"},false);
      
      startTime = System.currentTimeMillis();
      
      while(gameState == 26)
      {
         repaint();
      }
   }
   
   /**
    * This method is used in the formatting of dialogue to cut a String into a String array of lines no greater than 65 characters long.
    * @param sentence A String variable passed into the method that is the initial sentence that needs to be cut into smaller lines.
    * @return A String array, where each row is a String no longer than 65 characters.
    */
   public String[] cutSentence(String sentence)
   {
      String[] separated = new String[1+sentence.length()/65];
      String temp;
      for(int i = 0;i<separated.length;i++)
      {
         if(sentence.length() < 65)
         {
            separated[i] = sentence;
         }
         else if(sentence.length() == 65)
         {
            String[] arr = new String[1];
            arr[0] = sentence;
            return arr;
         }
         else
         {
            temp = sentence.substring(0, 65);
            separated[i] = sentence.substring(0,sentence.substring(0,65).lastIndexOf(" ")+1);
            sentence = sentence.substring(temp.lastIndexOf(" ")+1);
         }
      }
      return separated;
   }
   
   /**
    * This method draws the menu at the end of Level 1. It gets the current state of the menu and displays it on the window.
    * @param g A Graphics variable passed to the method by default in order to draw on the JPanel component(which is then attached to the JFrame) using its
    *          methods.
    */
   private void drawMenu(Graphics g)
   {
      String[] options = currentMenu.getOptions();
      for(int i = 0; i<options.length; i++)
      {
         if(i == currentMenu.getChoice())
         {
            g.setColor(new Color(255,206,42));
         }
         else
         {
            g.setColor(Color.white);
         }

         g.drawString(options[i],200+200*i, 350);
      }
   }
   
   /**
    * This is a getter method that returns the menu choice selected at the end of the level. 
    * @return The option on the menu that the user is currently on, used to determine whether to return to the main menu or continue to level 2.
    */
   public int getMenuChoice()
   {
      return currentMenu.getChoice();
   }
   
   /**
    * This method gets the graphical details of a string (such as its width and height) given the specific font it uses.
    * @param g A Graphics variable passed to the method by default in order to draw on the JPanel component(which is then attached to the JFrame) using its
    *          methods.
    * @param s The String of which the details will be returned.
    * @param f The font that the String parameter uses.
    * @return A Rectangle2D containing important graphical details such as the String parameter's width.
    */
   private Rectangle2D getStringDetails(Graphics g, String s, Font f)
   {
      return g.getFontMetrics(f).getStringBounds(s, g);
   }
   
     /**
      * This method draws a String centered horizontally on a specific section of the screen. This section is based on the width and offset variables.
      * @param g A Graphics variable passed to the method by default in order to draw on the JPanel component(which is then attached to the JFrame) using its
      *          methods.
      * @param s The String to be drawn on the screen.
      * @param f The specific font that the aforementioned String uses.
      * @param width The width of the area that the String will be centred on.
      * @param y The y-ccordinate at which the String should be drawn.
      * @param offset The offset, in pixels, of the String to be drawn from the left side of the screen.
      */
     public void drawCentered(Graphics g, String s, Font f, int width, int y, int offset)
     {
             Rectangle2D r2d = getStringDetails(g, s, f);
             int start = (int) (width - r2d.getWidth()) / 2;
             g.drawString(s, start + offset, y);
     }
   }