package com.maxfierke.sandwichroulette;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class SandwichDataSource {
	// Database fields
	private SQLiteDatabase database;
	private SandwichDBHelper dbHelper;
	private String[] allColumns = { 
		SandwichDBHelper.COLUMN_ID,
		SandwichDBHelper.COLUMN_TITLE,
		SandwichDBHelper.COLUMN_BASE,
		SandwichDBHelper.COLUMN_BREAD,
		SandwichDBHelper.COLUMN_CHEESE,
		SandwichDBHelper.COLUMN_PICKLES,
		SandwichDBHelper.COLUMN_ONIONS,
		SandwichDBHelper.COLUMN_LETTUCE,
		SandwichDBHelper.COLUMN_TOMATO,
		SandwichDBHelper.COLUMN_GRNPEPPER,
		SandwichDBHelper.COLUMN_SPINACH,
		SandwichDBHelper.COLUMN_CUCUMBER,
		SandwichDBHelper.COLUMN_BANPEPPER,
		SandwichDBHelper.COLUMN_OLIVE,
		SandwichDBHelper.COLUMN_JALAPENO,
		SandwichDBHelper.COLUMN_CHIPOTLE,
		SandwichDBHelper.COLUMN_HNYMUSTARD,
		SandwichDBHelper.COLUMN_SWTONION,
		SandwichDBHelper.COLUMN_MAYO,
		SandwichDBHelper.COLUMN_MUSTARD,
		SandwichDBHelper.COLUMN_OIL,
		SandwichDBHelper.COLUMN_VINAIGRETTE,
		SandwichDBHelper.COLUMN_VINEGAR,
		SandwichDBHelper.COLUMN_ITALDRESSING,
		SandwichDBHelper.COLUMN_RANCH,
		SandwichDBHelper.COLUMN_CSRDRESSING,
		SandwichDBHelper.COLUMN_HUMMUS
	};

	public SandwichDataSource(Context context) {
		dbHelper = new SandwichDBHelper(context);
	}
	
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	public void openSafe() {
		database = dbHelper.getReadableDatabase();
	}
	
	public void close() {
		dbHelper.close();
	}

	public void createSandwich(ContentValues values) {
		long insertId = database.insert(SandwichDBHelper.TABLE_SAMMICHES, null,
				values);
		System.out.println("Sandwich created with id: " + insertId);
	}
	
	public void updateSandwich(long id, ContentValues values) {
		database.update(SandwichDBHelper.TABLE_SAMMICHES, values, SandwichDBHelper.COLUMN_ID
				+ " = " + id, null);
		System.out.println("Sandwich updated with id: " + id);
	}

	public void deleteSandwich(SandwichDataModel sandwich) {
		long id = sandwich.getId();
		database.delete(SandwichDBHelper.TABLE_SAMMICHES, SandwichDBHelper.COLUMN_ID
				+ " = " + id, null);
		System.out.println("Sandwich deleted with id: " + id);
	}
	
	public SandwichDataModel getSandwich(long id) {
		Cursor cursor = database.query(SandwichDBHelper.TABLE_SAMMICHES, allColumns, SandwichDBHelper.COLUMN_ID
				+ " = " + id, null, null, null, null);
		if(cursor.getCount()>0) {
			cursor.moveToFirst();
			SandwichDataModel sandwich = cursorToSandwich(cursor);
			// Make sure to close the cursor
			cursor.close();
			return sandwich;
		} else {
			// Make sure to close the cursor
			cursor.close();
			return null;
		}
	}
	
	public SandwichDataModel getSandwich(String title) {
		Cursor cursor = database.query(SandwichDBHelper.TABLE_SAMMICHES, allColumns, SandwichDBHelper.COLUMN_TITLE
				+ " = \"" + title + "\"", null, null, null, null);
		if(cursor.getCount()>0) {
			cursor.moveToFirst();
			SandwichDataModel sandwich = cursorToSandwich(cursor);
			// Make sure to close the cursor
			cursor.close();
			return sandwich;
		} else {
			// Make sure to close the cursor
			cursor.close();
			return null;
		}
	}
	
	public List<SandwichDataModel> getAllSandwiches() {
		List<SandwichDataModel> sandwiches = new ArrayList<SandwichDataModel>();

		Cursor cursor = database.query(SandwichDBHelper.TABLE_SAMMICHES,
						allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			SandwichDataModel sandwich = cursorToSandwich(cursor);
			sandwiches.add(sandwich);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return sandwiches;
	}
	
	public int getDBSize() {
		openSafe();
		int size = getAllSandwiches().size();
		close();
		return size;
	}

	private SandwichDataModel cursorToSandwich(Cursor cursor) {
		SandwichDataModel sandwich = new SandwichDataModel();
		sandwich.setId(cursor.getLong(0));
		sandwich.setTitle(cursor.getString(1));
		sandwich.setBase(cursor.getInt(2));
		sandwich.setBread(cursor.getInt(3));
		sandwich.setCheese(cursor.getInt(4));
		sandwich.setPickles(fromSQLiteBool(cursor.getInt(5)));
		sandwich.setOnions(fromSQLiteBool(cursor.getInt(6)));
		sandwich.setLettuce(fromSQLiteBool(cursor.getInt(7)));
		sandwich.setTomato(fromSQLiteBool(cursor.getInt(8)));
		sandwich.setGrnPepper(fromSQLiteBool(cursor.getInt(9)));
		sandwich.setSpinach(fromSQLiteBool(cursor.getInt(10)));
		sandwich.setCucumber(fromSQLiteBool(cursor.getInt(11)));
		sandwich.setBanPepper(fromSQLiteBool(cursor.getInt(12)));
		sandwich.setOlive(fromSQLiteBool(cursor.getInt(13)));
		sandwich.setJalapeno(fromSQLiteBool(cursor.getInt(14)));
		sandwich.setChipotle(fromSQLiteBool(cursor.getInt(15)));
		sandwich.setHnyMustard(fromSQLiteBool(cursor.getInt(16)));
		sandwich.setSwtOnion(fromSQLiteBool(cursor.getInt(17)));
		sandwich.setMayo(fromSQLiteBool(cursor.getInt(18)));
		sandwich.setMustard(fromSQLiteBool(cursor.getInt(19)));
		sandwich.setOil(fromSQLiteBool(cursor.getInt(20)));
		sandwich.setVinaigrette(fromSQLiteBool(cursor.getInt(21)));
		sandwich.setVinegar(fromSQLiteBool(cursor.getInt(22)));
		sandwich.setItalDressing(fromSQLiteBool(cursor.getInt(23)));
		sandwich.setRanch(fromSQLiteBool(cursor.getInt(24)));
		return sandwich;
	}
	
	private boolean fromSQLiteBool(int value) {
		return (value==1) ? true : false;
	}
}
