package com.epam.xml.builder.impl;

import com.epam.xml.builder.AbstractPeriodicalsBuilder;
import com.epam.xml.builder.PaperEnum;
import com.epam.xml.entity.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class PeriodicalsStAXBuilderImpl extends AbstractPeriodicalsBuilder {

    private Set<Paper> periodicals;
    private XMLInputFactory inputFactory;
    private Logger logger = LogManager.getLogger();

    public PeriodicalsStAXBuilderImpl() {
        inputFactory = XMLInputFactory.newInstance();
        periodicals = new HashSet<>();
    }

    public PeriodicalsStAXBuilderImpl(Set<Paper> periodicals) {
        super(periodicals);
    }

    public Set<Paper> getPeriodicals() {
        return periodicals;
    }

    @Override
    public void buildSetPeriodicals(String fileName) {
        FileInputStream inputStream = null;
        XMLStreamReader reader;
        String name;
        try {
            inputStream = new FileInputStream(new File(fileName));
            reader = inputFactory.createXMLStreamReader(inputStream);
            while (reader.hasNext()) {
                int type = reader.next();
                if (type == XMLStreamConstants.START_ELEMENT) {
                    name = reader.getLocalName();
                    if (PaperEnum.valueOf(name.toUpperCase()) == PaperEnum.NEWSPAPER) {
                        Paper paper = buildPaper(reader, PaperEnum.NEWSPAPER);
                        periodicals.add(paper);
                    }
                    if (PaperType.valueOf(name.toUpperCase()) == PaperType.MAGAZINE) {
                        Paper paper = buildPaper(reader, PaperEnum.MAGAZINE);
                        periodicals.add(paper);
                    }
                    if (PaperType.valueOf(name.toUpperCase()) == PaperType.BOOKLET) {
                        Paper paper = buildPaper(reader, PaperEnum.BOOKLET);
                        periodicals.add(paper);
                    }
                }
            }
        } catch (XMLStreamException ex) {
            logger.log(Level.ERROR, "StAX parsing error! ", ex);
        } catch (FileNotFoundException ex) {
            logger.log(Level.ERROR, "File {} not found! ", fileName, ex);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                logger.log(Level.ERROR, "Impossible close file {} : ", fileName, e);
            }
        }
    }

    private Paper buildPaper(XMLStreamReader reader, PaperEnum paperEnum) throws XMLStreamException {
        Paper paper = null;
        if (paperEnum.equals(PaperEnum.NEWSPAPER)) {
            paper = new Newspaper();
            logger.log(Level.INFO, "object newspaper create");
        }
        if (paperEnum.equals(PaperEnum.MAGAZINE)) {
            paper = new Magazine();
            logger.log(Level.INFO, "object magazine create");
        }
        if (paperEnum.equals(PaperEnum.BOOKLET)) {
            paper = new Booklet();
            logger.log(Level.INFO, "object booklet create");
        }
        paper.setTitle(reader.getAttributeValue(null, PaperEnum.TITLE.getValue()));
        String stringPaper = reader.getAttributeValue(null, PaperEnum.TYPE.getValue());
        paper.setType(PaperType.valueOf(stringPaper.toUpperCase()));
        String name;
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    switch (PaperEnum.valueOf(name.toUpperCase())) {
                        case MONTHLY:
                            name = getXMLText(reader);
                            if (name.equals("true")) {
                                paper.setMonthly(true);
                            } else {
                                paper.setMonthly(false);
                            }
                            break;
                        case PUBLICATION_DATE:
                            name = getXMLText(reader);
                            String[] date = name.split("\\.");
                            int[] intDate = new int[3];
                            for (int i = 0; i < date.length; i++) {
                                intDate[i] = Integer.parseInt(date[i]);
                            }
                            paper.setPublicationDate(LocalDate.of(intDate[2], intDate[1], intDate[0]));
                            break;
                        case CHARACTERISTICS:
                            paper.setCharacteristics(getXMLCharacteristics(reader, paper));
                            break;
                        case INDEX:
                        case BOOKLET_TYPE:
                            name = getXMLText(reader);
                            paper.setOtherCharacteristic(name);
                            break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    if (PaperEnum.valueOf(name.toUpperCase()) == PaperEnum.NEWSPAPER
                            || PaperEnum.valueOf(name.toUpperCase()) == PaperEnum.MAGAZINE
                            || PaperEnum.valueOf(name.toUpperCase()) == PaperEnum.BOOKLET) {
                        return paper;
                    }
                    break;
            }
        }
        throw new XMLStreamException("Unknown element in tag paper");
    }

    private Paper.Characteristics getXMLCharacteristics(XMLStreamReader reader, Paper paper)
            throws XMLStreamException {
        Paper.Characteristics characteristics = paper.setCharacteristics();
        int type;
        String name;
        while (reader.hasNext()) {
            type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    switch (PaperEnum.valueOf(name.toUpperCase())) {
                        case PAGE_NUMBER:
                            name = getXMLText(reader);
                            characteristics.setPageNumber(Integer.parseInt(name));
                            break;
                        case GLOSSY:
                            name = getXMLText(reader);
                            if (name.equals("true")) {
                                characteristics.setGlossy(true);
                            } else {
                                characteristics.setGlossy(false);
                            }
                            break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    if (PaperEnum.valueOf(name.toUpperCase()) == PaperEnum.CHARACTERISTICS) {
                        return characteristics;
                    }
                    break;
            }
        }
        throw new XMLStreamException("Unknown element in tag Characteristics");
    }

    private String getXMLText(XMLStreamReader reader) throws XMLStreamException {
        String text = null;
        if (reader.hasNext()) {
            reader.next();
            text = reader.getText();
        }
        return text;
    }
}
