package com.tosh.kaleido.config.handler;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.tosh.kaleido.config.Operator;

@Component
public class MPMetaObjectHandler implements MetaObjectHandler {

	@Autowired
	private Operator operator;

	@Override
	public void insertFill(MetaObject metaObject) {
		if (StringUtils.isEmpty(operator.getUser())) {
			return;
		}

		if (metaObject.hasSetter("creator") && getFieldValByName("creator", metaObject) == null) {
			this.strictInsertFill(metaObject, "creater", String.class, operator.getUser());
		}
		if (metaObject.hasSetter("createTime") && getFieldValByName("createTime", metaObject) == null) {
			this.strictInsertFill(metaObject, "createTime", String.class, operator.getOperateTime());
		}
		if (metaObject.hasSetter("updater") && getFieldValByName("updater", metaObject) == null) {
			this.strictInsertFill(metaObject, "updater", String.class, operator.getUser());
		}
		if (metaObject.hasSetter("updateTime") && getFieldValByName("updateTime", metaObject) == null) {
			this.strictInsertFill(metaObject, "updateTime", String.class, operator.getOperateTime());
		}
		if (operator.isPublish()) {
			if (metaObject.hasSetter("publisher") && getFieldValByName("publisher", metaObject) == null) {
				this.strictInsertFill(metaObject, "publisher", String.class, operator.getUser());
			}
			if (metaObject.hasSetter("publishTime") && getFieldValByName("publishTime", metaObject) == null) {
				this.strictInsertFill(metaObject, "publishTime", String.class, operator.getOperateTime());
			}
		}
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		if (StringUtils.isEmpty(operator.getUser())) {
			return;
		}
		if (metaObject.hasSetter("updater")) {
			this.strictUpdateFill(metaObject, "updater", String.class, operator.getUser());
		}
		if (metaObject.hasSetter("updateTime")) {
			this.strictUpdateFill(metaObject, "updateTime", String.class, operator.getOperateTime());
		}
		if (operator.isPublish()) {
			if (metaObject.hasSetter("publisher")) {
				this.strictUpdateFill(metaObject, "publisher", String.class, operator.getUser());
			}
			if (metaObject.hasSetter("publishTime")) {
				this.strictUpdateFill(metaObject, "publishTime", String.class, operator.getOperateTime());
			}
		}
	}
}
