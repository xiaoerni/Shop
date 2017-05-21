package cn.itcast.shop.intercepror;

import org.apache.struts2.ServletActionContext;

import cn.itcast.shop.adminuser.vo.AdminUser;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**
 * 后台权限校验的拦截器
 * *对没有登录的用户是不可以访问的
 * @author wrx
 *
 */
public class PrivilegeInterceptor extends MethodFilterInterceptor{

	@Override
	//执行拦截的方法：
	protected String doIntercept(ActionInvocation actionInvocation) throws Exception {
		AdminUser existAdminUser = (AdminUser) ServletActionContext.getRequest().getSession().getAttribute("existAdminUser");
		if(existAdminUser == null){
			//没有登录进行访问
			ActionSupport actionSupport = (ActionSupport) actionInvocation.getAction();
			actionSupport.addActionError("亲！您还没有登录！没有权限访问！");
			return "loginFail";
		}else{
			//已经登录过
			return actionInvocation.invoke();
		}
		
	}
	
}
