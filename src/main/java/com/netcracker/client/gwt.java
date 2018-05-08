package com.netcracker.client;

import com.google.gwt.cell.client.DateCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.netcracker.shared.Book;
import com.netcracker.shared.Hello;
import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class gwt implements EntryPoint {


    public static List<Book> bookList;

    @Override
    public void onModuleLoad() {
        Defaults.setServiceRoot(com.google.gwt.core.client.GWT.getHostPageBaseURL());

        RootPanel.get().setStyleName("root");
        RootPanel rootPanel = RootPanel.get("resty");

        final VerticalPanel verticalPanel = new VerticalPanel();

        HorizontalPanel horizontalPanel = new HorizontalPanel();
        horizontalPanel.setSpacing(20);
        Button addButton = new Button("Add");
        Button delButton = new Button("Delete");
        Button refreshButton = new Button("Refresh");
        horizontalPanel.add(addButton);
        horizontalPanel.add(delButton);
        horizontalPanel.add(refreshButton);
        verticalPanel.add(horizontalPanel);

        rootPanel.add(verticalPanel);

//////////////////////////////   CellTable   ///////////////////////////////////////////////////////////////////////////
        final CellTable<Book> table = new CellTable<Book>();
        table.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);
        // add columns
        TextColumn<Book> id = new TextColumn<Book>() {
            @Override
            public String getValue(Book book) {
                return "" + book.getId();
            }
        };
        table.addColumn(id, "ID");
        TextColumn<Book> author = new TextColumn<Book>() {
            @Override
            public String getValue(Book book) {
                return "" + book.getAuthor();
            }
        };
        table.addColumn(author, "Author");
        TextColumn<Book> title = new TextColumn<Book>() {
            @Override
            public String getValue(Book book) {
                return "" + book.getTitle();
            }
        };
        table.addColumn(title, "Title");
        TextColumn<Book> pages = new TextColumn<Book>() {
            @Override
            public String getValue(Book book) {
                return "" + book.getPagesCount();
            }
        };
        table.addColumn(pages, "Pages");
        TextColumn<Book> publish = new TextColumn<Book>() {
            @Override
            public String getValue(Book book) {
                return "" + book.getPublishDate();
            }
        };
        table.addColumn(publish, "PublishDate");
        TextColumn<Book> date = new TextColumn<Book>() {
            @Override
            public String getValue(Book book) {
                return "" + book.getDateInBase();
            }
        };
        table.addColumn(date, "Date In Base");

        // Add a selection model to handle user selection.
        final SingleSelectionModel<Book> selectionModel = new SingleSelectionModel<Book>();
        table.setSelectionModel(selectionModel);


        verticalPanel.add(table);

        HelloClient client = GWT.create(HelloClient.class);

        client.getBooks(new MethodCallback<List<Book>>() {

            @Override
            public void onSuccess(Method method, List<Book> books) {
                gwt.bookList = books;
                table.setRowCount(bookList.size(), true);
                table.setRowData(0, bookList);

            }

            @Override
            public void onFailure(Method method, Throwable throwable) {
                throw new RuntimeException("call failed");
            }
        });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        refreshButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {

                table.redraw();
                // Push the data into the widget.

                /*for (Book book : bookList) {
                    verticalPanel.add(new Label(book.toString()));
                }*/
            }
        });


    }
}