package com.tosh.kaleido.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class SortNoBounds {
	/**
	 * 最小值
	 */
	private String min;
	/**
	 * 最大值
	 */
	private String max;
}
