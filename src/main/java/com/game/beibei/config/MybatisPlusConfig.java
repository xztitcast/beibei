package com.game.beibei.config;

import java.util.Date;

import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;

@Configuration
@MapperScan("com.game.beibei.mapper")
public class MybatisPlusConfig {

	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
		interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
		interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
		return interceptor;
	}
	
	@Component
	public static class WalletMetaObjectHandler implements MetaObjectHandler {
		
		private static final String CREATED = "created";
		
		private static final String UPDATED = "updated";

		@Override
		public void insertFill(MetaObject metaObject) {
			Date date = new Date();
			this.strictInsertFill(metaObject, CREATED, Date.class, date);
			this.strictInsertFill(metaObject, UPDATED, Date.class, date);
		}

		@Override
		public void updateFill(MetaObject metaObject) {
			this.strictUpdateFill(metaObject, UPDATED, Date.class, new Date());
		}
	}
}
