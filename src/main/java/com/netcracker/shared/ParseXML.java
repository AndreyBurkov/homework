package com.netcracker.shared;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ParseXML {

    private static File file = new File("c:/books.xml");

    public static List<Book> getBooks() {


        List<Book> bookList = new ArrayList<>();


        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);

            NodeList bookNodeList = document.getElementsByTagName("Book");

            for (int i = 0; i < bookNodeList.getLength(); i++) {
                if (bookNodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element bookElement = (Element) bookNodeList.item(i);

                    Integer id = Integer.parseInt(bookElement.getAttribute("id"));
                    String author = "";
                    String title = "";
                    Integer pagesCount = 0;
                    String publishDate = null;
                    String dateInBase = null;


                    NodeList childNodes = bookElement.getChildNodes();

                    for (int j = 0; j < childNodes.getLength(); j++) {
                        if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
                            Element childElement = (Element) childNodes.item(j);
                            switch (childElement.getNodeName()) {
                                case "Author":
                                    author = childElement.getTextContent();
                                    break;
                                case "Title":
                                    title = childElement.getTextContent();
                                    break;
                                case "Pages":
                                    pagesCount = Integer.valueOf(childElement.getTextContent());
                                    break;
                                case "PublishDate":
                                    publishDate = childElement.getTextContent();
                                    break;
                                case "DateInBase":
                                    dateInBase = childElement.getTextContent();
                                    break;
                            }
                        }
                    }

                    Book book = new Book(id, author, title, pagesCount, publishDate, dateInBase);

                    bookList.add(book);

                }
            }


        } catch (Exception e) {
            try {
                PrintWriter writer = new PrintWriter("c:/errors.txt", "UTF-8");
                writer.println(e.getMessage());
                writer.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }

        return bookList;
    }
}
