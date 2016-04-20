package org.devfleet.mob.app.ui;

import com.karumi.rosie.view.RosieFragment;

import org.devfleet.mob.app.model.EveCharacter;

public abstract class AbstractFragment extends RosieFragment {

    @Override
    protected abstract int getLayoutId();

    public void showError(String error) {
        if (getActivity() instanceof AbstractActivity) {
            ((AbstractActivity) getActivity()).showError(error);
        }
    }

    public void showLoading(boolean loading) {
        if (getActivity() instanceof AbstractActivity) {
            ((AbstractActivity)getActivity()).showLoading(loading);
        }
    }

    public void setCharacter(final EveCharacter character) {}
}
