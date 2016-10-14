package co.waspp.divait.helptheworld.recycle;

import android.support.annotation.NonNull;

import co.waspp.divait.helptheworld.recycle.interfaces.RecyclerContract;

/**
 * Created by divait on 12/10/2016.
 *
 *
 */

public class RecyclerPresenter implements RecyclerContract.Presenter, RecyclerInteractor.Callback {

    private final RecyclerContract.View registerView;
    private RecyclerInteractor registerInteractor;

    public RecyclerPresenter(@NonNull RecyclerContract.View registerView, @NonNull RecyclerInteractor registerInteractor) {
        this.registerView = registerView;
        registerView.setRegisterPresenter(this);
        this.registerInteractor = registerInteractor;
    }

    @Override
    public void start() {

    }

    @Override
    public void getRecycleItems() {
        registerInteractor.getListOfItems(this);
    }

    @Override
    public void onListGot(RecycleItem[] items) {
        registerView.showListOfItems(items);
    }

    @Override
    public void onListGotError(String msg) {
        registerView.errorGettingItems(msg);
    }
}
