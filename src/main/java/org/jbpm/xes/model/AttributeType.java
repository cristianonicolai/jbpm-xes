package org.jbpm.xes.model;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * <p>Java class for AttributeType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="AttributeType">
 *   &lt;complexContent>
 *     &lt;extension base="{}AttributableType">
 *       &lt;attribute name="key" use="required" type="{http://www.w3.org/2001/XMLSchema}Name" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlTransient
@XmlType(name = "AttributeType")
@XmlSeeAlso({
        AttributeIDType.class,
        AttributeIntType.class,
        AttributeDateType.class,
        AttributeFloatType.class,
        AttributeListType.class,
        AttributeBooleanType.class,
        AttributeStringType.class
})
public class AttributeType extends AttributableType {

    @XmlAttribute(name = "key", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "Name")
    protected String key;

    public AttributeType() {
    }

    public AttributeType(String key) {
        this.key = key;
    }

    /**
     * Gets the value of the key property.
     * @return possible object is
     * {@link String }
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * @param value allowed object is
     * {@link String }
     */
    public void setKey(String value) {
        this.key = value;
    }
}
