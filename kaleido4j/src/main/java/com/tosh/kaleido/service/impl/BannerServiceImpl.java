package com.tosh.kaleido.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tosh.kaleido.enums.BannerStatus;
import com.tosh.kaleido.mapper.BannerMapper;
import com.tosh.kaleido.model.entity.Banner;
import com.tosh.kaleido.model.vo.PageData;
import com.tosh.kaleido.model.vo.SortNoBounds;
import com.tosh.kaleido.service.IBannerService;

@Component
public class BannerServiceImpl implements IBannerService {

	@Autowired
	private BannerMapper mapper;

	@Override
	public PageData<Banner> getPages(long currentPage, long size) {
		LambdaQueryWrapper<Banner> wrapper = Wrappers.lambdaQuery();
		wrapper.orderByAsc(Banner::getSortNo);
		Page<Banner> page = new Page<Banner>(currentPage, size, true);
		IPage<Banner> iPage = mapper.selectPage(page, wrapper);
		return new PageData<Banner>(iPage.getPages(), iPage.getTotal(), iPage.getRecords());
	}

	@Override
	public boolean addBanner(Banner banner) {
		SortNoBounds bounds = getSortNoBounds();

		// 非递增排序
		if (banner.getSortNo().compareTo(bounds.getMax()) <= 0) {
			LambdaUpdateWrapper<Banner> wrapper = Wrappers.lambdaUpdate();
			wrapper.ge(Banner::getSortNo, banner.getSortNo()).setSql("sort_no=sort_no+1");

			// 更新排序
			if (mapper.update(null, wrapper) < 0) {
				throw new IllegalStateException("更新失败");
			}
		}

		// 插入数据
		if (mapper.insert(banner) <= 0) {
			throw new IllegalStateException("添加失败");
		}
		return true;
	}

	@Override
	public boolean updateBanner(Banner newer, Banner proto) {
		String protoSortNo = proto.getSortNo();
		String newSortNo = newer.getSortNo();

		// 更新排序
		if (!proto.getSortNo().equals(newer.getSortNo())) {
			LambdaUpdateWrapper<Banner> wrapper = Wrappers.lambdaUpdate();
			if (newSortNo.compareTo(protoSortNo) > 0) {
				wrapper.gt(Banner::getSortNo, protoSortNo).le(Banner::getSortNo, newSortNo).setSql("sort_no=sort_no-1");
			} else {
				wrapper.ge(Banner::getSortNo, newSortNo).lt(Banner::getSortNo, protoSortNo).setSql("sort_no=sort_no+1");
			}
			// 修改排序至Bounds之外，会出现更新0条的情况
			if (mapper.update(null, wrapper) < 0) {
				throw new IllegalStateException();
			}
		}
		// 更新数据
		if (mapper.updateById(newer) != 1) {
			throw new IllegalStateException();
		}
		return true;
	}

	@Override
	public boolean deleteBanner(String id) {
		Banner banner = mapper.selectById(id);
		if (!BannerStatus.OFFLINE.status().equals(banner.getStatus())) {
			return false;
		}
		if (mapper.deleteById(id) != 1) {
			throw new IllegalStateException();
		}
		// 更新排序
		LambdaUpdateWrapper<Banner> wrapper = Wrappers.lambdaUpdate();
		wrapper.setSql("sort_no=sort_no-1").gt(Banner::getSortNo, banner.getSortNo());
		mapper.update(null, wrapper);
		return true;
	}

	@Override
	public SortNoBounds getSortNoBounds() {
		List<String> boundList = mapper.selectSortNoBounds();
		// 数据库没有数据的场景
		if (boundList == null || boundList.isEmpty() || StringUtils.isEmpty(boundList.get(0))) {
			return new SortNoBounds("0", "0");
		}
		// 数据库一条和多条数据的场景
		SortNoBounds bounds = new SortNoBounds(boundList.get(0),
				boundList.size() > 1 ? boundList.get(1) : boundList.get(0));
		return bounds;
	}
}
