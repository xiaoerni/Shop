package cn.itcast.shop.order.service;

import java.util.List;

import cn.itcast.shop.order.dao.OrderDao;
import cn.itcast.shop.order.vo.Order;
import cn.itcast.shop.order.vo.OrderItem;
import cn.itcast.shop.utils.PageBean;

/**
 * ����ģ�飺ҵ������
 * @author wrx
 *
 */
public class OrderService {

	private OrderDao orderDao;

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	//���涩����ҵ������
	public void save(Order order) {
		orderDao.save(order);
	}

	//�ҵĶ�����ҵ���Ĵ���
	public PageBean<Order> findByPageUid(Integer uid, Integer page) {
		PageBean<Order> pageBean = new PageBean<Order>();
		//���õ�ǰҳ��
		pageBean.setPage(page);
		//����ÿҳ��ʾ�ļ�¼��
		Integer limit = 5;
		pageBean.setLimit(limit);
		//�����ܼ�¼��
		Integer totalCount = null;
		totalCount = orderDao.findByCountUid(uid);
		pageBean.setTotalCount(totalCount);
		//������ҳ��
		Integer totalPage = null;
		if(totalCount % limit == 0 ){
			totalPage = totalCount / limit;
		}else{
			totalPage = totalCount / limit + 1;
		}
		pageBean.setTotalPage(totalPage);
		//ÿҳ��ʾ���ݼ���
		Integer begin = (page - 1) * limit;
		List<Order> list = orderDao.findByPageUid(uid,begin,limit);
		pageBean.setList(list);
		return pageBean;
	}

	//���ݶ���id��ѯ����
	public Order findByOid(Integer oid) {
		
		return orderDao.findByOid(oid);
	}

	//ҵ��㣺�޸Ķ����Ĳ���
	public void update(Order currOrder) {

		orderDao.update(currOrder);
	}

	//ҵ����̨��ҳ��ѯ�����ķ���
	public PageBean<Order> findByPage(Integer page) {
		PageBean<Order> pageBean = new PageBean<Order>();
		//���õ�ǰҳ��
		pageBean.setPage(page);
		//����ÿҳ��ʾ�ļ�¼��
		int limit = 10;
		pageBean.setLimit(limit);
		//�����ܼ�¼��
		int totalCount = orderDao.findByCount();
		pageBean.setTotalCount(totalCount);
		//������ҳ��
		Integer totalPage = 0;
		if(totalCount % limit == 0 ){
			totalPage = totalCount / limit;
		}else{
			totalPage = totalCount / limit + 1;
		}
		pageBean.setTotalPage(totalPage);
		//ÿҳ��ʾ���ݼ���
		int begin = (page - 1) * limit;
		List<Order> list = orderDao.findByPage(begin,limit);
		pageBean.setList(list);
		return pageBean;
	}

	//ҵ�����ɸ��ݶ���id��ѯ������ķ���
	public List<OrderItem> findOrderItem(Integer oid) {
		
		return orderDao.findOrderItem(oid);
	}
	
}
