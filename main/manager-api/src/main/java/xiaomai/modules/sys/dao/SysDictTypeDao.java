package mdtg.modules.sys.dao;

import org.apache.ibatis.annotations.Mapper;

import mdtg.common.dao.BaseDao;
import mdtg.modules.sys.entity.SysDictTypeEntity;

/**
 * 字典类型
 */
@Mapper
public interface SysDictTypeDao extends BaseDao<SysDictTypeEntity> {

}
