import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;

/**
 * This class makes level 2 of the game, that being the testing level where the user must make ethical decisions relating to the classroom.
 *
  <p>
 * Version 1 (25 min, by Dorian Groza, on 2024/05/24):
 * Implementing a rudimentary menu in paint(), currently for bug testing purposes.
 * Implementing user input and corresponding movement of player sprite by creating a userInput() method.
 * </p>
 *
 * <p>
 * Version 2 (3 hours 30 min, by Soham Malla, on 2024/05/31):
 * Importing, drawing, and scaling all images necessary for Level 2. Image variables were intialized in the constructor()
 * Implementing dialogue system by adding a drawDialogue() method and dialogueTrack variable.
 * Editing userInput code by reducing speed, adding conditional statements to allow for controlling menu movement through userInput() as well.
 * Organizing code by moving the code responsible for drawing the background in the paint() method into a seperate drawBackground() method.
 * Moving the code responsible for drawing the dialogue menu options into a seperate drawMenu().
 * </p>
 *
 * <p>
 * Version 3 (3 hours, by Soham Malla, on 2024/06/03):
 * Fully implementing dialogue system, by adding Stringarrays for dialogue and responses, as well as adding a lock parameter to userInput() to stop movement when in dialogue. 
 * Implementing exit menu by adding an exitScreen() method.
 * Implementing instructions screen by adding extra code to the drawBackground() method, and adding a firstTime variable to only display instructions when it's the users first time playing the game.
 * </p>
 *
 * <p>
 * Version 4 (3 hours, by Soham Malla, on 2024/06/06):
 * Fully implementing and bug fixing collision detection by adding if-else structures to detect collision detection and editing conditonal statement responsible for user momvent to not change positoin if collision is detected.
 * Bug fixing exit menu screen(was not appearing in certain instances such as when user was in the middle of dialogue) by resetting the value of menuChoice and currentMenu before the exit screen was displayed in the play() method.
 * Fixing font sizes in dialogue and adjusting scaling of Ai-den.
 * Added a continueNext variable and a getContinueNext() method which are used to determine program flow after level 2 is finished. The Main class variable, titleChoice is set to continueNext, which can be used to control if the program goes back to the main menu or moves on to level 3.
 * </p>
 *
 * <p>
 * Version 5 (1 hour 40 mins, by Soham Malla, on 2024/06/06):
 * Editing collision detection to make it more lenient by editing the values in the condiotinal statement of the if-else structures responsible for collision detection.
 * Fixed dialogue options spacing by editing certain values in the drawMenu() method.
 * Added another line to instructions in the drawBackground() method.  
 * </p>
 *
 * <p>
 * Citations:
 * </p>
 *
 * <p>
 * 1) We learned how to make the getStringDetails() and drawTextCentered() methods for formatting text from Alex Yan.
 * </p> 
 *
 * <p>
 * 2) We learned about the concepts of how to do collision detection with two rectangles from
 *    https://www.jeffreythompson.org/collision-detection/rect-rect.php.
 * </p>
 *
 * <p>
 * 3) The getStringDetails() and drawTextCentered() methods were originally by classmate Alex Yan.
 * </p>
 *
 * <h2>Course Info:</h2>
 * Course: ICS4U0, Teacher: Ms. Krasteva
 *
 * @author Soham Malla and Dorian Groza
 * @version 2.0
 */
