package cn.iosd.base.generator.vo;

import java.util.List;

/**
 * @author ok1996
 */
public class MybatisGeneratorVo {
    /**
     * 项目名称 例：simple-base-generator
     */
    private String projectName;

    private String dataBaseUrl;
    private String dataBaseUserName;
    private String dataBasePassword;
    private String authorName;
    /**
     * 例：cn.iosd.demo.generator
     * <p>packageName:cn.iosd.demo</p>
     * <p>moduleName:generator</p>
     * <p>将与moduleName组合成完成包名:packageName.moduleName</p>
     */
    private String packageName;
    /**
     * 例：generator
     */
    private String moduleName;
    private String tableName;
    private List<String> tablePrefix;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<String> getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(List<String> tablePrefix) {
        this.tablePrefix = tablePrefix;
    }
}
