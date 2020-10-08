package com.epam.xml.main;

import com.epam.xml.builder.AbstractPeriodicalsBuilder;
import com.epam.xml.parserfactory.PeriodicalsBuilderFactory;

public class Main {
    public static void main(String[] args) {
        PeriodicalsBuilderFactory factory = new PeriodicalsBuilderFactory();
        AbstractPeriodicalsBuilder builder = factory.createPeriodicalsBuilder("dom");
        builder.buildSetPeriodicals("data/periodicals.xml");
        System.out.println(builder.getPeriodicals());
    }
}
