package com.epam.xml.builder;

public enum PaperEnum {

    PAPER("paper"),
    NEWSPAPER("newspaper"),
    MAGAZINE("magazine"),
    BOOKLET("booklet"),
    TITLE("title"),
    TYPE("type"),
    MONTHLY("monthly"),
    PUBLICATION_DATE("publication_date"),
    GLOSSY("glossy"),
    PAGE_NUMBER("page_number"),
    CHARACTERISTICS("characteristics"),
    INDEX("index"),
    BOOKLET_TYPE("booklet_type");

    private String value;

    PaperEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
