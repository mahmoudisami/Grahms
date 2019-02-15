package data;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Residential extends District{

	private int size;
	private int maxPeople;
	private String src1 = "src/image/House_LVL1.jpg";
	private String src2 = "src/image/House_LVL2.jpg";
	private String src3 = "src/image/House_LVL3.jpg";
	private String srcStation1 = "src/image/House_LVL3.jpg"; // Changer chemin
	private String srcStation2 = "src/image/House_LVL2.jpg"; // Changer chemin
	private String srcStation3 = "src/image/House_LVL3.jpg"; // Changer chemin
	private BufferedImage img;
	private District work;
	private District services;
	
	
	public Residential() { 
		super(Const.GAIN_RES,0); 
		size = 1; // Taille de depart
		isStation = false;
	}
	
	public BufferedImage getImg() {
		if(!isStation) {
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
					img = ImageIO.read(new File(srcStation1)); // Changer avec station
				}catch (IOException e){
					e.printStackTrace();
				}
			}
			else {
				try {
					img = ImageIO.read(new File(srcStation1)); // Changer avec station
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
