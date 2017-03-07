package Models.Map;

import java.util.ArrayList;

import Controller.StoryController;
import Models.Entity;
import Models.NPCs.Doctor;
import Models.Upgrades.BonusDamage;
import Models.Upgrades.DamageReduction;
import Models.Upgrades.ForceFieldReflection;
import Models.Upgrades.MedPack;
import Models.Upgrades.SpeedBoost;
import SpriteSheet.SpriteSheet;
import javafx.scene.paint.Color;

public class Floor7Map extends Floor1Map{
	
	private ArrayList<Entity> rooms;
	private ArrayList<Entity> upgrades;
	private StoryController controller;

	public Floor7Map(StoryController controller, int width, int height) {
		super(controller, width, height);
	}

	@Override
	public void populateMap(ArrayList<Entity> rooms) {
		this.upgrades = new ArrayList<Entity>();
		this.rooms = rooms;
		populateUpgrades();
		populateLevelSpecificEntities();
	}

	public void populateLevelSpecificEntities() {

		Entity lastRoom = rooms.get(rooms.size() - 1);
		int x = rand.nextInt(lastRoom.getWidth() - 30) + lastRoom.getShape().getMinX() + 15;
		int y = rand.nextInt(lastRoom.getHeight() - 30) + lastRoom.getShape().getMinY() + 15;
		Doctor doc = new Doctor(SpriteSheet.getBorderedBlock(30, 30, Color.BLACK, 2), controller, x, y);
		getMapObjects().add(doc);
	}

	public void populateUpgrades() {
		for (Entity e : rooms) {
			Entity temp = upgradeChoice(e.getShape().getMinX(), e.getShape().getMinY(), e.getWidth(), e.getHeight());
			if (temp != null) {
				upgrades.add(temp);
			}
		}
		getMapObjects().addAll(upgrades);
	}

	public Entity upgradeChoice(int roomX, int roomY, int width, int height) {

		if (rand.nextInt(100) + 1 < 51) {
			int selection = rand.nextInt(5) + 1;// choosing what Upgrade is
												// created
			int x = rand.nextInt(width - 30) + roomX + 15;
			int y = rand.nextInt(height - 30) + roomY + 15;
			switch (selection) {
			case 1:
				return new BonusDamage(SpriteSheet.getBorderedBlock(10, 10, Color.BLANCHEDALMOND, 2), x, y);
			case 2:
				return new DamageReduction(SpriteSheet.getBorderedBlock(10, 10, Color.CORNSILK, 2), x, y);
			case 3:
				return new ForceFieldReflection(SpriteSheet.getBorderedBlock(10, 10, Color.YELLOW, 2), x, y);
			case 4:
				return new MedPack(SpriteSheet.getBorderedBlock(10, 10, Color.RED, 2), x, y);
			default:
				return new SpeedBoost(SpriteSheet.getBorderedBlock(10, 10, Color.PLUM, 2), x, y);

			}
		} else {
			return null;
		}
	}
	
	@Override
	public void generateDoors(ArrayList<Entity> rooms){
		createExit(rooms.get(0));
//		createEntrance(rooms.get(rooms.size()-1));
	}
}
