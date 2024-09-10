package networklatency.config;

import networklatency.controller.LatencyWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final LatencyWebSocketHandler latencyWebSocketHandler;

    public WebSocketConfig(LatencyWebSocketHandler latencyWebSocketHandler) {
        this.latencyWebSocketHandler = latencyWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 注册 WebSocket 处理器，前端通过 ws://localhost:8080/latency-ws 连接
        registry.addHandler(latencyWebSocketHandler, "/latency-ws").setAllowedOrigins("*");
    }
}
