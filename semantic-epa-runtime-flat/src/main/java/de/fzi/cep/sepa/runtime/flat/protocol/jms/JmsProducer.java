package de.fzi.cep.sepa.runtime.flat.protocol.jms;

import javax.jms.JMSException;

import de.fzi.cep.sepa.commons.messaging.activemq.ActiveMQPublisher;
import de.fzi.cep.sepa.runtime.flat.datatype.DatatypeDefinition;
import de.fzi.cep.sepa.runtime.flat.protocol.Producer;

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
		try {
			publisher.sendText(new String(dataType.marshal(message)));
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		try {
			publisher.close();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
