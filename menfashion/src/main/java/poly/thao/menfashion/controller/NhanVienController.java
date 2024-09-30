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
import poly.thao.menfashion.entity.NhanVien;
import poly.thao.menfashion.model.response.PageInfo;
import poly.thao.menfashion.model.response.ResponseObject;
import poly.thao.menfashion.service.AppService;
import poly.thao.menfashion.service.NhanVienService;
import poly.thao.menfashion.service.base.Service;
import poly.thao.menfashion.utils.MappingConstant;
import poly.thao.menfashion.utils.PageConstant;
import poly.thao.menfashion.utils.helper.PaginationUtil;

import java.util.List;

@Controller
@RequestMapping(MappingConstant.API_NHAN_VIEN)
public class NhanVienController {

    @Autowired
    private NhanVienService service;

    @ModelAttribute("currentUser")
    private NhanVien currentUser() {
        return AppService.currentUser;
    }

    @ModelAttribute("isAdmin")
    private Boolean isAdmin(){
        return AppService.isAdmin(AppService.currentUser);
    }

    private PaginationUtil<NhanVien> paginationUtil = new PaginationUtil<>();


    @GetMapping("")
    public String hienThi(
            @RequestParam(name = "key", defaultValue = "", required = false) String key,
            @RequestParam(name = "page", defaultValue = "1", required = false) int page, Model model) {
        if (currentUser() == null || !isAdmin()) {
            return "redirect:/loi";
        }

        List<NhanVien> list = service.getList().data;
        if(!key.isBlank()) list = service.search(list, key);
        int totalPage = paginationUtil.getTotalPages(list, PageConstant.pageSize);
        list = paginationUtil.paginate(list, page, PageConstant.pageSize);
        PageInfo<NhanVien> listInfo = new PageInfo<NhanVien>(list, page, totalPage);
        model.addAttribute("listInfo", listInfo);
        model.addAttribute("key", key);
        model.addAttribute("api", MappingConstant.API_NHAN_VIEN);

        return MappingConstant.VIEW_NHAN_VIEN + "/list";
    }

    @GetMapping("/add")
    public String formThem() {
        if (currentUser() == null || !isAdmin()) {
            return "redirect:/loi";
        }

        return MappingConstant.VIEW_NHAN_VIEN + "/form-add";
    }

    @PostMapping("/add")
    public String them(NhanVien nhanVien, Model model, RedirectAttributes red) {

        ResponseObject<NhanVien> data;
        if (nhanVien.getMa().equals("")
                || nhanVien.getTen().equals("")
                || nhanVien.getMatKhau().equals("")
                || nhanVien.getTenDangNhap().equals("")
        ) {
            data = new ResponseObject<NhanVien>(true, nhanVien, "Không được để trống các trường mã-tên-tên đăng nhập-mật khẩu");
            red.addFlashAttribute("rp", data);
            return "redirect:" + MappingConstant.API_NHAN_VIEN + "/add";
        }
        data = service.add(nhanVien);
        red.addFlashAttribute("rp", data);

        if (data.isHasError) {
            return "redirect:" + MappingConstant.API_NHAN_VIEN + "/add";
        }
        return "redirect:" + MappingConstant.API_NHAN_VIEN;
    }

    @GetMapping("/update/{id}")
    public String formChiTiet(@PathVariable Integer id, Model model) {
        if (currentUser() == null || !isAdmin()) {
            return "redirect:/loi";
        }

        if (!model.containsAttribute("rp")) {
            ResponseObject<NhanVien> data = service.findById(id);
            model.addAttribute("rp", data);
        } else {
            model.addAttribute("rp", model.getAttribute("rp"));
        }
        return MappingConstant.VIEW_NHAN_VIEN + "/form-update";
    }

    @PostMapping("/update")
    public String sua(NhanVien nhanVien, Model model, RedirectAttributes red) {
        System.out.println(nhanVien);
        ResponseObject<NhanVien> data;
        if (nhanVien.getMa().equals("")
                || nhanVien.getTen().equals("")
                || nhanVien.getMatKhau().equals("")
                || nhanVien.getTenDangNhap().equals("")
        ) {
            data = new ResponseObject<NhanVien>(true, nhanVien, "Không được để trống các trường mã-tên-tên đăng nhập-mật khẩu");
            red.addFlashAttribute("rp", data);
            return "redirect:" + MappingConstant.API_NHAN_VIEN + "/update/" + nhanVien.getId();
        }
        data = service.update(nhanVien);
        red.addFlashAttribute("rp", data);

        if (data.isHasError) {
            System.out.println(data.isHasError);
            return "redirect:" + MappingConstant.API_NHAN_VIEN + "/update/" + nhanVien.getId();
        }
        return "redirect:" + MappingConstant.API_NHAN_VIEN;
    }

    @GetMapping("/delete/{id}")
    public String xoa(@PathVariable Integer id, Model model, RedirectAttributes red) {
        if (currentUser() == null || !isAdmin()) {
            return "redirect:/loi";
        }
        ResponseObject<Integer> data = service.deleteById(id);
        red.addFlashAttribute("rp", data);
        return "redirect:" + MappingConstant.API_NHAN_VIEN;
    }
}
