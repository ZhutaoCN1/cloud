package com.dream.utils;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class CodeGenerator {

    public static void main(String[] args) {

        String projectPath = System.getProperty("user.dir");
        String classPath = projectPath + "/consumer" + "/src/main/java";
        String mapperPath = projectPath + "/consumer" + "/src/main/resources/mapper";
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
                Builder("jdbc:mysql://localhost:3306/consumer_database?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC&nullCatalogMeansCurrent=true", "root", "root");
    }


    private static String[] getTables() {

       /* HikariDataSource dsc = new HikariDataSource();
        dsc.setJdbcUrl("jdbc:mysql://192.168.10.18:3306/talents_affirm_platform?useUnicode=true&useSSL=false&characterEncoding=utf8");
        dsc.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");

        List<String> tableList = new ArrayList<>();

        Connection connection = null;
        try {
            connection = dsc.getConnection();
            ResultSet tables = connection.getMetaData().getTables("talents_affirm_platform", null, "%", null);

            while (tables.next()) {
                String string = tables.getString(3);
                    tableList.add(string);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return tableList.toArray(new String[0]);*/

        String[] strings = new String[1];
        strings[0] = "sys_user";
        return strings;

    }

}
