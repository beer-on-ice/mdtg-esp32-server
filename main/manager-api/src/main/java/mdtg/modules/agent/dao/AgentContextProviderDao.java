package mdtg.modules.agent.dao;

import org.apache.ibatis.annotations.Mapper;
import mdtg.common.dao.BaseDao;
import mdtg.modules.agent.entity.AgentContextProviderEntity;

@Mapper
public interface AgentContextProviderDao extends BaseDao<AgentContextProviderEntity> {
}
