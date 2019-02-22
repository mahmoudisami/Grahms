package data;

import data.Const;

public class Money {
	
	private int money;
	
	public Money() {
		money = Const.START_MONEY;
	}
	
	public void addMoney(int gain) {
		money += gain;
	}
	
	public void withDraw(int loss) {
		money -= loss;
	}

	public int getMoney() {
		return money;
	}	
	
}
