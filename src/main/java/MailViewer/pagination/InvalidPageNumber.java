package MailViewer.pagination;

public class InvalidPageNumber extends RuntimeException {
    public InvalidPageNumber(String message) {
        super(message);
    }
}
