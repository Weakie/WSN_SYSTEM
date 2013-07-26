package model;

public class InheritMethodCross extends InheritMethodCombo {

	String[] items = {
			"onePoint", 
			"twoPoint",
			"uniformd",
			"arithmeticPart",
			"arithmeticFull"};

	@Override
	protected String[] getItems() {
		return items;
	}

}
