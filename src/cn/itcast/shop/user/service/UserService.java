package cn.itcast.shop.user.service;


import org.springframework.transaction.annotation.Transactional;

import cn.itcast.shop.user.dao.UserDao;
import cn.itcast.shop.user.vo.User;
import cn.itcast.shop.utils.MailUitls;
import cn.itcast.shop.utils.UUIDUtils;
/**
 * �û�ģ��ҵ������
 * @author wrx
 *
 */
@Transactional
public class UserService {
	//ע��UserDao
	private UserDao userDao;
	
	public void setUserDao(UserDao userDao){
		this.userDao = userDao;
	}
	//���û�����ѯ�û��ķ�����
	public User findByUsername(String username){
		return userDao.findByUsername(username);
	}
	
	//ҵ�������û�ע����룻
	public void save(User user) {
		// �����ݴ��뵽���ݿ�
		user.setState(0); // 0:�����û�δ����.  1:�����û��Ѿ�����.
		String code = UUIDUtils.getUUID()+UUIDUtils.getUUID();
		user.setCode(code);
		userDao.save(user);
		// ���ͼ����ʼ�;
		MailUitls.sendMail(user.getEmail(), code);
		
	}
	
	//ҵ�����ݼ������ѯ�û�
	public User findByCode(String code) {
		
		return userDao.findByCode(code);
	}
	
	//�޸��û�״̬�ķ���
	public void update(User existUser) {
		userDao.update(existUser);
		
	}
	
	//�û���¼ѽ�ķ���
	public User login(User user) {
		
		return userDao.login(user);
	}
	
}
