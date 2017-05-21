package cn.itcast.shop.order.action;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.struts2.ServletActionContext;

import cn.itcast.shop.cart.vo.Cart;
import cn.itcast.shop.cart.vo.CartItem;
import cn.itcast.shop.order.service.OrderService;
import cn.itcast.shop.order.vo.Order;
import cn.itcast.shop.order.vo.OrderItem;
import cn.itcast.shop.user.vo.User;
import cn.itcast.shop.utils.PageBean;
import cn.itcast.shop.utils.PaymentUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
/**
 * ���������Action
 * @author wrx
 *
 */
public class OrderAction extends ActionSupport implements ModelDriven<Order>{

	//ģ������ʹ�õĶ���
	private Order order = new Order();
	//ע��OrderService
	private OrderService orderService;
	//����page�Ĳ���
	private Integer page;
	//����֧��ͨ������
	private String pd_FrpId;
	//���ܸ���ɹ������Ӧ����
	private String r6_Order;
	private String r3_Amt;
	
	public void setR6_Order(String r6_Order) {
		this.r6_Order = r6_Order;
	}

	public void setR3_Amt(String r3_Amt) {
		this.r3_Amt = r3_Amt;
	}

	public void setPd_FrpId(String pd_FrpId) {
		this.pd_FrpId = pd_FrpId;
	}

	public Order getModel() {
		
		return order;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public static Date getNowDate() throws ParseException{
		   Date currentTime = new Date();
		   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		   String dateString = formatter.format(currentTime);
		   Date date1=formatter.parse(dateString);
		   return date1;
		}
	
	//���ɶ����ķ���
	public String save() throws ParseException{
		//1.�������ݵ����ݿ�
		//�������ݲ�ȫ
		order.setOrdertime(getNowDate());
		System.out.println(order.getOrdertime());
		order.setState(1);    //1.δ����    2.�Ѿ������û�з���      3.�Ѿ�����������û��ȷ���ջ�      4���������
		//�ܼƵ������ǹ��ﳵ�е���Ϣ
		Cart cart = (Cart) ServletActionContext.getRequest().getSession().getAttribute("cart");
		if (cart == null) {
			this.addActionError("��!����û�й���!����ȥ����!");
			return "msg";
		}
		order.setTotal(cart.getTotal());
		// ���ö����еĶ�����:
		for (CartItem cartItem : cart.getCartItems()) {
			// ���������Ϣ�ӹ������õ�.
			OrderItem orderItem = new OrderItem();
			orderItem.setCount(cartItem.getCount());
			orderItem.setSubtotal(cartItem.getSubtotal());
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setOrder(order);

			order.getOrderItems().add(orderItem);
		}
		//���ö��������û�
		User existUser =(User) ServletActionContext.getRequest().getSession().getAttribute("existUser");
		if (existUser == null) {
			this.addActionError("��!����û�е�¼!����ȥ��¼��");
			return "login";
		}
		order.setUser(existUser);
		orderService.save(order);
		//2.������������ʾ��ҳ����
		//ͨ��ֵջ�ķ�ʽ��ʾ����Ϊorder��ʾ�Ķ������ģ������ʹ�õĶ���
		//��չ��ﳵ
		cart.clearCart();
		return "saveSuccess";
	}
	
	//�ҵĶ����Ĳ�ѯ
	public String findByUid(){
		//�����û���id��ѯ
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("existUser");
		//����Service
		PageBean<Order> pageBean = orderService.findByPageUid(user.getUid(),page);
		//����ҳ������ʾ��ҳ����
		ActionContext.getContext().getValueStack().set("pageBean",pageBean);
		return "findByUidSuccess";
	}
	
	//���ݶ�����id��ѯ�����ķ���
	public String findByOid(){
		order = orderService.findByOid(order.getOid());
		return "findByOidSuccess";
	}
	
	//Ϊ��������ķ���
	public String payOrder() throws IOException{
		//�޸Ķ���
		Order currOrder = orderService.findByOid(order.getOid());
		currOrder.setAddr(order.getAddr());
		currOrder.setName(order.getName());
		currOrder.setPhone(order.getPhone());
		orderService.update(currOrder);
		//Ϊ��������
		String p0_Cmd = "Buy"; // ҵ������:
		String p1_MerId = "10001126856";// �̻����:
		String p2_Order = order.getOid().toString();// �������:
		String p3_Amt = "0.01"; // ������:
		String p4_Cur = "CNY"; // ���ױ���:
		String p5_Pid = ""; // ��Ʒ����:
		String p6_Pcat = ""; // ��Ʒ����:
		String p7_Pdesc = ""; // ��Ʒ����:
		String p8_Url = "http://192.168.21.118:8080/shop/order_callBack.action"; // �̻�����֧���ɹ����ݵĵ�ַ:
		String p9_SAF = ""; // �ͻ���ַ:
		String pa_MP = ""; // �̻���չ��Ϣ:
		String pd_FrpId = this.pd_FrpId;// ֧��ͨ������:
		String pr_NeedResponse = "1"; // Ӧ�����:
		String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl"; // ��Կ
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
				p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
				pd_FrpId, pr_NeedResponse, keyValue); // hmac
		
		// ���ױ���������:
		StringBuffer stringBuffer = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
		stringBuffer.append("p0_Cmd=").append(p0_Cmd).append("&");
		stringBuffer.append("p1_MerId=").append(p1_MerId).append("&");
		stringBuffer.append("p2_Order=").append(p2_Order).append("&");
		stringBuffer.append("p3_Amt=").append(p3_Amt).append("&");
		stringBuffer.append("p4_Cur=").append(p4_Cur).append("&");
		stringBuffer.append("p5_Pid=").append(p5_Pid).append("&");
		stringBuffer.append("p6_Pcat=").append(p6_Pcat).append("&");
		stringBuffer.append("p7_Pdesc=").append(p7_Pdesc).append("&");
		stringBuffer.append("p8_Url=").append(p8_Url).append("&");
		stringBuffer.append("p9_SAF=").append(p9_SAF).append("&");
		stringBuffer.append("pa_MP=").append(pa_MP).append("&");
		stringBuffer.append("pd_FrpId=").append(pd_FrpId).append("&");
		stringBuffer.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
		stringBuffer.append("hmac=").append(hmac);
		
		//�ض����ױ�
		ServletActionContext.getResponse().sendRedirect(stringBuffer.toString());
		
		return NONE;
	}
	
	//����ɹ����ת��
	public String callBack(){
		//�޸Ķ���״̬���޸�״̬Ϊ�Ѿ�����
		Order currOrder = orderService.findByOid(Integer.parseInt(r6_Order));
		currOrder.setState(2);
		orderService.update(currOrder);
		//��ҳ����ʾ����ɹ�����Ϣ
		this.addActionMessage("��������ɹ���������ţ�"+r6_Order+"����Ľ�"+r3_Amt);
		return "msg";
	}
	
	//ȷ���ջ����޸Ķ���״̬
	public String updateState(){
		//���ݶ���id��ѯ����
		Order currOrder = orderService.findByOid(order.getOid());
		currOrder.setState(4);
		orderService.update(currOrder);
		return "updateStateSuccess";
	}
}
