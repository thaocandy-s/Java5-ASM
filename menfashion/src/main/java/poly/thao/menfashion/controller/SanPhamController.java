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
import poly.thao.menfashion.entity.SanPham;
import poly.thao.menfashion.model.response.SanPhamDTO;
import poly.thao.menfashion.model.response.PageInfo;
import poly.thao.menfashion.model.response.ResponseObject;
import poly.thao.menfashion.service.AppService;
import poly.thao.menfashion.service.SanPhamService;
import poly.thao.menfashion.utils.MappingConstant;
import poly.thao.menfashion.utils.PageConstant;
import poly.thao.menfashion.utils.helper.PaginationUtil;

import java.util.List;

@Controller
@RequestMapping(MappingConstant.API_SAN_PHAM)
public class SanPhamController {

    @Autowired
    public SanPhamService service;

    @ModelAttribute("currentUser")
    private NhanVien currentUser() {
        return AppService.currentUser;
    }

    @ModelAttribute("isAdmin")
    private Boolean isAdmin(){
        return AppService.isAdmin(AppService.currentUser);
    }

    private PaginationUtil<SanPhamDTO> paginationUtil = new PaginationUtil<>();

    @GetMapping("")
    public String hienThi(
            @RequestParam(name = "key", defaultValue = "", required = false) String key,
            @RequestParam(name = "page", defaultValue = "1", required = false) int page, Model model) {
        if (currentUser() == null) {
            return "redirect:/loi";
        }
        List<SanPham> list = service.getList().data;
        if(!key.isBlank()) list = service.search(list, key);

        List<SanPhamDTO> listDTO = service.getListDTO(list);
        int totalPage = paginationUtil.getTotalPages(listDTO, PageConstant.pageSize);
        listDTO = paginationUtil.paginate(listDTO, page, PageConstant.pageSize);
        PageInfo<SanPhamDTO> listInfo = new PageInfo<SanPhamDTO>(listDTO, page, totalPage);
        model.addAttribute("listInfo", listInfo);
        model.addAttribute("key", key);
        model.addAttribute("api", MappingConstant.API_SAN_PHAM);

        return MappingConstant.VIEW_SAN_PHAM + "/list";
    }

    @GetMapping("/add")
    public String formThem() {
        if (currentUser() == null) {
            return "redirect:/loi";
        }
        return MappingConstant.VIEW_SAN_PHAM + "/form-add";
    }

    @PostMapping("/add")
    public String them(SanPham sanPham, Model model, RedirectAttributes red) {
        ResponseObject<SanPham> data;
        if (sanPham.getMa().equals("")
                || sanPham.getTen().equals("")
        ) {
            data = new ResponseObject<SanPham>(true, sanPham, "Không được để trống các trường mã-tên");
            red.addFlashAttribute("rp", data);
            return "redirect:" + MappingConstant.API_SAN_PHAM + "/add";
        }
        data = service.add(sanPham);
        red.addFlashAttribute("rp", data);

        if (data.isHasError) {
            return "redirect:" + MappingConstant.API_SAN_PHAM + "/add";
        }
        return "redirect:" + MappingConstant.API_SAN_PHAM;
    }

    @GetMapping("/update/{id}")
    public String formChiTiet(@PathVariable Integer id, Model model) {
        if (currentUser() == null) {
            return "redirect:/loi";
        }
        if(!model.containsAttribute("rp")){
            ResponseObject<SanPham> data = service.findById(id);
            model.addAttribute("rp", data);
        }else {
            model.addAttribute("rp", model.getAttribute("rp"));
        }
        return MappingConstant.VIEW_SAN_PHAM + "/form-update";
    }

    @PostMapping("/update")
    public String sua(SanPham sanPham, Model model, RedirectAttributes red) {
        System.out.println(sanPham);
        ResponseObject<SanPham> data;
        if (sanPham.getMa().equals("")
                || sanPham.getTen().equals("")
        ) {
            data = new ResponseObject<SanPham>(true, sanPham, "Không được để trống các trường mã-tên");
            red.addFlashAttribute("rp", data);
            return "redirect:" + MappingConstant.API_SAN_PHAM + "/update/" + sanPham.getId();
        }
        data = service.update(sanPham);
        red.addFlashAttribute("rp", data);

        if (data.isHasError) {
            System.out.println(data.isHasError);
            return "redirect:" + MappingConstant.API_SAN_PHAM + "/update/" + sanPham.getId();
        }
        return "redirect:" + MappingConstant.API_SAN_PHAM;
    }

    @GetMapping("/delete/{id}")
    public String xoa(@PathVariable Integer id, Model model, RedirectAttributes red) {
        if (currentUser() == null) {
            return "redirect:/loi";
        }
        ResponseObject<Integer> data = service.deleteById(id);
        red.addFlashAttribute("rp", data);
        return "redirect:" + MappingConstant.API_SAN_PHAM;
    }
}
