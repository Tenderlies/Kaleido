package com.tosh.kaleido.model.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class PageData<T> {
	/**
	 * 总页数
	 */
	private Long pages;
	/**
	 * 总条数
	 */
	private Long counts;
	/**
	 * 数据
	 */
	private List<T> data;
}
