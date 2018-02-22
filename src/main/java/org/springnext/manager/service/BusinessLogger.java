package org.springnext.manager.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 打印业务日志，格式为:
 * 
 * 日期,实体类型,操作类型 ,操作用户,json格式的扩展字段
 * 
 * @author HyDe
 */
@Component
public class BusinessLogger {
	public static final String BUSINESS_LOGGER_NAME = "business";

	private Logger businessLogger = LoggerFactory.getLogger(BUSINESS_LOGGER_NAME);
	private ObjectMapper mapper = new ObjectMapper();;

	public void log(String entity, String action, String user, Map<Object,Object> data) {
		String json;
		try {
			json = (data != null ? mapper.writeValueAsString(data) : "{}");
		} catch (JsonProcessingException e) {
			json = "";
		}
		businessLogger.info("{},{},{},{}", entity, action, user, json);
	}
}
