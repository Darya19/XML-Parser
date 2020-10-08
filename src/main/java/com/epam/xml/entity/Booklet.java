package com.epam.xml.entity;

public class Booklet extends Paper {

    protected String bookletType;

    @Override
    public String getOtherCharacteristic() {
        return bookletType;
    }

    @Override
    public void setOtherCharacteristic(String bookletType) {
        this.bookletType = bookletType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Booklet booklet = (Booklet) o;

        return bookletType != null ? bookletType.equals(booklet.bookletType) : booklet.bookletType == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (bookletType != null ? bookletType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Booklet").append("\n");
        sb.append(super.toString());
        sb.append("bookletType: ").append(bookletType);
        sb.append("\n");
        return sb.toString();
    }
}
