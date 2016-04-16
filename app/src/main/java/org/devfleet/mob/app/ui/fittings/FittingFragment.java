package org.devfleet.mob.app.ui.fittings;

import com.karumi.rosie.view.Presenter;

import org.devfleet.mob.app.R;
import org.devfleet.mob.app.model.EveFitting;
import org.devfleet.mob.app.presenter.FittingPresenter;
import org.devfleet.mob.app.ui.AbstractFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

public class FittingFragment extends AbstractFragment implements FittingPresenter.View {

    @Inject
    @Presenter
    FittingPresenter presenter;

    @Bind(R.id.fragmentFittingsWidget)
    FittingWidget view;

    @Override
    protected int getLayoutId() {
        return R.layout.f_fittings;
    }

    @Override
    public void setCharacter(final long character) {
        this.presenter.setCharacter(character);
    }

    @Override
    public void setFittings(List<EveFitting> fittings) {
        this.view.setFittings(fittings);
    }
}
