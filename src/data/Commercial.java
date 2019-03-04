package data;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sun.prism.Image;

public class Commercial extends District{

	private int size;
	private String src = "src/image/Commercial.png";
	private String srcStation = "src/image/CommercialStation.png"; // Chemin a changer
	private BufferedImage img;

	public Commercial() {
		super(Const.GAIN_COM,0); 
		size = 1; // Taille de depart 
	}
	
	public BufferedImage getImg() {
		try {
			if(!isStation) {
				img = ImageIO.read(new File(src));
			}
			else {
				img = ImageIO.read(new File(srcStation)); // changer img avec station
			}
		}catch (IOException e){
			e.printStackTrace();
		}
		return img;
	}
	
	public void upSize() {
		size++;
	}
	
	public boolean isCommercial() {
		return true;
	}
	
}
