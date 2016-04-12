package com.eve0.fleetmob.app.ui.drawer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.eve0.fleetmob.app.R;
import com.eve0.fleetmob.app.eve.EveImages;
import com.eve0.fleetmob.app.model.EveCharacter;
import com.eve0.fleetmob.app.ui.MainActivity;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class MainDrawer implements DrawerView {
    private static final Logger LOG = LoggerFactory.getLogger(MainActivity.class);
    private static final long  PROFILE_ADD = 912542510700000003l;

    private Drawer drawer;
    private AccountHeader header;

    public MainDrawer(final Activity activity, final Bundle savedInstanceState) {
        this.header = buildHeader(activity);

        DrawerBuilder builder =
                new DrawerBuilder()
                .withAccountHeader(this.header)
                .withActivity(activity)
                .withHasStableIds(true)
                //.withItemAnimator(new AlphaCrossFadeAnimator())
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true);

        final Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        if (null != toolbar) {
            builder = builder.withToolbar(toolbar);
        }
        this.drawer = builder.build();
    }

    @Override
    public void setSelectedCharacter(final EveCharacter character) {
        final List<IProfile> profiles = this.header.getProfiles();

        for (IProfile p: profiles) {
            if (p.getIdentifier() == character.getID()) {
                this.header.setActiveProfile(character.getID());
                return;
            }
        }
        this.header.addProfile(newProfileItem(character), profiles.size() - 1);
        this.header.setActiveProfile(character.getID());
    }

    @Override
    public void setCharacters(final List<EveCharacter> characters) {
        final List<IProfile> profiles = new ArrayList<>(characters.size());
        for (EveCharacter c: characters) {
            LOG.error(ToStringBuilder.reflectionToString(c));
            profiles.add(newProfileItem(c));
        }

        profiles.add(new ProfileSettingDrawerItem()
                .withName("Add Character")
                .withDescription("Add a new character")
                .withIdentifier(PROFILE_ADD));
                //.withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_plus)
                //.actionBar().paddingDp(5)
                //.colorRes(R.color.material_drawer_primary_text)).withIdentifier(-1),
        this.header.setProfiles(profiles);
    }

    @Override
    public void openDrawer() {
        this.drawer.openDrawer();
    }

    @Override
    public void closeDrawer() {
        this.drawer.closeDrawer();
    }

    @Override
    public boolean isDrawerOpened() {
        return this.drawer.isDrawerOpen();
    }

    protected void onAddCharacter() {

    }

    protected void onSelectCharacter(final long charID) {

    }

    private AccountHeader buildHeader(final Activity activity) {
        return
                new AccountHeaderBuilder()
                        .withActivity(activity)
                        .withHeaderBackground(R.drawable.navigation)
                        .withTranslucentStatusBar(true)
                        .withOnAccountHeaderListener((view, profile, current) -> {
                            LOG.error("selected {}", profile.getIdentifier());
                            if (profile.getIdentifier() == PROFILE_ADD) {
                                onAddCharacter();
                                return true;
                            }
                            onSelectCharacter(profile.getIdentifier());
                            return true;
                        })
                        .build();
    }

    private static ProfileDrawerItem newProfileItem(final EveCharacter c) {
        return
            new ProfileDrawerItem()
            .withIdentifier(c.getID())
            .withName(c.getName())
            .withIcon(EveImages.getCharacterIconURL(c.getID()));
    }
}
