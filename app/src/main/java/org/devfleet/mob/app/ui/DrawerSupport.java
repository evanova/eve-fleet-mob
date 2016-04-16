package org.devfleet.mob.app.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import org.devfleet.mob.app.R;
import org.devfleet.mob.app.domain.EveImages;
import org.devfleet.mob.app.model.EveCharacter;

import java.util.ArrayList;
import java.util.List;

class DrawerSupport {

    private Drawer drawer;
    private ProfileSettingDrawerItem addItem;

    private AccountHeader header;

    public DrawerSupport(final Activity activity, final Bundle savedInstanceState) {
        this.addItem =
                new ProfileSettingDrawerItem()
                .withName(activity.getResources().getString(R.string.drawer_character_add))
                .withDescription(activity.getResources().getString(R.string.drawer_character_add_description))
                .withIcon(R.drawable.eveui_recruitment)
                .withIdentifier(R.id.menu_drawer_add);

        this.header = buildHeader(activity);

        DrawerBuilder builder =
                new DrawerBuilder()
                .withAccountHeader(this.header)
                .withActivity(activity)
                .withHasStableIds(true)
                //.withItemAnimator(new AlphaCrossFadeAnimator())
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
                .inflateMenu(R.menu.drawer_character)
                .addDrawerItems(new DividerDrawerItem())
                .inflateMenu(R.menu.drawer_common)
                .addStickyDrawerItems(this.addItem)
                .withOnDrawerItemClickListener((view, position, drawerItem) -> onMenuItemSelected((int)drawerItem.getIdentifier(), false))
                .withOnDrawerItemLongClickListener((view, position, drawerItem) -> onMenuItemSelected((int)drawerItem.getIdentifier(), true));

        final Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        if (null != toolbar) {
            builder = builder.withToolbar(toolbar);
        }

        this.drawer = builder.build();
    }

    public long getActiveCharacter() {
        IProfile active = this.header.getActiveProfile();
        return (null == active) ? 0 : active.getIdentifier();
    }

    public void setActiveCharacter(final EveCharacter character) {
        if (null == character) {
            this.header.setActiveProfile(0);
            return;
        }

        final IProfile newProfile = profileItem(character);

        final List<IProfile> profiles = this.header.getProfiles();
        for (IProfile p: profiles) {
            if (p.getIdentifier() == character.getID()) {
                this.header.updateProfile(newProfile);
                this.header.setActiveProfile(character.getID());
                return;
            }
        }
        this.header.addProfile(newProfile, profiles.size() - 1);//add button!

    }
    public void setCharacters(final List<EveCharacter> characters) {
        final List<IProfile> profiles = profiles(characters);
        profiles.add(this.addItem);
        this.header.setProfiles(profiles);
    }

    public void openDrawer() {
        this.drawer.openDrawer();
    }

    public void closeDrawer() {
        this.drawer.closeDrawer();
    }

    public boolean isDrawerOpened() {
        return this.drawer.isDrawerOpen();
    }

    protected boolean onCharacterSelected(final long charID) {
        return false;
    }

    protected boolean onMenuItemSelected(final int itemID, final boolean longClick) {
        return false;
    }

    private AccountHeader buildHeader(final Activity activity) {
        return
            new AccountHeaderBuilder()
                .withActivity(activity)
                .withHeaderBackground(R.drawable.navigation)
                .withTranslucentStatusBar(true)
                .withOnAccountHeaderListener((view, profile, current) -> {
                    if (profile.getIdentifier() == R.id.menu_drawer_add) {
                        onMenuItemSelected(R.id.menu_drawer_add, false);
                        return true;
                    }
                    return onCharacterSelected(profile.getIdentifier());
                })
                .build();
    }

    private static List<IProfile> profiles(final List<EveCharacter> characters) {
        final List<IProfile> profiles = new ArrayList<>(characters.size());
        for (EveCharacter c: characters) {
            profiles.add(profileItem(c));
        }
        return profiles;
    }

    private static ProfileDrawerItem profileItem(final EveCharacter c) {
        return
            new ProfileDrawerItem()
            .withIdentifier(c.getID())
            .withName(c.getName())
            .withNameShown(true)
            .withEmail(c.getCorporationName())
            .withIcon(EveImages.getCharacterIconURL(c.getID()));
    }
}
