package data;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Residential extends District{

	private int size;
	private int maxPeople;
	private String src1 = "/src/img/House_LVL1.jpg";
	private String src2 = "/src/img/House_LVL2.jpg";
	private String src3 = "/src/img/House_LVL3.jpg";
	private BufferedImage img;
	
	public Residential() {
		super(Const.GAIN_RES,0); 
		size = 1; // Taille de depart
	}
	
	public BufferedImage getImg() {
		if(size == 1) {
			try {
				img = ImageIO.read(new File(src1));
			}catch (IOException e){
				e.printStackTrace();
			}
		}
		else if(size == 2) {
			try {
				img = ImageIO.read(new File(src2));
			}catch (IOException e){
				e.printStackTrace();
			}
		}
		else {
			try {
				img = ImageIO.read(new File(src3));
			}catch (IOException e){
				e.printStackTrace();
			}
		}
		return img;
	}
	
	public void upSize() {
		size++;
	}
	
	public int getMaxPeople() {
		return maxPeople;
	}
	
	public boolean isResidential() {
		return true;
	}
	
	public int getSize() {
		return size;
	}
	
	public void setSize(int newSize) {
		size = newSize;
	}
}
