import java.util.PriorityQueue;
import java.util.Queue;

public class Escalonador {

	private Queue<Fregues> fila;
	private int tempoTotalAtendendo;
	private int reqAtendidas;
	private int quantMediaFila;
	private Servidor servidor;
	private Distribuicao dist;

	public Escalonador(int tempoMedioServico, Servidor servidor, double[] params) {
		this.fila = new PriorityQueue<Fregues>();
		this.tempoTotalAtendendo = 0;
		this.reqAtendidas = 0;
		this.quantMediaFila = 0;
		this.servidor = servidor;
		this.dist = new Distribuicao(TipoDistribuicao.NORMAL, params);
	}

	public boolean filaIsEmpty() {
		return this.fila.isEmpty();
	}

	public void enfileirar(Fregues freg) {
		this.fila.add(freg);
	}

	public double atenderFregues(int momento) {
		Fregues freg = this.fila.poll();
		return this.escalonar(freg, momento);
	}

	public double escalonar(Fregues fregues, int momento) {
		this.servidor.ocupar();
		this.reqAtendidas++;
		int tempoTermino = (int) this.dist.gerar();
		fregues.finalizar((int) (tempoTermino + momento));
		int tempoAtendido = fregues.getTempoAtendido();
		this.tempoTotalAtendendo += tempoAtendido;
		return tempoTermino + momento;
	}

	public int getReqAtendidas() {
		return this.reqAtendidas;
	}

	public int getTempoMedio() {
		return this.tempoTotalAtendendo/this.reqAtendidas;
	}

	public int getQuantMediaFila(int momento) {
		return (this.quantMediaFila * (momento) + this.fila.size() ) / (momento + 1);
	}
}
