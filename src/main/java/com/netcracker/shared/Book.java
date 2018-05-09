package com.netcracker.shared;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Book {

    private Integer id;
    private String author;
    private String title;
    private Integer pagesCount;
    private String publishDate;
    private String dateInBase;

    @JsonCreator
    public Book(@JsonProperty("id") Integer id,
                @JsonProperty("author") String author,
                @JsonProperty("title") String title,
                @JsonProperty("pages")Integer pagesCount,
                @JsonProperty("publish") String publishDate,
                @JsonProperty("date") String dateInBase) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.pagesCount = pagesCount;
        this.publishDate = publishDate;
        this.dateInBase = dateInBase;
    }

    public Book(Integer id, String author, String title) {
        this.id = id;
        this.author = author;
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPagesCount() {
        return pagesCount;
    }

    public void setPagesCount(Integer pagesCount) {
        this.pagesCount = pagesCount;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getDateInBase() {
        return dateInBase;
    }

    public void setDateInBase(String dateInBase) {
        this.dateInBase = dateInBase;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", pagesCount=" + pagesCount +
                ", publishDate='" + publishDate + '\'' +
                ", dateInBase='" + dateInBase + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) &&
                Objects.equals(author, book.author) &&
                Objects.equals(title, book.title) &&
                Objects.equals(pagesCount, book.pagesCount) &&
                Objects.equals(publishDate, book.publishDate) &&
                Objects.equals(dateInBase, book.dateInBase);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, author, title, pagesCount, publishDate, dateInBase);
    }
}
