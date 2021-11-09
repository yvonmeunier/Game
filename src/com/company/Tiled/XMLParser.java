package com.company.Tiled;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public abstract class XMLParser<T> {

    public Document document;
    public abstract T result();

    public XMLParser(String file) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
        try{
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            document = documentBuilder.parse(new File(file));
        }catch (Exception e) {

        }
    }
}
