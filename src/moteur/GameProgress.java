package moteur;

import java.util.ArrayList;
import java.util.Iterator;

import data.*;
import ihm.*;

public class GameProgress {
	
	private Clock clock;
	private Money money;
	private FinancialCalculator fin = new FinancialCalculator();
	private int tmpMoney;
	private String historicText ="";
	private Grid grid;
	private District[][] map;
	private int nbrLine, nbrRow;
	
	public GameProgress(Clock clock, Money money, Grid grid) {
		this.clock = clock;
		this.money = money;
		this.grid = grid;
		map = grid.getMapTab();
		nbrLine = map.length;
		nbrRow = map[0].length;
	}
	
	public void launchGameProgress () {
		if(clock.getDayPos()==7 && clock.getHour().equals("23")) { //Chaque dimanche a 23h
			historicCalculator();
		}
		if(clock.getHour().equals("07") && clock.getDayPos() != 7 && clock.getDayPos() != 6) {
			goWork();
		}
		if(clock.getHour().equals("16") && clock.getDayPos() != 7 && clock.getDayPos() != 6) {
			goHome();
		}
	}
	
	public void goWork() {
		for(int i=0; i<nbrLine; i++) {
			for(int j=0; j<nbrRow; j++) {
				if(map[i][j] != null && map[i][j].isResidential()) {
					ArrayList<AccessibleDistrict> aDistrict = map[i][j].getAccessibleDistrict();
					Iterator<AccessibleDistrict> ite = aDistrict.iterator();
					AccessibleDistrict visitedDistrict;
					while(ite.hasNext()) {
						visitedDistrict = ite.next();
						if(visitedDistrict.getDistrict().isCommercial()) {
							/* on envoie x habitants travailler
							 * Calcul du temps de trajet par rapport à la distance
							 * On ajoute les nouveaux travailleurs au quartier concerné
							 */
						}
						else if(visitedDistrict.getDistrict().isService()){
							/* on envoie x habitants travailler
							 * Calcul du temps de trajet par rapport à la distance
							 * On ajoute les nouveaux travailleurs au quartier concerné
							 */
						}
						
					}
				}
			}
		}
	}
	
	public void goHome() {
		for(int i=0; i<nbrLine; i++) {
			for(int j=0; j<nbrRow; j++) {
				if(map[i][j] != null && !map[i][j].isResidential()) {
					map[i][j].changeActualPeople(0); // Partent du travail
					/*
					 * Attente du tps de trajet
					 * Ajout ajout des travailleurs dans le quartier résid
					 * 
					 */
				}
			}
		}
	}
	
	
	
	public void historicCalculator() {
		tmpMoney = fin.districtCalculatorCost(grid.getMapTab());
		money.withDraw(tmpMoney);
		historicText += "------------ "+ clock.getDay() +" "+ clock.getMonthName() +"------------\n";
		historicText += "-" + tmpMoney + " Entretien des quartiers\n";
		tmpMoney = fin.districtCalculatorGain(grid.getMapTab());
		money.addMoney(tmpMoney);
		historicText += "+" + tmpMoney + " Gain des quartiers \n";
		tmpMoney = fin.stationCalculatorCost(grid.getMapTab());
		money.withDraw(tmpMoney);
		historicText += "-" + tmpMoney + " Entretien des stations \n";
	}
	
	public String getHistoricText() {
		return historicText;
	}
}

