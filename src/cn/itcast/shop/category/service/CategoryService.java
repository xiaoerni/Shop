package cn.itcast.shop.category.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.shop.category.dao.CategoryDao;
import cn.itcast.shop.category.vo.Category;

/**
 * һ�������ҵ������
 * @author wrx
 *
 */
@Transactional
public class CategoryService {
	//ע��CategoryDao
	private CategoryDao categoryDao;

	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	//ҵ����ѯ����һ������ķ���
	public List<Category> findAll() {
		return categoryDao.findAll();
		
	}

	//ҵ��㱣���Լ�����ķ���
	public void save(Category category) {
		categoryDao.save(category);
	}

	//ҵ������cid��ѯһ������
	public Category findByCid(Integer cid) {
		return categoryDao.findByCid(cid);
	}

	//ҵ���ɾ��һ������ķ���
	public void delete(Category category) {
		categoryDao.delete(category);
	}

	//ҵ����޸�һ������ķ���
	public void update(Category category) {
		categoryDao.update(category);
	}
	
}
