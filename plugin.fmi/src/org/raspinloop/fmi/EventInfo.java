/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.raspinloop.fmi;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-03-01")
public class EventInfo implements org.apache.thrift.TBase<EventInfo, EventInfo._Fields>, java.io.Serializable, Cloneable, Comparable<EventInfo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("EventInfo");

  private static final org.apache.thrift.protocol.TField NEW_DISCRETE_STATES_NEEDED_FIELD_DESC = new org.apache.thrift.protocol.TField("newDiscreteStatesNeeded", org.apache.thrift.protocol.TType.BOOL, (short)1);
  private static final org.apache.thrift.protocol.TField TERMINATE_SIMULATION_FIELD_DESC = new org.apache.thrift.protocol.TField("terminateSimulation", org.apache.thrift.protocol.TType.BOOL, (short)2);
  private static final org.apache.thrift.protocol.TField NOMINALS_OF_CONTINUOUS_STATES_CHANGED_FIELD_DESC = new org.apache.thrift.protocol.TField("nominalsOfContinuousStatesChanged", org.apache.thrift.protocol.TType.BOOL, (short)3);
  private static final org.apache.thrift.protocol.TField VALUES_OF_CONTINUOUS_STATES_CHANGED_FIELD_DESC = new org.apache.thrift.protocol.TField("valuesOfContinuousStatesChanged", org.apache.thrift.protocol.TType.BOOL, (short)4);
  private static final org.apache.thrift.protocol.TField NEXT_EVENT_TIME_DEFINED_FIELD_DESC = new org.apache.thrift.protocol.TField("nextEventTimeDefined", org.apache.thrift.protocol.TType.BOOL, (short)5);
  private static final org.apache.thrift.protocol.TField NEXT_EVENT_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("nextEventTime", org.apache.thrift.protocol.TType.DOUBLE, (short)6);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new EventInfoStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new EventInfoTupleSchemeFactory();

  public boolean newDiscreteStatesNeeded; // required
  public boolean terminateSimulation; // required
  public boolean nominalsOfContinuousStatesChanged; // required
  public boolean valuesOfContinuousStatesChanged; // required
  public boolean nextEventTimeDefined; // required
  public double nextEventTime; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    NEW_DISCRETE_STATES_NEEDED((short)1, "newDiscreteStatesNeeded"),
    TERMINATE_SIMULATION((short)2, "terminateSimulation"),
    NOMINALS_OF_CONTINUOUS_STATES_CHANGED((short)3, "nominalsOfContinuousStatesChanged"),
    VALUES_OF_CONTINUOUS_STATES_CHANGED((short)4, "valuesOfContinuousStatesChanged"),
    NEXT_EVENT_TIME_DEFINED((short)5, "nextEventTimeDefined"),
    NEXT_EVENT_TIME((short)6, "nextEventTime");

    private static final java.util.Map<java.lang.String, _Fields> byName = new java.util.HashMap<java.lang.String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // NEW_DISCRETE_STATES_NEEDED
          return NEW_DISCRETE_STATES_NEEDED;
        case 2: // TERMINATE_SIMULATION
          return TERMINATE_SIMULATION;
        case 3: // NOMINALS_OF_CONTINUOUS_STATES_CHANGED
          return NOMINALS_OF_CONTINUOUS_STATES_CHANGED;
        case 4: // VALUES_OF_CONTINUOUS_STATES_CHANGED
          return VALUES_OF_CONTINUOUS_STATES_CHANGED;
        case 5: // NEXT_EVENT_TIME_DEFINED
          return NEXT_EVENT_TIME_DEFINED;
        case 6: // NEXT_EVENT_TIME
          return NEXT_EVENT_TIME;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(java.lang.String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final java.lang.String _fieldName;

    _Fields(short thriftId, java.lang.String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public java.lang.String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __NEWDISCRETESTATESNEEDED_ISSET_ID = 0;
  private static final int __TERMINATESIMULATION_ISSET_ID = 1;
  private static final int __NOMINALSOFCONTINUOUSSTATESCHANGED_ISSET_ID = 2;
  private static final int __VALUESOFCONTINUOUSSTATESCHANGED_ISSET_ID = 3;
  private static final int __NEXTEVENTTIMEDEFINED_ISSET_ID = 4;
  private static final int __NEXTEVENTTIME_ISSET_ID = 5;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.NEW_DISCRETE_STATES_NEEDED, new org.apache.thrift.meta_data.FieldMetaData("newDiscreteStatesNeeded", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.TERMINATE_SIMULATION, new org.apache.thrift.meta_data.FieldMetaData("terminateSimulation", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.NOMINALS_OF_CONTINUOUS_STATES_CHANGED, new org.apache.thrift.meta_data.FieldMetaData("nominalsOfContinuousStatesChanged", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.VALUES_OF_CONTINUOUS_STATES_CHANGED, new org.apache.thrift.meta_data.FieldMetaData("valuesOfContinuousStatesChanged", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.NEXT_EVENT_TIME_DEFINED, new org.apache.thrift.meta_data.FieldMetaData("nextEventTimeDefined", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.NEXT_EVENT_TIME, new org.apache.thrift.meta_data.FieldMetaData("nextEventTime", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(EventInfo.class, metaDataMap);
  }

  public EventInfo() {
  }

  public EventInfo(
    boolean newDiscreteStatesNeeded,
    boolean terminateSimulation,
    boolean nominalsOfContinuousStatesChanged,
    boolean valuesOfContinuousStatesChanged,
    boolean nextEventTimeDefined,
    double nextEventTime)
  {
    this();
    this.newDiscreteStatesNeeded = newDiscreteStatesNeeded;
    setNewDiscreteStatesNeededIsSet(true);
    this.terminateSimulation = terminateSimulation;
    setTerminateSimulationIsSet(true);
    this.nominalsOfContinuousStatesChanged = nominalsOfContinuousStatesChanged;
    setNominalsOfContinuousStatesChangedIsSet(true);
    this.valuesOfContinuousStatesChanged = valuesOfContinuousStatesChanged;
    setValuesOfContinuousStatesChangedIsSet(true);
    this.nextEventTimeDefined = nextEventTimeDefined;
    setNextEventTimeDefinedIsSet(true);
    this.nextEventTime = nextEventTime;
    setNextEventTimeIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public EventInfo(EventInfo other) {
    __isset_bitfield = other.__isset_bitfield;
    this.newDiscreteStatesNeeded = other.newDiscreteStatesNeeded;
    this.terminateSimulation = other.terminateSimulation;
    this.nominalsOfContinuousStatesChanged = other.nominalsOfContinuousStatesChanged;
    this.valuesOfContinuousStatesChanged = other.valuesOfContinuousStatesChanged;
    this.nextEventTimeDefined = other.nextEventTimeDefined;
    this.nextEventTime = other.nextEventTime;
  }

  public EventInfo deepCopy() {
    return new EventInfo(this);
  }

  @Override
  public void clear() {
    setNewDiscreteStatesNeededIsSet(false);
    this.newDiscreteStatesNeeded = false;
    setTerminateSimulationIsSet(false);
    this.terminateSimulation = false;
    setNominalsOfContinuousStatesChangedIsSet(false);
    this.nominalsOfContinuousStatesChanged = false;
    setValuesOfContinuousStatesChangedIsSet(false);
    this.valuesOfContinuousStatesChanged = false;
    setNextEventTimeDefinedIsSet(false);
    this.nextEventTimeDefined = false;
    setNextEventTimeIsSet(false);
    this.nextEventTime = 0.0;
  }

  public boolean isNewDiscreteStatesNeeded() {
    return this.newDiscreteStatesNeeded;
  }

  public EventInfo setNewDiscreteStatesNeeded(boolean newDiscreteStatesNeeded) {
    this.newDiscreteStatesNeeded = newDiscreteStatesNeeded;
    setNewDiscreteStatesNeededIsSet(true);
    return this;
  }

  public void unsetNewDiscreteStatesNeeded() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __NEWDISCRETESTATESNEEDED_ISSET_ID);
  }

  /** Returns true if field newDiscreteStatesNeeded is set (has been assigned a value) and false otherwise */
  public boolean isSetNewDiscreteStatesNeeded() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __NEWDISCRETESTATESNEEDED_ISSET_ID);
  }

  public void setNewDiscreteStatesNeededIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __NEWDISCRETESTATESNEEDED_ISSET_ID, value);
  }

  public boolean isTerminateSimulation() {
    return this.terminateSimulation;
  }

  public EventInfo setTerminateSimulation(boolean terminateSimulation) {
    this.terminateSimulation = terminateSimulation;
    setTerminateSimulationIsSet(true);
    return this;
  }

  public void unsetTerminateSimulation() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __TERMINATESIMULATION_ISSET_ID);
  }

  /** Returns true if field terminateSimulation is set (has been assigned a value) and false otherwise */
  public boolean isSetTerminateSimulation() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __TERMINATESIMULATION_ISSET_ID);
  }

  public void setTerminateSimulationIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __TERMINATESIMULATION_ISSET_ID, value);
  }

  public boolean isNominalsOfContinuousStatesChanged() {
    return this.nominalsOfContinuousStatesChanged;
  }

  public EventInfo setNominalsOfContinuousStatesChanged(boolean nominalsOfContinuousStatesChanged) {
    this.nominalsOfContinuousStatesChanged = nominalsOfContinuousStatesChanged;
    setNominalsOfContinuousStatesChangedIsSet(true);
    return this;
  }

  public void unsetNominalsOfContinuousStatesChanged() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __NOMINALSOFCONTINUOUSSTATESCHANGED_ISSET_ID);
  }

  /** Returns true if field nominalsOfContinuousStatesChanged is set (has been assigned a value) and false otherwise */
  public boolean isSetNominalsOfContinuousStatesChanged() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __NOMINALSOFCONTINUOUSSTATESCHANGED_ISSET_ID);
  }

  public void setNominalsOfContinuousStatesChangedIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __NOMINALSOFCONTINUOUSSTATESCHANGED_ISSET_ID, value);
  }

  public boolean isValuesOfContinuousStatesChanged() {
    return this.valuesOfContinuousStatesChanged;
  }

  public EventInfo setValuesOfContinuousStatesChanged(boolean valuesOfContinuousStatesChanged) {
    this.valuesOfContinuousStatesChanged = valuesOfContinuousStatesChanged;
    setValuesOfContinuousStatesChangedIsSet(true);
    return this;
  }

  public void unsetValuesOfContinuousStatesChanged() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __VALUESOFCONTINUOUSSTATESCHANGED_ISSET_ID);
  }

  /** Returns true if field valuesOfContinuousStatesChanged is set (has been assigned a value) and false otherwise */
  public boolean isSetValuesOfContinuousStatesChanged() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __VALUESOFCONTINUOUSSTATESCHANGED_ISSET_ID);
  }

  public void setValuesOfContinuousStatesChangedIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __VALUESOFCONTINUOUSSTATESCHANGED_ISSET_ID, value);
  }

  public boolean isNextEventTimeDefined() {
    return this.nextEventTimeDefined;
  }

  public EventInfo setNextEventTimeDefined(boolean nextEventTimeDefined) {
    this.nextEventTimeDefined = nextEventTimeDefined;
    setNextEventTimeDefinedIsSet(true);
    return this;
  }

  public void unsetNextEventTimeDefined() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __NEXTEVENTTIMEDEFINED_ISSET_ID);
  }

  /** Returns true if field nextEventTimeDefined is set (has been assigned a value) and false otherwise */
  public boolean isSetNextEventTimeDefined() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __NEXTEVENTTIMEDEFINED_ISSET_ID);
  }

  public void setNextEventTimeDefinedIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __NEXTEVENTTIMEDEFINED_ISSET_ID, value);
  }

  public double getNextEventTime() {
    return this.nextEventTime;
  }

  public EventInfo setNextEventTime(double nextEventTime) {
    this.nextEventTime = nextEventTime;
    setNextEventTimeIsSet(true);
    return this;
  }

  public void unsetNextEventTime() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __NEXTEVENTTIME_ISSET_ID);
  }

  /** Returns true if field nextEventTime is set (has been assigned a value) and false otherwise */
  public boolean isSetNextEventTime() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __NEXTEVENTTIME_ISSET_ID);
  }

  public void setNextEventTimeIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __NEXTEVENTTIME_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case NEW_DISCRETE_STATES_NEEDED:
      if (value == null) {
        unsetNewDiscreteStatesNeeded();
      } else {
        setNewDiscreteStatesNeeded((java.lang.Boolean)value);
      }
      break;

    case TERMINATE_SIMULATION:
      if (value == null) {
        unsetTerminateSimulation();
      } else {
        setTerminateSimulation((java.lang.Boolean)value);
      }
      break;

    case NOMINALS_OF_CONTINUOUS_STATES_CHANGED:
      if (value == null) {
        unsetNominalsOfContinuousStatesChanged();
      } else {
        setNominalsOfContinuousStatesChanged((java.lang.Boolean)value);
      }
      break;

    case VALUES_OF_CONTINUOUS_STATES_CHANGED:
      if (value == null) {
        unsetValuesOfContinuousStatesChanged();
      } else {
        setValuesOfContinuousStatesChanged((java.lang.Boolean)value);
      }
      break;

    case NEXT_EVENT_TIME_DEFINED:
      if (value == null) {
        unsetNextEventTimeDefined();
      } else {
        setNextEventTimeDefined((java.lang.Boolean)value);
      }
      break;

    case NEXT_EVENT_TIME:
      if (value == null) {
        unsetNextEventTime();
      } else {
        setNextEventTime((java.lang.Double)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case NEW_DISCRETE_STATES_NEEDED:
      return isNewDiscreteStatesNeeded();

    case TERMINATE_SIMULATION:
      return isTerminateSimulation();

    case NOMINALS_OF_CONTINUOUS_STATES_CHANGED:
      return isNominalsOfContinuousStatesChanged();

    case VALUES_OF_CONTINUOUS_STATES_CHANGED:
      return isValuesOfContinuousStatesChanged();

    case NEXT_EVENT_TIME_DEFINED:
      return isNextEventTimeDefined();

    case NEXT_EVENT_TIME:
      return getNextEventTime();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case NEW_DISCRETE_STATES_NEEDED:
      return isSetNewDiscreteStatesNeeded();
    case TERMINATE_SIMULATION:
      return isSetTerminateSimulation();
    case NOMINALS_OF_CONTINUOUS_STATES_CHANGED:
      return isSetNominalsOfContinuousStatesChanged();
    case VALUES_OF_CONTINUOUS_STATES_CHANGED:
      return isSetValuesOfContinuousStatesChanged();
    case NEXT_EVENT_TIME_DEFINED:
      return isSetNextEventTimeDefined();
    case NEXT_EVENT_TIME:
      return isSetNextEventTime();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof EventInfo)
      return this.equals((EventInfo)that);
    return false;
  }

  public boolean equals(EventInfo that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_newDiscreteStatesNeeded = true;
    boolean that_present_newDiscreteStatesNeeded = true;
    if (this_present_newDiscreteStatesNeeded || that_present_newDiscreteStatesNeeded) {
      if (!(this_present_newDiscreteStatesNeeded && that_present_newDiscreteStatesNeeded))
        return false;
      if (this.newDiscreteStatesNeeded != that.newDiscreteStatesNeeded)
        return false;
    }

    boolean this_present_terminateSimulation = true;
    boolean that_present_terminateSimulation = true;
    if (this_present_terminateSimulation || that_present_terminateSimulation) {
      if (!(this_present_terminateSimulation && that_present_terminateSimulation))
        return false;
      if (this.terminateSimulation != that.terminateSimulation)
        return false;
    }

    boolean this_present_nominalsOfContinuousStatesChanged = true;
    boolean that_present_nominalsOfContinuousStatesChanged = true;
    if (this_present_nominalsOfContinuousStatesChanged || that_present_nominalsOfContinuousStatesChanged) {
      if (!(this_present_nominalsOfContinuousStatesChanged && that_present_nominalsOfContinuousStatesChanged))
        return false;
      if (this.nominalsOfContinuousStatesChanged != that.nominalsOfContinuousStatesChanged)
        return false;
    }

    boolean this_present_valuesOfContinuousStatesChanged = true;
    boolean that_present_valuesOfContinuousStatesChanged = true;
    if (this_present_valuesOfContinuousStatesChanged || that_present_valuesOfContinuousStatesChanged) {
      if (!(this_present_valuesOfContinuousStatesChanged && that_present_valuesOfContinuousStatesChanged))
        return false;
      if (this.valuesOfContinuousStatesChanged != that.valuesOfContinuousStatesChanged)
        return false;
    }

    boolean this_present_nextEventTimeDefined = true;
    boolean that_present_nextEventTimeDefined = true;
    if (this_present_nextEventTimeDefined || that_present_nextEventTimeDefined) {
      if (!(this_present_nextEventTimeDefined && that_present_nextEventTimeDefined))
        return false;
      if (this.nextEventTimeDefined != that.nextEventTimeDefined)
        return false;
    }

    boolean this_present_nextEventTime = true;
    boolean that_present_nextEventTime = true;
    if (this_present_nextEventTime || that_present_nextEventTime) {
      if (!(this_present_nextEventTime && that_present_nextEventTime))
        return false;
      if (this.nextEventTime != that.nextEventTime)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((newDiscreteStatesNeeded) ? 131071 : 524287);

    hashCode = hashCode * 8191 + ((terminateSimulation) ? 131071 : 524287);

    hashCode = hashCode * 8191 + ((nominalsOfContinuousStatesChanged) ? 131071 : 524287);

    hashCode = hashCode * 8191 + ((valuesOfContinuousStatesChanged) ? 131071 : 524287);

    hashCode = hashCode * 8191 + ((nextEventTimeDefined) ? 131071 : 524287);

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(nextEventTime);

    return hashCode;
  }

  @Override
  public int compareTo(EventInfo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetNewDiscreteStatesNeeded()).compareTo(other.isSetNewDiscreteStatesNeeded());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetNewDiscreteStatesNeeded()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.newDiscreteStatesNeeded, other.newDiscreteStatesNeeded);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetTerminateSimulation()).compareTo(other.isSetTerminateSimulation());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTerminateSimulation()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.terminateSimulation, other.terminateSimulation);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetNominalsOfContinuousStatesChanged()).compareTo(other.isSetNominalsOfContinuousStatesChanged());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetNominalsOfContinuousStatesChanged()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.nominalsOfContinuousStatesChanged, other.nominalsOfContinuousStatesChanged);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetValuesOfContinuousStatesChanged()).compareTo(other.isSetValuesOfContinuousStatesChanged());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetValuesOfContinuousStatesChanged()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.valuesOfContinuousStatesChanged, other.valuesOfContinuousStatesChanged);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetNextEventTimeDefined()).compareTo(other.isSetNextEventTimeDefined());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetNextEventTimeDefined()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.nextEventTimeDefined, other.nextEventTimeDefined);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetNextEventTime()).compareTo(other.isSetNextEventTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetNextEventTime()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.nextEventTime, other.nextEventTime);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public java.lang.String toString() {
    java.lang.StringBuilder sb = new java.lang.StringBuilder("EventInfo(");
    boolean first = true;

    sb.append("newDiscreteStatesNeeded:");
    sb.append(this.newDiscreteStatesNeeded);
    first = false;
    if (!first) sb.append(", ");
    sb.append("terminateSimulation:");
    sb.append(this.terminateSimulation);
    first = false;
    if (!first) sb.append(", ");
    sb.append("nominalsOfContinuousStatesChanged:");
    sb.append(this.nominalsOfContinuousStatesChanged);
    first = false;
    if (!first) sb.append(", ");
    sb.append("valuesOfContinuousStatesChanged:");
    sb.append(this.valuesOfContinuousStatesChanged);
    first = false;
    if (!first) sb.append(", ");
    sb.append("nextEventTimeDefined:");
    sb.append(this.nextEventTimeDefined);
    first = false;
    if (!first) sb.append(", ");
    sb.append("nextEventTime:");
    sb.append(this.nextEventTime);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class EventInfoStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public EventInfoStandardScheme getScheme() {
      return new EventInfoStandardScheme();
    }
  }

  private static class EventInfoStandardScheme extends org.apache.thrift.scheme.StandardScheme<EventInfo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, EventInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // NEW_DISCRETE_STATES_NEEDED
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.newDiscreteStatesNeeded = iprot.readBool();
              struct.setNewDiscreteStatesNeededIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // TERMINATE_SIMULATION
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.terminateSimulation = iprot.readBool();
              struct.setTerminateSimulationIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // NOMINALS_OF_CONTINUOUS_STATES_CHANGED
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.nominalsOfContinuousStatesChanged = iprot.readBool();
              struct.setNominalsOfContinuousStatesChangedIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // VALUES_OF_CONTINUOUS_STATES_CHANGED
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.valuesOfContinuousStatesChanged = iprot.readBool();
              struct.setValuesOfContinuousStatesChangedIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // NEXT_EVENT_TIME_DEFINED
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.nextEventTimeDefined = iprot.readBool();
              struct.setNextEventTimeDefinedIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // NEXT_EVENT_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.nextEventTime = iprot.readDouble();
              struct.setNextEventTimeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, EventInfo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(NEW_DISCRETE_STATES_NEEDED_FIELD_DESC);
      oprot.writeBool(struct.newDiscreteStatesNeeded);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(TERMINATE_SIMULATION_FIELD_DESC);
      oprot.writeBool(struct.terminateSimulation);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(NOMINALS_OF_CONTINUOUS_STATES_CHANGED_FIELD_DESC);
      oprot.writeBool(struct.nominalsOfContinuousStatesChanged);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(VALUES_OF_CONTINUOUS_STATES_CHANGED_FIELD_DESC);
      oprot.writeBool(struct.valuesOfContinuousStatesChanged);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(NEXT_EVENT_TIME_DEFINED_FIELD_DESC);
      oprot.writeBool(struct.nextEventTimeDefined);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(NEXT_EVENT_TIME_FIELD_DESC);
      oprot.writeDouble(struct.nextEventTime);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class EventInfoTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public EventInfoTupleScheme getScheme() {
      return new EventInfoTupleScheme();
    }
  }

  private static class EventInfoTupleScheme extends org.apache.thrift.scheme.TupleScheme<EventInfo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, EventInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetNewDiscreteStatesNeeded()) {
        optionals.set(0);
      }
      if (struct.isSetTerminateSimulation()) {
        optionals.set(1);
      }
      if (struct.isSetNominalsOfContinuousStatesChanged()) {
        optionals.set(2);
      }
      if (struct.isSetValuesOfContinuousStatesChanged()) {
        optionals.set(3);
      }
      if (struct.isSetNextEventTimeDefined()) {
        optionals.set(4);
      }
      if (struct.isSetNextEventTime()) {
        optionals.set(5);
      }
      oprot.writeBitSet(optionals, 6);
      if (struct.isSetNewDiscreteStatesNeeded()) {
        oprot.writeBool(struct.newDiscreteStatesNeeded);
      }
      if (struct.isSetTerminateSimulation()) {
        oprot.writeBool(struct.terminateSimulation);
      }
      if (struct.isSetNominalsOfContinuousStatesChanged()) {
        oprot.writeBool(struct.nominalsOfContinuousStatesChanged);
      }
      if (struct.isSetValuesOfContinuousStatesChanged()) {
        oprot.writeBool(struct.valuesOfContinuousStatesChanged);
      }
      if (struct.isSetNextEventTimeDefined()) {
        oprot.writeBool(struct.nextEventTimeDefined);
      }
      if (struct.isSetNextEventTime()) {
        oprot.writeDouble(struct.nextEventTime);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, EventInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(6);
      if (incoming.get(0)) {
        struct.newDiscreteStatesNeeded = iprot.readBool();
        struct.setNewDiscreteStatesNeededIsSet(true);
      }
      if (incoming.get(1)) {
        struct.terminateSimulation = iprot.readBool();
        struct.setTerminateSimulationIsSet(true);
      }
      if (incoming.get(2)) {
        struct.nominalsOfContinuousStatesChanged = iprot.readBool();
        struct.setNominalsOfContinuousStatesChangedIsSet(true);
      }
      if (incoming.get(3)) {
        struct.valuesOfContinuousStatesChanged = iprot.readBool();
        struct.setValuesOfContinuousStatesChangedIsSet(true);
      }
      if (incoming.get(4)) {
        struct.nextEventTimeDefined = iprot.readBool();
        struct.setNextEventTimeDefinedIsSet(true);
      }
      if (incoming.get(5)) {
        struct.nextEventTime = iprot.readDouble();
        struct.setNextEventTimeIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}
