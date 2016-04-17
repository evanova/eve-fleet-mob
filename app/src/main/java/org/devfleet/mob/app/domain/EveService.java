package org.devfleet.mob.app.domain;

import android.net.Uri;

import org.devfleet.mob.app.model.EveCharacter;
import org.devfleet.mob.app.model.EveContact;
import org.devfleet.mob.app.model.EveFitting;
import org.devfleet.mob.app.model.EveLocation;
import org.devfleet.mob.app.model.EveRoute;
import org.devfleet.mob.app.model.EveServerStatus;

import java.util.List;

public interface EveService {

    interface Authenticator {

        Uri getLoginUri();

        EveService fromPublic();

        EveService fromAuthCode(final String authCode);

        EveService fromToken(final String authToken);

    }

    EveServerStatus getServerStatus();

    EveCharacter getCharacter();

    boolean setRoute(final EveRoute route);

    boolean addRoute(final EveRoute route);

    List<EveContact> getContacts();

    boolean addContact(final EveContact contact);

    boolean removeContact(final long contactID);

    List<EveFitting> getFittings();
}
