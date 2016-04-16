package org.devfleet.mob.app.ui.fittings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.devfleet.mob.app.R;
import org.devfleet.mob.app.domain.EveImages;
import org.devfleet.mob.app.model.EveFitting;
import org.devfleet.mob.app.widgets.ListRecyclerViewAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FittingAdapter extends ListRecyclerViewAdapter<EveFitting> {

    static class FittingHolder extends ListRecyclerViewAdapter.ViewHolder<EveFitting> {

        @Bind(R.id.rowFittingImage)
        ImageView shipImage;

        @Bind(R.id.rowFittingName)
        TextView nameView;

        @Bind(R.id.rowFittingShip)
        TextView shipNameView;

        @Bind(R.id.rowFittingDescription)
        TextView descriptionView;

        private final EveImages images;

        public FittingHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.images = new EveImages(itemView.getContext());
        }

        @Override
        public void render(final EveFitting fitting) {
            this.nameView.setText(fitting.getName());
            this.descriptionView.setText(fitting.getDescription());
            this.shipNameView.setText(fitting.getShipName());
            images.loadItemIcon(fitting.getShipId(), shipImage);
        }
    }

    @Override
    public FittingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_fitting, parent, false);
        return new FittingHolder(view);
    }

}
