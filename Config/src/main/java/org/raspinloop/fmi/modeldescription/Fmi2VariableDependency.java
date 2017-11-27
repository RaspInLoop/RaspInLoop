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


/**
 * <p>Classe Java pour fmi2VariableDependency complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="fmi2VariableDependency">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence maxOccurs="unbounded">
 *         &lt;element name="Unknown">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="index" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" />
 *                 &lt;attribute name="dependencies">
 *                   &lt;simpleType>
 *                     &lt;list itemType="{http://www.w3.org/2001/XMLSchema}unsignedInt" />
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *                 &lt;attribute name="dependenciesKind">
 *                   &lt;simpleType>
 *                     &lt;list>
 *                       &lt;simpleType>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}normalizedString">
 *                           &lt;enumeration value="dependent"/>
 *                           &lt;enumeration value="constant"/>
 *                           &lt;enumeration value="fixed"/>
 *                           &lt;enumeration value="tunable"/>
 *                           &lt;enumeration value="discrete"/>
 *                         &lt;/restriction>
 *                       &lt;/simpleType>
 *                     &lt;/list>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fmi2VariableDependency", propOrder = {
    "unknown"
})
public class Fmi2VariableDependency {

    @XmlElement(name = "Unknown", required = true)
    protected List<Fmi2VariableDependency.Unknown> unknown;

    /**
     * Gets the value of the unknown property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the unknown property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUnknown().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Fmi2VariableDependency.Unknown }
     * 
     * 
     */
    public List<Fmi2VariableDependency.Unknown> getUnknown() {
        if (unknown == null) {
            unknown = new ArrayList<Fmi2VariableDependency.Unknown>();
        }
        return this.unknown;
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
     *       &lt;attribute name="index" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" />
     *       &lt;attribute name="dependencies">
     *         &lt;simpleType>
     *           &lt;list itemType="{http://www.w3.org/2001/XMLSchema}unsignedInt" />
     *         &lt;/simpleType>
     *       &lt;/attribute>
     *       &lt;attribute name="dependenciesKind">
     *         &lt;simpleType>
     *           &lt;list>
     *             &lt;simpleType>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}normalizedString">
     *                 &lt;enumeration value="dependent"/>
     *                 &lt;enumeration value="constant"/>
     *                 &lt;enumeration value="fixed"/>
     *                 &lt;enumeration value="tunable"/>
     *                 &lt;enumeration value="discrete"/>
     *               &lt;/restriction>
     *             &lt;/simpleType>
     *           &lt;/list>
     *         &lt;/simpleType>
     *       &lt;/attribute>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Unknown {

        @XmlAttribute(name = "index", required = true)
        @XmlSchemaType(name = "unsignedInt")
        protected long index;
        @XmlAttribute(name = "dependencies")
        protected List<Long> dependencies;
        @XmlAttribute(name = "dependenciesKind")
        protected List<String> dependenciesKind;

        /**
         * Obtient la valeur de la propriété index.
         * 
         */
        public long getIndex() {
            return index;
        }

        /**
         * Définit la valeur de la propriété index.
         * 
         */
        public void setIndex(long value) {
            this.index = value;
        }

        /**
         * Gets the value of the dependencies property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the dependencies property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDependencies().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Long }
         * 
         * 
         */
        public List<Long> getDependencies() {
            if (dependencies == null) {
                dependencies = new ArrayList<Long>();
            }
            return this.dependencies;
        }

        /**
         * Gets the value of the dependenciesKind property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the dependenciesKind property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDependenciesKind().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getDependenciesKind() {
            if (dependenciesKind == null) {
                dependenciesKind = new ArrayList<String>();
            }
            return this.dependenciesKind;
        }

    }

}
