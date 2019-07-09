package cn.zzybzf.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cn.zzybzf.controller.base.BaseController;
import cn.zzybzf.dao.DAO;
import cn.zzybzf.util.PageData;

@RestController
@Scope("prototype")
public class HelloController extends BaseController{
	
	@Resource(name = "daoSupport")
	private DAO daoSupport;
	private final Logger log = LoggerFactory.getLogger(getClass());

	@RequestMapping("/hello")
	public Object hello() throws Exception {
		Object list = daoSupport.findForList("TaxMapper.hello", null);
		return list;
	}
	
	@RequestMapping("/toHello")
	public ModelAndView toHello() {
		PageData pd = this.getPageData();
		String name = pd.getString("name");
		pd.put("hello", "hello, " + (name != null ? name : "world!"));
		return new ModelAndView("index").addObject("pd", pd);
	}
	
}
