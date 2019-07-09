package cn.zzybzf.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

@Configuration
public class DruidConfiguration {

	@Bean
	public ServletRegistrationBean DruidStatViewServle() {

		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),
				"/druid/*");

		servletRegistrationBean.addInitParameter("allow", "127.0.0.1");

		servletRegistrationBean.addInitParameter("deny", "");

		servletRegistrationBean.addInitParameter("loginUsername", "admin");

		servletRegistrationBean.addInitParameter("loginPassword", "123456");

		servletRegistrationBean.addInitParameter("resetEnable", "false");

		return servletRegistrationBean;

	}

	@Bean
	public FilterRegistrationBean druidStatFilter() {

		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());

		filterRegistrationBean.addUrlPatterns("/*");

		// 添加不需要忽略的格式信息.
		filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");

		return filterRegistrationBean;

	}

}