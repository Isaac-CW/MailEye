package MailViewer.pagination;

import java.lang.Math;
import com.sun.mail.imap.IMAPFolder;
import jakarta.mail.MessagingException;

/**
 * PageManager is the user-interface for a set of pages.
 */
public class PageManager {
    /**
     * How many pages should be held in memory
     */
    private static final int CACHE_SIZE = 5;

    /**
     * Represents the availability of pages where 0 indicates that there are no pages to go backwards on
     */
    private int cursor = 0;

    /**
     * Stores the page pointed to by cursor
     */
    private Page currentPage;

    /**
     * The maximum value that cursor can take before preventing a player from calling nextPage
     * Changing this recomputes the page cache
     */
    private int maxPages;

    /**
     * How many messages should be stored per page.
     * Changing this will cause maxPages to be recomputed and the page cache to be emptied
     */
    private int pageSize = 0;

    /**
     * Stores how many messages exist in the folder
     */
    private int totalMessages;

    /**
     * Stores a reference to the IMAPFolder to retrieve paginated messages from
     */
    private final IMAPFolder folder;

    public PageManager(IMAPFolder targetFolder){
        folder = targetFolder;
    }

    /**
     *
     * @param newSize
     * @throws MessagingException
     */
    public void setPageSize(int newSize) throws MessagingException {
        pageSize = newSize;
        totalMessages = folder.getMessageCount();
        maxPages = (int) Math.ceil(totalMessages / (double) pageSize);

        jumpToPage(0);
    }

    /**
     * Returns true or false if there's a next page to the cursor available
     * @return
     */
    public boolean hasNext(){
        return cursor < maxPages;
    }

    /**
     * Returns true or false if there is a previous page to the cursor available
     * @return
     */
    public boolean hasPrev(){
        return  cursor > 0;
    }

    private Page createPage(int pageNum) throws MessagingException{
        int startInd = pageNum * pageSize + 1;
        int endInd = startInd + pageSize <= totalMessages ? startInd + pageSize : totalMessages;
        Page retVal = new Page(startInd, endInd, folder);

        return retVal;
    }

    public Page nextPage() throws MessagingException{
        if (!hasNext()){
            throw new PageMissingException(String.format("No next page from cursor value: %d, max: %d", cursor, maxPages));
        }
        cursor += 1;

        currentPage = createPage(cursor);
        return currentPage;
    }

    /**
     * Sets the cursor to the given pageNum (provided its within the range [-maxPages, maxPages] both inclusive)
     * @param pageNum
     * @throws InvalidPageNumber thrown when pageNum is outside of the range [0, maxPages]
     */
    public Page jumpToPage(int pageNum) throws MessagingException{
        if (pageNum < -maxPages || pageNum > maxPages){
            throw new InvalidPageNumber(String.format("Given pageNum: %d is not in range [0, &d]", pageNum, maxPages));
        }
        if (pageNum < 0){
            pageNum = maxPages + pageNum - 1;
        }
        cursor = pageNum;
        currentPage = createPage(pageNum);

        return currentPage;
    }

    public Page currentPage(){
        return currentPage;
    }

    public Page previousPage() throws MessagingException{
        if (!hasPrev()){
            throw new PageMissingException(String.format("No page exists before current page %d", cursor));
        }

        cursor -= 1;

        currentPage = createPage(cursor);
        return currentPage;
    }
}
