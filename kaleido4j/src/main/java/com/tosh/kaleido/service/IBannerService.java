package com.tosh.kaleido.service;

import com.tosh.kaleido.model.entity.Banner;
import com.tosh.kaleido.model.vo.PageData;
import com.tosh.kaleido.model.vo.SortNoBounds;

public interface IBannerService {
	PageData<Banner> getPages(long currentPage, long size);

	boolean addBanner(Banner banner);

	boolean updateBanner(Banner banner, Banner protoBanner);

	boolean deleteBanner(String id);

	SortNoBounds getSortNoBounds();
}
