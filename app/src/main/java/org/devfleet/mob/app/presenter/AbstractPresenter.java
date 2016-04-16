package org.devfleet.mob.app.presenter;

import com.karumi.rosie.domain.usecase.UseCaseHandler;
import com.karumi.rosie.view.RosiePresenter;

abstract class AbstractPresenter<T extends AbstractPresenter.View> extends RosiePresenter<T> {

    public interface View extends RosiePresenter.View {

        void showError(final String error);

        void showLoading(final boolean loading);

    }

    public AbstractPresenter(final UseCaseHandler useCaseHandler) {
        super(useCaseHandler);
    }


}
