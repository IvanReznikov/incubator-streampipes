package org.streampipes.runtime.flat.protocol.jms;


import org.streampipes.messaging.jms.ActiveMQPublisher;
import org.streampipes.runtime.flat.datatype.DatatypeDefinition;
import org.streampipes.runtime.flat.protocol.Producer;

import javax.jms.JMSException;

public class JmsProducer extends Producer {

	private String brokerHostname;
	private int brokerPort;
	private String topic;
	
	private ActiveMQPublisher publisher;
	
	public JmsProducer(String brokerHostname, int brokerPort, String topic, DatatypeDefinition dataType) {
		super(dataType);
		this.brokerHostname = brokerHostname;
		this.brokerPort = brokerPort;
		this.topic = topic;
	}
	
	@Override
	public void publish(Object message) {
		publisher.publish(new String(dataType.marshal(message)));
	}

	@Override
	public void openProducer() {
		try {
			publisher = new ActiveMQPublisher(brokerHostname +":" +brokerPort, topic);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void closeProducer() {
		publisher.closeProducer();
	}

}