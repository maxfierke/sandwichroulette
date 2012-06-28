package com.maxfierke.sandwichroulette;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class SandwichRouletteAbout extends TabActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);

		Resources resources = getResources(); 
		TabHost tabHost = getTabHost(); 

		// About the Application
		Intent intentAboutApp = new Intent().setClass(this, SandwichRouletteAboutApp.class);
		TabSpec tabSpecAboutApp = tabHost
				.newTabSpec("About")
				.setIndicator("About", resources.getDrawable(android.R.drawable.ic_menu_help))
				.setContent(intentAboutApp);

		// About the Creation of the Application
		Intent intentAboutIdea = new Intent().setClass(this, SandwichRouletteAboutIdea.class);
		TabSpec tabSpecAboutIdea = tabHost
				.newTabSpec("Idea")
				.setIndicator("Idea", resources.getDrawable(android.R.drawable.ic_menu_info_details))
				.setContent(intentAboutIdea);

		// About the License of the Application
		Intent intentAboutLicense = new Intent().setClass(this, SandwichRouletteAboutLicense.class);
		TabSpec tabSpecAboutLicense = tabHost
				.newTabSpec("License")
				.setIndicator("License", resources.getDrawable(R.drawable.ic_copyleft))
				.setContent(intentAboutLicense);

		tabHost.addTab(tabSpecAboutApp);
		tabHost.addTab(tabSpecAboutIdea);
		tabHost.addTab(tabSpecAboutLicense);

		tabHost.setCurrentTab(0);
	}
}
