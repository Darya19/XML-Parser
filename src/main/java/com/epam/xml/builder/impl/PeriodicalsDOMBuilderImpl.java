package com.epam.xml.builder.impl;

import com.epam.xml.builder.AbstractPeriodicalsBuilder;
import com.epam.xml.builder.PaperEnum;
import com.epam.xml.entity.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class PeriodicalsDOMBuilderImpl extends AbstractPeriodicalsBuilder {

    private Set<Paper> periodicals;
    private DocumentBuilder docBuilder;
    private Logger logger = LogManager.getLogger();

    public PeriodicalsDOMBuilderImpl() {
        this.periodicals = new HashSet<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            logger.log(Level.ERROR, "error in parser configuration", e);
        }
    }

    public PeriodicalsDOMBuilderImpl(Set<Paper> periodicals) {
        super(periodicals);
    }

    @Override
    public Set<Paper> getPeriodicals() {
        return periodicals;
    }

    @Override
    public void buildSetPeriodicals(String fileName) {
        Document document;
        try {
            document = docBuilder.parse(fileName);
            Element root = document.getDocumentElement();
            NodeList newspaperList = root.getElementsByTagName("p:newspaper");
            for (int i = 0; i < newspaperList.getLength(); i++) {
                Element newspaperElement = (Element) newspaperList.item(i);
                Paper newspaper = buildPaper(newspaperElement, PaperEnum.NEWSPAPER);
                periodicals.add(newspaper);
            }
            NodeList magazineList = root.getElementsByTagName("p:magazine");
            for (int i = 0; i < magazineList.getLength(); i++) {
                Element magazineElement = (Element) magazineList.item(i);
                Paper magazine = buildPaper(magazineElement, PaperEnum.MAGAZINE);
                periodicals.add(magazine);
            }
            NodeList bookletList = root.getElementsByTagName("p:booklet");
            for (int i = 0; i < bookletList.getLength(); i++) {
                Element bookletElement = (Element) bookletList.item(i);
                Paper booklet = buildPaper(bookletElement, PaperEnum.BOOKLET);
                periodicals.add(booklet);
            }
        } catch (IOException e) {
            logger.log(Level.ERROR, "File error or I/O error: ", e);
        } catch (SAXException e) {
            logger.log(Level.ERROR, "Parsing failure: ", e);
        }
    }

    private Paper buildPaper(Element paperElement, PaperEnum type) {
        Paper paper = null;
        if (type.equals(PaperEnum.NEWSPAPER)) {
            paper = new Newspaper();
            logger.log(Level.INFO, "object newspaper create");
        }
        if (type.equals(PaperEnum.MAGAZINE)) {
            paper = new Magazine();
            logger.log(Level.INFO, "object magazine create");
        }
        if (type.equals(PaperEnum.BOOKLET)) {
            paper = new Booklet();
            logger.log(Level.INFO, "object booklet create");
        }
        paper.setTitle(paperElement.getAttribute("title"));
        String stringType = paperElement.getAttribute("type");
        paper.setType(PaperType.valueOf(stringType.toUpperCase()));
        if (getElementTextContent(paperElement, "monthly").equals("true")) {
            paper.setMonthly(true);
        } else {
            paper.setMonthly(false);
        }
        String[] date = getElementTextContent(
                paperElement, "publication_date").split("\\.");
        int[] intDate = new int[3];
        for (int i = 0; i < date.length; i++) {
            intDate[i] = Integer.parseInt(date[i]);
        }
        paper.setPublicationDate(LocalDate.of(intDate[2], intDate[1], intDate[0]));
        Paper.Characteristics characteristics = paper.setCharacteristics();
        Element characteristicElement = (Element) paperElement
                .getElementsByTagName("p:characteristics").item(0);
        if (getElementTextContent(characteristicElement, "glossy").equals("true")) {
            characteristics.setGlossy(true);
        } else {
            characteristics.setGlossy(false);
        }
        Integer pageNumber = Integer.parseInt(getElementTextContent
                (characteristicElement, "page_number"));
        characteristics.setPageNumber(pageNumber);
        if (PaperEnum.BOOKLET.equals(type)) {
            paper.setOtherCharacteristic(getElementTextContent(paperElement, "booklet_type"));
        } else {
            paper.setOtherCharacteristic(getElementTextContent(paperElement, "index"));
        }
        return paper;
    }

    private static String getElementTextContent(Element element, String elementName) {
        NodeList nList = element.getElementsByTagName(elementName);
        Node node = nList.item(0);
        String text = node.getTextContent();
        return text;
    }
}
