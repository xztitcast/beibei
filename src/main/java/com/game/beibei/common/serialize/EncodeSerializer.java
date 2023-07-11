package com.game.beibei.common.serialize;

import java.io.IOException;
import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import cn.hutool.core.util.CharsetUtil;

public class EncodeSerializer extends JsonSerializer<String> {

	@Override
	public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		if(StringUtils.isNotBlank(value)) {
			String encode = CharsetUtil.convert(value, CharsetUtil.ISO_8859_1, CharsetUtil.GBK);
			gen.writeString(encode);
		}else {
			gen.writeString(value);
		}
	}

}
