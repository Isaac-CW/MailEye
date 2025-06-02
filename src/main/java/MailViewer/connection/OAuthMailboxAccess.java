package MailViewer.connection;

import MailViewer.authorization.OAuthURL;
import MailViewer.secret.Secret;
import com.google.api.client.auth.oauth2.*;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class OAuthMailboxAccess extends MailboxAccess{
    /**
     * The directory to store the credentials in. This should refer to a folder rather than a specific file
     */
    private static final String DATASTORE_DIR = "./src/main/resources/data";

    private OAuthURL access;
    private Secret secret;
    private String[] scopes;

    protected OAuthMailboxAccess(OAuthURL access, Secret s, String[] scopes){
        this.access = access;
        this.secret = s;
        this.scopes = scopes;
    }

    @Override
    public String getAccessToken(String username) {
        FileDataStoreFactory fds;
        String accessToken;
        try {
            File dsDir = new File(DATASTORE_DIR);
            fds = new FileDataStoreFactory(dsDir);

            AuthorizationCodeFlow f = new AuthorizationCodeFlow.Builder(
                    BearerToken.authorizationHeaderAccessMethod(),
                    new NetHttpTransport(),
                    new GsonFactory(),
                    new GenericUrl(access.getTokenRequestURL()),
                    new ClientParametersAuthentication(secret.getID(), secret.getSecret()),
                    secret.getID(),
                    access.getAuthorizationServerURL()
            )
                    .setScopes(Arrays.asList(scopes))
                    .setDataStoreFactory(fds)
                    .build();

            Credential c = f.loadCredential(username);

            if (c == null || !c.refreshToken()) {
                // Create a fresh access and refresh token
                String tokenRequestURL = f.newAuthorizationUrl()
                        .setRedirectUri("urn:ietf:wg:oauth:2.0:oob")
                        .setState("Session")
                        .build();

                System.out.printf("Visit to authorize: %s\n", tokenRequestURL);
                Scanner s = new Scanner(System.in);
                String token = s.nextLine();

                TokenResponse credentials = f.newTokenRequest(token)
                        .setRedirectUri("urn:ietf:wg:oauth:2.0:oob")
                        .execute();

                c = f.createAndStoreCredential(credentials, username);
            }
            accessToken = c.getAccessToken();
        } catch (IOException e) {
            throw new RuntimeException(String.format("Unable to create fetch OAuth token: %s", e.toString()));
        }
        return accessToken;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private Secret s;
        private OAuthURL access;
        private String[] scopes;

        public Builder setAccess(OAuthURL access) {
            this.access = access;
            return this;
        }
        public Builder setOAuthURL(Secret s) {
            this.s = s;
            return this;
        }
        public Builder setScopes(String[] scopes){
            this.scopes = scopes;
            return this;
        }

        public OAuthMailboxAccess build(){
            return new OAuthMailboxAccess(access, s, scopes);
        }
    }
}
