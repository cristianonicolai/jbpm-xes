package org.jbpm.xes;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.jbpm.xes.model.LogType;
import org.jbpm.xes.model.ObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

public class XESLogMarshaller {

    private static final Logger LOGGER = LoggerFactory.getLogger(XESLogMarshaller.class);

    private JAXBContext context;
    private Schema xesSchema;

    public XESLogMarshaller() {
        try {
            context = JAXBContext.newInstance("org.jbpm.xes.model");
            final URL schema = Thread.currentThread().getContextClassLoader().getResource("xes.xsd");
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            xesSchema = sf.newSchema(schema);
        } catch (JAXBException | SAXException ex) {
            LOGGER.error("Error trying to create XES marshaller: {}",
                         ex.getMessage(),
                         ex);
            throw new RuntimeException(ex);
        }
    }

    public String marshall(LogType log) throws Exception {
        Marshaller m = context.createMarshaller();
        m.setSchema(xesSchema);
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
}
