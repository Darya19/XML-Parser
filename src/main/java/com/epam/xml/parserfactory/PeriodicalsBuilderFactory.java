package com.epam.xml.parserfactory;

import com.epam.xml.builder.AbstractPeriodicalsBuilder;
import com.epam.xml.builder.impl.PeriodicalsDOMBuilderImpl;
import com.epam.xml.builder.impl.PeriodicalsSAXBuilderImpl;
import com.epam.xml.builder.impl.PeriodicalsStAXBuilderImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PeriodicalsBuilderFactory {

    private enum TypeParser {
        SAX, STAX, DOM
    }

    private Logger logger = LogManager.getLogger();

    public AbstractPeriodicalsBuilder createPeriodicalsBuilder(String typeParser) {
        TypeParser type = TypeParser.valueOf(typeParser.toUpperCase());
        switch (type) {
            case DOM:
                logger.log(Level.INFO, "DOM builder use...");
                return new PeriodicalsDOMBuilderImpl();
            case STAX:
                logger.log(Level.INFO, "STAX builder use...");
                return new PeriodicalsStAXBuilderImpl();
            case SAX:
                logger.log(Level.INFO, "SAX builder use...");
                return new PeriodicalsSAXBuilderImpl();
            default:
                throw new EnumConstantNotPresentException(type.getDeclaringClass(), type.name());
        }
    }
}
