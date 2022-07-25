package com.dream.utils;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.util.Collections;


public class CodeGenerator {

    public static void main(String[] args) {

        String projectPath = System.getProperty("user.dir");
        String classPath = projectPath + "/provider" + "/src/main/java";
        String mapperPath = projectPath + "/provider" + "/src/main/resources/mapper";
        FastAutoGenerator.create(getDataSource())
                // 全局配置
                .globalConfig((scanner, builder) ->
                        builder.author("BigZ")
                                .fileOverride()
                                .enableSwagger()
                                .outputDir(classPath)
                )
                // 包配置
                .packageConfig((scanner, builder) ->
                        builder.parent("com.dream")
                                .pathInfo(Collections.singletonMap(OutputFile.xml, mapperPath))
                )
                // 策略配置
                .strategyConfig((scanner, builder) -> builder.addInclude(getTables())
                        .controllerBuilder().enableRestStyle().enableHyphenStyle()
                        .entityBuilder().enableLombok().addTableFills(
                                new Column("gmt_create", FieldFill.INSERT),
                                new Column("gmt_modified", FieldFill.INSERT_UPDATE)
                        ).build())
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

    private static DataSourceConfig.Builder getDataSource() {
        return new DataSourceConfig.
                Builder("jdbc:mysql://localhost:3306/provider_database?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC&nullCatalogMeansCurrent=true", "root", "root");
    }


    private static String[] getTables() {
        String[] strings = new String[1];
        strings[0] = "sys_user";
        return strings;

    }

}
