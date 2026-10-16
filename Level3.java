import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * This class makes level 3 of the game, that being the minigame.
 *
 * <p>
 * Version 1 (2 hours, by Dorian Groza, on 2024/05/21):
 * Imported and scaled all images necessary for Level 3.
 * Added basic movement logic.
 * Created the general structure of the play() method (the main while loop checking the number of strikes).
 * Added rudimentary UI to the header.
 * </p>
 *
 * <p>
 * Version 2 (2 hours, by Dorian Groza, on 2024/05/22):
 * Added the majority of the logic for answers (the questions, answers, correctAnswers, answerCoords, and answerDirs arrays, as well as setting the values
 * of answerCoords and answerDirs in play(), displaying questions/answers in paint(), and implementing answer movement in paint()).
 * Added pausing before answers start moving.
 * </p>
 *
 * <p>
 * Version 3 (3 hours, by Dorian Groza, on 2024/05/24):
 * Added basic collision detection for touching answers.
 * Added the answerQuestion method and the logic for calling it in paint().
 * Made it so that answers bounce off walls instead of going off the screen by adding if statements in paint().
 * </p>
 *
 * <p>
 * Version 4 (2 hours, by Dorian Groza, on 2024/05/26):
 * Added the full list of questions, answers, and correct answers.
 * Improved collision detection to be more accurate.
 * Changed the logic for answers boucing so it takes into account the length of the String.
 * </p>
 *
 * <p>
 * Version 5 (1 hour 30 mins, by Dorian Groza, on 2024/05/27):
 * Added pausing before answers start moving.
 * Created the gameState variable to assist with the pausing.
 * </p>
 *
 * <p>
 * Version 6 (1 hour 30 mins, by Dorian Groza, on 2024/05/28):
 * Added the tutorial screens that display at the start of the level.
 * Separated the majority of the code in the paint() method into the displayUI(), tutorial(), game(), and gameOver() methods, with if statements determining
 * what gets called based on gameState.
 * Modified code for displaying the UI so that all text fits (changed font size and used code by ALex Yan to centre the question).
 * </p>
 *
 * <p>
 * Version 7 (3 hours, by Dorian Groza, on 2024/05/29):
 * Fixed several answers/questions that had incorrect text (Antimony question, Hyperbole question, and expansion of x(x+3) question).
 * Added pausing before and after the questions move, adding the timer() method and various while loops in play() to do this.
 * Added a do-while loop at the start of play() to ensure that questions do not repeat, also added the taken array to accomplish this.
 * </p>
 *
 * <p>
 * Version 8 (5 minutes, by Dorian Groza, on 2024/06/05):
 * Fixed the issue where a print statement was required for one of the pauses to work by replacing calls to repaint() with calls to paintImmediately().
 * </p>
 *
 * <p>
 * Citations:
 * </p>
 *
 * <p>
 * 1) We learned the basics of how to create output using Swing from https://drive.google.com/file/d/1z86MBSUQ5TXL0Cl39Etu53yV2Z2SbATs/view.
 * </p>
 *
 * <p>
 * 2) We learned about the concepts of how to do collision detection with two rectangles from
 *    https://www.jeffreythompson.org/collision-detection/rect-rect.php.
 * </p>
 *
 * <p>
 * 3) Alex Yan showed us how to centre text, which is used for the question in displayUI().
 * </p>
 *
 * <p>
 * 4) The website at https://coderanch.com/t/341113/java/repaint-loop was used to find out about paintImmediately().
 * </p>
 *
 * <h2>Course Info:</h2>
 * Course: ICS4U0, Teacher: Ms. Krasteva
 *
 * @author Dorian Groza
 * @version 2.0
 */
public class Level3 extends Level
{
   /**
    * Stores the number of questions that the user has answered correctly. It is displayed on the right side of the header and determines the final rank.
    */
   private int numCorrect;
   
