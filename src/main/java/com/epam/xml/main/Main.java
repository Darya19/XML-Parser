package com.epam.xml.main;

import com.epam.xml.util.AbstractPeriodicalsBuilder;
import com.epam.xml.util.impl.PeriodicalsSAXBuilder;

public class Main {
    public static void main(String[] args) {
        AbstractPeriodicalsBuilder saxBuilder = new PeriodicalsSAXBuilder();
        saxBuilder.buildSetPeriodicals("data/periodicals.xml");
        System.out.println(saxBuilder.getPeriodicals());

    }
}
