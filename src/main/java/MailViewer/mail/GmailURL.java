package MailViewer.mail;

public class GmailURL implements EmailURL{
    private static final String IMAP_ADDR = "imap.gmail.com";
    private static final String IMAP_PORT = "993";

    @Override
    public String getIMAPAddr() {
        return IMAP_ADDR;
    }

    @Override
    public String getIMAPPort() {
        return IMAP_PORT;
    }
}
