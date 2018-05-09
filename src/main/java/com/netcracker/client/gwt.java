package com.netcracker.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SingleSelectionModel;
import com.netcracker.shared.Book;
import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.List;

public class gwt implements EntryPoint {


    public static List<Book> bookList;

    BookClient client = GWT.create(BookClient.class);

    final CellTable<Book> table = new CellTable<Book>();

    final SingleSelectionModel<Book> selectionModel = new SingleSelectionModel<Book>();

    SimplePager pager = new SimplePager(SimplePager.TextLocation.CENTER);

    final ListDataProvider<Book> dataProvider = new ListDataProvider<Book>();

    @Override
    public void onModuleLoad() {
        Defaults.setServiceRoot(com.google.gwt.core.client.GWT.getHostPageBaseURL());

        RootPanel.get().setStyleName("root");
        RootPanel rootPanel = RootPanel.get("resty");

        final VerticalPanel verticalPanel = new VerticalPanel();

        HorizontalPanel horizontalPanel = new HorizontalPanel();
        horizontalPanel.setStyleName("hpanel");
        horizontalPanel.setSpacing(50);
        Button addButton = new Button("Add");
        Button delButton = new Button("Delete");
        Button sortButton = new Button("Sort");
        horizontalPanel.add(addButton);
        horizontalPanel.add(delButton);
        horizontalPanel.add(sortButton);
        verticalPanel.add(horizontalPanel);

        rootPanel.add(verticalPanel);

//////////////////////////////   CellTable   ///////////////////////////////////////////////////////////////////////////

        table.setStyleName("table");
        table.setTableLayoutFixed(true);
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
        table.setColumnWidth(0, "100px");
        table.setColumnWidth(3, "100px");

        table.setSelectionModel(selectionModel);

        pager.setStyleName("pager");
        pager.setDisplay(table);
        pager.setPageSize(7);
        pager.setRangeLimited(false);

        verticalPanel.add(table);
        verticalPanel.add(pager);

        getBooks();

        addButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                addBook();
            }
        });

        delButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                delBook();
            }
        });

        sortButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                sortByColumn();
            }
        });
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void getBooks() {
        client.getBooks(new MethodCallback<List<Book>>() {

            @Override
            public void onSuccess(Method method, List<Book> books) {
                gwt.bookList = books;
                table.setRowCount(bookList.size(), false);
                table.setRowData(0, bookList);
                dataProvider.setList(bookList);
                dataProvider.addDataDisplay(table);
            }

            @Override
            public void onFailure(Method method, Throwable throwable) {
                Window.alert("Error getBooks()");
            }
        });
    }

    public void addBook() {
        final DialogBox dialogBox = new DialogBox();
        dialogBox.setAnimationEnabled(true);
        dialogBox.setGlassEnabled(true);
        dialogBox.setText("Add new book");

        Grid grid = new Grid(7, 2);
        grid.setStyleName("grid");
        Button okButton = new Button("OK");
        Button cancelButton = new Button("Cancel");
        final TextBox id = new TextBox();
        final TextBox author = new TextBox();
        final TextBox title = new TextBox();
        final TextBox pages = new TextBox();
        final TextBox publish = new TextBox();
        final TextBox date = new TextBox();
        grid.setWidget(0,0, new Label("ID:"));
        grid.setWidget(1,0, new Label("Author:"));
        grid.setWidget(2,0, new Label("Title:"));
        grid.setWidget(3,0, new Label("Pages:"));
        grid.setWidget(4,0, new Label("Publish date:"));
        grid.setWidget(5,0, new Label("Date in base:"));
        grid.setWidget(6,0, okButton);
        grid.setWidget(0,1, id);
        grid.setWidget(1,1, author);
        grid.setWidget(2,1, title);
        grid.setWidget(3,1, pages);
        grid.setWidget(4,1, publish);
        grid.setWidget(5,1, date);
        grid.setWidget(6,1, cancelButton);
        dialogBox.setWidget(grid);
        int left = Window.getClientWidth()/ 2 - 200;
        int top = Window.getClientHeight()/ 2 - 150;
        dialogBox.setPopupPosition(left, top);
        dialogBox.show();

        cancelButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                dialogBox.hide();
            }
        });

        okButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                Book book = new Book(Integer.parseInt(id.getText()), author.getText(), title.getText(),
                        Integer.parseInt(pages.getText()), publish.getText(), date.getText());
                client.addBook(book, new MethodCallback<List<Book>>() {
                    @Override
                    public void onFailure(Method method, Throwable throwable) {
                        Window.alert("Error addBook()");
                    }

                    @Override
                    public void onSuccess(Method method, List<Book> books) {
                        gwt.bookList = books;
                        table.setRowCount(bookList.size(), false);
                        table.setRowData(0, bookList);
                        dataProvider.setList(bookList);
                        dataProvider.addDataDisplay(table);
                        table.redraw();
                        dialogBox.hide();
                    }
                });
                dialogBox.hide();
            }
        });
    }

    public void delBook() {
        Book book = selectionModel.getSelectedObject();
        client.delBook(book, new MethodCallback<List<Book>>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                Window.alert("Error delBook()");
            }

            @Override
            public void onSuccess(Method method, List<Book> books) {
                gwt.bookList = books;
                table.setRowCount(bookList.size(), false);
                table.setRowData(0, bookList);
                dataProvider.setList(bookList);
                dataProvider.addDataDisplay(table);
                table.redraw();
            }
        });
    }

    public void sortByColumn() {
        final DialogBox dialogBox = new DialogBox();
        dialogBox.setAnimationEnabled(true);
        dialogBox.setGlassEnabled(true);
        dialogBox.setText("Sort");

        VerticalPanel panel = new VerticalPanel();
        panel.setStyleName("vpanel");
        panel.setSpacing(5);
        Button okButton = new Button("OK");
        okButton.setStyleName("okButton");

        final RadioButton idButton = new RadioButton("radioGroup", "Id");
        idButton.setValue(true);
        final RadioButton authorButton = new RadioButton("radioGroup", "Author");
        final RadioButton titleButton = new RadioButton("radioGroup", "Title");
        final RadioButton pagesButton = new RadioButton("radioGroup", "Pages");
        final RadioButton publishButton = new RadioButton("radioGroup", "Publish date");
        final RadioButton dateButton = new RadioButton("radioGroup", "Date in base");

        panel.add(idButton);
        panel.add(authorButton);
        panel.add(titleButton);
        panel.add(pagesButton);
        panel.add(publishButton);
        panel.add(dateButton);
        panel.add(okButton);

        dialogBox.setWidget(panel);
        int left = Window.getClientWidth()/ 2 - 200;
        int top = Window.getClientHeight()/ 2 - 150;
        dialogBox.setPopupPosition(left, top);
        dialogBox.show();

        okButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                String column = "id";
                if (idButton.getValue()) column = "id";
                if (authorButton.getValue()) column = "author";
                if (titleButton.getValue()) column = "title";
                if (pagesButton.getValue()) column = "pages";
                if (publishButton.getValue()) column = "publish";
                if (dateButton.getValue()) column = "date";

                client.getSortBooks(column, new MethodCallback<List<Book>>() {
                    @Override
                    public void onFailure(Method method, Throwable throwable) {
                        Window.alert("Error sortByColumn()");
                    }

                    @Override
                    public void onSuccess(Method method, List<Book> books) {
                        gwt.bookList = books;
                        table.setRowCount(bookList.size(), false);
                        table.setRowData(0, bookList);
                        dataProvider.setList(bookList);
                        dataProvider.addDataDisplay(table);
                        table.redraw();
                        dialogBox.hide();
                    }
                });
                dialogBox.hide();
            }
        });




    }

}