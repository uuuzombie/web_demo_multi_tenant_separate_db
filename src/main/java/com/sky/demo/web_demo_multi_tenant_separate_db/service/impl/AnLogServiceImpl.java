package com.sky.demo.web_demo_multi_tenant_separate_db.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.google.common.base.Joiner;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sky.demo.web_demo_multi_tenant_separate_db.base.Pager;
import com.sky.demo.web_demo_multi_tenant_separate_db.dao.AnLogDao;
import com.sky.demo.web_demo_multi_tenant_separate_db.dto.anlog.*;
import com.sky.demo.web_demo_multi_tenant_separate_db.model.ActionType;
import com.sky.demo.web_demo_multi_tenant_separate_db.model.AnLog;
import com.sky.demo.web_demo_multi_tenant_separate_db.model.FeatureType;
import com.sky.demo.web_demo_multi_tenant_separate_db.service.AnLogService;
import com.sky.demo.web_demo_multi_tenant_separate_db.util.HttpUtil;
import com.sky.demo.web_demo_multi_tenant_separate_db.util.json.JsonUtil;
import org.springframework.stereotype.Service;


/**
 * Created by rg on 2015/7/6.
 */
//@Service  //for MyBatis
public class AnLogServiceImpl implements AnLogService {

    private static final Logger logger = LoggerFactory.getLogger(AnLogServiceImpl.class);

    @Resource
    private AnLogDao anLogDao;


    private static final int QUERY_DAY = -60;

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

    private static final Function<AnLogInsertRequest, AnLog> transferInsertReq2AnLog = new Function<AnLogInsertRequest, AnLog>() {
        @Override
        public AnLog apply(AnLogInsertRequest request) {
            AnLog log = new AnLog();
            log.setCreateTime(new Date());
            log.setUserId(request.getUserId());
            log.setRoleId(request.getRoleId());
            log.setServerIp(HttpUtil.getLocalIp());
            log.setClientIp(request.getClientIp());
            log.setActionType(request.getActionType());
            log.setFeatureType(request.getFeatureType());

            String actionInfo = JsonUtil.writeValueAsString(request.getActionInfo());
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
        AnLogForm anLogForm = anLogDao.selectById(id);
        return anLogForm;
    }

    @Override
    public List<AnLogForm> queryList(List<Long> ids) {
        Map<String, Object> condition = Maps.newHashMap();
        String strIds = Joiner.on(",").skipNulls().join(ids);
        condition.put("ids", strIds);

        List<AnLogForm> anLogForms = anLogDao.selectList(condition);
        return anLogForms;
    }

    @Override
    public Pager<AnLogForm> queryList(AnLogQueryRequest request) {
        Map<String, Object> condition = Maps.newHashMap();
        condition.put("beginTime", request.getBeginDate() + " 00:00:00");
        condition.put("endTime", request.getEndDate() + " 23:59:59");

        int totalRecord = anLogDao.selectCount(condition);
        Pager<AnLogForm> ret = new Pager<AnLogForm>(totalRecord, request.getPageNo(), request.getPageSize());

        int limit = ret.getPageSize();
        long offset = (ret.getPageNumber() - 1) * ret.getPageSize();
        List<AnLogForm> anLogForms = anLogDao.selectList(condition, new RowBounds(new Long(offset).intValue(), limit));  //maybe wrong

        ret.setRows(anLogForms);
        return ret;
    }

    @Override
    public boolean delete(long id) {
        int row = anLogDao.deleteById(id);
        return row > 0;
    }

    @Override
    public boolean deleteList(List<Long> ids) {
        int row = anLogDao.batchDelete(ids);
        return row > 0;
    }

    @Override
    public boolean add(AnLogInsertRequest request) {
        int row = 0;
        try {
            AnLog log = transferInsertReq2AnLog.apply(request);
            row = anLogDao.insert(log);
        } catch (Exception e) {
            logger.error(request.toString(), e);
        }
        return row > 0;

    }

    @Override
    public boolean addList(List<AnLogInsertRequest> requests) {
        int row = 0;
        List<AnLog> anLogs = Lists.newArrayList();
        try {
            AnLog log = null;
            for (AnLogInsertRequest request : requests) {
                log = transferInsertReq2AnLog.apply(request);
                anLogs.add(log);
            }
            row = anLogDao.batchInsert(anLogs);
        } catch (Exception e) {
            logger.error(requests.toString(), e);
        }
        return row > 0;
    }

    @Override
    public boolean update(AnLogUpdateRequest request) {
        AnLog log = transferUpdateReq2AnLog.apply(request);
        int row = anLogDao.update(log);
        return row > 0;
    }

    @Override
    public boolean updateList(List<AnLogUpdateRequest> records) {
//        Map<String,Object> params = Maps.newHashMap();
//        AnLog log = transferUpdateReq2AnLog.apply(request);
//        int row = anLogDao.batchUpdate(ids, log);
        return false; //row > 0;
    }


    private String calculatePreviousDay(int queryDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, queryDay);

        String previousDay = DateFormatUtils.format(calendar.getTime(), "yyyy-MM-dd HH:mm:ss");
        return previousDay;
    }

    public static void main(String[] args) {
        int previous = -60;
        String result = new AnLogServiceImpl().calculatePreviousDay(previous);
        System.out.println(result);
    }
}
