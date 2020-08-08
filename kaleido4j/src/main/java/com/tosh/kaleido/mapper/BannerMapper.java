package com.tosh.kaleido.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tosh.kaleido.model.entity.Banner;

public interface BannerMapper extends BaseMapper<Banner> {
	@Select("SELECT MIN(sort_no) bound FROM t_banner UNION SELECT MAX(sort_no) bound FROM t_banner")
	List<String> selectSortNoBounds();
}
