/**
 * This file is part of fr.inria.plasmalab.distributed.
 *
 * fr.inria.plasmalab.distributed is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.distributed is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.distributed.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.distributed.heartbeat.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataReceiver implements Runnable {
	final static Logger logger = LoggerFactory.getLogger(DataReceiver.class);
	//HEARTBEAT PORT
	private static final int HEARTBEAT_PORT = 8112;
	// Singleton
	private static DataReceiver dataReceiver;
	// Socket
	private DatagramSocket udpSocket;
	// Heartbeat consumer
	private List<HeartbeatConsumer> consumers;

	private DataReceiver() {
		logger.debug("new DataReceiver");
		try {
			udpSocket = new DatagramSocket(HEARTBEAT_PORT);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		this.consumers = new LinkedList<HeartbeatConsumer>();
	}
	
	/**
	 * This method returns the DataReceiver singleton.
	 * @return		the DataReceiver
	 */
	public static DataReceiver getDataReceiver(){
		if(dataReceiver == null){
			dataReceiver = new DataReceiver();
		}		
		return dataReceiver;
	}

	public void addConsumer(HeartbeatConsumer consumer){
		logger.debug("add consumer");
		consumers.add(consumer);
	}
	
	public void remConsumer(HeartbeatConsumer consumer){
		consumers.remove(consumer);
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				byte[] buf = new byte[256];
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				// Listen to incoming packet on udpSocket
				udpSocket.receive(packet);
				String msg = new String(packet.getData(), 0, packet.getLength());
				logger.debug("receive message: " + msg);

				//For now there is only heartbeat consumer so we transmitt to all of them
				for(HeartbeatConsumer cons: consumers)
					cons.receiveHeartBeat(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
