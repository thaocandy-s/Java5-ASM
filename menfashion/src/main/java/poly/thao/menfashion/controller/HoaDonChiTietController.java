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
import poly.thao.menfashion.entity.HoaDonChiTiet;
import poly.thao.menfashion.entity.NhanVien;
import poly.thao.menfashion.entity.SanPhamChiTiet;
import poly.thao.menfashion.model.request.HoaDonChiTietReq;
import poly.thao.menfashion.model.response.PageInfo;
import poly.thao.menfashion.model.response.ResponseObject;
import poly.thao.menfashion.service.AppService;
import poly.thao.menfashion.service.HoaDonChiTietService;
import poly.thao.menfashion.service.HoaDonService;
import poly.thao.menfashion.service.SanPhamChiTietService;
import poly.thao.menfashion.utils.MappingConstant;
import poly.thao.menfashion.utils.PageConstant;
import poly.thao.menfashion.utils.helper.PaginationUtil;

import java.util.List;

@Controller
@RequestMapping(MappingConstant.API_HOA_DON_CHI_TIET)
public class HoaDonChiTietController {

    @Autowired
    public HoaDonChiTietService service;

    @Autowired
    private HoaDonService hoaDonService;

    @Autowired
    private SanPhamChiTietService sanPhamChiTietService;

    @ModelAttribute("currentUser")
    private NhanVien currentUser() {
        return AppService.currentUser;
    }

    @ModelAttribute("isAdmin")
    private Boolean isAdmin(){
        return AppService.isAdmin(AppService.currentUser);
    }

    @ModelAttribute("listHoaDon")
    private List<HoaDon> getListHoaDon(){
        return hoaDonService.getList().data;
    }

    @ModelAttribute("listSPCT")
    private List<SanPhamChiTiet> getListSPCT(){
        return sanPhamChiTietService.listSPCTActive();
    }

    private PaginationUtil<HoaDonChiTiet> paginationUtil = new PaginationUtil<>();


    @GetMapping("")
    public String hienThi(@RequestParam(name = "page", defaultValue = "1", required = false) int page,
                          @RequestParam(name = "key", defaultValue = "", required = false) String key,
                          Model model) {
        if (currentUser() == null || !isAdmin()) {
            return "redirect:/loi";
        }
        List<HoaDonChiTiet> list = service.getList().data;
        if(!key.isBlank()) list = service.searchHoaDonChiTiet(list, key);
        int totalPage = paginationUtil.getTotalPages(list, PageConstant.pageSize);
        list = paginationUtil.paginate(list, page, PageConstant.pageSize);
        PageInfo<HoaDonChiTiet> listInfo = new PageInfo<HoaDonChiTiet>(list, page, totalPage);
        model.addAttribute("listInfo", listInfo);
        model.addAttribute("key", key);
        model.addAttribute("api", MappingConstant.API_HOA_DON_CHI_TIET);

        return MappingConstant.VIEW_HOA_DON_CHI_TIET + "/list";
    }

    @GetMapping("/update/{id}")
    public String formChiTiet(@PathVariable Integer id, Model model) {
        if (currentUser() == null || !isAdmin()) {
            return "redirect:/loi";
        }
        if (!model.containsAttribute("rp")) {
            ResponseObject<HoaDonChiTiet> rpHDCT = service.findById(id);
            ResponseObject<HoaDonChiTietReq> data = new ResponseObject<>(
                    rpHDCT.isHasError,
                    new HoaDonChiTietReq(rpHDCT.data.getId(),rpHDCT.data.hoaDon.getId(), rpHDCT.data.sanPhamChiTiet.getId(), rpHDCT.data.getSoLuong()),
                    rpHDCT.getMessage()
            );
            model.addAttribute("rp", data);
        } else {
            model.addAttribute("rp", model.getAttribute("rp"));
        }
        return MappingConstant.VIEW_HOA_DON_CHI_TIET + "/form-update";
    }

    @PostMapping("/update")
    public String sua(HoaDonChiTietReq req, Model model, RedirectAttributes red) {
        ResponseObject<HoaDonChiTietReq> data;
        if(req.idHoaDon == null || req.idSanPhamChiTiet == null){
            data = new ResponseObject<HoaDonChiTietReq>(true, req, "Vui lòng chọn hóa đơn và sản phẩm");
            return "redirect:" + MappingConstant.API_HOA_DON_CHI_TIET + "/update/" + req.getId();
        }
        HoaDonChiTiet hd = service.findById(req.id).data;
        SanPhamChiTiet spctBandau = hd.getSanPhamChiTiet();
        Integer soLuongBanDau = hd.getSoLuong();

        hd.setHoaDon(hoaDonService.findById(req.idHoaDon).data);
        SanPhamChiTiet spct = sanPhamChiTietService.findById(req.idSanPhamChiTiet).data;
        hd.setSanPhamChiTiet(spct);
        hd.setDonGia(spct.getDonGia());
        hd.setSoLuong(req.soLuong);
        ResponseObject<HoaDonChiTiet> rpHDCT= service.update(hd);

        data = new ResponseObject<>(rpHDCT.isHasError, req, rpHDCT.message);
        red.addFlashAttribute("rp", data);

        if (data.isHasError) {
            return "redirect:" + MappingConstant.API_HOA_DON_CHI_TIET + "/update/" + hd.getId();
        }else {
            spct.setSoLuong(spct.getSoLuong()-req.soLuong);
            sanPhamChiTietService.update(spct);

            spctBandau.setSoLuong(spctBandau.getSoLuong()+soLuongBanDau);
            sanPhamChiTietService.update(spctBandau);
        }
        return "redirect:" + MappingConstant.API_HOA_DON_CHI_TIET;
    }

}
