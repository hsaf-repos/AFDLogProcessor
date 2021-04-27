import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class FilesExtention{

	static  Map<String, String> filesExtentionMap;

	
	
	static{
		Map<String, String> tempMap = new HashMap<String, String>();
		tempMap.put("h01", ".gz");
		tempMap.put("h02", ".gz");	
		tempMap.put("h02B", ".gz");
		tempMap.put("h03", ".gz");	
		tempMap.put("h03B", ".gz");
		tempMap.put("h05", ".gz");	
		tempMap.put("h05B", ".gz");
		tempMap.put("h15", ".gz");
		tempMap.put("h08", ".nc");
		tempMap.put("h14", ".bz2");
		tempMap.put("h10", ".gz");
		tempMap.put("h11", ".gz");
		tempMap.put("h12", ".gz");
		tempMap.put("h13", ".gz");		
		tempMap.put("h14", ".bz2");
		tempMap.put("h16", ".gz");
		tempMap.put("h101",".gz");
		tempMap.put("h102",".gz");
		tempMap.put("h103",".gz");
		
		filesExtentionMap = Collections.unmodifiableMap(tempMap);
	}
}
