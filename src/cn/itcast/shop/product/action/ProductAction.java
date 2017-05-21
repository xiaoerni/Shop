package cn.itcast.shop.product.action;

import cn.itcast.shop.category.service.CategoryService;
import cn.itcast.shop.product.service.ProductService;
import cn.itcast.shop.product.vo.Product;
import cn.itcast.shop.utils.PageBean;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * ��Ʒ��Action����
 * @author wrx
 * @param <implement>
 *
 */
public class ProductAction extends ActionSupport implements ModelDriven<Product> {
	// ���ڽ������ݵ�ģ������.
	private Product product = new Product();
	//ע����Ʒ��Service
	private ProductService productService;
	//���շ���cid
	private Integer cid;
	//���ܶ�������ID
	private Integer csid;
	
	public Integer getCsid() {
		return csid;
	}

	public void setCsid(Integer csid) {
		this.csid = csid;
	}
	//ע��һ�������Service
	private CategoryService categoryService;
	//���յ�ǰҳ��
	private int page;
	
	
	public void setPage(int page) {
		this.page = page;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}
	

	public Integer getCid() {
		return cid;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public Product getModel() {
		return product;
	}
	
	// ������Ʒ��ID���в�ѯ��Ʒ:ִ�з���:
	public String findByPid() {
		// ����Service�ķ�����ɲ�ѯ.
		product = productService.findByPid(product.getPid());
		return "findByPid";
	}
	//���ݷ����id��ѯ��Ʒ
	public String findByCid() {
		//List<Category> cList = categoryService.findAll();
		PageBean<Product> pageBean = productService.findByPageCid(cid,page);  //����һ�������ѯ��Ʒ������ҳ��ѯ
		//��PageBean���뵽ֵջ��
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "findByCid";
	}
	
	//���ݶ�������id��ѯ��Ʒ
	public String findByCsid(){
		//���ݶ��������ѯ��Ʒ
		PageBean<Product> pageBean = productService.findByCsid(csid,page);
		//��PageBean���뵽ֵջ��
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "findByCsid";
	}
}
