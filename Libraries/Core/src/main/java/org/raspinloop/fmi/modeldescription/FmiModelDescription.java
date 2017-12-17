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
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java pour anonymous complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;sequence maxOccurs="2">
 *           &lt;element name="ModelExchange" minOccurs="0">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="SourceFiles" minOccurs="0">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;sequence maxOccurs="unbounded">
 *                               &lt;element name="File">
 *                                 &lt;complexType>
 *                                   &lt;complexContent>
 *                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
 *                                     &lt;/restriction>
 *                                   &lt;/complexContent>
 *                                 &lt;/complexType>
 *                               &lt;/element>
 *                             &lt;/sequence>
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                   &lt;/sequence>
 *                   &lt;attribute name="modelIdentifier" use="required" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
 *                   &lt;attribute name="needsExecutionTool" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *                   &lt;attribute name="completedIntegratorStepNotNeeded" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *                   &lt;attribute name="canBeInstantiatedOnlyOncePerProcess" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *                   &lt;attribute name="canNotUseMemoryManagementFunctions" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *                   &lt;attribute name="canGetAndSetFMUstate" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *                   &lt;attribute name="canSerializeFMUstate" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *                   &lt;attribute name="providesDirectionalDerivative" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="CoSimulation" minOccurs="0">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="SourceFiles" minOccurs="0">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;sequence maxOccurs="unbounded">
 *                               &lt;element name="File">
 *                                 &lt;complexType>
 *                                   &lt;complexContent>
 *                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
 *                                     &lt;/restriction>
 *                                   &lt;/complexContent>
 *                                 &lt;/complexType>
 *                               &lt;/element>
 *                             &lt;/sequence>
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                   &lt;/sequence>
 *                   &lt;attribute name="modelIdentifier" use="required" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
 *                   &lt;attribute name="needsExecutionTool" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *                   &lt;attribute name="canHandleVariableCommunicationStepSize" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *                   &lt;attribute name="canInterpolateInputs" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *                   &lt;attribute name="maxOutputDerivativeOrder" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" default="0" />
 *                   &lt;attribute name="canRunAsynchronuously" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *                   &lt;attribute name="canBeInstantiatedOnlyOncePerProcess" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *                   &lt;attribute name="canNotUseMemoryManagementFunctions" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *                   &lt;attribute name="canGetAndSetFMUstate" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *                   &lt;attribute name="canSerializeFMUstate" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *                   &lt;attribute name="providesDirectionalDerivative" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *         &lt;/sequence>
 *         &lt;element name="UnitDefinitions" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence maxOccurs="unbounded">
 *                   &lt;element name="Unit" type="{}fmi2Unit"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="TypeDefinitions" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence maxOccurs="unbounded">
 *                   &lt;element name="SimpleType" type="{}fmi2SimpleType"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="LogCategories" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence maxOccurs="unbounded">
 *                   &lt;element name="Category">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
 *                           &lt;attribute name="description" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="DefaultExperiment" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="startTime" type="{http://www.w3.org/2001/XMLSchema}double" />
 *                 &lt;attribute name="stopTime" type="{http://www.w3.org/2001/XMLSchema}double" />
 *                 &lt;attribute name="tolerance" type="{http://www.w3.org/2001/XMLSchema}double" />
 *                 &lt;attribute name="stepSize" type="{http://www.w3.org/2001/XMLSchema}double" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="VendorAnnotations" type="{}fmi2Annotation" minOccurs="0"/>
 *         &lt;element name="ModelVariables">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence maxOccurs="unbounded">
 *                   &lt;element name="ScalarVariable" type="{}fmi2ScalarVariable"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="ModelStructure">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Outputs" type="{}fmi2VariableDependency" minOccurs="0"/>
 *                   &lt;element name="Derivatives" type="{}fmi2VariableDependency" minOccurs="0"/>
 *                   &lt;element name="InitialUnknowns" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence maxOccurs="unbounded">
 *                             &lt;element name="Unknown">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;attribute name="index" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" />
 *                                     &lt;attribute name="dependencies">
 *                                       &lt;simpleType>
 *                                         &lt;list itemType="{http://www.w3.org/2001/XMLSchema}unsignedInt" />
 *                                       &lt;/simpleType>
 *                                     &lt;/attribute>
 *                                     &lt;attribute name="dependenciesKind">
 *                                       &lt;simpleType>
 *                                         &lt;list>
 *                                           &lt;simpleType>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}normalizedString">
 *                                               &lt;enumeration value="dependent"/>
 *                                               &lt;enumeration value="constant"/>
 *                                               &lt;enumeration value="fixed"/>
 *                                               &lt;enumeration value="tunable"/>
 *                                               &lt;enumeration value="discrete"/>
 *                                             &lt;/restriction>
 *                                           &lt;/simpleType>
 *                                         &lt;/list>
 *                                       &lt;/simpleType>
 *                                     &lt;/attribute>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="fmiVersion" use="required" type="{http://www.w3.org/2001/XMLSchema}normalizedString" fixed="2.0" />
 *       &lt;attribute name="modelName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="guid" use="required" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
 *       &lt;attribute name="description" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="author" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="version" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
 *       &lt;attribute name="copyright" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="license" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="generationTool" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
 *       &lt;attribute name="generationDateAndTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="variableNamingConvention" default="flat">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}normalizedString">
 *             &lt;enumeration value="flat"/>
 *             &lt;enumeration value="structured"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="numberOfEventIndicators" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "modelExchangeAndCoSimulation",
    "unitDefinitions",
    "typeDefinitions",
    "logCategories",
    "defaultExperiment",
    "vendorAnnotations",
    "modelVariables",
    "modelStructure"
})
@XmlRootElement(name = "fmiModelDescription")
public class FmiModelDescription {

