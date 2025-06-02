package MailViewer.connection;

/**
 * MailboxAccess represents the process used to retrieve the credentials that Jakarta Mail will use to access a mailbox
 */
public abstract class MailboxAccess {

    public abstract String getAccessToken(String username);

    static abstract class Builder {
        public abstract MailboxAccess build();
    }
}
