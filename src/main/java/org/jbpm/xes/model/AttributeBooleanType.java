package org.jbpm.xes.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for AttributeBooleanType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="AttributeBooleanType">
 *   &lt;complexContent>
 *     &lt;extension base="{}AttributeType">
 *       &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttributeBooleanType")
public class AttributeBooleanType extends AttributeType {

    @XmlAttribute(name = "value", required = true)
    protected boolean value;

    /**
     * Gets the value of the value property.
     */
    public boolean isValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     */
    public void setValue(boolean value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "AttributeBooleanType{" +
                "value=" + value +
                ", key='" + key + '\'' +
                ", stringOrDateOrInt=" + stringOrDateOrInt +
                '}';
    }
}
