package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import Cutscene.Cutscene;
import Cutscene.DialogCutscene;
import Cutscene.Introduction;
import Interfaces.Subscribable;
import Models.Entity;
import Models.Map.Floor1Map;
import Models.Map.Floor2Map;
import Models.Map.Floor3Map;
import Models.Map.Floor4Map;
import Models.Map.Floor5Map;
import Models.Map.Floor6Map;
import Models.Map.Floor7Map;
import Models.Map.Map;
import Models.Players.HumanPlayer;
import Models.Players.PlayableCharacter;
import SpriteSheet.SpriteSheet;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Window;

public class StoryController implements Initializable, Subscribable<PlayableCharacter> {
	
	@FXML
	private Canvas myCanvas;
	private GameController gc;
	private PlayableCharacter player1;
	private Floor1Map[] levels;
	private int currentLevel;
	private int lives = 5;

	public StoryController(){
		generateLevels();		
	}	
	
	public void StoryController1(){
		gc = new GameController(myCanvas, true);
		
		Image img = SpriteSheet.getBorderedBlock(30, 30, Color.WHITE, 3);
		player1 = new HumanPlayer(img, 0, 0);
		gc.attach(this);
		gc.addEntity(player1.getDisplayableEntities());
		gc.addPlayer(player1);
		gc.setFocus(player1);
		gc.addEntity(levels[0].getMapObjects().toArray(new Entity[0]));
	}
	
	public void generateLevels(){
		levels = new Floor1Map[7];

		levels[0] = new Floor1Map(this, 500, 500);

		levels[1] = new Floor2Map(this, 500, 500);
		levels[2] = new Floor3Map(this, 500, 500);
		levels[3] = new Floor4Map(this, 500, 500);
		levels[4] = new Floor5Map(this, 500, 500);
		levels[5] = new Floor6Map(this, 500, 500);
		levels[6] = new Floor7Map(this, 500, 500);
	}
	
	public void changeLevel(int i){
		System.out.println(i);
		stop();
		gc.removeEntity(levels[currentLevel].getMapObjects().toArray(new Entity[0]));
		int previousLevel = currentLevel;
		currentLevel = i;
		gc.addEntity(levels[currentLevel].getMapObjects().toArray(new Entity[0]));
		// Went up
		if(i - 1 == previousLevel){
			player1.setXPos(levels[currentLevel].getExit().getXPos());
			player1.setYPos(levels[currentLevel].getExit().getYPos());
		} 
		// Went down
		else if(i + 1 == previousLevel){
			player1.setXPos(levels[currentLevel].getEntrance().getXPos());
			player1.setYPos(levels[currentLevel].getEntrance().getYPos());
		}
		// Used Elevelator
		else {
			player1.setXPos(levels[currentLevel].getExit().getXPos());
			player1.setYPos(levels[currentLevel].getExit().getYPos());
		}
		start();
	}
	
	// Cutscenes need to be instantiated with the StoryController
	// Because we can not subscribe to them
	// Because java... pretty sure c++ can do that
	public void startCutscene(Cutscene c){
		stop();
		c.start();
	}
	
	public void startGame(){
//		
//		DialogCutscene s = new DialogCutscene(this, .5, "Hello", "i am a potato", "Where is the ranch", 
//				"Is the potato a dog or the dog a potato. I do not know for I am a dolphin among fish and a "
//				+ "fish amoung dolhins. Who am I?");
		startCutscene(new Introduction(this));
	}
	
	public void start(){
		gc.start();
	}
	
	public void stop(){
		gc.stop();
	}
	
	public Canvas getCanvas(){
		return myCanvas;
	}
	
	public int getCurrentLevel(){
		return currentLevel;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Resizes the Canvas to it's Stage Width and Height...
		myCanvas.sceneProperty().addListener(new ChangeListener<Scene>(){
		
			// This is the actual method when ^ this action happens
			@Override
			public void changed(ObservableValue<? extends Scene> observable, Scene oldValue, Scene newValue) {
				if(newValue != null){
					newValue.windowProperty().addListener(new ChangeListener<Window>(){
						@Override
						
						// This is the actual method when ^ this action happens
						public void changed(ObservableValue<? extends Window> observable, Window oldValue,
								Window newValue) {
							if(newValue != null){
								myCanvas.widthProperty().bind(newValue.widthProperty());
								myCanvas.heightProperty().bind(newValue.heightProperty());
								
								StoryController1();
								// Create Map
								
							}
						}
					});
				}
			}
		});
		
		// Neccesary
		myCanvas.setFocusTraversable(true);
		// KeyEvent to record input while playing
		myCanvas.setOnKeyPressed((e) -> InputHandler.keyPress(e));
		myCanvas.setOnKeyReleased((e) -> InputHandler.keyRelease(e));
	}

	@Override
	public void update(PlayableCharacter value) {
		// we should have a menu like pressing escape or something
		// woud it be here?...
		if(!value.isAlive()){
			System.out.println("Live lost"); 
			if(--lives <= 0){
				// displayLoss();
				gc.stop();
			} else {
				// beginning of level?
				player1.setXPos(0);
				player1.setYPos(0);
			}
		}
	}

	// Cutscenes will call this method to tell the Controller the Cutscene is over
	public void update(Boolean b) {
		if(b){ start(); }
	}
}