/*******************************************************************************
 * Copyright 2018 RaspInLoop
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.7 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2015.03.16 à 07:28:31 AM CET 
//


package org.raspinloop.fmi.modeldescription;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * Type attributes of a scalar variable
 * 
 * <p>Classe Java pour fmi2SimpleType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="fmi2SimpleType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element name="Real">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;attGroup ref="{}fmi2RealAttributes"/>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="Integer">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;attGroup ref="{}fmi2IntegerAttributes"/>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="Boolean" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *           &lt;element name="String" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *           &lt;element name="Enumeration">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence maxOccurs="unbounded">
 *                     &lt;element name="Item">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
 *                             &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                             &lt;attribute name="description" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                   &lt;/sequence>
 *                   &lt;attribute name="quantity" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
 *       &lt;attribute name="description" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fmi2SimpleType", propOrder = {
    "real",
    "integer",
    "_boolean",
    "string",
    "enumeration"
})
public class Fmi2SimpleType {

    @XmlElement(name = "Real")
    protected Fmi2SimpleType.Real real;
    @XmlElement(name = "Integer")
    protected Fmi2SimpleType.Integer integer;
    @XmlElement(name = "Boolean")
    protected Object _boolean;
    @XmlElement(name = "String")
    protected Object string;
    @XmlElement(name = "Enumeration")
    protected Fmi2SimpleType.Enumeration enumeration;
    @XmlAttribute(name = "name", required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String name;
    @XmlAttribute(name = "description")
    protected String description;

    /**
     * Obtient la valeur de la propriété real.
     * 
     * @return
     *     possible object is
     *     {@link Fmi2SimpleType.Real }
     *     
     */
    public Fmi2SimpleType.Real getReal() {
        return real;
    }

    /**
     * Définit la valeur de la propriété real.
     * 
     * @param value
     *     allowed object is
     *     {@link Fmi2SimpleType.Real }
     *     
     */
    public void setReal(Fmi2SimpleType.Real value) {
        this.real = value;
    }

    /**
     * Obtient la valeur de la propriété integer.
     * 
     * @return
     *     possible object is
     *     {@link Fmi2SimpleType.Integer }
     *     
     */
    public Fmi2SimpleType.Integer getInteger() {
        return integer;
    }

    /**
     * Définit la valeur de la propriété integer.
     * 
     * @param value
     *     allowed object is
     *     {@link Fmi2SimpleType.Integer }
     *     
     */
    public void setInteger(Fmi2SimpleType.Integer value) {
        this.integer = value;
    }

    /**
     * Obtient la valeur de la propriété boolean.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getBoolean() {
        return _boolean;
    }

    /**
     * Définit la valeur de la propriété boolean.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setBoolean(Object value) {
        this._boolean = value;
    }

    /**
     * Obtient la valeur de la propriété string.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getString() {
        return string;
    }

    /**
     * Définit la valeur de la propriété string.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setString(Object value) {
        this.string = value;
    }

    /**
     * Obtient la valeur de la propriété enumeration.
     * 
     * @return
     *     possible object is
     *     {@link Fmi2SimpleType.Enumeration }
     *     
     */
    public Fmi2SimpleType.Enumeration getEnumeration() {
        return enumeration;
    }

    /**
     * Définit la valeur de la propriété enumeration.
     * 
     * @param value
     *     allowed object is
     *     {@link Fmi2SimpleType.Enumeration }
     *     
     */
    public void setEnumeration(Fmi2SimpleType.Enumeration value) {
        this.enumeration = value;
    }

    /**
     * Obtient la valeur de la propriété name.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Définit la valeur de la propriété name.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Obtient la valeur de la propriété description.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Définit la valeur de la propriété description.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }


    /**
     * <p>Classe Java pour anonymous complex type.
     * 
     * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence maxOccurs="unbounded">
     *         &lt;element name="Item">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
     *                 &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
     *                 &lt;attribute name="description" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *       &lt;attribute name="quantity" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "item"
    })
    public static class Enumeration {

        @XmlElement(name = "Item", required = true)
        protected List<Fmi2SimpleType.Enumeration.Item> item;
        @XmlAttribute(name = "quantity")
        @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
        @XmlSchemaType(name = "normalizedString")
        protected String quantity;

        /**
         * Gets the value of the item property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the item property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getItem().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Fmi2SimpleType.Enumeration.Item }
         * 
         * 
         */
        public List<Fmi2SimpleType.Enumeration.Item> getItem() {
            if (item == null) {
                item = new ArrayList<Fmi2SimpleType.Enumeration.Item>();
            }
            return this.item;
        }

        /**
         * Obtient la valeur de la propriété quantity.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getQuantity() {
            return quantity;
        }

        /**
         * Définit la valeur de la propriété quantity.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setQuantity(String value) {
            this.quantity = value;
        }


        /**
         * <p>Classe Java pour anonymous complex type.
         * 
         * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
         *       &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
         *       &lt;attribute name="description" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class Item {

            @XmlAttribute(name = "name", required = true)
            @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
            @XmlSchemaType(name = "normalizedString")
            protected String name;
            @XmlAttribute(name = "value", required = true)
            protected int value;
            @XmlAttribute(name = "description")
            protected String description;

            /**
             * Obtient la valeur de la propriété name.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getName() {
                return name;
            }

            /**
             * Définit la valeur de la propriété name.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setName(String value) {
                this.name = value;
            }

            /**
             * Obtient la valeur de la propriété value.
             * 
             */
            public int getValue() {
                return value;
            }

            /**
             * Définit la valeur de la propriété value.
             * 
             */
            public void setValue(int value) {
                this.value = value;
            }

            /**
             * Obtient la valeur de la propriété description.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDescription() {
                return description;
            }

            /**
             * Définit la valeur de la propriété description.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDescription(String value) {
                this.description = value;
            }

        }

    }


    /**
     * <p>Classe Java pour anonymous complex type.
     * 
     * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attGroup ref="{}fmi2IntegerAttributes"/>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Integer {

        @XmlAttribute(name = "quantity")
        @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
        @XmlSchemaType(name = "normalizedString")
        protected String quantity;
        @XmlAttribute(name = "min")
        protected java.lang.Integer min;
        @XmlAttribute(name = "max")
        protected java.lang.Integer max;

        /**
         * Obtient la valeur de la propriété quantity.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getQuantity() {
            return quantity;
        }

        /**
         * Définit la valeur de la propriété quantity.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setQuantity(String value) {
            this.quantity = value;
        }

        /**
         * Obtient la valeur de la propriété min.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.Integer }
         *     
         */
        public java.lang.Integer getMin() {
            return min;
        }

        /**
         * Définit la valeur de la propriété min.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.Integer }
         *     
         */
        public void setMin(java.lang.Integer value) {
            this.min = value;
        }

        /**
         * Obtient la valeur de la propriété max.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.Integer }
         *     
         */
        public java.lang.Integer getMax() {
            return max;
        }

        /**
         * Définit la valeur de la propriété max.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.Integer }
         *     
         */
        public void setMax(java.lang.Integer value) {
            this.max = value;
        }

    }


    /**
     * <p>Classe Java pour anonymous complex type.
     * 
     * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attGroup ref="{}fmi2RealAttributes"/>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Real {

        @XmlAttribute(name = "quantity")
        @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
        @XmlSchemaType(name = "normalizedString")
        protected String quantity;
        @XmlAttribute(name = "unit")
        @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
        @XmlSchemaType(name = "normalizedString")
        protected String unit;
        @XmlAttribute(name = "displayUnit")
        @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
        @XmlSchemaType(name = "normalizedString")
        protected String displayUnit;
        @XmlAttribute(name = "relativeQuantity")
        protected Boolean relativeQuantity;
        @XmlAttribute(name = "min")
        protected Double min;
        @XmlAttribute(name = "max")
        protected Double max;
        @XmlAttribute(name = "nominal")
        protected Double nominal;
        @XmlAttribute(name = "unbounded")
        protected Boolean unbounded;

        /**
         * Obtient la valeur de la propriété quantity.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getQuantity() {
            return quantity;
        }

        /**
         * Définit la valeur de la propriété quantity.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setQuantity(String value) {
            this.quantity = value;
        }

        /**
         * Obtient la valeur de la propriété unit.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getUnit() {
            return unit;
        }

        /**
         * Définit la valeur de la propriété unit.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setUnit(String value) {
            this.unit = value;
        }

        /**
         * Obtient la valeur de la propriété displayUnit.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDisplayUnit() {
            return displayUnit;
        }

        /**
         * Définit la valeur de la propriété displayUnit.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDisplayUnit(String value) {
            this.displayUnit = value;
        }

        /**
         * Obtient la valeur de la propriété relativeQuantity.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public boolean isRelativeQuantity() {
            if (relativeQuantity == null) {
                return false;
            } else {
                return relativeQuantity;
            }
        }

        /**
         * Définit la valeur de la propriété relativeQuantity.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setRelativeQuantity(Boolean value) {
            this.relativeQuantity = value;
        }

        /**
         * Obtient la valeur de la propriété min.
         * 
         * @return
         *     possible object is
         *     {@link Double }
         *     
         */
        public Double getMin() {
            return min;
        }

        /**
         * Définit la valeur de la propriété min.
         * 
         * @param value
         *     allowed object is
         *     {@link Double }
         *     
         */
        public void setMin(Double value) {
            this.min = value;
        }

        /**
         * Obtient la valeur de la propriété max.
         * 
         * @return
         *     possible object is
         *     {@link Double }
         *     
         */
        public Double getMax() {
            return max;
        }

        /**
         * Définit la valeur de la propriété max.
         * 
         * @param value
         *     allowed object is
         *     {@link Double }
         *     
         */
        public void setMax(Double value) {
            this.max = value;
        }

        /**
         * Obtient la valeur de la propriété nominal.
         * 
         * @return
         *     possible object is
         *     {@link Double }
         *     
         */
        public Double getNominal() {
            return nominal;
        }

        /**
         * Définit la valeur de la propriété nominal.
         * 
         * @param value
         *     allowed object is
         *     {@link Double }
         *     
         */
        public void setNominal(Double value) {
            this.nominal = value;
        }

        /**
         * Obtient la valeur de la propriété unbounded.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public boolean isUnbounded() {
            if (unbounded == null) {
                return false;
            } else {
                return unbounded;
            }
        }

        /**
         * Définit la valeur de la propriété unbounded.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setUnbounded(Boolean value) {
            this.unbounded = value;
        }

    }

}
