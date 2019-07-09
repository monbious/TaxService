package cn.zzybzf.controller.tax;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.zzybzf.controller.base.BaseController;
import cn.zzybzf.dao.DAO;
import cn.zzybzf.util.PageData;

@RequestMapping("/tax")
@Scope("prototype")
@RestController
public class AmountInfoController extends BaseController{
	
	@Resource(name = "daoSupport")
	private DAO daoSupport;
	private final Logger log = LoggerFactory.getLogger(getClass());

	@RequestMapping("/getAmountInfos")
	public Object getCurrentInfo() throws Exception {
		PageData pd = this.getPageData();
		pd.put("ACCOUNT_STR", "0");
		pd.put("ACCOUNT", "6228480728686520178");
		pd.put("ACCOUNT_NAME", "XXX");
		pd.put("IN_OR_OUT", "in");
		PageData inMap = new PageData();
		inMap.putAll(pd);
		deepSelect(inMap, 1);
		pd.put("IN_OR_OUT", "out");
		PageData outMap = new PageData();
		outMap.putAll(pd);
		deepSelect(outMap, 1);
		pd.put("IN_MAP", inMap);
		pd.put("OUT_MAP", outMap);
		
		return pd;
	}
	
	private void deepSelect(PageData pd, int num) throws Exception {
		String in_or_out = pd.getString("IN_OR_OUT");
		List<Map<String, Object>> list = new ArrayList<>();
		pd.put("CHILDRENS", list);
		// 把子账号不重复查询出来
		logger.info("倒数第 " + (num - 1) + " 遍递归");
		List<PageData> childrenAccount = (List<PageData>) daoSupport.findForList("TaxMapper.getChildrenAccounts", pd);
		String accounts = "";
		for (PageData pageData : childrenAccount) {
			accounts += pageData.getString("OTHER_ACCOUNT") + ",";
		}
		for (PageData pageData : childrenAccount) {
			// 把上一个账号加到p_account
			list.add(pageData);
			String account = pageData.getString("OTHER_ACCOUNT");
			pageData.put("ACCOUNT", account);
			String other_account_name = pageData.getString("OTHER_ACCOUNT_NAME");
			pageData.put("ACCOUNT_NAME", other_account_name != null ? other_account_name.split(",")[0] : "none");
			pageData.remove("OTHER_ACCOUNT");
			pageData.remove("OTHER_ACCOUNT_NAME");
			String[] detailsArr = pageData.get("DETAILS").toString().split(",");
			Arrays.sort(detailsArr, String::compareTo);
			String SORTEDDETAILS = Arrays.toString(detailsArr).replaceAll("[\\[|\\]]", "");
			SORTEDDETAILS = "交易 " + pageData.get("TRADE_NUM").toString() + " 次；共 ￥" + pageData.get("TRADE_TOTAL").toString() + "," + SORTEDDETAILS;
			pageData.put("DETAILS", SORTEDDETAILS);
			//pageData.put("INOROUT", inorout);
			if (num > 1 && !pd.getString("accounts").contains(account)) {
				//避免查询死循环
				pageData.put("accounts", pd.getString("accounts") + "," + accounts);
				deepSelect(pageData, num - 1);
			}
		}
	}
	
}
