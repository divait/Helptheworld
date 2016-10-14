package co.waspp.divait.helptheworld.recycle.interfaces;

import co.waspp.divait.helptheworld.models.BasePresenter;
import co.waspp.divait.helptheworld.models.BaseView;
import co.waspp.divait.helptheworld.recycle.RecycleItem;
import co.waspp.divait.helptheworld.register.interfaces.RegisterContract;

/**
 * Created by divai on 12/10/2016.
 */

public interface RecyclerContract {

    public interface View extends BaseView<RecyclerContract.Presenter> {

        void showListOfItems(RecycleItem[] items);

        void errorGettingItems(String msg);

    }

    public interface Presenter extends BasePresenter {
        void getRecycleItems();
    }
}