   /**
    * Stores the number of questions the user has answered incorrectly. It is displayed on the left side of the header, and the game ends upon reaching 3
    * strikes.
    */
   private int strikes;
   /**
    * This int determines what to display when paint() is called. It is set to different values in play() at different points in execution to draw the
    * tutorial screens, the main game screens, pause the game, and display the game over screen.
    */
   private volatile int gameState;
   /**
    * This int stores the index of the array that corresponds to the current question. It is used anytime the question and answers are needed, such as when
    * they are being drawn, when collision is being detected, and then the correct answer is being determined.
    */
   private volatile int questionIndex = -1;
   /**
    * Stores all of the questions that could be displayed. It is used to display the current question at the top of the screen.
    */
   private String[] questions =
           {
                   "Which of the following is Chicago?",
                   "What is the expanded form of x(x+3)?",
                   "A reaction between an acid & base is...",
                   "What war was the Battle of the Atlantic part of?",
                   "The opposite of photosynthesis is?",
                   "What is the value of sin(90°)?",
                   "What is the symbol for Antimony?",
                   "What is the second largest country by area?",
                   "What is the second largest country by population?",
                   "Another word for overexaggeration is...",
                   "Newton's Second Law states that...",
                   "Who was the ruler of the USSR from 1924 to 1953?",
                   "Which of the following functions is its own inverse?",
                   "Which of these is known as the \"Red Planet\"?",
                   "What is the chemical formula of hydrobromic acid?",
                   "Who is the narrator of \"To Kill a Mockingbird\"?",
                   "What mountain range separates Europe and Asia?",
                   "Which of the following is equivalent to (sin²θ + cos²θ)?",
                   "What is the derivative of 2x²-2?",
                   "Which of these was not written by Shakespeare?"
           };
   /**
    * Stores the 4 answers to each questions, with each row corresponding to a question. It is used to draw the answers on the screen.
    */
   private String[][] answers =
           {
                   {"State","City","Continent","Town"},
                   {"x²+3x","-x","2x-3x","x²-3"},
                   {"Synthesis","Decomposition","Combustion","Neutralization"},
                   {"The War of 1812","World War I","World War II","The 7 Years War"},
                   {"Protosynthesis","Cellular Respiration","Fermentation","Digestion"},
                   {"0","1/2","-1","1"},
                   {"An","Pb","Sb","A"},
                   {"China","Canada","Russia","United States"},
                   {"India","China","United States","Indonesia"},
                   {"Situational Irony","Hyperbole","Allegory","Dramatic Irony"},
                   {"F = ma","s = d/t","e = mc²","V = IR"},
                   {"Vladimir Lenin","Mikhail Gorbachev","Joseph Stalin","Nikita Kruschev"},
                   {"1/x","x²","ln(x)","2x²/x"},
                   {"Saturn","Venus","Jupiter","Mars"},
                   {"HBr","HB","HBrO3","H2Br3"},
                   {"Scout Finch","Jem Finch","Atticus Finch","Boo Radley"},
                   {"Himalayas","Urals","Alps","Andes"},
                   {"-1","tan²θ","1","cot²θ"},
                   {"3x²","4x + C","2x","4x"},
                   {"Julius Caesar","Othello","Doctor Faustus","As You Like It"}
           };
   /** Stores the column index of the correct answers in the answers array. It is used to determine if the user got a question right or wrong. */
   private int[] correctAnswers = {1,0,3,2,1,3,2,1,1,1,0,2,0,3,0,0,1,2,3,2};
   /** Stores the x and y positions of each answer, so that it can easily by modified for animations.*/
   private double[] answerCoords = new double[8];
   /** Stores the change in x and y positions of each answer (±0.15) each frame, making it easier to randomize movement and incorporate bouncing/collisions. */
   private double[] answerDirs = new double[8];
   /** Stores whether of not each question has already been shown, to avoid repeats. */
   private boolean[] taken  = new boolean[20];

