package com.epam.xmltest.buider;

import com.epam.xml.builder.impl.PeriodicalsStAXBuilderImpl;
import com.epam.xml.entity.Paper;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class PeriodicalsStAXBuilderTest {
    PeriodicalsStAXBuilderImpl builder;

    @BeforeClass
    public void setUp() {
        builder = new PeriodicalsStAXBuilderImpl();
    }

    @Test(dataProvider = "periodicals", dataProviderClass = StaticDataProvider.class)
    public void buildSetPeriodicalsPositiveTest(HashSet<Paper> expected) {
        builder.buildSetPeriodicals("testdata/data.xml");
        Set<Paper> actual = builder.getPeriodicals();
        assertEquals(actual, expected);
    }

    @Test
    public void buildSetPeriodicalsNegativeTest() throws IOException {
        builder.buildSetPeriodicals("testdata/data1.xml");
    }

    @Test
    public void buildSetPeriodicalsNegativeXMLStreamTest() throws XMLStreamException {
        builder.buildSetPeriodicals("testdata/incorrectData.xml");
    }
}
