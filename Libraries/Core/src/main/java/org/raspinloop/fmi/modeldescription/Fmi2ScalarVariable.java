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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * Properties of a scalar variable
 * 
 * <p>Classe Java pour fmi2ScalarVariable complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="fmi2ScalarVariable">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element name="Real">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;attGroup ref="{}fmi2RealAttributes"/>
 *                   &lt;attribute name="declaredType" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
 *                   &lt;attribute name="start" type="{http://www.w3.org/2001/XMLSchema}double" />
 *                   &lt;attribute name="derivative" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" />
 *                   &lt;attribute name="reinit" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="Integer">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;attGroup ref="{}fmi2IntegerAttributes"/>
 *                   &lt;attribute name="declaredType" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
 *                   &lt;attribute name="start" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="Boolean">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;attribute name="declaredType" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
 *                   &lt;attribute name="start" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="String">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;attribute name="declaredType" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
 *                   &lt;attribute name="start" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="Enumeration">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;attribute name="declaredType" use="required" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
 *                   &lt;attribute name="quantity" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
 *                   &lt;attribute name="min" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                   &lt;attribute name="max" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                   &lt;attribute name="start" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *         &lt;/choice>
 *         &lt;element name="Annotations" type="{}fmi2Annotation" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
 *       &lt;attribute name="valueReference" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" />
 *       &lt;attribute name="description" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="causality" default="local">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}normalizedString">
 *             &lt;enumeration value="parameter"/>
 *             &lt;enumeration value="calculatedParameter"/>
 *             &lt;enumeration value="input"/>
 *             &lt;enumeration value="output"/>
 *             &lt;enumeration value="local"/>
 *             &lt;enumeration value="independent"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="variability" default="continuous">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}normalizedString">
 *             &lt;enumeration value="constant"/>
 *             &lt;enumeration value="fixed"/>
 *             &lt;enumeration value="tunable"/>
 *             &lt;enumeration value="discrete"/>
 *             &lt;enumeration value="continuous"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="initial">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}normalizedString">
 *             &lt;enumeration value="exact"/>
 *             &lt;enumeration value="approx"/>
 *             &lt;enumeration value="calculated"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="canHandleMultipleSetPerTimeInstant" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fmi2ScalarVariable", propOrder = {
    "real",
    "integer",
    "_boolean",
    "string",
    "enumeration",
    "annotations"
})
public class Fmi2ScalarVariable {

