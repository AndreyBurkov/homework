package com.netcracker.client;

import com.netcracker.shared.Book;
import com.netcracker.shared.Hello;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

@Path("api/hellos")
public interface HelloClient extends RestService {

    @GET
    void getAll(MethodCallback<List<Hello>> hellos);

    @GET
    @Path("{id}")
    void getHello(@PathParam("id") String id, MethodCallback<Hello> hello);

    @GET
    @Path("/books")
    void getBooks(MethodCallback<List<Book>> books);
}
