package mdtg.modules.device.service;

import java.util.Map;

import mdtg.common.page.PageData;
import mdtg.common.service.BaseService;
import mdtg.modules.device.entity.OtaEntity;

/**
 * OTA固件管理
 */
public interface OtaService extends BaseService<OtaEntity> {
    PageData<OtaEntity> page(Map<String, Object> params);

    boolean save(OtaEntity entity);

    void update(OtaEntity entity);

    void delete(String[] ids);

    OtaEntity getLatestOta(String type);
}