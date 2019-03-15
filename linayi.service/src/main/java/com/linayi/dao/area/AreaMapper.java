package com.linayi.dao.area;

import com.linayi.entity.area.Area;

import java.util.List;

public interface AreaMapper {
    int insert(Area record);

    int insertSelective(Area record);

    /**
     * 获取地区信息
     *
     * @return
     */
    List<Area> getArea();

    /**
     * 获取省
     *
     * @return
     */
    List<Area> getProvince();

    /**
     * 通过街道名字搜索街道code
     * @param streetName
     * @return
     */
    List<String> getStreetCodeByStreetName(String streetName);

    /**
     * 通过街道名字搜索街道code
     * @param code
     * @return
     */
    String getNameByCode(String code);

    /**
     * 得到市区
     *
     * @param area
     * @return
     */
    List<Area> listAreaInfo(Area area);
   	/**
   	 * 通过area某个信息获取Area信息
   	 * @param area
   	 * @return
   	 */
    Area getAreaInfo(Area area);

    Integer updateByPrimaryKey(Area area);

    List<Area> query(Area area);

	Area selectByPrimaryKey(String code);

    Long queryTotal();

    Integer deleteByPrimaryKey(Integer code);


}