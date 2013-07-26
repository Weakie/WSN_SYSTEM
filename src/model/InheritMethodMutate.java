package model;


public class InheritMethodMutate extends InheritMethodCombo{
	String[] items = {
			"areaExchange",
			"twoPointsExchange",
			"borderExchange",
			"averageMutate",
			"averageMutateExchange",
			"maxDisExchange",
			"disagreeMutate",
			"adapterSelfMutate"};

	@Override
	protected String[] getItems() {
		return items;
	}
	
}
