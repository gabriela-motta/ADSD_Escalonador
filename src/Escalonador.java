import java.util.PriorityQueue;
import java.util.Queue;

public class Escalonador {

	private Queue<Fregues> fila;
	private double tempoTotalAtendendo;
	private int reqAtendidas;
	private int quantMediaFila;
	private Servidor servidor;
	private Distribuicao dist;

	public Escalonador(double tempoMedioServico, Servidor servidor, double[] params, TipoDistribuicao tipoDist) {
		this.fila = new PriorityQueue<Fregues>();
		this.tempoTotalAtendendo = 0;
		this.reqAtendidas = 0;
		this.quantMediaFila = 0;
		this.servidor = servidor;
		this.dist = new Distribuicao(tipoDist, params);
	}

	public boolean filaIsEmpty() {
		return this.fila.isEmpty();
	}

	public void enfileirar(Fregues freg) {
		this.fila.add(freg);
	}

	public double atenderFregues(double momento) {
		Fregues freg = this.fila.poll();
		return this.escalonar(freg, momento);
	}

	public double escalonar(Fregues fregues, double momento) {
		this.servidor.ocupar();
		this.reqAtendidas++;
		double tempoTermino =  this.dist.gerar();
		fregues.finalizar( (tempoTermino + momento));
		double tempoAtendido = fregues.getTempoAtendido();
		this.tempoTotalAtendendo += tempoAtendido;
		return tempoTermino + momento;
	}

	public int getReqAtendidas() {
		return this.reqAtendidas;
	}

	public double getTempoMedio() {
		return Math.abs(this.tempoTotalAtendendo/this.reqAtendidas);
	}

	public double getQuantMediaFila(double momento) {
		return (this.quantMediaFila * (momento) + this.fila.size() ) / (momento + 1);
	}
}
