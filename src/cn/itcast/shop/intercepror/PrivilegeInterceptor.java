package cn.itcast.shop.intercepror;

import org.apache.struts2.ServletActionContext;

import cn.itcast.shop.adminuser.vo.AdminUser;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**
 * ��̨Ȩ��У���������
 * *��û�е�¼���û��ǲ����Է��ʵ�
 * @author wrx
 *
 */
public class PrivilegeInterceptor extends MethodFilterInterceptor{

	@Override
	//ִ�����صķ�����
	protected String doIntercept(ActionInvocation actionInvocation) throws Exception {
		AdminUser existAdminUser = (AdminUser) ServletActionContext.getRequest().getSession().getAttribute("existAdminUser");
		if(existAdminUser == null){
			//û�е�¼���з���
			ActionSupport actionSupport = (ActionSupport) actionInvocation.getAction();
			actionSupport.addActionError("�ף�����û�е�¼��û��Ȩ�޷��ʣ�");
			return "loginFail";
		}else{
			//�Ѿ���¼��
			return actionInvocation.invoke();
		}
		
	}
	
}
