import java.text.SimpleDateFormat;
import java.util.Date;

public class FoodTruckFinder {

	// TODO make fields' values configurable
	static String apiUrl = "https://data.sfgov.org/resource/jjew-r69b.csv";
	static String selectClause = "applicant, location";
	static String orderClause = "applicant";

	int limit;
	int offset;

	/**
	 * @param limit
	 * @param offset
	 */
	FoodTruckFinder(int limit, int offset) {
		this.limit = limit;
		this.offset = offset;
	}

	String getUrl() {
		return apiUrl;
	}

	String getSelectClause() {
		return selectClause;
	}

	String getWhereClause() {
		Date now = new Date();
		String time = new SimpleDateFormat("HH:mm").format(now);
		String day = new SimpleDateFormat("EEEE").format(now);
		return String.format("'%1$s' BETWEEN start24 AND end24 AND dayofweekstr='%2$s'", time, day);
	}

	String getOrderClause() {
		return orderClause;
	}

	String getQuery() {
		return QueryBuilder.builder().setUrl(getUrl()).setSelect(getSelectClause()).setWhere(getWhereClause())
				.setOrder(getOrderClause()).setLimit(limit).setOffset(offset).toString();
	}

	public static String query(int limit, int offset) {
		return new FoodTruckFinder(limit, offset).getQuery();
	}
}
