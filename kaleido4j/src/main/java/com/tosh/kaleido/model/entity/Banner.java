package com.tosh.kaleido.model.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Banner implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 唯一标识，主键
	 */
	@TableId
	private String id;

	/**
	 * 标题
	 */
	private String bannerName;

	/**
	 * 图片地址
	 */
	private String pictureUrl;

	/**
	 * 图片大小
	 */
	private String imageSize;

	/**
	 * 超链接
	 */
	private String hyperLink;

	/**
	 * 排序号
	 */
	@TableField(value = "sort_no")
	private String sortNo;

	/**
	 * 创建者
	 */
	@TableField(fill = FieldFill.INSERT)
	private String creater;

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private String createTime;

	/**
	 * 更新者
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String updater;

	/**
	 * 更新时间
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String updateTime;

	/**
	 * 发布者
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String publisher;

	/**
	 * 发布时间
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String publishTime;

	/**
	 * 状态码，1上线，0下线
	 */
	private String status;
}
