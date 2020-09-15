package com.hospital_care.hospitalcare.codegenerator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 代码生成器
 */
// 演示例子，执行 main 方法控制台输入模块表名回车自动生成对应项目目录中
public class CodeGenerator {

    //permission,role,role_permission,t_bed,t_checkin,t_checkout,t_customer,t_emp,t_food,t_food_date,t_food_manage,t_leave,t_nurse_level,t_nurse_project,t_nurse_record,t_service_focus,t_service_obj,user,user_role

    /**
     * 读取控制台内容
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 1.全局配置
        GlobalConfig gc = new GlobalConfig();
        //当前路径
        String projectPath = System.getProperty("user.dir");
        //输出路径
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("hostpital_care");    //设置作者
        //生成代码后，是否打开文件夹
        gc.setOpen(false);
        gc.setFileOverride(true);  //是否覆盖原来代码，个人建议设置为false,别覆盖，危险系数太高
        gc.setServiceName("%sService");   //去掉service的I前缀,一般只需要设置service就行
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");
        gc.setIdType(IdType.AUTO);
        gc.setDateType(DateType.ONLY_DATE);   //日期格式
        gc.setSwagger2(true);       // 实体属性 Swagger2 注解,实体类上会增加注释
        mpg.setGlobalConfig(gc);

        // 2.数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql:///hospital_dr?useUnicode=true&characterEncoding=utf8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        dsc.setDbType(DbType.MYSQL);    //指定数据库的类型
        mpg.setDataSource(dsc);

        // 3.包的配置
        PackageConfig pc = new PackageConfig();
        //pc.setModuleName("module");   //模块名称  设置后,会生成com.cxyxs.test.module,里面存放之前设置的mapper,entity
        pc.setParent("com.hospital_care.hospitalcare");   //自定义包的路径
        pc.setEntity("entity");
        pc.setMapper("mapper");
        pc.setService("service");
        pc.setController("controller");
        mpg.setPackageInfo(pc);

        // 4.策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));    //设置映射的表名，可以设置多个表
        //表前缀设置  cxyxs_student
        strategy.setTablePrefix(new String[]{"t_"}); //去掉表名前缀
        //包的命名规则，使用驼峰规则
        strategy.setNaming(NamingStrategy.underline_to_camel);
        //列的名称，使用驼峰规则
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //是否使用lombok
        strategy.setEntityLombokModel(true);
        //驼峰命名
        strategy.setRestControllerStyle(true);
        //strategy.setControllerMappingHyphenStyle(true);// http://localhost:8080/hello_id_1
        //逻辑删除,假删除会用到
        strategy.setLogicDeleteFieldName("is_deleted");
        //自动填充字段,在项目开发过程中,例如创建时间，修改时间,每次，都需要我们来指定，太麻烦了,设置为自动填充规则，就不需要我们赋值咯
        TableFill fillInsert = new TableFill("create_time", FieldFill.INSERT);
        TableFill fillUpdate= new TableFill("update_time", FieldFill.UPDATE);
        List fillLists = new ArrayList();
        fillLists.add(fillInsert);
        fillLists.add(fillUpdate);
        strategy.setTableFillList(fillLists);
        //乐观锁
        //strategy.setVersionFieldName("version");
        mpg.setStrategy(strategy);

        mpg.execute();  //执行
    }

}
