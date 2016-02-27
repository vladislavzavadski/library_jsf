/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.usebean;

import java.sql.Date;

/**
 *
 * @author Владислав
 */
public class Book {
    private long bookId;
    private String name;
    private int pageCount;
    private String isbn;
    private String genre;
    private String author;
    private int publishDate;
    private String publisher;
    private String description;
    private byte[] image;

    public Book(long bookId, String name, int pageCount, String isbn, String genreId, String authorId, int publishDate, String publisherId, byte[] image, String description) {
        this.bookId = bookId;
        this.name = name;
        this.pageCount = pageCount;
        this.isbn = isbn;
        this.genre = genreId;
        this.author = authorId;
        this.publishDate = publishDate;
        this.publisher = publisherId;
        this.image = image;
        this.description = description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    
    public Book(){}

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(int publishDate) {
        this.publishDate = publishDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

}
