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
	
	public void linkDistrict(District d1, District d2, int distance) {
		Station s1, s2;
		s1 = d1.getStation();
		s2 = d2.getStation();
		Line line = new Line(s1,s2,distance);
		addAD(d1,d2,distance);
		addAD(d2,d1,distance);
	}
	
	public void addAD(District dist1, District dist2,int distance) {
		dist1.addAccessibleDistrict(dist2, distance);
		ArrayList<AccessibleDistrict> aDistrict1 = dist1.getAccessibleDistrict();
		ArrayList<AccessibleDistrict> aDistrict2 = dist2.getAccessibleDistrict();
		Boolean isAccessible;
		District newDistrict;
		int newDistance;
		int size = aDistrict1.size();
		for(int index = 0; index < size;index++) {
			isAccessible  = false;
			for(int j=0; j< aDistrict2.size(); j++) {
				if(aDistrict1.get(index) == aDistrict2.get(j)) {
					isAccessible = true;
				}
			}
			if(!isAccessible && aDistrict1.get(index).getDistrict()!= dist2) {
				newDistance = distance + aDistrict1.get(index).getDistance();
				newDistrict = aDistrict1.get(index).getDistrict();
				dist2.addAccessibleDistrict(newDistrict, newDistance);
			}
		}
	}
}
