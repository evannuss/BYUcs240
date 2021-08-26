package ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.example.familymap.R;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.Map;

import model.DataCache;
import model.Event;
import model.Person;


public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapLoadedCallback {

    private GoogleMap map;
    private ImageView icon;
    private TextView textLine1;
    private TextView textLine2;
    private LinearLayout eventDetails;
    private Event currEvent;

    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, container, savedInstanceState);
        View view = layoutInflater.inflate(R.layout.fragment_map, container, false);

        icon = (ImageView) view.findViewById(R.id.android_icon);
        textLine1 = (TextView) view.findViewById(R.id.event_textView_1);
        textLine2 = (TextView) view.findViewById(R.id.event_textView_2);
        eventDetails = (LinearLayout) view.findViewById(R.id.mapTextView);

        if(getActivity().getClass() == MainActivity.class) { //if calling activity is MainActivity

            icon.setImageResource(R.drawable.googleg_standard_color_18);
            textLine1.setText(R.string.map_textview_1);
            textLine2.setText("");

            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

            setHasOptionsMenu(true);
        }
        else if(getActivity().getClass() == EventActivity.class){ //if calling activity is EventActivity
            currEvent = DataCache.getInstance().getEventToDisplay();
            Person currPerson = DataCache.getInstance().getPeople().get(currEvent.getPersonID());

            Drawable genderIcon;
            if(currPerson.getGender().equals("m")){
                genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_male)
                        .colorRes(R.color.teal_700);
            }
            else{
                genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_female)
                        .colorRes(R.color.purple_200);
            }
            icon.setImageDrawable(genderIcon);

            String name = currPerson.getFirstName() + " " + currPerson.getLastName();
            String line2 = currEvent.getEventType() + ": " + currEvent.getCity() + ", "
                    + currEvent.getCountry() + " (" + currEvent.getYear() + ")";

            textLine1.setText(name);
            textLine2.setText(line2);

            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnMapLoadedCallback(this);
        Map<String, Float> colors = DataCache.getInstance().getColorMapping();

        for(Map.Entry<String, Event> entry : DataCache.getInstance().getEvents().entrySet()){
            LatLng currEvent = new LatLng(entry.getValue().getLatitude(),
                                          entry.getValue().getLongitude());

            map.addMarker(new MarkerOptions().position(currEvent)
                                             .icon(BitmapDescriptorFactory
                                             .defaultMarker(colors.get(entry.getValue().getEventType().toLowerCase())))
                                             .title(entry.getKey()));
        }

       if(getActivity().getClass() == EventActivity.class){
           LatLng currLatLng = new LatLng(currEvent.getLatitude(), currEvent.getLongitude());
           map.animateCamera(CameraUpdateFactory.newLatLng(currLatLng));
       }

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Map<String, Event> events = DataCache.getInstance().getEvents();
                Map<String, Person> people = DataCache.getInstance().getPeople();
                Event currEvent = events.get(marker.getTitle());
                Person currPerson = people.get(currEvent.getPersonID());

                Drawable genderIcon;
                if(currPerson.getGender().equals("m")){
                    genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_male)
                                                    .colorRes(R.color.teal_700);
                }
                else{
                    genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_female)
                                                    .colorRes(R.color.purple_200);
                }
                icon.setImageDrawable(genderIcon);

                String name = currPerson.getFirstName() + " " + currPerson.getLastName();
                String line2 = currEvent.getEventType() + ": " + currEvent.getCity() + ", "
                               + currEvent.getCountry() + " (" + currEvent.getYear() + ")";

                textLine1.setText(name);
                textLine2.setText(line2);
                textLine2.setTextAppearance(android.R.style.TextAppearance_Medium);

                LatLng currLatLng = new LatLng(currEvent.getLatitude(), currEvent.getLongitude());
                map.animateCamera(CameraUpdateFactory.newLatLng(currLatLng));

                eventDetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), PersonActivity.class);
                        intent.putExtra("PersonID", currPerson.getPersonID());
                        startActivity(intent);
                    }
                });

                return false;
            }
        });
    }

    @Override
    public void onMapLoaded() {
        // You probably don't need this callback. It occurs after onMapReady and I have seen
        // cases where you get an error when adding markers or otherwise interacting with the map in
        // onMapReady(...) because the map isn't really all the way ready. If you see that, just
        // move all code where you interact with the map (everything after
        // map.setOnMapLoadedCallback(...) above) to here.
    }
}
