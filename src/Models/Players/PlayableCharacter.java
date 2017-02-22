package Models.Players;

import Interfaces.Collideable;
import Models.Bounds;
import Models.Entity;
import javafx.scene.image.Image;

public class PlayableCharacter extends Entity {

	public PlayableCharacter(Image i, int x, int y) {
		super(i, x, y);
	}

	@Override
	public boolean isColliding(Collideable c) {
		throw new UnsupportedOperationException("Not yet Implemented");
	}

	@Override
	public Bounds getBounds() {
		throw new UnsupportedOperationException("Not yet Implemented");
	}
}
