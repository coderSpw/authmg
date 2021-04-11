package com.spw.authmg.backup.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

/**
 * @Description mysql备份还原工具类
 * @Author spw
 * @Date 2021/4/5
 */
@Slf4j
public class BackupRestoreUtils {

    /**
     * 备份数据库数据
     * @param host   地址：端口号
     * @param username 用户名
     * @param password 密码
     * @param backupPath 备份路径
     * @param database 数据库
     * @param fileName 备份文件名称
     * @return
     */
    public static Boolean backup(String host, String username, String password
            , String backupPath, String database, String fileName) {
        File backupFile = new File(backupPath);
        if (!backupFile.exists()) {
            backupFile.mkdirs();
        }
        if (!backupPath.endsWith(File.separator)
                && !backupPath.endsWith("/")) {
            backupPath = backupPath + "/";
        }
        String backupFilePath = backupPath + fileName;
        StringBuilder sql = new StringBuilder("msyqldump --opt --add-drop-database --add-drop-table ");
        sql.append(" -h").append(host)
                .append(" -u").append(username)
                .append(" -p").append(password)
                .append(" --result-file=").append(backupFilePath)
                .append(" default-character-set=utf-8 ").append(database);

        try {
            Process process = Runtime.getRuntime().exec(getCommand(sql.toString()));
            if (process.waitFor() == 0) {
                log.info("数据已备份入{}中", backupFilePath);
                return true;
            }
        } catch (Exception e) {
            log.error("备份异常：{}", e);
        }
        return false;
    }

    /**
     * 还原数据
     * @param restorePath 文件路径
     * @param host IP地址
     * @param username 用户名
     * @param password 密码
     * @param database 数据库
     * @return
     */
    public static boolean restore(String restorePath, String host, String username
            , String password, String database) {
        File restoreFile = new File(restorePath);
        if (restoreFile.isDirectory()) {
            for (File file : restoreFile.listFiles()) {
                if (file.exists() && file.getPath().endsWith(".sql")) {
                    restorePath = file.getAbsolutePath();
                    break;
                }
            }
        }
        StringBuilder sql = new StringBuilder();
        sql.append("mysql -h").append(host)
                .append(" -u").append(username)
                .append(" -p").append(password)
                .append(" ").append(database).append(" < ").append(restorePath);
        try {
            Process exec = Runtime.getRuntime().exec(getCommand(sql.toString()));
            if (exec.waitFor() == 0) {
                log.info("数据还原成功");
                return true;
            }
        } catch (Exception e) {
            log.error("数据还原失败，{}", e);
        }
        return false;
    }

    private static String[] getCommand(String command) {
        String os = System.getProperty("os.name");
        String shell = "/bin/bash";
        String c = "-c";
        if(os.toLowerCase().startsWith("Win")){
            shell = "cmd";
            c = "/c";
        }
        String[] cmd = { shell, c, command };
        return cmd;
    }
}
