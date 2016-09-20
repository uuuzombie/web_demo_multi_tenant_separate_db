package com.sky.demo.web_demo_multi_tenant_separate_db.service.impl;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sky.demo.web_demo_multi_tenant_separate_db.base.Pager;
import com.sky.demo.web_demo_multi_tenant_separate_db.basedb.BaseDao;
import com.sky.demo.web_demo_multi_tenant_separate_db.dao.JdbcAnLogDao;
import com.sky.demo.web_demo_multi_tenant_separate_db.dto.anlog.*;
import com.sky.demo.web_demo_multi_tenant_separate_db.model.ActionType;
import com.sky.demo.web_demo_multi_tenant_separate_db.model.AnLog;
import com.sky.demo.web_demo_multi_tenant_separate_db.model.FeatureType;
import com.sky.demo.web_demo_multi_tenant_separate_db.service.AnLogService;
import com.sky.demo.web_demo_multi_tenant_separate_db.util.Constants;
import com.sky.demo.web_demo_multi_tenant_separate_db.util.HttpUtil;
import com.sky.demo.web_demo_multi_tenant_separate_db.util.json.JsonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 16/9/18.
 */
@Service
public class JdbcAnLogServiceImpl implements AnLogService {

    private static final Logger logger = LoggerFactory.getLogger(JdbcAnLogServiceImpl.class);

    @Resource
    private JdbcAnLogDao anLogDao;


    private static final Function<Map<String, Object>, AnLogForm> transferMap2Form = new Function<Map<String, Object>, AnLogForm>() {
        @Override
        public AnLogForm apply(Map<String, Object> map) {
            AnLogForm anLogForm = new AnLogForm();
            anLogForm.setId((long) map.get("id"));
            anLogForm.setCreateTime((String) map.get("createTime"));
            anLogForm.setUserName((String) map.get("userName"));
            anLogForm.setRoleName((String) map.get("roleName"));
            anLogForm.setServerIp((String) map.get("serverIp"));
            anLogForm.setClientIp((String) map.get("clientIp"));
            anLogForm.setActionName(ActionType.getActionTypeByCode((int) map.get("actionType")).getDesc());
            anLogForm.setFeatureName(FeatureType.getFeatureTypeByCode((int) map.get("featureType")).getDesc());

            String info = (String) map.get("actionInfo");
            List<BaseAnActionInfo> actionInfo = JsonUtil.readValue(info, List.class, List.class, BaseAnActionInfo.class);
            anLogForm.setActionInfo(actionInfo);
            return anLogForm;
        }
    };

    private static final Function<AnLogDto, AnLogForm> transfer2Form = new Function<AnLogDto, AnLogForm>() {
        @Override
        public AnLogForm apply(AnLogDto input) {
            AnLogForm anLogForm = new AnLogForm();
            anLogForm.setId(input.getId());
            anLogForm.setCreateTime(DateFormatUtils.format(input.getCreateTime(), Constants.DATETIME_PATTERN));
            anLogForm.setUserName(input.getUserName());
            anLogForm.setRoleName(input.getRoleName());
            anLogForm.setServerIp(input.getServerIp());
            anLogForm.setClientIp(input.getClientIp());
            anLogForm.setActionName(ActionType.getActionTypeByCode(input.getActionType()).getDesc());
            anLogForm.setFeatureName(FeatureType.getFeatureTypeByCode(input.getFeatureType()).getDesc());

            String info = input.getActionInfo();
            List<BaseAnActionInfo> actionInfo = JsonUtil.readValue(info, List.class, List.class, BaseAnActionInfo.class);
            anLogForm.setActionInfo(actionInfo);
            return anLogForm;
        }
    };

    private static final Function<AnLogInsertRequest, AnLog> transferInsertReq2AnLog = new Function<AnLogInsertRequest, AnLog>() {
        @Override
        public AnLog apply(AnLogInsertRequest input) {
            AnLog log = new AnLog();
            log.setCreateTime(new Date());
            log.setUserId(input.getUserId());
            log.setRoleId(input.getRoleId());
            log.setServerIp(HttpUtil.getLocalIp());
            log.setClientIp(input.getClientIp());
            log.setActionType(input.getActionType().getCode());
            log.setFeatureType(input.getFeatureType().getCode());

            String actionInfo = JsonUtil.writeValueAsString(input.getActionInfo());
            log.setActionInfo(actionInfo);
            return log;
        }
    };

