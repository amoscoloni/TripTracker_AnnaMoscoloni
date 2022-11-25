package com.example.triptracker_annamoscoloni;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static TripRepository tripRepository;
    public static int idToModify; // not ideal at all but I couldnt get the bundle to work to pass params
    public static AddTrip addTripFragment;
    public static TripItemFragment tripItemFragment;
    public static TextView displayFilterValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tripRepository = new TripRepository(TripSampleData.getSampleTripData());
        displayFilterValue = this.findViewById(R.id.date_filter_value);

        tripItemFragment = new TripItemFragment();
        loadFragment(new TripItemFragment());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.addTrip_btn) {
            // new trip fragment
            loadAddFragmentWithData(AddTrip.ADD_MODE_ID);
            return true;
        }
        else if(id == R.id.home_btn){
            // home button fragment
            changeFilterVisibility(true);
            tripItemFragment = new TripItemFragment();
            loadFragment(tripItemFragment);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    private void changeFilterVisibility(boolean visible){
        View filter = (View) findViewById(R.id.filter_layout);

        if(visible){
            filter.setVisibility(View.VISIBLE);
        }
        else{
            filter.setVisibility(View.GONE);
        }

    }

    public void loadAddFragmentWithData(int tripId){
        // Bundle bundle = new Bundle();
        // bundle.putInt( getString(R.string.trip_id_add_fragment), tripId);
        changeFilterVisibility(false);


        idToModify = tripId;
        addTripFragment = new AddTrip();
        // addTrip.setArguments(bundle);

        loadFragment(addTripFragment);
    }


}