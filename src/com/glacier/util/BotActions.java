package com.glacier.util;

import java.awt.Robot;
import java.awt.event.KeyEvent;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BotActions {
	/**
     * Runs through the process of filling in the video details
     * @param bot The Robot that is doing the movement
     * @param title The title of the video
     * @param description the description of the video
     * @param tags the tags on the video
     * @param counter which cycle are we on? (i.e. how many opening tabs do I need?)
     */
    public static void fillThingsIn(Robot bot, String title, String description, String tags, int counter)
    {
        if(counter==0)
            tab(bot,Utilities.CYCLE_0_TO_TITLE);
        else if(counter==1)
        	tab(bot,Utilities.CYCLE_1_TO_TITLE);
        else
        {
            tab(bot,Utilities.TO_TITLE);
        }
        bot.delay(1000);//1000 is one second
        typePhrase(bot,title);
        tab(bot,Utilities.TO_DESCRIPTION);
        typePhrase(bot,description);
        tab(bot,Utilities.TO_TAGS);
        typePhrase(bot,tags);
    }
    /**
     * Hits tab the amount of times it's told to
     * @param bot The bot it's using to press buttons
     * @param amt The amount of times it's told
     */
    public static void tab(Robot bot, int amt)
    {
        for(int i = 1; i<=amt;i++)
        {
            bot.keyPress(KeyEvent.VK_TAB);
            bot.keyRelease(KeyEvent.VK_TAB);
        }
    }
    /**
     * Types a given string with the given bot
     * @param phrase the phrase it's typing
     * @param robot the bot that it uses to type the phrase
     */
    public static void typePhrase(Robot robot, String phrase) 
    {
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
            Scene ErrorScene = new Scene(inside, Utilities.ERROR_SIZE, Utilities.ERROR_SIZE_TWO);
            Text error = new Text("Oh dear, looks like the robot doesn't quite get how to type " + c + ", email glaciernester@gmail.com and tell him what to teach the bot.");
            inside.getChildren().add(error);
            ErrorStage.setScene(ErrorScene);
            ErrorStage.show();
            //above shows a window that tells the user which key caused a problem
        }
    }
}