public class Level2 extends Level
{
   /** Stores the number of points the user has accumulated in the level. Used to determine if they can go onto the next level. */
   private int points;
   /** Stores the amount of time that has passed since the gameplay started. It is used to determine when to end the level. */
   long time;
   /** Responsible for controlling the flow of dialogue in paint().*/
   private int dialogueTrack;
   /** Responsible for "locking" player movement when dialogue is on screen. Limits user input to exclusively navigating dialogue box. */
   private boolean lock;
   /** Stores an instance of the Menu class, to be used for the menus throughout the level (the three answers to each question, and the game over menu). */
   private Menu currentMenu;
   /** Stores the choice selected in the various menus. Used to determine if the user got a question right or not, as well as what to do at the end of the level. */
   private int menuChoice;
   /** Stores and controls how long the level will run for. If the time limit is exceeded, the exit menu will be shown.*/
   private final int timeLimit;
   /** Stores the prompts that will be shown in the dialogue box when user the user first interacts with a student. There is 1 prompt per student.*/
   String[] prompts;
   /** Stores the options that will be shown to the user to select after interacting with a student and reading their prompt. There are 3 options for each prompt, and each row contains one response for each student. */
   String[][] options;
   /** Stores the responses that are displayed after the user selects an option. There is one response per option.*/
   String[][] responses;
   /** Stores the index of the option that is the correct answer for each prompt. The index represents the row number in the options[][] array, with the specific option being accessed through the current value of questionIndex. */
   int[] answerIndexes = {1, 2, 0, 2, 1, 2, 0, 1, 0, 2};
   /** Stores the number that determines the current question to be displayed. Also controls which student will need to be interacted with to begin the dialogue propmt. */
   int questionIndex = -1;
   /** Stores whether a question has already been shown. Each index corresponds to an index in the prompts[] array (they are parallel arrays). */
   boolean[] questionsDone = new boolean[10];
   /** Stores whether the user is playing this level for the first time. It is used to show the instructions screen either when it is first started or when the user goes to the exit menu and then back to the level.*/
   boolean firstTime;
   /**Determines if the game will continue to the mainMenu or to the next level when it ends.*/
   int continueNext;

   /** Stores the image for Ai-den, so that it can be drawn on the window.*/
   Image aiden = Main.readImage("aiden_forward.png");
   /** Stores the image for the dialogue box, so that it can be drawn on the window.*/
   Image dialogueBox = Main.readImage("dialoguescreen.png");
   /** Stores the image for the classroom in the background, so that it can be drawn on the window.*/
   Image backgroundClassroom = Main.readImage("backgroundclassroom.png");
   /** Stores the image for the student desks, so that it can be drawn on the window.*/
   Image desk = Main.readImage("desk.png");
   /** Stores the image for the student chairs, so that it can be drawn on the window.*/
   Image chair = Main.readImage("chair.png");

   /** Stores the image for the teacher's desk, so that it can be drawn on the window.*/
   Image mrAvetsarkDesk = Main.readImage("mravetsarkdesk.png");
   /** Stores the image file for the teacher, so that it can be drawn on the window.*/
   Image mrAvetsark = Main.readImage("mravetsarkbody.png");
   /** Stores the image file for the header at the top right of the level which is used to display the score and time left. */
   Image header = Main.readImage("level2header.png");
   /** Stores the image file for the menu screen, which is used to display the instructions for the level and the exit menu. */
   Image menu = Main.readImage("menu.png");
   /** Stores the images for every student's full body, as well as a happy and sad version of their faces.*/
   Image[][] studentImages = {
           {Main.readImage("student1body.png"), Main.readImage("student2body.png"), Main.readImage("student3body.png"), Main.readImage("student4body.png"), Main.readImage("student5body.png"), Main.readImage("student6body.png"), Main.readImage("student7body.png"), Main.readImage("student8body.png"), Main.readImage("student9body.png"), Main.readImage("student10body.png")},
           {Main.readImage("student1happy.png"), Main.readImage("student2happy.png"), Main.readImage("student3happy.png"), Main.readImage("student4happy.png"), Main.readImage("student5happy.png"), Main.readImage("student6happy.png"), Main.readImage("student7happy.png"), Main.readImage("student8happy.png"), Main.readImage("student9happy.png"), Main.readImage("student10happy.png")},
           {Main.readImage("student1sad.png"), Main.readImage("student2sad.png"), Main.readImage("student3sad.png"), Main.readImage("student4sad.png"), Main.readImage("student5sad.png"), Main.readImage("student6sad.png"), Main.readImage("student7sad.png"), Main.readImage("student8sad.png"), Main.readImage("student9sad.png"), Main.readImage("student10sad.png")}
   };

   /** Stores the names of each student, which are displayed in their dialogue box when the user interacts with them.*/
   String[] studentNames;

