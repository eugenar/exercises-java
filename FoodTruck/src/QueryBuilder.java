import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Builds a query string conform to SoQL API 
 * TODO add support for GROUP BY
 *
 */
public class QueryBuilder {

	public static class Builder {
		public String toString() {
			String queryString = null;
			try {
				queryString = URLEncoder.encode(
						String.format("SELECT %1$s WHERE %2$s ORDER BY %3$s LIMIT %4$d OFFSET %5$d", selectClause,
								whereClause, orderClause, limit, offset),
						StandardCharsets.UTF_8.toString());
			} catch (UnsupportedEncodingException e) {
				// TODO handle
			}
			return String.format("%1$s?$query=%2$s", url, queryString);
		}

		public Builder setUrl(String url) {
			this.url = url;
			return this;
		}

		public Builder setSelect(String selectClause) {
			this.selectClause = selectClause;
			return this;
		}

		public Builder setWhere(String whereClause) {
			this.whereClause = whereClause;
			return this;
		}

		public Builder setOrder(String orderClause) {
			this.orderClause = orderClause;
			return this;
		}

		public Builder setLimit(int limit) {
			this.limit = limit;
			return this;
		}

		public Builder setOffset(int offset) {
			this.offset = offset;
			return this;
		}

		String url;
		String selectClause;
		String whereClause;
		String orderClause;
		int limit;
		int offset;
	}

	public static Builder builder() {
		return new Builder();
	}

	private QueryBuilder(Builder builder) {
		url = builder.url;
		selectClause = builder.selectClause;
		whereClause = builder.whereClause;
		orderClause = builder.orderClause;
	}

	String url;
	String selectClause;
	String whereClause;
	String orderClause;
	int limit;
	int offset;
}
