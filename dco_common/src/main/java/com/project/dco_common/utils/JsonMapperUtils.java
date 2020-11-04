package com.project.dco_common.utils;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.stream.Collectors;

public class JsonMapperUtils {

	public static String getJsonFromRequest(HttpServletRequest httpServletRequest) throws IOException {
		return httpServletRequest.getReader().lines().collect(Collectors.joining());
	}

	public static boolean isRequestBodyFormat(HttpServletRequest httpServletRequest, Class<?> clzz) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(Feature.STRICT_DUPLICATE_DETECTION, Feature.IGNORE_UNDEFINED);
			mapper.readValue(getJsonFromRequest(httpServletRequest), clzz);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}