   /**
    * Default constructor of the Level2 class. Calls the superclass' constructor and scales the images of the students, Ai-den,
    * the background, and the desk and chairs to make them fit the canvas and be accurately proportioned. It is also used to initialize
    * other important variables used in the level to specific values.
    */
   public Level2()
   {
      super();
      for(int i = 0;i<10;i++)
      {
         studentImages[0][i] = studentImages[0][i].getScaledInstance(33, 84, Image.SCALE_DEFAULT);
         studentImages[2][i] = studentImages[2][i].getScaledInstance(54, 60, Image.SCALE_DEFAULT);
         studentImages[1][i] = studentImages[1][i].getScaledInstance(54, 60, Image.SCALE_DEFAULT);
         studentImages[2][i] = studentImages[2][i].getScaledInstance(54, 60, Image.SCALE_DEFAULT);
      }
      aiden = aiden.getScaledInstance(45, 82, Image.SCALE_DEFAULT);
      backgroundClassroom = backgroundClassroom.getScaledInstance(784, 461, Image.SCALE_DEFAULT);
      desk = desk.getScaledInstance(61, 56, Image.SCALE_DEFAULT);
      chair = chair.getScaledInstance(37, 65, Image.SCALE_DEFAULT);
      firstTime = true;
      playerX=25;
      playerY=25;
      dialogueTrack = 1;
      menuChoice = -1;
      timeLimit = 180;
      prompts = new String[]{"I've been very stressed out lately with all the assignments and tests coming up, and I haven't been able to focus.",
                             "I did really bad on this assignment, and the one before that, and the one before that... What should I do?!?",
                             "This student is on their phone...",
                             "I got back my English assignment, but I don't understand why exactly I lost marks in this part. Could you explain why and how I could improve next time?",
                             "I failed this test because of issues that I'm having at home, and now I feel like I'm a bad student seeing everyone else's high grades even though I try hard!",
                             "I'm really nervous about the presentation tomorrow. I freeze up when I talk in front of the class and feel like crying. What do I do? ",
                             "I don't have time to do my homework this week. Can you do it for me?",
                             "When Mr. Avetsark gave lessons, he used to do all sorts of fun activities as well. I'm going to miss those...",
                             "I don't get this concept at all! It's so frustrating. I don't want to do this anymore.",
                             "I know this is the day before the assignment is due, but I don't think I'll be able to finish this assignment. I had a lot of other school work during this week and underestimated how much time this assignment would take. ",};
      
      options = new String[][]{{"School is very important! You should always prioritize it, no matter how hard it gets.",
                                "This may be due to poor scheduling. If you plan better next time, you'll definitely do well!",
                                "Please put down your phone and pay attention! It's not good to be distracted during a lesson. You are attending school to learn, you can use your phone during lunch or at home.",
                                "Upon further review, you haven't made any mistakes. I will increase your mark. ",
                                "What questions exactly did you get wrong? I can help explain what you did incorrectly.",
                                "It's okay if you have public anxiety! You can record it instead to get around that.",
                                "Sorry, but no. Critical thinking skills are very important, and you're not using them at all when you ask an AI!",
                                "Well, the point of school is to learn above all else, so you'll probably focus and do better without them.",
                                "What concept exactly are you talking about? If you tell me, we can work together to figure it out at your own pace.",
                                "Sorry, but the schedule I made does not allow for that. You will have to submit the assignment on time or after the deadline, in which case I will comply with school regulations and deduct 5%."},

                               {"It’s ok to take breaks. If you want, you can talk about it with me.",
                                "The internet always has the answer. If you spend more time researching and less time hanging out with friends, you'll do much better.",
                                "You shouldn't be on your phone in class, now give me that!",
                                "It seems you didn't include enough detail in response. Please double check your answers after finishing next time. ",
                                "If you're having issues at home, it's completely understandable that you don't have as much time to devote to academics as others. Don't let a number define your worth.",
                                "You can search up presentation tips on the internet and just follow those.",
                                "Absolutely! Since you say you don't have time, just give it to me and I'll do it!",
                                "I'll be sure to include more of those! It's very important to interact meaningfully with the class.",
                                "You don't strictly have to do it. If it's too much for you, there's no shame in giving up.",
                                "Sure, that completely fine! Take as much time as you need to, as long as you submit good work. "},

                              {"I can do your homework for you - I’m rather good at that!",
                               "Talking to your friends to vent frustrations or ask for tips is always a great starting point.",
                               "What are you doing? Stop that immediately! Meet me after school for detention.",
                               "It seems that you didn't support your points with enough detail. Next time consider using more quotes/direct evidence when writing your response.",
                               "Don't worry, I've seen the other students' grades and I can tell you that they're not that much better than you!",
                               "Presentation skills are very important for life in general, both personal and professional! You can practice by presenting it to your friends or me.",
                               "Absolutely not! How lazy can you possibly be?",
                               "Sorry, but I don't know what exactly qualifies as a \"fun activity.\"",
                               "If you're frustrated, you can take deep breaths and listen to calming music.",
                               "Please inform me in advance next time. Being punctual and organized are extremely important skills in life. I will extend the deadline by a day."}};
                            
      
      responses=new String[][]{{"It's already as hard as it can get!  That didn't help...",
                                "I don't think sheduling is the issue at hand, and plus, that's only one part of an assignment!",
                                "I guess that's true...",
                                "Are you sure? I know I made some mistakes there. You're just going to give me marks like that?",
                                "That's only going to help me with this specific assignment! I'm still probably going to do bad on upcoming assignments.",
                                "I could do that, but then what do I do when I'll be older and I still have to present?",
                                "Hmph... I guess you're right...",
                                "But it's boring without them, so we actually learn worse!",
                                "Okay, that sounds like a good idea. I'll talk to you after school.",
                                "Aw man... I was staying up really late all week to finish work and I really didn't have time..."},

                              {"That's a good point... Thank you!",
                               "But Mr. Avetsark always told us that social interaction is important...",
                               "Hey!",
                               "That's not helpful at all! What do you mean by detail? What am I missing, what exactly do I have to look for when double checking?",
                               "You know what? You're right! Why should I stress out when it's out opf my control in the first place!",
                               "I've already done that, but it doesn't change the stress I feel when I'm at the front of the classroom.",
                               "Great! I did actually have time to do the work, but this is so much easier!",
                               "Well, that's reassuring!",
                               "But I don't want to be a quitter! I've tried so hard the whole year, and I'm not about to give up now!",
                               "Thank you! But you don't mind me having more time than other people to finish the same work?"},

                              {"Is that really the best way forward? Seems unethical to me.",
                               "Hmmmm... I could definitely try that!",
                               "Seriously? Just because I was on my phone?",
                               "Thanks for the helpful explanation!",
                               "That doesn't change the fact that I personally did bad!",
                               "You're right, I can go ask Amanda to help me!",
                               "Hey, that's not very nice! Just because I don't feel like doing trigonometry doesn't make me lazy!",
                               "How can you not know what counts as fun? If only it was still Mr. Avetsark teaching...",
                               "But that still doesnt help with the fact that I don't understand the concept!",
                               "Thank you for understanding! I can definitely finish it by tomorrow."}};
       
     
     
      studentNames = new String[]{"James", "Charlie", "John", "Alex", "Nikolas", "Ming", "Amanda", "Khloe", "Dalia", "Mary"};
   }

