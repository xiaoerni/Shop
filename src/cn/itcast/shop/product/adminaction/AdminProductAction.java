package cn.itcast.shop.product.adminaction;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import cn.itcast.shop.categorysecond.service.CategorySecondService;
import cn.itcast.shop.categorysecond.vo.CategorySecond;
import cn.itcast.shop.product.service.ProductService;
import cn.itcast.shop.product.vo.Product;
import cn.itcast.shop.utils.PageBean;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * ��̨��Ʒ�����Action
 * @author wrx
 *
 */
public class AdminProductAction extends ActionSupport implements ModelDriven<Product>{
	//ģ������ʹ�õĶ���
	private Product product = new Product();
	@Override
	public Product getModel() {
		
		return product;
	}

	//ע����Ʒ��Service
	private ProductService productService;
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	
	//ע����Ʒ�Ķ��������Service
	public CategorySecondService categorySecondService;
	
	
	public void setCategorySecondService(CategorySecondService categorySecondService) {
		this.categorySecondService = categorySecondService;
	}

	//����page ����
	private Integer page;
	public void setPage(Integer page) {
		this.page = page;
	}
	
	//�ļ��ϴ���Ҫ�Ĳ���
	private File upload;   //�ϴ����ļ�
	private String uploadFileName;  //�����ļ��ϴ����ļ���
	private String uploadContextType;   //�����ļ��ϴ����ļ���MIME������
	
	
	public void setUpload(File upload) {
		this.upload = upload;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public void setUploadContextType(String uploadContextType) {
		this.uploadContextType = uploadContextType;
	}

	//����ҳ�Ĳ�ѯ��Ʒ��ִ�еķ���
	public String findAll(){
		//����service��ɲ�ѯ����
		PageBean<Product> pageBean = productService.findByPage(page);
		//�����ݴ��ݵ�ҳ����
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		//ҳ����ת
		return "findAll";
	}
	
	//��ת�����ҳ��ķ���
	public String addPage(){
		//��ѯ���еĶ�������ļ���
		List<CategorySecond> csList = categorySecondService.findAll();
		//ͨ��ֵջ���б�������
		ActionContext.getContext().getValueStack().set("csList", csList);
		return "addPageSuccess";
	}
	
	public static Date getNowDate() throws ParseException{
		   Date currentTime = new Date();
		   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		   String dateString = formatter.format(currentTime);
		   Date date1=formatter.parse(dateString);
		   return date1;
		}
	
	//������Ʒ�ķ���
	public String save() throws IOException, ParseException{
		//����Service��ɱ���Ĳ���
		product.setPdate(getNowDate());
		System.out.println(product.getPdate());
		if(upload != null){
			//����ļ��ϴ��Ĵ��̾���·��
			String realPath = ServletActionContext.getServletContext().getRealPath("/products");
			//����һ���ļ�
			File diskFile = new File(realPath+"//"+uploadFileName);
			//�ļ��ϴ�
			FileUtils.copyFile(upload,diskFile);
			product.setImage("products/"+uploadFileName);
		}
		//�����ݱ��浽���ݿ�
		productService.save(product);
		//ҳ����ת
		return "saveSuccess";
	}
	
	//ɾ����Ʒ�ķ���
	public String delete(){
		//�Ȳ�ѯ��ɾ��
		product = productService.findByPid(product.getPid());
		//ɾ���ϴ���ͼƬ
		String path =product.getImage();
		if(path != null){
			String realPath = ServletActionContext.getServletContext().getRealPath("/"+path);
			File file = new File(realPath);
			file.delete();
		}
		//ɾ����Ʒ
		productService.delete(product);
		//ҳ����ת
		return "deleteSuccess";
	}
	
	//�༭��Ʒ�ķ���
	public String edit(){
		//������Ʒ��ID��ѯ����Ʒ
		product = productService.findByPid(product.getPid());
		//��ѯ���еĶ�������ļ���
		List<CategorySecond> csList = categorySecondService.findAll();
		//�����ݱ��浽ҳ��
		ActionContext.getContext().getValueStack().set("csList", csList);
		//ҳ����ת
		return "editSuccess";
	}
	
	//�޸���Ʒ�ķ���
	public String update() throws IOException, ParseException{
		product.setPdate(getNowDate());
		System.out.println(product.getPdate());
		//�ļ��ϴ�
		if(upload != null){
			//��ԭ���ϴ�����Ʒ��ͼƬɾ��
			String path = product.getImage();
			File file = new File( ServletActionContext.getServletContext().getRealPath("/"+path));
			file.delete();
			//����ļ��ϴ��Ĵ��̾���·��
			String realPath = ServletActionContext.getServletContext().getRealPath("/products");
			//����һ���ļ�
			File diskFile = new File(realPath+"//"+uploadFileName);
			//�ļ��ϴ�
			FileUtils.copyFile(upload,diskFile);
			product.setImage("products/"+uploadFileName);
		}
		//�޸���Ʒ�����ݵ����ݿ�
		productService.update(product);
		//ҳ����ת
		return "updateSuccess";
	}
}
