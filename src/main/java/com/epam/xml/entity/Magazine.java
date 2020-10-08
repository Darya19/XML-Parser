package com.epam.xml.entity;

public class Magazine extends Paper {

    private String index;

    @Override
    public String getOtherCharacteristic() {
        return index;
    }

    @Override
    public void setOtherCharacteristic(String index) {
        this.index = index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Magazine magazine = (Magazine) o;

        return index != null ? index.equals(magazine.index) : magazine.index == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (index != null ? index.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Magazine").append("\n");
        sb.append(super.toString());
        sb.append("index: ").append(index).append("\n");
        return sb.toString();
    }
}
