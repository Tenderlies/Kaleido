package com.tosh.kaleido.main;

import java.lang.management.ManagementFactory;

import com.alibaba.fastjson.JSON;

public class MagicMain {

	public static void main(String[] args) {
		System.out.println(ManagementFactory.getRuntimeMXBean().getName());
		System.out.println(JSON.toJSONString(ManagementFactory.getRuntimeMXBean().getSystemProperties()));
	}
}
