/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package conceptos;  
@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class comprobante extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"comprobante\",\"namespace\":\"conceptos\",\"fields\":[{\"name\":\"concepto\",\"type\":{\"type\":\"record\",\"name\":\"comprobante\",\"namespace\":\"concepto.conceptos\",\"fields\":[{\"name\":\"descripcion\",\"type\":\"string\"},{\"name\":\"unidad\",\"type\":\"string\"},{\"name\":\"cantidad\",\"type\":\"int\"},{\"name\":\"complementoConcepto\",\"type\":\"string\"},{\"name\":\"importe\",\"type\":\"double\"},{\"name\":\"valorUnitario\",\"type\":\"double\"}]}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  @Deprecated public concepto.conceptos.comprobante concepto;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use {@link \#newBuilder()}. 
   */
  public comprobante() {}

  /**
   * All-args constructor.
   */
  public comprobante(concepto.conceptos.comprobante concepto) {
    this.concepto = concepto;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return concepto;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: concepto = (concepto.conceptos.comprobante)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'concepto' field.
   */
  public concepto.conceptos.comprobante getConcepto() {
    return concepto;
  }

  /**
   * Sets the value of the 'concepto' field.
   * @param value the value to set.
   */
  public void setConcepto(concepto.conceptos.comprobante value) {
    this.concepto = value;
  }

  /** Creates a new comprobante RecordBuilder */
  public static conceptos.comprobante.Builder newBuilder() {
    return new conceptos.comprobante.Builder();
  }
  
  /** Creates a new comprobante RecordBuilder by copying an existing Builder */
  public static conceptos.comprobante.Builder newBuilder(conceptos.comprobante.Builder other) {
    return new conceptos.comprobante.Builder(other);
  }
  
  /** Creates a new comprobante RecordBuilder by copying an existing comprobante instance */
  public static conceptos.comprobante.Builder newBuilder(conceptos.comprobante other) {
    return new conceptos.comprobante.Builder(other);
  }
  
  /**
   * RecordBuilder for comprobante instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<comprobante>
    implements org.apache.avro.data.RecordBuilder<comprobante> {

    private concepto.conceptos.comprobante concepto;

    /** Creates a new Builder */
    private Builder() {
      super(conceptos.comprobante.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(conceptos.comprobante.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.concepto)) {
        this.concepto = data().deepCopy(fields()[0].schema(), other.concepto);
        fieldSetFlags()[0] = true;
      }
    }
    
    /** Creates a Builder by copying an existing comprobante instance */
    private Builder(conceptos.comprobante other) {
            super(conceptos.comprobante.SCHEMA$);
      if (isValidValue(fields()[0], other.concepto)) {
        this.concepto = data().deepCopy(fields()[0].schema(), other.concepto);
        fieldSetFlags()[0] = true;
      }
    }

    /** Gets the value of the 'concepto' field */
    public concepto.conceptos.comprobante getConcepto() {
      return concepto;
    }
    
    /** Sets the value of the 'concepto' field */
    public conceptos.comprobante.Builder setConcepto(concepto.conceptos.comprobante value) {
      validate(fields()[0], value);
      this.concepto = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'concepto' field has been set */
    public boolean hasConcepto() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'concepto' field */
    public conceptos.comprobante.Builder clearConcepto() {
      concepto = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    @Override
    public comprobante build() {
      try {
        comprobante record = new comprobante();
        record.concepto = fieldSetFlags()[0] ? this.concepto : (concepto.conceptos.comprobante) defaultValue(fields()[0]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}