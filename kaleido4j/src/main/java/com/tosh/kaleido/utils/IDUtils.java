package com.tosh.kaleido.utils;

import com.baomidou.mybatisplus.core.toolkit.Sequence;

public class IDUtils {

	private Sequence seq = new Sequence();

	public static long nextSnowFlakeId() {
		return new Sequence().nextId();
	}

	public long nextId() {
		return seq.nextId();
	}
}
