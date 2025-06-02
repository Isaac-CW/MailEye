package MailViewer.authorization;

/**
 * OAuthURL contains a set of URLs used to authorize access to a mailbox with OAuth
 */
public interface OAuthURL {
    public String getTokenRequestURL();
    public String getAuthorizationServerURL();
}
