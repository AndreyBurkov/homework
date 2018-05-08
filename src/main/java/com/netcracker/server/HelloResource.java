package com.netcracker.server;

import com.netcracker.shared.Book;
import com.netcracker.shared.Hello;
import com.netcracker.shared.ParseXML;
import jersey.repackaged.com.google.common.collect.Lists;

import javax.servlet.annotation.WebServlet;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.HashMap;
import java.util.List;

@Path("hellos")
@WebServlet
public class HelloResource {

    private HashMap<String, Hello> database;

    public HelloResource() {
        database = new HashMap<>();
        Hello helloRonan = new Hello("1", "Ronan");
        Hello helloJohn = new Hello("2", "John");
        database.put(helloRonan.getId(), helloRonan);
        database.put(helloJohn.getId(), helloJohn);
    }

    @GET
    @Produces("application/json")
    public List<Hello> getAll() {
        return Lists.newArrayList(database.values());
    }

    @GET
    @Produces("application/json")
    @Path("/{id}")
    public Hello get(@PathParam("id") String id) {
        return database.get(id);
    }

    @GET
    @Produces("application/json")
    @Path("/books")
    public List<Book> getBooks() {
        return ParseXML.getBooks();
    }

}
