package org.devfleet.mob.app.ui.contacts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.devfleet.mob.app.R;
import org.devfleet.mob.app.domain.EveImages;
import org.devfleet.mob.app.model.EveContact;
import org.devfleet.mob.app.widgets.ListRecyclerViewAdapter;

import butterknife.Bind;

class ContactAdapter extends ListRecyclerViewAdapter<EveContact> {

    static class ContactHolder extends ListRecyclerViewAdapter.ViewHolder<EveContact> {

        @Bind(R.id.rowContactImage)
        ImageView portraitImage;

        @Bind(R.id.rowContactRatingImage)
        ImageView ratingImage;

        @Bind(R.id.rowContactName)
        TextView nameView;

        @Bind(R.id.rowContactDescription)
        TextView descriptionView;

        @Bind(R.id.rowContactCorporation)
        TextView corporationView;

        private final EveImages images;

        public ContactHolder(View itemView) {
            super(itemView);
            this.images = new EveImages(itemView.getContext());
        }

        @Override
        public void render(final EveContact contact) {
            this.nameView.setText(contact.getName());
            this.corporationView.setText(contact.getCorporationName());
            this.descriptionView.setText(contact.getDescription());
            this.images.loadCharacterIcon(contact.getID(), this.portraitImage);

            if (contact.getStanding() < -5) {
                this.ratingImage.setBackgroundResource(R.drawable.bg_standing_terrible);
                this.ratingImage.setImageResource(R.drawable.ic_standing_negative);
            }
            else if (contact.getStanding() < 0) {
                this.ratingImage.setBackgroundResource(R.drawable.bg_standing_bad);
                this.ratingImage.setImageResource(R.drawable.ic_standing_negative);
            }
            else if (contact.getStanding() < 5) {
                this.ratingImage.setBackgroundResource(R.drawable.bg_standing_neutral);
                this.ratingImage.setImageResource(R.drawable.ic_standing_neutral);
            }
            else if (contact.getStanding() < 10) {
                this.ratingImage.setBackgroundResource(R.drawable.bg_standing_good);
                this.ratingImage.setImageResource(R.drawable.ic_standing_positive);
            }
            else {
                this.ratingImage.setBackgroundResource(R.drawable.bg_standing_excellent);
                this.ratingImage.setImageResource(R.drawable.ic_standing_positive);
            }
        }
    }

    @Override
    public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_contact, parent, false);
        return new ContactHolder(view);
    }
}
