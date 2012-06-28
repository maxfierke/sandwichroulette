package com.maxfierke.sandwichroulette;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceActivity;

public class SandwichRouletteSettings extends PreferenceActivity implements OnSharedPreferenceChangeListener{
	/** Called when the activity is first created. */
	private CheckBoxPreference[] ingredPrefs = new CheckBoxPreference[SandwichRoulette.id.getIngredients().length];
	private CheckBoxPreference[] saucePrefs = new CheckBoxPreference[SandwichRoulette.id.getSauces().length];
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		for(int i = 0; i < SandwichRoulette.id.getIngredients().length; i++)
		{
			ingredPrefs[i] = (CheckBoxPreference)findPreference(SandwichRoulette.id.getIng(i).getText().toString());
		}
		for(int i = 0; i < SandwichRoulette.id.getSauces().length; i++)
		{
			saucePrefs[i] = (CheckBoxPreference)findPreference(SandwichRoulette.id.getSauce(i).getText().toString());
		}
		getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener((OnSharedPreferenceChangeListener) this);
	}
	@Override
	public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
		prefs = getSharedPreferences(SandwichRoulette.prefFile,0);
		SharedPreferences.Editor editor = prefs.edit();

		CheckBoxPreference baseRandom = (CheckBoxPreference)findPreference("baseRandom");
		editor.putBoolean(baseRandom.getKey(), baseRandom.isChecked());

		CheckBoxPreference breadRandom = (CheckBoxPreference)findPreference("breadRandom");
		editor.putBoolean(breadRandom.getKey(), breadRandom.isChecked());

		CheckBoxPreference cheeseRandom = (CheckBoxPreference)findPreference("cheeseRandom");
		editor.putBoolean(cheeseRandom.getKey(), cheeseRandom.isChecked());
		
		CheckBoxPreference shakeRandom = (CheckBoxPreference)findPreference("shakeRandom");
		editor.putBoolean(shakeRandom.getKey(), cheeseRandom.isChecked());

		for(int i = 0; i < ingredPrefs.length; i++) {
			editor.putBoolean(ingredPrefs[i].getKey(), ingredPrefs[i].isChecked());
		}

		for(int i = 0; i < saucePrefs.length; i++) {
			editor.putBoolean(saucePrefs[i].getKey(), saucePrefs[i].isChecked());
		}
		editor.commit();
	}
}
