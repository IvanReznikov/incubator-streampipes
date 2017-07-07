package org.streampipes.pe.algorithms.esper.proasense.drillingstop;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.streampipes.commons.Utils;
import org.streampipes.pe.algorithms.esper.config.EsperConfig;
import org.streampipes.model.impl.EpaType;
import org.streampipes.model.impl.EventSchema;
import org.streampipes.model.impl.EventStream;
import org.streampipes.model.impl.Response;
import org.streampipes.model.impl.eventproperty.EventProperty;
import org.streampipes.model.impl.eventproperty.EventPropertyPrimitive;
import org.streampipes.model.impl.graph.SepaDescription;
import org.streampipes.model.impl.graph.SepaInvocation;
import org.streampipes.model.impl.output.AppendOutputStrategy;
import org.streampipes.model.impl.output.OutputStrategy;
import org.streampipes.model.impl.staticproperty.FreeTextStaticProperty;
import org.streampipes.model.impl.staticproperty.MappingPropertyUnary;
import org.streampipes.model.impl.staticproperty.StaticProperty;
import org.streampipes.model.util.SepaUtils;
import org.streampipes.model.vocabulary.MhWirth;
import org.streampipes.model.vocabulary.XSD;
import org.streampipes.runtime.flat.declarer.FlatEpDeclarer;
import org.streampipes.container.util.StandardTransportFormat;

public class DrillingStopController extends FlatEpDeclarer<DrillingStopParameters>{

	@Override
	public Response invokeRuntime(SepaInvocation sepa) {
			
		int minRpm = Integer.parseInt(SepaUtils.getFreeTextStaticPropertyValue(sepa, "rpm"));
		int minTorque = Integer.parseInt(SepaUtils.getFreeTextStaticPropertyValue(sepa, "torque"));
		
		String latPropertyName = SepaUtils.getMappingPropertyName(sepa, "rpm");
		String lngPropertyName = SepaUtils.getMappingPropertyName(sepa, "latitude");	
	
		System.out.println(minRpm +", " +minTorque +", " +latPropertyName +", " +lngPropertyName);
		DrillingStopParameters staticParam = new DrillingStopParameters(
				sepa, 
				minRpm,
				minTorque,
				latPropertyName,
				lngPropertyName);

		return submit(staticParam, DrillingStop::new, sepa);

	}
	
	@Override
	public SepaDescription declareModel() {
	
		EventStream stream1 = new EventStream();
		EventStream stream2 = new EventStream();
		
		EventSchema schema1 = new EventSchema();
		EventPropertyPrimitive p1 = new EventPropertyPrimitive(Utils.createURI(MhWirth.Rpm));
		schema1.addEventProperty(p1);
		
		EventSchema schema2 = new EventSchema();
		EventPropertyPrimitive p2 = new EventPropertyPrimitive(Utils.createURI(MhWirth.Torque));
		schema2.addEventProperty(p2);
		
		
		SepaDescription desc = new SepaDescription("drillingstop", "Driling Stop", "Detects stop of a drilling process");
		desc.setIconUrl(EsperConfig.iconBaseUrl + "/Drilling_Stop_HQ.png");
		desc.setCategory(Arrays.asList(EpaType.ALGORITHM.name()));
		
		stream1.setUri(EsperConfig.serverUrl +"/" +Utils.getRandomString());
		stream2.setUri(EsperConfig.serverUrl +"/" +Utils.getRandomString());
		stream1.setEventSchema(schema1);
		stream2.setEventSchema(schema2);
		desc.addEventStream(stream1);
		desc.addEventStream(stream2);
		
		List<OutputStrategy> strategies = new ArrayList<OutputStrategy>();
		List<EventProperty> appendProperties = new ArrayList<EventProperty>();			
		
		EventProperty result = new EventPropertyPrimitive(XSD._boolean.toString(),
				"drilingStatus", "", Utils.createURI(MhWirth.DrillingStatus));;
	
		appendProperties.add(result);
		strategies.add(new AppendOutputStrategy(appendProperties));
		desc.setOutputStrategies(strategies);
		
		List<StaticProperty> staticProperties = new ArrayList<StaticProperty>();
		
		FreeTextStaticProperty rpmThreshold = new FreeTextStaticProperty("rpm", "RPM threshold", "");
		FreeTextStaticProperty torqueThreshold = new FreeTextStaticProperty("torque", "Torque threshold", "");
		staticProperties.add(rpmThreshold);
		staticProperties.add(torqueThreshold);
		
		staticProperties.add(new MappingPropertyUnary(URI.create(p1.getElementName()), "rpm", "Select RPM Mapping", ""));
		staticProperties.add(new MappingPropertyUnary(URI.create(p2.getElementName()), "latitude", "Select Torque Mapping", ""));
		desc.setStaticProperties(staticProperties);
		desc.setSupportedGrounding(StandardTransportFormat.getSupportedGrounding());
		return desc;
	}
	
}