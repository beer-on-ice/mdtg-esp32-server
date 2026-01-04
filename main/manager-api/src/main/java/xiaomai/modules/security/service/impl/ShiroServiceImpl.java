package xiaomai.modules.security.service.impl;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import xiaomai.modules.security.dao.SysUserTokenDao;
import xiaomai.modules.security.entity.SysUserTokenEntity;
import xiaomai.modules.security.service.ShiroService;
import xiaomai.modules.sys.dao.SysUserDao;
import xiaomai.modules.sys.entity.SysUserEntity;

@AllArgsConstructor
@Service
public class ShiroServiceImpl implements ShiroService {
    private final SysUserDao sysUserDao;
    private final SysUserTokenDao sysUserTokenDao;

    @Override
    public SysUserTokenEntity getByToken(String token) {
        return sysUserTokenDao.getByToken(token);
    }

    @Override
    public SysUserEntity getUser(Long userId) {
        return sysUserDao.selectById(userId);
    }
}