   /**
    * This method is responsible for drawing the level. It calls methods to detect userInput, draw the background, and then generates a random
    * question index, which determines which student the user has to move towards and answer. It draws an arrow over that student, which gets
    * highlighted when the user comes near the student. It then displays the dialogue if the user interacts with a student and there is time left to
    * play the level. If time runs out, it displays the exit menu instead.
    * @param g A Graphics variable passed to the method by default in order to draw on the JPanel component(which is then attached to the JFrame) using its
    *          methods.
    */
   public void paint(Graphics g)
   {
      int count=0;
      userInput(lock);
      drawBackground(g);
      while(questionIndex==-1)
      {
         questionIndex = (int)(Math.random()*10);
         if(questionsDone[questionIndex])
         {
            questionIndex = -1;
         }

         count++;

         if(count >= 10)
         {
            questionsDone = new boolean[]{false, false, false, false, false, false, false, false, false, false};
         }
      }
      questionsDone[questionIndex]=true;

      int[] xPoints = {95+110*(questionIndex%5), 95+110*(questionIndex%5)+25, 95+110*(questionIndex%5)+50};
      int[] yPoints = {80+150*(questionIndex/5), 80+150*(questionIndex/5)+30, 80+150*(questionIndex/5)};
      if(!firstTime) {
         if ((playerX >= (58 + 110 * (questionIndex % 5)) && playerX <= (58 + 110 * (questionIndex % 5) + 62)) && ((playerY >= (152 * (questionIndex / 5)) && (playerY <= ((110 + (150 * (questionIndex / 5))) + 56))))) {
            g.setColor(new Color(255, 206, 42, 200));
         } else {
            g.setColor(new Color(255, 206, 42, 130));
         }
         g.fillPolygon(xPoints, yPoints, 3);
      }


      if((lock && time<timeLimit && dialogueTrack>=1)){
         displayDialogue(g);
      }else if(time>=timeLimit){
         exitScreen(g);
      }
      lastAction = 0;
   }

