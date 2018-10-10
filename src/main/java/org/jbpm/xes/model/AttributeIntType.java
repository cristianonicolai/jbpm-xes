package org.jbpm.xes.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for AttributeIntType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="AttributeIntType">
 *   &lt;complexContent>
 *     &lt;extension base="{}AttributeType">
 *       &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}long" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttributeIntType")
public class AttributeIntType extends AttributeType {

    @XmlAttribute(name = "value", required = true)
    protected long value;

    public AttributeIntType() {
    }

    public AttributeIntType(String key,
                            long value) {
        super(key);
        this.value = value;
    }

    /**
     * Gets the value of the value property.
     */
    public long getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     */
    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "AttributeIntType{" +
                "value=" + value +
                ", key='" + key + '\'' +
                ", stringOrDateOrInt=" + stringOrDateOrInt +
                '}';
    }
}
