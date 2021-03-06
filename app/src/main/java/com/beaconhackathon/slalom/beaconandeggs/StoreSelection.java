package com.beaconhackathon.slalom.beaconandeggs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.overlay.Icon;
import com.mapbox.mapboxsdk.overlay.ItemizedIconOverlay;
import com.mapbox.mapboxsdk.overlay.Marker;
import com.mapbox.mapboxsdk.views.MapView;

import java.util.ArrayList;

/**
 * Created by ainsleyherndon on 10/20/15.
 */
public class StoreSelection  extends Activity {

    private MapView mv;
    private ItemizedIconOverlay itemizedIconOverlayGT;
    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.map_view);


        TextView tv = (TextView) findViewById(R.id.textView3);
        String htmlString="<u>Selec</u>t Your Store";
        tv.setText(Html.fromHtml(htmlString));

        mv = (MapView) findViewById(R.id.mapview);
        mv.setMinZoomLevel(mv.getTileProvider().getMinimumZoomLevel());
        mv.setMaxZoomLevel(mv.getTileProvider().getMaximumZoomLevel());
        mv.setCenter(new LatLng(47.616035, -122.309646));
        mv.setZoom(13);

        mv.setUserLocationEnabled(true);

        ArrayList<Marker> Markers = new ArrayList<Marker>();
        marker = new Marker(mv, "Safeway", " ", new LatLng(47.616035, -122.309646));
        marker.setIcon(new Icon(getApplicationContext().getResources()
                .getDrawable(R.drawable.shop_icon)));

        Marker marker2 = new Marker(mv, "Safeway", " ", new LatLng(47.601343, -122.333243));
        marker2.setIcon(new Icon(getApplicationContext().getResources()
                .getDrawable(R.drawable.shop_icon)));
        Markers.add(marker);
        Markers.add(marker2);
        itemizedIconOverlayGT = new ItemizedIconOverlay(this, Markers,
                new ItemizedIconOverlay.OnItemGestureListener<Marker>() {

                    @Override
                    public boolean onItemSingleTapUp(int index, Marker item) {
                        Intent intent = new Intent(StoreSelection.this, BeaconAndEggs.class);
                        startActivity(intent);
                        return true;
                    }

                    @Override
                    public boolean onItemLongPress(int index, Marker item) {
                        Intent intent = new Intent(StoreSelection.this, BeaconAndEggs.class);
                        startActivity(intent);
                        return true;
                    }
                });
        // marker
        mv.addMarker(marker);
        mv.addMarker(marker2);
        mv.addItemizedOverlay(itemizedIconOverlayGT);
    }

}
