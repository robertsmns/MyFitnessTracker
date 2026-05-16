package com.schule.myfitnessTracker.data.db;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.schule.myfitnessTracker.data.model.Run;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Float;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class RunDao_Impl implements RunDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Run> __insertionAdapterOfRun;

  private final EntityDeletionOrUpdateAdapter<Run> __deletionAdapterOfRun;

  private final EntityDeletionOrUpdateAdapter<Run> __updateAdapterOfRun;

  public RunDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfRun = new EntityInsertionAdapter<Run>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `runs` (`id`,`startTime`,`endTime`,`distanceMeters`,`avgSpeedKmh`,`steps`,`calories`,`elevationGain`,`isActive`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Run entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getStartTime());
        statement.bindLong(3, entity.getEndTime());
        statement.bindDouble(4, entity.getDistanceMeters());
        statement.bindDouble(5, entity.getAvgSpeedKmh());
        statement.bindLong(6, entity.getSteps());
        statement.bindLong(7, entity.getCalories());
        statement.bindDouble(8, entity.getElevationGain());
        final int _tmp = entity.isActive() ? 1 : 0;
        statement.bindLong(9, _tmp);
      }
    };
    this.__deletionAdapterOfRun = new EntityDeletionOrUpdateAdapter<Run>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `runs` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Run entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfRun = new EntityDeletionOrUpdateAdapter<Run>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `runs` SET `id` = ?,`startTime` = ?,`endTime` = ?,`distanceMeters` = ?,`avgSpeedKmh` = ?,`steps` = ?,`calories` = ?,`elevationGain` = ?,`isActive` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Run entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getStartTime());
        statement.bindLong(3, entity.getEndTime());
        statement.bindDouble(4, entity.getDistanceMeters());
        statement.bindDouble(5, entity.getAvgSpeedKmh());
        statement.bindLong(6, entity.getSteps());
        statement.bindLong(7, entity.getCalories());
        statement.bindDouble(8, entity.getElevationGain());
        final int _tmp = entity.isActive() ? 1 : 0;
        statement.bindLong(9, _tmp);
        statement.bindLong(10, entity.getId());
      }
    };
  }

  @Override
  public Object insertRun(final Run run, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfRun.insertAndReturnId(run);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteRun(final Run run, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfRun.handle(run);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateRun(final Run run, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfRun.handle(run);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public LiveData<List<Run>> getAllRuns() {
    final String _sql = "SELECT * FROM runs ORDER BY startTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"runs"}, false, new Callable<List<Run>>() {
      @Override
      @Nullable
      public List<Run> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfDistanceMeters = CursorUtil.getColumnIndexOrThrow(_cursor, "distanceMeters");
          final int _cursorIndexOfAvgSpeedKmh = CursorUtil.getColumnIndexOrThrow(_cursor, "avgSpeedKmh");
          final int _cursorIndexOfSteps = CursorUtil.getColumnIndexOrThrow(_cursor, "steps");
          final int _cursorIndexOfCalories = CursorUtil.getColumnIndexOrThrow(_cursor, "calories");
          final int _cursorIndexOfElevationGain = CursorUtil.getColumnIndexOrThrow(_cursor, "elevationGain");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final List<Run> _result = new ArrayList<Run>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Run _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpStartTime;
            _tmpStartTime = _cursor.getLong(_cursorIndexOfStartTime);
            final long _tmpEndTime;
            _tmpEndTime = _cursor.getLong(_cursorIndexOfEndTime);
            final float _tmpDistanceMeters;
            _tmpDistanceMeters = _cursor.getFloat(_cursorIndexOfDistanceMeters);
            final float _tmpAvgSpeedKmh;
            _tmpAvgSpeedKmh = _cursor.getFloat(_cursorIndexOfAvgSpeedKmh);
            final int _tmpSteps;
            _tmpSteps = _cursor.getInt(_cursorIndexOfSteps);
            final int _tmpCalories;
            _tmpCalories = _cursor.getInt(_cursorIndexOfCalories);
            final float _tmpElevationGain;
            _tmpElevationGain = _cursor.getFloat(_cursorIndexOfElevationGain);
            final boolean _tmpIsActive;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp != 0;
            _item = new Run(_tmpId,_tmpStartTime,_tmpEndTime,_tmpDistanceMeters,_tmpAvgSpeedKmh,_tmpSteps,_tmpCalories,_tmpElevationGain,_tmpIsActive);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<Float> getTodayDistanceLive() {
    final String _sql = "\n"
            + "        SELECT COALESCE(SUM(distanceMeters), 0) \n"
            + "        FROM runs \n"
            + "        WHERE DATE(startTime / 1000, 'unixepoch', 'localtime') = DATE('now', 'localtime')\n"
            + "          AND isActive = 0\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"runs"}, false, new Callable<Float>() {
      @Override
      @Nullable
      public Float call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Float _result;
          if (_cursor.moveToFirst()) {
            final Float _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getFloat(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getActiveRun(final Continuation<? super Run> $completion) {
    final String _sql = "SELECT * FROM runs WHERE isActive = 1 LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Run>() {
      @Override
      @Nullable
      public Run call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfDistanceMeters = CursorUtil.getColumnIndexOrThrow(_cursor, "distanceMeters");
          final int _cursorIndexOfAvgSpeedKmh = CursorUtil.getColumnIndexOrThrow(_cursor, "avgSpeedKmh");
          final int _cursorIndexOfSteps = CursorUtil.getColumnIndexOrThrow(_cursor, "steps");
          final int _cursorIndexOfCalories = CursorUtil.getColumnIndexOrThrow(_cursor, "calories");
          final int _cursorIndexOfElevationGain = CursorUtil.getColumnIndexOrThrow(_cursor, "elevationGain");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final Run _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpStartTime;
            _tmpStartTime = _cursor.getLong(_cursorIndexOfStartTime);
            final long _tmpEndTime;
            _tmpEndTime = _cursor.getLong(_cursorIndexOfEndTime);
            final float _tmpDistanceMeters;
            _tmpDistanceMeters = _cursor.getFloat(_cursorIndexOfDistanceMeters);
            final float _tmpAvgSpeedKmh;
            _tmpAvgSpeedKmh = _cursor.getFloat(_cursorIndexOfAvgSpeedKmh);
            final int _tmpSteps;
            _tmpSteps = _cursor.getInt(_cursorIndexOfSteps);
            final int _tmpCalories;
            _tmpCalories = _cursor.getInt(_cursorIndexOfCalories);
            final float _tmpElevationGain;
            _tmpElevationGain = _cursor.getFloat(_cursorIndexOfElevationGain);
            final boolean _tmpIsActive;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp != 0;
            _result = new Run(_tmpId,_tmpStartTime,_tmpEndTime,_tmpDistanceMeters,_tmpAvgSpeedKmh,_tmpSteps,_tmpCalories,_tmpElevationGain,_tmpIsActive);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public LiveData<Run> getLastRun() {
    final String _sql = "SELECT * FROM runs ORDER BY startTime DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"runs"}, false, new Callable<Run>() {
      @Override
      @Nullable
      public Run call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfDistanceMeters = CursorUtil.getColumnIndexOrThrow(_cursor, "distanceMeters");
          final int _cursorIndexOfAvgSpeedKmh = CursorUtil.getColumnIndexOrThrow(_cursor, "avgSpeedKmh");
          final int _cursorIndexOfSteps = CursorUtil.getColumnIndexOrThrow(_cursor, "steps");
          final int _cursorIndexOfCalories = CursorUtil.getColumnIndexOrThrow(_cursor, "calories");
          final int _cursorIndexOfElevationGain = CursorUtil.getColumnIndexOrThrow(_cursor, "elevationGain");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final Run _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpStartTime;
            _tmpStartTime = _cursor.getLong(_cursorIndexOfStartTime);
            final long _tmpEndTime;
            _tmpEndTime = _cursor.getLong(_cursorIndexOfEndTime);
            final float _tmpDistanceMeters;
            _tmpDistanceMeters = _cursor.getFloat(_cursorIndexOfDistanceMeters);
            final float _tmpAvgSpeedKmh;
            _tmpAvgSpeedKmh = _cursor.getFloat(_cursorIndexOfAvgSpeedKmh);
            final int _tmpSteps;
            _tmpSteps = _cursor.getInt(_cursorIndexOfSteps);
            final int _tmpCalories;
            _tmpCalories = _cursor.getInt(_cursorIndexOfCalories);
            final float _tmpElevationGain;
            _tmpElevationGain = _cursor.getFloat(_cursorIndexOfElevationGain);
            final boolean _tmpIsActive;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp != 0;
            _result = new Run(_tmpId,_tmpStartTime,_tmpEndTime,_tmpDistanceMeters,_tmpAvgSpeedKmh,_tmpSteps,_tmpCalories,_tmpElevationGain,_tmpIsActive);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<Float> getDistanceSince(final long since) {
    final String _sql = "\n"
            + "        SELECT COALESCE(SUM(distanceMeters), 0) \n"
            + "        FROM runs \n"
            + "        WHERE startTime >= ? AND isActive = 0\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, since);
    return __db.getInvalidationTracker().createLiveData(new String[] {"runs"}, false, new Callable<Float>() {
      @Override
      @Nullable
      public Float call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Float _result;
          if (_cursor.moveToFirst()) {
            final Float _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getFloat(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<Integer> getTodayStepsLive() {
    final String _sql = "\n"
            + "        SELECT COALESCE(SUM(steps), 0) \n"
            + "        FROM runs \n"
            + "        WHERE DATE(startTime / 1000, 'unixepoch', 'localtime') = DATE('now', 'localtime')\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"runs"}, false, new Callable<Integer>() {
      @Override
      @Nullable
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<Float> getAvgSpeedLive() {
    final String _sql = "SELECT COALESCE(AVG(avgSpeedKmh), 0) FROM runs WHERE isActive = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"runs"}, false, new Callable<Float>() {
      @Override
      @Nullable
      public Float call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Float _result;
          if (_cursor.moveToFirst()) {
            final Float _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getFloat(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getRunById(final long runId, final Continuation<? super Run> $completion) {
    final String _sql = "SELECT * FROM runs WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, runId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Run>() {
      @Override
      @Nullable
      public Run call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfDistanceMeters = CursorUtil.getColumnIndexOrThrow(_cursor, "distanceMeters");
          final int _cursorIndexOfAvgSpeedKmh = CursorUtil.getColumnIndexOrThrow(_cursor, "avgSpeedKmh");
          final int _cursorIndexOfSteps = CursorUtil.getColumnIndexOrThrow(_cursor, "steps");
          final int _cursorIndexOfCalories = CursorUtil.getColumnIndexOrThrow(_cursor, "calories");
          final int _cursorIndexOfElevationGain = CursorUtil.getColumnIndexOrThrow(_cursor, "elevationGain");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final Run _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpStartTime;
            _tmpStartTime = _cursor.getLong(_cursorIndexOfStartTime);
            final long _tmpEndTime;
            _tmpEndTime = _cursor.getLong(_cursorIndexOfEndTime);
            final float _tmpDistanceMeters;
            _tmpDistanceMeters = _cursor.getFloat(_cursorIndexOfDistanceMeters);
            final float _tmpAvgSpeedKmh;
            _tmpAvgSpeedKmh = _cursor.getFloat(_cursorIndexOfAvgSpeedKmh);
            final int _tmpSteps;
            _tmpSteps = _cursor.getInt(_cursorIndexOfSteps);
            final int _tmpCalories;
            _tmpCalories = _cursor.getInt(_cursorIndexOfCalories);
            final float _tmpElevationGain;
            _tmpElevationGain = _cursor.getFloat(_cursorIndexOfElevationGain);
            final boolean _tmpIsActive;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp != 0;
            _result = new Run(_tmpId,_tmpStartTime,_tmpEndTime,_tmpDistanceMeters,_tmpAvgSpeedKmh,_tmpSteps,_tmpCalories,_tmpElevationGain,_tmpIsActive);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getWeeklyStats(final long since,
      final Continuation<? super List<DailyStats>> $completion) {
    final String _sql = "\n"
            + "        SELECT DATE(startTime / 1000, 'unixepoch', 'localtime') AS day,\n"
            + "               SUM(distanceMeters) / 1000.0 AS distanceKm\n"
            + "        FROM runs\n"
            + "        WHERE startTime >= ? AND isActive = 0\n"
            + "        GROUP BY day\n"
            + "        ORDER BY day ASC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, since);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<DailyStats>>() {
      @Override
      @NonNull
      public List<DailyStats> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfDay = 0;
          final int _cursorIndexOfDistanceKm = 1;
          final List<DailyStats> _result = new ArrayList<DailyStats>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final DailyStats _item;
            final String _tmpDay;
            if (_cursor.isNull(_cursorIndexOfDay)) {
              _tmpDay = null;
            } else {
              _tmpDay = _cursor.getString(_cursorIndexOfDay);
            }
            final float _tmpDistanceKm;
            _tmpDistanceKm = _cursor.getFloat(_cursorIndexOfDistanceKm);
            _item = new DailyStats(_tmpDay,_tmpDistanceKm);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
