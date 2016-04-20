package org.devfleet.mob.app.domain.repository;

import android.content.Context;

import org.devfleet.mob.app.domain.EveRepository;
import org.devfleet.mob.app.model.EveCharacter;
import org.devfleet.mob.app.model.EveLocation;


import java.util.ArrayList;
import java.util.List;

class EveRepositoryImpl implements EveRepository {

    private final EveDatabase database;

    public EveRepositoryImpl(final Context context) {
        this.database = EveDatabase.from(context);
    }

    @Override
    public String getToken(long charID) {
        return this.database.getCharacterToken(charID);
    }

    @Override
    public List<EveCharacter> listCharacters() {
        final List<CharacterEntity> entities = this.database.listCharacters();
        final List<EveCharacter> returned = new ArrayList<>();
        for (CharacterEntity e: entities) {
            returned.add(RepositoryMapper.map(e, this.database.getCorporation(e.getCorporationId())));
        }
        return returned;
    }

    @Override
    public EveCharacter getCharacter(long charID) {
        final CharacterEntity entity = this.database.getCharacter(charID);
        return RepositoryMapper.map(
                entity,
                this.database.getCorporation(entity.getCorporationId()));
    }

    @Override
    public EveCharacter firstCharacter() {
        final List<CharacterEntity> entities = this.database.listCharacters(1);
        if (entities.isEmpty()) {
            return null;
        }
        final CharacterEntity entity = entities.get(0);
        return RepositoryMapper.map(entity, this.database.getCorporation(entity.getCorporationId()));
    }

    @Override
    public void addCharacter(final EveCharacter character) {
        this.database.addCharacter(RepositoryMapper.map(character));
        final CorporationEntity corp = new CorporationEntity();
        corp.setId(character.getCorporationID());
        corp.setCorporationName(character.getCorporationName());
        this.database.addCorporation(corp);
    }

    @Override
    public void deleteCharacter(final long charID) {
        this.database.removeCharacter(charID);
    }

    @Override
    public EveLocation findLocation(long id) {
        return RepositoryMapper.map(this.database.getLocation(id));
    }

    @Override
    public EveLocation findLocation(String name) {
        return RepositoryMapper.map(this.database.getLocation(name));
    }

    @Override
    public void setLocations(List<EveLocation> locations) {
        final List<LocationEntity> entities = new ArrayList<>(locations.size());
        for (EveLocation l: locations) {
            entities.add(RepositoryMapper.map(l));
        }
        this.database.setLocations(entities);
    }
}
