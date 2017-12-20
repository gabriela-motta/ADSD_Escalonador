import java.util.PriorityQueue;
import java.util.Queue;

public class Escalonador {

	private Queue<Fregues> fila;
	private int tempoMedioAtendendo;
	private int reqAtendidas;
	private Servidor servidor;
	private Distribuicao dist;

	public Escalonador(int tempoMedioServico, Servidor servidor) {
		this.fila = new PriorityQueue<Fregues>();
		this.tempoMedioAtendendo = 0;
		this.reqAtendidas = 0;
		this.servidor = servidor;
		int[] params = new int[1];
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
		int tempoTermino = this.dist.gerar();
		fregues.finalizar((int) (tempoTermino + System.currentTimeMillis()));
		int tempoAtendido = fregues.getTempoAtendido();
		this.tempoMedioAtendendo = (int) ((this.tempoMedioAtendendo * (this.reqAtendidas - 1) + tempoAtendido) / (this.reqAtendidas + 1));
		return tempoTermino + System.currentTimeMillis();
	}
}
