public class Simulador {

	private int quantRepeticoes;        
	private int duracaoSimulacao;
	private double tempoMedioServico;
	private int reqRecebidas;
	private Distribuicao dist;
	private double momento;
	private double[] paramsDistribuicao;
	private TipoDistribuicao tipoDistribuicaoChegada;
	
	
	public Simulador(int quantRepeticoes, int duracaoSimulacao, double tempoMedioServico,
			double[] paramsDistribuicao, TipoDistribuicao tipoDistribuicaoChegada) {
		this.quantRepeticoes = quantRepeticoes;
		this.duracaoSimulacao = duracaoSimulacao;
		this.tempoMedioServico = tempoMedioServico;
		this.reqRecebidas = 0;
		this.dist = new Distribuicao(tipoDistribuicaoChegada, paramsDistribuicao);
		this.momento = 0;
		this.paramsDistribuicao = paramsDistribuicao;
		this.tipoDistribuicaoChegada = tipoDistribuicaoChegada;
	}
	
	public String run(){
		String resultados = "";
		for(int i = 0; i<this.quantRepeticoes; i++){
			this.momento = 0;
			this.reqRecebidas = 0;
			Servidor serv = new Servidor();
			Escalonador esc = new Escalonador(this.tempoMedioServico, serv, this.paramsDistribuicao, this.tipoDistribuicaoChegada);
			double tempoInicio = this.momento;
			double proximaChegada =  (this.momento + this.dist.gerar());
			double proximoTermino = 0;
			while(!this.fimExecucao(tempoInicio)){
				if(this.momento >= proximoTermino){
					serv.liberar();
				}
				if(serv.isLivre()){
					if(!esc.filaIsEmpty()){
						serv.ocupar();
						proximoTermino =  esc.atenderFregues(this.momento);
					}else if(this.chegouFregues(proximaChegada)){
						Fregues freg = new Fregues(this.momento);
						proximaChegada =  Math.abs(this.momento +  this.dist.gerar());
						proximoTermino =  esc.escalonar(freg, this.momento);
					}
				}else{
					if(this.chegouFregues(proximaChegada)){
						Fregues freg = new Fregues(this.momento);
						esc.enfileirar(freg);
					}
				}
				this.momento++;
			}
			
			String parametros = "";
			for (double d : paramsDistribuicao) {
				parametros += d + " ";
			}
			
			resultados += "Distribuicao de chegada: " + this.dist.getTipo() + "\n"
			        + "Parametros: " + parametros  + "\n"
					+ "Valor medio serviço: " + this.tempoMedioServico + "\n"
					+ "Duracao da simulacao: " + this.duracaoSimulacao + "\n"
					+ "Quantidade de Requisicoes recebidas: " + this.reqRecebidas + "\n"
					+ "Quantidade de Requisicoes atendidas: " + esc.getReqAtendidas() + "\n"
					+ "Tempo medio de atendimento: " + esc.getTempoMedio() + "\n"
					+ "Quantidade media de elementos em espera: " + esc.getQuantMediaFila(this.momento) + "\n" + "\n";
		}
		return resultados;
	}
	
	public boolean fimExecucao(double tempoInicio){
		double delta = this.momento - tempoInicio;
		return delta > this.duracaoSimulacao;
	}

	public boolean chegouFregues(double proximaChegada){
		if(this.momento >= proximaChegada){
			this.reqRecebidas++;
			return true;
		}
		return false;
	}
}
