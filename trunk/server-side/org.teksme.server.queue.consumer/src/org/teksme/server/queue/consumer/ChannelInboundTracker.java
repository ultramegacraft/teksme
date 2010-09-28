/**
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

package org.teksme.server.queue.consumer;

import java.io.IOException;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.teksme.server.common.messaging.AMQPQueues;
import org.teksme.server.common.messaging.AMQPServiceRegistry;
import org.teksme.server.queue.consumer.impl.InboundConsumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 
 * @since 0.5
 * 
 */
public class ChannelInboundTracker extends ServiceTracker {

	private final String queueName;

	public ChannelInboundTracker(BundleContext context, String queueName) {
		super(context, Connection.class.getName(), null);
		this.queueName = queueName;
	}

	@Override
	public Object addingService(ServiceReference reference) {
		Connection conn = (Connection) context.getService(reference);
		String consumerTag = null;

		Channel channel = null;
		try {

			final boolean durable = Boolean.getBoolean(AMQPQueues.CHANNEL.DURABLE);
			final boolean autoDelete = Boolean.getBoolean(AMQPQueues.CHANNEL.AUTO_DELETE);
			final boolean exclusive = Boolean.getBoolean(AMQPQueues.CHANNEL.EXCLUSIVE);

			channel = conn.createChannel();

			InboundConsumer consumer = new InboundConsumer(channel);

			channel.queueDeclare(queueName, durable, exclusive, autoDelete, null);

			consumerTag = channel.basicConsume(queueName, false, consumer);

			return new AMQPServiceRegistry<Channel, String>(channel, consumerTag);

		} catch (IOException e) {
			System.err.println("Error subscribing consumer");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void removedService(ServiceReference reference, Object service) {
		@SuppressWarnings("unchecked")
		AMQPServiceRegistry<Channel, String> pair = (AMQPServiceRegistry<Channel, String>) service;

		try {
			pair.getFst().basicCancel(pair.getSender());
			pair.getFst().close();
		} catch (IOException e) {
			System.err.println("Error unsubscribing consumer");
			e.printStackTrace();
		}

		context.ungetService(reference);
	}
}
