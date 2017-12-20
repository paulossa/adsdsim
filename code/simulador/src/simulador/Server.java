package simulador;

import java.util.LinkedList;
import java.util.function.Predicate;

import org.apache.commons.math3.distribution.ExponentialDistribution;

public class Server {
	
	private LinkedList<Double> queue;
	private double avgServiceTime;
	private ExponentialDistribution expDist;
	private boolean busy = false;
	private int requestsAttended;

	public Server(double avgServiceTime) {
		this.queue = new LinkedList<Double>();
		this.avgServiceTime = avgServiceTime;
		this.expDist = new ExponentialDistribution(avgServiceTime);
	}

	public void receivesRequest(double presentTime) {
		this.queue.add(presentTime + Math.floor(expDist.sample()));
	}

	private boolean isBusy() { return busy;	}

	/**
	 * Ao ser chamado esse método irá verificar se coisas precisam ser processadas 
	 * ou finalizaram de ser processadas e atualizar o servidor de acordo. 
	 * @param presentTime
	 */
	public void clock(int presentTime) {
		busy = (queue.size() > 0);
		
		final double paramTime = presentTime;
		Predicate<Double> isCurrentTime = t -> t == paramTime;
		if (queue.removeIf(isCurrentTime)) {
			requestsAttended += 1;
		}
		
	}
	
	
	
	
	
	
	

}
