package com.epam.xml.util;

import com.epam.xml.entity.Paper;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractPeriodicalsBuilder {

    protected Set<Paper> periodicals;

    public AbstractPeriodicalsBuilder() {
        periodicals = new HashSet<Paper>();
    }

    public AbstractPeriodicalsBuilder(Set<Paper> periodicals) {
        this.periodicals = periodicals;
    }

    public Set<Paper> getPeriodicals() {
        return periodicals;
    }

    abstract public void buildSetPeriodicals(String fileName);
}
