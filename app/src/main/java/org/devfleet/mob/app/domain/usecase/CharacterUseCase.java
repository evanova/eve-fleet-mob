package org.devfleet.mob.app.domain.usecase;

import org.devfleet.mob.app.domain.EveRepository;
import org.devfleet.mob.app.domain.EveService;
import org.devfleet.mob.app.model.EveCharacter;
import org.devfleet.mob.app.model.EveContact;
import org.devfleet.mob.app.model.EveFitting;

import java.util.List;

import javax.inject.Inject;

public class CharacterUseCase {

    private final EveRepository repository;
    private final EveService.Authenticator authenticator;

    @Inject
    public CharacterUseCase(
            final EveService.Authenticator authenticator,
            final EveRepository repository) {
        this.authenticator = authenticator;
        this.repository = repository;
    }

    public List<EveContact> listContacts(final long charID) {
        final EveService service = authenticator.fromToken(this.repository.getToken(charID));
        final List<EveContact> contacts = service.getContacts();
        if (null == contacts) {
            throw new IllegalStateException("Unauthenticated character " + charID);
        }
        return contacts;
    }

    public List<EveFitting> listFittings(final long charID) {
        final EveService service = authenticator.fromToken(this.repository.getToken(charID));
        final List<EveFitting> fittings = service.getFittings();
        if (null == fittings) {
            throw new IllegalStateException("Unauthenticated character " + charID);
        }
        return fittings;
    }

    public List<EveCharacter> listCharacters() {
        return repository.listCharacters();
    }
}
