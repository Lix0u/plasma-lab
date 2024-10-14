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
/**
 * 
 */
package fr.inria.plasmalab.distributed.heartbeat.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author kevin.corre@inria.fr
 *
 */
public class DataSender implements Runnable {
	final static Logger logger = LoggerFactory.getLogger(DataSender.class);
	//Singleton
	private static DataSender sender;
	//Message queue
	private static LinkedBlockingQueue<DatagramPacket> pckQueue;
	//Socket
	private static DatagramSocket udpSocket;
	//Atomic operation counter
	private static AtomicInteger users = new AtomicInteger(0);
	
	private DataSender(){
		pckQueue = new LinkedBlockingQueue<DatagramPacket>();
		try {
			udpSocket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while(users.get() > 0){
			try {
				//Take the oldest packet and send it
				udpSocket.send(pckQueue.take());
			} catch (IOException e) {
				logger.warn(e.getMessage());
			} catch (InterruptedException e) {
				logger.warn(e.getMessage());
			}
		}
		logger.debug("date sender done");
	}
	
	
	/**
	 * This method returns the DataSender instance.
	 * DataSender is a singleton because we need only one udp transmitter for each JVM.
	 * Increase a counter of DataSender user. DataSender thread will stop if the counter reaches 0.
	 * @return 			the DataSender instance
	 */
	public static DataSender getDataSender(){
		//Double checked locking
		synchronized (logger) {
		if(sender == null){
				sender = new DataSender();
				//Start udp sender
				new Thread(sender).start();
			}
		}
		users.getAndIncrement();
		return sender;
	}
	
	/**
	 * This method decrease the number of DataSender user.
	 * One the counter reach 0, DataSender thread stop. DataSender could be restarted
	 * at the next getDataSender call.
	 * A last packet is sent to unlock the blocking queue.
	 * @return
	 */
	public static void remDataSender(DatagramPacket pck){
		users.getAndDecrement();
		//Add pck to unlock
		pckQueue.add(pck);
	}
	
	public LinkedBlockingQueue<DatagramPacket> getPckQueue(){
		return pckQueue;
	}

	
}
