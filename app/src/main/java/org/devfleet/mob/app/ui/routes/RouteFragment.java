package org.devfleet.mob.app.ui.routes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karumi.rosie.view.Presenter;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.devfleet.mob.app.R;
import org.devfleet.mob.app.model.EveCharacter;
import org.devfleet.mob.app.model.EveFitting;
import org.devfleet.mob.app.model.EveRoute;
import org.devfleet.mob.app.presenter.FittingPresenter;
import org.devfleet.mob.app.presenter.RoutePresenter;
import org.devfleet.mob.app.ui.AbstractFragment;
import org.devfleet.mob.app.ui.fittings.FittingWidget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

public class RouteFragment extends AbstractFragment implements RoutePresenter.View {
    private static final Logger LOG = LoggerFactory.getLogger(RouteFragment.class);

    static class RoutePagerAdapter extends PagerAdapter {
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int resId = 0;
            switch (position) {
                case 0:
                    resId = R.id.fRouteFastest;
                    break;
                case 1:
                    resId = R.id.fRouteHigh;
                    break;
                case 2:
                    resId = R.id.fRouteLow;
                    break;
            }
            return container.findViewById(resId);
        }


        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == ((View) arg1);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            LOG.error("destroyItem: {} {}", position, object);
        }
    }

    @Inject
    @Presenter
    RoutePresenter presenter;

    @Bind(R.id.fRoutePager)
    ViewPager pagerView;

    @Bind(R.id.fRouteFastest)
    RouteWidget fastestView;

    @Bind(R.id.fRouteHigh)
    RouteWidget highView;

    @Bind(R.id.fRouteLow)
    RouteWidget lowView;

    @Override
    protected int getLayoutId() {
        return R.layout.f_routes;
    }

    @Override
    public void setCharacter(final EveCharacter character) {
      //  this.presenter.setCharacter((null == character) ? 0 : character.getID());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        pagerView.setId(R.id.pagerRoutes);
        pagerView.setAdapter(new RoutePagerAdapter());
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        this.presenter.route(Arrays.asList("Jita", "Amarr"));
    }

    @Override
    public void setRoute(final EveRoute route) {
        LOG.error("setRoute: {}", ToStringBuilder.reflectionToString(route));
        switch (route.getType()) {
            case EveRoute.FASTEST:
                fastestView.setRoute(route);
                break;
            case EveRoute.HIGHSEC:
                highView.setRoute(route);
                break;
            case EveRoute.LOWSEC:
                lowView.setRoute(route);
                break;
            default:
                break;
        }
    }
}
