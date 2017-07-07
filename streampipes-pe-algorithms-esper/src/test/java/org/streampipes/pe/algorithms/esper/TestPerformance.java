package org.streampipes.pe.algorithms.esper;

import org.apache.commons.lang3.RandomStringUtils;

import com.google.common.io.Resources;

import org.streampipes.commons.exceptions.SepaParseException;
import org.streampipes.pe.algorithms.esper.extract.ProjectController;
import org.streampipes.model.impl.graph.SepaInvocation;
import org.streampipes.container.util.DeclarerUtils;

public class TestPerformance {

	public static void main(String[] args) throws InterruptedException
	{
		SepaInvocation invocation;
		try {
			ProjectController controller = new ProjectController();
			invocation = DeclarerUtils.descriptionFromResources(Resources.getResource("test-invocation.jsonLd"), SepaInvocation.class);
			controller.invokeRuntime(invocation);
			
			SepaInvocation invocation2 = new SepaInvocation(invocation);
			invocation2.getOutputStream().getEventGrounding().getTransportProtocol().setTopicName(RandomStringUtils.randomAlphabetic(8));
			
			controller.invokeRuntime(invocation2);
			Thread.sleep(2000);
			controller.detachRuntime(invocation.getElementId());
			Thread.sleep(2000);
			controller.detachRuntime(invocation2.getElementId());
			
		} catch (SepaParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}