package lp.util;

public class FormatJsonReturnList {
	//格式化json
	public static String Fomat(String JsonResource) {
		if (JsonResource.contains("[")) {
			return JsonResource;
		} 

			return ("[" + JsonResource + "]");
	}
}