    @XmlElement(name = "Real")
    protected Fmi2ScalarVariable.Real real;
    @XmlElement(name = "Integer")
    protected Fmi2ScalarVariable.Integer integer;
    @XmlElement(name = "Boolean")
    protected Fmi2ScalarVariable.Boolean _boolean;
    @XmlElement(name = "String")
    protected Fmi2ScalarVariable.String string;
    @XmlElement(name = "Enumeration")
    protected Fmi2ScalarVariable.Enumeration enumeration;
    @XmlElement(name = "Annotations")
    protected Fmi2Annotation annotations;
    @XmlAttribute(name = "name", required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected java.lang.String name;
    @XmlAttribute(name = "valueReference", required = true)
    @XmlSchemaType(name = "unsignedInt")
    protected long valueReference;
    @XmlAttribute(name = "description")
    protected java.lang.String description;
    @XmlAttribute(name = "causality")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected java.lang.String causality;
    @XmlAttribute(name = "variability")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected java.lang.String variability;
    @XmlAttribute(name = "initial")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected java.lang.String initial;
    @XmlAttribute(name = "canHandleMultipleSetPerTimeInstant")
    protected java.lang.Boolean canHandleMultipleSetPerTimeInstant;

    /**
     * Obtient la valeur de la propriété real.
     * 
     * @return
     *     possible object is
     *     {@link Fmi2ScalarVariable.Real }
     *     
     */
    public Fmi2ScalarVariable.Real getReal() {
        return real;
    }

    /**
     * Définit la valeur de la propriété real.
     * 
     * @param value
     *     allowed object is
     *     {@link Fmi2ScalarVariable.Real }
     *     
     */
    public void setReal(Fmi2ScalarVariable.Real value) {
        this.real = value;
    }

    /**
     * Obtient la valeur de la propriété integer.
     * 
     * @return
     *     possible object is
     *     {@link Fmi2ScalarVariable.Integer }
     *     
     */
    public Fmi2ScalarVariable.Integer getInteger() {
        return integer;
    }

    /**
     * Définit la valeur de la propriété integer.
     * 
     * @param value
     *     allowed object is
     *     {@link Fmi2ScalarVariable.Integer }
     *     
     */
    public void setInteger(Fmi2ScalarVariable.Integer value) {
        this.integer = value;
    }

    /**
     * Obtient la valeur de la propriété boolean.
     * 
     * @return
     *     possible object is
     *     {@link Fmi2ScalarVariable.Boolean }
     *     
     */
    public Fmi2ScalarVariable.Boolean getBoolean() {
        return _boolean;
    }

    /**
     * Définit la valeur de la propriété boolean.
     * 
     * @param value
     *     allowed object is
     *     {@link Fmi2ScalarVariable.Boolean }
     *     
     */
    public void setBoolean(Fmi2ScalarVariable.Boolean value) {
        this._boolean = value;
    }

    /**
     * Obtient la valeur de la propriété string.
     * 
     * @return
     *     possible object is
     *     {@link Fmi2ScalarVariable.String }
     *     
     */
    public Fmi2ScalarVariable.String getString() {
        return string;
    }

    /**
     * Définit la valeur de la propriété string.
     * 
     * @param value
     *     allowed object is
     *     {@link Fmi2ScalarVariable.String }
     *     
     */
    public void setString(Fmi2ScalarVariable.String value) {
        this.string = value;
    }

    /**
     * Obtient la valeur de la propriété enumeration.
     * 
     * @return
     *     possible object is
     *     {@link Fmi2ScalarVariable.Enumeration }
     *     
     */
    public Fmi2ScalarVariable.Enumeration getEnumeration() {
        return enumeration;
    }

    /**
     * Définit la valeur de la propriété enumeration.
     * 
     * @param value
     *     allowed object is
     *     {@link Fmi2ScalarVariable.Enumeration }
     *     
     */
    public void setEnumeration(Fmi2ScalarVariable.Enumeration value) {
        this.enumeration = value;
    }

    /**
     * Obtient la valeur de la propriété annotations.
     * 
     * @return
     *     possible object is
     *     {@link Fmi2Annotation }
     *     
     */
    public Fmi2Annotation getAnnotations() {
        return annotations;
    }

    /**
     * Définit la valeur de la propriété annotations.
     * 
     * @param value
     *     allowed object is
     *     {@link Fmi2Annotation }
     *     
     */
    public void setAnnotations(Fmi2Annotation value) {
        this.annotations = value;
    }

    /**
     * Obtient la valeur de la propriété name.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getName() {
        return name;
    }

    /**
     * Définit la valeur de la propriété name.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setName(java.lang.String value) {
        this.name = value;
    }

    /**
     * Obtient la valeur de la propriété valueReference.
     * 
     */
    public long getValueReference() {
        return valueReference;
    }

    /**
     * Définit la valeur de la propriété valueReference.
     * 
     */
    public void setValueReference(long value) {
        this.valueReference = value;
    }

    /**
     * Obtient la valeur de la propriété description.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getDescription() {
        return description;
    }

    /**
     * Définit la valeur de la propriété description.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setDescription(java.lang.String value) {
        this.description = value;
    }

    /**
     * Obtient la valeur de la propriété causality.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getCausality() {
        if (causality == null) {
            return "local";
        } else {
            return causality;
        }
    }

    /**
     * Définit la valeur de la propriété causality.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setCausality(java.lang.String value) {
        this.causality = value;
    }

    /**
     * Obtient la valeur de la propriété variability.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getVariability() {
        if (variability == null) {
            return "continuous";
        } else {
            return variability;
        }
    }

    /**
     * Définit la valeur de la propriété variability.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setVariability(java.lang.String value) {
        this.variability = value;
    }

    /**
     * Obtient la valeur de la propriété initial.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getInitial() {
        return initial;
    }

    /**
     * Définit la valeur de la propriété initial.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setInitial(java.lang.String value) {
        this.initial = value;
    }

    /**
     * Obtient la valeur de la propriété canHandleMultipleSetPerTimeInstant.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.Boolean }
     *     
     */
    public java.lang.Boolean isCanHandleMultipleSetPerTimeInstant() {
        return canHandleMultipleSetPerTimeInstant;
    }

    /**
     * Définit la valeur de la propriété canHandleMultipleSetPerTimeInstant.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.Boolean }
     *     
     */
    public void setCanHandleMultipleSetPerTimeInstant(java.lang.Boolean value) {
        this.canHandleMultipleSetPerTimeInstant = value;
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
     *       &lt;attribute name="declaredType" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
     *       &lt;attribute name="start" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Boolean {

        @XmlAttribute(name = "declaredType")
        @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
        @XmlSchemaType(name = "normalizedString")
        protected java.lang.String declaredType;
        @XmlAttribute(name = "start")
        protected java.lang.Boolean start;

        /**
         * Obtient la valeur de la propriété declaredType.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String }
         *     
         */
        public java.lang.String getDeclaredType() {
            return declaredType;
        }

        /**
         * Définit la valeur de la propriété declaredType.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String }
         *     
         */
        public void setDeclaredType(java.lang.String value) {
            this.declaredType = value;
        }

        /**
         * Obtient la valeur de la propriété start.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.Boolean }
         *     
         */
        public java.lang.Boolean isStart() {
            return start;
        }

        /**
         * Définit la valeur de la propriété start.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.Boolean }
         *     
         */
        public void setStart(java.lang.Boolean value) {
            this.start = value;
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
     *       &lt;attribute name="declaredType" use="required" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
     *       &lt;attribute name="quantity" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
     *       &lt;attribute name="min" type="{http://www.w3.org/2001/XMLSchema}int" />
     *       &lt;attribute name="max" type="{http://www.w3.org/2001/XMLSchema}int" />
     *       &lt;attribute name="start" type="{http://www.w3.org/2001/XMLSchema}int" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Enumeration {

        @XmlAttribute(name = "declaredType", required = true)
        @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
        @XmlSchemaType(name = "normalizedString")
        protected java.lang.String declaredType;
        @XmlAttribute(name = "quantity")
        @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
        @XmlSchemaType(name = "normalizedString")
        protected java.lang.String quantity;
        @XmlAttribute(name = "min")
        protected java.lang.Integer min;
        @XmlAttribute(name = "max")
        protected java.lang.Integer max;
        @XmlAttribute(name = "start")
        protected java.lang.Integer start;

        /**
         * Obtient la valeur de la propriété declaredType.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String }
         *     
         */
        public java.lang.String getDeclaredType() {
            return declaredType;
        }

        /**
         * Définit la valeur de la propriété declaredType.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String }
         *     
         */
        public void setDeclaredType(java.lang.String value) {
            this.declaredType = value;
        }

        /**
         * Obtient la valeur de la propriété quantity.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String }
         *     
         */
        public java.lang.String getQuantity() {
            return quantity;
        }

        /**
         * Définit la valeur de la propriété quantity.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String }
         *     
         */
        public void setQuantity(java.lang.String value) {
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

        /**
         * Obtient la valeur de la propriété start.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.Integer }
         *     
         */
        public java.lang.Integer getStart() {
            return start;
        }

        /**
         * Définit la valeur de la propriété start.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.Integer }
         *     
         */
        public void setStart(java.lang.Integer value) {
            this.start = value;
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
     *       &lt;attribute name="declaredType" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
     *       &lt;attribute name="start" type="{http://www.w3.org/2001/XMLSchema}int" />
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

        @XmlAttribute(name = "declaredType")
        @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
        @XmlSchemaType(name = "normalizedString")
        protected java.lang.String declaredType;
        @XmlAttribute(name = "start")
        protected java.lang.Integer start;
        @XmlAttribute(name = "quantity")
        @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
        @XmlSchemaType(name = "normalizedString")
        protected java.lang.String quantity;
        @XmlAttribute(name = "min")
        protected java.lang.Integer min;
        @XmlAttribute(name = "max")
        protected java.lang.Integer max;

        /**
         * Obtient la valeur de la propriété declaredType.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String }
         *     
         */
        public java.lang.String getDeclaredType() {
            return declaredType;
        }

        /**
         * Définit la valeur de la propriété declaredType.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String }
         *     
         */
        public void setDeclaredType(java.lang.String value) {
            this.declaredType = value;
        }

        /**
         * Obtient la valeur de la propriété start.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.Integer }
         *     
         */
        public java.lang.Integer getStart() {
            return start;
        }

        /**
         * Définit la valeur de la propriété start.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.Integer }
         *     
         */
        public void setStart(java.lang.Integer value) {
            this.start = value;
        }

        /**
         * Obtient la valeur de la propriété quantity.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String }
         *     
         */
        public java.lang.String getQuantity() {
            return quantity;
        }

        /**
         * Définit la valeur de la propriété quantity.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String }
         *     
         */
        public void setQuantity(java.lang.String value) {
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
     *       &lt;attribute name="declaredType" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
     *       &lt;attribute name="start" type="{http://www.w3.org/2001/XMLSchema}double" />
     *       &lt;attribute name="derivative" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" />
     *       &lt;attribute name="reinit" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
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

        @XmlAttribute(name = "declaredType")
        @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
        @XmlSchemaType(name = "normalizedString")
        protected java.lang.String declaredType;
        @XmlAttribute(name = "start")
        protected Double start;
        @XmlAttribute(name = "derivative")
        @XmlSchemaType(name = "unsignedInt")
        protected Long derivative;
        @XmlAttribute(name = "reinit")
        protected java.lang.Boolean reinit;
        @XmlAttribute(name = "quantity")
        @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
        @XmlSchemaType(name = "normalizedString")
        protected java.lang.String quantity;
        @XmlAttribute(name = "unit")
        @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
        @XmlSchemaType(name = "normalizedString")
        protected java.lang.String unit;
        @XmlAttribute(name = "displayUnit")
        @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
        @XmlSchemaType(name = "normalizedString")
        protected java.lang.String displayUnit;
        @XmlAttribute(name = "relativeQuantity")
        protected java.lang.Boolean relativeQuantity;
        @XmlAttribute(name = "min")
        protected Double min;
        @XmlAttribute(name = "max")
        protected Double max;
        @XmlAttribute(name = "nominal")
        protected Double nominal;
        @XmlAttribute(name = "unbounded")
        protected java.lang.Boolean unbounded;

        /**
         * Obtient la valeur de la propriété declaredType.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String }
         *     
         */
        public java.lang.String getDeclaredType() {
            return declaredType;
        }

        /**
         * Définit la valeur de la propriété declaredType.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String }
         *     
         */
        public void setDeclaredType(java.lang.String value) {
            this.declaredType = value;
        }

        /**
         * Obtient la valeur de la propriété start.
         * 
         * @return
         *     possible object is
         *     {@link Double }
         *     
         */
        public Double getStart() {
            return start;
        }

        /**
         * Définit la valeur de la propriété start.
         * 
         * @param value
         *     allowed object is
         *     {@link Double }
         *     
         */
        public void setStart(Double value) {
            this.start = value;
        }

        /**
         * Obtient la valeur de la propriété derivative.
         * 
         * @return
         *     possible object is
         *     {@link Long }
         *     
         */
        public Long getDerivative() {
            return derivative;
        }

        /**
         * Définit la valeur de la propriété derivative.
         * 
         * @param value
         *     allowed object is
         *     {@link Long }
         *     
         */
        public void setDerivative(Long value) {
            this.derivative = value;
        }

        /**
         * Obtient la valeur de la propriété reinit.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.Boolean }
         *     
         */
        public boolean isReinit() {
            if (reinit == null) {
                return false;
            } else {
                return reinit;
            }
        }

        /**
         * Définit la valeur de la propriété reinit.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.Boolean }
         *     
         */
        public void setReinit(java.lang.Boolean value) {
            this.reinit = value;
        }

        /**
         * Obtient la valeur de la propriété quantity.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String }
         *     
         */
        public java.lang.String getQuantity() {
            return quantity;
        }

        /**
         * Définit la valeur de la propriété quantity.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String }
         *     
         */
        public void setQuantity(java.lang.String value) {
            this.quantity = value;
        }

        /**
         * Obtient la valeur de la propriété unit.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String }
         *     
         */
        public java.lang.String getUnit() {
            return unit;
        }

        /**
         * Définit la valeur de la propriété unit.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String }
         *     
         */
        public void setUnit(java.lang.String value) {
            this.unit = value;
        }

        /**
         * Obtient la valeur de la propriété displayUnit.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String }
         *     
         */
        public java.lang.String getDisplayUnit() {
            return displayUnit;
        }

        /**
         * Définit la valeur de la propriété displayUnit.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String }
         *     
         */
        public void setDisplayUnit(java.lang.String value) {
            this.displayUnit = value;
        }

        /**
         * Obtient la valeur de la propriété relativeQuantity.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.Boolean }
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
         *     {@link java.lang.Boolean }
         *     
         */
        public void setRelativeQuantity(java.lang.Boolean value) {
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
         *     {@link java.lang.Boolean }
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
         *     {@link java.lang.Boolean }
         *     
         */
        public void setUnbounded(java.lang.Boolean value) {
            this.unbounded = value;
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
     *       &lt;attribute name="declaredType" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
     *       &lt;attribute name="start" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class String {

        @XmlAttribute(name = "declaredType")
        @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
        @XmlSchemaType(name = "normalizedString")
        protected java.lang.String declaredType;
        @XmlAttribute(name = "start")
        protected java.lang.String start;

        /**
         * Obtient la valeur de la propriété declaredType.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String }
         *     
         */
        public java.lang.String getDeclaredType() {
            return declaredType;
        }

        /**
         * Définit la valeur de la propriété declaredType.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String }
         *     
         */
        public void setDeclaredType(java.lang.String value) {
            this.declaredType = value;
        }

        /**
         * Obtient la valeur de la propriété start.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String }
         *     
         */
        public java.lang.String getStart() {
            return start;
        }

        /**
         * Définit la valeur de la propriété start.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String }
         *     
         */
        public void setStart(java.lang.String value) {
            this.start = value;
        }

    }

}
