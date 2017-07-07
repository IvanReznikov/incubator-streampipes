package org.streampipes.pe.algorithms.esper.pattern.sequence;

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
import org.streampipes.model.impl.output.CustomOutputStrategy;
import org.streampipes.model.impl.output.OutputStrategy;
import org.streampipes.model.impl.staticproperty.FreeTextStaticProperty;
import org.streampipes.model.impl.staticproperty.MatchingStaticProperty;
import org.streampipes.model.impl.staticproperty.OneOfStaticProperty;
import org.streampipes.model.impl.staticproperty.Option;
import org.streampipes.model.impl.staticproperty.StaticProperty;
import org.streampipes.model.util.SepaUtils;
import org.streampipes.runtime.flat.declarer.FlatEpDeclarer;
import org.streampipes.container.util.StandardTransportFormat;

public class SequenceController extends FlatEpDeclarer<SequenceParameters> {

	@Override
	public SepaDescription declareModel() {
		EventStream stream1 = new EventStream();
		EventStream stream2 = new EventStream();
		
		EventProperty e1 = new EventPropertyPrimitive();
		EventProperty e2 = new EventPropertyPrimitive();
		
		SepaDescription desc = new SepaDescription("sequence", "Sequence", "Detects a sequence of events in the following form: Event A followed by Event B within X seconds. In addition, both streams can be matched by a common property value (e.g., a.machineId = b.machineId).");
		desc.setIconUrl(EsperConfig.iconBaseUrl + "/Sequence_Icon_HQ.png");
		desc.setCategory(Arrays.asList(EpaType.PATTERN_DETECT.name()));
		
		stream1.setUri(EsperConfig.serverUrl +"/" +Utils.getRandomString());
		stream1.setEventSchema(new EventSchema(Arrays.asList(e1)));
		stream2.setUri(EsperConfig.serverUrl +"/" +Utils.getRandomString());
		stream2.setEventSchema(new EventSchema(Arrays.asList(e2)));
		
		desc.addEventStream(stream1);
		desc.addEventStream(stream2);	
		
		List<OutputStrategy> strategies = new ArrayList<OutputStrategy>();
		strategies.add(new CustomOutputStrategy(false));
		desc.setOutputStrategies(strategies);
		
		List<StaticProperty> staticProperties = new ArrayList<StaticProperty>();
		
		OneOfStaticProperty timeWindowUnit = new OneOfStaticProperty("time-unit", "Time Unit", "Specifies a unit for the time window of the sequence. ");
		timeWindowUnit.addOption(new Option("sec"));
		timeWindowUnit.addOption(new Option("min"));
		timeWindowUnit.addOption(new Option("hrs"));
		staticProperties.add(timeWindowUnit);
		
//		OneOfStaticProperty matchingOperator = new OneOfStaticProperty("matching-operator", "Time Unit", "Specifies a unit for the time window of the sequence. ");
//		matchingOperator.addOption(new Option("=="));
//		matchingOperator.addOption(new Option(">="));
//		matchingOperator.addOption(new Option("<="));
//		matchingOperator.addOption(new Option("<"));
//		matchingOperator.addOption(new Option(">"));
//		matchingOperator.setValueRequired(false);
//		staticProperties.add(matchingOperator);
		
		FreeTextStaticProperty duration = new FreeTextStaticProperty("duration", "Time Value", "Specifies the size of the time window.");
		staticProperties.add(duration);
		
		MatchingStaticProperty matchingProperty = new MatchingStaticProperty("matching", "Matching", "Specifies an additional value restriction on both streams.", URI.create(e1.getElementId()), URI.create(e2.getElementId()));
		//staticProperties.add(matchingProperty);
		
//		MappingProperty m1 = new MappingPropertyUnary(URI.create(e1.getElementId()), "partition", "Partition", "The streams will be partitioned based on the selected property.");
//		m1.setValueRequired(false);
//		staticProperties.add(m1);
		
		//staticProperties.add(new MatchingStaticProperty("select matching", ""));
		desc.setStaticProperties(staticProperties);
		desc.setSupportedGrounding(StandardTransportFormat.getSupportedGrounding());
		return desc;
	}

	@Override
	public Response invokeRuntime(SepaInvocation invocationGraph) {
		String timeUnit = SepaUtils.getOneOfProperty(invocationGraph, "time-unit");
		//String matchingOperator = SepaUtils.getOneOfProperty(invocationGraph, "matching-operator");
		String matchingOperator = "";
		int duration = Integer.parseInt(SepaUtils.getFreeTextStaticPropertyValue(invocationGraph, "duration"));
		//String partitionProperty = SepaUtils.getMappingPropertyName(invocationGraph, "partition", true);
		//List<String> matchingProperties = SepaUtils.getMatchingPropertyNames(invocationGraph, "matching");
		List<String> matchingProperties = new ArrayList<>();
		SequenceParameters params = new SequenceParameters(invocationGraph, timeUnit, matchingOperator, duration, matchingProperties);

		return submit(params, Sequence::new, invocationGraph);

	}

}