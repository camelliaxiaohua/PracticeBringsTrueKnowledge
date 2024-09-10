package networklatency;

import java.net.InetAddress;

public class PingTest {
    public static void main(String[] args) {
        while (true) {
            try {
                String ipAddress = "www.bilibili.com"; // 要ping的主机地址
                InetAddress inet = InetAddress.getByName(ipAddress); // 将主机名转换为IP地址
                long startTime = System.currentTimeMillis(); // 记录开始时间
                boolean reachable = inet.isReachable(5000); // 使用Ping操作，超时设为5秒
                long endTime = System.currentTimeMillis(); // 记录结束时间
                if (reachable) {
                    System.out.println("Ping成功，延迟时间: " + (endTime - startTime) + " 毫秒");
                } else {
                    System.out.println("Ping失败，主机不可达");
                }
            } catch (Exception e) {
                e.printStackTrace(); // 捕获异常并打印堆栈信息
            }
        }
    }
}
