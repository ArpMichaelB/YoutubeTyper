package com.glacier.main;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.glacier.handlers.activateHandler;
import com.glacier.util.Utilities;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


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
    @Override
    public void start(Stage primaryStage)
    {
        VBox holder = new VBox();
        VBox inputs = new VBox();
        HBox inVBox = new HBox();
        Text message = new Text("Delimit items with +, make sure to have a + at the end");
        Button btStart = new Button("Get to work!");
        TextField titles = new TextField();//what are the titles? (split by +)
        TextField descriptions = new TextField();//what are the descriptions? (split by +)
        TextField tags = new TextField();//what are the sets of tags? (split by +)
        btStart.pressedProperty().addListener(
			new ChangeListener<Boolean>(){
				@Override
				public void changed(ObservableValue<? extends Boolean> obs, Boolean wasArmed, Boolean isArmed) {
					System.gc();
					btStart.setOnAction(new activateHandler(titles.getText(),descriptions.getText(),tags.getText()));
				}
			}
		);//when the start button is pressed, update the activateHandler to have the desired titles, descriptions, and text
        btStart.setOnAction(new activateHandler(titles.getText(),descriptions.getText(),tags.getText()));
        titles.setPrefColumnCount(30);
        titles.setPrefHeight(100);
        descriptions.setPrefColumnCount(30);
        descriptions.setPrefHeight(100);
        tags.setPrefColumnCount(30);
        tags.setPrefHeight(100);//set the sizes of the text boxes so they look nicer
        inputs.getChildren().addAll(titles,descriptions,tags);//put the text fields into their set
        inVBox.getChildren().addAll(inputs,btStart);//put the button and set of text fields next to each other
        holder.getChildren().addAll(message,inVBox);//then put the text and the group from earlier in the window
        Scene primscene = new Scene(holder,Utilities.MENU_SIZE,Utilities.MENU_SIZE_TWO);
        primaryStage.setScene(primscene);//make the scene with all the stuff in it and set it to the main window
        primaryStage.show();//show the main window
    }
    
    public void jiggle(Robot bot)
    {
    	//TODO: Make this do more than just hit escape then home
    	//look into changing the sleep settings from the command line rather than relying on seeing the progress bar with the robot library
        bot.keyPress(KeyEvent.VK_ESCAPE);
        bot.keyRelease(KeyEvent.VK_ESCAPE);
        bot.keyPress(KeyEvent.VK_HOME);
        bot.keyRelease(KeyEvent.VK_HOME);
    }
    public static void main(String[] args) throws IOException {
    	File logFolder = new File("C:\\Glacier Nester\\logs");
    	File file = null;
    	if(!logFolder.exists())
    	{
    		logFolder.setWritable(true);
    		if(logFolder.mkdirs())
    		{
    			file = new File("C:\\Glacier Nester\\logs\\YoutubeTyper.log");
    		}
    	}
    	else
    	{
    		file = new File("C:\\Glacier Nester\\logs\\YoutubeTyper.log");
    	}
    	FileOutputStream fos = new FileOutputStream(file);
		PrintStream ps = new PrintStream(fos);
		System.setErr(ps);
		System.err.println("Started MouseJiggler at " + DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss").format(LocalDateTime.now()));
    	launch(args);
    }
}
