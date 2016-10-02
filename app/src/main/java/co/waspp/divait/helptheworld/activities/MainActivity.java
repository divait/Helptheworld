package co.waspp.divait.helptheworld.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

import co.waspp.divait.helptheworld.R;

public class MainActivity extends AppCompatActivity {
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
}
