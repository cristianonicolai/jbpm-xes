package org.jbpm.xes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

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

    public LogType unmarshall(String xml) throws Exception {
        Unmarshaller unmarshaller = context.createUnmarshaller();
        unmarshaller.setSchema(xesSchema);

        JAXBElement<LogType> log = (JAXBElement<LogType>) unmarshaller.unmarshal(new StringReader(xml));
        return log.getValue();
    }

    public void validate(String xml) throws Exception {
        Validator validator = xesSchema.newValidator();
        validator.validate(new StreamSource(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8))));
    }
}
