package MailViewer;

import MailViewer.authorization.GoogleURLs;
import MailViewer.connection.MailRegistry;
import MailViewer.connection.Mailbox;
import MailViewer.connection.MailboxAccess;
import MailViewer.connection.OAuthMailboxAccess;
import MailViewer.secret.SecretHandler;

/**
 * Top level singleton that represents the session of the application
 */
public class Application {
    private static Application inst;

    public final MailRegistry mailboxes;

    private Application(){
        mailboxes = new MailRegistry();
        try {
            MailboxAccess obj = OAuthMailboxAccess.builder()
                    .setAccess(new GoogleURLs())
                    .setScopes(new String[]{"https://mail.google.com/"})
                    .setOAuthURL(new SecretHandler("local", "secret.txt"))
                    .build();

            mailboxes.registerMailboxAccess(Mailbox.GMAIL, obj);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Application getInstance(){
        if (inst == null){
            inst = new Application();
        }
        return inst;
    }
}
