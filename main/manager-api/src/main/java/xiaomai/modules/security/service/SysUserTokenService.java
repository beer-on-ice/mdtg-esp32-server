package xiaomai.modules.security.service;

import xiaomai.common.page.TokenDTO;
import xiaomai.common.service.BaseService;
import xiaomai.common.utils.Result;
import xiaomai.modules.security.entity.SysUserTokenEntity;
import xiaomai.modules.sys.dto.PasswordDTO;
import xiaomai.modules.sys.dto.SysUserDTO;

/**
 * 用户Token
 * Copyright (c) 人人开源 All rights reserved.
 * Website: https://www.renren.io
 */
public interface SysUserTokenService extends BaseService<SysUserTokenEntity> {

    /**
     * 生成token
     *
     * @param userId 用户ID
     */
    Result<TokenDTO> createToken(Long userId);

    SysUserDTO getUserByToken(String token);

    /**
     * 退出
     *
     * @param userId 用户ID
     */
    void logout(Long userId);

    /**
     * 修改密码
     *
     * @param userId
     * @param passwordDTO
     */
    void changePassword(Long userId, PasswordDTO passwordDTO);

}