   /**
    * This method is used to draw the dialogue when the user interacts with a student and control the flow of dialogue. It is also responsible
    * for increasing/decreasing points, as well as resetting questionIndex in order to prevent the same question being repeated again.
    * @param g A Graphics variable passed to the method by default in order to draw on the JPanel component(which is then attached to the JFrame) using its
    *          methods.
    */
   private void displayDialogue(Graphics g)
   {
      g.drawImage(dialogueBox, 10, 315, null);
      g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
      g.setColor(Color.WHITE);
      g.drawString(studentNames[questionIndex], 65, 420);


     if(dialogueTrack==1)
     {
         g.drawImage(studentImages[1][questionIndex], 60, 330, null);
         g.setColor(Color.WHITE);
         drawTextCentered(g, prompts[questionIndex], new Font(Font.SANS_SERIF, Font.PLAIN, 16),550, 350, 50, 10);
      } 
      else if(dialogueTrack==2)
      {
         g.drawImage(studentImages[1][questionIndex], 60, 330, null);
         if(currentMenu==null)
         {
            currentMenu = new Menu(new String[]{options[0][questionIndex], options[1][questionIndex], options[2][questionIndex]}, true);
         }
         drawMenu(g);
      } 
      else if(dialogueTrack==3)
      {
         if(menuChoice!=answerIndexes[questionIndex])
         {
            g.drawImage(studentImages[2][questionIndex], 60, 330, null);
         } 
         else 
         {
            g.drawImage(studentImages[1][questionIndex], 60, 330, null);
         }
         g.setColor(Color.WHITE);
         drawTextCentered(g, responses[menuChoice][questionIndex], new Font(Font.SANS_SERIF, Font.PLAIN, 15), 550, 350, 50, 10);

      }
      else if(dialogueTrack==4){
         if(menuChoice==answerIndexes[questionIndex])
         {
            points+=100;
         } 
         else 
         {
            points-=50;
         }
         lock=false;
         questionIndex=-1;
         dialogueTrack = 1;
         if(points<0)
         {
            points=0;
         }
      }
   }

