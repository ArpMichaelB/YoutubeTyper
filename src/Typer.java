
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.VBox;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Michael
 */
public class Typer extends Application
{
    final int TO_TITLE = 19;
    final int TO_DESCRIPTION = 1;
    final int TO_TAGS = 1;//amount of tabs to move from the current item to the item listed
    String getNextEndSignal = "1234567890CompletedWork0987654321";
    int menuSize = 550;
    int menuSizeTwo = 550;//size of the initial menu
    int errorSize = 375;
    int errorSizeTwo = 60;
    TextField titles = new TextField(getNextEndSignal);//what are the titles? (split by +)
    TextField descriptions = new TextField(getNextEndSignal);//what are the descriptions? (split by +)
    TextField tags = new TextField(getNextEndSignal);//what are the sets of tags? (split by +)
    //text fields are global because I need their inside text and don't want to pass it around all the time
    //menu size is global because the error stages need it too I'm pretty sure
    //constants are global in case I need them elsewhere
    //getNextEndSignal could probably be local to getNext but I'm leaving it where it is since it's useful in the UI anyways
    @Override
    public void start(Stage primaryStage)
    {
        VBox holder = new VBox();
        VBox inputs = new VBox();
        HBox inVBox = new HBox();
        Text message = new Text("Delimit items with +, leave what's in the field on open there");
        Button btStart = new Button("Get to work!");
        btStart.setOnAction(new activateHandler());
        titles.setPrefColumnCount(30);
        titles.setPrefHeight(100);
        descriptions.setPrefColumnCount(30);
        descriptions.setPrefHeight(100);
        tags.setPrefColumnCount(30);
        tags.setPrefHeight(100);//set the sizes of the text boxes so they look nicer
        inputs.getChildren().addAll(titles,descriptions,tags);//put the text fields into their set
        inVBox.getChildren().addAll(inputs,btStart);//put the button and set of text fields next to each other
        holder.getChildren().addAll(message,inVBox);//then put the text and the group from earlier in the window
        Scene primscene = new Scene(holder,menuSize,menuSizeTwo);
        primaryStage.setScene(primscene);//make the scene with all the stuff in it and set it to the main window
        primaryStage.show();//show the main window
    }
    class activateHandler implements EventHandler<ActionEvent>
    {

        @Override
        public void handle(ActionEvent event) {
            int howlongnum = 0;
            Robot bot;
            try 
            {
                //TODO: Have it jiggle based on the progress bar at the top of the screen
                bot = new Robot();
                bot.setAutoDelay(50);//wait .05s between all actions
                bot.delay(10000);//wait ten seconds so I can get over to chrome
                bot.keyPress(KeyEvent.VK_F11);//fullscreen chrome
                String[][] items = parseItems();
                int counter = 0;
                while(!(items[0][counter].contains(getNextEndSignal)) &&!(items[1][counter].contains(getNextEndSignal)) && !(items[2][counter].contains(getNextEndSignal)))
                {
                    fillThingsIn(bot,items[0][counter],items[1][counter],items[2][counter],counter);
                    counter++;
                }
            }
            catch (AWTException ex) 
            {
                Stage ErrorStage = new Stage();
                HBox inside = new HBox();
                Scene ErrorScene = new Scene(inside,errorSize,errorSizeTwo);
                Text error = new Text("The Bot Is A N G E R");
                inside.getChildren().add(error);
                ErrorStage.setScene(ErrorScene);
                ErrorStage.show();
                //above shows a window that says the bot is anger
            }
        }
    }
    /**
     * Types a given string with the given bot
     * @param phrase the phrase it's typing
     * @param robot the bot that it uses to type the phrase
     */
    public void typePhrase(Robot robot, String phrase) 
    {
        //Note: Certain punctuations may make keyPress throw an error, keep an eye out for that
        
        char c = ' ';
        try
        {
        for (int i = 0; i < phrase.length(); i++) {//for the length of the phrase
            c = phrase.charAt(i);//take in a character
            if (Character.isUpperCase(c)) 
            {
                robot.keyPress(KeyEvent.VK_SHIFT);
            }//if it's uppercase, press shift
            if(c == ':')
            {
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(KeyEvent.VK_SEMICOLON);
                robot.keyRelease(KeyEvent.VK_SHIFT);
                robot.keyRelease(KeyEvent.VK_SEMICOLON);
            }
            else if(c=='’')
            {
                robot.keyPress(KeyEvent.VK_QUOTE);
                robot.keyRelease(KeyEvent.VK_QUOTE);
            }
            else if(c=='!')
            {
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(KeyEvent.VK_1);
                robot.keyRelease(KeyEvent.VK_SHIFT);
                robot.keyRelease(KeyEvent.VK_1);
            }
            else
            {
                robot.keyPress(KeyEvent.getExtendedKeyCodeForChar(c));
                robot.keyRelease(KeyEvent.getExtendedKeyCodeForChar(c));//press and release the key
            }
            if (Character.isUpperCase(c)) 
            {
                robot.keyRelease(KeyEvent.VK_SHIFT);
            }//release shift if it's held
        }
        }
        catch(IllegalArgumentException ex)
        {
            Stage ErrorStage = new Stage();
            HBox inside = new HBox();
            Scene ErrorScene = new Scene(inside, errorSize, errorSizeTwo);
            Text error = new Text("Oh dear, looks like the robot doesn't quite get how to type " + c + ", email glaciernester@gmail.com and tell him what to teach the bot.");
            inside.getChildren().add(error);
            ErrorStage.setScene(ErrorScene);
            ErrorStage.show();
            //above shows a window that tells the user which key caused a problem
        }
    }
    /**
     * Hits tab the amount of times it's told to
     * @param bot The bot it's using to press buttons
     * @param amt The amount of times it's told
     */
    public void tab(Robot bot, int amt)
    {
        for(int i = 1; i<=amt;i++)
        {
            bot.keyPress(KeyEvent.VK_TAB);
            bot.keyRelease(KeyEvent.VK_TAB);
        }
    }
    /**
     * Runs through the process of filling in the video details
     * @param bot The Robot that is doing the movement
     * @param title The title of the video
     * @param description the description of the video
     * @param tags the tags on the video
     * @param counter which cycle are we on? (i.e. how many opening tabs do I need?)
     */
    public void fillThingsIn(Robot bot, String title, String description, String tags, int counter)
    {
        if(counter==0)
            tab(bot,12);
        else if (counter==1)
        {
            tab(bot,18);
        }
        else
        {
            tab(bot,TO_TITLE);
        }
        bot.delay(1000);//1000 is one second
        typePhrase(bot,title);
        tab(bot,TO_DESCRIPTION);
        typePhrase(bot,description);
        tab(bot,TO_TAGS);
        typePhrase(bot,tags);
    }
    public String[][] parseItems()
    {
        String[][] ret = new String[3][30];
        String[] temp = titles.getText().split("[+]");//make an array, each item being the given box's thing
        System.arraycopy(temp, 0, ret[0], 0, temp.length); //fill the first array with Titles
        temp = descriptions.getText().split("[+]");
        System.arraycopy(temp, 0, ret[1], 0, temp.length); //fill the second array with Descriptions
        temp = tags.getText().split("[+]");
        System.arraycopy(temp, 0, ret[2], 0, temp.length); //fill the third array with tags
        return ret;
    }
    public void jiggle(Robot bot)
    {
        bot.keyPress(KeyEvent.VK_ESCAPE);
        bot.keyRelease(KeyEvent.VK_ESCAPE);
        bot.keyPress(KeyEvent.VK_HOME);
        bot.keyRelease(KeyEvent.VK_HOME);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
