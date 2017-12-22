package simulador;

import org.apache.commons.math3.distribution.*;
import java.util.Date;
import java.util.LinkedList;import java.util.function.Predicate;

public class Simulador {
	
	static final int NORMAL_DIST	= 1;
	static final int EXP_DIST 		= 2;
	static final int UNIFORM_DIST 	= 3;

	private int distribution;
	private int repetitions, duration;
	private double mean;
	private AbstractRealDistribution realDist;
	private Server server;
	private LinkedList<Double> requests;
	private int requestsSent = 0;
	
	
	public Simulador(int distribution, double mean, int duration, int repetitions) {
		this.repetitions = repetitions;
		this.distribution = distribution;
		this.mean = mean;
		setDist(distribution);
		this.duration = duration;		
		this.server = new Server(mean);
		this.requests = new LinkedList<>();
	}
	
	public long totalTime(long timeInit, long timeFinish) {
		return timeFinish - timeInit ;
	}
	
	public void simulate() throws InterruptedException {
		int total = 0;	
		for (int i = 0; i < repetitions; i++) {
			server = new Server(mean);
			requests = new LinkedList();
			requestsSent = 0;
			
			for (int presentTime = 0; presentTime < duration; presentTime++) {
				double requestTime = presentTime + Math.floor(getGenerator().sample());
				if (requests.size() < 2) {
					requests.add(requestTime);
//					System.out.println(String.format("%d %s", presentTime, requests.toString()));
				}
				
				
				final int paramTime = presentTime;
				Predicate<Double> isCurrentTime = t -> t == paramTime;
				boolean elementsRemoved = requests.removeIf(isCurrentTime);
				if (elementsRemoved) {
					server.receivesRequest(presentTime);
					requestsSent++;
				}
				server.clock(presentTime);	
			}
			System.out.println(
					String.format("%s;%.2f;%d;%d;%d;%.2f;%.2f", 
							nameDist(distribution), 
							mean, 
							duration, 
							requestsSent, 
							server.totalServed() , 
							server.avgServiceTime(), 
							server.avgWaitingCount(duration)));
		}
	}
	

	private void setDist(int distribution) {
		switch (distribution) {
			case NORMAL_DIST: 	realDist = new NormalDistribution(mean, mean*.3); 			break;
			case EXP_DIST: 		realDist = new ExponentialDistribution(mean); 	break;
			case UNIFORM_DIST: 	realDist = new UniformRealDistribution(mean*.3, mean*1.6);    	break;
		}
	}
	
	private String nameDist(int distribution) {
		switch(distribution) {
			case 1: return "Normal - mean 25 - sd 25*.3";
			case 2: return "Exponencial - mean 25";
			case 3: return "Uniforme - lower 25*.3, upper 25*1.6";
		}
		return "";
	}
	
	private AbstractRealDistribution getGenerator() { return realDist; }

	private void sendRequestToServer() {
		
	}

	// Considere o trecho de código abaixo independente da classe acima.
	
	public static void main(String[] args) throws InterruptedException {

		int repetitions = 20;
		int duration = 6000;
		
		int[] distributions = {Simulador.EXP_DIST, Simulador.NORMAL_DIST, Simulador.UNIFORM_DIST};
		System.out.println(
				String.format("distname;mean;duration;reqsent;reqserved;avgSvcTime;avgWaitingCount")	
			);
		for (int dist: distributions) {
			Simulador simulador = new Simulador(dist, 25, duration, repetitions);
			simulador.simulate();
		}
	}
}
