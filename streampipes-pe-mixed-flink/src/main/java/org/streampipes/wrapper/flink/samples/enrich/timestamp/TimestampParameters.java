package org.streampipes.wrapper.flink.samples.enrich.timestamp;

import java.util.List;

import org.streampipes.model.impl.graph.SepaInvocation;
import org.streampipes.runtime.BindingParameters;

public class TimestampParameters extends BindingParameters {

	private String appendTimePropertyName;
	private List<String> selectProperties;
	
	public TimestampParameters(SepaInvocation graph,
			String appendTimePropertyName, List<String> selectProperties) {
		super(graph);
		this.appendTimePropertyName = appendTimePropertyName;
		this.selectProperties = selectProperties;
	}

	public String getAppendTimePropertyName() {
		return appendTimePropertyName;
	}

	public List<String> getSelectProperties() {
		return selectProperties;
	}

}