    @XmlElements({
        @XmlElement(name = "ModelExchange", type = FmiModelDescription.ModelExchange.class),
        @XmlElement(name = "CoSimulation", type = FmiModelDescription.CoSimulation.class)
    })
    protected List<Object> modelExchangeAndCoSimulation;
    @XmlElement(name = "UnitDefinitions")
    protected FmiModelDescription.UnitDefinitions unitDefinitions;
    @XmlElement(name = "TypeDefinitions")
    protected FmiModelDescription.TypeDefinitions typeDefinitions;
    @XmlElement(name = "LogCategories")
    protected FmiModelDescription.LogCategories logCategories;
    @XmlElement(name = "DefaultExperiment")
    protected FmiModelDescription.DefaultExperiment defaultExperiment;
    @XmlElement(name = "VendorAnnotations")
    protected Fmi2Annotation vendorAnnotations;
    @XmlElement(name = "ModelVariables", required = true)
    protected FmiModelDescription.ModelVariables modelVariables;
    @XmlElement(name = "ModelStructure", required = true)
    protected FmiModelDescription.ModelStructure modelStructure;
    @XmlAttribute(name = "fmiVersion", required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String fmiVersion;
    @XmlAttribute(name = "modelName", required = true)
    protected String modelName;
    @XmlAttribute(name = "guid", required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String guid;
    @XmlAttribute(name = "description")
    protected String description;
    @XmlAttribute(name = "author")
    protected String author;
    @XmlAttribute(name = "version")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String version;
    @XmlAttribute(name = "copyright")
    protected String copyright;
    @XmlAttribute(name = "license")
    protected String license;
    @XmlAttribute(name = "generationTool")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String generationTool;
    @XmlAttribute(name = "generationDateAndTime")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar generationDateAndTime;
    @XmlAttribute(name = "variableNamingConvention")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String variableNamingConvention;
    @XmlAttribute(name = "numberOfEventIndicators")
    @XmlSchemaType(name = "unsignedInt")
    protected Long numberOfEventIndicators;

    /**
     * Gets the value of the modelExchangeAndCoSimulation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the modelExchangeAndCoSimulation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getModelExchangeAndCoSimulation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FmiModelDescription.ModelExchange }
     * {@link FmiModelDescription.CoSimulation }
     * 
     * 
     */
    public List<Object> getModelExchangeAndCoSimulation() {
        if (modelExchangeAndCoSimulation == null) {
            modelExchangeAndCoSimulation = new ArrayList<Object>();
        }
        return this.modelExchangeAndCoSimulation;
    }

    /**
     * Obtient la valeur de la propriété unitDefinitions.
     * 
     * @return
     *     possible object is
     *     {@link FmiModelDescription.UnitDefinitions }
     *     
     */
    public FmiModelDescription.UnitDefinitions getUnitDefinitions() {
        return unitDefinitions;
    }

    /**
     * Définit la valeur de la propriété unitDefinitions.
     * 
     * @param value
     *     allowed object is
     *     {@link FmiModelDescription.UnitDefinitions }
     *     
     */
    public void setUnitDefinitions(FmiModelDescription.UnitDefinitions value) {
        this.unitDefinitions = value;
    }

    /**
     * Obtient la valeur de la propriété typeDefinitions.
     * 
     * @return
     *     possible object is
     *     {@link FmiModelDescription.TypeDefinitions }
     *     
     */
    public FmiModelDescription.TypeDefinitions getTypeDefinitions() {
        return typeDefinitions;
    }

    /**
     * Définit la valeur de la propriété typeDefinitions.
     * 
     * @param value
     *     allowed object is
     *     {@link FmiModelDescription.TypeDefinitions }
     *     
     */
    public void setTypeDefinitions(FmiModelDescription.TypeDefinitions value) {
        this.typeDefinitions = value;
    }

    /**
     * Obtient la valeur de la propriété logCategories.
     * 
     * @return
     *     possible object is
     *     {@link FmiModelDescription.LogCategories }
     *     
     */
    public FmiModelDescription.LogCategories getLogCategories() {
        return logCategories;
    }

    /**
     * Définit la valeur de la propriété logCategories.
     * 
     * @param value
     *     allowed object is
     *     {@link FmiModelDescription.LogCategories }
     *     
     */
    public void setLogCategories(FmiModelDescription.LogCategories value) {
        this.logCategories = value;
    }

    /**
     * Obtient la valeur de la propriété defaultExperiment.
     * 
     * @return
     *     possible object is
     *     {@link FmiModelDescription.DefaultExperiment }
     *     
     */
    public FmiModelDescription.DefaultExperiment getDefaultExperiment() {
        return defaultExperiment;
    }

    /**
     * Définit la valeur de la propriété defaultExperiment.
     * 
     * @param value
     *     allowed object is
     *     {@link FmiModelDescription.DefaultExperiment }
     *     
     */
    public void setDefaultExperiment(FmiModelDescription.DefaultExperiment value) {
        this.defaultExperiment = value;
    }

    /**
     * Obtient la valeur de la propriété vendorAnnotations.
     * 
     * @return
     *     possible object is
     *     {@link Fmi2Annotation }
     *     
     */
    public Fmi2Annotation getVendorAnnotations() {
        return vendorAnnotations;
    }

    /**
     * Définit la valeur de la propriété vendorAnnotations.
     * 
     * @param value
     *     allowed object is
     *     {@link Fmi2Annotation }
     *     
     */
    public void setVendorAnnotations(Fmi2Annotation value) {
        this.vendorAnnotations = value;
    }

    /**
     * Obtient la valeur de la propriété modelVariables.
     * 
     * @return
     *     possible object is
     *     {@link FmiModelDescription.ModelVariables }
     *     
     */
    public FmiModelDescription.ModelVariables getModelVariables() {
        return modelVariables;
    }

    /**
     * Définit la valeur de la propriété modelVariables.
     * 
     * @param value
     *     allowed object is
     *     {@link FmiModelDescription.ModelVariables }
     *     
     */
    public void setModelVariables(FmiModelDescription.ModelVariables value) {
        this.modelVariables = value;
    }

    /**
     * Obtient la valeur de la propriété modelStructure.
     * 
     * @return
     *     possible object is
     *     {@link FmiModelDescription.ModelStructure }
     *     
     */
    public FmiModelDescription.ModelStructure getModelStructure() {
        return modelStructure;
    }

    /**
     * Définit la valeur de la propriété modelStructure.
     * 
     * @param value
     *     allowed object is
     *     {@link FmiModelDescription.ModelStructure }
     *     
     */
    public void setModelStructure(FmiModelDescription.ModelStructure value) {
        this.modelStructure = value;
    }

    /**
     * Obtient la valeur de la propriété fmiVersion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFmiVersion() {
        if (fmiVersion == null) {
            return "2.0";
        } else {
            return fmiVersion;
        }
    }

    /**
     * Définit la valeur de la propriété fmiVersion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFmiVersion(String value) {
        this.fmiVersion = value;
    }

    /**
     * Obtient la valeur de la propriété modelName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModelName() {
        return modelName;
    }

    /**
     * Définit la valeur de la propriété modelName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModelName(String value) {
        this.modelName = value;
    }

    /**
     * Obtient la valeur de la propriété guid.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGuid() {
        return guid;
    }

    /**
     * Définit la valeur de la propriété guid.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGuid(String value) {
        this.guid = value;
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
     * Obtient la valeur de la propriété author.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Définit la valeur de la propriété author.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthor(String value) {
        this.author = value;
    }

    /**
     * Obtient la valeur de la propriété version.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Définit la valeur de la propriété version.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Obtient la valeur de la propriété copyright.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCopyright() {
        return copyright;
    }

    /**
     * Définit la valeur de la propriété copyright.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCopyright(String value) {
        this.copyright = value;
    }

    /**
     * Obtient la valeur de la propriété license.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLicense() {
        return license;
    }

    /**
     * Définit la valeur de la propriété license.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLicense(String value) {
        this.license = value;
    }

    /**
     * Obtient la valeur de la propriété generationTool.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGenerationTool() {
        return generationTool;
    }

    /**
     * Définit la valeur de la propriété generationTool.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGenerationTool(String value) {
        this.generationTool = value;
    }

    /**
     * Obtient la valeur de la propriété generationDateAndTime.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getGenerationDateAndTime() {
        return generationDateAndTime;
    }

    /**
     * Définit la valeur de la propriété generationDateAndTime.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setGenerationDateAndTime(XMLGregorianCalendar value) {
        this.generationDateAndTime = value;
    }

    /**
     * Obtient la valeur de la propriété variableNamingConvention.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVariableNamingConvention() {
        if (variableNamingConvention == null) {
            return "flat";
        } else {
            return variableNamingConvention;
        }
    }

    /**
     * Définit la valeur de la propriété variableNamingConvention.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVariableNamingConvention(String value) {
        this.variableNamingConvention = value;
    }

    /**
     * Obtient la valeur de la propriété numberOfEventIndicators.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getNumberOfEventIndicators() {
        return numberOfEventIndicators;
    }

    /**
     * Définit la valeur de la propriété numberOfEventIndicators.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setNumberOfEventIndicators(Long value) {
        this.numberOfEventIndicators = value;
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
     *       &lt;sequence>
     *         &lt;element name="SourceFiles" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence maxOccurs="unbounded">
     *                   &lt;element name="File">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *       &lt;attribute name="modelIdentifier" use="required" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
     *       &lt;attribute name="needsExecutionTool" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
     *       &lt;attribute name="canHandleVariableCommunicationStepSize" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
     *       &lt;attribute name="canInterpolateInputs" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
     *       &lt;attribute name="maxOutputDerivativeOrder" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" default="0" />
     *       &lt;attribute name="canRunAsynchronuously" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
     *       &lt;attribute name="canBeInstantiatedOnlyOncePerProcess" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
     *       &lt;attribute name="canNotUseMemoryManagementFunctions" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
     *       &lt;attribute name="canGetAndSetFMUstate" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
     *       &lt;attribute name="canSerializeFMUstate" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
     *       &lt;attribute name="providesDirectionalDerivative" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "sourceFiles"
    })
    public static class CoSimulation {

        @XmlElement(name = "SourceFiles")
        protected FmiModelDescription.CoSimulation.SourceFiles sourceFiles;
        @XmlAttribute(name = "modelIdentifier", required = true)
        @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
        @XmlSchemaType(name = "normalizedString")
        protected String modelIdentifier;
        @XmlAttribute(name = "needsExecutionTool")
        protected Boolean needsExecutionTool;
        @XmlAttribute(name = "canHandleVariableCommunicationStepSize")
        protected Boolean canHandleVariableCommunicationStepSize;
        @XmlAttribute(name = "canInterpolateInputs")
        protected Boolean canInterpolateInputs;
        @XmlAttribute(name = "maxOutputDerivativeOrder")
        @XmlSchemaType(name = "unsignedInt")
        protected Long maxOutputDerivativeOrder;
        @XmlAttribute(name = "canRunAsynchronuously")
        protected Boolean canRunAsynchronuously;
        @XmlAttribute(name = "canBeInstantiatedOnlyOncePerProcess")
        protected Boolean canBeInstantiatedOnlyOncePerProcess;
        @XmlAttribute(name = "canNotUseMemoryManagementFunctions")
        protected Boolean canNotUseMemoryManagementFunctions;
        @XmlAttribute(name = "canGetAndSetFMUstate")
        protected Boolean canGetAndSetFMUstate;
        @XmlAttribute(name = "canSerializeFMUstate")
        protected Boolean canSerializeFMUstate;
        @XmlAttribute(name = "providesDirectionalDerivative")
        protected Boolean providesDirectionalDerivative;

        /**
         * Obtient la valeur de la propriété sourceFiles.
         * 
         * @return
         *     possible object is
         *     {@link FmiModelDescription.CoSimulation.SourceFiles }
         *     
         */
        public FmiModelDescription.CoSimulation.SourceFiles getSourceFiles() {
            return sourceFiles;
        }

        /**
         * Définit la valeur de la propriété sourceFiles.
         * 
         * @param value
         *     allowed object is
         *     {@link FmiModelDescription.CoSimulation.SourceFiles }
         *     
         */
        public void setSourceFiles(FmiModelDescription.CoSimulation.SourceFiles value) {
            this.sourceFiles = value;
        }

        /**
         * Obtient la valeur de la propriété modelIdentifier.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getModelIdentifier() {
            return modelIdentifier;
        }

        /**
         * Définit la valeur de la propriété modelIdentifier.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setModelIdentifier(String value) {
            this.modelIdentifier = value;
        }

        /**
         * Obtient la valeur de la propriété needsExecutionTool.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public boolean isNeedsExecutionTool() {
            if (needsExecutionTool == null) {
                return false;
            } else {
                return needsExecutionTool;
            }
        }

        /**
         * Définit la valeur de la propriété needsExecutionTool.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setNeedsExecutionTool(Boolean value) {
            this.needsExecutionTool = value;
        }

        /**
         * Obtient la valeur de la propriété canHandleVariableCommunicationStepSize.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public boolean isCanHandleVariableCommunicationStepSize() {
            if (canHandleVariableCommunicationStepSize == null) {
                return false;
            } else {
                return canHandleVariableCommunicationStepSize;
            }
        }

        /**
         * Définit la valeur de la propriété canHandleVariableCommunicationStepSize.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setCanHandleVariableCommunicationStepSize(Boolean value) {
            this.canHandleVariableCommunicationStepSize = value;
        }

        /**
         * Obtient la valeur de la propriété canInterpolateInputs.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public boolean isCanInterpolateInputs() {
            if (canInterpolateInputs == null) {
                return false;
            } else {
                return canInterpolateInputs;
            }
        }

        /**
         * Définit la valeur de la propriété canInterpolateInputs.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setCanInterpolateInputs(Boolean value) {
            this.canInterpolateInputs = value;
        }

        /**
         * Obtient la valeur de la propriété maxOutputDerivativeOrder.
         * 
         * @return
         *     possible object is
         *     {@link Long }
         *     
         */
        public long getMaxOutputDerivativeOrder() {
            if (maxOutputDerivativeOrder == null) {
                return  0L;
            } else {
                return maxOutputDerivativeOrder;
            }
        }

        /**
         * Définit la valeur de la propriété maxOutputDerivativeOrder.
         * 
         * @param value
         *     allowed object is
         *     {@link Long }
         *     
         */
        public void setMaxOutputDerivativeOrder(Long value) {
            this.maxOutputDerivativeOrder = value;
        }

        /**
         * Obtient la valeur de la propriété canRunAsynchronuously.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public boolean isCanRunAsynchronuously() {
            if (canRunAsynchronuously == null) {
                return false;
            } else {
                return canRunAsynchronuously;
            }
        }

        /**
         * Définit la valeur de la propriété canRunAsynchronuously.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setCanRunAsynchronuously(Boolean value) {
            this.canRunAsynchronuously = value;
        }

        /**
         * Obtient la valeur de la propriété canBeInstantiatedOnlyOncePerProcess.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public boolean isCanBeInstantiatedOnlyOncePerProcess() {
            if (canBeInstantiatedOnlyOncePerProcess == null) {
                return false;
            } else {
                return canBeInstantiatedOnlyOncePerProcess;
            }
        }

        /**
         * Définit la valeur de la propriété canBeInstantiatedOnlyOncePerProcess.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setCanBeInstantiatedOnlyOncePerProcess(Boolean value) {
            this.canBeInstantiatedOnlyOncePerProcess = value;
        }

        /**
         * Obtient la valeur de la propriété canNotUseMemoryManagementFunctions.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public boolean isCanNotUseMemoryManagementFunctions() {
            if (canNotUseMemoryManagementFunctions == null) {
                return false;
            } else {
                return canNotUseMemoryManagementFunctions;
            }
        }

        /**
         * Définit la valeur de la propriété canNotUseMemoryManagementFunctions.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setCanNotUseMemoryManagementFunctions(Boolean value) {
            this.canNotUseMemoryManagementFunctions = value;
        }

        /**
         * Obtient la valeur de la propriété canGetAndSetFMUstate.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public boolean isCanGetAndSetFMUstate() {
            if (canGetAndSetFMUstate == null) {
                return false;
            } else {
                return canGetAndSetFMUstate;
            }
        }

        /**
         * Définit la valeur de la propriété canGetAndSetFMUstate.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setCanGetAndSetFMUstate(Boolean value) {
            this.canGetAndSetFMUstate = value;
        }

        /**
         * Obtient la valeur de la propriété canSerializeFMUstate.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public boolean isCanSerializeFMUstate() {
            if (canSerializeFMUstate == null) {
                return false;
            } else {
                return canSerializeFMUstate;
            }
        }

        /**
         * Définit la valeur de la propriété canSerializeFMUstate.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setCanSerializeFMUstate(Boolean value) {
            this.canSerializeFMUstate = value;
        }

        /**
         * Obtient la valeur de la propriété providesDirectionalDerivative.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public boolean isProvidesDirectionalDerivative() {
            if (providesDirectionalDerivative == null) {
                return false;
            } else {
                return providesDirectionalDerivative;
            }
        }

        /**
         * Définit la valeur de la propriété providesDirectionalDerivative.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setProvidesDirectionalDerivative(Boolean value) {
            this.providesDirectionalDerivative = value;
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
         *         &lt;element name="File">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
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
        @XmlType(name = "", propOrder = {
            "file"
        })
        public static class SourceFiles {

            @XmlElement(name = "File", required = true)
            protected List<FmiModelDescription.CoSimulation.SourceFiles.File> file;

            /**
             * Gets the value of the file property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the file property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getFile().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link FmiModelDescription.CoSimulation.SourceFiles.File }
             * 
             * 
             */
            public List<FmiModelDescription.CoSimulation.SourceFiles.File> getFile() {
                if (file == null) {
                    file = new ArrayList<FmiModelDescription.CoSimulation.SourceFiles.File>();
                }
                return this.file;
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
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class File {

                @XmlAttribute(name = "name", required = true)
                @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
                @XmlSchemaType(name = "normalizedString")
                protected String name;

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
     *       &lt;attribute name="startTime" type="{http://www.w3.org/2001/XMLSchema}double" />
     *       &lt;attribute name="stopTime" type="{http://www.w3.org/2001/XMLSchema}double" />
     *       &lt;attribute name="tolerance" type="{http://www.w3.org/2001/XMLSchema}double" />
     *       &lt;attribute name="stepSize" type="{http://www.w3.org/2001/XMLSchema}double" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class DefaultExperiment {

        @XmlAttribute(name = "startTime")
        protected Double startTime;
        @XmlAttribute(name = "stopTime")
        protected Double stopTime;
        @XmlAttribute(name = "tolerance")
        protected Double tolerance;
        @XmlAttribute(name = "stepSize")
        protected Double stepSize;

        /**
         * Obtient la valeur de la propriété startTime.
         * 
         * @return
         *     possible object is
         *     {@link Double }
         *     
         */
        public Double getStartTime() {
            return startTime;
        }

        /**
         * Définit la valeur de la propriété startTime.
         * 
         * @param value
         *     allowed object is
         *     {@link Double }
         *     
         */
        public void setStartTime(Double value) {
            this.startTime = value;
        }

        /**
         * Obtient la valeur de la propriété stopTime.
         * 
         * @return
         *     possible object is
         *     {@link Double }
         *     
         */
        public Double getStopTime() {
            return stopTime;
        }

        /**
         * Définit la valeur de la propriété stopTime.
         * 
         * @param value
         *     allowed object is
         *     {@link Double }
         *     
         */
        public void setStopTime(Double value) {
            this.stopTime = value;
        }

        /**
         * Obtient la valeur de la propriété tolerance.
         * 
         * @return
         *     possible object is
         *     {@link Double }
         *     
         */
        public Double getTolerance() {
            return tolerance;
        }

        /**
         * Définit la valeur de la propriété tolerance.
         * 
         * @param value
         *     allowed object is
         *     {@link Double }
         *     
         */
        public void setTolerance(Double value) {
            this.tolerance = value;
        }

        /**
         * Obtient la valeur de la propriété stepSize.
         * 
         * @return
         *     possible object is
         *     {@link Double }
         *     
         */
        public Double getStepSize() {
            return stepSize;
        }

        /**
         * Définit la valeur de la propriété stepSize.
         * 
         * @param value
         *     allowed object is
         *     {@link Double }
         *     
         */
        public void setStepSize(Double value) {
            this.stepSize = value;
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
     *       &lt;sequence maxOccurs="unbounded">
     *         &lt;element name="Category">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
     *                 &lt;attribute name="description" type="{http://www.w3.org/2001/XMLSchema}string" />
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
    @XmlType(name = "", propOrder = {
        "category"
    })
    public static class LogCategories {

        @XmlElement(name = "Category", required = true)
        protected List<FmiModelDescription.LogCategories.Category> category;

        /**
         * Gets the value of the category property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the category property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCategory().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link FmiModelDescription.LogCategories.Category }
         * 
         * 
         */
        public List<FmiModelDescription.LogCategories.Category> getCategory() {
            if (category == null) {
                category = new ArrayList<FmiModelDescription.LogCategories.Category>();
            }
            return this.category;
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
        public static class Category {

            @XmlAttribute(name = "name", required = true)
            @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
            @XmlSchemaType(name = "normalizedString")
            protected String name;
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
     * List of capability flags that an FMI2 Model Exchange interface can provide
     * 
     * <p>Classe Java pour anonymous complex type.
     * 
     * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="SourceFiles" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence maxOccurs="unbounded">
     *                   &lt;element name="File">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *       &lt;attribute name="modelIdentifier" use="required" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
     *       &lt;attribute name="needsExecutionTool" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
     *       &lt;attribute name="completedIntegratorStepNotNeeded" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
     *       &lt;attribute name="canBeInstantiatedOnlyOncePerProcess" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
     *       &lt;attribute name="canNotUseMemoryManagementFunctions" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
     *       &lt;attribute name="canGetAndSetFMUstate" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
     *       &lt;attribute name="canSerializeFMUstate" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
     *       &lt;attribute name="providesDirectionalDerivative" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "sourceFiles"
    })
    public static class ModelExchange {

        @XmlElement(name = "SourceFiles")
        protected FmiModelDescription.ModelExchange.SourceFiles sourceFiles;
        @XmlAttribute(name = "modelIdentifier", required = true)
        @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
        @XmlSchemaType(name = "normalizedString")
        protected String modelIdentifier;
        @XmlAttribute(name = "needsExecutionTool")
        protected Boolean needsExecutionTool;
        @XmlAttribute(name = "completedIntegratorStepNotNeeded")
        protected Boolean completedIntegratorStepNotNeeded;
        @XmlAttribute(name = "canBeInstantiatedOnlyOncePerProcess")
        protected Boolean canBeInstantiatedOnlyOncePerProcess;
        @XmlAttribute(name = "canNotUseMemoryManagementFunctions")
        protected Boolean canNotUseMemoryManagementFunctions;
        @XmlAttribute(name = "canGetAndSetFMUstate")
        protected Boolean canGetAndSetFMUstate;
        @XmlAttribute(name = "canSerializeFMUstate")
        protected Boolean canSerializeFMUstate;
        @XmlAttribute(name = "providesDirectionalDerivative")
        protected Boolean providesDirectionalDerivative;

        /**
         * Obtient la valeur de la propriété sourceFiles.
         * 
         * @return
         *     possible object is
         *     {@link FmiModelDescription.ModelExchange.SourceFiles }
         *     
         */
        public FmiModelDescription.ModelExchange.SourceFiles getSourceFiles() {
            return sourceFiles;
        }

        /**
         * Définit la valeur de la propriété sourceFiles.
         * 
         * @param value
         *     allowed object is
         *     {@link FmiModelDescription.ModelExchange.SourceFiles }
         *     
         */
        public void setSourceFiles(FmiModelDescription.ModelExchange.SourceFiles value) {
            this.sourceFiles = value;
        }

        /**
         * Obtient la valeur de la propriété modelIdentifier.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getModelIdentifier() {
            return modelIdentifier;
        }

        /**
         * Définit la valeur de la propriété modelIdentifier.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setModelIdentifier(String value) {
            this.modelIdentifier = value;
        }

        /**
         * Obtient la valeur de la propriété needsExecutionTool.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public boolean isNeedsExecutionTool() {
            if (needsExecutionTool == null) {
                return false;
            } else {
                return needsExecutionTool;
            }
        }

        /**
         * Définit la valeur de la propriété needsExecutionTool.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setNeedsExecutionTool(Boolean value) {
            this.needsExecutionTool = value;
        }

        /**
         * Obtient la valeur de la propriété completedIntegratorStepNotNeeded.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public boolean isCompletedIntegratorStepNotNeeded() {
            if (completedIntegratorStepNotNeeded == null) {
                return false;
            } else {
                return completedIntegratorStepNotNeeded;
            }
        }

        /**
         * Définit la valeur de la propriété completedIntegratorStepNotNeeded.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setCompletedIntegratorStepNotNeeded(Boolean value) {
            this.completedIntegratorStepNotNeeded = value;
        }

        /**
         * Obtient la valeur de la propriété canBeInstantiatedOnlyOncePerProcess.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public boolean isCanBeInstantiatedOnlyOncePerProcess() {
            if (canBeInstantiatedOnlyOncePerProcess == null) {
                return false;
            } else {
                return canBeInstantiatedOnlyOncePerProcess;
            }
        }

        /**
         * Définit la valeur de la propriété canBeInstantiatedOnlyOncePerProcess.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setCanBeInstantiatedOnlyOncePerProcess(Boolean value) {
            this.canBeInstantiatedOnlyOncePerProcess = value;
        }

        /**
         * Obtient la valeur de la propriété canNotUseMemoryManagementFunctions.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public boolean isCanNotUseMemoryManagementFunctions() {
            if (canNotUseMemoryManagementFunctions == null) {
                return false;
            } else {
                return canNotUseMemoryManagementFunctions;
            }
        }

        /**
         * Définit la valeur de la propriété canNotUseMemoryManagementFunctions.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setCanNotUseMemoryManagementFunctions(Boolean value) {
            this.canNotUseMemoryManagementFunctions = value;
        }

        /**
         * Obtient la valeur de la propriété canGetAndSetFMUstate.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public boolean isCanGetAndSetFMUstate() {
            if (canGetAndSetFMUstate == null) {
                return false;
            } else {
                return canGetAndSetFMUstate;
            }
        }

        /**
         * Définit la valeur de la propriété canGetAndSetFMUstate.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setCanGetAndSetFMUstate(Boolean value) {
            this.canGetAndSetFMUstate = value;
        }

        /**
         * Obtient la valeur de la propriété canSerializeFMUstate.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public boolean isCanSerializeFMUstate() {
            if (canSerializeFMUstate == null) {
                return false;
            } else {
                return canSerializeFMUstate;
            }
        }

        /**
         * Définit la valeur de la propriété canSerializeFMUstate.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setCanSerializeFMUstate(Boolean value) {
            this.canSerializeFMUstate = value;
        }

        /**
         * Obtient la valeur de la propriété providesDirectionalDerivative.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public boolean isProvidesDirectionalDerivative() {
            if (providesDirectionalDerivative == null) {
                return false;
            } else {
                return providesDirectionalDerivative;
            }
        }

        /**
         * Définit la valeur de la propriété providesDirectionalDerivative.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setProvidesDirectionalDerivative(Boolean value) {
            this.providesDirectionalDerivative = value;
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
         *         &lt;element name="File">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
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
        @XmlType(name = "", propOrder = {
            "file"
        })
        public static class SourceFiles {

            @XmlElement(name = "File", required = true)
            protected List<FmiModelDescription.ModelExchange.SourceFiles.File> file;

            /**
             * Gets the value of the file property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the file property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getFile().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link FmiModelDescription.ModelExchange.SourceFiles.File }
             * 
             * 
             */
            public List<FmiModelDescription.ModelExchange.SourceFiles.File> getFile() {
                if (file == null) {
                    file = new ArrayList<FmiModelDescription.ModelExchange.SourceFiles.File>();
                }
                return this.file;
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
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class File {

                @XmlAttribute(name = "name", required = true)
                @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
                @XmlSchemaType(name = "normalizedString")
                protected String name;

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
     *       &lt;sequence>
     *         &lt;element name="Outputs" type="{}fmi2VariableDependency" minOccurs="0"/>
     *         &lt;element name="Derivatives" type="{}fmi2VariableDependency" minOccurs="0"/>
     *         &lt;element name="InitialUnknowns" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence maxOccurs="unbounded">
     *                   &lt;element name="Unknown">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;attribute name="index" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" />
     *                           &lt;attribute name="dependencies">
     *                             &lt;simpleType>
     *                               &lt;list itemType="{http://www.w3.org/2001/XMLSchema}unsignedInt" />
     *                             &lt;/simpleType>
     *                           &lt;/attribute>
     *                           &lt;attribute name="dependenciesKind">
     *                             &lt;simpleType>
     *                               &lt;list>
     *                                 &lt;simpleType>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}normalizedString">
     *                                     &lt;enumeration value="dependent"/>
     *                                     &lt;enumeration value="constant"/>
     *                                     &lt;enumeration value="fixed"/>
     *                                     &lt;enumeration value="tunable"/>
     *                                     &lt;enumeration value="discrete"/>
     *                                   &lt;/restriction>
     *                                 &lt;/simpleType>
     *                               &lt;/list>
     *                             &lt;/simpleType>
     *                           &lt;/attribute>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
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
    @XmlType(name = "", propOrder = {
        "outputs",
        "derivatives",
        "initialUnknowns"
    })
    public static class ModelStructure {

        @XmlElement(name = "Outputs")
        protected Fmi2VariableDependency outputs;
        @XmlElement(name = "Derivatives")
        protected Fmi2VariableDependency derivatives;
        @XmlElement(name = "InitialUnknowns")
        protected FmiModelDescription.ModelStructure.InitialUnknowns initialUnknowns;

        /**
         * Obtient la valeur de la propriété outputs.
         * 
         * @return
         *     possible object is
         *     {@link Fmi2VariableDependency }
         *     
         */
        public Fmi2VariableDependency getOutputs() {
            return outputs;
        }

        /**
         * Définit la valeur de la propriété outputs.
         * 
         * @param value
         *     allowed object is
         *     {@link Fmi2VariableDependency }
         *     
         */
        public void setOutputs(Fmi2VariableDependency value) {
            this.outputs = value;
        }

        /**
         * Obtient la valeur de la propriété derivatives.
         * 
         * @return
         *     possible object is
         *     {@link Fmi2VariableDependency }
         *     
         */
        public Fmi2VariableDependency getDerivatives() {
            return derivatives;
        }

        /**
         * Définit la valeur de la propriété derivatives.
         * 
         * @param value
         *     allowed object is
         *     {@link Fmi2VariableDependency }
         *     
         */
        public void setDerivatives(Fmi2VariableDependency value) {
            this.derivatives = value;
        }

        /**
         * Obtient la valeur de la propriété initialUnknowns.
         * 
         * @return
         *     possible object is
         *     {@link FmiModelDescription.ModelStructure.InitialUnknowns }
         *     
         */
        public FmiModelDescription.ModelStructure.InitialUnknowns getInitialUnknowns() {
            return initialUnknowns;
        }

        /**
         * Définit la valeur de la propriété initialUnknowns.
         * 
         * @param value
         *     allowed object is
         *     {@link FmiModelDescription.ModelStructure.InitialUnknowns }
         *     
         */
        public void setInitialUnknowns(FmiModelDescription.ModelStructure.InitialUnknowns value) {
            this.initialUnknowns = value;
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
        @XmlType(name = "", propOrder = {
            "unknown"
        })
        public static class InitialUnknowns {

            @XmlElement(name = "Unknown", required = true)
            protected List<FmiModelDescription.ModelStructure.InitialUnknowns.Unknown> unknown;

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
             * {@link FmiModelDescription.ModelStructure.InitialUnknowns.Unknown }
             * 
             * 
             */
            public List<FmiModelDescription.ModelStructure.InitialUnknowns.Unknown> getUnknown() {
                if (unknown == null) {
                    unknown = new ArrayList<FmiModelDescription.ModelStructure.InitialUnknowns.Unknown>();
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
     *         &lt;element name="ScalarVariable" type="{}fmi2ScalarVariable"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "scalarVariable"
    })
    public static class ModelVariables {

        @XmlElement(name = "ScalarVariable", required = true)
        protected List<Fmi2ScalarVariable> scalarVariable;

        /**
         * Gets the value of the scalarVariable property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the scalarVariable property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getScalarVariable().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Fmi2ScalarVariable }
         * 
         * 
         */
        public List<Fmi2ScalarVariable> getScalarVariable() {
            if (scalarVariable == null) {
                scalarVariable = new ArrayList<Fmi2ScalarVariable>();
            }
            return this.scalarVariable;
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
     *       &lt;sequence maxOccurs="unbounded">
     *         &lt;element name="SimpleType" type="{}fmi2SimpleType"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "simpleType"
    })
    public static class TypeDefinitions {

        @XmlElement(name = "SimpleType", required = true)
        protected List<Fmi2SimpleType> simpleType;

        /**
         * Gets the value of the simpleType property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the simpleType property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getSimpleType().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Fmi2SimpleType }
         * 
         * 
         */
        public List<Fmi2SimpleType> getSimpleType() {
            if (simpleType == null) {
                simpleType = new ArrayList<Fmi2SimpleType>();
            }
            return this.simpleType;
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
     *       &lt;sequence maxOccurs="unbounded">
     *         &lt;element name="Unit" type="{}fmi2Unit"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "unit"
    })
    public static class UnitDefinitions {

        @XmlElement(name = "Unit", required = true)
        protected List<Fmi2Unit> unit;

        /**
         * Gets the value of the unit property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the unit property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getUnit().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Fmi2Unit }
         * 
         * 
         */
        public List<Fmi2Unit> getUnit() {
            if (unit == null) {
                unit = new ArrayList<Fmi2Unit>();
            }
            return this.unit;
        }

    }

}
