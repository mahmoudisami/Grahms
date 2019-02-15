package moteur;


public class GameProgress {
	
	// private long speed = 1000; à mettre dans gameScreen
	private static Clock clock;
	
	public void GameProgress(long speed){
		String dayName = clock.getDayName();
		int dayCount = clock.getDayCpt();
		String hour = clock.getHour();
		
		while (true) {
			try {
				Thread.sleep(speed);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
			
			
			clock.increment();		
			// a set (clock.displayGameTimeInfo()); puis get dans ihm
		}
	}
}

