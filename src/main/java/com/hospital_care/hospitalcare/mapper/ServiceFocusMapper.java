package com.hospital_care.hospitalcare.mapper;

import com.hospital_care.hospitalcare.entity.ServiceFocus;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hostpital_care
 * @since 2020-09-15
 */
public interface ServiceFocusMapper extends BaseMapper<ServiceFocus> {

    List<ServiceFocus> findServiceFocusListIfRecordIdNotEmpty();

}
