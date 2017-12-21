import java.util.PriorityQueue;
import java.util.Queue;

public class Escalonador {

	private Queue<Fregues> fila;
	private int tempoMedioAtendendo;
	private int reqAtendidas;
	private int quantMediaFila;
	private Servidor servidor;
	private Distribuicao dist;

	public Escalonador(int tempoMedioServico, Servidor servidor, double[] params) {
		this.fila = new PriorityQueue<Fregues>();
		this.tempoMedioAtendendo = 0;
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
		this.tempoMedioAtendendo = (int) ((this.tempoMedioAtendendo * (this.reqAtendidas - 1)) + tempoAtendido) / (this.reqAtendidas);
		return tempoTermino + momento;
	}

	public int getReqAtendidas() {
		return this.reqAtendidas;
	}

	public int getTempoMedio() {
		return this.tempoMedioAtendendo;
	}

	public int getQuantMediaFila(int momento) {
		return (this.quantMediaFila * (momento) + this.fila.size() ) / (momento + 1);
	}
}
