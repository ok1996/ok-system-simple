package cn.iosd.base.generator.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * @author ok1996
 */
public class DatabaseGenVo {

    @Schema(description = "项目名称",example = "simple-service-generator")
    private String projectName;

    @Schema(description = "包名前缀, 将与moduleName组合成完成包名: packageName.moduleName ",example = "cn.iosd.demo")
    private String packageName;

    @Schema(description = "模块名称",example = "generator")
    private String moduleName;

    @Schema(description = "数据库连接地址",example = "jdbc:mysql://127.0.0.1:3306/simple_demo")
    private String dataBaseUrl;

    @Schema(description = "数据库用户名",example = "root")
    private String dataBaseUserName;

    @Schema(description = "数据库用户密码",example = "123456")
    private String dataBasePassword;

    @Schema(description = "注释用户名",example = "ok1996")
    private String authorName;

    @Schema(description = "表名",example = "demo_article")
    private List<String> tableNames;

    @Schema(description = "过滤表名前缀",example = "demo")
    private List<String> tablePrefix;


    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getDataBaseUrl() {
        return dataBaseUrl;
    }

    public void setDataBaseUrl(String dataBaseUrl) {
        this.dataBaseUrl = dataBaseUrl;
    }

    public String getDataBaseUserName() {
        return dataBaseUserName;
    }

    public void setDataBaseUserName(String dataBaseUserName) {
        this.dataBaseUserName = dataBaseUserName;
    }

    public String getDataBasePassword() {
        return dataBasePassword;
    }

    public void setDataBasePassword(String dataBasePassword) {
        this.dataBasePassword = dataBasePassword;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public List<String> getTableNames() {
        return tableNames;
    }

    public void setTableNames(List<String> tableNames) {
        this.tableNames = tableNames;
    }

    public List<String> getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(List<String> tablePrefix) {
        this.tablePrefix = tablePrefix;
    }
}
