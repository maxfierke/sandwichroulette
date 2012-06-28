package com.maxfierke.sandwichroulette;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import com.commonsware.android.shaker.Shaker;

public class SandwichRoulette extends Activity implements Shaker.Callback {
	protected static final int OPEN_SAMMICH_TIEM = 0xB6A;
	protected static final int DEL_SAMMICH_TIEM = 0xB6B;
	SharedPreferences preferences;
	public static final String prefFile = "swRoulettePrefs";
	Editor edit;
	AlertDialog alert;
	AlertDialog.Builder builder;
	private Random rnd;
	private EditText sammichName;
	private Spinner baseSpinner;
	private Spinner breadSpinner;
	private Spinner cheeseSpinner;
	private ArrayAdapter<CharSequence> baseArray;
	private ArrayAdapter<CharSequence> breadArray;
	private ArrayAdapter<CharSequence> cheeseArray;
	private boolean allowBaseRandom;
	private boolean allowBreadRandom;
	private boolean allowCheeseRandom;
	private boolean allowShake;
	SandwichDataSource dataSrc;
	static SandwichIngredientData id;
	protected Dialog mSplashDialog;
	private Shaker shaker = null;

	/* Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
				
		SandwichRouletteStateSaver data = (SandwichRouletteStateSaver) getLastNonConfigurationInstance();
		
		if (data != null) {
	        // Show splash screen if still loading
	        if (data.showSplashScreen) {
	            showSplashScreen();
	        }
	        setContentView(R.layout.main);	 
	        // Rebuild your UI with your saved state here
	    } else {
	        showSplashScreen();
	        setContentView(R.layout.main);
	        // Do your heavy loading here on a background thread
	    }
		
		id = new SandwichIngredientData(this, this.findViewById(android.R.id.content));
		dataSrc = new SandwichDataSource(this);

		sammichName = (EditText) findViewById(R.id.sammichNameField);
		baseSpinner = (Spinner) findViewById(R.id.baseSelect);
		baseArray = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, id.getBaseArray());
		baseArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		baseSpinner.setAdapter(baseArray);

		breadSpinner = (Spinner) findViewById(R.id.breadSelect);
		breadArray = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, id.getBreadArray());
		breadArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		breadSpinner.setAdapter(breadArray);

		cheeseSpinner = (Spinner) findViewById(R.id.cheeseSelect);
		cheeseArray = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, id.getCheeseArray());
		cheeseArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		cheeseSpinner.setAdapter(cheeseArray);
		
		updateStateFromPrefs();
		
		if(allowShake) shaker=new Shaker(this, 1.25d, 500, this);
		else shaker = null;
	}
	@Override
	protected void onResume() {
		super.onResume();
		updateStateFromPrefs();
		if(allowShake) shaker = new Shaker(this, 1.25d, 500, this);
		else shaker = null;
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(shaker!=null) shaker.close();
	}
	@Override
	protected void onPause() {
		super.onPause();
		if(shaker!=null) shaker.close();
	}
	@Override
	public Object onRetainNonConfigurationInstance() {
	    SandwichRouletteStateSaver data = new SandwichRouletteStateSaver();
	    // Save your important data here
	 
	    if (mSplashDialog != null) {
	        data.showSplashScreen = true;
	        removeSplashScreen();
	    }
	    return data;
	}
	private void updateStateFromPrefs()
	{
		preferences = getSharedPreferences(prefFile, 0);
		allowBaseRandom = preferences.getBoolean("baseRandom", true);
		allowBreadRandom = preferences.getBoolean("breadRandom", true);
		allowCheeseRandom = preferences.getBoolean("cheeseRandom", true);
		allowShake = preferences.getBoolean("allowShake", true);
		for(int i = 0; i < id.getIngredients().length; i++) {
			id.getIng(i).setTag(id.getIng(i).getText());
			if(preferences.getBoolean(id.getIng(i).getText().toString(), true)) {
				id.getIng(i).setEnabled(true);
			} else {
				id.getIng(i).setChecked(false);
				id.getIng(i).setEnabled(false);
			}
		}
		for(int i = 0; i < id.getSauces().length; i++) {
			id.getSauce(i).setTag(id.getSauce(i).getText());
			if(preferences.getBoolean(id.getSauce(i).getText().toString(), true)) {
				id.getSauce(i).setEnabled(true);
			} else {
				id.getSauce(i).setChecked(false);
				id.getSauce(i).setEnabled(false);
			}
		}
	}
	
	/**
	 * Removes the Dialog that displays the splash screen
	 */
	protected void removeSplashScreen() {
	    if (mSplashDialog != null) {
	        mSplashDialog.dismiss();
	        mSplashDialog = null;
	    }
	}
	 
