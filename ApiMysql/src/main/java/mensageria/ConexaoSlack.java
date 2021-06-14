package mensageria;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;
import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.github.britooo.looca.api.group.processos.Processo;

public class ConexaoSlack {
    private Slack slack;
    private String token;
    private MethodsClient methods;
    private PostMessageService messageService;

    public ConexaoSlack() {
        this.slack = Slack.getInstance();
        this.token = "xoxb-2074434693523-2074492198403-D0vfiU9towayh0DlnE7ytcgb";
        this.methods = slack.methods(token);
        this.messageService = new PostMessageService();
    }
    
    public void mensagem(Processo processo) {
        try {
            this.messageService.sendMessage(methods, "C022JR0GNUR", String.format("Atenção, sua máquina atingiu o nivel de: %.2f de CPU e %.2f de RAM",
                    processo.getUsoCpu(),processo.getUsoMemoria()));
        } catch (Exception e) { }
    }
    
    
    }

