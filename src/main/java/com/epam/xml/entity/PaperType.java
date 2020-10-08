package com.epam.xml.entity;

public enum PaperType {
    PAPER("paper"),
    NEWSPAPER("newspaper"),
    MAGAZINE("magazine"),
    BOOKLET("booklet");

    private String value;

    PaperType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
