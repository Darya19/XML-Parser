package com.epam.xml.entity;

import java.time.LocalDate;

public abstract class Paper {

    protected String title;
    protected PaperType type;
    protected boolean monthly;
    protected LocalDate publicationDate;
    protected Characteristics characteristics;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PaperType getType() {
        if (type == null) {
            return PaperType.PAPER;
        } else {
            return type;
        }
    }

    public void setType(PaperType type) {
        this.type = type;
    }

    public boolean isMonthly() {
        return monthly;
    }

    public void setMonthly(boolean monthly) {
        this.monthly = monthly;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Characteristics getCharacteristics() {
        return characteristics;
    }

    public Characteristics setCharacteristics() {
        if (characteristics == null) {
            this.characteristics = new Characteristics();
        }
        return characteristics;
    }

    public void setCharacteristics(Characteristics characteristics) {
        this.characteristics = characteristics;
    }

    public class Characteristics {

        private int pageNumber;
        private boolean glossy;

        public int getPageNumber() {
            return pageNumber;
        }

        public void setPageNumber(int pageNumber) {
            this.pageNumber = pageNumber;
        }

        public boolean isGlossy() {
            return glossy;
        }

        public void setGlossy(boolean glossy) {
            this.glossy = glossy;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append("pageNumber: ").append(pageNumber).append("\n");
            sb.append("glossy: ").append(glossy).append("\n");
            return sb.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Characteristics that = (Characteristics) o;

            if (getPageNumber() != that.getPageNumber()) return false;
            return isGlossy() == that.isGlossy();
        }

        @Override
        public int hashCode() {
            int result = getPageNumber();
            result = 31 * result + (isGlossy() ? 1 : 0);
            return result;
        }
    }

    public abstract String getOtherCharacteristic();

    public abstract void setOtherCharacteristic(String value);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Paper paper = (Paper) o;

        if (isMonthly() != paper.isMonthly()) return false;
        if (getTitle() != null ? !getTitle().equals(paper.getTitle()) : paper.getTitle() != null) return false;
        if (getType() != null ? !getType().equals(paper.getType()) : paper.getType() != null) return false;
        if (getPublicationDate() != null ? !getPublicationDate().equals(paper.getPublicationDate()) : paper.getPublicationDate() != null)
            return false;
        return getCharacteristics() != null ? getCharacteristics().equals(paper.getCharacteristics()) : paper.getCharacteristics() == null;
    }

    @Override
    public int hashCode() {
        int result = getTitle() != null ? getTitle().hashCode() : 0;
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        result = 31 * result + (isMonthly() ? 1 : 0);
        result = 31 * result + (getPublicationDate() != null ? getPublicationDate().hashCode() : 0);
        result = 31 * result + (getCharacteristics() != null ? getCharacteristics().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("title: ").append(title).append("\n");
        sb.append("type: ").append(type).append("\n");
        sb.append("monthly: ").append(monthly).append("\n");
        sb.append("publicationDate: ").append(publicationDate).append("\n");
        sb.append("characteristics \n").append(characteristics);
        return sb.toString();
    }
}
