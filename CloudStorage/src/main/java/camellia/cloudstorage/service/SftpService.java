package camellia.cloudstorage.service;

import com.jcraft.jsch.ChannelSftp;

import java.util.List;

/**
 * @Datetime: 2024/9/28上午11:57
 * @author: Camellia.xioahua
 */
public interface SftpService {

    /**
     * 获取sftp连接客户端。
     */
    void traverseDirectory(ChannelSftp sftpChannel, String remoteFolderPath,List<String> fileNames);


}
