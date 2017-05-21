package cn.itcast.shop.adminuser.service;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.shop.adminuser.dao.AdminUserDao;
import cn.itcast.shop.adminuser.vo.AdminUser;

/**
 * 后台登录的Service类
 * @author wrx
 *
 */
@Transactional
public class AdminUserService {
	//注入Dao
	private AdminUserDao adminUserDao;

	public void setAdminUserDao(AdminUserDao adminUserDao) {
		this.adminUserDao = adminUserDao;
	}

	/**
	 * 业务层登陆的方法
	 * @param adminUser
	 * @return
	 */
	public AdminUser login(AdminUser adminUser) {
		
		return adminUserDao.login(adminUser);
	}
	
}