   /**
    * This method draws the exit menu when the level is finished. It displays the final score of the user and shows them if they won or lost.
    * It also gives them the choice to continue or exit the level(return to the mainMenu) if they won, and restart or exit the level if they lost.
    * @param g A Graphics variable passed to the method by default in order to draw on the JPanel component(which is then attached to the JFrame) using its
    *          methods.
    */
   private void exitScreen(Graphics g) {
      g.setColor(new Color(0, 0, 0, 128));
      g.fillRect(0, 0, 800, 500);
      g.setColor(Color.WHITE);
      g.drawImage(menu, 0, 0, null);
      g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
      g.drawString("Time's Up!!", 30, 50);
      g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
      g.drawString("You got a score of " + points + "!", 300, 100);
      if(points>=500){
         drawTextCentered(g, "This is above the minimum score of 500, so you pass! Congratulations on beating the second level!", new Font(Font.SANS_SERIF, Font.PLAIN, 25), 500, 200, 0, 20);
         g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
         if(currentMenu==null){
            currentMenu = new Menu(new String[]{"Continue", "Exit Game"}, false);
         }
         drawMenu(g);
      } else {
         drawTextCentered(g, "This is below the minimum score of 500, so you fail. It is recommended to try again.", new Font(Font.SANS_SERIF, Font.PLAIN, 25), 500, 200, 0, 20);
         g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
         if(currentMenu==null){
            currentMenu = new Menu(new String[]{"Restart", "Exit Game"}, false);
         }
         drawMenu(g);
      }
   }

   /**
    * This method is responsible for drawing the background of the level. It draws the students, their chairs and desks, the classroom, the teacher,
    * and the teacher's desk. It is also responsible for drawing the header, which displays the score and time. If the firstTime variable is set
    * to true, it will draw the instructions screen on top of the background before the level starts.
    * @param g A Graphics variable passed to the method by default in order to draw on the JPanel component(which is then attached to the JFrame) using its
    *          methods.
    */
   private void drawBackground(Graphics g){
      g.setColor(Color.WHITE);
      g.fillRect(0,0,800,500);
      g.drawImage(backgroundClassroom, 0, 0, null);

      for(int i = 0;i < 5;i++)
      {
         g.drawImage(chair, 100+110*i, 128, null);
         g.drawImage(studentImages[0][i], 100+110*i, 110, null);
         g.drawImage(desk, 88+110*i, 150, null);
         g.drawImage(chair, 100+110*i, 278, null);
         g.drawImage(studentImages[0][i+5], 100+110*i, 260, null);
         g.drawImage(desk, 88+110*i, 300, null);
      }
      g.drawImage(mrAvetsark, 550, 220, null);
      g.drawImage(mrAvetsarkDesk, 550, 220, null);
      g.setColor(Color.BLACK);
      g.drawImage(aiden, playerX, playerY, null);



      g.drawImage(header, 520, 0, null);
      g.setColor(Color.WHITE);
      g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
      g.drawString("Score: "+ points, 570, 30);
      g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
      g.drawString("Time Left: ", 670, 30);
      g.drawString(+ (timeLimit-time) + "s", 710, 60);


      if(firstTime && lastAction!=5) {
         g.drawImage(menu, 0, 0, null);
         g.setColor(Color.WHITE);
         g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
         g.drawString("Instructions", 40, 50);
         g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
         g.drawString("The goal of this game is to interact with as many students as possible in 3 min. ", 150, 100);
         g.drawString("You have to practice the skills of empathy and giving good feedback in order to", 145, 130);
         g.drawString("interact with students. When you interact with a student, you will be given a", 140, 160);
         g.drawString("situation/prompt. You will then be given dialogue options, and you will have", 135, 190);
         g.drawString("to pick the one that meets the criteria of empathy and good feedback the best. ", 130, 220);
         g.drawString("100 points will be given for a correct answer and 50 points will be deducted", 125, 250);
         g.drawString("for a wrong answer. Use W, A, S, D to move AI-den towards a student highlighted", 120, 280);
         g.drawString("by the arrow above them and press space to interact with the students. Press", 115, 310);
         g.drawString("the spacebar to exit this screen and start the game!", 110, 340);
      } else if(firstTime){
         firstTime = false;
      }
   }

