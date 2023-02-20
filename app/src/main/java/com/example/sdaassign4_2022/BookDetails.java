package com.example.sdaassign4_2022;

import java.util.Date;

/**
 * Book Details Model
 */
public class BookDetails {
    /**
     * get or set image url
     */
    private String imageUrl;
    /**
     * get ot set book name
     */
    private String bookName;

    /**
     * get or set author name
     */
    private String authorName;

    /**
     * get or set current date
     */
    private String currentDate;

    /**
     * get ot set borrower id
     */

    private String borrowerId;

    /**
     * get ot set expire date
     */
    private String expiredDate;

    /**
     * initialize
     */
    public BookDetails() {}

    /**
     * get current date
     * @return currentDate
     */
    public String getCurrentDate() {
        return currentDate;
    }

    /**
     * set current date
     * @param currentDate
     */
    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    /**
     * get borrower id
     * @return borrowerId
     */
    public String getBorrowerId() {
        return borrowerId;
    }

    /**
     * set borrower Id
     * @param borrowerId
     */
    public void setBorrowerId(String borrowerId) {
        this.borrowerId = borrowerId;
    }

    /**
     * get expired Date
     * @return expiredDate
     */
    public String getExpiredDate() {
        return expiredDate;
    }

    /**
     * set expired Date
     * @param expiredDate
     */
    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    /**
     * get image Url
     * @return imageUrl
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * set image Url
     * @param imageUrl
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * get book name
     * @return bookName
     */
    public String getBookName() {
        return bookName;
    }

    /**
     * set book name
     * @param bookName
     */
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    /**
     * get author name
     * @return authorName
     */
    public String getAuthorName() {
        return authorName;
    }

    /**
     * set author name
     * @param authorName
     */
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
