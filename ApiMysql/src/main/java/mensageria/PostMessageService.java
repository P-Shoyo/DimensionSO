package mensageria;

import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import javax.swing.JOptionPane;

class PostMessageService {
    public ChatPostMessageResponse sendMessage(MethodsClient methods, String channelId, String message) {
      ChatPostMessageRequest request = ChatPostMessageRequest.builder()
        .channel(channelId)
        .text(message)
        .build();
      try {
        ChatPostMessageResponse response = methods.chatPostMessage(request);
        return response;
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro ao enviar a mensagem via API slack. " + e.toString());
        return null;
      }
    }
}
