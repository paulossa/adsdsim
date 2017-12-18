package simulador;

import java.util.LinkedList;

import org.apache.commons.math3.distribution.ExponentialDistribution;

public class Server {
	
	private LinkedList queue;
	private double avgServiceTime;
	private ExponentialDistribution expDist;

	public Server(double avgServiceTime) {
		this.queue = new LinkedList<>();
		this.avgServiceTime = avgServiceTime;
		this.expDist = new ExponentialDistribution(avgServiceTime);
	}
	
	
	
	
	
	
	

}
