package de.fzi.cep.sepa.model.impl.staticproperty;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

import com.clarkparsia.empire.annotation.Namespaces;
import com.clarkparsia.empire.annotation.RdfProperty;
import com.clarkparsia.empire.annotation.RdfsClass;

import de.fzi.cep.sepa.model.UnnamedSEPAElement;

@Namespaces({"sepa", "http://sepa.event-processing.org/sepa#",
	 "dc",   "http://purl.org/dc/terms/", "so", "http://schema.org/"})
@RdfsClass("sepa:StaticProperty")
@MappedSuperclass
@Entity
public abstract class StaticProperty extends UnnamedSEPAElement {

	private static final long serialVersionUID = 2509153122084646025L;

	@RdfProperty("rdfs:label")
	protected String label;
	
	@RdfProperty("rdfs:description")
	protected String description;
	
	@RdfProperty("sepa:internalName")
	protected String internalName;
	
	@RdfProperty("so:valueRequired")
	protected boolean valueRequired;

	protected StaticPropertyType staticPropertyType;
	
	
	public StaticProperty()
	{
		super();
	}

	public StaticProperty(StaticPropertyType type) {
		super();
		this.staticPropertyType = type;
	}
	
	public StaticProperty(StaticProperty other)
	{
		super(other);
		this.description = other.getDescription();
		this.internalName = other.getInternalName();
		this.valueRequired = other.isValueRequired();
		this.staticPropertyType = other.getStaticPropertyType();
		this.label = other.getLabel();
	}
	
	public StaticProperty(StaticPropertyType type, String internalName, String label, String description)
	{
		super();
		this.staticPropertyType = type;
		this.internalName = internalName;
		this.label = label;
		this.description = description;
	}

	public String getInternalName() {
		return internalName;
	}

	public void setInternalName(String name) {
		this.internalName = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean isValueRequired() {
		return valueRequired;
	}

	public void setValueRequired(boolean valueRequired) {
		this.valueRequired = valueRequired;
	}

	public StaticPropertyType getStaticPropertyType() {
		return staticPropertyType;
	}

	public void setStaticPropertyType(StaticPropertyType staticPropertyType) {
		this.staticPropertyType = staticPropertyType;
	}
}