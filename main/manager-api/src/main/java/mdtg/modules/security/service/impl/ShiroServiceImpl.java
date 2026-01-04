package mdtg.modules.security.service.impl;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import mdtg.modules.security.dao.SysUserTokenDao;
import mdtg.modules.security.entity.SysUserTokenEntity;
import mdtg.modules.security.service.ShiroService;
import mdtg.modules.sys.dao.SysUserDao;
import mdtg.modules.sys.entity.SysUserEntity;

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