package cn.itcast.shop.order.adminaction;

import java.util.List;

import cn.itcast.shop.order.service.OrderService;
import cn.itcast.shop.order.vo.Order;
import cn.itcast.shop.order.vo.OrderItem;
import cn.itcast.shop.utils.PageBean;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * ��̨���������Action
 * @author wrx
 *
 */
public class AdminOrderAction extends ActionSupport implements ModelDriven<Order>{
	//ģ������ʹ�õĶ���
	private Order order = new Order();
	
	@Override
	public Order getModel() {

		return order;
	}
	
	//ע�붩�������Service
	private OrderService orderService;

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	
	//����page����
	private Integer page;

	public void setPage(Integer page) {
		this.page = page;
	}
	
	//����ҳ��ѯ��ִ�еķ���
	public String findAll(){
		//��ҳ��ѯ
		PageBean<Order> pageBean = orderService.findByPage(page);
		//ͨ��ֵջ���б�������
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		//ҳ����ת
		return "findAll";
	}

	//���ݶ���id��ѯ������
	public String findOrderItem(){
		//���ݶ���id��ѯ������
		List<OrderItem> list = orderService.findOrderItem(order.getOid());
		// ��ʾ��ҳ��:
		ActionContext.getContext().getValueStack().set("list", list);
		// ҳ����ת
		return "findOrderItem";
	}
	
	//��̨�޸Ķ���״̬�ķ���
	public String updateState(){
		//1.���ݶ���id��ѯ����
		Order currOrder = orderService.findByOid(order.getOid());
		//2.�޸Ķ���״̬
		currOrder.setState(3);
		orderService.update(currOrder);
		//3.ҳ����ת
		return "updateStateSuccess";
	}
}
