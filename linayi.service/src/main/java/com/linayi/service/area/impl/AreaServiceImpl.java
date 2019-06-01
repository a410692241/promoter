package com.linayi.service.area.impl;

import com.linayi.dao.area.AreaMapper;
import com.linayi.dao.area.SmallCommunityMapper;
import com.linayi.dao.community.CommunityMapper;
import com.linayi.entity.area.Area;
import com.linayi.entity.area.SmallCommunity;
import com.linayi.entity.area.SmallCommunityFullName;
import com.linayi.entity.community.Community;
import com.linayi.service.area.AreaService;
import com.linayi.service.order.OrderService;
import com.linayi.util.CheckUtil;
import com.linayi.util.PageResult;
import com.linayi.vo.promoter.PromoterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AreaServiceImpl implements AreaService {
    @Resource
    private AreaMapper areaMapper;
    @Autowired
    private SmallCommunityMapper smallCommunityMapper;
    @Autowired
    private CommunityMapper communityMapper;
    @Autowired
    private OrderService orderService;

    @Override
    public Map<String, List<Area>> getArea() {
        List<Area> areaList = areaMapper.getArea();
        Map<String, List<Area>> areaGroupByLevel = areaList.stream()
                .collect(Collectors.groupingBy(Area::getParent));
        return areaGroupByLevel;
    }

    @Override
    public List<Area> getArea(Area area) {
        List<Area> allArea = getAllAreaList();
        List<Area> areas = allArea.stream().filter(item -> item.getParent().equals(area.getParent())).collect(Collectors.toList());
        return areas;


    }


    @Override
    public List<Area> getProvince() {
        return areaMapper.getProvince();
    }

    @Override
    public List<Area> listAreaInfo(Area area) {
        return areaMapper.listAreaInfo(area);
    }

    @Override
    public void addArea(Area area) {
        //使用上级编号生成子集编号
        String parent = area.getParent();
        Area areaParam = new Area();
        areaParam.setParent(parent);
        List<Area> areaList = areaMapper.query(areaParam);
        String newCode;
        //存在已知最大code则+1,否则在父code上加000001
        if (areaList != null && areaList.size() > 0) {
            areaList.sort((a, b) -> (int) (Long.parseLong(b.getCode()) - Long.parseLong(a.getCode())));
            newCode = Long.parseLong(areaList.stream().findFirst().orElse(null).getCode()) + 1 + "";
        } else {
            newCode = parent + "000001";
        }
        area.setCode((newCode));
        areaMapper.insert(area);
    }


    @Override
    public List<Area> getAllArea() {
        List<Area> areas = new ArrayList<>();
        List<Area> areaList = areaMapper.query(new Area());

        //获取省集合
        List<Area> provinceList = areaList.stream().filter(area -> area.getLevel() == 1).collect(Collectors.toList());
        //获取市集合
        List<Area> shiList = areaList.stream().filter(area -> area.getLevel() == 2).collect(Collectors.toList());
        //获取区集合
        List<Area> quList = areaList.stream().filter(area -> area.getLevel() == 3).collect(Collectors.toList());
        //获取街道集合
        List<Area> streetList = areaList.stream().filter(area -> area.getLevel() == 4).collect(Collectors.toList());

        //查询小区,转化为areaCode集合
        List<SmallCommunity> allSmallCommunity = smallCommunityMapper.queryAllCommunity(new SmallCommunity());
        Set<Integer> communityIdList = allSmallCommunity.stream().map(item -> item.getCommunityId()).collect(Collectors.toSet());
        List<Community> communityList = communityMapper.getCommunityByCommunityIdList(new ArrayList<>(communityIdList));
        Set<String> areaCodeList = communityList.stream().map(item -> item.getAreaCode()).collect(Collectors.toSet());


        //分离出拥有小区的街道
        List<Area> StreetCodeList = streetList.stream().filter(item -> areaCodeList.contains(item.getCode())).collect(Collectors.toList());
        areas.addAll(StreetCodeList);
        //分离出拥有小区的区
        Set<String> quCodeList = StreetCodeList.stream().map(Area::getParent).collect(Collectors.toSet());
        List<Area> CodeQuList = quList.stream().filter(item -> quCodeList.contains(item.getCode())).collect(Collectors.toList());
        areas.addAll(CodeQuList);
        //分离出拥有小区的市
        Set<String> shiCodeList = CodeQuList.stream().map(item -> item.getParent()).collect(Collectors.toSet());
        List<Area> CodeShiList = shiList.stream().filter(item -> shiCodeList.contains(item.getCode())).collect(Collectors.toList());
        areas.addAll(CodeShiList);
        //分离出拥有小区的省
        Set<String> provinceCodeList = CodeShiList.stream().map(item -> item.getParent()).collect(Collectors.toSet());
        List<Area> resultProvinceList = provinceList.stream().filter(item -> provinceCodeList.contains(item.getCode())).collect(Collectors.toList());
        areas.addAll(resultProvinceList);

        //建立上下级数据关系
        Map<String, List<Area>> map = areas.stream().collect(Collectors.groupingBy(Area::getParent));
        resultProvinceList.parallelStream().forEach(item -> {
            String code = item.getCode();
            item.setChild(map.get(code));
        });
        CodeShiList.parallelStream().forEach(item -> {
            String code = item.getCode();
            item.setChild(map.get(code));
        });
        quList.parallelStream().forEach(item -> {
            String code = item.getCode();
            item.setChild(map.get(code));
        });
        return resultProvinceList;
    }

    private List<Area> getAllAreaList() {
        List<Area> areas = new ArrayList<>();
        List<Area> areaList = areaMapper.query(new Area());

        //获取省集合
        List<Area> provinceList = areaList.stream().filter(area -> area.getLevel() == 1).collect(Collectors.toList());
        //获取市集合
        List<Area> shiList = areaList.stream().filter(area -> area.getLevel() == 2).collect(Collectors.toList());
        //获取区集合
        List<Area> quList = areaList.stream().filter(area -> area.getLevel() == 3).collect(Collectors.toList());
        //获取街道集合
        List<Area> streetList = areaList.stream().filter(area -> area.getLevel() == 4).collect(Collectors.toList());

        //查询小区,转化为areaCode集合
        List<SmallCommunity> allSmallCommunity = smallCommunityMapper.queryAllCommunity(new SmallCommunity());
        Set<Integer> communityIdList = allSmallCommunity.stream().map(item -> item.getCommunityId()).collect(Collectors.toSet());
        List<Community> communityList = communityMapper.getCommunityByCommunityIdList(new ArrayList<>(communityIdList));
        Set<String> areaCodeList = communityList.stream().map(item -> item.getAreaCode()).collect(Collectors.toSet());

        //分离出拥有小区的街道
        List<Area> StreetCodeList = streetList.stream().filter(item -> areaCodeList.contains(item.getCode())).collect(Collectors.toList());
        areas.addAll(StreetCodeList);
        //分离出拥有小区的区
        Set<String> quCodeList = StreetCodeList.stream().map(Area::getParent).collect(Collectors.toSet());
        List<Area> CodeQuList = quList.stream().filter(item -> quCodeList.contains(item.getCode())).collect(Collectors.toList());
        areas.addAll(CodeQuList);
        //分离出拥有小区的市
        Set<String> shiCodeList = CodeQuList.stream().map(item -> item.getParent()).collect(Collectors.toSet());
        List<Area> CodeShiList = shiList.stream().filter(item -> shiCodeList.contains(item.getCode())).collect(Collectors.toList());
        areas.addAll(CodeShiList);
        //分离出拥有小区的省
        Set<String> provinceCodeList = CodeShiList.stream().map(item -> item.getParent()).collect(Collectors.toSet());
        List<Area> resultProvinceList = provinceList.stream().filter(item -> provinceCodeList.contains(item.getCode())).collect(Collectors.toList());
        areas.addAll(resultProvinceList);
        return areas;
    }

    @Override
    public List<Area> getAreaMap() {
        List<Area> areaList = areaMapper.query(new Area());
        //除去所有与国有关的地区数据
        List<Area> areas = areaList.stream().filter(area -> !("0".equals(area.getLevel() + ""))).collect(Collectors.toList());
        Map<String, List<Area>> map = areas.stream().collect(Collectors.groupingBy(Area::getParent));
        //获取省集合
        List<Area> provinceList = areaList.stream().filter(area -> area.getLevel() == 1).collect(Collectors.toList());
        provinceList.parallelStream().forEach(item -> {
            String code = item.getCode();
            item.setChild(map.get(code));
        });
        //获取市集合
        List<Area> shiList = areaList.stream().filter(area -> area.getLevel() == 2).collect(Collectors.toList());
        shiList.parallelStream().forEach(item -> {
            String code = item.getCode();
            item.setChild(map.get(code));
        });
        //获取区集合
        List<Area> quList = areaList.stream().filter(area -> area.getLevel() == 3).collect(Collectors.toList());
        quList.parallelStream().forEach(item -> {
            String code = item.getCode();
            item.setChild(map.get(code));
        });
        return provinceList;
    }

    @Override
    public PageResult<SmallCommunityFullName> getSmallCommunityByKey(PromoterVo.SearchSmallCommunityByKey searchSmallCommunityByKey) {
        String key = searchSmallCommunityByKey.getKey();
        Integer currentPage = searchSmallCommunityByKey.getCurrentPage();
        Integer pageSize = searchSmallCommunityByKey.getPageSize();
        List<SmallCommunityFullName> smallCommunityFullNames = new ArrayList<>();
        //防止初始化小区列表加载过慢
        SmallCommunity smallCommunity = new SmallCommunity();
        if (CheckUtil.isNullEmpty(key)) {
            return  new PageResult<>(smallCommunityFullNames,smallCommunity);
        }
        smallCommunity.setName(key);
        smallCommunity.setCurrentPage(currentPage);
        smallCommunity.setPageSize(pageSize);
        //模糊查询关键字的小区列表,还要满足小区绑定了communityId
        List<SmallCommunity> smallCommunityList = smallCommunityMapper.getBindedSmallCommunityList(smallCommunity);
        smallCommunityList.stream().forEach(item -> {
            SmallCommunityFullName smallCommunityFullName = new SmallCommunityFullName();
            smallCommunityFullName.setSmallCommunityId(item.getSmallCommunityId());

            //设置小区省市区全名
            String areaCode = item.getAreaCode();
            String name = item.getName();
            String areaName = orderService.getAreaNameByAreaCode(areaCode);
            smallCommunityFullName.setFullName(areaName);

            //设置小区的名字
            smallCommunityFullName.setName(name);
            smallCommunityFullNames.add(smallCommunityFullName);

        });
        PageResult<SmallCommunityFullName> goodsSkuPageResult = new PageResult<>(smallCommunityFullNames, smallCommunity);
//        goodsSkuPageResult.setTotalPage(smallCommunity.getTotal()/pageSize);
        return goodsSkuPageResult;
    }
}


