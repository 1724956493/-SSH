package com.nts.teststruts.interceptor;

import java.util.Map;

import com.nts.teststruts.struts.action.UserAction;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class UserAuthorityInterceptor extends AbstractInterceptor {

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		Object o = invocation.getAction();  
        System.out.println(o.getClass());  
        if(o instanceof UserAction){  
            return invocation.invoke();  
        }  
		
		ActionContext ctx = invocation.getInvocationContext();
		  Map session = ctx.getSession();
		  // 取出名为user的session属性
		  String cuserid = (String)session.get("cuserid");
		  // 如果没有登陆， 返回重新登陆
		  if (cuserid != null) {
		   return invocation.invoke();
		  }
		  // 没有登陆，将服务器提示设置成一个HttpServletRequest属性
		  ctx.put("tip", "您还没有登录，请登陆系统");
		return null;
	}

}
