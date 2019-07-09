package cn.zzybzf.controller.base;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import cn.zzybzf.util.PageData;

/**
 * @author Squre 修改时间：2015、12、11
 */
public class BaseController {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * new PageData对象
	 * 
	 * @return
	 */
	public PageData getPageData() {
		return new PageData(this.getRequest());
	}

	/**
	 * 得到ModelAndView
	 * 
	 * @return
	 */
	public ModelAndView getModelAndView() {
		return new ModelAndView();
	}

	/**
	 * 得到request对象
	 * 
	 * @return
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		return request;
	}

	/**
	 * 得到32位的uuid
	 * 
	 * @return
	 */
	public String getUUID() {
		return UUID.randomUUID().toString();
	}

	public static void logBefore(Logger logger, String interfaceName) {
		logger.info("");
		logger.info("start");
		logger.info(interfaceName);
	}

	public static void logAfter(Logger logger) {
		logger.info("end");
		logger.info("");
	}

	public Map getMap(HttpServletRequest request, String propertys) {
		Map<String, String> map = new HashMap<String, String>();
		String[] pps = propertys.split(",");
		for (int i = 0; i < pps.length; i++) {
			if (!"".equals(StrNullTo(request.getParameter(pps[i])))) {
				map.put(pps[i], StrNullTo(request.getParameter(pps[i])));
			}
			logger.info(pps[i] + "---" + map.get(pps[i]));
		}

		return map;
	}

	public String StrNullTo(String str) {
		if (StringUtils.isEmpty(str) || str.equals("null") || str.equals("Null")) {
			str = "";
		}
		return str;
	}

	public Map getMap(String result) {
		Map map2 = new HashMap();
		String[] str = result.split(",");
		for (int i = 0; i < str.length; i++) {
			map2.put(str[i], "");
		}
		return map2;
	}

}
