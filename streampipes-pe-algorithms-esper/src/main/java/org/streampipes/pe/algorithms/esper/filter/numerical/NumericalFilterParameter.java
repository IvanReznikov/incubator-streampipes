package org.streampipes.pe.algorithms.esper.filter.numerical;

import org.streampipes.pe.algorithms.esper.util.NumericalOperator;
import org.streampipes.model.impl.graph.SepaInvocation;
import org.streampipes.runtime.BindingParameters;

public class NumericalFilterParameter extends BindingParameters {

	private double threshold;
	private NumericalOperator numericalOperator;
	private String filterProperty;
	
	public NumericalFilterParameter(SepaInvocation graph, double threshold, NumericalOperator numericalOperator, String filterProperty) {
		super(graph);
		this.threshold = threshold;
		this.numericalOperator = numericalOperator;
		this.filterProperty = filterProperty;
	}

	public double getThreshold() {
		return threshold;
	}

	public NumericalOperator getNumericalOperator() {
		return numericalOperator;
	}

	public String getFilterProperty() {
		return filterProperty;
	}
	
	
	
}