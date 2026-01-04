package mdtg.modules.sys.dao;

import org.apache.ibatis.annotations.Mapper;

import mdtg.common.dao.BaseDao;
import mdtg.modules.sys.entity.SysUserEntity;

/**
 * 系统用户
 */
@Mapper
public interface SysUserDao extends BaseDao<SysUserEntity> {

}