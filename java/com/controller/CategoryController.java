package com.controller;



import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.multipart.MultipartFile;

import com.controller.Model.CateGory;
import com.controller.Model.MyUploadForm;
import com.controller.Repositories.CategoryRepository;


import jakarta.servlet.http.HttpServletRequest;


//@RestController
@Controller
@RequestMapping("/admin")

public class CategoryController {
	@Autowired
	private CategoryRepository  rep;


	/*
	 * @RequestMapping(value="/",method = RequestMethod.GET) public @ResponseBody
	 * List<CateGory> all() { return rep.findAll(); }
	 */
	  
		  
	  @GetMapping("/list") 
	  public String View(Model model) {
	
		  try {
			  Iterable<CateGory> he1 = rep.findAll();
			  model.addAttribute("listCustomers",he1);
			  
			  return "index";
			  
			  
		  }catch(Exception ec) {
			  ec.printStackTrace();
			throw new RuntimeException("list error!!");
		  }
		  
		  
		
		  //System.out.print("mm = "+ mm.getFileDatas());
		  
		  
	 }
	 //////////////////
	  @RequestMapping(value="/insert", method = RequestMethod.GET)
	    public String createUserForm(Model model) {
		  
		  	try {

				  MyUploadForm myUploadForm2 = new MyUploadForm();
			      model.addAttribute("myUploadForm", myUploadForm2);
			      
			      return "insert";
			      
		  	}catch(Exception ec) {
		  		ec.printStackTrace();
				throw new RuntimeException("Error in page insert!!");
		  	}
		  	
	      
	         // Trả về tên của file HTML để hiển thị biểu mẫu
	    }
	 
