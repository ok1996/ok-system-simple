package cn.iosd.base.generator.api.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author ok1996
 */
public class ProjectGenVo {
    @Schema(description = "项目名称", example = "simple-service-generator")
    private String projectName;

    @Schema(description = "包名前缀, 将与moduleName组合成完成包名: packageName.moduleName", example = "cn.iosd.demo")
    private String packageName;

    @Schema(description = "模块名称", example = "generator")
    private String moduleName;

    @Schema(description = "依赖模块版本号", hidden = true)
    private String simpleVersion = "2023.4.2.0-SNAPSHOT";

    @Schema(description = "模块名称-首字母大写", example = "Generator", hidden = true)
    private String moduleNameCapitalized;

    @Schema(description = "包名切割为目录", example = "cn/iosd/demo", hidden = true)
    private String packageDir;

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

    public String getSimpleVersion() {
        return simpleVersion;
    }

    public void setSimpleVersion(String simpleVersion) {
        this.simpleVersion = simpleVersion;
    }


    public String getModuleNameCapitalized() {
        return this.moduleNameCapitalized;
    }

    public String getPackageDir() {
        return this.packageDir;
    }


    public void setModuleNameCapitalized(String moduleNameCapitalized) {
        this.moduleNameCapitalized = moduleNameCapitalized;
    }

    public void setPackageDir(String packageDir) {
        this.packageDir = packageDir;
    }

    /**
     * 将模块名称首字母修改为大写
     *
     * @return 模块名称-首字母大写
     */
    public String getModuleNameCapitalizedCustom() {
        if (this.moduleName == null || this.moduleName.isEmpty()) {
            return this.moduleName;
        }
        char firstChar = Character.toUpperCase(this.moduleName.charAt(0));
        return firstChar + this.moduleName.substring(1);
    }

    public String getPackageDirCustom() {
        if (this.packageName == null || this.packageName.isEmpty()) {
            return this.packageName;
        }
        return packageName.replace(".", "/");
    }
}
