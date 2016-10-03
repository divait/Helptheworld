package co.waspp.divait.helptheworld.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.waspp.divait.helptheworld.R;

/**
 * Created by divait on 3/10/2016.
 *
 * The main fragment when your  guest.
 */

public class MainGuestFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_guest_main, container, false);
    }

}
