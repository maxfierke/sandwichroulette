package com.maxfierke.sandwichroulette;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class SandwichRoulette extends Activity {
    protected static final int SAMMICH_TIEM = 2922;
	SharedPreferences preferences;
	public static final String prefFile = "swRoulettePrefs";
	Editor edit;
	AlertDialog.Builder builder;
    private Random rnd;
    private EditText sammichName;
    private Spinner baseSpinner;
    private Spinner breadSpinner;
    private Spinner cheeseSpinner;
    private ArrayAdapter<CharSequence> baseArray;
    private ArrayAdapter<CharSequence> breadArray;
    private ArrayAdapter<CharSequence> cheeseArray;
    public static CheckBox[] ingredients;
	private boolean allowBreadRandom;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        sammichName = (EditText) findViewById(R.id.sammichNameField);
        baseSpinner = (Spinner) findViewById(R.id.premadeSelect);
        baseArray = ArrayAdapter.createFromResource(this, R.array.base_array, android.R.layout.simple_spinner_item);
        baseArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        baseSpinner.setAdapter(baseArray);
        
        breadSpinner = (Spinner) findViewById(R.id.breadSelect);
        breadArray = ArrayAdapter.createFromResource(this, R.array.bread_array, android.R.layout.simple_spinner_item);
        breadArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        breadSpinner.setAdapter(breadArray);
        
        cheeseSpinner = (Spinner) findViewById(R.id.cheeseSelect);
        cheeseArray = ArrayAdapter.createFromResource(this, R.array.cheese_array, android.R.layout.simple_spinner_item);
        cheeseArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cheeseSpinner.setAdapter(cheeseArray);
        
        ingredients = new CheckBox[]{
    			(CheckBox)findViewById(R.id.picklesBox),
    			(CheckBox)findViewById(R.id.onionsBox),
    			(CheckBox)findViewById(R.id.lettuceBox),
    			(CheckBox)findViewById(R.id.tomatoBox),
    			(CheckBox)findViewById(R.id.grnPepBox),
    			(CheckBox)findViewById(R.id.spinachBox),
    			(CheckBox)findViewById(R.id.cucumberBox),
    			(CheckBox)findViewById(R.id.bananaPepBox),
    			(CheckBox)findViewById(R.id.oliveBox),
    			(CheckBox)findViewById(R.id.jalapenoBox),
    			(CheckBox)findViewById(R.id.chipotleBox),
    			(CheckBox)findViewById(R.id.honeyMustardBox),
    			(CheckBox)findViewById(R.id.sweetOnionBox),
    			(CheckBox)findViewById(R.id.mayoBox),
    			(CheckBox)findViewById(R.id.mustardBox),
    			(CheckBox)findViewById(R.id.oilBox),
    			(CheckBox)findViewById(R.id.vinaigretteBox),
    			(CheckBox)findViewById(R.id.vinegarBox),
    			(CheckBox)findViewById(R.id.italianDrsngBox),
    			(CheckBox)findViewById(R.id.ranchBox)
    	};
        preferences = getSharedPreferences(prefFile, 0);
        allowBreadRandom = preferences.getBoolean("breadRandom", true);
        for(int i = 0; i < ingredients.length; i++) {
        	ingredients[i].setTag(ingredients[i].getText());
        	if(preferences.getBoolean(ingredients[i].getText().toString(), true)) {
    			ingredients[i].setEnabled(true);
    		} else {
    			ingredients[i].setChecked(false);
    			ingredients[i].setEnabled(false);
    		}
        }
    	builder = new AlertDialog.Builder(this);
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
            if(requestCode == SAMMICH_TIEM){
            	if(resultCode == RESULT_OK) {
	            	String file = data.getStringExtra("filename");
	        		File root = Environment.getExternalStorageDirectory();
	            	try {
	    				openSammich(new File(root + "/sandwiches/" + file));
	    			} catch (FileNotFoundException e1) {
	    				e1.printStackTrace();
	    				builder.setTitle("Sandwich Not Found");
	    				builder.setMessage("The sandwich in question could not be read for some reason.");
	    				builder.setCancelable(false);
	         	        builder.setNeutralButton("Okay", new DialogInterface.OnClickListener() {
	         	           public void onClick(DialogInterface dialog, int id) {
	         	                dialog.dismiss();
	         	           }
	         	        });
	         	        AlertDialog alert = builder.create();
	         	        alert.show();
	    			} catch (IOException e) {
	    				e.printStackTrace();
	    			}
            	}
    		}
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	// Handle item selection
        switch (item.getItemId()) {
        case R.id.opnSwItem:
        	File root = Environment.getExternalStorageDirectory();
        	if(listFileNames(new File(root + "/sandwiches/")).length > 0) {
	        	Intent i = new Intent(SandwichRoulette.this, FileListView.class);
	        	// We use SAMMICH_TIEM as an 'identifier'
	        	startActivityForResult(i, SAMMICH_TIEM);
        	} else {
        		builder.setTitle("No Sandwiches Found");
        		builder.setMessage("No sandwiches were found! :(");
        		builder.setCancelable(false);
     	        builder.setNeutralButton("Okay", new DialogInterface.OnClickListener() {
     	           public void onClick(DialogInterface dialog, int id) {
     	                dialog.dismiss();
     	           }
     	        });
     	        AlertDialog alert = builder.create();
     	        alert.show();
        	}
        	return true;
        case R.id.saveSwItem:
        	try {
				saveSammich();
				builder.setTitle("Sandwich Saved!");
				builder.setMessage(sammichName.getText().toString() + " was saved to the SD card.");
				builder.setCancelable(false);
     	        builder.setNeutralButton("Okay", new DialogInterface.OnClickListener() {
     	           public void onClick(DialogInterface dialog, int id) {
     	                dialog.dismiss();
     	           }
     	        });
     	        AlertDialog alert = builder.create();
     	        alert.show();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				builder.setTitle("Unsupported Encoding!");
				builder.setMessage("The file could not be encoded. Check to make sure that your device supports the UTF-8 charset.");
				builder.setCancelable(false);
     	        builder.setNeutralButton("Okay", new DialogInterface.OnClickListener() {
     	           public void onClick(DialogInterface dialog, int id) {
     	                dialog.dismiss();
     	           }
     	        });
     	        AlertDialog alert = builder.create();
     	        alert.show();
			} catch (IOException e) {
				e.printStackTrace();
				builder.setTitle("File Write Error!");
				builder.setMessage("The file could not be written. Check to make sure that your SD card is fully inserted and mounted.");
				builder.setCancelable(false);
     	        builder.setNeutralButton("Okay", new DialogInterface.OnClickListener() {
     	           public void onClick(DialogInterface dialog, int id) {
     	                dialog.dismiss();
     	           }
     	        });
     	        AlertDialog alert = builder.create();
     	        alert.show();
			}
        	return true;
        case R.id.rndSwItem:
        	generateRandomSammich(System.currentTimeMillis());
        	return true;
        case R.id.aboutItem:
        	builder.setTitle(R.string.aboutTitleStr)
        			.setMessage(this.getResources().getText(R.string.aboutTextStr).toString())
        	       .setCancelable(false)
        	       .setNeutralButton("Okay", new DialogInterface.OnClickListener() {
        	           public void onClick(DialogInterface dialog, int id) {
        	                dialog.dismiss();
        	           }
        	       });
        	AlertDialog alert = builder.create();
        	alert.show();
        	return true;
        case R.id.prefItem:
        	startActivity(new Intent(SandwichRoulette.this, UserSettings.class));
        	return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    public void generateRandomSammich(long seed) {
    	rnd = new Random(seed);
    	sammichName.setText("");
    	baseSpinner.setSelection(rnd.nextInt(this.getResources().getStringArray(R.array.base_array).length));
    	if(allowBreadRandom) breadSpinner.setSelection(rnd.nextInt(this.getResources().getStringArray(R.array.bread_array).length));
    	cheeseSpinner.setSelection(rnd.nextInt(this.getResources().getStringArray(R.array.cheese_array).length));
    	for(int i = 0; i < ingredients.length; i++)
    	{
    		if(preferences.getBoolean(ingredients[i].getText().toString(), true)) {
    			ingredients[i].setEnabled(true);
    			ingredients[i].setChecked(rnd.nextBoolean());
    		} else {
    			ingredients[i].setChecked(false);
    			ingredients[i].setEnabled(false);
    		}
    	}
    }
    public boolean isSDReady() {
    	String state = Environment.getExternalStorageState();
    	if (Environment.MEDIA_MOUNTED.equals(state)) {
    	    return true;
    	} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
    	    return false;
    	} else {
    	    return false;
    	}
    }
    public static String[] listFileNames(File dir) {
        Collection<String> files  =new ArrayList<String>();
        if(dir.isDirectory()){
            File[] listFiles = dir.listFiles();
            for(File file : listFiles){
                if(file.isFile()) {
                    files.add(file.getName().substring(0, file.getName().length()-7));
                }
            }
        }
        return files.toArray(new String[]{});
    }
    public void openSammich(File filename) throws IOException {
    	if(isSDReady()) {
            BufferedReader scan = new BufferedReader(new FileReader(filename));
            String input, var, val;
            while(scan.ready())
            {
                input = scan.readLine();
                if(input.equals("sndwchRltt1.0")) continue;
                var = input.substring(0, input.indexOf("=")).toLowerCase();
                val = input.substring(input.indexOf("=") + 1);
                if(var.equals("name")) {
                	sammichName.setText(val);
                } else if(var.equals("base")) {
                	baseSpinner.setSelection(baseArray.getPosition(val));
                } else if(var.equals("bread")) {
                	breadSpinner.setSelection(breadArray.getPosition(val));
                } else if(var.equals("cheese")) {
                	cheeseSpinner.setSelection(cheeseArray.getPosition(val));
                } else {
                	for(int i = 0; i < ingredients.length; i++)
                	{
                		if(ingredients[i].getText().toString().equalsIgnoreCase(var)) {
                			if(ingredients[i].isEnabled()) {
                				ingredients[i].setChecked(Boolean.parseBoolean(val));
                			}
                		}
                	}
                }
            }
    	}
    }
    public void saveSammich() throws UnsupportedEncodingException, IOException {
    	if(isSDReady()) {
    		File root = Environment.getExternalStorageDirectory();
    		File saveFile = new File(root + "/sandwiches/");
    		if(root.canWrite()) {
    			saveFile.mkdirs();
    			saveFile = new File(root + "/sandwiches/"+(sammichName.getText().toString())+".sndwch");
    			saveFile.createNewFile();
    			FileOutputStream fos = new FileOutputStream(root + "/sandwiches/"+sammichName.getText().toString()+".sndwch");
    			fos.write("sndwchRltt1.0\n".getBytes("UTF-8"));
    			fos.write(("name=" + sammichName.getText().toString() + "\n").getBytes("UTF-8"));
    			fos.write(("base=" + baseArray.getItem(baseSpinner.getSelectedItemPosition()).toString() +"\n").getBytes("UTF-8"));
    			fos.write(("bread=" + breadArray.getItem(breadSpinner.getSelectedItemPosition()).toString()+"\n").getBytes("UTF-8"));
    			fos.write(("cheese=" + cheeseArray.getItem(cheeseSpinner.getSelectedItemPosition()).toString()+"\n").getBytes("UTF-8"));
    			for(int j = 0; j < ingredients.length; j++)
    			{
    				fos.write((ingredients[j].getText().toString() + "=" + Boolean.toString(ingredients[j].isChecked()) + "\n").getBytes("UTF-8"));
    			}
    		}
    	}
    }
}