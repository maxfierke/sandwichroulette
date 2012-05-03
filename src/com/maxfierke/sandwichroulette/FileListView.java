package com.maxfierke.sandwichroulette;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.io.File;

public class FileListView extends ListActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		  setListAdapter(new ArrayAdapter<String>(this, R.layout.listview, SandwichRoulette.listFileNames(new File(Environment.getExternalStorageDirectory() + "/sandwiches/"))));

		  ListView lv = getListView();
		  lv.setTextFilterEnabled(true);

		  lv.setOnItemClickListener(new OnItemClickListener() {
			  @Override
			  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				  Intent resultIntent = getIntent();
				  resultIntent.putExtra("filename", parent.getItemAtPosition(position).toString()+".sndwch");
				  setResult(ListActivity.RESULT_OK, resultIntent);
				  finish();
			  }
		  });
	}
}
