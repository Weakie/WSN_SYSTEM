package model;

public class InheritMethodCalcuProb extends InheritMethodCombo{
	String[] items = {
			"fitnessProp",
			"linerRankBased",
			"unLinerRankBased"};
	
	@Override
	protected String[] getItems() {
		return items;
	}
}
