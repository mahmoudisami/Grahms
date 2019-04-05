package data;

import data.Const;

public class Money {
	
	private static int money;
	
	public Money() {
		if(!(money > 0)) {
		money = Const.START_MONEY;
		}
	}
	
	public static void addMoney(int gain) {
		money += gain;
	}
	
	public void withDraw(int loss) {
		money -= loss;
	}

	public static int getMoney() {
		return money;
	}
	public static void setMoney(int moneyPut) {
		money = moneyPut;
	}
	
}