	  @RequestMapping(value = "/insert1", method = RequestMethod.POST)
	 public String InsertCategory( @RequestParam("name") String name,@RequestParam("id") int id,CateGory category,Model model
			 ,@RequestParam("image") String image, @RequestParam("price") float price,
			 @RequestParam("fileDatas") MultipartFile file1
			 ,MyUploadForm myUploadForm,
			 @ModelAttribute("myUploadForm") MyUploadForm myUploadForm1,
			 HttpServletRequest request){
		  
		  try {
		 		System.out.println("MultipartFile file1 = "+file1+" ===== ");
				
				  System.out.println("id = "+id);
				  System.out.println("======================================");
				  
				  System.out.println("name = "+name+" ======= ");
				  
				  System.out.println("image = "+image+"=======");
				  
				  System.out.println("======================================");
				  
				  System.out.println("price = "+price);
				  
				  System.out.println("======================================");
				 

			 category.setId(id); category.setName(name); category.setPrice(price); category.setImageCate(image);
				  
			 rep.insert(category);
			
		      
//			 this.doUpload(request, model, myUploadForm1);
			 	
			 	
			 	//------- ----------------------------
			 	  Path staticPath = Paths.get("src", "main", "resources", "static","image");
			 	  
			 	  String temp1 = staticPath.toString();
			 	 System.out.println(" staticPath:  "+temp1 +" === ");
			 	 File uploadRootDir1 = new File(temp1);
			 	 
			 	 System.out.println("====== uploadRootDir1 :  "+uploadRootDir1 +" === ");
			 	   if (!uploadRootDir1.exists()) {
			 	         uploadRootDir1.mkdirs();
			 	   }
			 	   
			 	   
			 	  MultipartFile[] fileDatas = myUploadForm.getFileDatas();
			 	  System.out.println(" ====== file Datas" + fileDatas + "======");
			 	  
			    //----------------------------------------------------

			    //MultipartFile[] fileDatas = myUploadForm.getFileDatas();
				 List<File> uploadedFiles = new ArrayList<File>();
				 ///Đường dẫn hình ảnh
				 for (MultipartFile fileData : fileDatas) {
					 
					 
					 String originalFilename = fileData.getOriginalFilename();
					 try {
						 File serverFile = new File(uploadRootDir1.getAbsolutePath() + File.separator + originalFilename);
						 
						 System.out.println("======== Sum = "+serverFile+" =======");
						 BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
						 
						  stream.write(fileData.getBytes());
			              stream.close();
					 
			              uploadedFiles.add(serverFile);
			              System.out.println("Write file: " + serverFile);
					 }catch(Exception ex) {
						 
						 
					 }
			  
				 }
			  
			 Iterable<CateGory> he1 = rep.findAll();
			 model.addAttribute("listCustomers",he1);
				 
			 
			 return "redirect:/admin/list";
		  }catch(Exception ec) {
			  ec.printStackTrace();
			throw new RuntimeException("Error delete!!");
		  }
		  
		
		
	
	 }
	 //.........Delete Category
	  @PostMapping("/delete")
	  public String DeleteCategory(Model model,@RequestParam("id1") String id1){
		  
		  int newparint;
		  
		  newparint = Integer.parseInt(id1);
		  
		  CateGory template =  rep.findById(newparint);
		  System.out.println("template = "+ template);
		  
		  
		 System.out.print("id delete ="+newparint);
		 System.out.print("======================================");
			
		 rep.deleteById(newparint); 

			//lấy đường dẫn hình ảnh theo id được chọn
		 
		 String imagetemp = template.getImageCate();
		 
		 System.out.println("Template String image = "+imagetemp);
		 
		 
		 File imageFile = new File("src/main/resources/static/image/" + imagetemp);
	 	 System.out.println("static + image = "+imageFile);
	 	 
	 	 if(imageFile.exists()) {
	 		imageFile.delete();
	 	 }
	 	
		  return "redirect:/admin/list";
	  }
	  //........Edit Category
	  
	  
	  @GetMapping("/edit")
	    public String EditUserForm(Model model,@RequestParam("id1") int id1) {
		
		CateGory cateName = rep.findById(id1);
		 
		model.addAttribute("id123",cateName.getId());
		 model.addAttribute("name123",	cateName.getName());
		 model.addAttribute("pricenew",	cateName.getPrice());
		
		 
		
		 
		 CateGory template =  rep.findById(id1);
		 String tem = template.getImageCate();
		System.out.println("Getimage = "+tem);
		model.addAttribute("image", tem);
		
		 //xóa hình đã khi thay đổi hình khác
		
		  
		  
		  File imageFile = new File("src/main/resources/static/image/" + tem);
		  System.out.println("static + image = "+imageFile);
		 	 
		  if(imageFile.exists()) {
		 		imageFile.delete();
		  }
		
		
		
		MyUploadForm myUploadForm2 = new MyUploadForm();
	    model.addAttribute("myUploadForm", myUploadForm2);
	      
		  return "edit"; 
	  }
	  
	  
	  
	  @PostMapping("/edit1")
	 public String EditCategory(Model model,@RequestParam("id") int id,@RequestParam("name") String name,CateGory cate,
			 @RequestParam("image") String image, @RequestParam("price") float price,
			 MyUploadForm myUploadForm){
		  
		  try {

			  cate.setName(name);
			  cate.setId(id);
			  cate.setPrice(price);
			  cate.setImageCate(image);
			  
			  
			  System.out.println("name = "+name);
			  System.out.println("id = "+ id);
			  System.out.println("price =  "+price);
			  System.out.println("image = "+image);
			  
			  rep.update(cate);
			  
			  CateGory template =  rep.findById(id);
			  String tem = template.getImageCate();
			  
			  System.out.println("Getimage = "+tem);
		
			 	//----------------------------------------------------------------------------
			  //----------------------------------------------------------------------------
			 	//thêm hình vào thư mục static
			 	 Path staticPath = Paths.get("src", "main", "resources", "static","image");
			 	  
			 	  String temp1 = staticPath.toString();
			 	 System.out.println(" staticPath:  "+temp1 +" === ");
			 	 File uploadRootDir1 = new File(temp1);
			 	 
			 	 System.out.println("====== uploadRootDir1 :  "+uploadRootDir1 +" === ");
			 	   if (!uploadRootDir1.exists()) {
			 	         uploadRootDir1.mkdirs();
			 	   }
			 	   
			 	   
			 	  MultipartFile[] fileDatas = myUploadForm.getFileDatas();
			 	  System.out.println(" ====== file Datas" + fileDatas + "======");
			 	  
			 	 
			 	//MultipartFile[] fileDatas = myUploadForm.getFileDatas();
					 List<File> uploadedFiles = new ArrayList<File>();
					 ///Đường dẫn hình ảnh
					 for (MultipartFile fileData : fileDatas) {
						 
						 
						 String originalFilename = fileData.getOriginalFilename();
						 try {
							 File serverFile = new File(uploadRootDir1.getAbsolutePath() + File.separator + originalFilename);
							 
							 System.out.println("======== Sum = "+serverFile+" =======");
							 BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
							 
							  stream.write(fileData.getBytes());
				              stream.close();
					 
				              uploadedFiles.add(serverFile);
				              System.out.println("Write file: " + serverFile);
						 }catch(Exception ex) {
							 
							 
						 }
				  
					 }
			 	 
			 	 
			 	 //----------------------------------------------------------------------
			  //danh sach san phẩm
			  Iterable<CateGory> he1 = rep.findAll();
			model.addAttribute("listCustomers",he1);  
			  
		  }catch(Exception ex) {
			  
		  }
		  
			  
		  return"redirect:/admin/list";
	  }
	  
