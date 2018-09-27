package com.glacier.main;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import com.glacier.util.BotActions;
import com.glacier.util.Utilities;

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
    TextField titles = new TextField(Utilities.getNextEndSignal);//what are the titles? (split by +)
    TextField descriptions = new TextField(Utilities.getNextEndSignal);//what are the descriptions? (split by +)
    TextField tags = new TextField(Utilities.getNextEndSignal);//what are the sets of tags? (split by +)
    //text fields are global because I need their inside text and don't want to pass it around all the time
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
        Scene primscene = new Scene(holder,Utilities.menuSize,Utilities.menuSizeTwo);
        primaryStage.setScene(primscene);//make the scene with all the stuff in it and set it to the main window
        primaryStage.show();//show the main window
    }
    class activateHandler implements EventHandler<ActionEvent>
    {

        @Override
        public void handle(ActionEvent event) {
            Robot bot;
            try 
            {    
                bot = new Robot();
                bot.setAutoDelay(50);//wait .05s between all actions
                bot.delay(10000);//wait ten seconds so I can get over to chrome
                bot.keyPress(KeyEvent.VK_F11);//fullscreen chrome
                String[][] items = Utilities.parseItems(titles.getText(),descriptions.getText(),tags.getText());
                int counter = 0;
                while(!(items[0][counter].contains(Utilities.getNextEndSignal)) &&!(items[1][counter].contains(Utilities.getNextEndSignal)) && !(items[2][counter].contains(Utilities.getNextEndSignal)))
                {
                    BotActions.fillThingsIn(bot,items[0][counter],items[1][counter],items[2][counter],counter);
                    counter++;
                }
            }
            catch (AWTException ex) 
            {
                Stage ErrorStage = new Stage();
                HBox inside = new HBox();
                Scene ErrorScene = new Scene(inside,Utilities.errorSize,Utilities.errorSizeTwo);
                Text error = new Text("The Bot Is A N G E R");
                inside.getChildren().add(error);
                ErrorStage.setScene(ErrorScene);
                ErrorStage.show();
                //above shows a window that says the bot is anger
            }
        }
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
