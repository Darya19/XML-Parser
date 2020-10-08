package com.epam.xml.builder;

import com.epam.xml.entity.Paper;

import java.util.Set;

public abstract class AbstractPeriodicalsBuilder {

    protected Set<Paper> periodicals;

    public AbstractPeriodicalsBuilder() {
    }

    public AbstractPeriodicalsBuilder(Set<Paper> periodicals) {
        this.periodicals = periodicals;
    }

    public Set<Paper> getPeriodicals() {
        return periodicals;
    }

    abstract public void buildSetPeriodicals(String fileName);
}
