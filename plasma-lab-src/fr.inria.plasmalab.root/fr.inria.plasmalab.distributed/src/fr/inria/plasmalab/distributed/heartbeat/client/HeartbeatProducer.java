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
package fr.inria.plasmalab.distributed.heartbeat.client;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeartbeatProducer implements Runnable {
	final static Logger logger = LoggerFactory.getLogger(HeartbeatProducer.class);
	//Beat frequency, in ms
	private static final int SLEEP_FOR = 3000;
	//HEARTBEAT PORT
	private static final int HEARTBEAT_PORT = 8112;
	//Node UID to transmit
	private String nodeUID;
	//Network parameters
	private InetAddress serverAddress;
	//Message queue
	private LinkedBlockingQueue<DatagramPacket> pckQueue;
	//Transmit while alive
	private boolean alive;
	
	private DatagramPacket pck;

	public HeartbeatProducer(String serverAddress, String nodeUID){
		logger.debug("new heartbeat producer");
		this.nodeUID = nodeUID;
		try {
			this.serverAddress = InetAddress.getByName(serverAddress);
		} catch (UnknownHostException e) {
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		}
		DataSender sender = DataSender.getDataSender();
		this.pckQueue = sender.getPckQueue();
		this.alive = true;
		
		//Generate packet
		pck = new DatagramPacket(nodeUID.getBytes(), nodeUID.getBytes().length,
				this.serverAddress, HEARTBEAT_PORT);
	}
	
	@Override
	public void run() {
		//Transmit packet every three seconds
		while(alive){
			try {
				logger.debug("send a heartbeat");
				pckQueue.put(pck);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(SLEEP_FOR);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		logger.debug("heartbeat producer stopped");
	}
	
	public void stopTransmitting(){
		logger.debug("stop heartbeat producer");
		alive = false;
		DataSender.remDataSender(pck);
	}

}
