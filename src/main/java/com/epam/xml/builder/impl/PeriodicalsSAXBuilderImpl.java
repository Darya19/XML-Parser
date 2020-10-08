package com.epam.xml.builder.impl;

import com.epam.xml.builder.AbstractPeriodicalsBuilder;
import com.epam.xml.builder.PeriodicalsHandler;
import com.epam.xml.entity.Paper;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.util.Set;

public class PeriodicalsSAXBuilderImpl extends AbstractPeriodicalsBuilder {

    private PeriodicalsHandler periodicalsHandler;
    private XMLReader reader;
    private Logger logger = LogManager.getLogger();

    public PeriodicalsSAXBuilderImpl() {
        periodicalsHandler = new PeriodicalsHandler();
        try {
            reader = XMLReaderFactory.createXMLReader();
            reader.setContentHandler(periodicalsHandler);
        } catch (SAXException e) {
            logger.log(Level.ERROR, "error SAX parser", e);
        }
    }

    public PeriodicalsSAXBuilderImpl(Set<Paper> periodicals) {
        super(periodicals);
    }

    @Override
    public void buildSetPeriodicals(String fileName) {
        try {
            reader.parse(fileName);
        } catch (SAXException e) {
            logger.log(Level.ERROR, "SAX parser error", e);
        } catch (IOException e) {
            logger.log(Level.ERROR, "File error or I/O error: ", e);
        }
        periodicals = periodicalsHandler.getPeriodicals();
    }
}
