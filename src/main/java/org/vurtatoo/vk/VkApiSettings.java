package org.vurtatoo.vk;


public class VkApiSettings {

	private String appID;
	private String appSecret;
	private String uriWebApp;
	
	private String access_token;
	private String expires_in;
	private String user_id;
	
	private boolean standalone;
	
	
	public VkApiSettings(String appID, String appSecret ,String uriWebApp,boolean standaloneApp) {
		this.appID = appID;
		this.appSecret = appSecret;
		this.uriWebApp = uriWebApp;
		standalone = standaloneApp;
	}
	
	public void setAuthData(String access_token, String expires_in, String user_id) {
		this.access_token = access_token;
		this.expires_in = expires_in;
		this.user_id = user_id;
				
	}

	public String getAppID() {
		return appID;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public String getUriWebApp() {
		return uriWebApp;
	}

	public String getAccess_token() {
		return access_token;
	}

	public String getExpires_in() {
		return expires_in;
	}

	public String getUser_id() {
		return user_id;
	}

	@Override
	public String toString() {
		return "VkApiSettings [appID=" + appID + ", appSecret=" + appSecret
				+ ", uriWebApp=" + uriWebApp + ", access_token=" + access_token
				+ ", expires_in=" + expires_in + ", user_id=" + user_id + "]";
	}

	public boolean isStandalone() {
		return standalone;
	}
	
	
}
