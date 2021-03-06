package com.hospital_care.hospitalcare.service;

import com.hospital_care.hospitalcare.entity.ServiceFocus;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hostpital_care
 * @since 2020-09-15
 */
public interface ServiceFocusService extends IService<ServiceFocus> {

    List<ServiceFocus> findServiceFocusListIfRecordIdNotEmpty();

}
