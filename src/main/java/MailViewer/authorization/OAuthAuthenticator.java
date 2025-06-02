package MailViewer.authorization;

import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;

public class OAuthAuthenticator extends Authenticator {
    private final String id;
    private final String token;

    public OAuthAuthenticator(String id, String token){
        this.id = id;
        this.token = token;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(id, token);
    }
}
