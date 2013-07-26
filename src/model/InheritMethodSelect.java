package model;

public class InheritMethodSelect extends InheritMethodCombo {
	String[] items = {
			"elitistModel",
			"rouletteWheel",
			"tournamentSelection",
			"stoTournamentSelection"};
	@Override
	protected String[] getItems() {
		return items;
	}
}
