package org.devfleet.mob.app.domain.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

final class EveDatabase extends OrmLiteSqliteOpenHelper {
    private static final Logger LOG = LoggerFactory.getLogger(EveDatabase.class);
    private static final String DATABASE_NAME = "fm.db";
    private static final int DATABASE_VERSION = 4;

    private static EveDatabase database;

    private Dao<CharacterEntity, Long> characterDAO;
    private Dao<CorporationEntity, Long> corporationDAO;

    private EveDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        try {
            this.characterDAO = getDao(CharacterEntity.class);
            this.corporationDAO = getDao(CorporationEntity.class);
        }
        catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public static EveDatabase from(final Context context) {
        if (null == database) {
            database = new EveDatabase(context);
        }
        return database;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, CharacterEntity.class);
            TableUtils.createTable(connectionSource, CorporationEntity.class);
        }
        catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        onDelete(database, connectionSource);
        onCreate(database, connectionSource);
    }

    public void onDelete(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.dropTable(connectionSource, CharacterEntity.class, true);
            TableUtils.dropTable(connectionSource, CorporationEntity.class, true);
        }
        catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public List<CharacterEntity> listCharacters() {
        try {
            return this.characterDAO.queryForAll();
        }
        catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
            return Collections.emptyList();
        }
    }

    public List<CharacterEntity> listCharacters(final long limit) {
        try {
            return this.characterDAO.queryBuilder().limit(limit).query();
        }
        catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
            return Collections.emptyList();
        }
    }

    public String getCharacterToken(final long id) {
        try {
            CharacterEntity entity = this.characterDAO
                    .queryBuilder()
                    .selectColumns("refreshToken")
                    .where()
                    .eq("id", id)
                    .queryForFirst();
            return (null == entity) ? null : entity.getRefreshToken();
        }
        catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
            return null;
        }
    }

    public CharacterEntity getCharacter(final long id) {
        try {
            return this.characterDAO.queryForId(id);
        }
        catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
            return null;
        }
    }

    public void addCharacter(CharacterEntity entity) {
        try {
            this.characterDAO.createOrUpdate(entity);
        }
        catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
    }

    public void removeCharacter(long characterID) {
        try {
            this.characterDAO.deleteById(characterID);
        }
        catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
    }

    public CorporationEntity getCorporation(final long id) {
        try {
            return this.corporationDAO.queryForId(id);
        }
        catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
            return null;
        }
    }

    public void addCorporation(CorporationEntity entity) {
        try {
            this.corporationDAO.createOrUpdate(entity);
        }
        catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
    }

    public void removeCorporation(long corporationID) {
        try {
            this.corporationDAO.deleteById(corporationID);
        }
        catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
    }
}
