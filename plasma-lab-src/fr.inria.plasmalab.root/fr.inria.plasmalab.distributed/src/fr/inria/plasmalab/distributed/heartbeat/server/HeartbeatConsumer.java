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

import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.inria.plasmalab.distributed.heartbeat.common.HeartbeatListener;

public class HeartbeatConsumer implements Runnable{
	final static Logger logger = LoggerFactory.getLogger(HeartbeatConsumer.class);
	private static final int SLEEP_FOR = 3000;
	private static final long TIMEOUT = 3*SLEEP_FOR; //3*SLEEP_FOR
	private DataReceiver udpReceiver;
	private ConcurrentHashMap<String, Long> timeStampMap;
	
	//Algorithm scheduler
	private HeartbeatListener listener;
	private boolean alive;
	
	public HeartbeatConsumer(HeartbeatListener listener){
		timeStampMap = new ConcurrentHashMap<String, Long>();
		this.listener = listener;
		alive = true;
	}
	
	
	@Override
	public void run() {
		//Start UDP receiver
		udpReceiver = DataReceiver.getDataReceiver();
		udpReceiver.addConsumer(this);
		new Thread(udpReceiver).start();
		while(alive){
			logger.debug(timeStampMap.size() + " node registered");
			for(String nodeUID: timeStampMap.keySet()){
				logger.debug(nodeUID + " last time stamp: " + timeStampMap.get(nodeUID));
				if(System.currentTimeMillis()-timeStampMap.get(nodeUID)>TIMEOUT)
					onTimeOut(nodeUID);
			}
			//SLEEP
			try {
				Thread.sleep(SLEEP_FOR);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		udpReceiver.remConsumer(this);
	}

	public void stopListening(){
		alive = false;
		udpReceiver.remConsumer(this);
	}
	
	public void registerNode(String nodeUID){
		if(alive)
			timeStampMap.replace(nodeUID, System.currentTimeMillis());
		logger.debug(nodeUID+" registered");
	}
	
	public void removeNode(String nodeUID){
		if(alive)
			timeStampMap.remove(nodeUID);
		logger.debug(nodeUID+" removed from list");
	}
	
	public void receiveHeartBeat(String nodeUID){
		if(alive)
			timeStampMap.put(nodeUID, System.currentTimeMillis());
		logger.debug(nodeUID+" transmitted a heartbeat");
	}
	
	public void onTimeOut(String nodeUID){
		if(alive)
			listener.timeoutTriggered(nodeUID);
		logger.info(nodeUID+" triggered timeout");
	}
}
