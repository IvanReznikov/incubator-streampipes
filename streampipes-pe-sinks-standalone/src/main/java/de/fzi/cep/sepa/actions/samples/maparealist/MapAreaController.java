package de.fzi.cep.sepa.actions.samples.maparealist;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.fzi.cep.sepa.actions.config.ActionConfig;
import de.fzi.cep.sepa.actions.samples.ActionController;
import de.fzi.cep.sepa.actions.samples.util.ActionUtils;
import de.fzi.cep.sepa.commons.Utils;
import de.fzi.cep.sepa.sdk.helpers.EpRequirements;
import de.fzi.cep.sepa.model.impl.EcType;
import de.fzi.cep.sepa.model.impl.EventSchema;
import de.fzi.cep.sepa.model.impl.EventStream;
import de.fzi.cep.sepa.model.impl.Response;
import de.fzi.cep.sepa.model.impl.eventproperty.EventProperty;
import de.fzi.cep.sepa.model.impl.eventproperty.EventPropertyList;
import de.fzi.cep.sepa.model.impl.eventproperty.EventPropertyPrimitive;
import de.fzi.cep.sepa.model.impl.graph.SecDescription;
import de.fzi.cep.sepa.model.impl.graph.SecInvocation;
import de.fzi.cep.sepa.model.impl.staticproperty.MappingPropertyUnary;
import de.fzi.cep.sepa.model.impl.staticproperty.StaticProperty;
import de.fzi.cep.sepa.model.util.SepaUtils;
import de.fzi.cep.sepa.model.vocabulary.Geo;

public class MapAreaController extends ActionController {

	@Override
	public boolean isVisualizable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getHtml(SecInvocation sec) {
		String newUrl = createWebsocketUri(sec);
		String inputTopic = extractTopic(sec);
		
		String latitudeNw = SepaUtils.getMappingPropertyName(sec, "latitudeNw");
		String longitudeNw = SepaUtils.getMappingPropertyName(sec, "longitudeNw");
		String latitudeSe = SepaUtils.getMappingPropertyName(sec, "latitudeSe");
		String longitudeSe = SepaUtils.getMappingPropertyName(sec, "longitudeSe");
		String labelName = SepaUtils.getMappingPropertyName(sec, "label");
		
		System.out.println(latitudeNw);
		MapAreaParameters mapsParameters = new MapAreaParameters(inputTopic, newUrl, latitudeNw, longitudeNw, latitudeSe, longitudeSe, labelName);
		
		return new MapAreaGenerator(mapsParameters).generateHtml();
	}

	@Override
	public SecDescription declareModel() {
		SecDescription sec = new SecDescription("maparealist", "Map area view (list input)", "", "");
		sec.setIconUrl(ActionConfig.iconBaseUrl + "/Map_Icon_HQ.png");
		sec.setCategory(Arrays.asList(EcType.VISUALIZATION_GEO.name()));
		EventPropertyList listProperty = new EventPropertyList();
		
		List<EventProperty> eventProperties = new ArrayList<EventProperty>();
		EventProperty e1 = EpRequirements.domainPropertyReq(Geo.lat);
		EventProperty e2 = EpRequirements.domainPropertyReq(Geo.lng);
		EventProperty e3 = EpRequirements.domainPropertyReq(Geo.lat);
		EventProperty e4 = EpRequirements.domainPropertyReq(Geo.lng);
		EventProperty e5 = new EventPropertyPrimitive();
		
		eventProperties.add(e1);
		eventProperties.add(e2);
		eventProperties.add(e3);
		eventProperties.add(e4);
		eventProperties.add(e5);
		
		listProperty.setEventProperties(eventProperties);
		
		EventSchema schema1 = new EventSchema();
		schema1.setEventProperties(Arrays.asList(listProperty));
		
		EventStream stream1 = new EventStream();
		stream1.setEventSchema(schema1);		
		stream1.setUri(ActionConfig.serverUrl +"/" +Utils.getRandomString());
		
		
		List<StaticProperty> staticProperties = new ArrayList<StaticProperty>();
		staticProperties.add(new MappingPropertyUnary(URI.create(e1.getElementName()), "latitudeNw", "Select latitude property (NW)", ""));
		staticProperties.add(new MappingPropertyUnary(URI.create(e2.getElementName()), "longitudeNw", "Select longitude property (NW)", ""));
		staticProperties.add(new MappingPropertyUnary(URI.create(e3.getElementName()), "latitudeSe", "Select latitude property (SE)", ""));
		staticProperties.add(new MappingPropertyUnary(URI.create(e4.getElementName()), "longitudeSe", "Select longitude property (SE)", ""));
		staticProperties.add(new MappingPropertyUnary(URI.create(e5.getElementName()), "label", "Select Label", ""));

		sec.addEventStream(stream1);
		sec.setStaticProperties(staticProperties);
		sec.setSupportedGrounding(ActionUtils.getSupportedGrounding());
		
		return sec;
	}

    @Override
    public Response invokeRuntime(SecInvocation invocationGraph) {
        String pipelineId = invocationGraph.getCorrespondingPipeline();
        return new Response(pipelineId, true);
    }

    @Override
    public Response detachRuntime(String pipelineId) {
        return new Response(pipelineId, true);
    }

}