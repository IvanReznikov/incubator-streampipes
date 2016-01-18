package de.fzi.proasense.hella.streams;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.fzi.cep.sepa.commons.Utils;
import de.fzi.cep.sepa.model.builder.EpProperties;
import de.fzi.cep.sepa.model.impl.EventSchema;
import de.fzi.cep.sepa.model.impl.EventStream;
import de.fzi.cep.sepa.model.impl.eventproperty.EventProperty;
import de.fzi.cep.sepa.model.impl.eventproperty.EventPropertyPrimitive;
import de.fzi.cep.sepa.model.impl.graph.SepDescription;
import de.fzi.cep.sepa.model.vocabulary.SO;
import de.fzi.cep.sepa.model.vocabulary.XSD;
import de.fzi.proasense.hella.config.HellaVariables;
import de.fzi.proasense.hella.main.AbstractHellaStream;

public class DustParticleStream extends AbstractHellaStream {

	public static final String dustFolder =System.getProperty("user.home") + File.separator +".streampipes" +File.separator +"sources" +File.separator +"data" +File.separator +"dust" +File.separator;

	@Override
	public EventStream declareModel(SepDescription sep) {
		
		EventStream stream = prepareStream(HellaVariables.Dust.topic());
		
		EventSchema schema = new EventSchema();
		List<EventProperty> eventProperties = new ArrayList<EventProperty>();
		eventProperties.add(EpProperties.stringEp("variable_type", SO.Text));
		eventProperties.add(new EventPropertyPrimitive(XSD._long.toString(), "variable_timestamp", "", Utils.createURI("http://schema.org/DateTime")));
		eventProperties.add(new EventPropertyPrimitive(XSD._string.toString(), "id", "", Utils.createURI(SO.Text)));
		eventProperties.add(new EventPropertyPrimitive(XSD._integer.toString(), "bin0", "", Utils.createURI(SO.Number)));
		eventProperties.add(new EventPropertyPrimitive(XSD._integer.toString(), "bin1", "", Utils.createURI(SO.Number)));
		eventProperties.add(new EventPropertyPrimitive(XSD._integer.toString(), "bin2", "", Utils.createURI(SO.Number)));
		eventProperties.add(new EventPropertyPrimitive(XSD._integer.toString(), "bin3", "", Utils.createURI(SO.Number)));
		eventProperties.add(new EventPropertyPrimitive(XSD._integer.toString(), "bin4", "", Utils.createURI(SO.Number)));
		eventProperties.add(new EventPropertyPrimitive(XSD._integer.toString(), "bin5", "", Utils.createURI(SO.Number)));
		eventProperties.add(new EventPropertyPrimitive(XSD._integer.toString(), "bin6", "", Utils.createURI(SO.Number)));
		eventProperties.add(new EventPropertyPrimitive(XSD._integer.toString(), "bin7", "", Utils.createURI(SO.Number)));
		eventProperties.add(new EventPropertyPrimitive(XSD._integer.toString(), "bin8", "", Utils.createURI(SO.Number)));
		eventProperties.add(new EventPropertyPrimitive(XSD._integer.toString(), "bin9", "", Utils.createURI(SO.Number)));
		eventProperties.add(new EventPropertyPrimitive(XSD._integer.toString(), "bin10", "", Utils.createURI(SO.Number)));
		eventProperties.add(new EventPropertyPrimitive(XSD._integer.toString(), "bin11", "", Utils.createURI(SO.Number)));
		eventProperties.add(new EventPropertyPrimitive(XSD._integer.toString(), "bin12", "", Utils.createURI(SO.Number)));
		eventProperties.add(new EventPropertyPrimitive(XSD._integer.toString(), "bin13", "", Utils.createURI(SO.Number)));
		eventProperties.add(new EventPropertyPrimitive(XSD._integer.toString(), "bin14", "", Utils.createURI(SO.Number)));
		eventProperties.add(new EventPropertyPrimitive(XSD._integer.toString(), "bin15", "", Utils.createURI(SO.Number)));
			
	
		
		schema.setEventProperties(eventProperties);
		stream.setEventSchema(schema);
		stream.setName(HellaVariables.Dust.eventName());
		stream.setDescription(HellaVariables.Dust.description());
		stream.setUri(sep.getUri() + "/dust");
		
		return stream;
	}

	@Override
	public boolean isExecutable() {
		return false;
	}

}
