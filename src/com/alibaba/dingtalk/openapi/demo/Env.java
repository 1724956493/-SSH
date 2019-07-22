package com.alibaba.dingtalk.openapi.demo;



public class Env {
	
	public static final String OAPI_HOST = "https://oapi.dingtalk.com";	   //"http://192.168.1.106:8088/AndroidWeb/userInfo.jsp";//
	public static final String OA_BACKGROUND_URL = ""; 
	//公司
//	注意：当应用类中直接使用下列值而不通过get获取时，在发布的时候，会直接写入class中，所以，当更改时必须换成正式数据，再次编译后再发布
/*	public static final String CORP_ID ="ding7acc0673f06aa78e";//公司
	public static final String CORP_SECRET = "fm8X7kyK_6_G1I5VpcZEgU6w1Vm_b9nMTIAM21xEGicM9H7CZFt4sNxJ96WHs_ok";//公司
	public static final String SSO_Secret = "0jEOh8TZUtxjHg9sjjylJD3COxhswArduxwsmaO3bAOf2l_lIlOUfIWrpZXjl13Z";//公司
*/
	//自己
	public static final String CORP_ID ="ding92ebe605dc60c58a35c2f4657eb6378f";//自己
	public static final String CORP_SECRET = "ScI53o-o_51uvv6bIJAp4Et5YRNG2Cs1mE782oinrEVvW2ZQ6vjP2ryCvuQOC2QE";//自己
	public static final String SSO_Secret = "l8izVy4nqi-DGP6QPP0s9IyyseImcYU2TLvbrjSoQ7MmW6gJFus35J53-6fLA06K";//自己

	
	public static String suiteTicket; 
	public static String authCode; 
	public static String suiteToken; 

	public static final String CREATE_SUITE_KEY = "suite4xxxxxxxxxxxxxxx";
	public static final String SUITE_KEY = "";
	public static final String SUITE_SECRET = "";
	public static final String TOKEN = "";
	public static final String ENCODING_AES_KEY = "";
	public static String getOapiHost() {
		return OAPI_HOST;
	}
	public static String getOaBackgroundUrl() {
		return OA_BACKGROUND_URL;
	}
	public static String getCorpId() {
		return CORP_ID;
	}
	public static String getCorpSecret() {
		return CORP_SECRET;
	}
	public static String getSsoSecret() {
		return SSO_Secret;
	}
	public static String getSuiteTicket() {
		return suiteTicket;
	}
	public static String getAuthCode() {
		return authCode;
	}
	public static String getSuiteToken() {
		return suiteToken;
	}
	public static String getCreateSuiteKey() {
		return CREATE_SUITE_KEY;
	}
	public static String getSuiteKey() {
		return SUITE_KEY;
	}
	public static String getSuiteSecret() {
		return SUITE_SECRET;
	}
	public static String getToken() {
		return TOKEN;
	}
	public static String getEncodingAesKey() {
		return ENCODING_AES_KEY;
	}
	
	
	
	
	
	
	
	
}
