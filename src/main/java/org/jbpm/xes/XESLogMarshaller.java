package org.jbpm.xes;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.jbpm.xes.model.LogType;
import org.jbpm.xes.model.ObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XESLogMarshaller {

    private static final Logger LOGGER = LoggerFactory.getLogger(XESLogMarshaller.class);

    private JAXBContext context;

    public XESLogMarshaller() {
        try {
            context = JAXBContext.newInstance("org.jbpm.xes.model");
        } catch (JAXBException ex) {
            LOGGER.error("Error trying to create XES marshaller: {}",
                         ex.getMessage(),
                         ex);
            throw new RuntimeException(ex);
        }
    }

    public String marshall(LogType log) throws Exception {
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                      Boolean.TRUE);

        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        final ObjectFactory factory = new ObjectFactory();

        m.marshal(factory.createLog(log),
                  stream);

        String xml = stream.toString();
        LOGGER.debug("\n" + xml);
        return xml;
    }

    public LogType unmarshall(String xml) throws Exception {
        Unmarshaller unmarshaller = context.createUnmarshaller();
        JAXBElement<LogType> log = (JAXBElement<LogType>) unmarshaller.unmarshal(new StringReader(xml));
        return log.getValue();
    }
}
