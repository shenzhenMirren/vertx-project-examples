package github.mirrentools.core.sql;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonObject;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.PropertyKind;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;

import java.util.ArrayList;
import java.util.List;

/**
 * SQL执行器默认实现
 *
 * @author <a href="https://github.com/shenzhenMirren">Mirren</a>
 */
public class SQLExecuteImpl implements SQLExecute {
  /**
   * 日志工具
   */
  public final Logger LOG = LoggerFactory.getLogger(this.getClass());

  /**
   * 客户端
   */
  private final Pool pool;

  public SQLExecuteImpl(Pool pool) {
    super();
    this.pool = pool;
  }


  @Override
  public Pool pool() {
    return pool;
  }


  public void execute(SqlAndParams query, Handler<AsyncResult<RowSet<Row>>> handler) {
    if (query.succeeded()) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("SQL Execute: ");
        LOG.debug(query);
      }
      if (query.getParams() == null) {
        pool.query(query.getSql()).execute(handler);
      } else {
        pool.preparedQuery(query.getSql()).execute(query.getParams(), handler);
      }
    } else {
      handler.handle(Future.failedFuture(query.getSql()));
    }
  }

  @Override
  public void selectAsLong(SqlAndParams query, Handler<AsyncResult<Long>> handler) {
    execute(query, res -> {
      if (res.succeeded()) {
        long result = 0L;
        RowSet<Row> set = res.result();
        if (set != null && set.iterator().hasNext()) {
          result = set.iterator().next().getLong(0);
        }
        handler.handle(Future.succeededFuture(result));
      } else {
        handler.handle(Future.failedFuture(res.cause()));
      }
    });
  }

  public void selectAsObject(SqlAndParams query, Handler<AsyncResult<JsonObject>> handler) {
    execute(query, res -> {
      if (res.succeeded()) {
        RowSet<Row> rowSet = res.result();
        if (rowSet == null || !rowSet.iterator().hasNext()) {
          handler.handle(Future.succeededFuture());
        } else {
          Row row = rowSet.iterator().next();
          handler.handle(Future.succeededFuture(row.toJson()));
        }
      } else {
        handler.handle(Future.failedFuture(res.cause()));
      }
    });
  }

  public void selectAsList(SqlAndParams query, Handler<AsyncResult<List<JsonObject>>> handler) {
    execute(query, res -> {
      if (res.succeeded()) {
        RowSet<Row> rowSet = res.result();
        if (rowSet == null || rowSet.size() <= 0) {
          handler.handle(Future.succeededFuture(new ArrayList<>()));
        } else {
          List<JsonObject> list = new ArrayList<>();
          for (Row row : rowSet) {
            list.add(row.toJson());
          }
          handler.handle(Future.succeededFuture(list));
        }
      } else {
        handler.handle(Future.failedFuture(res.cause()));
      }
    });
  }

  public void update(SqlAndParams query, Handler<AsyncResult<Integer>> handler) {
    execute(query, res -> {
      if (res.succeeded()) {
        if (res.result() == null) {
          handler.handle(Future.succeededFuture(0));
        } else {
          handler.handle(Future.succeededFuture(res.result().rowCount()));
        }
      } else {
        handler.handle(Future.failedFuture(res.cause()));
      }
    });
  }

  public <R> void updateResult(SqlAndParams query, PropertyKind<R> property, Handler<AsyncResult<R>> handler) {
    execute(query, res -> {
      if (res.succeeded()) {
        if (property == null || res.result() == null) {
          handler.handle(Future.succeededFuture());
        } else {
          handler.handle(Future.succeededFuture(res.result().property(property)));
        }
      } else {
        handler.handle(Future.failedFuture(res.cause()));
      }
    });
  }

  public void batch(SqlAndParams query, Handler<AsyncResult<Integer>> handler) {
    if (query.succeeded()) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("SQL Execute Batch:");
        LOG.debug(query);
      }
      pool.preparedQuery(query.getSql())
        .executeBatch(query.getBatchParams())
        .onSuccess(rowSet -> {
          if (rowSet == null) {
            handler.handle(Future.succeededFuture(0));
          } else {
            int count = rowSet.rowCount();
            RowSet<Row> next = rowSet.next();
            while (next != null) {
              count += next.rowCount();
              next = next.next();
            }
            handler.handle(Future.succeededFuture(count));
          }
        })
        .onFailure(err -> handler.handle(Future.failedFuture(err)));
    } else {
      handler.handle(Future.failedFuture(query.getSql()));
    }
  }

  public <R> void batch(SqlAndParams query, PropertyKind<R> property, Handler<AsyncResult<List<R>>> handler) {
    if (query.succeeded()) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("SQL Execute Batch:");
        LOG.debug(query);
      }
      pool
        .preparedQuery(query.getSql())
        .executeBatch(query.getBatchParams())
        .onSuccess(rowSet -> {
          if (rowSet == null) {
            handler.handle(Future.succeededFuture(null));
          } else {
            List<R> result = new ArrayList<>();
            result.add(rowSet.property(property));
            RowSet<Row> next = rowSet.next();
            while (next != null) {
              result.add(next.property(property));
              next = next.next();
            }
            handler.handle(Future.succeededFuture(result));
          }
        })
        .onFailure(err -> handler.handle(Future.failedFuture(err)));
    } else {
      handler.handle(Future.failedFuture(query.getSql()));
    }
  }


}
