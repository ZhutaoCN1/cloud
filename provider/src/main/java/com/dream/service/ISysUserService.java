package com.dream.service;

import com.dream.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author BigZ
 * @since 2022-07-25
 */
public interface ISysUserService extends IService<SysUser> {

    void createUser();

}
