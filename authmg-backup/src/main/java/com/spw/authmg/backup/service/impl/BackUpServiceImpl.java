package com.spw.authmg.backup.service.impl;

import com.spw.authmg.backup.configuration.DataSourceProperties;
import com.spw.authmg.backup.service.BackUpService;
import com.spw.authmg.backup.utils.BackupRestoreUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Description 数据脚本备份
 * @Author spw
 * @Date 2021/4/5
 */
@Service
@EnableConfigurationProperties(DataSourceProperties.class)
public class BackUpServiceImpl implements BackUpService {
    private static final String backupPath = "/usr/local/docker/backup/";
    @Autowired
    private DataSourceProperties dataSourceProperties;

    @Override
    public Boolean backUp(String database, Boolean isDefault) {
        String host = dataSourceProperties.getHost();
        String username = dataSourceProperties.getUsername();
        String password = dataSourceProperties.getPassword();
        String fileName = "authmg_"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"));
        if (isDefault) {
            fileName = "authmg";
        }
        return BackupRestoreUtils.backup(host,username,password,backupPath, database, fileName);
    }

    @Override
    public Boolean restore(String database) {
        File restoreFile = new File(backupPath);
        String host = dataSourceProperties.getHost();
        String username = dataSourceProperties.getUsername();
        String password = dataSourceProperties.getPassword();
        if (restoreFile.exists() && restoreFile.isDirectory()) {
            String restorePath = Arrays.stream(Objects.requireNonNull(restoreFile.listFiles()))
                    .filter(x -> x.exists() && x.getPath().endsWith(".sql"))
                    .collect(Collectors.toList()).get(1)
                    .getAbsolutePath();
            return BackupRestoreUtils.restore(restorePath,host, username, password, database);
        }
        return false;
    }

    @Override
    public List<String>  findByName(String name) {
        //判断是否存在默认备份文件， 不存在新建
        if (!new File(backupPath + File.separator + name).exists()) {
            this.backUp(name, true);
        }
        File backFile = new File(backupPath);
        List<String> fileNameList= new ArrayList<>();
        if (backFile.exists() && backFile.isDirectory()) {
            fileNameList = Arrays.stream(Objects.requireNonNull(backFile.listFiles()))
                    .map(File::getName)
                    .collect(Collectors.toList());
        }
        return fileNameList;
    }

    @Override
    public void deleteByName(@NotNull String name) {
        File file = new File(backupPath);
        if (file.exists() && file.isDirectory()) {
            Arrays.stream(Objects.requireNonNull(file.listFiles()))
                    .filter(x -> x.getName().startsWith(name))
                    .forEach(File::delete);
        }
    }

}
