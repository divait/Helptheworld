package co.waspp.divait.helptheworld.recycle;

import android.content.Context;
import android.content.res.Resources;

import com.google.firebase.auth.FirebaseAuth;

import co.waspp.divait.helptheworld.R;

/**
 * Created by divait on 12/10/2016.
 *
 *
 */

public class RecyclerInteractor {

    private final Context context;

    interface Callback {
        void onListGot(RecycleItem[] items);
        void onListGotError(String msg);
    }

    RecyclerInteractor(Context context) {
        this.context = context;
    }

    public void getListOfItems(Callback callback) {
        Resources res = context.getResources();
        String[] types = res.getStringArray(R.array.recycle_titles);
        String[] desc = res.getStringArray(R.array.recycle_description);

        RecycleItem[] items = new RecycleItem[types.length];

        for (int i=0; i<items.length; i++) {
            items[i] = new RecycleItem(types[i], desc[i], getDrawable(i), getDrawableBackground(i));
        }

        callback.onListGot(items);
    }

    private int getDrawable(int pos) {
        switch (pos) {
            default:
            case 0:
                return R.drawable.cascara;
            case 1:
                return R.drawable.vidrio;
            case 2:
                return R.drawable.papel;
        }
    }

    private int getDrawableBackground(int pos) {
        switch (pos) {
            default:
            case 0:
                return R.drawable.round_button_green;
            case 1:
                return R.drawable.round_button_grey;
            case 2:
                return R.drawable.round_button_blue;
        }
    }
}
