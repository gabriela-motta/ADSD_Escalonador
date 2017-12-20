import java.util.PriorityQueue;
import java.util.Queue;

public class Escalonador {

	private Queue<Fregues> fila;
	private double tempoMedioAtendendo;
	private int reqAtendidas;
	private Servidor servidor;
	private Distribuicao dist;

	public Escalonador(double tempoMedioServico, Servidor servidor) {
		this.fila = new PriorityQueue<Fregues>();
		this.tempoMedioAtendendo = 0;
		this.reqAtendidas = 0;
		this.servidor = servidor;
		double[] params = new double[1];
		params[0] = tempoMedioServico;
		this.dist = new Distribuicao(TipoDistribuicao.EXPONENCIAL, params);
	}
	
	public boolean filaIsEmpty() {
		return this.fila.isEmpty();
	}
	
	public void enfileirar(Fregues freg) {
		this.fila.add(freg);
	}
	
	public double atenderFregues(){
		Fregues freg = this.fila.poll();
		return this.escalonar(freg);
	}
	
	public double escalonar(Fregues fregues){
		this.servidor.ocupar();
		double tempoTermino = this.dist.gerar();
		fregues.finalizar(tempoTermino + System.currentTimeMillis());
		double tempoAtendido = fregues.getTempoAtendido();
		this.tempoMedioAtendendo = (this.tempoMedioAtendendo * (this.reqAtendidas - 1) + tempoAtendido) / (this.reqAtendidas + 1);
		return tempoTermino + System.currentTimeMillis();
	}
}