   /**
    * This method is responsible for getting user input, allowing the player to control the position of the
    * AI-den sprite using their keyboard and interqact with menus. It is called in paint(), to allow it to change the position of the AI-den
    * sprite before drawing it. It is also responsible for collision detection, so that it freezes movement in the
    * direction of the movement that collides with one of the objects on the screen.
    * @param lock It is used to lock userInput for movement if user has interacted with a student and is reading dialogue.
    */
   private void userInput(boolean lock){
      boolean collisionXPos = false;
      boolean collisionXNeg = false;
      boolean collisionYPos = false;
      boolean collisionYNeg = false;

      for(int i=0; i<5; i++){
         if (playerX+45>=(100+110*i) && playerX<=100+110*i+40 && ((playerY+82>=105 && playerY<=105+30) || (playerY+82>=260 && playerY<=260+30))){
            if(playerX<=100+110*i && playerX+45<=100+110*i){
               collisionXPos = true;
            } else if(playerX+45>=100+110*i+40 && playerX>=100+110*i+40){
               collisionXNeg = true;
            } else if((playerY<=105)){
               collisionYPos = true;
            } else if(playerY<=260 && playerY>105+30){
               collisionYPos = true;
            } else {
               collisionYNeg = true;
            }
            break;
         }
      }
      if (playerX+45>=550 && playerX<=670 && playerY+82>=260 && playerY<=430){
         if(playerX<=550 && playerX+45<=550){
            collisionXPos = true;
         } else if(playerX+45>=660 && playerX>=660){
            collisionXNeg = true;
         } else if((playerY<=260)){
            collisionYPos = true;
         } else {
            collisionYNeg = true;
         }
      }
      if(playerX+45>=660 && playerX<=740 && playerY+82>=220 && playerY<=420){
         if(playerX<=660 && playerX+45<=660){
            collisionXPos = true;
         } else if(playerX+45>=740 && playerX>=740){
            collisionXNeg = true;
         } else if((playerY<=220)){
            collisionYPos = true;
         } else {
            collisionYNeg = true;
         }
      }

      if(lastAction == 1)
      {
         if(currentMenu == null && playerY>=0 && !collisionYNeg && !lock)
         {
            playerY -= 5;
         } else if(currentMenu==null && lock){
            dialogueTrack++;
         }
         else if(currentMenu!=null && currentMenu.getVertical())
         {
            currentMenu.setChoice(lastAction);
         }
      }
      else if(lastAction == 2)
      {
         if(currentMenu == null && playerX >= 25 && !collisionXNeg && !lock)
         {
            playerX -= 5;
         } else if(currentMenu==null && lock){
            dialogueTrack++;
         } else if(currentMenu!=null && !currentMenu.getVertical())
         {
            currentMenu.setChoice(lastAction);
         }

      }
      else if(lastAction == 3)
      {
         if(currentMenu == null && playerY<=330 && !collisionYPos && !lock)
         {
            playerY += 5;
         }
         else if(currentMenu==null && lock)
         {
            dialogueTrack++;
         }
         else if(currentMenu!=null && currentMenu.getVertical())
         {
            currentMenu.setChoice(lastAction);
         }
      }
      else if(lastAction == 4)
      {
         if(currentMenu==null && playerX<=700 && !collisionXPos && !lock)
         {
            playerX += 5;
         } else if(currentMenu==null && lock){
            dialogueTrack++;
         } else if(currentMenu!=null && !currentMenu.getVertical())
         {
            currentMenu.setChoice(lastAction);
         }
      }
      else if(lastAction == 5)
      {
         boolean collisionCharacter = (((playerX >= (58 + (110 * (questionIndex % 5)))) && (playerX <= (58 + (110 * (questionIndex % 5)) + 62)))) && ((playerY >= (152*(questionIndex/5)) && (playerY <= ((110 + (150 * (questionIndex / 5))) + 56))));
         if((collisionCharacter && !this.lock))
         {
            this.lock=true;
         }
         else if(currentMenu==null && lock)
         {
            dialogueTrack++;
         }
         else if(currentMenu!=null)
         {
            menuChoice = currentMenu.getChoice();
            currentMenu = null;
            if(lock){
               dialogueTrack++;
            }
         }
      }
   }

