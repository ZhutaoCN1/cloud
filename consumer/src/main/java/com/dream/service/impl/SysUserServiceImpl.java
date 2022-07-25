package com.dream.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dream.client.ProviderClient;
import com.dream.entity.SysUser;
import com.dream.mapper.SysUserMapper;
import com.dream.service.ISysUserService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author BigZ
 * @since 2022-07-25
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    ProviderClient providerClient;

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public void createUser() {
        SysUser sysUser = new SysUser();
        sysUser.setName("李四");
        sysUser.setAddr("西安");
        save(sysUser);
        providerClient.createUser();
    }
}
