import java.util.Queue;

public class Simulador {

	private int qnt_repeticoes;        
	private double duracao_simulacao;
	private double tempo_medio_servico;
	private int reqRecebidas;
	private Distribuicao dist;
	
	
	public Simulador(Queue<Fregues> fila, int qnt_repeticoes, double duracao_simulacao, double tempo_medio_servico,
			double[] params_distribuicao, TipoDistribuicao tipo_distribuicao_chegada, int req_recebidas, int req_atendidas) {
		this.qnt_repeticoes = qnt_repeticoes;
		this.duracao_simulacao = duracao_simulacao;
		this.tempo_medio_servico = tempo_medio_servico;
		this.reqRecebidas = 0;
		this.dist = new Distribuicao(tipo_distribuicao_chegada, params_distribuicao);
	}
	
	public void run(){
		for(int i = 0; i<this.qnt_repeticoes; i++){
			Servidor serv = new Servidor();
			Escalonador esc = new Escalonador(this.tempo_medio_servico, serv);
			double tempoInicio = System.currentTimeMillis();
			int proximaChegada = (int) (System.currentTimeMillis() + this.dist.gerar());
			int proximoTermino = 0;
			while(!this.fimExecucao(tempoInicio)){
				System.out.println(System.currentTimeMillis() - tempoInicio + " " + this.reqRecebidas);
				if(System.currentTimeMillis() >= proximoTermino){
					serv.liberar();
				}
				if(serv.isLivre()){
					if(!esc.filaIsEmpty()){
						proximoTermino = (int) esc.atenderFregues();
						System.out.println("free, prox termino: " + (proximoTermino - tempoInicio));
					}else if(this.chegouFregues(proximaChegada)){
						Fregues freg = new Fregues();
						proximaChegada = (int) (System.currentTimeMillis() +  this.dist.gerar());
						proximoTermino = (int) esc.escalonar(freg);
						System.out.println("free, chegou fregues! prox termino: " + (proximoTermino - tempoInicio) + "prox chegada: " + (proximaChegada - tempoInicio));
					}
				}else{
					if(this.chegouFregues(proximaChegada)){
						Fregues freg = new Fregues();
						esc.enfileirar(freg);
						System.out.println("not free, enfileirar cliente");
					}
				}
			}
		}
	}
	
	public boolean fimExecucao(double tempoInicio){
		double delta = System.currentTimeMillis() - tempoInicio;
		return delta > this.duracao_simulacao;
	}

	public boolean chegouFregues(int proximaChegada){
		if(System.currentTimeMillis() >= proximaChegada){
			this.reqRecebidas++;
			return true;
		}
		return false;
	}
}
