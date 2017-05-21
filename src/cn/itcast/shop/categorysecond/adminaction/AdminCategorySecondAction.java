package cn.itcast.shop.categorysecond.adminaction;

import java.util.List;

import cn.itcast.shop.category.service.CategoryService;
import cn.itcast.shop.category.vo.Category;
import cn.itcast.shop.categorysecond.service.CategorySecondService;
import cn.itcast.shop.categorysecond.vo.CategorySecond;
import cn.itcast.shop.utils.PageBean;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * ��̨������������Action
 * @author wrx
 *
 */
public class AdminCategorySecondAction extends ActionSupport implements ModelDriven<CategorySecond>{

	//ģ������ʹ�õĶ���
	private CategorySecond categorySecond = new CategorySecond();
	
	//ע����������Service
	private CategorySecondService categorySecondService;
	public void setCategorySecondService(CategorySecondService categorySecondService) {
		this.categorySecondService = categorySecondService;
	}
	@Override
	public CategorySecond getModel() {
		return categorySecond;
	}
	
	//����page
	private Integer page;
	public void setPage(Integer page) {
		this.page = page;
	}
	//ע��һ�������Service
	private CategoryService categoryService;
	
	
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	//��ѯ��������ķ���
	public String findAll(){
		PageBean<CategorySecond> pageBean = categorySecondService.findByPage(page);
		//��PageBean�����ݱ��浽ֵջ��
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "findAll";
	}
	
	//��ת�����ҳ��
	public String addPage(){
		//��ѯ����һ������
		List<Category> cList = categoryService.findAll();
		//��������ʾ��ҳ��������б���
		ActionContext.getContext().getValueStack().set("cList", cList);
		//ҳ����ת
		return "addPageSuccess";
	}
	
	//�����������ķ���
	public String save(){
		categorySecondService.save(categorySecond);
		return "saveSuccess";
	}
	
	//ɾ����������ķ���
	public String delete(){
		//�������ɾ�����Ȳ�ѯ��ɾ��������cascade
		categorySecond = categorySecondService.findByCsid(categorySecond.getCsid());
		categorySecondService.delete(categorySecond);
		return "deleteSuccess";
	}

	//�༭��������ķ���
	public String edit(){
		//���ݶ�������id��ѯ��������Ķ���
		categorySecond = categorySecondService.findByCsid(categorySecond.getCsid());
		//��ѯ���е�һ������
		List<Category> cList = categoryService.findAll();
		ActionContext.getContext().getValueStack().set("cList", cList);
		return "editSuccess";
	}
	
	//�޸Ķ�������ķ���
	public String update(){
		categorySecondService.update(categorySecond);
		return "updateSuccess";
	}
}
