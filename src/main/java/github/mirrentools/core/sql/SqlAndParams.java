package github.mirrentools.core.sql;

import java.util.List;

import io.vertx.sqlclient.Tuple;

/**
 * 用于生成数据库语句时返回SQL语句与参数
 *
 * @author <a href="https://github.com/shenzhenMirren">Mirren</a>
 *
 */
public class SqlAndParams {
	/** SQL语句 */
	private String sql;
	/** 参数 */
	private Tuple params;
	/** 批量参数 */
	private List<Tuple> batchParams;
	/** 生成语句是否成功 */
	private boolean succeeded = true;
	/**
	 * 创建一个新的SqlAndParams
	 */
	public SqlAndParams() {
		super();
	}
	/**
	 * 创建一个新的SqlAndParams
	 *
	 * @param sql
	 *          SQL语句
	 */
	public SqlAndParams(String sql) {
		super();
		this.sql = sql;
	}

	/**
	 * 创建一个新的SqlAndParams
	 *
	 * @param succeeded
	 *          是否成功,true=成功,false=失败
	 * @param sql
	 *          SQL语句或者失败语句
	 */
	public SqlAndParams(boolean succeeded, String sql) {
		super();
		this.succeeded = succeeded;
		this.sql = sql;
	}

	/**
	 * 创建一个新的SqlAndParams
	 *
	 * @param sql
	 *          SQL语句
	 * @param params
	 *          参数
	 */
	public SqlAndParams(String sql, Tuple params) {
		super();
		this.sql = sql;
		this.params = params;
	}
	/**
	 * 创建一个新的SqlAndParams
	 *
	 * @param sql
	 *          SQL语句
	 * @param batchParams
	 *          参数集合
	 */
	public SqlAndParams(String sql, List<Tuple> batchParams) {
		super();
		this.sql = sql;
		this.batchParams = batchParams;
	}

	/**
	 * 获得SQL语句,如果失败时则为错误语句
	 */
	public String getSql() {
		return sql;
	}
	/**
	 * 设置SQL语句,如果失败时则为错误语句
	 */
	public SqlAndParams setSql(String sql) {
		this.sql = sql;
		return this;
	}
	/**
	 * 获得参数
	 */
	public Tuple getParams() {
		return params;
	}
	/**
	 * 设置参数
	 */
	public SqlAndParams setParams(Tuple params) {
		this.params = params;
		return this;
	}

	/**
	 * 获取参数集合
	 */
	public List<Tuple> getBatchParams() {
		return batchParams;
	}
	/**
	 * 设置参数集合
	 */
	public SqlAndParams setBatchParams(List<Tuple> batchParams) {
		this.batchParams = batchParams;
		return this;
	}
	/**
	 * 获取是否成功
	 */
	public boolean succeeded() {
		return succeeded;
	}
	/**
	 * 设置是否成功
	 */
	public SqlAndParams setSucceeded(boolean succeeded) {
		this.succeeded = succeeded;
		return this;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("SqlAndParams [succeeded=").append(succeeded).append(",");
		result.append("sql=").append(sql).append(",");
		result.append("params=").append(params == null ? "null" : params.deepToString()).append(",");
		result.append("batchParams=");
		if (batchParams == null) {
			result.append("null");
		} else {
			result.append("(");
			for (Tuple tuple : batchParams) {
				result.append(tuple == null ? "null" : tuple.deepToString());
			}
			result.append(")");
		}
		result.append("]");
		return result.toString();
	}

}
