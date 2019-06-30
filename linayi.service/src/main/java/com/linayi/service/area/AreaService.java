package com.linayi.service.area;

import java.util.List;
import java.util.Map;

import com.linayi.entity.area.Area;
import com.linayi.entity.area.SmallCommunityFullName;
import com.linayi.entity.community.Community;
import com.linayi.util.PageResult;
import com.linayi.vo.promoter.PromoterVo;

public interface AreaService {
	/**
     * 获取地区信息 
     * @return
     */
	Map<String, List<Area>> getArea();

    /**通过父级查子集
     * @param area
     * @return
     */
    List<Area> getArea(Area area);

    /**
     * 获取省
     * @return
     */
    List<Area> getProvince();
    
    /**
     * 得到市区
     * @param area
     * @return
     */
    List<Area> listAreaInfo(Area area);

    /**新增地址（省市区街道小区）
     * @param area
     */
    void addArea(Area area);

    /**查询所有存在服务点的地区(分层次一次性获取)
     * @return
     */
    List<Area> getAllArea();

    /**
     * @return 获取所有的地区
     */
    List<Area> getAreaMap();

    /**通过名字获取模糊搜索的小区
     * @param key
     * @return
     */
    PageResult<SmallCommunityFullName> getSmallCommunityByKey(PromoterVo.SearchSmallCommunityByKey key);

    List<Area> getAllStreet(Area area);

    Area getByPrimaryKey(String code);
}
