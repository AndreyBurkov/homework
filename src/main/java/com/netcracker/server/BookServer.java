package com.netcracker.server;

import com.netcracker.shared.Book;
import com.netcracker.shared.ParseXML;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Path("books")
public class BookServer {


    private static List<Book> bookList = ParseXML.getBooks();

    public BookServer() {
    }


    @GET
    @Produces("application/json")
    @Path("/all")
    public List<Book> getBooks() {
        return bookList;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/add")
    public List<Book> addBook(Book book) {
        bookList.add(book);
        return bookList;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/del")
    public List<Book> delBook(Book book) {
        for (int i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).equals(book)) {
                bookList.remove(i);
                break;
            }
        }
        return bookList;
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/sort")
    public List<Book> getSortBooks(String column) {
        if (column.equals("id")) {
            Collections.sort(bookList, new Comparator<Book>() {
                @Override
                public int compare(Book o1, Book o2) {
                    return o1.getId().compareTo(o2.getId());
                }
            });
        }
        if (column.equals("author")) {
            Collections.sort(bookList, new Comparator<Book>() {
                @Override
                public int compare(Book o1, Book o2) {
                    return o1.getAuthor().compareTo(o2.getAuthor());
                }
            });
        }
        if (column.equals("title")) {
            Collections.sort(bookList, new Comparator<Book>() {
                @Override
                public int compare(Book o1, Book o2) {
                    return o1.getTitle().compareTo(o2.getTitle());
                }
            });
        }
        if (column.equals("pages")) {
            Collections.sort(bookList, new Comparator<Book>() {
                @Override
                public int compare(Book o1, Book o2) {
                    return o1.getPagesCount().compareTo(o2.getPagesCount());
                }
            });
        }
        if (column.equals("publish")) {
            Collections.sort(bookList, new Comparator<Book>() {
                @Override
                public int compare(Book o1, Book o2) {
                    return o1.getPublishDate().compareTo(o2.getPublishDate());
                }
            });
        }
        if (column.equals("date")) {
            Collections.sort(bookList, new Comparator<Book>() {
                @Override
                public int compare(Book o1, Book o2) {
                    return o1.getDateInBase().compareTo(o2.getDateInBase());
                }
            });
        }
        return bookList;
    }
}

