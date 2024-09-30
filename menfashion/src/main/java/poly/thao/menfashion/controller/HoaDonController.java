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
import poly.thao.menfashion.entity.HoaDon;
import poly.thao.menfashion.entity.KhachHang;
import poly.thao.menfashion.entity.NhanVien;
import poly.thao.menfashion.model.request.HoaDonReq;
import poly.thao.menfashion.model.response.PageInfo;
import poly.thao.menfashion.model.response.ResponseObject;
import poly.thao.menfashion.service.AppService;
import poly.thao.menfashion.service.HoaDonService;
import poly.thao.menfashion.service.KhachHangService;
import poly.thao.menfashion.service.NhanVienService;
import poly.thao.menfashion.utils.MappingConstant;
import poly.thao.menfashion.utils.PageConstant;
import poly.thao.menfashion.utils.helper.PaginationUtil;

import java.util.List;

@Controller
@RequestMapping(MappingConstant.API_HOA_DON)
public class HoaDonController {

    @Autowired
    private HoaDonService service;

    @Autowired
    private KhachHangService khachHangService;

    @Autowired
    private NhanVienService nhanVienService;

    @ModelAttribute("currentUser")
    private NhanVien currentUser() {
        return AppService.currentUser;
    }

    @ModelAttribute("isAdmin")
    private Boolean isAdmin(){
        return AppService.isAdmin(AppService.currentUser);
    }

    @ModelAttribute("listKhachHang")
    private List<KhachHang> getListKhachHang(){
        return khachHangService.getList().data;
    }

    @ModelAttribute("listNhanVien")
    private List<NhanVien> getListNhanVien(){
        return nhanVienService.getList().data;
    }

    @ModelAttribute("listTongGia")
    private List<Double> getListTongGia(){
        return service.listTongGiaHD();
    }

    private PaginationUtil<HoaDon> paginationUtil = new PaginationUtil<>();

    @GetMapping("")
    public String hienThi(@RequestParam(name = "page", defaultValue = "1", required = false) int page,
                          @RequestParam(name = "key", defaultValue = "", required = false) String key,
                          Model model) {
        if (currentUser() == null || !isAdmin()) {
            return "redirect:/loi";
        }
        List<HoaDon> list = service.getList().data;
        if(!key.isBlank()) list = service.searchHoaDon(list, key);
        int totalPage = paginationUtil.getTotalPages(list, PageConstant.pageSize);
        list = paginationUtil.paginate(list, page, PageConstant.pageSize);
        PageInfo<HoaDon> listInfo = new PageInfo<HoaDon>(list, page, totalPage);
        model.addAttribute("listInfo", listInfo);
        model.addAttribute("key", key);
        model.addAttribute("api", MappingConstant.API_HOA_DON);


        return MappingConstant.VIEW_HOA_DON + "/list";
    }

    @GetMapping("/update/{id}")
    public String formChiTiet(@PathVariable Integer id, Model model) {
        if (currentUser() == null || !isAdmin()) {
            return "redirect:/loi";
        }
        if (!model.containsAttribute("rp")) {
            ResponseObject<HoaDon> rpHD = service.findById(id);
            ResponseObject<HoaDonReq> data = new ResponseObject<>(
                    rpHD.isHasError,
                    new HoaDonReq(rpHD.data.getId(), rpHD.data.khachHang.getId(), rpHD.data.nhanVien.getId()),
                    rpHD.getMessage()
                    );
            model.addAttribute("rp", data);
        } else {
            model.addAttribute("rp", model.getAttribute("rp"));
        }
        return MappingConstant.VIEW_HOA_DON + "/form-update";
    }

    @PostMapping("/update")
    public String sua(HoaDonReq hoaDonReq, Model model, RedirectAttributes red) {
        ResponseObject<HoaDonReq> data;
        if(hoaDonReq.idKhachHang == null || hoaDonReq.idNhanVien == null){
            data = new ResponseObject<HoaDonReq>(true, hoaDonReq, "Vui lòng chọn khách hàng và nhân viên");
            return "redirect:" + MappingConstant.API_HOA_DON + "/update/" + hoaDonReq.getId();
        }
        HoaDon hd = service.findById(hoaDonReq.id).data;
        hd.setKhachHang(khachHangService.findById(hoaDonReq.idKhachHang).data);
        hd.setNhanVien(nhanVienService.findById(hoaDonReq.idNhanVien).data);
        ResponseObject<HoaDon> rpHD= service.update(hd);
        data = new ResponseObject<>(true, hoaDonReq, rpHD.message);
        red.addFlashAttribute("rp", data);
        if (rpHD.isHasError) {
            return "redirect:" + MappingConstant.API_HOA_DON + "/update/" + hoaDonReq.getId();
        }
        return "redirect:" + MappingConstant.API_HOA_DON;
    }


}
