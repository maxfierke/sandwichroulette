package com.maxfierke.sandwichroulette;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.CheckBox;

public final class SandwichIngredientData {
	
	Resources resources;
	
	private CheckBox[] ingredients;
	private CheckBox[] sauces;
	private String[] baseArray;
	private String[] breadArray;
	private String[] cheeseArray;
	
	public SandwichIngredientData(Context context, View view)
	{
		resources = context.getResources();
		
		baseArray = resources.getStringArray(R.array.base_array);
		breadArray = resources.getStringArray(R.array.bread_array);
		cheeseArray = resources.getStringArray(R.array.cheese_array);
		
		/* DO NOT ALTER THE ORDER OF ANY ELEMENTS ALREADY PRESENT
		 * THEY CORRESPOND TO DATABASE COLUMNS 
		 * Add to the end.
		 */

		ingredients = new CheckBox[]{
				(CheckBox)view.findViewById(R.id.picklesBox),
				(CheckBox)view.findViewById(R.id.onionsBox),
				(CheckBox)view.findViewById(R.id.lettuceBox),
				(CheckBox)view.findViewById(R.id.tomatoBox),
				(CheckBox)view.findViewById(R.id.grnPepBox),
				(CheckBox)view.findViewById(R.id.spinachBox),
				(CheckBox)view.findViewById(R.id.cucumberBox),
				(CheckBox)view.findViewById(R.id.bananaPepBox),
				(CheckBox)view.findViewById(R.id.oliveBox),
				(CheckBox)view.findViewById(R.id.jalapenoBox)
		};
		
		sauces = new CheckBox[]{
				(CheckBox)view.findViewById(R.id.chipotleBox),
				(CheckBox)view.findViewById(R.id.honeyMustardBox),
				(CheckBox)view.findViewById(R.id.sweetOnionBox),
				(CheckBox)view.findViewById(R.id.mayoBox),
				(CheckBox)view.findViewById(R.id.mustardBox),
				(CheckBox)view.findViewById(R.id.oilBox),
				(CheckBox)view.findViewById(R.id.vinaigretteBox),
				(CheckBox)view.findViewById(R.id.vinegarBox),
				(CheckBox)view.findViewById(R.id.italianDrsngBox),
				(CheckBox)view.findViewById(R.id.ranchBox),
				(CheckBox)view.findViewById(R.id.caesarDrsngBox),
				(CheckBox)view.findViewById(R.id.hummusBox)
		};
	}
	
	public String[] getBaseArray() {
		return baseArray;
	}
		
	public String[] getBreadArray() {
		return breadArray;
	}
		
	public String[] getCheeseArray() {
		return cheeseArray;
	}
	
	public CheckBox[] getIngredients() {
		return ingredients;
	}
	
	public CheckBox[] getSauces() {
		return sauces;
	}
	
	public CheckBox getIng(int id) {
		return ingredients[id];
	}
	
	public CheckBox getSauce(int id) {
		return sauces[id];
	}
	
	/*
	 * DO NOT CHANGE THESE VALUES
	 * ONLY ADD NEW VALUES
	 * THE VALUES MUST MATCH WITH THE ORDER OF THE RESOURCES
	 * These values must remain for backwards compatibility. 
	 */

	/* Sandwich Base Values */
	public final int BASE_HAM = 0x00; // Ham
	public final int BASE_BLT = 0x01; // Bacon, Lettuce, Tomato
	public final int BASE_BUFCHK = 0x02; // Buffalo Chicken
	public final int BASE_CHKBAC = 0x03; // Chicken & Bacon
	public final int BASE_HAMTURK = 0x04;  // Ham & Turkey
	public final int BASE_HAMITSAUSG = 0x05; // Ham & Italian Sausage
	public final int BASE_MEATBALL = 0x06; // Meatball
	public final int BASE_CHKSALAD = 0x07; // Chicken Salad
	public final int BASE_CHK = 0x08; // Chicken
	public final int BASE_RBEEF = 0x09; // Roast Beef
	public final int BASE_SPITMEAT = 0x0A; // Spicy Italian Meats
	public final int BASE_BEEFSTK = 0x0B; // Steak
	public final int BASE_CLUB = 0x0C; // Clubhouse
	public final int BASE_HAMTURKMLT = 0x0D; // Ham & Turkey Melt
	public final int BASE_PHILCHZ = 0x0E; // Philly Cheesesteak
	public final int BASE_TRYKICHK = 0x0F; // Teriyaki Chicken
	public final int BASE_TUNA = 0x10; // Tuna
	public final int BASE_TURK = 0x11; // Turkey
	public final int BASE_VEG = 0x12; // Veggie
	
	/* Sandwich Bread Values */
	public final int BRD_WHEAT = 0x00;
	public final int BRD_FLAT = 0x01;
	public final int BRD_ITAL = 0x02;
	public final int BRD_OATWHEAT = 0x03;
	public final int BRD_ITHERBCHZ = 0x04;
	public final int BRD_MONTCHDR = 0x05;
	public final int BRD_GARLIC = 0x06;
	public final int BRD_SRDOUGH = 0x07;
	
	/* Sandwich Cheese Values */
	public final int CHZ_CHD = 0x00;
	public final int CHZ_AMER = 0x01;
	public final int CHZ_SHRMONTCHD = 0x02;
	public final int CHZ_PARM = 0x03;
	public final int CHZ_PEPJCK = 0x04;
	public final int CHZ_PROV = 0x05;
	public final int CHZ_SWISS = 0x06;
	
	/* Sandwich Ingredient Values */
	public final int IN_PICKLES = 0x00;
	public final int IN_ONIONS = 0x01;
	public final int IN_LETTUCE = 0x02;
	public final int IN_TOMATO = 0x03;
	public final int IN_GRNPEP = 0x04;
	public final int IN_SPINACH = 0x05;
	public final int IN_CUCUMBER = 0x06;
	public final int IN_BANPEP = 0x07;
	public final int IN_OLIVE = 0x08;
	public final int IN_JALAPENO = 0x09;
	
	/* Sandwich Sauce Values */
	public final int SC_CHIPOTLE = 0x00;
	public final int SC_HNYMUST = 0x01;
	public final int SC_SWTONION = 0x02;
	public final int SC_MAYO = 0x03;
	public final int SC_MUST = 0x04;
	public final int SC_OIL = 0x05;
	public final int SC_VINAI = 0x06;
	public final int SC_VINEGAR = 0x07;
	public final int SC_ITALDRSNG = 0x08;
	public final int SC_RANCH = 0x09;
	public final int SC_CSRDRSNG = 0x0A;
	public final int SC_HUMMUS = 0x0B;
	
}
