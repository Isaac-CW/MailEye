package MailViewer.pagination;

import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPMessage;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;

/**
 * Page represents a set of messages from a specific range of values
 */
public class Page {

    private <A,B> A[] castArray(B[] from, A[] to){
        for (int i = 0; i < from.length; i++){
            to[i] = (A) from[i];
        }

        return to;
    }

    /**
     * The index of the first message in the page
     */
    public final int firstMessage;

    /**
     * The index of the last message in the page
     */
    public final int lastMessage;

    /**
     * The number of messages in this page. Logically this is dervied from lastMesage - firstMessage + 1
     */
    public final int pageSize;

    /**
     * Stores all the messages where messages[0] has the index of firstMessage and messages[messages.length - 1]; the lastMessage
     */
    public final IMAPMessage[] messages;

    /**
     * Retrives the messages from the given IMAPFolder given its firstMessage and lastMessage
     * @param firstMessage
     * @param lastMessage
     * @param folder
     */
    public Page(int firstMessage, int lastMessage, IMAPFolder folder) throws MessagingException {
        var msgs = folder.getMessages(firstMessage, lastMessage);
        this.messages = castArray(msgs, new IMAPMessage[msgs.length]);
        this.firstMessage = firstMessage;
        this.lastMessage = lastMessage;
        this.pageSize = lastMessage - firstMessage + 1;
    }
}
