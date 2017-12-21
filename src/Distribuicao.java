import org.apache.commons.math3.distribution.*;
public class Distribuicao {

	public TipoDistribuicao tipo;
	public double[] params;
	
	public Distribuicao(TipoDistribuicao tipo, double[] params) {
		this.tipo = tipo;
		this.params = params;
	}

	public double gerar(){
		double dist = 0;
		if (this.tipo.equals(TipoDistribuicao.UNIFORME)){
			dist = new UniformRealDistribution(params[0], params[1]).sample();
		} else if(this.tipo.equals(TipoDistribuicao.EXPONENCIAL)){
			dist = (int) new ExponentialDistribution(params[0]).sample();
		}else if(this.tipo.equals(TipoDistribuicao.NORMAL)){
			dist = (int) new NormalDistribution(this.params[0], this.params[1]).sample();
		}
		return dist;
	}
	
	public String getTipo(){
		String retorno = "";
		if (this.tipo.equals(TipoDistribuicao.UNIFORME)){
			retorno = "uniforme";
		} else if(this.tipo.equals(TipoDistribuicao.EXPONENCIAL)){
			retorno = "exponencial";
		}else if(this.tipo.equals(TipoDistribuicao.NORMAL)){
			retorno = "normal";
		}
		return retorno;
	}
}
