import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Timeliness {

	static  Map<String, Long> timelinessMap;
	static final Map<String, Long> offsetMap     = new HashMap<String, Long>();
	
	
	static{
		Map<String, Long> tempMap = new HashMap<String, Long>();
		tempMap.put("h01", 150L);
		tempMap.put("h02", 60L);
		tempMap.put("h02B", 150L);
		tempMap.put("h03B", 15L);
		tempMap.put("h03", 15L);	
		tempMap.put("h04", 1L);
		tempMap.put("h05", 15L);
		tempMap.put("h05B", 25L);
		tempMap.put("h15", 15L);
		tempMap.put("h08", 130L);
		tempMap.put("h14", 2160L);
		tempMap.put("h10", 1800L);
		tempMap.put("h11", 3600L);
		tempMap.put("h12", 1800L);
		tempMap.put("h13", 3600L);		
		tempMap.put("h14", 1440L);
		tempMap.put("h16", 120L);
		tempMap.put("h101",120L);
		tempMap.put("h102",120L);
		tempMap.put("h103",120L);
		
		timelinessMap = Collections.unmodifiableMap(tempMap);
	}
}