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

package org.teksme.server;

import java.io.IOException;

import javax.ejb.Local;
import javax.xml.parsers.ParserConfigurationException;

import org.teksme.model.teks.InboundTextMessage;
import org.xml.sax.SAXException;

@Local
public interface SMSInboundMessageLocal {

	void setReceiveCycle(int receiveCycle);

	void readMessage(InboundTextMessage inboundMessage);

	InboundTextMessage createInboundMsgModelFromXml(String xmlInput)
			throws IOException, SAXException, ParserConfigurationException;

}
