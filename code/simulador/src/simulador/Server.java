package simulador;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Predicate;

import org.apache.commons.math3.distribution.ExponentialDistribution;

public class Server {
	
	private LinkedList<Map<String, Double>> queue;
	private double avgServiceTime;
	private ExponentialDistribution expDist;
	private boolean busy = false;
	private int requestsAttended;
	private int served;
	private double totalWorkTime;
	private double totalWaiting;

	public Server(double avgServiceTime) {
		this.queue = new LinkedList<Map<String, Double>>();
		this.avgServiceTime = avgServiceTime;
		this.expDist = new ExponentialDistribution(avgServiceTime);
		this.served = 0;
		this.totalWorkTime = 0;
		this.totalWaiting = 0;
	}

	public void receivesRequest(double presentTime) {
		Map m = new HashMap<String, Double>();
		m.put("start", presentTime);
		m.put("end", presentTime + Math.floor(expDist.sample()));
		this.queue.add(m);
	}

	private boolean isBusy() { return busy;	}

	/**
	 * Ao ser chamado esse método irá verificar se coisas precisam ser processadas 
	 * ou finalizaram de ser processadas e atualizar o servidor de acordo. 
	 * @param presentTime
	 */
	public void clock(int presentTime) {
		busy = (queue.size() > 0);
		totalWaiting += queue.size();
		Iterator<Map<String, Double>> it = queue.iterator();
		while (it.hasNext()) {
			Map <String, Double> elem = it.next();
			if (elem.get("end") == presentTime) {
				served++;
				it.remove();
				totalWorkTime += elem.get("end") - elem.get("start"); 
			}
		}
	}

	public int totalServed() { return served; }

	public Double avgServiceTime() { return totalWorkTime/served; }

	public Double avgWaitingCount(int duration) { return totalWaiting/duration;	}
	

}
