package data;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Residential extends District{

	private int size;
	private int happinessLevel;
	private int maxPeople;
	private String src1 = "src/image/House_LVL1.png";
	private String src2 = "src/image/House_LVL2.png";
	private String src3 = "src/image/House_LVL3.png";
	private String srcStation1 = "src/image/HouseStation_LVL1.png"; 
	private String srcStation2 = "src/image/HouseStation_LVL2.png"; 
	private String srcStation3 = "src/image/HouseStation_LVL3.png";
	private BufferedImage img;
	private District work;
	private District services;
	
	
	public Residential() { 
		super(0); 
		size = 1; // Taille de depart
		isStation = false;
	}
	
	public BufferedImage getImg() {
		if(!isStation) {
				try {
					img = ImageIO.read(new File(src1));
				}catch (IOException e){
					e.printStackTrace();
				}
		}
		else {
			if(size == 1) {
				try {
					img = ImageIO.read(new File(srcStation1)); // Changer avec station
				}catch (IOException e){
					e.printStackTrace();
				}
			}
			else if(size == 2) {
				try {
					img = ImageIO.read(new File(srcStation2)); // Changer avec station
				}catch (IOException e){
					e.printStackTrace();
				}
			}
			else {
				try {
					img = ImageIO.read(new File(srcStation3)); // Changer avec station
				}catch (IOException e){
					e.printStackTrace();
				}
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
	
	public void setWorkingDistrict(District dist) {
		work = dist;
	}
	
	public void setServicesDistrict(District dist) {
		services = dist;
	}
}
