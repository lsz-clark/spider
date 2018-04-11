package com.lgfei.tool.spider.operate.mvcrawler.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.lgfei.tool.spider.common.exception.SystemException;
import com.lgfei.tool.spider.common.message.response.BaseResponse;
import com.lgfei.tool.spider.common.message.response.PageResultResponse;
import com.lgfei.tool.spider.common.util.CreateIDUtil;
import com.lgfei.tool.spider.common.vo.PageVO;
import com.lgfei.tool.spider.operate.mvcrawler.dao.MvInfoDao;
import com.lgfei.tool.spider.operate.mvcrawler.enums.TablesEnum;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.DeleteMvReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.QueryMvPageListReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.reqdata.UpdateMvReqData;
import com.lgfei.tool.spider.operate.mvcrawler.model.vo.MvInfoVO;
import com.lgfei.tool.spider.operate.mvcrawler.model.vo.MvSourceVO;
import com.lgfei.tool.spider.operate.mvcrawler.service.MvInfoService;
import com.lgfei.tool.spider.operate.mvcrawler.service.MvSourceService;

@Service
public class MvInfoServiceImpl implements MvInfoService
{
    private static Logger logger = LoggerFactory.getLogger(MvInfoServiceImpl.class);
    
    @Autowired
    private MvInfoDao mvInfoDao;
    
    @Autowired
    private MvSourceService mvSourceService;
    
    @Override
    public void insert(MvInfoVO vo)
        throws SystemException
    {
        if (null == vo)
        {
            return;
        }
        try
        {
            mvInfoDao.insert(vo);
            addMvSource(vo);
        }
        catch (Exception e)
        {
            logger.debug("影片：{}入库失败,{}", vo.getName(), e.getMessage());
            if (e instanceof DuplicateKeyException)
            {
                MvInfoVO dbVO = mvInfoDao.query(vo);
                if (null != dbVO)
                {
                    dbVO.setWebsiteId(vo.getWebsiteId());
                    dbVO.setSourceUrls(vo.getSourceUrls());
                    addMvSource(dbVO);
                }
            }
        }
    }
    
    private void addMvSource(MvInfoVO vo)
        throws SystemException
    {
        String sourceUrls = vo.getSourceUrls();
        if (StringUtils.isEmpty(sourceUrls))
        {
            return;
        }
        
        List<String> exixtsUrls = new ArrayList<>();
        
        MvSourceVO paramVO = new MvSourceVO();
        paramVO.setMvId(vo.getMvId());
        List<MvSourceVO> exixtsSources = mvSourceService.findList(paramVO);
        for (MvSourceVO mvSourceVO : exixtsSources)
        {
            exixtsUrls.add(mvSourceVO.getSourceUrl());
        }
        
        String[] urls = sourceUrls.split(",");
        List<MvSourceVO> sourceList = new ArrayList<>();
        for (String url : urls)
        {
            if (exixtsUrls.contains(url))
            {
                continue;
            }
            MvSourceVO sourceVO = new MvSourceVO();
            sourceVO.setSourceId(CreateIDUtil.businessID(TablesEnum.T_MV_SOURCE.getModule()));
            sourceVO.setMvId(vo.getMvId());
            sourceVO.setWebsiteId(vo.getWebsiteId());
            sourceVO.setSourceUrl(url);
            
            sourceList.add(sourceVO);
        }
        // 插入播放源
        mvSourceService.batchInsert(sourceList);
    }
    
    @Override
    public void findPageList(QueryMvPageListReqData data, PageResultResponse<MvInfoVO> resp)
        throws SystemException
    {
        MvInfoVO vo = new MvInfoVO();
        vo.setMvId(data.getMvId());
        vo.setName(data.getName());
        PageVO pageVO = new PageVO();
        pageVO.setPageNum(data.getPageNum());
        pageVO.setPageSize(data.getPageSize());
        
        List<MvInfoVO> list = mvInfoDao.queryMvPageList(vo, pageVO);
        
        resp.setTotal(pageVO.getTotal());
        resp.setRows(list);
    }
    
    @Override
    public void batchDelete(DeleteMvReqData data, BaseResponse resp)
        throws SystemException
    {
        List<MvInfoVO> voList = data.getMvInfos();
        if (CollectionUtils.isEmpty(voList))
        {
            return;
        }
        Set<String> mvIds = new HashSet<>();
        for (MvInfoVO mvInfoVO : voList)
        {
            mvIds.add(mvInfoVO.getMvId());
        }
        // 删除片源
        mvSourceService.deleteByMvIds(mvIds);
        // 删除影片
        mvInfoDao.batchDelete(voList);
    }
    
    @Override
    public void batchUpdate(UpdateMvReqData data, BaseResponse resp)
        throws SystemException
    {
        List<MvInfoVO> voList = data.getMvInfos();
        if (CollectionUtils.isEmpty(voList))
        {
            return;
        }
        mvInfoDao.batchUpdate(voList);
    }
    
}
