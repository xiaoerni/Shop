package cn.itcast.shop.category.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.itcast.shop.category.vo.Category;

/**
 * 一级分类的持久层对象
 * @author wrx
 *
 */
public class CategoryDao extends HibernateDaoSupport{

	//DAO层的查询所有一级分类的方法
	public List<Category> findAll() {
		String hql = "from Category";
		List<Category> list = this.getHibernateTemplate().find(hql);
		return list;
	}

	//DAO层保存一级分类的方法
	public void save(Category category) {
		this.getHibernateTemplate().save(category);
	}

	//DAO层的根据cid查询一级分类的方法
	public Category findByCid(Integer cid) {
		return this.getHibernateTemplate().get(Category.class, cid);
	}

	//DAO层删除一级分类的方法
	public void delete(Category category) {
		this.getHibernateTemplate().delete(category);
	}

	//DAo层的修改一级分类的方法
	public void update(Category category) {
		this.getHibernateTemplate().update(category);
	}

}