   /**
    * This method is used to draw the menus in this level, including the exit menu and the dialogue menu. It also takes into account whether the menu is vertical or horizontal.
    * @param g A Graphics variable passed to the method by default in order to draw on the JPanel component(which is then attached to the JFrame) using its
    *          methods.
    */
   private void drawMenu(Graphics g)
   {
      String[] choices = currentMenu.getOptions();
      for(int i = 0; i<choices.length; i++)
      {
         if(i == currentMenu.getChoice())
         {
            g.setColor(new Color(255,206,42));
         }
         else
         {
            g.setColor(Color.WHITE);
         }

         if(!currentMenu.getVertical())
         {
            g.drawString(((i == currentMenu.getChoice())?"> ":"* ") + choices[i],100 + (choices.length<=2?300:200)*i, 400);
         }
         else
         {

            if(questionIndex==9) {
               drawTextCentered(g, choices[i], new Font(Font.SANS_SERIF, Font.PLAIN, 12), 570, 340 + 40 * i + (i!=0?(10*(choices[i-1].length()/100)):0), 25, 5);
            } else {
               drawTextCentered(g, choices[i], new Font(Font.SANS_SERIF, Font.PLAIN, 13), 570, 340 + (45 * i) + (i!=0?(10 * (choices[i - 1].length() / 93)):0), 25, 5);
            }
         }
      }
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
    * This method draws a block of text on the screen while wrapping to prevent text overflow.
    * @param g A Graphics variable passed to the method by default in order to draw on the JPanel component(which is then attached to the JFrame) using its
    *                 methods.
    * @param s The String to be drawn on the screen.
    * @param f The specific font that the aforementioned String uses.
    * @param width The width of the area that the String will be drawn on.
    * @param y The y-ccordinate at which the block of text should start to be drawn.
    * @param xPadding The amount of padding on the sides of the text.
    * @param yPadding The amount of padding between lines of text.
    */
   private void drawTextCentered(Graphics g, String s, Font f, int width, int y, int xPadding, int yPadding) {
      String[] textSplit = s.split(" ");
      String text = "";
      int lines = 0;


      for (String i : textSplit) {
         g.setFont(f);
         if (getStringDetails(g, text + i, f).getWidth() > width) {
            g.drawString(text, 190+xPadding / 2, (int) (getStringDetails(g, text, f).getHeight() + yPadding) * lines + y);
            text = i + " ";
            lines++;
         } else {
            text += i + " ";
         }
      }

      g.drawString(text, 190+xPadding / 2, (int) (getStringDetails(g, text, f).getHeight() + yPadding) * lines + y);
   }

   /**
    * This method is a getter method so that the Main class can access the user's choice on the final menu. It determines whether to go to the main menu or level 3.
    * @return The value of continueNext, which determines the flow of the game in the Main class.
    */
   public int getContinueNext(){
      return continueNext;
   }

   /**
    * This method is used to run the Level2 class from the Main class. It displays Level 2 and allows the user to play the game
    * for 1 min 30 sec before ending. It keeps repainting the screen until the time exceeds the limit using the repaint() method
    * (a part of the Applet class higher up in the hierarchy). When the method is first called, it has a separate while loop to
    * call repaint, which is used only display the instructions for the level while no updating the time. It then has another while
    * loop to allow the user to play the game. At the end, it has a final while loop to keep displaying the exit screen until
    * the user chooses an option from the menu.  If the user chooses to restart the level, it calls itself again and resets
    * all instance variables to their initial values.
    */
   public void play()
   {

      while(firstTime)
      {
         lock = true;
         dialogueTrack = 0;
         repaint();
      }

      lock = false;
      dialogueTrack = 1;
      long startTime = (long)System.currentTimeMillis();
      while((time = (long)timer(startTime)) < timeLimit)
      {
         repaint();
      }

      firstTime = true;
      menuChoice = -1;
      currentMenu = null;
      while(menuChoice == -1){
         if(firstTime){
            try {
               Thread.sleep(1000);
            } catch (InterruptedException e) {
               throw new RuntimeException(e);
            }
            firstTime = false;
         }
         repaint();
         if(menuChoice==1) {
            continueNext = 5;
         } else if(points<500 && menuChoice==0){
            currentMenu = null;
            points = 0;
            questionsDone = new boolean[]{false, false, false, false, false, false, false, false, false, false};
            menuChoice = -1;
            firstTime = true;
            playerX=25;
            playerY=25;
            play();
         }
      }
   }
}