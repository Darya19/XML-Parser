package com.epam.xmltest.buider;

import com.epam.xml.entity.Booklet;
import com.epam.xml.entity.Newspaper;
import com.epam.xml.entity.Paper;
import com.epam.xml.entity.PaperType;
import org.testng.annotations.DataProvider;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class StaticDataProvider {
    @DataProvider(name = "periodicals")
    public static Object[] text() {
        Set<Paper> periodicals = new HashSet<>();
        Paper paper = new Newspaper();
        paper.setTitle("Science development");
        paper.setType(PaperType.NEWSPAPER);
        paper.setMonthly(true);
        paper.setPublicationDate(LocalDate.of(1998, 3, 12));
        Paper.Characteristics characteristics = paper.setCharacteristics();
        characteristics.setGlossy(false);
        characteristics.setPageNumber(16);
        paper.setOtherCharacteristic("ghy/64");
        periodicals.add(paper);
        Paper paper1 = new Booklet();
        paper1.setTitle("Windows");
        paper1.setType(PaperType.BOOKLET);
        paper1.setMonthly(false);
        paper1.setPublicationDate(LocalDate.of(2019, 1, 23));
        Paper.Characteristics characteristics1 = paper1.setCharacteristics();
        characteristics1.setGlossy(true);
        characteristics1.setPageNumber(2);
        paper1.setOtherCharacteristic("fold");
        periodicals.add(paper1);
        return new Object[]{periodicals};
    }
}
