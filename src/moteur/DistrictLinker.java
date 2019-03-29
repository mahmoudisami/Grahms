package moteur;

import java.util.ArrayList;
import java.util.Iterator;

import data.*;

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
		refreshAD(d2,d1, distance);
		refreshAD(d1,d2, distance);
	}
	
	public void addAD(District dist1, District dist2,int distance) {
		dist1.addAccessibleDistrict(dist2, distance);
		System.out.println("Ajout de "+ dist2 +" dans " + dist1);
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
		District addNewDistrict;
		int newDistance;
		int size = aDistrict1.size();
		int sizeNA;
		for(int index = 0; index<size; index++) {
			addNewDistrict = aDistrict1.get(index).getDistrict();
			newAccessible = addNewDistrict.getAccessibleDistrict();
			sizeNA = newAccessible.size();
			for(int j=0; j<sizeNA;j++) {
				if((newAccessible.get(j).getDistrict() != dist2) && (newAccessible.get(j).getDistrict() != dist1) && (addNewDistrict != dist2)) {
					if(!containsDistrict(newAccessible, dist2)) {
						System.out.println("\n----" + dist2 );
						newDistance = distance + correspondingLineDistance(dist1.getLines(), dist1, addNewDistrict);
						addNewDistrict.addAccessibleDistrict(dist2, newDistance);
					}
				}
			}
		}
	}
	
	public int correspondingLineDistance(ArrayList<Line> line, District d1, District d2) {
		//System.out.println("d1 : " + d1 +" d2 : " + d2);
		int minDist = 99;
		for(int index = 0; index < line.size(); index ++) { 
			District fd = line.get(index).getFirstDistrict();
			District sd = line.get(index).getSecondDistrict();
			//System.out.println("fd : " + fd +" sd : " + sd);
			if((fd == d1 && sd == d2) || (fd == d2 && sd == d1)) {
				if(minDist>line.get(index).getDistance()) {
					minDist = line.get(index).getDistance();
					System.out.println(" New Distance min = "+ line.get(index).getDistance());
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
		Iterator<Line> listIterator = lineList.iterator();
		ArrayList<Coordinates> currentLineCoordinates = new ArrayList<>();
		ArrayList<Coordinates> newLineCoordinates = new ArrayList<>();
		Line currentLine;
		Coordinates coo;
		while(listIterator.hasNext()) {
			currentLine = listIterator.next();
			currentLineCoordinates = currentLine.getVisitedCoordonates();
			for(int index = 0; index < currentLineCoordinates.size();index++) {
				coo = currentLineCoordinates.get(index);
				if(dist[coo.getX()][coo.getY()] == concernedDistrict) {
					newLineCoordinates = visitedCoordinates(0, index, currentLine);
					Line newLine1 = new Line(currentLine.getFirstDistrict(),concernedDistrict,index, true, newLineCoordinates); 
					newLineCoordinates = visitedCoordinates(index, currentLineCoordinates.size(), currentLine);
					Line newLine2 = new Line(currentLine.getSecondDistrict(),concernedDistrict,currentLineCoordinates.size() - index, true, newLineCoordinates);
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
	
	public ArrayList<Coordinates> visitedCoordinates(int startIndex,int lastIndex, Line line){
		ArrayList<Coordinates> visited = new ArrayList<>();
		for(int index = startIndex; index < lastIndex; index ++) {
			visited.add(line.getVisitedCoordonates().get(index));
		}
		return visited;
	}
}
