package com.schule.myfitnessTracker.data.db;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.schule.myfitnessTracker.data.model.RoutePoint;
import java.lang.Class;
import java.lang.Exception;
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
public final class RoutePointDao_Impl implements RoutePointDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<RoutePoint> __insertionAdapterOfRoutePoint;

  private final SharedSQLiteStatement __preparedStmtOfDeletePointsForRun;

  public RoutePointDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfRoutePoint = new EntityInsertionAdapter<RoutePoint>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `route_points` (`id`,`runId`,`latitude`,`longitude`,`altitude`,`speed`,`accuracy`,`timestamp`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final RoutePoint entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getRunId());
        statement.bindDouble(3, entity.getLatitude());
        statement.bindDouble(4, entity.getLongitude());
        statement.bindDouble(5, entity.getAltitude());
        statement.bindDouble(6, entity.getSpeed());
        statement.bindDouble(7, entity.getAccuracy());
        statement.bindLong(8, entity.getTimestamp());
      }
    };
    this.__preparedStmtOfDeletePointsForRun = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM route_points WHERE runId = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertPoint(final RoutePoint point, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfRoutePoint.insert(point);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertPoints(final List<RoutePoint> points,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfRoutePoint.insert(points);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deletePointsForRun(final long runId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeletePointsForRun.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, runId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeletePointsForRun.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getPointsForRun(final long runId,
      final Continuation<? super List<RoutePoint>> $completion) {
    final String _sql = "SELECT * FROM route_points WHERE runId = ? ORDER BY timestamp ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, runId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<RoutePoint>>() {
      @Override
      @NonNull
      public List<RoutePoint> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfRunId = CursorUtil.getColumnIndexOrThrow(_cursor, "runId");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfAltitude = CursorUtil.getColumnIndexOrThrow(_cursor, "altitude");
          final int _cursorIndexOfSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "speed");
          final int _cursorIndexOfAccuracy = CursorUtil.getColumnIndexOrThrow(_cursor, "accuracy");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final List<RoutePoint> _result = new ArrayList<RoutePoint>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final RoutePoint _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpRunId;
            _tmpRunId = _cursor.getLong(_cursorIndexOfRunId);
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            final double _tmpAltitude;
            _tmpAltitude = _cursor.getDouble(_cursorIndexOfAltitude);
            final float _tmpSpeed;
            _tmpSpeed = _cursor.getFloat(_cursorIndexOfSpeed);
            final float _tmpAccuracy;
            _tmpAccuracy = _cursor.getFloat(_cursorIndexOfAccuracy);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            _item = new RoutePoint(_tmpId,_tmpRunId,_tmpLatitude,_tmpLongitude,_tmpAltitude,_tmpSpeed,_tmpAccuracy,_tmpTimestamp);
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

  @Override
  public LiveData<List<RoutePoint>> getPointsForRunLive(final long runId) {
    final String _sql = "SELECT * FROM route_points WHERE runId = ? ORDER BY timestamp ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, runId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"route_points"}, false, new Callable<List<RoutePoint>>() {
      @Override
      @Nullable
      public List<RoutePoint> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfRunId = CursorUtil.getColumnIndexOrThrow(_cursor, "runId");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfAltitude = CursorUtil.getColumnIndexOrThrow(_cursor, "altitude");
          final int _cursorIndexOfSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "speed");
          final int _cursorIndexOfAccuracy = CursorUtil.getColumnIndexOrThrow(_cursor, "accuracy");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final List<RoutePoint> _result = new ArrayList<RoutePoint>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final RoutePoint _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpRunId;
            _tmpRunId = _cursor.getLong(_cursorIndexOfRunId);
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            final double _tmpAltitude;
            _tmpAltitude = _cursor.getDouble(_cursorIndexOfAltitude);
            final float _tmpSpeed;
            _tmpSpeed = _cursor.getFloat(_cursorIndexOfSpeed);
            final float _tmpAccuracy;
            _tmpAccuracy = _cursor.getFloat(_cursorIndexOfAccuracy);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            _item = new RoutePoint(_tmpId,_tmpRunId,_tmpLatitude,_tmpLongitude,_tmpAltitude,_tmpSpeed,_tmpAccuracy,_tmpTimestamp);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
