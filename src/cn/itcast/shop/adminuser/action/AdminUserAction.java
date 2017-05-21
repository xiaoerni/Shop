package cn.itcast.shop.adminuser.action;

import org.apache.struts2.ServletActionContext;

import cn.itcast.shop.adminuser.service.AdminUserService;
import cn.itcast.shop.adminuser.vo.AdminUser;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * ��̨��¼��Action
 * @author wrx
 *
 */
public class AdminUserAction extends ActionSupport implements ModelDriven<AdminUser>{
	//ģ������ʹ�õĶ���
	private AdminUser adminUser = new AdminUser();
	public AdminUser getModel() {
		return adminUser;
	}
	//ע��Service��
	private AdminUserService adminUserService;
	public void setAdminUserService(AdminUserService adminUserService) {
		this.adminUserService = adminUserService;
	}

	/**
	 * ��̨��¼�ķ���
	 */
	public String login(){
		//����service��ɵ�¼
		AdminUser existAdminUser = adminUserService.login(adminUser);
		if(existAdminUser == null){
			//��¼ʧ��
			this.addActionError("�ף������û��������������");
			return "loginFail";
		}else{
			//��¼�ɹ�
			ServletActionContext.getRequest().getSession().setAttribute("existAdminUser",existAdminUser);
			return "loginSuccess";
		}
		
	}
}
