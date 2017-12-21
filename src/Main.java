import java.io.FileWriter;
import java.io.IOException;
import java.util.PriorityQueue;

public class Main {
	public static void main(String[] args) {
		Simulador sim = new Simulador(new PriorityQueue<>(), 10, 5, 2, new double[]{0, 0.4}, TipoDistribuicao.NORMAL);
		
		try {
			FileWriter fw = new FileWriter("resultados.txt");
			fw.write(sim.run());
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
}
