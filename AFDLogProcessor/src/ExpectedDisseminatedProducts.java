import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class ExpectedDisseminatedProducts {

	static  Map<String, Integer> expFilesMap;	
	
	static{
		Map<String, Integer> tempMap = new HashMap<String, Integer>();
		tempMap.put("h01", 57);
		tempMap.put("h02", 150);
		tempMap.put("h02B", 50);
		tempMap.put("h03B", 96);
		tempMap.put("h03", 96);	
		tempMap.put("h04", 1);
		tempMap.put("h05", 32);
		tempMap.put("h05B", 32);
		tempMap.put("h15", 96);
		tempMap.put("h08", 46);
		tempMap.put("h14", 1);
		tempMap.put("h10", 1);
		tempMap.put("h11", 1);
		tempMap.put("h12", 1);
		tempMap.put("h13", 1);	
		tempMap.put("h16", 480);
		tempMap.put("h101",480);
		tempMap.put("h102",480);
		tempMap.put("h103",480);
		
		expFilesMap = Collections.unmodifiableMap(tempMap);
	
	
}
}
