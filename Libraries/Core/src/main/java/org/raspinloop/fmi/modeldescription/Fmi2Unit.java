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
 * Unit definition (with respect to SI base units) and default display units
 * 
 * <p>Classe Java pour fmi2Unit complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="fmi2Unit">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BaseUnit" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="kg" type="{http://www.w3.org/2001/XMLSchema}int" default="0" />
 *                 &lt;attribute name="m" type="{http://www.w3.org/2001/XMLSchema}int" default="0" />
 *                 &lt;attribute name="s" type="{http://www.w3.org/2001/XMLSchema}int" default="0" />
 *                 &lt;attribute name="A" type="{http://www.w3.org/2001/XMLSchema}int" default="0" />
 *                 &lt;attribute name="K" type="{http://www.w3.org/2001/XMLSchema}int" default="0" />
 *                 &lt;attribute name="mol" type="{http://www.w3.org/2001/XMLSchema}int" default="0" />
 *                 &lt;attribute name="cd" type="{http://www.w3.org/2001/XMLSchema}int" default="0" />
 *                 &lt;attribute name="rad" type="{http://www.w3.org/2001/XMLSchema}int" default="0" />
 *                 &lt;attribute name="factor" type="{http://www.w3.org/2001/XMLSchema}double" default="1" />
 *                 &lt;attribute name="offset" type="{http://www.w3.org/2001/XMLSchema}double" default="0" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *           &lt;element name="DisplayUnit">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
 *                   &lt;attribute name="factor" type="{http://www.w3.org/2001/XMLSchema}double" default="1" />
 *                   &lt;attribute name="offset" type="{http://www.w3.org/2001/XMLSchema}double" default="0" />
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *         &lt;/sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fmi2Unit", propOrder = {
    "baseUnit",
    "displayUnit"
})
public class Fmi2Unit {

