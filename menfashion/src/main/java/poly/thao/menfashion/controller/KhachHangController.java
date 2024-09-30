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
import poly.thao.menfashion.entity.KhachHang;
import poly.thao.menfashion.entity.NhanVien;
import poly.thao.menfashion.model.response.PageInfo;
import poly.thao.menfashion.model.response.ResponseObject;
import poly.thao.menfashion.service.AppService;
import poly.thao.menfashion.service.KhachHangService;
import poly.thao.menfashion.service.base.Service;
import poly.thao.menfashion.utils.MappingConstant;
import poly.thao.menfashion.utils.PageConstant;
import poly.thao.menfashion.utils.helper.PaginationUtil;

import java.util.List;

@Controller
@RequestMapping(MappingConstant.API_KHACH_HANG)
public class KhachHangController {

    @Autowired
    private KhachHangService service;

    @ModelAttribute("currentUser")
    private NhanVien currentUser() {
        return AppService.currentUser;
    }

    @ModelAttribute("isAdmin")
    private Boolean isAdmin(){
        return AppService.isAdmin(AppService.currentUser);
    }

    private PaginationUtil<KhachHang> paginationUtil = new PaginationUtil<>();


    @GetMapping("")
    public String hienThi(
            @RequestParam(name = "key", defaultValue = "", required = false) String key,
            @RequestParam(name = "page", defaultValue = "1", required = false) int page, Model model) {
        if (currentUser() == null) {
            return "redirect:/loi";
        }
        List<KhachHang> list = service.getList().data;
        if(!key.isBlank()) list = service.search(list, key);

        int totalPage = paginationUtil.getTotalPages(list, PageConstant.pageSize);
        list = paginationUtil.paginate(list, page, PageConstant.pageSize);
        PageInfo<KhachHang> listInfo = new PageInfo<KhachHang>(list, page, totalPage);
        model.addAttribute("listInfo", listInfo);
        model.addAttribute("key", key);
        model.addAttribute("api", MappingConstant.API_KHACH_HANG);

        return MappingConstant.VIEW_KHACH_HANG + "/list";
    }

    @GetMapping("/add")
    public String formThem() {
        if (currentUser() == null) {
            return "redirect:/loi";
        }
        return MappingConstant.VIEW_KHACH_HANG + "/form-add";
    }

    @PostMapping("/add")
    public String them(KhachHang khachHang, Model model, RedirectAttributes red) {
        ResponseObject<KhachHang> data;
        if (khachHang.getMa().equals("")
                || khachHang.getTen().equals("")
                || khachHang.getSoDienThoai().equals("")
        ) {
            data = new ResponseObject<KhachHang>(true, khachHang, "Không được để trống các trường mã-tên-số điện thoại");
            red.addFlashAttribute("rp", data);
            return "redirect:" + MappingConstant.API_KHACH_HANG + "/add";
        }
        data = service.add(khachHang);
        red.addFlashAttribute("rp", data);

        if (data.isHasError) {
            return "redirect:" + MappingConstant.API_KHACH_HANG + "/add";
        }
        return "redirect:" + MappingConstant.API_KHACH_HANG;
    }

    @GetMapping("/update/{id}")
    public String formChiTiet(@PathVariable Integer id, Model model) {
        if (currentUser() == null) {
            return "redirect:/loi";
        }
        if(!model.containsAttribute("rp")){
            ResponseObject<KhachHang> data = service.findById(id);
            model.addAttribute("rp", data);
        }else {
            model.addAttribute("rp", model.getAttribute("rp"));
        }
        return MappingConstant.VIEW_KHACH_HANG + "/form-update";
    }

    @PostMapping("/update")
    public String sua(KhachHang khachHang, Model model, RedirectAttributes red) {
        System.out.println(khachHang);
        ResponseObject<KhachHang> data;
        if (khachHang.getMa().equals("")
                || khachHang.getTen().equals("")
                || khachHang.getSoDienThoai().equals("")
        ) {
            data = new ResponseObject<KhachHang>(true, khachHang, "Không được để trống các trường tên-số điện thoại");
            red.addFlashAttribute("rp", data);
            return "redirect:" + MappingConstant.API_KHACH_HANG + "/update/" + khachHang.getId();
        }
        data = service.update(khachHang);
        red.addFlashAttribute("rp", data);

        if (data.isHasError) {
            return "redirect:" + MappingConstant.API_KHACH_HANG + "/update/" + khachHang.getId();
        }
        return "redirect:" + MappingConstant.API_KHACH_HANG;
    }

    @GetMapping("/delete/{id}")
    public String xoa(@PathVariable Integer id, Model model, RedirectAttributes red) {
        if (currentUser() == null) {
            return "redirect:/loi";
        }
        ResponseObject<Integer> data = service.deleteById(id);
        red.addFlashAttribute("rp", data);
        return "redirect:" + MappingConstant.API_KHACH_HANG;
    }
}
