package simulador;

import org.apache.commons.math3.distribution.*;

public class Simulador {
	
	static final int NORMAL_DIST	= 1;
	static final int EXP_DIST 		= 2;
	static final int UNIFORM_DIST 	= 3;

	private int distribution;
	private int repetitions, duration;
	private double mean;
	private AbstractRealDistribution realDist;
	
	
	public Simulador(int distribution, double mean, int duration, int repetitions) {
		this.repetitions = repetitions;
		this.distribution = distribution;
		setDist(distribution);
		this.mean = mean; 
		this.duration = duration;		
	}
	
	public void simulate() {
		sendConsumer();
//		Thread.sleep(Math.round(getGenerator().sample()));
	}
	

	private void setDist(int distribution) {
		switch (distribution) {
		case NORMAL_DIST:
			realDist = new NormalDistribution();
			break;
		case EXP_DIST:
			realDist = new ExponentialDistribution(mean);
			break;
		case UNIFORM_DIST:
			realDist = new UniformRealDistribution();
			break;
		}
	}
	
	private AbstractRealDistribution getGenerator() {
		return realDist;
	}

	private void sendConsumer() {
		// TODO Auto-generated method stub
		
	}

	// Considere o trecho de código abaixo independente da classe acima.
	
	public static void main(String[] args) {

//		UniformIntegerDistribution	
//		Implementation of the uniform integer distribution.
//		UniformRealDistribution	
//		Implementation of the uniform real distribution.
		
//		ExponentialDistribution	
//		Implementation of the exponential distribution.
//		org.apache.commons.math3.distribution.NormalDistribution
		
		System.out.println("Hello bitch");
		
		

	}
}