   /**
    * Stores the image of the main character sprite for displaying in paint().
    */
   private Image aiden = Main.readImage("aiden_forward.png");
   /**
    * Stores the image of the background for displaying in paint().
    */
   private Image background = Main.readImage("simulation_bg.png");
   /**
    * Stores the image of the the header for displaying in paint().
    */
   private Image header = Main.readImage("simulation_header.png");
   /**
    * Stores the image of the red Xs for the strikes, for displaying in paint().
    */
   private Image strike = Main.readImage("strike.png");
   /**
    * Stores the color of the text, so that a new Color object is not created each time the colour is set.
    */
   private Color lightBlue = new Color(74, 203,255);

   /**
    * This method is the primary method responsible for drawing the level, using other helper methods. Draws the background, header, player, potentially
    * answers, and determines what to draw in the middle of the header depending on the value of the gameState.
    * @param g A Graphics variable passed to the method by default in order to draw on the JPanel component(which is then attached to the JFrame) using its
    *          methods.
    */
   public void paint(Graphics g)
   {
      g.drawImage(background,0,0,null);
      g.drawImage(header,0,0,null);

      g.drawImage(aiden,playerX,playerY,null);

      if(gameState <= 12)
      {
         tutorial(g);
      }
      else if(gameState == 13)
      {
         displayUI(g);
         game(g);
      }
      else if(gameState == 14)
      {
         displayUI(g);
         displayAnsweredChoices(g);
      }
      else if(gameState == 15)
      {
         displayUI(g);
      }
      else if(gameState == 16)
      {
         gameOver(g);
      }
   }