	/***
	 * return search list category
	 */
	  //private CateService CateService1;
	  
	  @Autowired
		JdbcTemplate db;
	  @GetMapping("/search")
	  public String filterCate(@RequestParam("name") String name,Model model) {
		  

		  List<CateGory> filteredUsers = rep.getCateByFilter(name);
				
		  model.addAttribute("listCustomers",filteredUsers);  
		
		  return"index";
	  }
	  
	  /***
	   * Upload file
	   * 
	   *
	   * 
	   */
	  //Home Upload file
	  @RequestMapping(value = "/uploadfile")
	   public String homePage1() {

	      return "upload/index";
	   }
	  //---------------------Upload one file -------------------------------
	// GET: ------------------ Show upload form page --------------.
	   @RequestMapping(value = "/uploadOneFile", method = RequestMethod.GET)
	   public String uploadOneFileHandler(Model model) {

	      MyUploadForm myUploadForm = new MyUploadForm();
	      model.addAttribute("myUploadForm", myUploadForm);

	      return "upload/uploadOneFile";
	   }
	   // POST: ------------ Do Upload ------------------
	   @RequestMapping(value = "/uploadOneFile", method = RequestMethod.POST)
	   public String uploadOneFileHandlerPOST(HttpServletRequest request, //
	         Model model, //
	         @ModelAttribute("myUploadForm") MyUploadForm myUploadForm) {

	      return this.doUpload(request, model, myUploadForm);

	   }
	   //-------------------------------------------------------------
	   //---------------------Upload one Multifile -------------------
	   
	   // GET: ----------- Show upload form page ----------------
	   @RequestMapping(value = "/uploadMultiFile", method = RequestMethod.GET)
	   public String uploadMultiFileHandler(Model model) {

	      MyUploadForm myUploadForm = new MyUploadForm();
	      model.addAttribute("myUploadForm", myUploadForm);

	      return "upload/uploadMutiFile";
	   }
	   
	   // POST:---------- Do Upload ------------
	   @RequestMapping(value = "/uploadMultiFile", method = RequestMethod.POST)
	   public String uploadMultiFileHandlerPOST(HttpServletRequest request, //
	         Model model, //
	         //--Lấy từ dữ liệu đường dẫn từ trang uploadMutiFile và gắn khi submit từ trang sẽ đổ các đường dẫn file 
	         //--vào trong ModelAtribute từ vào Model có là MyUploadForm  
	         @ModelAttribute("myUploadForm") MyUploadForm myUploadForm) {

		   	return this.doUpload(request, model, myUploadForm);
		   	
	   }
	   
	   
	   //-------------------------------------------------------------
	   
	 
	   
