import java.util.Map;
import java.util.Queue;

public class Simulador {

	private int qnt_repeticoes;        
	private double duracao_simulacao;
	private double tempo_medio_servico;
	private double[] params_distribuicao;
	private TipoDistribuicao tipo_distribuicao_chegada;
	private Servidor servidor;
	private Escalonador escalonador;
	private int req_recebidas;
	private int req_atendidas;
	
	
	public Simulador(Queue fila, int qnt_repeticoes, double duracao_simulacao, double tempo_medio_servico,
			double[] params_distribuicao, TipoDistribuicao tipo_distribuicao_chegada, int req_recebidas, int req_atendidas) {
		this.qnt_repeticoes = qnt_repeticoes;
		this.duracao_simulacao = duracao_simulacao;
		this.tempo_medio_servico = tempo_medio_servico;
		this.params_distribuicao = params_distribuicao;
		this.tipo_distribuicao_chegada = tipo_distribuicao_chegada;
		this.servidor = new Servidor();
		this.escalonador = new Escalonador(fila, tipo_distribuicao_chegada, params_distribuicao, tempo_medio_servico, duracao_simulacao, qnt_repeticoes);
		this.req_recebidas = 0;
		this.req_atendidas = 0;
	}
	
	public void chegada(){
		req_recebidas++;
		Fregues freg = new Fregues();
		Evento eventoChegada = new Evento(TipoEvento.CHEGADA, freg);
		this.escalonador.escalonar(eventoChegada, tipo_distribuicao_chegada);
		if (this.servidor.isLivre()){
			this.servidor.ocupar();
			Evento eventoTermino = new Evento(TipoEvento.TERMINO, freg);
			this.escalonador.escalonar(eventoTermino, this.escalonador.getTipoDistribuicao());
		}else{
			Evento eventoEnfileirar = new Evento(TipoEvento.ENFILEIRAR, freg);
			this.escalonador.escalonar(eventoEnfileirar, this.escalonador.getTipoDistribuicao());
		}
	}
	
	public void termino(Fregues freg){
		this.req_atendidas++;
		freg.finalizar();
		if(this.escalonador.filaIsEmpty()){
			this.servidor.liberar();
		}else{
			Fregues fregFila = this.escalonador.getFreg();
			Evento eventoTermino = new Evento(TipoEvento.TERMINO, fregFila);
			this.escalonador.escalonar(eventoTermino, this.escalonador.getTipoDistribuicao());
		}
	}
	
	public void enfileirar(Fregues freg){
		this.escalonador.enfileirar(freg);
	}
	
	public void start(){
		Map<Long, Evento> agenda = this.escalonador.getAgenda();
		while(){
			Long ms = System.currentTimeMillis();
			if (agenda.containsKey(ms)){
				if(agenda.get(ms).equals(TipoEvento.CHEGADA)){
					this.chegada();
				}else if(agenda.get(ms).equals(TipoEvento.TERMINO)){
					this.termino(agenda.get(ms).getFregues());
				}else if(agenda.get(ms).equals(TipoEvento.ENFILEIRAR)){
					this.enfileirar(agenda.get(ms).getFregues());
				}
			}
		}
	}
}
