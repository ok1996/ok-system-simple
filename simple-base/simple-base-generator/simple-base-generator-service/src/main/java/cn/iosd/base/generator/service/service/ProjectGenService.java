package cn.iosd.base.generator.service.service;

import cn.iosd.base.generator.api.vo.ProjectGenVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author ok1996
 */
@Service
public class ProjectGenService {
    private static final Logger log = LoggerFactory.getLogger(ProjectGenService.class);

    /**
     * 初始化压缩包地址
     */
    @Value("${simple.base.generator.project.initProjectZipUrl:/temp/initProject.zip}")
    private String initProjectZipUrl;

    /**
     * 解压覆盖生成项目压缩包操作的临时目录
     */
    @Value("${simple.base.generator.project.newProjectAndZipTempPath:/temp/simple/new}")
    private String newProjectAndZipTempPath;

    public synchronized FileSystemResource projectGenZip(ProjectGenVo req) throws IOException {
        String projectName = req.getProjectName();
        /**
         * 压缩包解压目录
         */
        String initUnzipPath = newProjectAndZipTempPath + "/init";
        /**
         * 复制解压后的内容到新的地址
         */
        String copyUnzipPath = newProjectAndZipTempPath + "/new";
        /**
         * 完成内容覆盖的目录地址,包含项目名称文件名
         */
        String successProjectPath = newProjectAndZipTempPath + "/new/" + projectName;
        /**
         * 压缩完成内容覆盖目录文件的地址
         */
        String createZipPath = newProjectAndZipTempPath + "/project-" + projectName + ".zip";

        log.debug("项目名称:{}---压缩包:{},解压至目录:{}", projectName, initProjectZipUrl, initUnzipPath);
        unzip(initProjectZipUrl, initUnzipPath);

        log.debug("项目名称:{}---复制初始化目录到新的目录地址,以备覆盖内容,初始化目录地址:{},新目录地址为:{}", projectName, initUnzipPath, copyUnzipPath);
        copyInitProjectFiles(initUnzipPath, copyUnzipPath, req);

        log.debug("项目名称:{}---在指定文件夹中递归地替换变量的值,指定文件夹为:{}", projectName, copyUnzipPath);
        replaceVariableValues(copyUnzipPath, req);

        log.debug("项目名称:{}---创建压缩包,完成内容覆盖的目录地址:{},压缩包地址:{}", projectName, successProjectPath, createZipPath);
        createZip(successProjectPath, createZipPath);

        log.debug("项目名称:{}---删除压缩包解压目录及目录下文件:{}", projectName, initUnzipPath);
        deleteFolder(new File(initUnzipPath));

        log.debug("项目名称:{}---删除复制解压后的内容地址及目录下文件:{}", projectName, copyUnzipPath);
        deleteFolder(new File(copyUnzipPath));

        log.debug("项目名称:{}---留存压缩包地址,以供下载,可定期清理:{}", projectName, createZipPath);
        return new FileSystemResource(createZipPath);
    }

    /**
     * 复制初始项目文件到新项目目录。
     *
     * @param initProjectPath 初始化项目路径
     * @param newProjectPath  新项目路径
     * @param req             项目生成配置信息
     * @throws IOException 如果在文件复制过程中发生I/O错误
     */
    private void copyInitProjectFiles(String initProjectPath, String newProjectPath, ProjectGenVo req) throws IOException {
        File initProject = new File(initProjectPath);
        File newProjectDir = new File(newProjectPath);
        copyFilesRecursively(initProject, newProjectDir, req);
    }

    /**
     * 递归地复制文件和文件夹。
     *
     * @param source      源文件或源文件夹
     * @param destination 目标文件夹
     * @param req         项目生成配置信息
     * @throws IOException 如果在文件复制过程中发生I/O错误
     */
    private void copyFilesRecursively(File source, File destination, ProjectGenVo req) throws IOException {
        if (source.isDirectory()) {
            if (!destination.exists()) {
                destination.mkdirs();
            }
            for (String file : Objects.requireNonNull(source.list())) {
                File sourceFile = new File(source, file);
                File destFile = new File(destination, replaceVariables(file, req));
                copyFilesRecursively(sourceFile, destFile, req);
            }
        } else {
            try (InputStream in = new FileInputStream(source);
                 OutputStream out = new FileOutputStream(destination)) {
                FileCopyUtils.copy(in, out);
            }
        }
    }

