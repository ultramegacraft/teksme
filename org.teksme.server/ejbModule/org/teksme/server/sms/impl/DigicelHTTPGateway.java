package org.teksme.server.comm.sms;

import org.smslib.AGateway;

public class DigicelHTTPGateway extends AGateway {

	public DigicelHTTPGateway(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getQueueSchedulingInterval() {
		// TODO Auto-generated method stub
		return 0;
	}

}
