package MailViewer.pagination;

public class PageMissingException extends RuntimeException {
    public PageMissingException(String msg){
        super(msg);
    }
}
