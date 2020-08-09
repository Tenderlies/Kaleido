package com.tosh.kaleido.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tosh.kaleido.model.vo.SortNoBounds;
import com.tosh.kaleido.service.IBannerService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/banner")
@Slf4j
public class BannerController {
	@Autowired
	private IBannerService service;

	@RequestMapping(value = "/bounds", method = RequestMethod.GET)
	public Map<String, Object> getBannersBounds() {
		log.debug("Get bounds start.");
		SortNoBounds bounds = service.getSortNoBounds();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("data", bounds);
		log.debug("Get bounds end.");
		return data;
	}

}
