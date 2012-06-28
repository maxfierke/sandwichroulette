package com.maxfierke.sandwichroulette;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SandwichDBHelper extends SQLiteOpenHelper {
	
	public static final String TABLE_SAMMICHES = "sammiches";
	
	/* DO NOT ALTER THE ORDER OF ANY ELEMENTS ALREADY PRESENT 
	 * Add to the end.
	 */
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_BASE = "base";
	public static final String COLUMN_BREAD = "bread";
	public static final String COLUMN_CHEESE = "cheese";
	public static final String COLUMN_PICKLES = "pickles";
	public static final String COLUMN_ONIONS = "onions";
	public static final String COLUMN_LETTUCE = "lettuce";
	public static final String COLUMN_TOMATO = "tomato";
	public static final String COLUMN_GRNPEPPER = "grnPepper";
	public static final String COLUMN_SPINACH = "spinach";
	public static final String COLUMN_CUCUMBER = "cucumber";
	public static final String COLUMN_BANPEPPER = "banPepper";
	public static final String COLUMN_OLIVE = "olive";
	public static final String COLUMN_JALAPENO = "jalapeno";
	public static final String COLUMN_CHIPOTLE = "chipotle";
	public static final String COLUMN_HNYMUSTARD = "hnyMustard";
	public static final String COLUMN_SWTONION = "swtOnion";
	public static final String COLUMN_MAYO = "mayo";
	public static final String COLUMN_MUSTARD = "mustard";
	public static final String COLUMN_OIL = "oil";
	public static final String COLUMN_VINAIGRETTE = "vinaigrette";
	public static final String COLUMN_VINEGAR = "vinegar";
	public static final String COLUMN_ITALDRESSING = "italDressing";
	public static final String COLUMN_RANCH = "ranch";
	public static final String COLUMN_CSRDRESSING = "csrDressing";
	public static final String COLUMN_HUMMUS = "hummus";

	private static final String DATABASE_NAME = "sammiches.db";
	private static final int DATABASE_VERSION_1 = 0x01;
	private static final int DATABASE_VERSION = DATABASE_VERSION_1;
	
	// Database creation
	private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_SAMMICHES + " (" +
		COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
		COLUMN_TITLE + " TEXT NOT NULL UNIQUE DEFAULT ('')," +
		
		/* DO NOT ALTER THE ORDER OF ANY ELEMENTS ALREADY PRESENT 
		 * Add to the end.
		 */
		COLUMN_BASE + " INTEGER NOT NULL DEFAULT (0)," + 
		COLUMN_BREAD + " INTEGER NOT NULL DEFAULT (0)," +
		COLUMN_CHEESE + " INTEGER NOT NULL DEFAULT (0)," +
		COLUMN_PICKLES + " NUMERIC NOT NULL DEFAULT (0)," + // Boolean
		COLUMN_ONIONS + " NUMERIC NOT NULL DEFAULT (0)," + // Boolean
		COLUMN_LETTUCE + " NUMERIC NOT NULL DEFAULT (0)," + // Boolean
		COLUMN_TOMATO + " NUMERIC NOT NULL DEFAULT (0)," + // Boolean
		COLUMN_GRNPEPPER + " NUMERIC NOT NULL DEFAULT (0)," + // Boolean
		COLUMN_SPINACH + " NUMERIC NOT NULL DEFAULT (0)," + // Boolean
		COLUMN_CUCUMBER + " NUMERIC NOT NULL DEFAULT (0)," + // Boolean
		COLUMN_BANPEPPER + " NUMERIC NOT NULL DEFAULT (0)," + // Boolean
		COLUMN_OLIVE + " NUMERIC NOT NULL DEFAULT (0)," + // Boolean
		COLUMN_JALAPENO + " NUMERIC NOT NULL DEFAULT (0)," + // Boolean
		COLUMN_CHIPOTLE + " NUMERIC NOT NULL DEFAULT (0)," + // Boolean
		COLUMN_HNYMUSTARD + " NUMERIC NOT NULL DEFAULT (0)," + // Boolean
		COLUMN_SWTONION + " NUMERIC NOT NULL DEFAULT (0)," + // Boolean
		COLUMN_MAYO + " NUMERIC NOT NULL DEFAULT (0)," + // Boolean
		COLUMN_MUSTARD + " NUMERIC NOT NULL DEFAULT (0)," + // Boolean
		COLUMN_OIL + " NUMERIC NOT NULL DEFAULT (0)," + // Boolean
		COLUMN_VINAIGRETTE + " NUMERIC NOT NULL DEFAULT (0)," + // Boolean
		COLUMN_VINEGAR + " NUMERIC NOT NULL DEFAULT (0)," + // Boolean
		COLUMN_ITALDRESSING + " NUMERIC NOT NULL DEFAULT (0)," + // Boolean
		COLUMN_RANCH + " NUMERIC NOT NULL DEFAULT (0)," + // Boolean
		COLUMN_CSRDRESSING + " NUMERIC NOT NULL DEFAULT (0)," + // Boolean
		COLUMN_HUMMUS + " NUMERIC NOT NULL DEFAULT (0)" + // Boolean
    ")";

	public SandwichDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(SandwichDBHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion);
		if(oldVersion==DATABASE_VERSION_1) {
			// TODO: Add columns added since version 1 of the database.
			// Set default ingredient values to false (0).
		}

	}

}
