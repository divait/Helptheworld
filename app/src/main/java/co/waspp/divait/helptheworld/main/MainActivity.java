package co.waspp.divait.helptheworld.main;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import co.waspp.divait.helptheworld.R;
import co.waspp.divait.helptheworld.events.UserStateChangeEvent;
import co.waspp.divait.helptheworld.login.LoginActivity;
import co.waspp.divait.helptheworld.register.RegisterActivity;
import co.waspp.divait.helptheworld.storage.UserPreferences;

public class MainActivity extends AppCompatActivity {
    static final String STATE_USER = "userState";

    private Toolbar toolbar;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add toolbar behavior
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setActionBarTitle();
        setSupportActionBar(toolbar);

        // Add navigation drawer behavior
        drawer = (DrawerLayout) findViewById(R.id.activity_main);
        initNavDrawer();

        // Check the container exists to add the fragment
        if (findViewById(R.id.fragment_container) != null) {
            //Restore for previous state
            if (savedInstanceState != null) {
                if(UserPreferences.getUserState(this) != savedInstanceState.getBoolean(STATE_USER)) {
                    addFragment(false, chooseFragment());
                }
                return;
            }
            addFragment(true, chooseFragment());

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

        Class cl;
        if(UserPreferences.getUserState(this)) {
            cl = MainFragment.class;
        } else {
            cl = MainGuestFragment.class;
        }

        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        Fragment fragment = null;
        for(Fragment fr : fragments) {
            if (fr != null)
                fragment = fr;
        }

        if(fragment != null && fragment.getClass() != cl)
            addFragment(false, chooseFragment());
        changeMenuLogin(UserPreferences.getUserState(getApplicationContext()));
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putBoolean(STATE_USER, UserPreferences.getUserState(this));

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    /**
     * Set the title of the ActionBar with the name of the Shop.
     **/
    private void setActionBarTitle () {
        // SharedPreferences settings = getSharedPreferences (Utils.PREFERENCES, MODE_PRIVATE);
        // String name = settings.getString(Utils.PREFERENCE_SHOPKEEPER_NAME, getString(R.string.app_name));
        // toolbar.setTitle(name);
    }

    /**
     * Set the entire behavior for the Navigation bar and the drawer.
     **/
    private void initNavDrawer () {
        // Get the Navigation View
        NavigationView navView = (NavigationView) findViewById(R.id.drawer);

        // Set the behavior on click any option
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                return onDrawerItemSelected(id);
            }
        });

        // Set the data for drawer header
        setDrawerHeader(navView);

        // Create Toggle for the drawer
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        // Add toggle to the drawer
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    /**
     * Define the action to perform when a drawer option is Selected.
     *
     * @param id The id of the selected item.
     * @return True if there was an action perform and False otherwise.
     **/
    private boolean onDrawerItemSelected (int id) {
        switch (id) {
            case R.id.drawer_register:
                drawer.closeDrawers();
                signup();
                return true;
            case R.id.drawer_login:
                drawer.closeDrawers();
                login();
                return true;
            case R.id.drawer_points:
                drawer.closeDrawers();
                return true;
            case R.id.drawer_badges:
                drawer.closeDrawers();
                return true;
            case R.id.drawer_ranking:
                drawer.closeDrawers();
                return true;
            case R.id.drawer_logout:
                drawer.closeDrawers();
                logout();
                return true;
            default:
                return false;
        }
    }

    /**
     * Set the Data in the drawer header with the info of the shop that is logged.
     *
     * @param navView the NavigationView that have the header.
     **/
    private void setDrawerHeader (NavigationView navView) {
        // Set the Data in the Drawer
        View header = navView.getHeaderView(0);
        ((TextView) header.findViewById(R.id.text_name_drawer)).setText(getString(R.string.app_name));
    }

    /**
     * Listen when user change the logged state.
     *
     * @param event the event that arrive.
     **/
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserStateEvent(UserStateChangeEvent event) {
        addFragment(false, chooseFragment());
        changeMenuLogin(event.isLooged());
    }

    /**
     * Add or Replace a fragment in the container of this activity
     *
     * @param add If you need to add or replace
     * @param fragment the fragment to add
     **/
    private void addFragment(boolean add, Fragment fragment) {
        if(add) {
            // Add fragment
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        } else {
            // Replace fragment
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    /**
     * This method return the fragment corresponding is the user is logged or not.
     *
     * @return The corresponding fragment for the current state.
     **/
    private Fragment chooseFragment() {
        // If user is Logged
        if(UserPreferences.getUserState(this)) {
            return new MainFragment();
        } else {
            return new MainGuestFragment();
        }

    }

    private void changeMenuLogin(boolean isLogged) {
        NavigationView navView = (NavigationView) findViewById(R.id.drawer);
        Menu menu = navView.getMenu();

        View header = navView.getHeaderView(0);

        ImageView image = (ImageView) header.findViewById(R.id.header_image);

        if(!isLogged) {
            image.setVisibility(View.GONE);

            menu.findItem(R.id.drawer_login).setVisible(true);
            menu.findItem(R.id.drawer_register).setVisible(true);
            menu.findItem(R.id.drawer_logout).setVisible(false);
            menu.findItem(R.id.drawer_points).setVisible(false);
            menu.findItem(R.id.drawer_badges).setVisible(false);
        } else {
            image.setVisibility(View.VISIBLE);
            image.setImageResource(R.drawable.hombre);

            menu.findItem(R.id.drawer_login).setVisible(false);
            menu.findItem(R.id.drawer_register).setVisible(false);
            menu.findItem(R.id.drawer_logout).setVisible(true);
            menu.findItem(R.id.drawer_points).setVisible(true);
            menu.findItem(R.id.drawer_badges).setVisible(true);
        }
    }

    private void signup() { // TODO: just one line
        // Start the Signup activity
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }

    private void login() { // TODO: just one line
        // Start the Signup activity
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        UserPreferences.logout(getApplicationContext());
    }
}
