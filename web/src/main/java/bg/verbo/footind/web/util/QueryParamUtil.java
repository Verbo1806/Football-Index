package bg.verbo.footind.web.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

public class QueryParamUtil {
	
	private QueryParamUtil() {
		throw new IllegalStateException("Utility class");
	}
	
	@SafeVarargs
	public static Map<String, String> build(Pair<String, String>... pairs) {
		Map<String, String> params = new HashMap<String, String>();
		
		for (Pair<String, String> pair : pairs) {
			if (!StringUtils.isEmpty(pair.getFirst()) && !StringUtils.isEmpty(pair.getSecond())) {
				params.put(pair.getFirst(), pair.getSecond());
			}
		}
		
		return params;
	}
}
