package ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.familymap.R;

import org.w3c.dom.Text;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import model.DataCache;
import model.Event;
import model.Person;

public class PersonActivity extends AppCompatActivity {

    private TextView firstName;
    private TextView lastName;
    private TextView gender;
    private Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Map<String, Event> events = DataCache.getInstance().getEvents();
        Map<String, Person> people = DataCache.getInstance().getPeople();

        person = people.get(getIntent().getStringExtra("PersonID"));

        firstName = findViewById(R.id.firstName2);
        firstName.setText(person.getFirstName());

        lastName = findViewById(R.id.lastName2);
        lastName.setText(person.getLastName());

        gender = findViewById(R.id.gender2);
        String genderString = null;
        if(person.getGender().equals("f")){
            genderString = "Female";
        }
        else {
            genderString = "Male";
        }
        gender.setText(genderString);

        ExpandableListView listView = findViewById(R.id.listView);

        Map<Integer, Event> lifeEvents = new HashMap<>();
        for(Map.Entry<String, Event> entry : events.entrySet()){
            if(entry.getValue().getPersonID().equals(person.getPersonID())){
                lifeEvents.put(entry.getValue().getYear(), entry.getValue());
            }
        }

        Map<String, Person> relatives = new HashMap<>();
        Person dad = people.get(person.getFatherID());
        if(dad != null){
            relatives.put("Father", dad);
        }

        Person mom = people.get(person.getMotherID());
        if(mom != null){
            relatives.put("Mother", mom);
        }

        Person spouse = people.get(person.getSpouseID());
        if(spouse != null){
            relatives.put("Spouse", spouse);
        }


        for(Map.Entry<String, Person> entry : people.entrySet()){
            if(entry.getValue().getMotherID() != null) {
                if (entry.getValue().getMotherID().equals(person.getPersonID())) {
                    relatives.put("Child", entry.getValue());
                }
            }
            if(entry.getValue().getFatherID() != null){
                if(entry.getValue().getFatherID().equals(person.getPersonID())){
                    relatives.put("Child", entry.getValue());
                }
            }
        }

        listView.setAdapter(new ExpandableListAdapter(lifeEvents, relatives));
    }

    private class ExpandableListAdapter extends BaseExpandableListAdapter{

        private static final int LIFE_EVENTS_GROUP = 0;
        private static final int RELATIVE_GROUP = 1;

        private final Map<Integer, Event> lifeEvents;
        private final Map<String, Person> relatives;

        public ExpandableListAdapter(Map<Integer, Event>lifeEvents, Map<String, Person> relatives) {
            this.lifeEvents = lifeEvents;
            this.relatives = relatives;
        }

        @Override
        public int getGroupCount() { return 2; }

        @Override
        public int getChildrenCount(int groupPosition) {
            switch(groupPosition){
                case LIFE_EVENTS_GROUP:
                    return lifeEvents.size();
                case RELATIVE_GROUP:
                    return relatives.size();
                default:
                    throw new IllegalArgumentException("Unrecognized group position: " + groupPosition);
            }
        }

        @Override
        public Object getGroup(int groupPosition) {
            switch(groupPosition){
                case LIFE_EVENTS_GROUP:
                    return getString(R.string.life_event_title);
                case RELATIVE_GROUP:
                    return getString(R.string.relatives_title);
                default:
                    throw new IllegalArgumentException("Unrecognized group position: " + groupPosition);
            }
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            switch(groupPosition){
                case LIFE_EVENTS_GROUP:
                    Integer key = (Integer) lifeEvents.keySet().toArray()[childPosition];
                    return lifeEvents.get(key);
                case RELATIVE_GROUP:
                    String key2 = (String) relatives.keySet().toArray()[childPosition];
                    return relatives.get(key2);
                default:
                    throw new IllegalArgumentException("Unrecognized group position: " + groupPosition);
            }
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.list_item_group, parent, false);
            }

            TextView titleView = convertView.findViewById(R.id.listTitle);

            switch (groupPosition) {
                case LIFE_EVENTS_GROUP:
                    titleView.setText(R.string.life_event_title);
                    break;
                case RELATIVE_GROUP:
                    titleView.setText(R.string.relatives_title);
                    break;
                default:
                    throw new IllegalArgumentException("Unrecognized group position: " + groupPosition);
            }

            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            View itemView;

            switch(groupPosition) {
                case LIFE_EVENTS_GROUP:
                    itemView = getLayoutInflater().inflate(R.layout.life_events_item, parent, false);
                    initializeEventsView(itemView, childPosition);
                    break;
                case RELATIVE_GROUP:
                    itemView = getLayoutInflater().inflate(R.layout.family_member_item, parent, false);
                    initializeRelativesView(itemView, childPosition);
                    break;
                default:
                    throw new IllegalArgumentException("Unrecognized group position: " + groupPosition);
            }

            return itemView;
        }

        private void initializeEventsView(View eventsItemView, final int childPosition){
            ImageView marker = eventsItemView.findViewById(R.id.marker_icon);
            Drawable markerIcon = new IconDrawable(PersonActivity.this, FontAwesomeIcons.fa_map_marker)
                    .colorRes(R.color.black);
            marker.setImageDrawable(markerIcon);

            TextView eventLine1 = eventsItemView.findViewById(R.id.event_info1);
            Integer key = (Integer) lifeEvents.keySet().toArray()[childPosition];
            Event currEvent = lifeEvents.get(key);
            String eventInfo1 = currEvent.getEventType().toUpperCase() + ": " + currEvent.getCity() + ", "
                                + currEvent.getCountry() + " (" + currEvent.getYear() + ")";
            eventLine1.setText(eventInfo1);

            TextView eventLine2 = eventsItemView.findViewById(R.id.event_info2);

            String eventInfo2 = person.getFirstName() + " " + person.getLastName();
            eventLine2.setText(eventInfo2);

            eventsItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PersonActivity.this, EventActivity.class);
                    DataCache.getInstance().setEventToDisplay(currEvent);
                    startActivity(intent);
                }
            });
        }

        private void initializeRelativesView(View relativesItemView, final int childPosition){
            String key = (String) relatives.keySet().toArray()[childPosition];
            Person currPerson = relatives.get(key);

            ImageView genderImage = relativesItemView.findViewById(R.id.gender_image);
            Drawable icon;
            if(currPerson.getGender().equals("f")){
                icon = new IconDrawable(PersonActivity.this, FontAwesomeIcons.fa_female)
                        .colorRes(R.color.purple_200);
            }
            else{
                icon = new IconDrawable(PersonActivity.this, FontAwesomeIcons.fa_male)
                        .colorRes(R.color.teal_700);
            }
            genderImage.setImageDrawable(icon);

            TextView nameView = relativesItemView.findViewById(R.id.family_member_name);
            String name = currPerson.getFirstName() + " " + currPerson.getLastName();
            nameView.setText(name);

            TextView relationshipView = relativesItemView.findViewById(R.id.family_member_relationship);
            relationshipView.setText(key);

            relativesItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PersonActivity.this, PersonActivity.class);
                    intent.putExtra("PersonID", currPerson.getPersonID());
                    startActivity(intent);
                }
            });
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}