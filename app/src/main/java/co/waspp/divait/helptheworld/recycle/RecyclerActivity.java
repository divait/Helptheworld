package co.waspp.divait.helptheworld.recycle;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import co.waspp.divait.helptheworld.R;
import co.waspp.divait.helptheworld.models.BaseBackActivity;

/**
 * Created by divait on 12/10/2016.
 *
 *
 */

public class RecyclerActivity extends BaseBackActivity {

    private RecyclerPresenter recyclerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        RecyclerFragment recycleFragment = (RecyclerFragment) getSupportFragmentManager()
                .findFragmentById(R.id.main_container);
        if (recycleFragment == null) {
            recycleFragment = RecyclerFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, recycleFragment)
                    .commit();
        }

        // Create interactor and presenter
        RecyclerInteractor recyclerInteractor = new RecyclerInteractor(getApplicationContext());
        recyclerPresenter = new RecyclerPresenter(recycleFragment, recyclerInteractor);

    }
}
