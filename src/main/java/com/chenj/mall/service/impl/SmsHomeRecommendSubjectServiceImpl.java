package com.chenj.mall.service.impl;

import com.chenj.mall.dao.SmsHomeRecommendSubjectDao;
import com.chenj.mall.dto.SmsHomeRecommendSubjectParam;
import com.chenj.mall.mbg.model.SmsHomeRecommendSubject;
import com.chenj.mall.service.SmsHomeRecommendSubjectService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SmsHomeRecommendSubjectServiceImpl implements SmsHomeRecommendSubjectService {

    @Autowired
    private SmsHomeRecommendSubjectDao homeRecommendSubjectDao;

    @Override
    public SmsHomeRecommendSubject selectById(Long id) {
        return homeRecommendSubjectDao.selectById(id);
    }

    @Override
    public List<SmsHomeRecommendSubject> selectAll() {
        return homeRecommendSubjectDao.selectAll();
    }

    @Override
    public List<SmsHomeRecommendSubject> list(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return homeRecommendSubjectDao.selectAll();
    }

    @Override
    public List<SmsHomeRecommendSubject> selectBySubjectNameOrRecommendStatus(String subjectName, Integer recommendStatus, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return homeRecommendSubjectDao.selectBySubjectNameOrRecommendStatus(subjectName, recommendStatus);
    }

    @Override
    public SmsHomeRecommendSubject selectBySubjectIdOrSubjectName(Long subjectId, String subjectName) {
        return homeRecommendSubjectDao.selectBySubjectIdOrSubjectName(subjectId, subjectName);
    }

    @Override
    public int insert(SmsHomeRecommendSubjectParam smsHomeRecommendSubjectParam) {
        Long subjectId = (long) (homeRecommendSubjectDao.selectAll().size() + 1);//实际项目中应该是系统分配随机编号，这里就根据数据库数据依次自增
        SmsHomeRecommendSubject smsHomeRecommendSubject = new SmsHomeRecommendSubject();
        BeanUtils.copyProperties(smsHomeRecommendSubjectParam,smsHomeRecommendSubject);
        smsHomeRecommendSubject.setSubjectId(subjectId);
        return homeRecommendSubjectDao.insertSelective(smsHomeRecommendSubject);
    }

    @Override
    public int updateById(Long id, SmsHomeRecommendSubjectParam smsHomeRecommendSubjectParam) {
        SmsHomeRecommendSubject smsHomeRecommendSubject = homeRecommendSubjectDao.selectById(id);
        BeanUtils.copyProperties(smsHomeRecommendSubjectParam, smsHomeRecommendSubject);
        return homeRecommendSubjectDao.updateByIdSelective(smsHomeRecommendSubject);
    }

    @Override
    public int updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
        return homeRecommendSubjectDao.updateRecommendStatus(ids, recommendStatus);
    }

    @Override
    public int deleteById(Long id) {
        return homeRecommendSubjectDao.deleteById(id);
    }

    @Override
    public int deleteByIds(List<Long> ids) {
        return homeRecommendSubjectDao.deleteByIds(ids);
    }
}
