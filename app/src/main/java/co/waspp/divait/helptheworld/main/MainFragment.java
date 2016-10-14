package co.waspp.divait.helptheworld.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import co.waspp.divait.helptheworld.R;
import co.waspp.divait.helptheworld.login.LoginActivity;
import co.waspp.divait.helptheworld.recycle.RecyclerActivity;
import co.waspp.divait.helptheworld.register.RegisterActivity;

/**
 * Created by divait on 3/10/2016.
 *
 * The main fragment when your  guest.
 */

public class MainFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        rootView.findViewById(R.id.button_recycle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRecycler();
            }
        });

        rootView.findViewById(R.id.button_water).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogNotReady();
            }
        });

        rootView.findViewById(R.id.button_dogs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogNotReady();
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

    private void openRecycler() { // TODO: just one line
        // Start the Signup activity
        Intent intent = new Intent(getActivity(), RecyclerActivity.class);
        getActivity().startActivity(intent);
    }

    public void showDialogNotReady() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyle);
        builder.setTitle("Coming soon!");
        builder.setMessage("This functionality is coming soon, for you to enjoy it.");
        builder.setPositiveButton("Discard", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}
