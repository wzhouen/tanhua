package com.xuwen.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuwen.pojo.db.BlackList;
import com.xuwen.pojo.db.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BlackListDao extends BaseMapper<BlackList> {

    @Select(value = "select tui.id,tui.avatar,tui.nickname,tui.gender,tui.age from  tb_user_info tui,tb_black_list tbl where tui.id = tbl.black_user_id and tbl.user_id=#{userId}")
    IPage<UserInfo> findBlackList(Page<UserInfo> pg, @Param("userId") Long userId);
}
