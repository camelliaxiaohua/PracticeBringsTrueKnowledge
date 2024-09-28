package camellia.cloudstorage.controller;

import camellia.cloudstorage.service.SftpService;
import camellia.cloudstorage.utils.SftpUtils;
import com.jcraft.jsch.ChannelSftp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Datetime: 2024/9/28上午11:53
 * @author: Camellia.xioahua
 */

@RestController
@RequestMapping
@Slf4j
public class CloudController {

    @Autowired
    private SftpService sftpService;

    @GetMapping("/{remoteFolderPath}")
    public ResponseEntity<List<String>> getFileFromSFTP(@PathVariable String remoteFolderPath) {
        ChannelSftp channelSftp = null;
        try {
            channelSftp = SftpUtils.openClient();
            log.info("channelSftp: {}", channelSftp);
            List<String> fileNames = new ArrayList<>();
            sftpService.traverseDirectory(channelSftp, remoteFolderPath, fileNames);
            if (!fileNames.isEmpty()) {
                return ResponseEntity.ok().body(fileNames);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("获取文件失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } finally {
            if (channelSftp != null) SftpUtils.closeClient();
        }
    }


}
