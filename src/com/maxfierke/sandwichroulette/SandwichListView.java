package com.maxfierke.sandwichroulette;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class SandwichListView extends ListActivity {
	private SandwichDataSource dataSrc;
	protected ArrayAdapter<SandwichDataModel> adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);

		dataSrc = new SandwichDataSource(this);
		dataSrc.openSafe();

		List<SandwichDataModel> values = dataSrc.getAllSandwiches();

		// Use the SimpleCursorAdapter to show the
		// elements in a ListView
		ArrayAdapter<SandwichDataModel> adapter = new ArrayAdapter<SandwichDataModel>(this,
				android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);

		ListView lv = getListView();
		lv.setTextFilterEnabled(true);

		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent resultIntent = getIntent();
				resultIntent.putExtra("sandwich", (SandwichDataModel)parent.getAdapter().getItem(position));
				setResult(ListActivity.RESULT_OK, resultIntent);
				finish();
			}
		});
	}

	@Override
	protected void onResume() {
		dataSrc.openSafe();
		super.onResume();
	}

	@Override
	protected void onPause() {
		dataSrc.close();
		super.onPause();
	}
}
