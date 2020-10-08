package com.epam.xml.builder;

import com.epam.xml.entity.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.time.LocalDate;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class PeriodicalsHandler extends DefaultHandler {

    private Set<Paper> periodicals;
    private Paper current = null;
    private PaperEnum currentEnum = null;
    private EnumSet<PaperEnum> withText;
    private Logger logger = LogManager.getLogger();

    public PeriodicalsHandler() {
        periodicals = new HashSet<>();
        withText = EnumSet.range(PaperEnum.PAPER, PaperEnum.BOOKLET_TYPE);
    }

    public Set<Paper> getPeriodicals() {
        return periodicals;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if ("newspaper".equals(localName)) {
            current = new Newspaper();
            logger.log(Level.INFO, "object newspaper create");
            current.setTitle(attributes.getValue(0));
            String type = attributes.getValue(1);
            current.setType(PaperType.valueOf(type.toUpperCase()));
        } else {
            if ("magazine".equals(localName)) {
                current = new Magazine();
                logger.log(Level.INFO, "object magazine create");
                current.setTitle(attributes.getValue(0));
                String type = attributes.getValue(1);
                current.setType(PaperType.valueOf(type.toUpperCase()));
            } else {
                if ("booklet".equals(localName)) {
                    current = new Booklet();
                    logger.log(Level.INFO, "object booklet create");
                    current.setTitle(attributes.getValue(0));
                    String type = attributes.getValue(1);
                    current.setType(PaperType.valueOf(type.toUpperCase()));
                } else {
                    PaperEnum temp = PaperEnum.valueOf(localName.toUpperCase());
                    if (withText.contains(temp)) {
                        currentEnum = temp;
                    }
                }
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String s = new String(ch, start, length).trim();
        if (currentEnum != null) {
            switch (currentEnum) {
                case PAPER:
                    break;
                case CHARACTERISTICS:
                    current.setCharacteristics();
                    break;
                case MONTHLY:
                    if (s.equals("true")) {
                        current.setMonthly(true);
                    } else {
                        current.setMonthly(false);
                    }
                    break;
                case PUBLICATION_DATE:
                    String[] date = s.split("\\.");
                    int[] intDate = new int[3];
                    for (int i = 0; i < date.length; i++) {
                        intDate[i] = Integer.parseInt(date[i]);
                    }
                    current.setPublicationDate(LocalDate.of(intDate[2], intDate[1], intDate[0]));
                    break;
                case GLOSSY:
                    if (s.equals("true")) {
                        current.getCharacteristics().setGlossy(true);
                    } else {
                        current.getCharacteristics().setGlossy(false);
                    }
                    break;
                case PAGE_NUMBER:
                    current.getCharacteristics().setPageNumber(Integer.parseInt(s));
                    break;
                case INDEX:
                case BOOKLET_TYPE:
                    current.setOtherCharacteristic(s);
                    break;
                default:
                    throw new EnumConstantNotPresentException(
                            currentEnum.getDeclaringClass(), currentEnum.name());
            }
        }
        currentEnum = null;
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if ("newspaper".equals(localName) || "magazine".equals(localName)
                || "booklet".equals(localName)) {
            periodicals.add(current);
        }
    }
}
