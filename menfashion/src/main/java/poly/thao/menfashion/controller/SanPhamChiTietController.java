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
import poly.thao.menfashion.entity.MauSac;
import poly.thao.menfashion.entity.NhanVien;
import poly.thao.menfashion.entity.SanPham;
import poly.thao.menfashion.entity.SanPhamChiTiet;
import poly.thao.menfashion.model.request.SanPhamChiTietRequest;
import poly.thao.menfashion.model.response.SanPhamChiTietDTO;
import poly.thao.menfashion.model.response.PageInfo;
import poly.thao.menfashion.model.response.ResponseObject;
import poly.thao.menfashion.service.AppService;
import poly.thao.menfashion.service.KichThuocService;
import poly.thao.menfashion.service.MauSacService;
import poly.thao.menfashion.service.SanPhamChiTietService;
import poly.thao.menfashion.service.SanPhamService;
import poly.thao.menfashion.utils.MappingConstant;
import poly.thao.menfashion.utils.PageConstant;
import poly.thao.menfashion.utils.helper.PaginationUtil;

import java.util.List;

@Controller
@RequestMapping(MappingConstant.API_SAN_PHAM_CHI_TIET)
public class SanPhamChiTietController {

    @Autowired
    public SanPhamChiTietService service;
    @Autowired
    public MauSacService mauService;
    @Autowired
    public KichThuocService KTService;
    @Autowired
    public SanPhamService SPService;

    @ModelAttribute("currentUser")
    private NhanVien currentUser() {
        return AppService.currentUser;
    }

    @ModelAttribute("isAdmin")
    private Boolean isAdmin(){
        return AppService.isAdmin(AppService.currentUser);
    }

    private PaginationUtil<SanPhamChiTietDTO> paginationUtil = new PaginationUtil<>();

    @ModelAttribute("listMau")
    private List<MauSac> getListMau() {
        return mauService.getList().data;
    }

    @ModelAttribute("listKT")
    private List<KichThuoc> getListKT() {
        return KTService.getList().data;
    }

    @ModelAttribute("listSP")
    private List<SanPham> getListSP() {
        return SPService.getList().data;
    }


    @GetMapping("")
    public String hienThi(
            @RequestParam(name = "key", defaultValue = "", required = false) String key,
            @RequestParam(name = "page", defaultValue = "1", required = false) int page, Model model) {
        if (currentUser() == null) {
            return "redirect:/loi";
        }
        List<SanPhamChiTietDTO> list = service.getListDTO().data;
        if(!key.isBlank()) list = service.search(list, key);

        int totalPage = paginationUtil.getTotalPages(list, PageConstant.pageSize);
        list = paginationUtil.paginate(list, page, PageConstant.pageSize);
        PageInfo<SanPhamChiTietDTO> listInfo = new PageInfo<SanPhamChiTietDTO>(list, page, totalPage);
        model.addAttribute("listInfo", listInfo);
        model.addAttribute("key", key);
        model.addAttribute("api", MappingConstant.API_SAN_PHAM_CHI_TIET);

        return MappingConstant.VIEW_SAN_PHAM_CHI_TIET + "/list";
    }

    @GetMapping("/add")
    public String formThem() {
        if (currentUser() == null) {
            return "redirect:/loi";
        }
        return MappingConstant.VIEW_SAN_PHAM_CHI_TIET + "/form-add";
    }

    @PostMapping("/add")
    public String them(SanPhamChiTietRequest sanPhamChiTietRequest, Model model, RedirectAttributes red) {
        SanPhamChiTiet sanPhamChiTiet = getSPCTfromReq(sanPhamChiTietRequest);
        ResponseObject<SanPhamChiTiet> rpSPCT = service.add(sanPhamChiTiet);
        ResponseObject<SanPhamChiTietRequest> data = getReq(rpSPCT);

        red.addFlashAttribute("rp", data);
        if (data.isHasError) {
            return "redirect:" + MappingConstant.API_SAN_PHAM_CHI_TIET + "/add";
        }
        return "redirect:" + MappingConstant.API_SAN_PHAM_CHI_TIET;
    }

    @GetMapping("/update/{id}")
    public String formChiTiet(@PathVariable Integer id, Model model) {
        if (currentUser() == null) {
            return "redirect:/loi";
        }
        if (!model.containsAttribute("rp")) {
            ResponseObject<SanPhamChiTiet> rpSPCT = service.findById(id);
            ResponseObject<SanPhamChiTietRequest> data = getReq(rpSPCT);
            model.addAttribute("rp", data);
        } else {
            model.addAttribute("rp", model.getAttribute("rp"));
        }
        return MappingConstant.VIEW_SAN_PHAM_CHI_TIET + "/form-update";
    }

    @PostMapping("/update")
    public String sua(SanPhamChiTietRequest sanPhamChiTietRequest, Model model, RedirectAttributes red) {

        SanPhamChiTiet sanPhamChiTiet = getSPCTfromReq(sanPhamChiTietRequest);
        ResponseObject<SanPhamChiTiet> rpSPCT = service.update(sanPhamChiTiet);
        ResponseObject<SanPhamChiTietRequest> data = getReq(rpSPCT);
        red.addFlashAttribute("rp", data);
        if (data.isHasError) {
            return "redirect:" + MappingConstant.API_SAN_PHAM_CHI_TIET + "/update/" + sanPhamChiTiet.getId();
        }
        return "redirect:" + MappingConstant.API_SAN_PHAM_CHI_TIET;
    }

    @GetMapping("/delete/{id}")
    public String xoa(@PathVariable Integer id, Model model, RedirectAttributes red) {
        if (currentUser() == null) {
            return "redirect:/loi";
        }
        ResponseObject<Integer> data = service.deleteById(id);
        red.addFlashAttribute("rp", data);
        return "redirect:" + MappingConstant.API_SAN_PHAM_CHI_TIET;
    }

    public SanPhamChiTiet getSPCTfromReq(SanPhamChiTietRequest request){
        SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet(
                request.getId(),
                request.getMa(),
                request.getTrangThai(),
                KTService.findById(request.getIdKichThuoc()).data,
                mauService.findById(request.getIdMauSac()).data,
                SPService.findById(request.getIdSanPham()).data,
                request.getSoLuong(),
                request.getDonGia()
        );
        return sanPhamChiTiet;
    }

    public ResponseObject<SanPhamChiTietRequest> getReq(ResponseObject<SanPhamChiTiet> rpSPCT){
        SanPhamChiTietRequest req = new SanPhamChiTietRequest(
                rpSPCT.data.getId(),
                rpSPCT.data.getMa(),
                rpSPCT.data.getSanPham().getId(),
                rpSPCT.data.getKichThuoc().getId(),
                rpSPCT.data.getMauSac().getId(),
                rpSPCT.data.getSoLuong(),
                rpSPCT.data.getDonGia(),
                rpSPCT.data.getTrangThai()
                );
        return new ResponseObject<SanPhamChiTietRequest>(rpSPCT.isHasError, req, rpSPCT.getMessage());
    }
}
