package com.epam.xmltest.buider;

import com.epam.xml.builder.impl.PeriodicalsDOMBuilderImpl;
import com.epam.xml.entity.Paper;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class PeriodicalsDOMBuilderTest {

    PeriodicalsDOMBuilderImpl builder;

    @BeforeClass
    public void setUp() {
        builder = new PeriodicalsDOMBuilderImpl();
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
    public void buildSetPeriodicalsNegativeSAXTest() throws SAXException {
        builder.buildSetPeriodicals("testdata/incorrectData.xml");
    }
}
