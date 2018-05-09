package com.netcracker.client;

import com.netcracker.shared.Book;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("api/books")
public interface BookClient extends RestService {

    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    void addBook(Book book, MethodCallback<List<Book>> books);

    @POST
    @Path("/del")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    void delBook(Book book, MethodCallback<List<Book>> books);

    @GET
    @Path("/all")
    void getBooks(MethodCallback<List<Book>> books);

    @POST
    @Path("/sort")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    void getSortBooks(String column, MethodCallback<List<Book>> books);
}
