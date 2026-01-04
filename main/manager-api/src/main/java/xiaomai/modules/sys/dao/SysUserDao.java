package xiaomai.modules.sys.dao;

import org.apache.ibatis.annotations.Mapper;

import xiaomai.common.dao.BaseDao;
import xiaomai.modules.sys.entity.SysUserEntity;

/**
 * 系统用户
 */
@Mapper
public interface SysUserDao extends BaseDao<SysUserEntity> {

}