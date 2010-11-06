/*
 * Copyright 2010 T�ksMe, Inc.
 * T�ksMe licenses this file to you under the Apache License, version
 * 2.0 (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *    http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.teksme.server.provider.sms.service;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.teksme.model.teks.InboundMessage;
import org.xml.sax.SAXException;

public interface SMSInboundMessage {

	public static String JNDI_NAME = "ejb/JNDI/SMSInboundMessage";

	void setReceiveCycle(int receiveCycle);

	void readMessage(InboundMessage inboundMessage);

	InboundMessage createInboundMsgModelFromXml(String xmlInput) throws IOException, SAXException, ParserConfigurationException;

}