	   /***
	    * Phương thưc doUpload
	    */
	   private String doUpload(HttpServletRequest request, Model model, //
		         MyUploadForm myUploadForm) {

		  // - Lấy thông tin mô tả từ đối tượng MyUploadForm.
		      String description = myUploadForm.getDescription();
		      System.out.println("Description: " + description);
		      model.addAttribute("description", description);

		      // Root Directory.
		      // -Lấy đường dẫn thư mục gốc (root directory) để lưu trữ tệp tin.
		      // -Phương thức getServletContext().getRealPath("upload") trả về đường dẫn tuyệt đối của thư mục "upload" trong ứng dụng.
//		      String uploadRootPath = request.getServletContext().getRealPath("upload");
//		      System.out.println("uploadRootPath=" + uploadRootPath);
		    
		      Path staticPath = Paths.get("src", "main", "resources", "static","image");
		      
			  String temp1 = staticPath.toString();
		      
//		      Path staticPath = Paths.get("src", "main", "resources", "static","image");
//		      String temp1 = staticPath.toString();
//		      
		      System.out.print("staticPath Stirng = "+temp1+" ");
		      
		      
		      File uploadRootDir1 = new File(temp1);
		      
		      System.out.print("staticPath = "+staticPath);
		      
		      System.out.print("==============================================");
		      System.out.print("==============================================");
		      
		      //- Tạo đối tượng File từ đường dẫn thư mục gốc.
		      File uploadRootDir = new File(temp1);
		      // Create directory if it not exists.
		      
		      //- Kiểm tra xem thư mục gốc đã tồn tại chưa. Nếu không tồn tại, phương thức sẽ tạo thư mục mới bằng cách sử dụng mkdirs().
		      
		      if (!uploadRootDir.exists()) {
		         uploadRootDir.mkdirs();
		      }
		      // - Lấy danh sách các tệp tin từ đối tượng MyUploadForm.
		      MultipartFile[] fileDatas = myUploadForm.getFileDatas();
		      
		      System.out.print(" ====== file Datas" + fileDatas + "======");
		      // - Khởi tạo danh sách rỗng để lưu trữ các tệp tin đã được tải lên thành công.
		      List<File> uploadedFiles = new ArrayList<File>();
		      
		      // - Khởi tạo danh sách rỗng để lưu trữ tên các tệp tin không thành công.
		      List<String> failedFiles = new ArrayList<String>();

		      for (MultipartFile fileData : fileDatas) {

		         // Client File Name----------
		         String name = fileData.getOriginalFilename();
		         System.out.println("Client File Name = " + name);
		         
		         File ImageSrc = new File(uploadRootDir1.getAbsolutePath() + name);
		         
		         System.out.print("ImageSrc = "+ ImageSrc );
		         
		         if (name != null && name.length() > 0) {
		            try {
		               // Create the file at server
		               File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);

		               // -Tạo BufferedOutputStream để ghi dữ liệu tệp tin vào serverFile.
		               BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
		               
		               // - Ghi dữ liệu của tệp tin vào serverFile.
		               stream.write(fileData.getBytes());
		               
		               // - Đóng stream.
		               stream.close();
		               
		               //- Thêm serverFile vào danh sách uploadedFiles.--Phần quang trọng
		               uploadedFiles.add(serverFile);
		               
		               System.out.println(" ---- "+"Write file: " + serverFile);
		            } catch (Exception e) {
		               System.out.println("Error Write file: " + name);
		               failedFiles.add(name);
		            }
		         }
		      }
		      // -Thêm thông tin mô tả vào model để sử dụng trong giao diện.
		      model.addAttribute("description", description);
		      
		      // -Thêm danh sách các tệp tin đã tải lên thành công vào model.
		      model.addAttribute("uploadedFiles", uploadedFiles);
		      
		      // - Thêm danh sách các tệp tin không thành công vào model.
		      model.addAttribute("failedFiles", failedFiles);
		      
		      //- Trả về tên template "uploadResult" để hiển thị kết quả tải lên trên giao diện.
		      return "upload/uploadResult";
		   }

	   
	   
	   
}
