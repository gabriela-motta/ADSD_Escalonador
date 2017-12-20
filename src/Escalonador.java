import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class Escalonador {

	private Queue fila;
	private Map agenda;
	private Long momento;
	private TipoDistribuicao tipoDistribuicao;
	private double[] paramsDistribuicao;
	private double tempoMedioServico;
	private double duracaoSimulacao;
	private int repeticoes;

	public Escalonador(Queue fila, TipoDistribuicao tipoDistribuicao, double[] params_distribuicao,
			double tempo_medio_servico, double duracao_simulacao, int repeticoes) {
		this.fila = fila;
		this.agenda = new HashMap();
		this.momento = System.currentTimeMillis();
		this.tipoDistribuicao = tipoDistribuicao;
		this.paramsDistribuicao = params_distribuicao;
		this.tempoMedioServico = tempo_medio_servico;
		this.duracaoSimulacao = duracao_simulacao;
		this.repeticoes = repeticoes;
	}

	public void escalonar(Evento evento, TipoDistribuicao tipo_dist) {
		Distribuicao dist = new Distribuicao(tipo_dist, tempoMedioServico, paramsDistribuicao);
		double tempo = System.currentTimeMillis() + dist.gerar();
		while (this.agenda.containsKey(tempo)) {
			tempo = System.currentTimeMillis() + dist.gerar();
		}
		this.agenda.put(tempo, evento);

	}

	public void enfileirar(Fregues freg) {
		this.fila.add(freg);
	}

	public boolean filaIsEmpty() {
		return this.fila.isEmpty();
	}

	public Fregues getFreg() {
		return (Fregues) this.fila.poll();
	}

	public Map getAgenda() {
		return this.agenda;
	}

	public TipoDistribuicao getTipoDistribuicao() {
		return this.tipoDistribuicao;
	}
}
