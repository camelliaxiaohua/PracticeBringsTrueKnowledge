package networklatency;

import java.net.HttpURLConnection;
import java.net.URL;

public class HttpPingByHttpURLConnection {
    public static void main(String[] args) {
        while (true) {
            try {
                String urlString = "https://www.bilibili.com"; // 要ping的URL
                URL url = new URL(urlString);

                long startTime = System.currentTimeMillis();

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000); // 设置连接超时5秒
                connection.setReadTimeout(5000);    // 设置读取超时5秒
                connection.connect();

                int responseCode = connection.getResponseCode();
                long endTime = System.currentTimeMillis();

                if (responseCode == 200) {
                    System.out.println("请求成功，延迟时间: " + (endTime - startTime) + " 毫秒");
                } else {
                    System.out.println("请求失败，响应码: " + responseCode);
                }

                connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
