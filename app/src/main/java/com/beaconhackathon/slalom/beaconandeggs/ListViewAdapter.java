package com.beaconhackathon.slalom.beaconandeggs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.beaconhackathon.slalom.beaconandeggs.Models.GroceryCart;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;

/**
 * Created by httpnick on 10/15/15.
 * List view adapter to display main page grocery list.
 */
public class ListViewAdapter extends BaseSwipeAdapter {
    private Context mContext;
    private GroceryCart _gc;

    public ListViewAdapter(Context mContext, GroceryCart gc) {
        this.mContext = mContext;
        _gc = gc;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(final int position, final ViewGroup parent) {
        final View v = LayoutInflater.from(mContext).inflate(R.layout.listview_item, null);
        final SwipeLayout swipeLayout = (SwipeLayout)v.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                YoYo
                        .with(Techniques.Tada)
                        .duration(500)
                        .delay(100)
                        .playOn(layout.findViewById(R.id.trash));
            }
        });
        swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout, boolean surface) {
                Toast.makeText(
                        mContext,
                        "DoubleClick",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
        v.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeShownLayouts(swipeLayout);
                _gc.removeAtIndex(position);
                notifyDataSetChanged();
                closeAllItems();
                Toast.makeText(
                        mContext,
                        "click delete",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
        return v;
    }

    @Override
    public void fillValues(int position, View convertView) {
        TextView t = (TextView)convertView.findViewById(R.id.text_data);
        t.setText(_gc.getItemByIndex(position).name);
    }

    @Override
    public int getCount() {
        return _gc.getItemListLength();
    }

    @Override
    public Object getItem(int position) {
        return _gc.getItemByIndex(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
