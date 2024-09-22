package networklatency.service.impl;

import networklatency.controller.LatencyWebSocketHandler;
import networklatency.service.NetworkMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;

@Service
public class NetworkMonitorServiceImpl implements NetworkMonitorService {

    @Autowired
    private LatencyWebSocketHandler latencyWebSocketHandler;

    @Override
    @Scheduled(fixedRate = 500) // 每2秒执行一次
    public Long pingDevice() {
        String host = "www.bilibili.com";
        try {
            InetAddress address = InetAddress.getByName(host);
            long startTime = System.currentTimeMillis();
            boolean reachable = address.isReachable(2000); // 2秒超时
            long endTime = System.currentTimeMillis();
            long latency = endTime - startTime;

            if (reachable) {
                System.out.println("Ping成功，延迟时间: " + latency + " 毫秒");
            } else {
                System.out.println("无法到达 " + host);
            }

            // 推送 ping 延迟数据
            latencyWebSocketHandler.broadcastLatency("ping", latency);
            return latency;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1L;
    }

    @Override
    @Scheduled(fixedRate = 500) // 每2秒执行一次
    public Long checkHttpLatency() {
        String urlString = "https://www.bilibili.com";
        try {
            URL url = new URL(urlString);
            long startTime = System.currentTimeMillis();

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(2000); // 2秒连接超时
            connection.setReadTimeout(2000);    // 2秒读取超时
            connection.connect();
            int responseCode = connection.getResponseCode();
            long endTime = System.currentTimeMillis();
            long latency = endTime - startTime;

            if (responseCode == 200) {
                System.out.println("请求成功，HTTP 延迟时间: " + latency + " 毫秒");
            } else {
                System.out.println("请求失败，响应码: " + responseCode);
            }
            connection.disconnect();

            // 推送 HTTP 延迟数据
            latencyWebSocketHandler.broadcastLatency("http", latency);
            return latency;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1L;
    }
}
