package co.waspp.divait.helptheworld.maps;

import android.os.Bundle;

import co.waspp.divait.helptheworld.R;
import co.waspp.divait.helptheworld.models.BaseBackActivity;

/**
 * Created by divait on 13/10/2016.
 */

public class MapActivity extends BaseBackActivity {

    MapFragment mMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
/*
        mMapFragment = MapFragment.n;
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, mMapFragment);
        fragmentTransaction.commit();
*/
    }
}
