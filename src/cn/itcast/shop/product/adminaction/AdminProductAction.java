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
 * 后台商品管理的Action
 * @author wrx
 *
 */
public class AdminProductAction extends ActionSupport implements ModelDriven<Product>{
	//模型驱动使用的对象
	private Product product = new Product();
	@Override
	public Product getModel() {
		
		return product;
	}

	//注入商品的Service
	private ProductService productService;
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	
	//注入商品的二级分类的Service
	public CategorySecondService categorySecondService;
	
	
	public void setCategorySecondService(CategorySecondService categorySecondService) {
		this.categorySecondService = categorySecondService;
	}

	//接收page 参数
	private Integer page;
	public void setPage(Integer page) {
		this.page = page;
	}
	
	//文件上传需要的参数
	private File upload;   //上传的文件
	private String uploadFileName;  //接收文件上传的文件名
	private String uploadContextType;   //接收文件上传的文件的MIME的类型
	
	
	public void setUpload(File upload) {
		this.upload = upload;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public void setUploadContextType(String uploadContextType) {
		this.uploadContextType = uploadContextType;
	}

	//带分页的查询商品的执行的方法
	public String findAll(){
		//调用service完成查询操作
		PageBean<Product> pageBean = productService.findByPage(page);
		//将数据传递到页面上
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		//页面跳转
		return "findAll";
	}
	
	//跳转到添加页面的方法
	public String addPage(){
		//查询所有的二级分类的集合
		List<CategorySecond> csList = categorySecondService.findAll();
		//通过值栈进行保存数据
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
	
	//保存商品的方法
	public String save() throws IOException, ParseException{
		//调用Service完成保存的操作
		product.setPdate(getNowDate());
		System.out.println(product.getPdate());
		if(upload != null){
			//获得文件上传的磁盘绝对路径
			String realPath = ServletActionContext.getServletContext().getRealPath("/products");
			//创建一个文件
			File diskFile = new File(realPath+"//"+uploadFileName);
			//文件上传
			FileUtils.copyFile(upload,diskFile);
			product.setImage("products/"+uploadFileName);
		}
		//将数据保存到数据库
		productService.save(product);
		//页面跳转
		return "saveSuccess";
	}
	
	//删除商品的方法
	public String delete(){
		//先查询再删除
		product = productService.findByPid(product.getPid());
		//删除上传的图片
		String path =product.getImage();
		if(path != null){
			String realPath = ServletActionContext.getServletContext().getRealPath("/"+path);
			File file = new File(realPath);
			file.delete();
		}
		//删除商品
		productService.delete(product);
		//页面跳转
		return "deleteSuccess";
	}
	
	//编辑商品的方法
	public String edit(){
		//根据商品的ID查询到商品
		product = productService.findByPid(product.getPid());
		//查询所有的二级分类的集合
		List<CategorySecond> csList = categorySecondService.findAll();
		//将数据保存到页面
		ActionContext.getContext().getValueStack().set("csList", csList);
		//页面跳转
		return "editSuccess";
	}
	
	//修改商品的方法
	public String update() throws IOException, ParseException{
		product.setPdate(getNowDate());
		System.out.println(product.getPdate());
		//文件上传
		if(upload != null){
			//将原来上传的商品的图片删除
			String path = product.getImage();
			File file = new File( ServletActionContext.getServletContext().getRealPath("/"+path));
			file.delete();
			//获得文件上传的磁盘绝对路径
			String realPath = ServletActionContext.getServletContext().getRealPath("/products");
			//创建一个文件
			File diskFile = new File(realPath+"//"+uploadFileName);
			//文件上传
			FileUtils.copyFile(upload,diskFile);
			product.setImage("products/"+uploadFileName);
		}
		//修改商品的数据到数据库
		productService.update(product);
		//页面跳转
		return "updateSuccess";
	}
}
