package co.waspp.divait.helptheworld.recycle;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.waspp.divait.helptheworld.R;

/**
 * Created by divait on 12/10/2016.
 *
 *
 */

public class RecyclerListAdapter extends RecyclerView.Adapter<RecycleViewHolder> {
    private RecycleItem[] items;

    public RecyclerListAdapter ( RecycleItem[] items) {
        this.items = items;
    }

    @Override
    public RecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.element_recycle_card, parent, false);

        return new RecycleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecycleViewHolder holder, int position) {
        holder.image.setImageResource(items[position].getImage());
        holder.image.setBackgroundResource(items[position].getBackground());
        holder.title.setText(items[position].getTitle());
        holder.description.setText(items[position].getDescription());
    }

    @Override
    public int getItemCount() {
        return items.length;
    }
}