    private static final Function<AnLogUpdateRequest,AnLog> transferUpdateReq2AnLog = new Function<AnLogUpdateRequest, AnLog>() {
        @Override
        public AnLog apply(AnLogUpdateRequest request) {
            AnLog log = new AnLog();
            log.setId(request.getId());
            log.setActionType(request.getActionType());
            log.setFeatureType(request.getFeatureType());

            String actionInfo = JsonUtil.writeValueAsString(request.getActionInfo());
            log.setActionInfo(actionInfo);
            return log;
        }
    };



    @Override
    public AnLogForm query(long id) {
        Map<String, Object> condition = Maps.newHashMap();
        condition.put("id", id);

        AnLogForm result = null;
        AnLogDto anLogDto = anLogDao.select(condition);
        if (anLogDto != null) {
            result = transfer2Form.apply(anLogDto);
        }
        return result;
    }

    @Override
    public List<AnLogForm> queryList(List<Long> ids) {
        Map<String, Object> condition = Maps.newHashMap();

        String strIds = Joiner.on(",").skipNulls().join(ids);
        condition.put("ids", strIds);

        List<AnLogForm> result = Lists.newArrayList();
        List<AnLogDto> anLogDtos = anLogDao.selectList(condition);
        if (CollectionUtils.isNotEmpty(anLogDtos)) {
            for (AnLogDto anLogDto : anLogDtos) {
                AnLogForm anLogForm = transfer2Form.apply(anLogDto);
                result.add(anLogForm);
            }
        }

        return result;
    }

    @Override
    public Pager<AnLogForm> queryList(AnLogQueryRequest queryRequest) {
        Map<String, Object> condition = Maps.newHashMap();
        condition.put("beginTime", queryRequest.getBeginDate() + " 00:00:00");
        condition.put("endTime", queryRequest.getEndDate() + " 23:59:59");

        long totalRecord = anLogDao.selectCount(condition);
        Pager<AnLogForm> ret = new Pager<AnLogForm>(totalRecord, queryRequest.getPageNumber(), queryRequest.getPageSize());

        int limit = ret.getPageSize();
        long offset = (ret.getPageNumber() - 1) * ret.getPageSize();
        condition.put(BaseDao.LIMIT, limit);
        condition.put(BaseDao.OFFSET, offset);

        List<AnLogForm> anLogForms = Lists.newArrayList();
        List<AnLogDto> anLogDtos = anLogDao.selectList(condition);
        if (CollectionUtils.isNotEmpty(anLogDtos)) {
            for (AnLogDto anLogDto : anLogDtos) {
                AnLogForm anLogForm = transfer2Form.apply(anLogDto);
                anLogForms.add(anLogForm);
            }
        }

        ret.setRows(anLogForms);
        return ret;
    }

    @Override
    public boolean add(AnLogInsertRequest insertRequest) {
        int row = 0;
        try {
            AnLog log = transferInsertReq2AnLog.apply(insertRequest);
            row = anLogDao.insert(log);
        } catch (Exception e) {
            logger.error(insertRequest.toString(), e);
        }
        return row > 0;
    }

    @Override
    public boolean addList(List<AnLogInsertRequest> insertRequests) {
        int row = 0;
        List<AnLog> anLogs = Lists.newArrayList();
        try {
            AnLog log = null;
            for (AnLogInsertRequest request : insertRequests) {
                log = transferInsertReq2AnLog.apply(request);
                anLogs.add(log);
            }
            row = anLogDao.batchInsert(anLogs);
        } catch (Exception e) {
            logger.error(insertRequests.toString(), e);
        }
        return row > 0;
    }

    @Override
    public boolean update(AnLogUpdateRequest updateRequest) {
        AnLog log = transferUpdateReq2AnLog.apply(updateRequest);
        int row = anLogDao.update(log);
        return row > 0;
    }

    @Override
    public boolean updateList(List<AnLogUpdateRequest> updateRequests) {
//        Map<String,Object> params = Maps.newHashMap();
//        AnLog log = transferUpdateReq2AnLog.apply(queryRequest);
//        int row = anLogDao.batchUpdate(ids, log);
        return false; //row > 0;
    }


    @Override
    public boolean delete(long id) {
        int row = anLogDao.delete(id);
        return row > 0;
    }

    @Override
    public boolean deleteList(List<Long> ids) {
        int row = anLogDao.batchDelete(ids);
        return row > 0;
    }
}

