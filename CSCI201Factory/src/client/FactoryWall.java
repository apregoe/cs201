package client;

import libraries.ImageLibrary;
import java.awt.Rectangle;

public class FactoryWall extends FactoryObject {
	public FactoryWall(Rectangle inDimensions){
		super(inDimensions);
		mImage = ImageLibrary.getImage("resources/img/Wall.jpeg");
	}
}
