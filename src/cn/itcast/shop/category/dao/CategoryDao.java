package cn.itcast.shop.category.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.itcast.shop.category.vo.Category;

/**
 * һ������ĳ־ò����
 * @author wrx
 *
 */
public class CategoryDao extends HibernateDaoSupport{

	//DAO��Ĳ�ѯ����һ������ķ���
	public List<Category> findAll() {
		String hql = "from Category";
		List<Category> list = this.getHibernateTemplate().find(hql);
		return list;
	}

	//DAO�㱣��һ������ķ���
	public void save(Category category) {
		this.getHibernateTemplate().save(category);
	}

	//DAO��ĸ���cid��ѯһ������ķ���
	public Category findByCid(Integer cid) {
		return this.getHibernateTemplate().get(Category.class, cid);
	}

	//DAO��ɾ��һ������ķ���
	public void delete(Category category) {
		this.getHibernateTemplate().delete(category);
	}

	//DAo����޸�һ������ķ���
	public void update(Category category) {
		this.getHibernateTemplate().update(category);
	}

}
