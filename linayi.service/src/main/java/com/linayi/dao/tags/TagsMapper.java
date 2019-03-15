package com.linayi.dao.tags;

import java.util.Map;

public interface TagsMapper {

    public Object sqlQuery();
    public Object categoryList(Map map);
    public Object selectCommunityList(Map map);
}
