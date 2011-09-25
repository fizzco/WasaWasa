package net.fizzco.wassrclient.wasawasa;

public class WassrStatus {
    private String userloginId;
	private String screenName;
    private String text;
    private String id;
    
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
    
    public String getUserloginId() {
		return userloginId;
	}

	public void setUserloginId(String userloginId) {
		this.userloginId = userloginId;
	}

	private String profileImageUrl;

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

	public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