    /**
     * 在指定文件夹中递归地替换变量的值
     *
     * @param directoryPath 文件夹路径
     * @param req           项目生成配置信息
     * @throws IOException 如果在文件读写过程中发生I/O错误
     */
    private void replaceVariableValues(String directoryPath, ProjectGenVo req) throws IOException {
        File directory = new File(directoryPath);
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.isDirectory()) {
                replaceVariableValues(file.getAbsolutePath(), req);
            } else {
                String content = readFileToString(file);
                content = replaceVariables(content, req);
                writeStringToFile(file, content);
            }
        }
    }

    /**
     * 读取文件内容为字符串
     *
     * @param file 文件
     * @return 文件内容字符串
     * @throws IOException 如果在文件读取过程中发生I/O错误
     */
    private String readFileToString(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
            return content.toString();
        }
    }

    /**
     * 将字符串内容写入文件
     *
     * @param file    文件
     * @param content 要写入的内容
     * @throws IOException 如果在文件写入过程中发生I/O错误
     */
    private void writeStringToFile(File file, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
        }
    }

    /**
     * 替换字符串中的变量值
     *
     * @param content 要处理的内容
     * @param req     项目生成配置信息
     * @return 替换后的内容
     */
    private String replaceVariables(String content, ProjectGenVo req) {
        Map<String, String> variables = Map.of(
                "{projectName}", req.getProjectName(),
                "{packageName}", req.getPackageName(),
                "{moduleName}", req.getModuleName(),
                "{simpleVersion}", req.getSimpleVersion(),
                "{moduleNameCapitalized}", req.getModuleNameCapitalizedCustom(),
                "{packageDir}", req.getPackageDirCustom(),
                "{springBootVersion}", req.getSpringBootVersion(),
                "{javaVersion}", req.getJavaVersion(),
                "{projectVersion}", req.getProjectVersion(),
                "{projectPort}", req.getProjectPort()
        );
        StringBuilder result = new StringBuilder(content);
        variables.forEach((variable, value) ->
                replaceVariable(result, variable, value));
        return result.toString();
    }

    private void replaceVariable(StringBuilder content, String variable, String value) {
        int index = content.indexOf(variable);
        while (index != -1) {
            content.replace(index, index + variable.length(), value);
            index = content.indexOf(variable, index + value.length());
        }
    }

    /**
     * 创建ZIP压缩文件
     *
     * @param sourcePath  要压缩的源文件夹路径
     * @param zipFilePath ZIP文件路径
     * @throws IOException 如果在压缩过程中发生I/O错误
     */
    private void createZip(String sourcePath, String zipFilePath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(zipFilePath);
             ZipOutputStream zipOut = new ZipOutputStream(fos)) {
            File sourceDir = new File(sourcePath);
            zipFile(sourceDir, sourceDir.getName(), zipOut);
        }
    }

    /**
     * 在ZIP文件中递归地压缩文件和文件夹
     *
     * @param fileToZip 要压缩的文件或文件夹
     * @param fileName  压缩后的文件名
     * @param zipOut    ZipOutputStream实例
     * @throws IOException 如果在压缩过程中发生I/O错误
     */
    private void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            for (File childFile : Objects.requireNonNull(fileToZip.listFiles())) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        try (FileInputStream fis = new FileInputStream(fileToZip)) {
            ZipEntry zipEntry = new ZipEntry(fileName);
            zipOut.putNextEntry(zipEntry);
            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
        }
    }

    /**
     * 解压缩工具方法，将指定的ZIP文件解压到目标文件夹
     *
     * @param zipFilePath           要解压的ZIP文件的路径
     * @param destinationFolderPath 解压后文件的目标文件夹路径
     * @throws IOException 如果解压过程中发生I/O错误
     */
    public static void unzip(String zipFilePath, String destinationFolderPath) throws IOException {
        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath))) {
            byte[] buffer = new byte[1024];
            ZipEntry zipEntry = zipInputStream.getNextEntry();

            while (zipEntry != null) {
                String entryName = zipEntry.getName();
                String filePath = destinationFolderPath + File.separator + entryName;

                if (zipEntry.isDirectory()) {
                    File dir = new File(filePath);
                    dir.mkdirs();
                } else {
                    File outputFile = new File(filePath);
                    File parentDir = outputFile.getParentFile();
                    if (parentDir != null) {
                        parentDir.mkdirs();
                    }

                    try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
                        int bytesRead;
                        while ((bytesRead = zipInputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                    }
                }
                zipInputStream.closeEntry();
                zipEntry = zipInputStream.getNextEntry();
            }
        }
    }

    /**
     * 递归地删除文件夹及其内容
     *
     * @param folder 要删除的文件夹
     */
    public static void deleteFolder(File folder) {
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteFolder(file);
                }
            }
        }
        folder.delete();
    }
}