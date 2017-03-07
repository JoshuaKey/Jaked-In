package Models.NPCs;

import java.util.ArrayList;

import Controller.InputHandler;
import Controller.StoryController;
import Cutscene.Cutscene;
import Cutscene.DialogCutscene;
import Interfaces.Interactable;
import Models.Collision;
import Models.Entity;
import Models.Players.PlayableCharacter;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class AngryNPC extends PlayableCharacter implements Interactable {
	
//	private String dialogue = "You killed my Family!";
	private StoryController controller;
	private PlayableCharacter enemy;
	private boolean interacted;

	public AngryNPC(Image i, StoryController controller, int x, int y) {
		super(i, x, y);
		this.controller = controller;
		setTag(getTag() + "-EnemyNPC");
	}
	
//	public String conversation(PlayableCharacter c) {
//		return dialogue;
//	}
	
	@Override
	public void interact(PlayableCharacter p) {
		Cutscene c = new DialogCutscene(controller, .5, "You killed my Family!");
		controller.startCutscene(c);
		
		setEnemy(p);
		interacted = true;
	}

	@Override
	public void hasCollided(Collision c) {
		Entity collider;
		if(c.collidingEntity == this){
			collider = c.collidedEntity;
		} else { 
			collider = c.collidingEntity;
		}
		System.out.println(collider);
		System.out.println(collider.getTag());
		String[] tagElements = collider.getTag().split("-");
		switch(tagElements[0]){
		case "Human":
			if(InputHandler.keyInputContains(KeyCode.F)){
				interact((PlayableCharacter)collider);
			}
			break;
		}
	}

	@Override
	public void update(ArrayList<Entity> entities) {
		if(interacted){
			
		}
	}
	
	public void setEnemy(PlayableCharacter c){
		enemy = c;
	}

}
