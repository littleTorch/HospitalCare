package com.hospital_care.hospitalcare.service.impl;

import com.hospital_care.hospitalcare.entity.Customer;
import com.hospital_care.hospitalcare.mapper.CustomerMapper;
import com.hospital_care.hospitalcare.service.CustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hostpital_care
 * @since 2020-09-10
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {

}
