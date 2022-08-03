package com.dream.service.impl;
import java.time.LocalDateTime;

import com.dream.entity.SysUser;
import com.dream.mapper.SysUserMapper;
import com.dream.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author BigZ
 * @since 2022-07-25
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Override
//    @GlobalTransactional(rollbackFor = Exception.class)
//    @Transactional(rollbackFor = Exception.class)
    public void createUser() {
        SysUser sysUser = new SysUser();
        sysUser.setName("张三");
        sysUser.setAddr("成都");
        save(sysUser);
        throw new RuntimeException();
    }
}
