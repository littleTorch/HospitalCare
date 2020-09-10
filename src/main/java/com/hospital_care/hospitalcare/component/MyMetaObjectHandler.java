package com.hospital_care.hospitalcare.component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void insertFill(MetaObject metaObject) {
        //boolean createTime = metaObject.hasSetter("createTime1");
        boolean createTime = metaObject.hasSetter("createTime");
        boolean controlTime = metaObject.hasSetter("controlTime");
        boolean loginTime = metaObject.hasSetter("loginTime");
        if (createTime){
            System.out.println("insertFill~~");
            setInsertFieldValByName("createTime", sdf.format(LocalDateTime.now()), metaObject);
        } else if (controlTime){
            setInsertFieldValByName("controlTime", sdf.format(LocalDateTime.now()), metaObject);
        } else if (loginTime){
            setInsertFieldValByName("loginTime", sdf.format(LocalDateTime.now()), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //Object updateTime = getFieldValByName("updateTime", metaObject);
        boolean updateTime = metaObject.hasSetter("updateTime");
        if (updateTime){
            System.out.println("updateFill~~");
            setUpdateFieldValByName("updateTime", sdf.format(LocalDateTime.now()), metaObject);
        }
    }
}
