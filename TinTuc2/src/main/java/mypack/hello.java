package mypack;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import Dao.attachDAO;
import Dao.pageDao;
import Dao.tintucDao;
import Dao.userDao;
import model.attachFileModel;
import model.page;
import model.tintuc;
import model.user;
import service.upload_service;

@Controller
public class hello {
	userDao dao = new userDao();
	tintucDao ttdao= new tintucDao();
	pageDao pdao=new pageDao();
	user u=new user();
	tintuc tt=new tintuc();
	page p=new page();
	attachDAO attachDao = new attachDAO();
	attachFileModel attach= new attachFileModel();
	LinkedList<attachFileModel> currentAttachFile = new LinkedList<attachFileModel>();
	//kiem tra dang nhap 
	@RequestMapping(value = "/abc", method = RequestMethod.POST)
	public String test(HttpServletRequest request,@Valid @ModelAttribute("tk") user u1, BindingResult bindingResult,ModelMap mm) {
		
		System.out.println("in ra  "+u1.getName()+" "+u1.getPass()); 
		u=dao.login(u1.getName());
		if(u.getPass().equals(u1.getPass()))	
			{
				request.getSession().setAttribute("login", true);
				request.getSession().setAttribute("username", u1.getName());
//				mm.addAttribute("nghia",false);
				return "redirect:/";
			}
		request.getSession().setAttribute("login", false);
		return "acc";
		
	}
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request)
	{
		request.getSession().setAttribute("login", false);
		request.getSession().removeAttribute("username");
		return "redirect:/";
		
	}
	//trang dang nhap
	@RequestMapping(value = "/acc", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("tk", new user());
        return "acc";
    }
	// lay du lieu{Tk} trang dang nhap truyen qua login
	@RequestMapping(value = "/acc", method = RequestMethod.POST)
	public String test1(ModelMap mm,@Valid @ModelAttribute("tk") user u, BindingResult bindingResult) {
		return "acc";
		
	}
	//home 
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(HttpServletRequest request,ModelMap mm) {

		mm.addAttribute("noidung", ttdao.getAllTinTuc());
		mm.addAttribute("p", pdao.getPage());
        return "home";
    }
	
	//Trang home
	@RequestMapping(value = "/19", method = RequestMethod.GET)
    public String getHome(HttpServletRequest request,ModelMap mm) {
	
		return "redirect:/";
    }
	@RequestMapping(value = "/20", method = RequestMethod.GET)
    public String edit(HttpServletRequest request,ModelMap mm) {
		if(request.getSession().getAttribute("login")!=null) {
	        mm.addAttribute("nd", new tintuc());
	        return "edit1";
	    }
		return "redirect:/acc";
    }
	// lay du lieu trang edit
	@RequestMapping(value = "/20", method = RequestMethod.POST)
	public String edit(ModelMap mm,@Valid @ModelAttribute("nd") tintuc tt, BindingResult bindingResult) {
		return "edit1";
		
	}
	//trang thuc hien edit
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String edit1(ModelMap mm,@Valid @ModelAttribute("nd") tintuc tt1, BindingResult bindingResult) {
		//tt1=new tintuc();
		int tt_id=ttdao.insertTinTuc(tt1.getTieuDe(),tt1.getNoiDung(),tt1.getMoTa(),tt1.getTacGia(),tt1.getNgayTao());
		System.out.println(tt1.getTieuDe()+" "+tt1.getNoiDung()+" "+tt1.getMoTa()+" "+tt1.getTacGia());
		Iterator<attachFileModel> itr = currentAttachFile.iterator();
		
		while(itr.hasNext()) {
			attachFileModel temp =  itr.next();
			attachDao.inserAttachFile(tt_id,temp.getLink(), temp.getName());
		}
		currentAttachFile.clear();
//		mm.addAttribute("nghia",true);
		return "redirect:/";
		
	}
	// trang xem tin tuc
	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public String lastnew1(ModelMap mm,@PathVariable(value = "id") int id){
		tt=ttdao.getTinTuc(id);
		mm.addAttribute("tieude",tt.getTieuDe());
		mm.addAttribute("noidung1", tt.getNoiDung());
		mm.addAttribute("mota", tt.getMoTa());
		mm.addAttribute("tacgia", tt.getTacGia());
		mm.addAttribute("noidung", ttdao.getAllTinTuc());
		mm.addAttribute("dinhkem", attachDao.getAllAttachFileByPostID(id));
		return "latestnewsThird";

	}
	// list tin tuc
	@RequestMapping(value = "/listTinTuc", method = RequestMethod.GET)
    public String getHome1(HttpServletRequest request,ModelMap mm) {
		mm.addAttribute("noidung", ttdao.getAllTinTuc());
		
		return "submission";
    }
	@RequestMapping(value = "/listTinTuc", method = RequestMethod.POST)
    public String getHome2(HttpServletRequest request,ModelMap mm) {
		mm.addAttribute("noidung", ttdao.getAllTinTuc());
		
		return "submission";
    }
	//delete tin tuc
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String delete(ModelMap mm,@PathVariable(value = "id") int id){
		
		if(ttdao.deleteTinTuc(id))
		{
			mm.addAttribute("noidung", ttdao.getAllTinTuc());
			return "submission";
		}
		return "home";

	}
	@RequestMapping(value = "/update1", method = RequestMethod.POST)
	public String update(ModelMap mm,@Valid @ModelAttribute("nd") tintuc tt1, BindingResult bindingResult) {
		//tt1=new tintuc();
		ttdao.updateTinTuc(tt1.getTieuDe(),tt1.getNoiDung(),tt1.getMoTa(),tt1.getTacGia(),tt1.getNgayTao(),tt1.getID());
		
		mm.addAttribute("noidung", ttdao.getAllTinTuc());
		
		Iterator<attachFileModel> itr = currentAttachFile.iterator();
		
		while(itr.hasNext()) {
			attachFileModel temp =  itr.next();
			attachDao.updateAttachFile(temp.getLink(), temp.getName(),tt1.getID());
		}
		currentAttachFile.clear();
		
		return "redirect:/submission";
		
	}
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String edit2(HttpServletRequest request,ModelMap mm,@PathVariable(value = "id") int id) {
    		mm.addAttribute("tt",ttdao.getTinTuc(id));
	        mm.addAttribute("nd", new tintuc());
	        if(request.getSession().getAttribute("login")!=null) {
		        mm.addAttribute("nd", new tintuc());
		        tt=ttdao.getTinTuc(id);
		        mm.addAttribute("tt",tt);
		        currentAttachFile.clear();
		        currentAttachFile.addAll(attachDao.getAllAttachFileByPostID(id));
		        return "edit2";
		    }
	 
		//return "edit2";
	        return "redirect:/acc";
    }
	// lay du lieu trang edit
	@RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
	public String edit2(ModelMap mm,@Valid @ModelAttribute("nd") tintuc tt, BindingResult bindingResult) {
		
		return "edit2";
		
		
	}
	//edit page
	@RequestMapping(value="/editpage/{id}",method=RequestMethod.GET)
	public String editpage(HttpServletRequest request,ModelMap mm,@PathVariable(value ="id") int id) {
		mm.addAttribute("tt", pdao.getPage());
		mm.addAttribute("nd", new page());
		return "edit3";
		
	}
	@RequestMapping(value="/editpage/{id}",method=RequestMethod.POST)
	public String editpage(HttpServletRequest request,ModelMap mm,@Valid @ModelAttribute("nd") page p, BindingResult bindingResult) {
		
	
		return "edit3";
		
	}
	@RequestMapping(value="/editpage1",method=RequestMethod.POST)
	public String editpage1(HttpServletRequest request,ModelMap mm,@Valid @ModelAttribute("nd") page p, BindingResult bindingResult) {
		pdao.updateTinTuc(p.getTieuDe(), p.getNoiDung(), p.getLink1(), p.getLink2(), p.getLink3(), p.getLink4());
		mm.addAttribute("p", pdao.getPage());
		return "home";
		
	}
	@RequestMapping(value = "/delete/att/{name}")
	@ResponseBody
	public boolean deleteAtt(HttpServletRequest request,@PathVariable(value = "name") String name) {
		if(request.getSession().getAttribute("username")!=null) {
			Iterator<attachFileModel> itr=	currentAttachFile.iterator();
			int pos=0;
			int flag =0;
			int id=-1;
			while(itr.hasNext()) {
				System.out.println("ITR");
				attachFileModel temp = itr.next();
				System.out.println(temp.getName()+"--"+name);
				System.out.println(temp.getName().split(".").length+"--"+name.split(".").length);
				
				if(temp.getName().split("\\.")[0].equals(name)) {
					flag=1;
					id=temp.getId();
					break;
				}
				pos++;
			}
			System.out.println("pos:"+pos);
			System.out.println("flag:"+flag);
			if(flag==1) {
				currentAttachFile.remove(pos);
				attachDao.deleteAttachFile(id);
				return true;
			}
			
		}
		
		return false;
	}
	// api
	@RequestMapping(value = "/api/uploadfile",method=RequestMethod.POST)
	@ResponseBody
	public String attachFile(@RequestParam("uploadfile") MultipartFile file) {
		try {
			upload_service sv= new upload_service();
			
			HashMap map = sv.handleUpload(file);
			currentAttachFile.add(new attachFileModel(file.getOriginalFilename(),map.get("link").toString()));
			System.out.println(map.get("link").toString());
			return map.get("link").toString();
		}
		catch(Exception e) {
			return "Fail";
		}
	}
	@RequestMapping(value = "/api/getAtt/{id}",method=RequestMethod.GET)
	@ResponseBody
	public List getAtt(@PathVariable(value = "id") int id) {
		if(attachDao.getAllAttachFileByPostID(id)!=null) {
			List myList = new ArrayList<attachFileModel>(attachDao.getAllAttachFileByPostID(id));
			
			return myList;
		}
		return null;
	}
	@RequestMapping(value = "/api/getContent/{id}",method=RequestMethod.GET)
	@ResponseBody
	public tintuc getContent(@PathVariable(value = "id") int id) {
		return ttdao.getTinTuc(id);
	}
	@RequestMapping(value = "/api/getContentPage/{id}",method=RequestMethod.GET)
	@ResponseBody
	public page getContentPage(@PathVariable(value = "id") int id) {
		return pdao.getPage();
	}
	
	
	
	
	
	

	
	
}
