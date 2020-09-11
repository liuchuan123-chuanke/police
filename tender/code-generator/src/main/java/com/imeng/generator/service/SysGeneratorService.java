package com.imeng.generator.service;

import java.util.List;
import java.util.Map;

/**
 * @Author zlt
 */
public interface SysGeneratorService  {

    Map<String, String> queryTable(String tableName);

    List<Map<String, String>> queryColumns(String tableName);

    byte[] generatorCode(String[] tableNames);
}
