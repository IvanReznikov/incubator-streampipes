package org.streampipes.runtime.declarer;

import org.streampipes.container.declarer.SemanticEventProcessingAgentDeclarer;
import org.streampipes.model.impl.Response;
import org.streampipes.model.impl.graph.SepaInvocation;
import org.streampipes.runtime.EPEngine;
import org.streampipes.runtime.EPRuntime;
import org.streampipes.runtime.BindingParameters;
import org.streampipes.runtime.EngineParameters;
import org.streampipes.sdk.extractor.ProcessingElementParameterExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public abstract class EpDeclarer<B extends BindingParameters, EPR extends EPRuntime> implements SemanticEventProcessingAgentDeclarer {

	public static final Logger logger = LoggerFactory.getLogger(EpDeclarer.class.getCanonicalName());
	
	private EPR epRuntime;
	private String elementId;
		
	public void invokeEPRuntime(B bindingParameters, Supplier<EPEngine<B>> supplier, SepaInvocation sepa) throws Exception {
		
		EngineParameters<B> engineParams;
		elementId = sepa.getElementId();
		
		Map<String, Map<String, Object>> inEventTypes = new HashMap<>();
		Map<String, Object> outEventType = sepa.getOutputStream().getEventSchema().toRuntimeMap();

		sepa.getInputStreams().forEach(is ->
			inEventTypes.put("topic://" +is.getEventGrounding().getTransportProtocol().getTopicName(), is.getEventSchema().toRuntimeMap()));
		
		engineParams = new EngineParameters<>(
				inEventTypes,
				outEventType, bindingParameters, sepa);

		
		epRuntime = prepareRuntime(bindingParameters, supplier, engineParams);
		epRuntime.initRuntime();
		
		
		start();
	}
		
	public Response detachRuntime(String pipelineId) {
		try {
			preDetach();
			epRuntime.discard();
			return new Response(elementId, true);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response(elementId, false, e.getMessage());
		}
	}

	protected ProcessingElementParameterExtractor getExtractor(SepaInvocation graph) {
		return ProcessingElementParameterExtractor.from(graph);
	}
	
	public abstract void preDetach() throws Exception;
	
	public abstract EPR prepareRuntime(B bindingParameters, Supplier<EPEngine<B>> supplier, EngineParameters<B> engineParams);

	public abstract void start() throws Exception;

	protected Response submit(B staticParams, Supplier<EPEngine<B>> engine, SepaInvocation sepa) {
		try {
			invokeEPRuntime(staticParams, engine, sepa);
			return new Response(sepa.getElementId(), true);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response(sepa.getElementId(), false, e.getMessage());
		}
	}
}