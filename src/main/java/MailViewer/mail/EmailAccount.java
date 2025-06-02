package MailViewer.mail;

import MailViewer.connection.MailboxAccess;
import com.sun.mail.imap.IMAPStore;
import jakarta.mail.MessagingException;
import jakarta.mail.NoSuchProviderException;
import jakarta.mail.Session;
import jakarta.mail.Store;

import java.util.Properties;

/**
 * Represents the access point for a specific email account
 */
public class EmailAccount {
    private MailboxAccess access;
    private EmailURL URL;

    private String emailAddr;

    private Session emailSession;

    public EmailAccount(MailboxAccess access, EmailURL provider, String emailAddress) {
        this.access = access;
        this.URL = provider;
        this.emailAddr = emailAddress;
        // Setup Jakarta Mail session
        Properties config = new Properties();
        config.setProperty("mail.imap.host", URL.getIMAPAddr());
        config.setProperty("mail.imap.port", URL.getIMAPPort());
        config.put("mail.imap.ssl.enable", "true"); // required for Gmail
        config.put("mail.imap.auth.mechanisms", "XOAUTH2");

        emailSession = Session.getInstance(config);
    }

    public Session getSession(){return emailSession;}
    public String getEmailAddr(){return emailAddr;}

    /**
     * Establishes a connection to the instances' EmailURL with IMAP
     */
    public Store connectImap() throws MessagingException{
        String token = access.getAccessToken(emailAddr);
        Store store;
        try {
            store = emailSession.getStore("imap");
            store.connect(emailAddr, token);
        } catch (NoSuchProviderException e) {
            return null;
        }

        return store;
    }
}
