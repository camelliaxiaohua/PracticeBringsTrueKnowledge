package networklatency.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@Component
public class LatencyWebSocketHandler extends TextWebSocketHandler {

    private List<WebSocketSession> sessions = new ArrayList<>();
    private ObjectMapper objectMapper = new ObjectMapper(); // 用于将数据转换为 JSON 格式

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }


    public void afterConnectionClosed(WebSocketSession session, boolean status) throws Exception {
        sessions.remove(session);
    }

    // 将延迟数据广播给所有客户端
    public void broadcastLatency(String type, long latency) {
        try {
            String message = objectMapper.writeValueAsString(new LatencyMessage(type, latency));
            for (WebSocketSession session : sessions) {
                session.sendMessage(new TextMessage(message));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 封装延迟数据的类
    static class LatencyMessage {
        private String type;
        private long latency;

        public LatencyMessage(String type, long latency) {
            this.type = type;
            this.latency = latency;
        }

        public String getType() {
            return type;
        }

        public long getLatency() {
            return latency;
        }
    }
}
