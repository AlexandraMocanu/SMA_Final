package com.alexandra.sma_final.activities;

import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.alexandra.sma_final.fragments.ModifyCitiesFragment;
import com.alexandra.sma_final.MyApplication;
import com.alexandra.sma_final.R;
import com.alexandra.sma_final.customviews.MontserratTextView;

import com.alexandra.sma_final.services.GPSTracker;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SettingsActivity extends BaseActivity {

    protected String activityName = "Settings";

    private static final String CITIES_FRAGMENT_TAG = "cities_fragment";

    private MontserratTextView currentLocation;
//    private MontserratTextView favoriteCities;
    private Button modifyCities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setListeners();
        mActivity.setText(getActivityName());
        bottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_SELECTED);
        bottomNavigationView.getMenu().setGroupCheckable(0, true, true);
        bottomNavigationView.getMenu().findItem(R.id.action_settings).setChecked(true);

        this.currentLocation = (MontserratTextView) findViewById(R.id.currentLocation);
//        this.favoriteCities = (MontserratTextView) findViewById(R.id.favorite_cities);
        this.modifyCities = (Button) findViewById(R.id.modify_cities);
        modifyCities.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "font/Montserrat-Regular.ttf"));

//        this.currentLocation.setText("Current city: " + MyApplication.city);
        this.currentLocation.setText("Current city: " + getCity());
//        setFavoriteCities();

        this.modifyCities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment f = getSupportFragmentManager().findFragmentByTag(CITIES_FRAGMENT_TAG);
                if (f != null) {
                    getSupportFragmentManager().popBackStack();
                } else {
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.slide_up,
                                    R.anim.slide_down,
                                    R.anim.slide_up,
                                    R.anim.slide_down)
                            .add(R.id.modify_cities_fragment,
                                    ModifyCitiesFragment
                                            .instantiate(getBaseContext(), ModifyCitiesFragment.class.getName()),
                                    CITIES_FRAGMENT_TAG
                            ).addToBackStack(null).commit();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

//    private void setFavoriteCities(){
//        ArrayList<City> favorites = new ArrayList<City>();
//        try(Realm realm = Realm.getDefaultInstance()) {
//            realm.executeTransaction(inRealm -> {
//                final RealmResults<City> cities  = realm.where(City.class).findAll();
//                for(City c : cities){
//                    favorites.add(c);
//                }
//            });
//        }
//
//        if(favorites.size() > 0){
//            String str1 = "Favorite cities: ";
//            for(int i = 0; i < favorites.size() - 1; i++){
//                str1 = str1 + favorites.get(i).getName();
//                str1 = str1 + ", ";
//            }
//            str1 = str1 + favorites.get(favorites.size() - 1).getName() +  ".";
//
//            SpannableStringBuilder str = new SpannableStringBuilder(str1);
//            int INT_START1 = 0; int INT_END1 = 16;
//            str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD),
//                    INT_START1, INT_END1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//            this.favoriteCities.setText(str);
//        }else{
//            SpannableStringBuilder str = new SpannableStringBuilder("No favorite cities. ");
//            int INT_START1 = 0; int INT_END1 = 18;
//            str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD),
//                    INT_START1, INT_END1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            this.favoriteCities.setText(str);
//        }
//
//    }

    private String getCity(){
        String city = "No city";
        GPSTracker gps = new GPSTracker(this);
        Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
        List<Address> address = null;
        try {
            address = geoCoder.getFromLocation(gps.latitude, gps.longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (address.size() > 0) {
            city = address.get(0).getLocality();
        }

        return city;
    }

    @Override
    public String getActivityName() {
        return activityName;
    }
}
