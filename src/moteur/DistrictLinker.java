package moteur;

import java.util.ArrayList;
import java.util.Iterator;

import data.AccessibleDistrict;
import data.District;
import data.Line;
import data.Station;

public class DistrictLinker {

	
	public DistrictLinker() {
	}
	
	public void linkDistrict(Line line) {
		int distance = line.getDistance();
		District d1 = line.getFirstDistrict();
		District d2 = line.getSecondDistrict();
		System.out.println("----"+ d1 +"  " + d2+"----");
		addAD(d1,d2,distance);
		addAD(d2,d1,distance);
		refreshAD(d1,d2, distance);
		refreshAD(d2,d1, distance);
	}
	
	public void addAD(District dist1, District dist2,int distance) {
		dist1.addAccessibleDistrict(dist2, distance);
		//System.out.println("Ajout de "+ dist2 +" dans " + dist1);
		ArrayList<AccessibleDistrict> aDistrict1 = dist1.getAccessibleDistrict();
		ArrayList<AccessibleDistrict> aDistrict2 = dist2.getAccessibleDistrict();
		Boolean isAccessible;
		District newDistrict;
		int newDistance;
		int size = aDistrict1.size();
		for(int index = 0; index < size;index++) {
			isAccessible  = false;
			for(int j=0; j< aDistrict2.size(); j++) {
				if(aDistrict1.get(index).getDistrict() == aDistrict2.get(j).getDistrict()) {
					isAccessible = true;
				}
			}
			if(!isAccessible && aDistrict1.get(index).getDistrict()!= dist2) {
				newDistance = distance + aDistrict1.get(index).getDistance();
				newDistrict = aDistrict1.get(index).getDistrict();
				//System.out.println("..Ajout de: "+ newDistrict + " dans " + dist2 + " avec dist1 = "+dist1 +" et distance " + newDistance);
				dist2.addAccessibleDistrict(newDistrict, newDistance);
			}
		}
	}
	
	public void refreshAD(District dist1, District dist2, int distance) {
		ArrayList<AccessibleDistrict> aDistrict1 = dist1.getAccessibleDistrict();
		ArrayList<AccessibleDistrict> newAccessible;
		District addDistrict;
		int newDistance;
		int size = aDistrict1.size();
		int sizeNA;
		for(int index = 0; index<size; index++) {
			System.out.println("<--->");
			//isAccesible = false;
			addDistrict = aDistrict1.get(index).getDistrict();
			newAccessible = addDistrict.getAccessibleDistrict();
			sizeNA = newAccessible.size();
			for(int j=0; j<sizeNA;j++) {
				if((newAccessible.get(j).getDistrict() != dist2) && (newAccessible.get(j).getDistrict() != dist1) && (addDistrict != dist2)) {
					System.out.println("newAccessible "+ newAccessible.get(j).getDistrict() + " et dist 1 = " + dist1 + " et dist2 = "+dist2 );
					if(!containsDistrict(newAccessible, dist2)) {
						System.out.println("add");
						newDistance = distance + 10;
						addDistrict.addAccessibleDistrict(dist2, newDistance);
					}
				}
			}
		}
	}
	
	
	public boolean containsDistrict(ArrayList<AccessibleDistrict> aDistrict, District d) {
		for(int i=0; i<aDistrict.size();i++) {
			if(aDistrict.get(i).getDistrict() == d) {
				return true;
			}
		}
		return false;
	}
}
