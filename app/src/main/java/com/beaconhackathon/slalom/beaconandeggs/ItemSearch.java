package com.beaconhackathon.slalom.beaconandeggs;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.SearchView;
import com.beaconhackathon.slalom.beaconandeggs.Models.Categories;
import com.beaconhackathon.slalom.beaconandeggs.Models.Category;
import com.beaconhackathon.slalom.beaconandeggs.Models.Item;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ItemSearch extends ListActivity {

    private List<Item> itemList;
    Context context = ItemSearch.this;

    private BaseAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_search);

        if(itemList == null) {
            getItems();
        }

        //See if the query string was added to the intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String query = extras.getString("query");
            searchItems(query);
        } else {
            adapter = new ItemViewAdapter(context, itemList);

            setListAdapter(adapter);
        }
    }

    private void searchItems(String query) {
        query = query.toLowerCase();
        List<Item> results = new ArrayList<>();
        for(Item item: itemList) {
            String name = item.name.toLowerCase();
            if (name.contains(query)) {
                results.add(item);
            }
        }

        adapter = new ItemViewAdapter(context, results);

        setListAdapter(adapter);
    }

    public void returnAddedItem(View view) {
        int position = getListView().getPositionForView((LinearLayout) view.getParent());
        Item item = (Item)adapter.getItem(position);
        Intent myintent = new Intent(ItemSearch.this, BeaconAndEggs.class).putExtra("item", item);
        startActivity(myintent);
    }

    private void getItems() {
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try (InputStream is = getResources().openRawResource(R.raw.data)) {
            Reader reader = new BufferedReader(new InputStreamReader(is));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (IOException e) {
            Log.e("Error", e.getMessage());
        }

        String jsonString = writer.toString();
        Categories categories = convertToJson(jsonString);
        setInitialItems(categories);
    }

    private void setInitialItems(Categories categories) {
        itemList = new ArrayList<>();
        for (Category category: categories.categories) {
            itemList.addAll(category.items);
        }
    }

    private Categories convertToJson(String jsonString) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson(
                jsonString,
                Categories.class
        );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item_search, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
