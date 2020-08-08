package com.tosh.kaleido.handler;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.tosh.kaleido.model.supporter.MpSupporter;

@Component
public class MPMetaObjectHandler implements MetaObjectHandler {

	private ThreadLocal<MpSupporter> supporter = new ThreadLocal<MpSupporter>();

	public MpSupporter getSupporter() {
		return supporter.get();
	}

	public void setSupporter(MpSupporter support) {
		this.supporter.set(support);
	}

	public void removeSupporter() {
		this.supporter.remove();
	}

	@Override
	public void insertFill(MetaObject metaObject) {
		if (supporter.get() == null) {
			return;
		}

		if (metaObject.hasSetter("creater") && getFieldValByName("creater", metaObject) == null) {
			this.strictInsertFill(metaObject, "creater", String.class, supporter.get().getUserName());
		}
		if (metaObject.hasSetter("createTime") && getFieldValByName("createTime", metaObject) == null) {
			this.strictInsertFill(metaObject, "createTime", String.class, supporter.get().getDateTime());
		}
		if (metaObject.hasSetter("updater") && getFieldValByName("updater", metaObject) == null) {
			this.strictInsertFill(metaObject, "updater", String.class, supporter.get().getUserName());
		}
		if (metaObject.hasSetter("updateTime") && getFieldValByName("updateTime", metaObject) == null) {
			this.strictInsertFill(metaObject, "updateTime", String.class, supporter.get().getDateTime());
		}
		if (supporter.get().isPublish()) {
			if (metaObject.hasSetter("publisher") && getFieldValByName("publisher", metaObject) == null) {
				this.strictInsertFill(metaObject, "publisher", String.class, supporter.get().getUserName());
			}
			if (metaObject.hasSetter("publishTime") && getFieldValByName("publishTime", metaObject) == null) {
				this.strictInsertFill(metaObject, "publishTime", String.class, supporter.get().getDateTime());
			}
		}
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		if (supporter.get() == null) {
			return;
		}
		if (metaObject.hasSetter("updater")) {
			this.strictUpdateFill(metaObject, "updater", String.class, supporter.get().getUserName());
		}
		if (metaObject.hasSetter("updateTime")) {
			this.strictUpdateFill(metaObject, "updateTime", String.class, supporter.get().getDateTime());
		}
		if (supporter.get().isPublish()) {
			if (metaObject.hasSetter("publisher")) {
				this.strictUpdateFill(metaObject, "publisher", String.class, supporter.get().getUserName());
			}
			if (metaObject.hasSetter("publishTime")) {
				this.strictUpdateFill(metaObject, "publishTime", String.class, supporter.get().getDateTime());
			}
		}
	}
}
