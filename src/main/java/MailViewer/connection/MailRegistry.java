package MailViewer.connection;

import java.util.HashMap;

/**
 * Contains a set of MailboxAccess instances that correlate to a given mailbox
 */
public class MailRegistry {
    private HashMap<Mailbox, MailboxAccess> registeredAccesses;
    public MailRegistry(){
        registeredAccesses = new HashMap<>();
    }

    public MailboxAccess getAccess(Mailbox mailboxType){
        if (!registeredAccesses.containsKey(mailboxType)){
            throw new RuntimeException(String.format("%s does not have a MailboxAccess registered to it", mailboxType.name()));
        }
        return registeredAccesses.get(mailboxType);
    }

    public void registerMailboxAccess(Mailbox mailboxType, MailboxAccess access){
        if (registeredAccesses.containsKey(mailboxType)){
            throw new RuntimeException(String.format("%s is already an existing MailboxAccess", mailboxType.name()));
        }

        registeredAccesses.put(mailboxType, access);
    }
}
