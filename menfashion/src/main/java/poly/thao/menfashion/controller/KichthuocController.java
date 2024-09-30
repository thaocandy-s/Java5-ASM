package poly.thao.menfashion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import poly.thao.menfashion.entity.KichThuoc;
import poly.thao.menfashion.entity.NhanVien;
import poly.thao.menfashion.model.response.PageInfo;
import poly.thao.menfashion.model.response.ResponseObject;
import poly.thao.menfashion.service.AppService;
import poly.thao.menfashion.service.KichThuocService;
import poly.thao.menfashion.utils.MappingConstant;
import poly.thao.menfashion.utils.PageConstant;
import poly.thao.menfashion.utils.helper.PaginationUtil;

import java.util.List;

@Controller
@RequestMapping(MappingConstant.API_KICH_THUOC)
public class KichthuocController {

    @Autowired
    public KichThuocService service;

    @ModelAttribute("currentUser")
    private NhanVien currentUser() {
        return AppService.currentUser;
    }

    @ModelAttribute("isAdmin")
    private Boolean isAdmin(){
        return AppService.isAdmin(AppService.currentUser);
    }

    private PaginationUtil<KichThuoc> paginationUtil = new PaginationUtil<>();


    @GetMapping("")
    public String hienThi(
            @RequestParam(name = "key", defaultValue = "", required = false) String key,
            @RequestParam(name = "page", defaultValue = "1", required = false) int page,
            Model model) {
        if (currentUser() == null) {
            return "redirect:/loi";
        }
        List<KichThuoc> list = service.getList().data;
        if(!key.isBlank()) list = service.search(list, key);
        int totalPage = paginationUtil.getTotalPages(list, PageConstant.pageSize);
        list = paginationUtil.paginate(list, page, PageConstant.pageSize);
        PageInfo<KichThuoc> listInfo = new PageInfo<KichThuoc>(list, page, totalPage);
        model.addAttribute("listInfo", listInfo);
        model.addAttribute("key", key);
        model.addAttribute("api", MappingConstant.API_KICH_THUOC);

        return MappingConstant.VIEW_KICH_THUOC + "/list";
    }

    @GetMapping("/add")
    public String formThem() {

        if (currentUser() == null) {
            return "redirect:/loi";
        }
        return MappingConstant.VIEW_KICH_THUOC + "/form-add";
    }

    @PostMapping("/add")
    public String them(KichThuoc kichThuoc, Model model, RedirectAttributes red) {
        ResponseObject<KichThuoc> data;
        if (kichThuoc.getMa().equals("")
                || kichThuoc.getTen().equals("")
        ) {
            data = new ResponseObject<KichThuoc>(true, kichThuoc, "Không được để trống các trường mã-tên");
            red.addFlashAttribute("rp", data);
            return "redirect:" + MappingConstant.API_KICH_THUOC + "/add";
        }
        data = service.add(kichThuoc);
        red.addFlashAttribute("rp", data);

        if (data.isHasError) {
            return "redirect:" + MappingConstant.API_KICH_THUOC + "/add";
        }
        return "redirect:" + MappingConstant.API_KICH_THUOC;
    }

    @GetMapping("/update/{id}")
    public String formChiTiet(@PathVariable Integer id, Model model) {
        if (currentUser() == null) {
            return "redirect:/loi";
        }
        if(!model.containsAttribute("rp")){
            ResponseObject<KichThuoc> data = service.findById(id);
            model.addAttribute("rp", data);
        }else {
            model.addAttribute("rp", model.getAttribute("rp"));
        }
        return MappingConstant.VIEW_KICH_THUOC + "/form-update";
    }

    @PostMapping("/update")
    public String sua(KichThuoc kichThuoc, Model model, RedirectAttributes red) {
        System.out.println(kichThuoc);
        ResponseObject<KichThuoc> data;
        if (kichThuoc.getMa().equals("")
                || kichThuoc.getTen().equals("")
        ) {
            data = new ResponseObject<KichThuoc>(true, kichThuoc, "Không được để trống các trường mã-tên");
            red.addFlashAttribute("rp", data);
            return "redirect:" + MappingConstant.API_KICH_THUOC + "/update/" + kichThuoc.getId();
        }
        data = service.update(kichThuoc);
        red.addFlashAttribute("rp", data);

        if (data.isHasError) {
            System.out.println(data.isHasError);
            return "redirect:" + MappingConstant.API_KICH_THUOC + "/update/" + kichThuoc.getId();
        }
        return "redirect:" + MappingConstant.API_KICH_THUOC;
    }

    @GetMapping("/delete/{id}")
    public String xoa(@PathVariable Integer id, Model model, RedirectAttributes red) {
        if (currentUser() == null) {
            return "redirect:/loi";
        }
        ResponseObject<Integer> data = service.deleteById(id);
        red.addFlashAttribute("rp", data);
        return "redirect:" + MappingConstant.API_KICH_THUOC;
    }
}
