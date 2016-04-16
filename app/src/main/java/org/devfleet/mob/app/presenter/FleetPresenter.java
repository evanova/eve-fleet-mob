package org.devfleet.mob.app.presenter;

import com.karumi.rosie.domain.usecase.UseCaseHandler;

import javax.inject.Inject;

public final class FleetPresenter extends AbstractPresenter<FleetPresenter.View> {

    public interface View extends AbstractPresenter.View {

    }

    @Inject
    public FleetPresenter(
          final UseCaseHandler useCaseHandler) {
        super(useCaseHandler);
    }
}
