package com.epam.xml.util.impl;

import com.epam.xml.entity.Paper;
import com.epam.xml.util.AbstractPeriodicalsBuilder;
import com.epam.xml.util.PeriodicalsHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.util.Set;

public class PeriodicalsSAXBuilder extends AbstractPeriodicalsBuilder {

    private PeriodicalsHandler periodicalsHandler;
    private XMLReader reader;

    public PeriodicalsSAXBuilder() {
        periodicalsHandler = new PeriodicalsHandler();
        try {
            reader = XMLReaderFactory.createXMLReader();
            reader.setContentHandler(periodicalsHandler);
        } catch (SAXException e) {
            System.err.print("ошибка SAX парсера: " + e);
        }
    }

    public PeriodicalsSAXBuilder(Set<Paper> periodicals) {
        super(periodicals);
    }

    @Override
    public void buildSetPeriodicals(String fileName) {
        try {
            reader.parse(fileName);
        } catch (SAXException e) {
            System.err.print("ошибка SAX парсера: " + e);
        } catch (IOException e) {
            System.err.print("ошибка I/О потока: " + e);
        }
        periodicals = periodicalsHandler.getPeriodicals();
    }
}
