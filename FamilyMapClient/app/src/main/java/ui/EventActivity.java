package ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.familymap.R;

import android.os.Bundle;

public class EventActivity extends AppCompatActivity {

    private MapFragment mapFragment;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fm = this.getSupportFragmentManager();
        mapFragment = (MapFragment) fm.findFragmentById(R.id.eventMapFragment);
        if(mapFragment == null){
            mapFragment = new MapFragment();

            fm.beginTransaction()
                    .add(R.id.eventMapFragment, mapFragment)
                    .commit();
        }
    }
}