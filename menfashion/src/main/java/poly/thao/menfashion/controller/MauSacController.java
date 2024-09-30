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
import poly.thao.menfashion.entity.MauSac;
import poly.thao.menfashion.entity.NhanVien;
import poly.thao.menfashion.model.response.PageInfo;
import poly.thao.menfashion.model.response.ResponseObject;
import poly.thao.menfashion.service.AppService;
import poly.thao.menfashion.service.MauSacService;
import poly.thao.menfashion.utils.MappingConstant;
import poly.thao.menfashion.utils.PageConstant;
import poly.thao.menfashion.utils.helper.PaginationUtil;

import java.util.List;

@Controller
@RequestMapping(MappingConstant.API_MAU_SAC)
public class MauSacController {

    @Autowired
    public MauSacService service;

    @ModelAttribute("currentUser")
    private NhanVien currentUser() {
        return AppService.currentUser;
    }

    @ModelAttribute("isAdmin")
    private Boolean isAdmin() {
        return AppService.isAdmin(AppService.currentUser);
    }

    private PaginationUtil<MauSac> paginationUtil = new PaginationUtil<>();


    @GetMapping("")
    public String hienThi(
            @RequestParam(name = "key", defaultValue = "", required = false) String key,
            @RequestParam(name = "page", defaultValue = "1", required = false) int page,
            Model model) {
        if (currentUser() == null) {
            return "redirect:/loi";
        }
        List<MauSac> list = service.getList().data;
        if(!key.isBlank()) list = service.search(list, key);
        int totalPage = paginationUtil.getTotalPages(list, PageConstant.pageSize);
        list = paginationUtil.paginate(list, page, PageConstant.pageSize);
        PageInfo<MauSac> listInfo = new PageInfo<MauSac>(list, page, totalPage);
        model.addAttribute("listInfo", listInfo);
        model.addAttribute("key", key);
        model.addAttribute("api", MappingConstant.API_MAU_SAC);

        return MappingConstant.VIEW_MAU_SAC + "/list";
    }

    @GetMapping("/add")
    public String formThem() {
        if (currentUser() == null) {
            return "redirect:/loi";
        }
        return MappingConstant.VIEW_MAU_SAC + "/form-add";
    }

    @PostMapping("/add")
    public String them(MauSac mauSac, Model model, RedirectAttributes red) {
        ResponseObject<MauSac> data;
        if (mauSac.getMa().equals("")
                || mauSac.getTen().equals("")
        ) {
            data = new ResponseObject<MauSac>(true, mauSac, "Không được để trống các trường mã-tên");
            red.addFlashAttribute("rp", data);
            return "redirect:" + MappingConstant.API_MAU_SAC + "/add";
        }
        data = service.add(mauSac);
        red.addFlashAttribute("rp", data);

        if (data.isHasError) {
            return "redirect:" + MappingConstant.API_MAU_SAC + "/add";
        }
        return "redirect:" + MappingConstant.API_MAU_SAC;
    }

    @GetMapping("/update/{id}")
    public String formChiTiet(@PathVariable Integer id, Model model) {
        if (currentUser() == null) {
            return "redirect:/loi";
        }
        if (!model.containsAttribute("rp")) {
            ResponseObject<MauSac> data = service.findById(id);
            model.addAttribute("rp", data);
        } else {
            model.addAttribute("rp", model.getAttribute("rp"));
        }
        return MappingConstant.VIEW_MAU_SAC + "/form-update";
    }

    @PostMapping("/update")
    public String sua(MauSac mauSac, Model model, RedirectAttributes red) {
        System.out.println(mauSac);
        ResponseObject<MauSac> data;
        if (mauSac.getMa().equals("")
                || mauSac.getTen().equals("")
        ) {
            data = new ResponseObject<MauSac>(true, mauSac, "Không được để trống các trường mã-tên");
            red.addFlashAttribute("rp", data);
            return "redirect:" + MappingConstant.API_MAU_SAC + "/update/" + mauSac.getId();
        }
        data = service.update(mauSac);
        red.addFlashAttribute("rp", data);

        if (data.isHasError) {
            System.out.println(data.isHasError);
            return "redirect:" + MappingConstant.API_MAU_SAC + "/update/" + mauSac.getId();
        }
        return "redirect:" + MappingConstant.API_MAU_SAC;
    }

    @GetMapping("/delete/{id}")
    public String xoa(@PathVariable Integer id, Model model, RedirectAttributes red) {
        if (currentUser() == null) {
            return "redirect:/loi";
        }
        ResponseObject<Integer> data = service.deleteById(id);
        red.addFlashAttribute("rp", data);
        return "redirect:" + MappingConstant.API_MAU_SAC;
    }
}
