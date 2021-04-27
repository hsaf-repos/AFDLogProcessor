import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Offset {

	static Map<String, Long> offsetMap;

	static {
		Map<String, Long> tempMap = new HashMap<String, Long>();
		tempMap.put("h01", 30L);
		tempMap.put("h02", 0L);
		tempMap.put("h02B", 100L);
		tempMap.put("h03B", 15L);
		tempMap.put("h03", 0L);
		tempMap.put("h04", 0L);
		tempMap.put("h05", 15L);
		tempMap.put("h05B", 0L);
		tempMap.put("h15", 15L);
		tempMap.put("h08", 0L);
		tempMap.put("h14", 0L);
		tempMap.put("h10", 0L);
		tempMap.put("h11", 0L);
		tempMap.put("h12", 0L);
		tempMap.put("h13", 0L);
		tempMap.put("h14", 0L);
		tempMap.put("h16", 0L);
		tempMap.put("h101", 0L);
		tempMap.put("h102", 0L);
		tempMap.put("h103", 0L);

		offsetMap = Collections.unmodifiableMap(tempMap);

	}
}
