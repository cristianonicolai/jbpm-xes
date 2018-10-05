
package org.jbpm.xes.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.jbpm.xes.model package.
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Log_QNAME = new QName("", "log");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.jbpm.xes.model
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LogType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "log")
    public JAXBElement<LogType> createLog(LogType value) {
        return new JAXBElement<>(_Log_QNAME, LogType.class, null, value);
    }

}
