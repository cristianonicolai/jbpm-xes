package org.jbpm.xes.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import org.jbpm.xes.mapper.XMLGregorianCalendarMapper;

import static java.util.Optional.ofNullable;

/**
 * <p>Java class for AttributableType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="AttributableType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="string" type="{}AttributeStringType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="date" type="{}AttributeDateType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="int" type="{}AttributeIntType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="float" type="{}AttributeFloatType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="boolean" type="{}AttributeBooleanType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="id" type="{}AttributeIDType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="list" type="{}AttributeListType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="container" type="{}AttributeContainerType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttributableType", propOrder = {
        "stringOrDateOrInt"
})
@XmlSeeAlso({
        ElementType.class,
        GlobalsType.class,
        AttributeContainerType.class,
        AttributeType.class
})
public class AttributableType {

    @XmlElements({
            @XmlElement(name = "string", type = AttributeStringType.class),
            @XmlElement(name = "date", type = AttributeDateType.class),
            @XmlElement(name = "int", type = AttributeIntType.class),
            @XmlElement(name = "float", type = AttributeFloatType.class),
            @XmlElement(name = "boolean", type = AttributeBooleanType.class),
            @XmlElement(name = "id", type = AttributeIDType.class),
            @XmlElement(name = "list", type = AttributeListType.class),
            @XmlElement(name = "container", type = AttributeContainerType.class)
    })
    protected List<AttributableType> stringOrDateOrInt;

    /**
     * Gets the value of the stringOrDateOrInt property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the stringOrDateOrInt property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStringOrDateOrInt().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AttributeStringType }
     * {@link AttributeDateType }
     * {@link AttributeIntType }
     * {@link AttributeFloatType }
     * {@link AttributeBooleanType }
     * {@link AttributeIDType }
     * {@link AttributeListType }
     * {@link AttributeContainerType }
     */
    public List<AttributableType> getStringOrDateOrInt() {
        if (stringOrDateOrInt == null) {
            stringOrDateOrInt = new ArrayList<AttributableType>();
        }
        return this.stringOrDateOrInt;
    }

    public void addStringType(
            String key,
            String value) {
        ofNullable(value).ifPresent(v -> getStringOrDateOrInt().add(new AttributeStringType(key,
                                                                                            v)));
    }

    public void addIntegerType(
            String key,
            Long value) {
        ofNullable(value).ifPresent(v -> getStringOrDateOrInt().add(new AttributeIntType(key,
                                                                                         v)));
    }

    public void addIntegerType(
            String key,
            Integer value) {
        ofNullable(value).ifPresent(v -> getStringOrDateOrInt().add(new AttributeIntType(key,
                                                                                         v)));
    }

    public void addDateType(
            String key,
            Date value) {
        ofNullable(value).ifPresent(v -> getStringOrDateOrInt().add(new AttributeDateType(key,
                                                                                          new XMLGregorianCalendarMapper().apply(v))));
    }
}
