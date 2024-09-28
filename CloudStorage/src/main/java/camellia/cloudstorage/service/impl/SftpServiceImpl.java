package camellia.cloudstorage.service.impl;

import camellia.cloudstorage.service.SftpService;


import com.jcraft.jsch.ChannelSftp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Vector;

/**
 * @Datetime: 2024/9/28上午11:57
 * @author: Camellia.xioahua
 */
@Service
@Slf4j
public class SftpServiceImpl implements SftpService {


    /**
     * 遍历远程目录
     * @param sftpChannel
     * @param remoteFolderPath
     * @param fileNames
     * @return 遍历到的文件。
     */
    @Override
    public void traverseDirectory(ChannelSftp sftpChannel, String remoteFolderPath, List<String> fileNames) {
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

}