	/**
	 * Shows the splash screen over the full Activity
	 */
	protected void showSplashScreen() {
	    mSplashDialog = new Dialog(this, R.style.splash);
	    mSplashDialog.setContentView(R.layout.splash);
	    mSplashDialog.setCancelable(false);
	    mSplashDialog.show();
	     
	    // Set Runnable to remove splash screen just in case
	    final Handler handler = new Handler();
	    handler.postDelayed(new Runnable() {
	      @Override
	      public void run() {
	        removeSplashScreen();
	      }
	    }, 5000);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.app_menu, menu);
		return true;
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == OPEN_SAMMICH_TIEM){
			if(resultCode == RESULT_OK) {
				SandwichDataModel sandwich = data.getParcelableExtra("sandwich");
				openSammich(sandwich);
			}
		} else if(requestCode == DEL_SAMMICH_TIEM){
			if(resultCode == RESULT_OK) {
				final SandwichDataModel sandwich = data.getParcelableExtra("sandwich");
				builder.setTitle("Delete Sandwich?");
				builder.setMessage("Are you sure you wish to delete " + sandwich.toString()+"\n\nYou cannot undo this.");
				builder.setCancelable(true);
				builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();
						dataSrc.open();
						SandwichRoulette.this.delSammich(sandwich);
						dataSrc.close();
						notifyDialog("Sandwich Deleted!", "The sandwich was removed from existence.");
					}
				});
				builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();
					}
				});
				alert = builder.create();
				alert.show();
			}
		}
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		builder = new AlertDialog.Builder(this);
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.opnSwItem:
			if(dataSrc.getDBSize() > 0) {
				Intent i = new Intent(SandwichRoulette.this, SandwichListView.class);
				// We use OPEN_SAMMICH_TIEM as an 'identifier'
				startActivityForResult(i, OPEN_SAMMICH_TIEM);
			} else {
				notifyDialog("No Sandwiches Found","By the Earl of Sandwich's beard! There are no sandwiches here!");
			}
			return true;
		case R.id.saveSwItem:
			if(!isBlank(sammichName.getText().toString())) {
				dataSrc.openSafe();
				if(dataSrc.getSandwich(sammichName.getText().toString())!=null)
				{
					builder.setTitle("Overwrite Sandwich?");
					builder.setMessage(sammichName.getText().toString() + " already exists! Overwrite?");
					builder.setCancelable(true);
					builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.dismiss();
							dataSrc.open();
							SandwichRoulette.this.saveSammich(dataSrc.getSandwich(sammichName.getText().toString()));
							dataSrc.close();
							notifyDialog("Sandwich Saved!", sammichName.getText().toString() + " was saved.");
						}
					});
					builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.dismiss();
						}
					});
					alert = builder.create();
					alert.show();
				} else {
					dataSrc.open();
					saveSammich(null);
					dataSrc.close();
					notifyDialog("Sandwich Saved!", sammichName.getText().toString() + " was saved.");
				}
			} else {
				notifyDialog("Save Error!", "Your delicious sandwich creation must have a name!");
			}
			dataSrc.close();
			return true;
		case R.id.rmSwItem:
			if(dataSrc.getDBSize() > 0) {
				Intent i = new Intent(SandwichRoulette.this, SandwichListView.class);
				// We use DEL_SAMMICH_TIEM as an 'identifier'
				startActivityForResult(i, DEL_SAMMICH_TIEM);
			} else {
				notifyDialog("No Sandwiches Found","By the Earl of Sandwich's beard! There are no sandwiches here!");
			}
			return true;
		case R.id.rndSwItem:
			generateRandomSammich();
			return true;
		case R.id.aboutItem:
			startActivity(new Intent(SandwichRoulette.this, SandwichRouletteAbout.class));
			return true;
		case R.id.prefItem:
			startActivity(new Intent(SandwichRoulette.this, SandwichRouletteSettings.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void notifyDialog(String title, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setCancelable(false);
		builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.dismiss();
			}
		});
		alert = builder.create();
		alert.show();
	}

	public void generateRandomSammich() {
		try {
			rnd = SecureRandom.getInstance("SHA1PRNG");
			rnd.setSeed(System.currentTimeMillis());
		} catch (NoSuchAlgorithmException e) {
			notifyDialog("Error!", "Your device is not capable of randomizing sandwiches with the power of a thousand suns. Falling back to the legacy randomizer.");
			rnd = new Random(System.currentTimeMillis());
		}
		sammichName.setText("A Deliciously Random Sandwich");
		if(allowBaseRandom) baseSpinner.setSelection(rnd.nextInt(this.getResources().getStringArray(R.array.base_array).length));
		if(allowBreadRandom) breadSpinner.setSelection(rnd.nextInt(this.getResources().getStringArray(R.array.bread_array).length));
		if(allowCheeseRandom) cheeseSpinner.setSelection(rnd.nextInt(this.getResources().getStringArray(R.array.cheese_array).length));
		for(int i = 0; i < id.getIngredients().length; i++)
		{
			if(preferences.getBoolean(id.getIng(i).getText().toString(), true)) {
				id.getIng(i).setEnabled(true);
				id.getIng(i).setChecked(rnd.nextBoolean());
			} else {
				id.getIng(i).setChecked(false);
				id.getIng(i).setEnabled(false);
			}
		}
		for(int i = 0; i < id.getSauces().length; i++)
		{
			if(preferences.getBoolean(id.getSauce(i).getText().toString(), true)) {
				id.getSauce(i).setEnabled(true);
				id.getSauce(i).setChecked(rnd.nextBoolean());
			} else {
				id.getSauce(i).setChecked(false);
				id.getSauce(i).setEnabled(false);
			}
		}
	}

	public void openSammich(SandwichDataModel sandwich) {
		sammichName.setText(sandwich.getTitle());
		baseSpinner.setSelection(sandwich.getBase());
		breadSpinner.setSelection(sandwich.getBread());
		cheeseSpinner.setSelection(sandwich.getCheese());
		// Load ingredients
		if(id.getIng(id.IN_PICKLES).isEnabled()) {
			id.getIng(id.IN_PICKLES).setChecked(sandwich.isPickles());
		}
		if(id.getIng(id.IN_ONIONS).isEnabled()) {
			id.getIng(id.IN_ONIONS).setChecked(sandwich.isOnions());
		}
		if(id.getIng(id.IN_LETTUCE).isEnabled()) {
			id.getIng(id.IN_LETTUCE).setChecked(sandwich.isLettuce());
		}
		if(id.getIng(id.IN_TOMATO).isEnabled()) {
			id.getIng(id.IN_TOMATO).setChecked(sandwich.isTomato());
		}
		if(id.getIng(id.IN_GRNPEP).isEnabled()) {
			id.getIng(id.IN_GRNPEP).setChecked(sandwich.isGrnPepper());
		}
		if(id.getIng(id.IN_SPINACH).isEnabled()) {
			id.getIng(id.IN_SPINACH).setChecked(sandwich.isSpinach());
		}
		if(id.getIng(id.IN_CUCUMBER).isEnabled()) {
			id.getIng(id.IN_CUCUMBER).setChecked(sandwich.isCucumber());
		}
		if(id.getIng(id.IN_BANPEP).isEnabled()) {
			id.getIng(id.IN_BANPEP).setChecked(sandwich.isBanPepper());
		}
		if(id.getIng(id.IN_OLIVE).isEnabled()) {
			id.getIng(id.IN_OLIVE).setChecked(sandwich.isOlive());
		}
		if(id.getIng(id.IN_JALAPENO).isEnabled()) {
			id.getIng(id.IN_JALAPENO).setChecked(sandwich.isJalapeno());
		}
		// Load Sauces
		if(id.getSauce(id.SC_CHIPOTLE).isEnabled()) {
			id.getSauce(id.SC_CHIPOTLE).setChecked(sandwich.isChipotle());
		}
		if(id.getSauce(id.SC_HNYMUST).isEnabled()) {
			id.getSauce(id.SC_HNYMUST).setChecked(sandwich.isHnyMustard());
		}
		if(id.getSauce(id.SC_SWTONION).isEnabled()) {
			id.getSauce(id.SC_SWTONION).setChecked(sandwich.isSwtOnion());
		}
		if(id.getSauce(id.SC_MAYO).isEnabled()) {
			id.getSauce(id.SC_MAYO).setChecked(sandwich.isMayo());
		}
		if(id.getSauce(id.SC_MUST).isEnabled()) {
			id.getSauce(id.SC_MUST).setChecked(sandwich.isMustard());
		}
		if(id.getSauce(id.SC_VINAI).isEnabled()) {
			id.getSauce(id.SC_VINAI).setChecked(sandwich.isVinaigrette());
		}
		if(id.getSauce(id.SC_VINEGAR).isEnabled()) {
			id.getSauce(id.SC_VINEGAR).setChecked(sandwich.isVinegar());
		}
		if(id.getSauce(id.SC_ITALDRSNG).isEnabled()) {
			id.getSauce(id.SC_ITALDRSNG).setChecked(sandwich.isItalDressing());
		}
		if(id.getSauce(id.SC_RANCH).isEnabled()) {
			id.getSauce(id.SC_RANCH).setChecked(sandwich.isRanch());
		}
		if(id.getSauce(id.SC_CSRDRSNG).isEnabled()) {
			id.getSauce(id.SC_CSRDRSNG).setChecked(sandwich.isCaesarDressing());
		}
		if(id.getSauce(id.SC_HUMMUS).isEnabled()) {
			id.getSauce(id.SC_HUMMUS).setChecked(sandwich.isHummus());
		}
	}
	
	private void delSammich(SandwichDataModel currentSandwich) {
		dataSrc.open();
		if(currentSandwich != null) {
			dataSrc.deleteSandwich(currentSandwich);
		}
		dataSrc.close();
	}

	public void saveSammich(SandwichDataModel currentSandwich) {
		dataSrc.open();
		if(currentSandwich != null) {
			System.out.println("current sandwich id: "+currentSandwich.getId());
			dataSrc.updateSandwich(currentSandwich.getId(), uiToValues());
		} else {
			dataSrc.createSandwich(uiToValues());
		}
		dataSrc.close();
	}

	private ContentValues uiToValues() {
		ContentValues values = new ContentValues();

		/* DO NOT ALTER THE ORDER OF ANY ELEMENTS ALREADY PRESENT
		 * THEY CORRESPOND TO DATABASE COLUMNS
		 * Add to the end.
		 */
		values.put(SandwichDBHelper.COLUMN_TITLE, sammichName.getText().toString());
		values.put(SandwichDBHelper.COLUMN_BASE, baseSpinner.getSelectedItemPosition());
		values.put(SandwichDBHelper.COLUMN_BREAD, breadSpinner.getSelectedItemPosition());
		values.put(SandwichDBHelper.COLUMN_CHEESE, cheeseSpinner.getSelectedItemPosition());
		values.put(SandwichDBHelper.COLUMN_PICKLES, toSQLiteBool(id.getIng(id.IN_PICKLES).isChecked()));
		values.put(SandwichDBHelper.COLUMN_ONIONS, toSQLiteBool(id.getIng(id.IN_ONIONS).isChecked()));
		values.put(SandwichDBHelper.COLUMN_LETTUCE, toSQLiteBool(id.getIng(id.IN_LETTUCE).isChecked()));
		values.put(SandwichDBHelper.COLUMN_TOMATO, toSQLiteBool(id.getIng(id.IN_TOMATO).isChecked()));
		values.put(SandwichDBHelper.COLUMN_GRNPEPPER, toSQLiteBool(id.getIng(id.IN_GRNPEP).isChecked()));
		values.put(SandwichDBHelper.COLUMN_SPINACH, toSQLiteBool(id.getIng(id.IN_SPINACH).isChecked()));
		values.put(SandwichDBHelper.COLUMN_CUCUMBER, toSQLiteBool(id.getIng(id.IN_CUCUMBER).isChecked()));
		values.put(SandwichDBHelper.COLUMN_BANPEPPER, toSQLiteBool(id.getIng(id.IN_BANPEP).isChecked()));
		values.put(SandwichDBHelper.COLUMN_OLIVE, toSQLiteBool(id.getIng(id.IN_OLIVE).isChecked()));
		values.put(SandwichDBHelper.COLUMN_JALAPENO, toSQLiteBool(id.getIng(id.IN_JALAPENO).isChecked()));
		values.put(SandwichDBHelper.COLUMN_CHIPOTLE, toSQLiteBool(id.getSauce(id.SC_CHIPOTLE).isChecked()));
		values.put(SandwichDBHelper.COLUMN_HNYMUSTARD, toSQLiteBool(id.getSauce(id.SC_HNYMUST).isChecked()));
		values.put(SandwichDBHelper.COLUMN_SWTONION, toSQLiteBool(id.getSauce(id.SC_SWTONION).isChecked()));
		values.put(SandwichDBHelper.COLUMN_MAYO, toSQLiteBool(id.getSauce(id.SC_MAYO).isChecked()));
		values.put(SandwichDBHelper.COLUMN_MUSTARD, toSQLiteBool(id.getSauce(id.SC_MUST).isChecked()));
		values.put(SandwichDBHelper.COLUMN_OIL, toSQLiteBool(id.getSauce(id.SC_OIL).isChecked()));
		values.put(SandwichDBHelper.COLUMN_VINAIGRETTE, toSQLiteBool(id.getSauce(id.SC_VINAI).isChecked()));
		values.put(SandwichDBHelper.COLUMN_VINEGAR, toSQLiteBool(id.getSauce(id.SC_VINEGAR).isChecked()));
		values.put(SandwichDBHelper.COLUMN_ITALDRESSING, toSQLiteBool(id.getSauce(id.SC_ITALDRSNG).isChecked()));
		values.put(SandwichDBHelper.COLUMN_RANCH, toSQLiteBool(id.getSauce(id.SC_RANCH).isChecked()));
		values.put(SandwichDBHelper.COLUMN_CSRDRESSING, toSQLiteBool(id.getSauce(id.SC_CSRDRSNG).isChecked()));
		values.put(SandwichDBHelper.COLUMN_HUMMUS, toSQLiteBool(id.getSauce(id.SC_HUMMUS).isChecked()));
		return values;
	}

	private int toSQLiteBool(boolean value) {
		return (value) ? 1 : 0;
	}

	private boolean isBlank(String string)
	{
		return string == null || string.length()<=0 || string.trim().length()<=0;
	}
	
	private class SandwichRouletteStateSaver {
		public boolean showSplashScreen = false;
	}

	@Override
	public void shakingStarted() {
		generateRandomSammich();
	}
	@Override
	public void shakingStopped() {
		// TODO Auto-generated method stub
		
	}
}