package ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.ClipData;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.familymap.R;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import net.DataRetrievalTask;

public class MainActivity extends AppCompatActivity {

    private LoginFragment loginFragment;
    private MapFragment mapFragment;
    private FragmentManager fm;
    private boolean loggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Iconify.with(new FontAwesomeModule());

        fm = this.getSupportFragmentManager();
        if(!loggedIn) {
            loginFragment = (LoginFragment) fm.findFragmentById(R.id.loginFrameLayout);
            if (loginFragment == null) {
                loginFragment = new LoginFragment();
//            Bundle args = new Bundle();
//            args.putChar("Switch fragment", 'f');
//            loginFragment.setArguments(args);

                fm.beginTransaction()
                        .add(R.id.loginFrameLayout, loginFragment)
                        .commit();
            }
        }
        else{
            mapFragment = new MapFragment();
            fm.beginTransaction()
                    .replace(R.id.loginFrameLayout, mapFragment)
                    .commit();

            invalidateOptionsMenu();
        }

//        if(loginFragment.getArguments().getChar("Switch fragment") == 't'){
//            mapFragment = new MapFragment();
//
//            fm.beginTransaction()
//                    .replace(R.id.loginFrameLayout, mapFragment)
//                    .commit();
//        }
    }


    public void switchFragment(){
        mapFragment = new MapFragment();
        fm.beginTransaction()
                .replace(R.id.loginFrameLayout, mapFragment)
                .commit();

        loggedIn = true;
        invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(loggedIn) {
            super.onCreateOptionsMenu(menu);
            new MenuInflater(this).inflate(R.menu.menu_resource, menu);
//            Drawable settingsIcon = new IconDrawable(getBaseContext(), FontAwesomeIcons.fa_gear)
//                                                     .sizeDp(20).color(R.color.white);
//            MenuItem settingsButton = menu.findItem(R.id.settings_button);
//            settingsButton.setIcon(settingsIcon);

//            Drawable searchIcon = new IconDrawable(getBaseContext(), FontAwesomeIcons.fa_search)
//                                                   .sizeDp(20).color(R.color.white);
//            MenuItem searchButton = menu.findItem(R.id.search_button);
//            searchButton.setIcon(searchIcon);

            return true;
        }

        return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.search_button:
                return true;
            case R.id.settings_button:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}