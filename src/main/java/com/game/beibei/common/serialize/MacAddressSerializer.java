package com.game.beibei.common.serialize;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class MacAddressSerializer extends JsonSerializer<String> {

	@Override
	public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		if(StringUtils.isNotBlank(value)) {
			String mac = value.substring(4).replaceAll("(.{2})", "$1-");
			mac = mac.substring(0, mac.length() - 1);
			gen.writeString(mac.toUpperCase());
		}else {
			gen.writeString(value);
		}
	}
	
}
