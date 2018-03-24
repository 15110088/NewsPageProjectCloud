package mypack;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.awt.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.boot.autoconfigure.web.ServerProperties.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.annotation.JsonFormat.Value;

import Dao.pageDao;
import Dao.tintucDao;
import Dao.userDao;
import model.page;
import model.tintuc;
import model.user;

@Controller
public class hello {
	userDao dao = new userDao();
	tintucDao ttdao= new tintucDao();
	pageDao pdao=new pageDao();
	user u=new user();
	tintuc tt=new tintuc();
	page p=new page();
	//kiem tra dang nhap 
	@RequestMapping(value = "/abc", method = RequestMethod.POST)
	public String test(HttpServletRequest request,@Valid @ModelAttribute("tk") user u1, BindingResult bindingResult,ModelMap mm) {
		
		System.out.println("in ra  "+u1.getName()+" "+u1.getPass()); 
		u=dao.login(u1.getName());
		if(u.getPass().equals(u1.getPass()))	
			{
				request.getSession().setAttribute("login", true);
//				mm.addAttribute("nghia",false);
				return "redirect:/";
			}
		request.getSession().setAttribute("login", false);
		return "acc";
		
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
		return "acc";
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
		ttdao.insertTinTuc(tt1.getTieuDe(),tt1.getNoiDung(),tt1.getMoTa(),tt1.getTacGia(),tt1.getNgayTao());
		System.out.println(tt1.getTieuDe()+" "+tt1.getNoiDung()+" "+tt1.getMoTa()+" "+tt1.getTacGia());
		mm.addAttribute("noidung","noidung" );
//		mm.addAttribute("nghia",true);
		return "home";
		
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
		return "submission";
		
	}
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String edit2(HttpServletRequest request,ModelMap mm,@PathVariable(value = "id") int id) {
		mm.addAttribute("tt",ttdao.getTinTuc(id));
	        mm.addAttribute("nd", new tintuc());
	 
		return "edit2";
    }
	// lay du lieu trang edit
	@RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
	public String edit2(ModelMap mm,@Valid @ModelAttribute("nd") tintuc tt, BindingResult bindingResult) {
		
		return "edit2";
		
		
	}
	//edit page
	@RequestMapping(value="/editpage",method=RequestMethod.GET)
	public String editpage(HttpServletRequest request,ModelMap mm) {
		mm.addAttribute("tt", pdao.getPage());
		mm.addAttribute("nd", new page());
		return "edit3";
		
	}
	@RequestMapping(value="/editpage",method=RequestMethod.POST)
	public String editpage(HttpServletRequest request,ModelMap mm,@Valid @ModelAttribute("nd") page p, BindingResult bindingResult) {
		
	
		return "edit3";
		
	}
	@RequestMapping(value="/editpage1",method=RequestMethod.POST)
	public String editpage1(HttpServletRequest request,ModelMap mm,@Valid @ModelAttribute("nd") page p, BindingResult bindingResult) {
		pdao.updateTinTuc(p.getTieuDe(), p.getNoiDung(), p.getLink1(), p.getLink2(), p.getLink3(), p.getLink4());
		mm.addAttribute("p", pdao.getPage());
		return "home";
		
	}
	
	
	
	
	

	
	
}
