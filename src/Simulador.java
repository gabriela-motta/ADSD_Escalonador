import java.util.Queue;

public class Simulador {

	private int quantRepeticoes;        
	private int duracaoSimulacao;
	private int tempoMedioServico;
	private int reqRecebidas;
	private Distribuicao dist;
	private int momento;
	private double[] paramsDistribuicao;
	
	
	public Simulador(Queue<Fregues> fila, int quantRepeticoes, int duracaoSimulacao, int tempoMedioServico,
			double[] paramsDistribuicao, TipoDistribuicao tipoDistribuicaoChegada) {
		this.quantRepeticoes = quantRepeticoes;
		this.duracaoSimulacao = duracaoSimulacao;
		this.tempoMedioServico = tempoMedioServico;
		this.reqRecebidas = 0;
		this.dist = new Distribuicao(tipoDistribuicaoChegada, paramsDistribuicao);
		this.momento = 0;
		this.paramsDistribuicao = paramsDistribuicao;
	}
	
	public String run(){
		String resultados = "";
		for(int i = 0; i<this.quantRepeticoes; i++){
			this.momento = 0;
			Servidor serv = new Servidor();
			Escalonador esc = new Escalonador(this.tempoMedioServico, serv, this.paramsDistribuicao);
			double tempoInicio = this.momento;
			int proximaChegada = (int) (this.momento + this.dist.gerar());
			int proximoTermino = 0;
			while(!this.fimExecucao(tempoInicio)){
				if(this.momento >= proximoTermino){
					serv.liberar();
				}
				if(serv.isLivre()){
					if(!esc.filaIsEmpty()){
						proximoTermino = (int) esc.atenderFregues(this.momento);
					}else if(this.chegouFregues(proximaChegada)){
						Fregues freg = new Fregues();
						proximaChegada = (int) (this.momento +  this.dist.gerar());
						proximoTermino = (int) esc.escalonar(freg, this.momento);
						this.momento++;
					}
				}else{
					if(this.chegouFregues(proximaChegada)){
						Fregues freg = new Fregues();
						esc.enfileirar(freg);
					}
				}
			}
			resultados += "Distribuicao de chegada: " + this.dist.getTipo() + "\n"
			        + "Parametros: " + this.paramsDistribuicao[0] + " " + this.paramsDistribuicao[1] + "\n"
					+ "Valor medio servi�o: " + this.tempoMedioServico + "\n"
					+ "Duracao da simulacao: " + this.duracaoSimulacao + "\n"
					+ "Quantidade de Requisicoes recebidas: " + this.reqRecebidas + "\n"
					+ "Quantidade de Requisicoes atendidas: " + esc.getReqAtendidas() + "\n"
					+ "Tempo medio de atendimento: " + esc.getTempoMedio() + "\n"
					+ "Quantidade media de elementos em espera: " + esc.getQuantMediaFila(momento) + "\n" + "\n";
		}
		return resultados;
	}
	
	public boolean fimExecucao(double tempoInicio){
		double delta = this.momento - tempoInicio;
		return delta > this.duracaoSimulacao;
	}

	public boolean chegouFregues(int proximaChegada){
		if(this.momento >= proximaChegada){
			this.reqRecebidas++;
			return true;
		}
		return false;
	}
}
