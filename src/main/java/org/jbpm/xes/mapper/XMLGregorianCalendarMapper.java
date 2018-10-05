package org.jbpm.xes.mapper;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.function.Function;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class XMLGregorianCalendarMapper implements Function<Date, XMLGregorianCalendar> {

    @Override
    public XMLGregorianCalendar apply(Date date) {
        GregorianCalendar gCalendar = new GregorianCalendar();
        gCalendar.setTime(date);
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gCalendar);
        } catch (Exception ex) {
            return null;
        }
    }
}
