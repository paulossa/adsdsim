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
	
	public long totalTime(long timeInit, long timeFinish) {
		return timeFinish - timeInit ;
	}
	
	public void simulate() throws InterruptedException {
		setStartTime(); //tempo inicial da execução do programa
		int total = 0;	
		for (int i = 0; i < repetitions; i++) {
			long startTimeRepetiton = new Date().getTime(); // tempo inicial de cada repetição
			int count = 0;
			while ((getCurrentTime() - startTimeRepetiton <= this.duration)) {
//				sendConsumer();
				System.out.println((getGenerator().sample() * 1000));
//				Thread.sleep( (long) (getGenerator().sample() * 1000));
				count++;	
			}
			total += count;
			System.out.println("---------------------- " + "Tipo da distribuição: " + nameDist(distribution));
			System.out.println("---------------------- " + "Parâmentro da distribuição: " + mean);
			System.out.println("---------------------- " + "Repetição: " + i); // qual repetição está
			System.out.println("---------------------- " + "Tempo da repetição: " + (getCurrentTime() - startTimeRepetiton));
			System.out.println("---------------------- " + "Tempo médio de atendimento - repetição: " + ((float)(getCurrentTime() - startTimeRepetiton)/count)); //tentar usando bigdecimal
			System.out.println("---------------------- " + "Quantidade de elementos por repetição: " + count); // imprime quantidade por repetição
			
		}
		System.out.println("\nTotal de elementos: " + total); // imprime o total de elementos das x repetições
		System.out.println("Tempo total: " + totalTime(this.startTime, getCurrentTime())); //imprime o tempo total somado as x repetições
		System.out.println("Tempo médio de atendimento - total: " + (float)(getCurrentTime() - this.startTime)/total);
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
	
	private String nameDist(int distribution) {
		switch(distribution) {
		case 1: return "Normal";
		case 2: return "Exponencial";
		case 3: return "Uniforme";
		}
		return "";
	}
	
	private AbstractRealDistribution getGenerator() { return realDist; }

	private void sendConsumer() {
		
	}

	// Considere o trecho de código abaixo independente da classe acima.
	
	public static void main(String[] args) throws InterruptedException {

//		UniformIntegerDistribution	// FIX constructor
//		UniformRealDistribution	// FIX constructor


		
		
		Simulador simulador = new Simulador(Simulador.EXP_DIST, 0.5, 3, 3);
		simulador.simulate();
		
		
		System.out.println("Hello bitch");
		
		

	}
}
