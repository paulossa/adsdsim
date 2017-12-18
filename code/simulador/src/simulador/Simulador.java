package simulador;

import org.apache.commons.math3.distribution.*;
import java.util.Date;

public class Simulador {
	
	static final int NORMAL_DIST	= 1;
	static final int EXP_DIST 		= 2;
	static final int UNIFORM_DIST 	= 3;

	private int distribution;
	private int repetitions, duration;
	private double mean;
	private AbstractRealDistribution realDist;
	private long startTime;
	private Server server;
	
	
	public Simulador(int distribution, double mean, int duration, int repetitions) {
		this.repetitions = repetitions;
		this.distribution = distribution;
		this.mean = mean;
		setDist(distribution);
		this.duration = duration;		
		this.startTime = new Date().getTime();
		this.server = new Server(mean);
	}
	
	private long getCurrentTime() {
		return new Date().getTime();
	}
	
	private void setStartTime() {
		this.startTime = new Date().getTime();
	}
	
	public void simulate() throws InterruptedException {
		setStartTime();
		for (int i = 0; i < repetitions; i++) {
			while ((getCurrentTime() - this.startTime <= this.duration)) {
//				sendConsumer();
				System.out.println((getGenerator().sample() * 1000));
//				Thread.sleep( (long) (getGenerator().sample() * 1000));
				
			}
		}
	}
	

	private void setDist(int distribution) {
		switch (distribution) {
		case NORMAL_DIST: 	realDist = new NormalDistribution();
			break;
		case EXP_DIST:
			System.out.println("mean = " + mean);
			realDist = new ExponentialDistribution(mean);
			break;
		case UNIFORM_DIST: 	realDist = new UniformRealDistribution();
			break;
		}
	}
	
	private AbstractRealDistribution getGenerator() { return realDist; }

	private void sendConsumer() {
		
	}

	// Considere o trecho de código abaixo independente da classe acima.
	
	public static void main(String[] args) throws InterruptedException {

//		UniformIntegerDistribution	// FIX constructor
//		UniformRealDistribution	// FIX constructor


		
		
		Simulador simulador = new Simulador(Simulador.EXP_DIST, .5, 3000, 30);
		simulador.simulate();
		
		
		
		System.out.println("Hello bitch");
		
		

	}
}
