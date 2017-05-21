package cn.itcast.shop.product.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.itcast.shop.product.vo.Product;
import cn.itcast.shop.utils.PageHibernateCallback;

/**
 * ��Ʒ�־ò�Ĵ���
 * @author wrx
 *
 */
public class ProductDao extends HibernateDaoSupport {

	// ��ҳ��������Ʒ��ѯ
	public List<Product> findHot() {
		// ʹ������������ѯ.
		DetachedCriteria criteria = DetachedCriteria.forClass(Product.class);
		// ��ѯ���ŵ���Ʒ,��������is_host = 1
		criteria.add(Restrictions.eq("is_hot", 1));
		// �����������:
		criteria.addOrder(Order.desc("pdate"));
		// ִ�в�ѯ:
		List<Product> list = this.getHibernateTemplate().findByCriteria(criteria, 0, 10);
		return list;
	}

	//��ҳ������Ʒ�Ĳ�ѯ
	public List<Product> findNew() {
		//ʹ������������ѯ
		DetachedCriteria criteria = DetachedCriteria.forClass(Product.class);
		//�����ڽ��е�������
		criteria.addOrder(Order.desc("pdate"));
		//ִ�в�ѯ
		List<Product> list = this.getHibernateTemplate().findByCriteria(criteria,0,10);
		return list;
	}

	//������Ʒ��ID��ѯ��Ʒ
	public Product finByPid(Integer pid) {
		return this.getHibernateTemplate().get(Product.class, pid);
	}

	//���ݷ���id��ѯ��Ʒ�ĸ���
	public int findCoundCid(Integer cid) {
		String hql = "select count(*) from Product p where p.categorySecond.category.cid=?";
		List<Long> list= this.getHibernateTemplate().find(hql,cid);
		if(list != null && list.size() > 0){
			return list.get(0).intValue();
		}
		return 0;
	}

	//���ݷ���id��ѯ��Ʒ�ļ���
	public List<Product> findByPageCid(Integer cid, int begin, int limit) {
		//select p.* from category c,categorysecond cs,product p where c.cid=cs.cid and cs.csid=p.csid and c.cid=2
		//select p from Category c,CategorySecond cs,Product p where c.cid = cs.categort.cid and cs.csid = p.categorySecond.csid and c.cid = ?
		String hql="select p from Product p join p.categorySecond cs join cs.category c where c.cid = ?";
		//��ҳ��һ��д��
		List<Product> list = this.getHibernateTemplate().execute(new PageHibernateCallback<Product>(hql, new Object[]{cid},begin,limit));
		if(list != null && list.size() > 0){
			return list;
		}
		return null;
	}

	//���ݶ��������ѯ��Ʒ����
	public int findCoundCsid(Integer csid) {
		String hql="select count(*) from Product p where p.categorySecond.csid=?";
		List<Long> list = this.getHibernateTemplate().find(hql,csid);
		if(list != null && list.size() > 0){
			return list.get(0).intValue();
		}
		return 0;
	}

	//���ݶ��������ѯ��Ʒ��Ϣ
	public List<Product> findByPageCsid(Integer csid, int begin, int limit) {
		String hql="select p from Product p join p.categorySecond cs where cs.csid = ?";
		List<Product> list = this.getHibernateTemplate().execute(new PageHibernateCallback<Product>(hql,new Object[]{csid},begin,limit));
		if(list != null && list.size() > 0){
			return list;
		}
		return null;
	}

	//DAO��ͳ����Ʒ�����ķ���
	public int findCound() {
		String hql="select count(*) from Product";
		List<Long> list = this.getHibernateTemplate().find(hql);
		if(list != null && list.size() > 0){
			return list.get(0).intValue();
		}
		return 0;
	}

	//����ҳ��ѯ��Ʒ�ķ���
	public List<Product> findByPage(int begin, int limit) {
		String hql="from Product order by pdate desc";
		List<Product> list = this.getHibernateTemplate().execute(new PageHibernateCallback<Product>(hql,null,begin,limit));
		if(list != null && list.size() > 0){
			return list;
		}
		return null;
	}

	//DAO�㱣����Ʒ�ķ���
	public void save(Product product) {
		this.getHibernateTemplate().save(product);
	}

	//DAO��ɾ����Ʒ�ķ���
	public void delete(Product product) {
		this.getHibernateTemplate().delete(product);
	}

	//DAO���޸���Ʒ�ķ���
	public void update(Product product) {
		this.getHibernateTemplate().update(product);
	}


}
