package org.streampipes.pe.algorithms.esper.writer;

import com.espertech.esper.client.EventBean;

import org.streampipes.runtime.OutputCollector;

public class SEPAWriter implements Writer {

	private OutputCollector collector;
	
	public SEPAWriter(OutputCollector collector) {
		this.collector = collector;
	}
	
	@Override
	public void onEvent(EventBean bean) {
		//System.out.println(new Gson().toJson(bean.getUnderlying()));
		collector.send(bean.getUnderlying());
	}

}