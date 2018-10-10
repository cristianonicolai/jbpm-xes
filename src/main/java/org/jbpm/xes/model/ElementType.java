package org.jbpm.xes.model;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

/**
 * <p>Java class for ElementType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ElementType">
 *   &lt;complexContent>
 *     &lt;extension base="{}AttributableType">
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlTransient
@XmlSeeAlso({
        LogType.class,
        TraceType.class,
        EventType.class
})
public class ElementType extends AttributableType {

}
