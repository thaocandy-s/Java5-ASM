package poly.thao.menfashion.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import poly.thao.menfashion.entity.NhanVien;
import poly.thao.menfashion.model.request.LoginReq;
import poly.thao.menfashion.model.response.ResponseObject;
import poly.thao.menfashion.service.AppService;
import poly.thao.menfashion.service.LoginService;
import poly.thao.menfashion.service.NhanVienService;

import java.util.List;

@Controller
@RequestMapping(value = {
        "",
        "/",
        "/login",
        "/login/",
})
public class LoginController {

    @Autowired
    private LoginService loginService;


    @GetMapping("")
    public String formLogin(Model model){
        model.addAttribute("loginReq", new LoginReq());
        return "login";
    }

    @PostMapping("")
    public String login(@Valid @ModelAttribute("loginReq") LoginReq loginReq, BindingResult result, RedirectAttributes red, Model model){
        if(result.hasErrors()){
            model.addAttribute("loginReq", loginReq);
            return "login";
        }
        ResponseObject<NhanVien> currentUser = loginService.login(loginReq);
        if(currentUser.isHasError){
            red.addFlashAttribute("rp", currentUser);
            red.addFlashAttribute("loginReq", loginReq);
            return "redirect:/";
        }else {
            AppService.currentUser = currentUser.data;
            return "redirect:/ban-hang";
        }
    }


    @GetMapping("/loi")
    public String pageError(){
        return "Error";
    }



}
