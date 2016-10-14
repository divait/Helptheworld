package co.waspp.divait.helptheworld.recycle;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import co.waspp.divait.helptheworld.R;
import co.waspp.divait.helptheworld.recycle.interfaces.RecyclerContract;

/**
 * Created by divait on 12/10/2016.
 *
 *
 */

public class RecyclerFragment extends Fragment implements RecyclerContract.View {
    private RecyclerView recyclerList;
    private RecyclerView.Adapter adapterList;
    private RecyclerView.LayoutManager layoutManager;

    private RecyclerContract.Presenter recyclerPresenter;

    public RecyclerFragment() {
    }

    public static RecyclerFragment newInstance() {
        Bundle args = new Bundle();

        RecyclerFragment fragment = new RecyclerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Get Arguments if are some
            Log.d("Data dev", "Have arguments");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recylcer, container, false);

        recyclerList = (RecyclerView) root.findViewById(R.id.recycler_list);
        recyclerList.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerList.setLayoutManager(layoutManager);

        setAdapter();

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerPresenter.start();
    }

    public void setAdapter() {
        recyclerPresenter.getRecycleItems();
    }

    @Override
    public void showListOfItems(RecycleItem[] items) {
        adapterList = new RecyclerListAdapter(items);
        recyclerList.setAdapter(adapterList);
    }

    @Override
    public void errorGettingItems(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setRegisterPresenter(RecyclerContract.Presenter presenter) {
        if (presenter != null) {
            recyclerPresenter = presenter;
        } else {
            throw new RuntimeException("Presenter can't be null.");
        }
    }
}
