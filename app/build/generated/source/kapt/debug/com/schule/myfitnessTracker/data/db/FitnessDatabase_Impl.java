package com.schule.myfitnessTracker.data.db;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class FitnessDatabase_Impl extends FitnessDatabase {
  private volatile RunDao _runDao;

  private volatile RoutePointDao _routePointDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `runs` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `startTime` INTEGER NOT NULL, `endTime` INTEGER NOT NULL, `distanceMeters` REAL NOT NULL, `avgSpeedKmh` REAL NOT NULL, `steps` INTEGER NOT NULL, `calories` INTEGER NOT NULL, `elevationGain` REAL NOT NULL, `isActive` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `route_points` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `runId` INTEGER NOT NULL, `latitude` REAL NOT NULL, `longitude` REAL NOT NULL, `altitude` REAL NOT NULL, `speed` REAL NOT NULL, `accuracy` REAL NOT NULL, `timestamp` INTEGER NOT NULL, FOREIGN KEY(`runId`) REFERENCES `runs`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_route_points_runId` ON `route_points` (`runId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '28319f153215cd5d376b92b7a63a7613')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `runs`");
        db.execSQL("DROP TABLE IF EXISTS `route_points`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsRuns = new HashMap<String, TableInfo.Column>(9);
        _columnsRuns.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRuns.put("startTime", new TableInfo.Column("startTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRuns.put("endTime", new TableInfo.Column("endTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRuns.put("distanceMeters", new TableInfo.Column("distanceMeters", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRuns.put("avgSpeedKmh", new TableInfo.Column("avgSpeedKmh", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRuns.put("steps", new TableInfo.Column("steps", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRuns.put("calories", new TableInfo.Column("calories", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRuns.put("elevationGain", new TableInfo.Column("elevationGain", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRuns.put("isActive", new TableInfo.Column("isActive", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRuns = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesRuns = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoRuns = new TableInfo("runs", _columnsRuns, _foreignKeysRuns, _indicesRuns);
        final TableInfo _existingRuns = TableInfo.read(db, "runs");
        if (!_infoRuns.equals(_existingRuns)) {
          return new RoomOpenHelper.ValidationResult(false, "runs(com.schule.myfitnessTracker.data.model.Run).\n"
                  + " Expected:\n" + _infoRuns + "\n"
                  + " Found:\n" + _existingRuns);
        }
        final HashMap<String, TableInfo.Column> _columnsRoutePoints = new HashMap<String, TableInfo.Column>(8);
        _columnsRoutePoints.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRoutePoints.put("runId", new TableInfo.Column("runId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRoutePoints.put("latitude", new TableInfo.Column("latitude", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRoutePoints.put("longitude", new TableInfo.Column("longitude", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRoutePoints.put("altitude", new TableInfo.Column("altitude", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRoutePoints.put("speed", new TableInfo.Column("speed", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRoutePoints.put("accuracy", new TableInfo.Column("accuracy", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRoutePoints.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRoutePoints = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysRoutePoints.add(new TableInfo.ForeignKey("runs", "CASCADE", "NO ACTION", Arrays.asList("runId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesRoutePoints = new HashSet<TableInfo.Index>(1);
        _indicesRoutePoints.add(new TableInfo.Index("index_route_points_runId", false, Arrays.asList("runId"), Arrays.asList("ASC")));
        final TableInfo _infoRoutePoints = new TableInfo("route_points", _columnsRoutePoints, _foreignKeysRoutePoints, _indicesRoutePoints);
        final TableInfo _existingRoutePoints = TableInfo.read(db, "route_points");
        if (!_infoRoutePoints.equals(_existingRoutePoints)) {
          return new RoomOpenHelper.ValidationResult(false, "route_points(com.schule.myfitnessTracker.data.model.RoutePoint).\n"
                  + " Expected:\n" + _infoRoutePoints + "\n"
                  + " Found:\n" + _existingRoutePoints);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "28319f153215cd5d376b92b7a63a7613", "19d7e5eab00267b4646f4d836a291531");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "runs","route_points");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `runs`");
      _db.execSQL("DELETE FROM `route_points`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(RunDao.class, RunDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(RoutePointDao.class, RoutePointDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public RunDao runDao() {
    if (_runDao != null) {
      return _runDao;
    } else {
      synchronized(this) {
        if(_runDao == null) {
          _runDao = new RunDao_Impl(this);
        }
        return _runDao;
      }
    }
  }

  @Override
  public RoutePointDao routePointDao() {
    if (_routePointDao != null) {
      return _routePointDao;
    } else {
      synchronized(this) {
        if(_routePointDao == null) {
          _routePointDao = new RoutePointDao_Impl(this);
        }
        return _routePointDao;
      }
    }
  }
}