    @XmlElement(name = "BaseUnit")
    protected Fmi2Unit.BaseUnit baseUnit;
    @XmlElement(name = "DisplayUnit")
    protected List<Fmi2Unit.DisplayUnit> displayUnit;
    @XmlAttribute(name = "name", required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String name;

    /**
     * Obtient la valeur de la propriété baseUnit.
     * 
     * @return
     *     possible object is
     *     {@link Fmi2Unit.BaseUnit }
     *     
     */
    public Fmi2Unit.BaseUnit getBaseUnit() {
        return baseUnit;
    }

    /**
     * Définit la valeur de la propriété baseUnit.
     * 
     * @param value
     *     allowed object is
     *     {@link Fmi2Unit.BaseUnit }
     *     
     */
    public void setBaseUnit(Fmi2Unit.BaseUnit value) {
        this.baseUnit = value;
    }

    /**
     * Gets the value of the displayUnit property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the displayUnit property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDisplayUnit().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Fmi2Unit.DisplayUnit }
     * 
     * 
     */
    public List<Fmi2Unit.DisplayUnit> getDisplayUnit() {
        if (displayUnit == null) {
            displayUnit = new ArrayList<Fmi2Unit.DisplayUnit>();
        }
        return this.displayUnit;
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
     * <p>Classe Java pour anonymous complex type.
     * 
     * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="kg" type="{http://www.w3.org/2001/XMLSchema}int" default="0" />
     *       &lt;attribute name="m" type="{http://www.w3.org/2001/XMLSchema}int" default="0" />
     *       &lt;attribute name="s" type="{http://www.w3.org/2001/XMLSchema}int" default="0" />
     *       &lt;attribute name="A" type="{http://www.w3.org/2001/XMLSchema}int" default="0" />
     *       &lt;attribute name="K" type="{http://www.w3.org/2001/XMLSchema}int" default="0" />
     *       &lt;attribute name="mol" type="{http://www.w3.org/2001/XMLSchema}int" default="0" />
     *       &lt;attribute name="cd" type="{http://www.w3.org/2001/XMLSchema}int" default="0" />
     *       &lt;attribute name="rad" type="{http://www.w3.org/2001/XMLSchema}int" default="0" />
     *       &lt;attribute name="factor" type="{http://www.w3.org/2001/XMLSchema}double" default="1" />
     *       &lt;attribute name="offset" type="{http://www.w3.org/2001/XMLSchema}double" default="0" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class BaseUnit {

        @XmlAttribute(name = "kg")
        protected Integer kg;
        @XmlAttribute(name = "m")
        protected Integer m;
        @XmlAttribute(name = "s")
        protected Integer s;
        @XmlAttribute(name = "A")
        protected Integer a;
        @XmlAttribute(name = "K")
        protected Integer k;
        @XmlAttribute(name = "mol")
        protected Integer mol;
        @XmlAttribute(name = "cd")
        protected Integer cd;
        @XmlAttribute(name = "rad")
        protected Integer rad;
        @XmlAttribute(name = "factor")
        protected Double factor;
        @XmlAttribute(name = "offset")
        protected Double offset;

        /**
         * Obtient la valeur de la propriété kg.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public int getKg() {
            if (kg == null) {
                return  0;
            } else {
                return kg;
            }
        }

        /**
         * Définit la valeur de la propriété kg.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setKg(Integer value) {
            this.kg = value;
        }

        /**
         * Obtient la valeur de la propriété m.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public int getM() {
            if (m == null) {
                return  0;
            } else {
                return m;
            }
        }

        /**
         * Définit la valeur de la propriété m.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setM(Integer value) {
            this.m = value;
        }

        /**
         * Obtient la valeur de la propriété s.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public int getS() {
            if (s == null) {
                return  0;
            } else {
                return s;
            }
        }

        /**
         * Définit la valeur de la propriété s.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setS(Integer value) {
            this.s = value;
        }

        /**
         * Obtient la valeur de la propriété a.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public int getA() {
            if (a == null) {
                return  0;
            } else {
                return a;
            }
        }

        /**
         * Définit la valeur de la propriété a.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setA(Integer value) {
            this.a = value;
        }

        /**
         * Obtient la valeur de la propriété k.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public int getK() {
            if (k == null) {
                return  0;
            } else {
                return k;
            }
        }

        /**
         * Définit la valeur de la propriété k.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setK(Integer value) {
            this.k = value;
        }

        /**
         * Obtient la valeur de la propriété mol.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public int getMol() {
            if (mol == null) {
                return  0;
            } else {
                return mol;
            }
        }

        /**
         * Définit la valeur de la propriété mol.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setMol(Integer value) {
            this.mol = value;
        }

        /**
         * Obtient la valeur de la propriété cd.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public int getCd() {
            if (cd == null) {
                return  0;
            } else {
                return cd;
            }
        }

        /**
         * Définit la valeur de la propriété cd.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setCd(Integer value) {
            this.cd = value;
        }

        /**
         * Obtient la valeur de la propriété rad.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public int getRad() {
            if (rad == null) {
                return  0;
            } else {
                return rad;
            }
        }

        /**
         * Définit la valeur de la propriété rad.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setRad(Integer value) {
            this.rad = value;
        }

        /**
         * Obtient la valeur de la propriété factor.
         * 
         * @return
         *     possible object is
         *     {@link Double }
         *     
         */
        public double getFactor() {
            if (factor == null) {
                return  1.0D;
            } else {
                return factor;
            }
        }

        /**
         * Définit la valeur de la propriété factor.
         * 
         * @param value
         *     allowed object is
         *     {@link Double }
         *     
         */
        public void setFactor(Double value) {
            this.factor = value;
        }

        /**
         * Obtient la valeur de la propriété offset.
         * 
         * @return
         *     possible object is
         *     {@link Double }
         *     
         */
        public double getOffset() {
            if (offset == null) {
                return  0.0D;
            } else {
                return offset;
            }
        }

        /**
         * Définit la valeur de la propriété offset.
         * 
         * @param value
         *     allowed object is
         *     {@link Double }
         *     
         */
        public void setOffset(Double value) {
            this.offset = value;
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
     *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
     *       &lt;attribute name="factor" type="{http://www.w3.org/2001/XMLSchema}double" default="1" />
     *       &lt;attribute name="offset" type="{http://www.w3.org/2001/XMLSchema}double" default="0" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class DisplayUnit {

        @XmlAttribute(name = "name", required = true)
        @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
        @XmlSchemaType(name = "normalizedString")
        protected String name;
        @XmlAttribute(name = "factor")
        protected Double factor;
        @XmlAttribute(name = "offset")
        protected Double offset;

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
         * Obtient la valeur de la propriété factor.
         * 
         * @return
         *     possible object is
         *     {@link Double }
         *     
         */
        public double getFactor() {
            if (factor == null) {
                return  1.0D;
            } else {
                return factor;
            }
        }

        /**
         * Définit la valeur de la propriété factor.
         * 
         * @param value
         *     allowed object is
         *     {@link Double }
         *     
         */
        public void setFactor(Double value) {
            this.factor = value;
        }

        /**
         * Obtient la valeur de la propriété offset.
         * 
         * @return
         *     possible object is
         *     {@link Double }
         *     
         */
        public double getOffset() {
            if (offset == null) {
                return  0.0D;
            } else {
                return offset;
            }
        }

        /**
         * Définit la valeur de la propriété offset.
         * 
         * @param value
         *     allowed object is
         *     {@link Double }
         *     
         */
        public void setOffset(Double value) {
            this.offset = value;
        }

    }

}
