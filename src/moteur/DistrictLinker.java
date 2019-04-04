package moteur;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import data.*;

public class DistrictLinker {

	
	public DistrictLinker() {
	}
	
	public void linkDistrict(Line line) {
		int distance = line.getDistance();
		District d1 = line.getFirstDistrict();
		District d2 = line.getSecondDistrict();
		addAD(d1,d2,distance);
		addAD(d2,d1,distance);
		refreshAD(d2,d1, distance, true);
		refreshAD(d1,d2, distance, true);
	}
	
	public void addAD(District dist1, District dist2,int distance) {
		dist1.addAccessibleDistrict(dist2, distance, true);
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
				dist2.addAccessibleDistrict(newDistrict, newDistance,true);
			}
		}
	}
	
	public void refreshAD(District dist1, District dist2, int distance,boolean line) {
		ArrayList<AccessibleDistrict> aDistrict1 = dist1.getAccessibleDistrict();
		ArrayList<AccessibleDistrict> newAccessible;
		District addNewDistrict;
		int newDistance;
		int size = aDistrict1.size();
		int sizeNA;
		int index, j;
		boolean isAccessible = false;
		for(index = 0; index<size; index++) {
			
			for(j = 0; j < dist2.getAccessibleDistrict().size(); j++) {
				if(dist2.getAccessibleDistrict().get(j).getDistrict() == aDistrict1.get(index).getDistrict()) {
					isAccessible = true;
				}
			}
			if(!isAccessible) {
				dist2.addAccessibleDistrict(aDistrict1.get(index).getDistrict(), distance + aDistrict1.get(index).getDistance(),line);
				isAccessible = false;
			}
			
			addNewDistrict = aDistrict1.get(index).getDistrict();
			newAccessible = addNewDistrict.getAccessibleDistrict();
			sizeNA = newAccessible.size();
			for(j=0; j<sizeNA;j++) {
				if((newAccessible.get(j).getDistrict() != dist2) && (newAccessible.get(j).getDistrict() != dist1) && (addNewDistrict != dist2)) {
					if(!containsDistrict(newAccessible, dist2)) {
						newDistance = distance + correspondingLineDistance(dist1.getLines(), dist1, addNewDistrict);
						addNewDistrict.addAccessibleDistrict(dist2, newDistance,line);
					}
				}
			}
		}
		Collections.sort(aDistrict1);
	}
	
	public int correspondingLineDistance(ArrayList<Line> line, District d1, District d2) {
		int minDist = 99;
		for(int index = 0; index < line.size(); index ++) { 
			District fd = line.get(index).getFirstDistrict();
			District sd = line.get(index).getSecondDistrict();
			if((fd == d1 && sd == d2) || (fd == d2 && sd == d1)) {
				if(minDist>line.get(index).getDistance()) {
					minDist = line.get(index).getDistance();
				}
			}
		}
		return minDist;
	}
	
	public boolean containsDistrict(ArrayList<AccessibleDistrict> aDistrict, District d) {
		for(int i=0; i<aDistrict.size();i++) {
			if(aDistrict.get(i).getDistrict() == d) {
				return true;
			}
		}
		return false;
	}
	
	public void stationModification(District[][] dist, ArrayList<Line> lineList, District concernedDistrict) { // Quand une station est crée sur une potentielle ligne
		ArrayList<Coordinates> currentLineCoordinates = new ArrayList<>();
		ArrayList<Coordinates> newLineCoordinates = new ArrayList<>();
		Line currentLine;
		Coordinates coo;
		int size = lineList.size();
		for(int index = 0; index < size; index ++) {
			currentLine = lineList.get(index);
			currentLineCoordinates = currentLine.getVisitedCoordonates();
			for(int j = 0; j < currentLineCoordinates.size();j++) {
				coo = currentLineCoordinates.get(j);
				if(dist[coo.getX()][coo.getY()] == concernedDistrict) {
					newLineCoordinates = visitedCoordinates(0, j+1, currentLine);
					Line newLine1 = new Line(currentLine.getFirstDistrict(),concernedDistrict,j, true, newLineCoordinates); 
					newLineCoordinates = visitedCoordinates(j, currentLineCoordinates.size(), currentLine);
					Line newLine2 = new Line(currentLine.getSecondDistrict(),concernedDistrict,currentLineCoordinates.size() - j, true, newLineCoordinates);
					lineList.add(newLine1);
					lineList.add(newLine2);
					linkDistrict(newLine1);
					linkDistrict(newLine2);
					currentLine = null;
					lineList.remove(currentLine);
				}
			}
		}
	}
	
	public void removedLine(Line line) {
		District d1 = line.getFirstDistrict();
		District d2 = line.getSecondDistrict();
		int distance = line.getDistance();
		removeAD(d1,d2,distance,0);
		removeAD(d2,d1,distance,0);
	}
	
	public void removeAD(District dist1, District dist2, int distance, int compteur) {
		ArrayList<AccessibleDistrict> aDistrict1 = dist1.getAccessibleDistrict();
		int size = aDistrict1.size();
		int index;
		ArrayList<Integer> suppIndex = new ArrayList<>();
		for(index = 0; index<size; index++) {
			if(aDistrict1.get(index).getDistrict() == dist2  && aDistrict1.get(index).getDistance()== distance) {
				suppIndex.add(index);
			}
			else if(compteur <= 3) {
				removeAD(aDistrict1.get(index).getDistrict(),dist2, distance + aDistrict1.get(index).getDistance(), compteur +1);
			}
		}
		for(index= 0; index < suppIndex.size(); index++) {
			aDistrict1.remove(suppIndex.get(index));
		}
	}
	
	public void remove(District tab[][], District dist) {
		ArrayList<AccessibleDistrict> aDist;
		int size;
		for(int i=0; i < tab.length; i++) {
			for(int j=0; j < tab[0].length; j++) {
				if(tab[i][j]!=null) {
					aDist = tab[i][j].getAccessibleDistrict();
					size = aDist.size();
					for(int index =0;index <aDist.size();index++) {
						if(aDist.get(index).getDistrict()==dist) {
							aDist.remove(index);
						}
					}
				}
			}
		}
	}

	
	public ArrayList<Coordinates> visitedCoordinates(int startIndex,int lastIndex, Line line){
		ArrayList<Coordinates> visited = new ArrayList<>();
		for(int index = startIndex; index < lastIndex; index ++) {
			visited.add(line.getVisitedCoordonates().get(index));
		}
		return visited;
	}
}
