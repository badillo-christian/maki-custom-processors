/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package emisor;  
@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class comprobante extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"comprobante\",\"namespace\":\"emisor\",\"fields\":[{\"name\":\"expedidoEn\",\"type\":{\"type\":\"record\",\"name\":\"comprobante\",\"namespace\":\"expedidoEn.emisor\",\"fields\":[{\"name\":\"noExterior\",\"type\":\"string\"},{\"name\":\"estado\",\"type\":\"string\"},{\"name\":\"codigoPostal\",\"type\":\"string\"},{\"name\":\"municipio\",\"type\":\"string\"},{\"name\":\"calle\",\"type\":\"string\"},{\"name\":\"colonia\",\"type\":\"string\"},{\"name\":\"pais\",\"type\":\"string\"}]}},{\"name\":\"regimenFiscal\",\"type\":{\"type\":\"record\",\"name\":\"comprobante\",\"namespace\":\"regimenFiscal.emisor\",\"fields\":[{\"name\":\"Regimen\",\"type\":\"string\"}]}},{\"name\":\"domicilioFiscal\",\"type\":{\"type\":\"record\",\"name\":\"comprobante\",\"namespace\":\"domicilioFiscal.emisor\",\"fields\":[{\"name\":\"noExterior\",\"type\":\"string\"},{\"name\":\"estado\",\"type\":\"string\"},{\"name\":\"codigoPostal\",\"type\":\"string\"},{\"name\":\"municipio\",\"type\":\"string\"},{\"name\":\"calle\",\"type\":\"string\"},{\"name\":\"localidad\",\"type\":\"string\"},{\"name\":\"colonia\",\"type\":\"string\"},{\"name\":\"pais\",\"type\":\"string\"}]}},{\"name\":\"nombre\",\"type\":\"string\"},{\"name\":\"rfc\",\"type\":\"string\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  @Deprecated public expedidoEn.emisor.comprobante expedidoEn;
  @Deprecated public regimenFiscal.emisor.comprobante regimenFiscal;
  @Deprecated public domicilioFiscal.emisor.comprobante domicilioFiscal;
  @Deprecated public java.lang.CharSequence nombre;
  @Deprecated public java.lang.CharSequence rfc;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use {@link \#newBuilder()}. 
   */
  public comprobante() {}

  /**
   * All-args constructor.
   */
  public comprobante(expedidoEn.emisor.comprobante expedidoEn, regimenFiscal.emisor.comprobante regimenFiscal, domicilioFiscal.emisor.comprobante domicilioFiscal, java.lang.CharSequence nombre, java.lang.CharSequence rfc) {
    this.expedidoEn = expedidoEn;
    this.regimenFiscal = regimenFiscal;
    this.domicilioFiscal = domicilioFiscal;
    this.nombre = nombre;
    this.rfc = rfc;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return expedidoEn;
    case 1: return regimenFiscal;
    case 2: return domicilioFiscal;
    case 3: return nombre;
    case 4: return rfc;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: expedidoEn = (expedidoEn.emisor.comprobante)value$; break;
    case 1: regimenFiscal = (regimenFiscal.emisor.comprobante)value$; break;
    case 2: domicilioFiscal = (domicilioFiscal.emisor.comprobante)value$; break;
    case 3: nombre = (java.lang.CharSequence)value$; break;
    case 4: rfc = (java.lang.CharSequence)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'expedidoEn' field.
   */
  public expedidoEn.emisor.comprobante getExpedidoEn() {
    return expedidoEn;
  }

  /**
   * Sets the value of the 'expedidoEn' field.
   * @param value the value to set.
   */
  public void setExpedidoEn(expedidoEn.emisor.comprobante value) {
    this.expedidoEn = value;
  }

  /**
   * Gets the value of the 'regimenFiscal' field.
   */
  public regimenFiscal.emisor.comprobante getRegimenFiscal() {
    return regimenFiscal;
  }

  /**
   * Sets the value of the 'regimenFiscal' field.
   * @param value the value to set.
   */
  public void setRegimenFiscal(regimenFiscal.emisor.comprobante value) {
    this.regimenFiscal = value;
  }

  /**
   * Gets the value of the 'domicilioFiscal' field.
   */
  public domicilioFiscal.emisor.comprobante getDomicilioFiscal() {
    return domicilioFiscal;
  }

  /**
   * Sets the value of the 'domicilioFiscal' field.
   * @param value the value to set.
   */
  public void setDomicilioFiscal(domicilioFiscal.emisor.comprobante value) {
    this.domicilioFiscal = value;
  }

  /**
   * Gets the value of the 'nombre' field.
   */
  public java.lang.CharSequence getNombre() {
    return nombre;
  }

  /**
   * Sets the value of the 'nombre' field.
   * @param value the value to set.
   */
  public void setNombre(java.lang.CharSequence value) {
    this.nombre = value;
  }

  /**
   * Gets the value of the 'rfc' field.
   */
  public java.lang.CharSequence getRfc() {
    return rfc;
  }

  /**
   * Sets the value of the 'rfc' field.
   * @param value the value to set.
   */
  public void setRfc(java.lang.CharSequence value) {
    this.rfc = value;
  }

  /** Creates a new comprobante RecordBuilder */
  public static emisor.comprobante.Builder newBuilder() {
    return new emisor.comprobante.Builder();
  }
  
  /** Creates a new comprobante RecordBuilder by copying an existing Builder */
  public static emisor.comprobante.Builder newBuilder(emisor.comprobante.Builder other) {
    return new emisor.comprobante.Builder(other);
  }
  
  /** Creates a new comprobante RecordBuilder by copying an existing comprobante instance */
  public static emisor.comprobante.Builder newBuilder(emisor.comprobante other) {
    return new emisor.comprobante.Builder(other);
  }
  
  /**
   * RecordBuilder for comprobante instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<comprobante>
    implements org.apache.avro.data.RecordBuilder<comprobante> {

    private expedidoEn.emisor.comprobante expedidoEn;
    private regimenFiscal.emisor.comprobante regimenFiscal;
    private domicilioFiscal.emisor.comprobante domicilioFiscal;
    private java.lang.CharSequence nombre;
    private java.lang.CharSequence rfc;

    /** Creates a new Builder */
    private Builder() {
      super(emisor.comprobante.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(emisor.comprobante.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.expedidoEn)) {
        this.expedidoEn = data().deepCopy(fields()[0].schema(), other.expedidoEn);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.regimenFiscal)) {
        this.regimenFiscal = data().deepCopy(fields()[1].schema(), other.regimenFiscal);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.domicilioFiscal)) {
        this.domicilioFiscal = data().deepCopy(fields()[2].schema(), other.domicilioFiscal);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.nombre)) {
        this.nombre = data().deepCopy(fields()[3].schema(), other.nombre);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.rfc)) {
        this.rfc = data().deepCopy(fields()[4].schema(), other.rfc);
        fieldSetFlags()[4] = true;
      }
    }
    
    /** Creates a Builder by copying an existing comprobante instance */
    private Builder(emisor.comprobante other) {
            super(emisor.comprobante.SCHEMA$);
      if (isValidValue(fields()[0], other.expedidoEn)) {
        this.expedidoEn = data().deepCopy(fields()[0].schema(), other.expedidoEn);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.regimenFiscal)) {
        this.regimenFiscal = data().deepCopy(fields()[1].schema(), other.regimenFiscal);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.domicilioFiscal)) {
        this.domicilioFiscal = data().deepCopy(fields()[2].schema(), other.domicilioFiscal);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.nombre)) {
        this.nombre = data().deepCopy(fields()[3].schema(), other.nombre);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.rfc)) {
        this.rfc = data().deepCopy(fields()[4].schema(), other.rfc);
        fieldSetFlags()[4] = true;
      }
    }

    /** Gets the value of the 'expedidoEn' field */
    public expedidoEn.emisor.comprobante getExpedidoEn() {
      return expedidoEn;
    }
    
    /** Sets the value of the 'expedidoEn' field */
    public emisor.comprobante.Builder setExpedidoEn(expedidoEn.emisor.comprobante value) {
      validate(fields()[0], value);
      this.expedidoEn = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'expedidoEn' field has been set */
    public boolean hasExpedidoEn() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'expedidoEn' field */
    public emisor.comprobante.Builder clearExpedidoEn() {
      expedidoEn = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'regimenFiscal' field */
    public regimenFiscal.emisor.comprobante getRegimenFiscal() {
      return regimenFiscal;
    }
    
    /** Sets the value of the 'regimenFiscal' field */
    public emisor.comprobante.Builder setRegimenFiscal(regimenFiscal.emisor.comprobante value) {
      validate(fields()[1], value);
      this.regimenFiscal = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'regimenFiscal' field has been set */
    public boolean hasRegimenFiscal() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'regimenFiscal' field */
    public emisor.comprobante.Builder clearRegimenFiscal() {
      regimenFiscal = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /** Gets the value of the 'domicilioFiscal' field */
    public domicilioFiscal.emisor.comprobante getDomicilioFiscal() {
      return domicilioFiscal;
    }
    
    /** Sets the value of the 'domicilioFiscal' field */
    public emisor.comprobante.Builder setDomicilioFiscal(domicilioFiscal.emisor.comprobante value) {
      validate(fields()[2], value);
      this.domicilioFiscal = value;
      fieldSetFlags()[2] = true;
      return this; 
    }
    
    /** Checks whether the 'domicilioFiscal' field has been set */
    public boolean hasDomicilioFiscal() {
      return fieldSetFlags()[2];
    }
    
    /** Clears the value of the 'domicilioFiscal' field */
    public emisor.comprobante.Builder clearDomicilioFiscal() {
      domicilioFiscal = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /** Gets the value of the 'nombre' field */
    public java.lang.CharSequence getNombre() {
      return nombre;
    }
    
    /** Sets the value of the 'nombre' field */
    public emisor.comprobante.Builder setNombre(java.lang.CharSequence value) {
      validate(fields()[3], value);
      this.nombre = value;
      fieldSetFlags()[3] = true;
      return this; 
    }
    
    /** Checks whether the 'nombre' field has been set */
    public boolean hasNombre() {
      return fieldSetFlags()[3];
    }
    
    /** Clears the value of the 'nombre' field */
    public emisor.comprobante.Builder clearNombre() {
      nombre = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    /** Gets the value of the 'rfc' field */
    public java.lang.CharSequence getRfc() {
      return rfc;
    }
    
    /** Sets the value of the 'rfc' field */
    public emisor.comprobante.Builder setRfc(java.lang.CharSequence value) {
      validate(fields()[4], value);
      this.rfc = value;
      fieldSetFlags()[4] = true;
      return this; 
    }
    
    /** Checks whether the 'rfc' field has been set */
    public boolean hasRfc() {
      return fieldSetFlags()[4];
    }
    
    /** Clears the value of the 'rfc' field */
    public emisor.comprobante.Builder clearRfc() {
      rfc = null;
      fieldSetFlags()[4] = false;
      return this;
    }

    @Override
    public comprobante build() {
      try {
        comprobante record = new comprobante();
        record.expedidoEn = fieldSetFlags()[0] ? this.expedidoEn : (expedidoEn.emisor.comprobante) defaultValue(fields()[0]);
        record.regimenFiscal = fieldSetFlags()[1] ? this.regimenFiscal : (regimenFiscal.emisor.comprobante) defaultValue(fields()[1]);
        record.domicilioFiscal = fieldSetFlags()[2] ? this.domicilioFiscal : (domicilioFiscal.emisor.comprobante) defaultValue(fields()[2]);
        record.nombre = fieldSetFlags()[3] ? this.nombre : (java.lang.CharSequence) defaultValue(fields()[3]);
        record.rfc = fieldSetFlags()[4] ? this.rfc : (java.lang.CharSequence) defaultValue(fields()[4]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
