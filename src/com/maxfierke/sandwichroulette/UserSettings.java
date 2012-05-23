package com.maxfierke.sandwichroulette;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceActivity;

public class UserSettings extends PreferenceActivity implements OnSharedPreferenceChangeListener{
	/** Called when the activity is first created. */
	private CheckBoxPreference[] ingredPrefs = new CheckBoxPreference[SandwichRoulette.ingredients.length];
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    addPreferencesFromResource(R.xml.preferences);
	    for(int i = 0; i < ingredPrefs.length; i++)
	    {
	    	ingredPrefs[i] = (CheckBoxPreference)findPreference(SandwichRoulette.ingredients[i].getText().toString());
	    }
	    getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener((OnSharedPreferenceChangeListener) this);
	}
	@Override
	public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
		prefs = getSharedPreferences(SandwichRoulette.prefFile,0);
		SharedPreferences.Editor editor = prefs.edit();
		CheckBoxPreference breadRandom = (CheckBoxPreference)findPreference("breadRandom");
		editor.putBoolean(breadRandom.getKey(), breadRandom.isChecked());
		for(int i = 0; i < ingredPrefs.length; i++) {
			editor.putBoolean(ingredPrefs[i].getKey(), ingredPrefs[i].isChecked());
		}
		editor.commit();
    }
}
