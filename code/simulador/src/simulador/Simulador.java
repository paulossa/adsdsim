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
			
			for (int presentTime = 0; presentTime < duration; presentTime++) {
				double requestTime = presentTime + Math.floor(getGenerator().sample());
				if (requests.size() < 2) {
					requests.add(requestTime);
					System.out.println(String.format("%d %s", presentTime, requests.toString()));
				}
				
				
				final int paramTime = presentTime;
				Predicate<Double> isCurrentTime = t -> t == paramTime;
				boolean elementsRemoved = requests.removeIf(isCurrentTime);
				if (elementsRemoved) {
					server.receivesRequest(presentTime);
					requestsSent++;
				}
				server.clock(presentTime);
//				count++;	
			}
			
			
			
//			total += count;
//			System.out.println("---------------------- " + "Tipo da distribuição: " + nameDist(distribution));
//			System.out.println("---------------------- " + "Parâmentro da distribuição: " + mean);
//			System.out.println("---------------------- " + "Repetição: " + i); // qual repetição está
//			System.out.println("---------------------- " + "Tempo da repetição: " + (getCurrentTime() - startTimeRepetiton));
//			System.out.println("---------------------- " + "Tempo médio de atendimento - repetição: " + ((float)(getCurrentTime() - startTimeRepetiton)/count)); //tentar usando bigdecimal
//			System.out.println("---------------------- " + "Quantidade de elementos por repetição: " + count); // imprime quantidade por repetição
			
		}
//		System.out.println("\nTotal de elementos: " + total); // imprime o total de elementos das x repetições
//		System.out.println("Tempo total: " + totalTime(this.startTime, getCurrentTime())); //imprime o tempo total somado as x repetições
//		System.out.println("Tempo médio de atendimento - total: " + (float)(getCurrentTime() - this.startTime)/server.something()?total);
	}
	

	private void setDist(int distribution) {
		switch (distribution) {
			case NORMAL_DIST: 	realDist = new NormalDistribution(); 			break;
			case EXP_DIST: 		realDist = new ExponentialDistribution(mean); 	break;
			case UNIFORM_DIST: 	realDist = new UniformRealDistribution();    	break;
		}
	}
	
	private String nameDist(int distribution) {
		switch(distribution) {
			case 1: return "Normal";
			case 2: return "Exponencial";
			case 3: return "Uniforme";
		}
		return "";
	}
	
	private AbstractRealDistribution getGenerator() { return realDist; }

	private void sendRequestToServer() {
		
	}

	// Considere o trecho de código abaixo independente da classe acima.
	
	public static void main(String[] args) throws InterruptedException {

		int repetitions = 20;
		int duration = 5000;
		
		Simulador simulador = new Simulador(Simulador.EXP_DIST, 25, duration, repetitions);
		simulador.simulate();
		System.out.println("END");
	}
}
