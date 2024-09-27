package camellia.cloudstorage;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class SFTPFileReader {

    // 方法返回指定目录中所有文件的文件名
    public static List<String> getFileNamesFromSFTP(String host, int port, String username, String password, String remoteFolderPath) {
        List<String> fileNames = new ArrayList<>();

        try {
            // 创建 JSch 对象
            JSch jsch = new JSch();
            Session session = jsch.getSession(username, host, port);
            session.setPassword(password);

            // 跳过主机密钥检查
            session.setConfig("StrictHostKeyChecking", "no");

            // 连接到服务器
            session.connect();

            // 打开 SFTP 通道
            ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
            sftpChannel.connect();

            // 递归遍历文件夹
            traverseDirectory(sftpChannel, remoteFolderPath, fileNames);

            // 关闭 SFTP 通道和会话
            sftpChannel.disconnect();
            session.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileNames;
    }

    // 递归遍历指定目录
    private static void traverseDirectory(ChannelSftp sftpChannel, String remoteFolderPath, List<String> fileNames) {
        try {
            // 列出远程文件夹中的所有文件和目录
            Vector<ChannelSftp.LsEntry> fileList = sftpChannel.ls(remoteFolderPath);
            for (ChannelSftp.LsEntry entry : fileList) {
                String fileName = entry.getFilename();

                // 跳过 "." 和 ".." 目录
                if (".".equals(fileName) || "..".equals(fileName)) {
                    continue;
                }

                // 如果是目录，递归进入子目录
                if (entry.getAttrs().isDir()) {
                    System.out.println("Entering directory: " + remoteFolderPath + fileName);
                    traverseDirectory(sftpChannel, remoteFolderPath + fileName + "/", fileNames);
                } else {
                    fileNames.add(remoteFolderPath + fileName);  // 添加完整路径
                    System.out.println("Found file: " + remoteFolderPath + fileName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // SFTP 服务器的配置信息
        String host = "192.168.110.130";
        int port = 22;
        String username = "root";
        String password = "24211";
        String remoteFolderPath = "/var/tmp/";

        // 调用方法获取文件名列表
        List<String> fileNames = getFileNamesFromSFTP(host, port, username, password, remoteFolderPath);

        if (!fileNames.isEmpty()) {
            System.out.println("Successfully retrieved file names from the SFTP server:");
            for (String fileName : fileNames) {
                System.out.println(fileName);
            }
        } else {
            System.out.println("No files found or failed to retrieve the file names.");
        }
    }
}
