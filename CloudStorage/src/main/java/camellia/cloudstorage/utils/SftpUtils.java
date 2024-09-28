package camellia.cloudstorage.utils;

import camellia.cloudstorage.enums.SftpEnum;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import lombok.extern.slf4j.Slf4j;

/**
 * @Datetime: 2024/9/28下午1:48
 * @author: Camellia.xioahua
 */
@Slf4j
public class SftpUtils {

    private static ThreadLocal<ChannelSftp> sftpChannel = new ThreadLocal<>();
    private static ThreadLocal<Session> session = new ThreadLocal<>();

    private SftpUtils() {}

    public static ChannelSftp openClient() {
        String userName = SftpEnum.USERNAME.getValue();
        String host = SftpEnum.HOST.getValue();
        int port = Integer.parseInt(SftpEnum.PORT.getValue());
        String password = SftpEnum.PASSWORD.getValue();
        try {
            JSch jsch = new JSch();
            Session sess = jsch.getSession(userName, host, port);
            sess.setPassword(password);
            sess.setConfig("StrictHostKeyChecking", "no");
            sess.connect();
            ChannelSftp channel = (ChannelSftp) sess.openChannel("sftp");
            channel.connect();
            session.set(sess);
            sftpChannel.set(channel);
            log.info("连接成功");
            return channel;
        } catch (JSchException e) {
            log.error("连接失败", e);
            throw new RuntimeException("SFTP连接失败", e);
        }
    }

    public static void closeClient() {
        ChannelSftp channel = sftpChannel.get();
        if (channel != null) {
            channel.disconnect();
            session.get().disconnect();
            sftpChannel.remove();
            session.remove();
        }
    }
}
