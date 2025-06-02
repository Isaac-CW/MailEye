package MailViewer.authorization;

public class GoogleURLs implements OAuthURL {
    private static final String authServer = "https://accounts.google.com/o/oauth2/v2/auth";
    private static final String tokenServer = "https://oauth2.googleapis.com/token";

    @Override
    public String getAuthorizationServerURL() {
        return authServer;
    }

    @Override
    public String getTokenRequestURL() {
        return tokenServer;
    }
}
