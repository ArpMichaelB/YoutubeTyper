package com.glacier.handlers;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.glacier.util.BotActions;
import com.glacier.util.Utilities;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class activateHandler implements EventHandler<ActionEvent>
{
	private String titles;
	private String descriptions;
	private String tags;
	
	public activateHandler(String titles,String descriptions,String tags)
	{
		this.titles = titles;
		this.descriptions = descriptions;
		this.tags = tags;
	}

    @Override
    public void handle(ActionEvent event) {
        Robot bot;
        try 
        {    
            bot = new Robot();
            bot.setAutoDelay(50);//wait .05s between all actions
            bot.delay(Utilities.INITIAL_WAIT);//wait ten seconds so I can get over to chrome
            bot.keyPress(KeyEvent.VK_F11);//fullscreen chrome
            String[][] items = Utilities.parseItems(titles,descriptions,tags);
            int counter = 0;
            
            while(items[0].length>counter && items[1].length>counter && items[2].length>counter)
            {
                BotActions.fillThingsIn(bot,items[0][counter],items[1][counter],items[2][counter],counter);
                counter++;
            }
        }
        catch (AWTException ex) 
        {
        	System.err.println("AWT Exception at " + DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss").format(LocalDateTime.now()) + " with trace");
        	ex.printStackTrace();
        	Stage ErrorStage = new Stage();
            HBox inside = new HBox();
            Scene ErrorScene = new Scene(inside,Utilities.ERROR_SIZE,Utilities.ERROR_SIZE_TWO);
            Text error = new Text("There was an error in the Robot. Send YoutubeTyperError.log\n (check for a folder called Glacier Nester)\n to glaciernester@gmail.com");
            inside.getChildren().add(error);
            ErrorStage.setScene(ErrorScene);
            ErrorStage.show();
            //above shows a window that says the bot is anger
        }
    }
}