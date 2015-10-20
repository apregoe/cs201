package client;

import libraries.ImageLibrary;
import java.awt.Rectangle;

public class FactoryWall extends FactoryObject {
	public FactoryWall(Rectangle inDimensions){
		super(inDimensions);
		mImage = ImageLibrary.getImage("resources/img/Wall.jpeg");
	}
	public FactoryWall(Rectangle inDimensions, String file){
		super(inDimensions);
		mImage = ImageLibrary.getImage(file);
	}
}
