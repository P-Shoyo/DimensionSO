package mensageria;

import br.com.dimension.insercao.Insercao;
import br.com.dimension.insercao.Log;
import java.io.IOException;
import mensageria.ConexaoSlack;
import org.json.JSONObject;

public class AppSlack {
    public void appSlack(Insercao insercao) throws IOException, InterruptedException {
        JSONObject json = new JSONObject();
        Log log = new Log();
        
 json.put("text","Computador em estado critico devido ao componente "+ insercao.getNomeComponente()+" pois seu "
         + "uso está em "+Math.round(insercao.getDadosColetados()));
 ConexaoSlack.sendMessage(json);
        
        try {
            ConexaoSlack.sendMessage(json);
        } catch (Exception e) {
            System.out.println("Erro ao enviar para o Slack!");
            log.criarLog("_Slack","Médio", e);
        }
        
    }
}
