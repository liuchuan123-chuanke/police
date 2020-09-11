package com.imeng.generator.utils;

import com.imeng.generator.CodeGeneratorApp;
import com.imeng.generator.service.SysGeneratorService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * GenUtils测试用例
 *
 * @author zlt
 * @date 2019/5/10
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CodeGeneratorApp.class)
public class GenUtilsTest {

    @Autowired
    SysGeneratorService sysGeneratorService;

    @Test
    public void testTableToJava() {
        String javaName = GenUtils.tableToJava("sys_task_temp", "sys_");
        Assertions.assertThat(javaName).isEqualTo("EventMessage");
    }

    @Test
    public void gen(){
        String[] tables = new String[1];
        tables[0]="sys_repair_info";    //表名
        //tables[1]="sys_vendor_info";    //表名
        //tables[2]="sys_vendor_file";
        sysGeneratorService.generatorCode(tables);
    }

}