   /**
    * This method draws the initial tutorial for this level, that explains the rules of the game. More specifically, it draws different text in the middle
    * of the header based on the value of the gameState instance variable, so as it is incremented, the text is shown sequentially.
    * @param g A Graphics variable passed to the method by default in order to draw on the JPanel component(which is then attached to the JFrame) using its
    *          methods.
    */
   private void tutorial(Graphics g)
   {
      if(gameState == 0)
      {
         g.setColor(lightBlue);
         g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,25));
         g.drawString("Hello, and welcome back to the",200,40);
         g.drawString("simulation inside your mind!",230,65);
      }
      else if(gameState == 1)
      {
         g.setColor(lightBlue);
         g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,25));
         g.drawString("After a long day of helping students,",180,30);
         g.drawString("it's time to train with more cut",230,55);
         g.drawString("and dry questions.",280,80);
      }
      else if(gameState == 2)
      {
         g.setColor(lightBlue);
         g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,25));
         g.drawString("Up here on the header, a question",190,30);
         g.drawString("will appear. At the same time, 4",210,55);
         g.drawString("answers will show up.",275,80);
      }
      else if(gameState == 3)
      {
         g.setColor(lightBlue);
         g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,25));
         g.drawString("You will have a few seconds to look",180,30);
         g.drawString("at the questions and answers as",210,55);
         g.drawString("a grace period.",305,80);
      }
      else if(gameState == 4)
      {
         g.setColor(lightBlue);
         g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,25));
         g.drawString("After that, the answers will start",210,30);
         g.drawString("moving around the room and will",200,55);
         g.drawString("bounce off of the walls.",260,80);
      }
      else if(gameState == 5)
      {
         g.setColor(lightBlue);
         g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,25));
         g.drawString("When the questions start moving,",200,30);
         g.drawString("you will also gain the ability",235,55);
         g.drawString("to move around.",310,80);
      }
      else if(gameState == 6)
      {
         g.setColor(lightBlue);
         g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,25));
         g.drawString("You will have to maneuver around",195,30);
         g.drawString("the room to touch the answer that",195,55);
         g.drawString("you think is right.",300,80);
      }
      else if(gameState == 7)
      {
         g.setColor(lightBlue);
         g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,25));
         g.drawString("Upon touching an answer, the ",220,30);
         g.drawString("correct answer will turn green, and ",190,55);
         g.drawString("all the others will turn red.",250,80);
      }
      else if(gameState == 8)
      {
         g.setColor(lightBlue);
         g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,25));
         g.drawString("If you got the question right, then",200,30);
         g.drawString("the correct answers counter will ",210,55);
         g.drawString("increment.",330,80);
      }
      else if(gameState == 9)
      {
         g.setColor(lightBlue);
         g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,25));
         g.drawString("If you got the question wrong,",220,35);
         g.drawString("however, then you will get a strike.",192,60);
      }
      else if(gameState == 10)
      {
         g.setColor(lightBlue);
         g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,25));
         g.drawString("After this, you will be teleported back",175,25);
         g.drawString("to the middle and the process",220,50);
         g.drawString("will repeat until you get 3 strikes.",205,75);
      }
      else if(gameState == 11)
      {
         g.setColor(lightBlue);
         g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,25));
         g.drawString("Once you get 3 strikes, your rank",200,30);
         g.drawString("will be shown, and after some time",190,55);
         g.drawString("you will return to the main menu.",205,80);
      }
      else if(gameState == 12)
      {
         g.setColor(lightBlue);
         g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,25));
         g.drawString("So, that was all the rules of this",210,40);
         g.drawString("minigame. Good luck!",260,65);
      }
   }

   /**
    * Draws the number of correct answers, the question, the answers, and the number of strikes. It displays all the information everything required to let
    * the user know what is happening in the game at that moment.
    *
    * @param g A Graphics variable passed to the method by default in order to draw on the JPanel component(which is then attached to the JFrame) using its
    *          methods.
    */
   private void displayUI(Graphics g)
   {
      g.setColor(lightBlue);
      g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,30));

      g.drawString("The Current Question is...",220,40);
      g.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,15));

      if(questionIndex != -1)
      {
         g.setColor(lightBlue);
         g.drawString(questions[questionIndex],200+(int) (400 - g.getFontMetrics(new Font(Font.SANS_SERIF,Font.PLAIN,15)).getStringBounds(questions[questionIndex], g).getWidth()) / 2,80);

         g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,20));
         g.drawString(answers[questionIndex][0],(int)answerCoords[0],(int)answerCoords[1]);
         g.drawString(answers[questionIndex][1],(int)answerCoords[2],(int)answerCoords[3]);
         g.drawString(answers[questionIndex][2],(int)answerCoords[4],(int)answerCoords[5]);
         g.drawString(answers[questionIndex][3],(int)answerCoords[6],(int)answerCoords[7]);
      }

      g.setColor(lightBlue);
      g.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,14));
      g.drawString("Strikes:",22,30);

      for(int i = 0; i<strikes; i++)
      {
         g.drawImage(strike,80 + 30*i,15,null);
      }

      g.setColor(lightBlue);
      g.drawString("Correct answers: " + numCorrect,635,30);

   }
   /**
    * This method is responsible for most of the logic in the gameplay section of the level. It handles player movement, answer movement, answers bouncing
    * off walls, and collision detection.
    *
    * @param g A Graphics variable passed to the method by default in order to draw on the JPanel component(which is then attached to the JFrame) using its
    *          methods.
    */
   private void game(Graphics g)
   {
      if(lastAction == 1 && playerY > 110)
      {
         playerY -= 15;
      }
      else if(lastAction == 2 && playerX > 30)
      {
         playerX -= 15;
      }
      else if(lastAction == 3 && playerY < 370)
      {
         playerY += 15;
      }
      else if(lastAction == 4 && playerX < 720)
      {
         playerX += 15;
      }

      lastAction = 0;

      if(questionIndex != -1)
      {
         if(answerCoords[0] <= 20 || answerCoords[0]+answers[questionIndex][0].length()*10 >= 775)
         {
            answerDirs[0] *= -1;
         }
         if(answerCoords[1] <= 110 || answerCoords[1] >= 460)
         {
            answerDirs[1] *= -1;
         }
         if(answerCoords[2] <= 20 || answerCoords[2]+answers[questionIndex][1].length()*10 >= 775)
         {
            answerDirs[2] *= -1;
         }
         if(answerCoords[3] <= 110 || answerCoords[3] >= 460)
         {
            answerDirs[3] *= -1;
         }
         if(answerCoords[4] <= 20 || answerCoords[4]+answers[questionIndex][2].length()*10 >= 775)
         {
            answerDirs[4] *= -1;
         }
         if(answerCoords[5] <= 110 || answerCoords[5] >= 460)
         {
            answerDirs[5] *= -1;
         }
         if(answerCoords[6] <= 20 || answerCoords[6]+answers[questionIndex][3].length()*10 >= 775)
         {
            answerDirs[6] *= -1;
         }
         if(answerCoords[7] <= 110 || answerCoords[7] >= 460)
         {
            answerDirs[7] *= -1;
         }


         if(playerX + 40 >= answerCoords[0] && playerX <= answerCoords[0] + 10*answers[questionIndex][0].length() && playerY + 90 >= answerCoords[1] && playerY <= answerCoords[1])
         {
            answerQuestion(0,g);
         }
         else if(playerX + 40 >= answerCoords[2] && playerX <= answerCoords[2] + 10*answers[questionIndex][1].length() && playerY + 90 >= answerCoords[3] && playerY <= answerCoords[3])
         {
            answerQuestion(1,g);
         }
         else if(playerX + 40 >= answerCoords[4] && playerX <= answerCoords[4] + 10*answers[questionIndex][2].length() && playerY + 90 >= answerCoords[5] && playerY <= answerCoords[5])
         {
            answerQuestion(2,g);
         }
         else if(playerX + 40 >= answerCoords[6] && playerX <= answerCoords[6] + 10*answers[questionIndex][3].length() && playerY + 90 >= answerCoords[7] && playerY <= answerCoords[7])
         {
            answerQuestion(3,g);
         }

         answerCoords[0] += answerDirs[0];
         answerCoords[1] += answerDirs[1];
         answerCoords[2] += answerDirs[2];
         answerCoords[3] += answerDirs[3];
         answerCoords[4] += answerDirs[4];
         answerCoords[5] += answerDirs[5];
         answerCoords[6] += answerDirs[6];
         answerCoords[7] += answerDirs[7];
      }

   }

   /**
    * Draws the screen shown after answering a question, where the correct answer is highlighted green, while the rest are red. It is also responsible for
    * increasing the number of correct answers or strikes, and resetting the values of questionIndex/answer directions.
    *
    * @param choice Stores the answer picked by the user. It will be compared to the correct answer index in the correctAnswers array to determine the if
    *               the user was right or wrong. 
    * @param g A Graphics variable passed to the method by default in order to draw on the JPanel component(which is then attached to the JFrame) using its
    *          methods.
    */
   private void answerQuestion(int choice, Graphics g)
   {
      g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,20));

      for(int i = 0; i<4; i++)
      {
         if(i == correctAnswers[questionIndex])
         {
            g.setColor(Color.green);
         }
         else
         {
            g.setColor(Color.red);
         }

         g.drawString(answers[questionIndex][i],(int)answerCoords[2*i],(int)answerCoords[2*i+1]);
      }

      for(int i = 0; i<8; i++)
      {
         answerDirs[i] = 0;
      }

      if(choice == correctAnswers[questionIndex])
      {
         numCorrect++;
      }
      else
      {
         strikes++;
      }

      gameState = 14;
   }

   /** Draws the selected question's answers during the feedback pause without moving them. */
   private void displayAnsweredChoices(Graphics g)
   {
      if (questionIndex < 0 || questionIndex >= answers.length) return;
      g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
      for (int i = 0; i < 4; i++)
      {
         g.setColor(i == correctAnswers[questionIndex] ? Color.GREEN : Color.RED);
         g.drawString(answers[questionIndex][i], (int) answerCoords[2 * i], (int) answerCoords[2 * i + 1]);
      }
   }

   /**
    * Displays the goodbye screen when the level is finished. It displays the rank of the user on the header, based on the number of points earned.
    *
    * @param g A Graphics variable passed to the method by default in order to draw on the JPanel component(which is then attached to the JFrame) using its
    *          methods.
    */
   private void gameOver(Graphics g)
   {
      g.setColor(lightBlue);
      g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,30));

      g.drawString("Training Complete!",260,35);
      g.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,20));
      g.drawString("Your rank is... " + ((numCorrect<=5)?"Subpar":(numCorrect<=10)?"Okay":(numCorrect<=15)?"Fairly Good":(numCorrect<=20)?"Great":(numCorrect<=25)?"Super":"Premier") + " AI",280,60);
      g.drawString("Returning to the main menu...",255,85);
   }

   /**
    * This method is used to run the Level3 class from the Main class, and contains the logic for the program flow of the level. It will first loop through
    * each of the tutorial screens, pausing for 4 seconds on each one. It then enters a loop where a random question that had not been chosen yet is
    * selected, the positions and directions of the player/answers are reset, the game pauses for 3 seconds to let the user see the question, and then
    * pauses again for 3 seconds after the character has touched a question. This loop repeats until the user gets 3 strikes, at which point the game over
    * screen is displayed.
    */
   private void pauseBriefly()
   {
      try
      {
         Thread.sleep(10);
      }
      catch (InterruptedException e)
      {
         Thread.currentThread().interrupt();
      }
   }

   public void play()
   {
      long startTime = 0;
      playerX = 380;
      playerY = 214;

      for(int i = 0; i<13; i++)
      {
         startTime = System.currentTimeMillis();

         while(timer(startTime) < 5)
         {
            paintImmediately(0,0,800,500);
            revalidate();
            pauseBriefly();
         }

         gameState++;
      }

      while(strikes != 3)
      {
         if(questionIndex == -1)
         {
            playerX = 380;
            playerY = 214;
            int counter = 0;

            do
            {
               if(counter == 20)
               {
                  for(int i = 0; i<20; i++)
                  {
                     taken[i] = false;
                  }
               }
               questionIndex = (int)(Math.random()*questions.length);
               counter++;
            }
            while(taken[questionIndex] == true);

            taken[questionIndex] = true;

            answerCoords[0] = 200;
            answerCoords[1] = 200;
            answerCoords[2] = 550;
            answerCoords[3] = 200;
            answerCoords[4] = 200;
            answerCoords[5] = 400;
            answerCoords[6] = 550;
            answerCoords[7] = 400;
            answerDirs[0] = (Math.random() >= 0.5)?0.15:-0.15;
            answerDirs[1] = (Math.random() >= 0.5)?0.15:-0.15;
            answerDirs[2] = (Math.random() >= 0.5)?0.15:-0.15;
            answerDirs[3] = (Math.random() >= 0.5)?0.15:-0.15;
            answerDirs[4] = (Math.random() >= 0.5)?0.15:-0.15;
            answerDirs[5] = (Math.random() >= 0.5)?0.15:-0.15;
            answerDirs[6] = (Math.random() >= 0.5)?0.15:-0.15;
            answerDirs[7] = (Math.random() >= 0.5)?0.15:-0.15;

            gameState = 15;

            startTime = System.currentTimeMillis();

            while(timer(startTime)<3)
            {
               paintImmediately(0,0,800,500);
               revalidate();
            }

            gameState = 13;
         }

         paintImmediately(0,0,800,500);
         revalidate();
         pauseBriefly();

         if (gameState == 14)
         {
            startTime = System.currentTimeMillis();

            while(timer(startTime) < 3)
            {
               paintImmediately(0,0,800,500);
               revalidate();
            }

            questionIndex = -1;
            gameState = 15;
         }

      }

      gameState = 16;
      startTime = System.currentTimeMillis();

      while(timer(startTime) < 3)
      {
         paintImmediately(0,0,800,500);
         revalidate();
      }
   }
}