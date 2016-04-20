package org.devfleet.mob.app.ui;

import android.widget.ImageView;
import android.widget.TextView;

import org.devfleet.mob.app.R;
import org.devfleet.mob.app.domain.EveImages;
import org.devfleet.mob.app.model.EveCharacter;

import javax.inject.Inject;

import butterknife.Bind;

public class MainFragment extends AbstractFragment {

    @Bind(R.id.fMainPortraitImage)
    ImageView portraitImage;

    @Bind(R.id.fMainName)
    TextView nameText;

    @Bind(R.id.fMainCorporation)
    TextView corporationText;

    @Bind(R.id.fMainLocation)
    TextView locationText;

    @Bind(R.id.fMainShipImage)
    ImageView shipImage;

    @Bind(R.id.fMainShipName)
    TextView shipNameText;

    @Bind(R.id.fMainShipDescription)
    TextView shipDescriptionText;

    @Inject
    EveImages images;

    @Override
    protected int getLayoutId() {
        return R.layout.f_main;
    }

    @Override
    public void setCharacter(final EveCharacter character) {
        if (null == character) {
            clear();
        }
        else {
            render(character);
        }
    }

    private void clear() {}

    private void render(final EveCharacter character) {
        nameText.setText(character.getName());
        corporationText.setText(character.getCorporationName());
        locationText.setText(character.getLocationName());
        images.loadCorporationIcon(character.getCorporationID(), portraitImage);


    }
}
