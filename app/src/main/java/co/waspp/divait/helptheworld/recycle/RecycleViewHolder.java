package co.waspp.divait.helptheworld.recycle;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import co.waspp.divait.helptheworld.R;

/**
 * Created by divai on 12/10/2016.
 */

class RecycleViewHolder extends RecyclerView.ViewHolder {
    ImageView image;
    TextView title;
    TextView description;


    RecycleViewHolder(View itemView) {
        super(itemView);

        image = (ImageView) itemView.findViewById(R.id.image_type);
        title = (TextView) itemView.findViewById(R.id.text_title);
        description = (TextView) itemView.findViewById(R.id.text_description);
    }
}
