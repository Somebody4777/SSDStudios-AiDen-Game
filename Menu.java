/**
 * <p>
 * This class is a general template for menus for all levels, including the main menu. 
 * </p>
 *
 * <p>
 * Version 1 (15 min, by Dorian Groza, on 2024/05/19):
 * Created basic methods and variables necessary for all menus, such as the choice selected and options in the menu,
 * along with getter and setter methods for both of them.
 * </p>
 *
 * <p>
 * Version 2 (15 min, by Dorian Groza, on 2024/05/31):
 * Implemented a boolean variable that allows for the creation of a vertical menu. Allows for user to use 'W" and 'S' instead of 'A' and 'D' to navigate
 * the menu, in the case that vertical is set to true.
 * </p>
 *
 * <p>
 * Version 3 (15 min, by Soham Malla, on 2024/05/31):
 * Changed setChoice() method to allow for looping through a menu. If user is on the last option and tries to go to the
 * next one, it will reset to the first option. If the user is on the first option and wants to go the previous option,
 * they will go to the last one.
 * </p>
 *
 * <h2>Course Info:</h2>
 * Course: ICS4U0, Teacher: Ms. Krasteva
 * 
 * @author Dorian Groza and Soham Malla
 * @version 2.0
 */
public class Menu
{
   /** Stores the choice from the menu that the user is currently on. */
   private int choice = 0;
   /** Stores all options on the menu. */
   private String[] options;
   /**
    * Stores the boolean value that determines if the menu is vertical. If the value is set to true, the menu is vertical, otherwise
    * the menu is horizontal.
    */
   private boolean vertical;
   
   /**
    * This constructor sets the menu options of this class and whether it is vertical or not to the parameters passed in when making an object of this
    * class.
    *
    * @param o A String array that represents the options that the menu will have.
    * @param orient The orientation of the menu, representing whether it is vertical or horizontal.
    */
   public Menu(String[] o, boolean orient)
   {
      options = o;
      vertical = orient;
   }
   
   /**
    * A getter method that returns the options String[] that stores the menu options. 
    * @return The options on the menu to choose from.
    */
   public String[] getOptions()
   {
      return options;
   }
   
   /**
    * A getter method that returns the choice int that stores the menu option currently selected. 
    * @return The currently selected choice.
    */
   public int getChoice()
   {
      return choice;
   }

   /**
    * A getter method that returns whether the menu is vertical or not. 
    * @return Whether the menu is vertical or not.
    */
   public boolean getVertical()
   {
      return vertical;
   }

   /**
    * A mutator method that decrements or increments the choice variable depending on the value of the key pressed. It will also take into account whether
    * the menu is vertical, as well as looping around if the user reaches either end of the menu and tries to go further. 
    * 
    * @param la The lastAction variable, representing the key pressed by the user.
    */
   public void setChoice(int la)
   {
      if (((la == 2 && !vertical) || (la == 1 && vertical)) && choice > 0)
      {
         choice--;
      }
      else if (((la == 4 && !vertical) || (la == 3 && vertical)) && choice < options.length - 1)
      {
         choice++;
      }
      else if (((la == 4 && !vertical) || (la == 3 && vertical)) && choice == options.length - 1)
      {
         choice = 0;
      }
      else if (((la == 2 && !vertical) || (la == 1 && vertical)) && choice == 0)
      {
         choice = options.length - 1;
      }
   }
}