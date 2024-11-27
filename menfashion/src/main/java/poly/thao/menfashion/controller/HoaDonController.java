package poly.thao.menfashion.controller;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import poly.thao.menfashion.entity.KhachHang;
import poly.thao.menfashion.entity.NhanVien;
import poly.thao.menfashion.model.request.HoaDonReq;
import poly.thao.menfashion.model.response.HoaDonResponse;
import poly.thao.menfashion.model.response.PageInfo;
import poly.thao.menfashion.model.response.ResponseObject;
import poly.thao.menfashion.service.AppService;
import poly.thao.menfashion.service.HoaDonChiTietService;
import poly.thao.menfashion.service.HoaDonService;
import poly.thao.menfashion.service.KhachHangService;
import poly.thao.menfashion.service.NhanVienService;
import poly.thao.menfashion.utils.MappingConstant;
import poly.thao.menfashion.utils.PageConstant;
import poly.thao.menfashion.utils.helper.Helper;
import poly.thao.menfashion.utils.helper.PaginationUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Boolean isAdmin() {
        return AppService.isAdmin(AppService.currentUser);
    }

    @ModelAttribute("listKhachHang")
    private List<KhachHang> getListKhachHang() {
        return khachHangService.getList().data;
    }

    @ModelAttribute("listNhanVien")
    private List<NhanVien> getListNhanVien() {
        return nhanVienService.getList().data;
    }

    @ModelAttribute("listTongGia")
    private List<Double> getListTongGia() {
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
        if (!key.isBlank()) list = service.searchHoaDon(list, key);
        int totalPage = paginationUtil.getTotalPages(list, PageConstant.pageSize);
        list = paginationUtil.paginate(list, page, PageConstant.pageSize);
        PageInfo<HoaDon> listInfo = new PageInfo<HoaDon>(list, page, totalPage);
        Map<HoaDon, Double> map = new HashMap<>();
        for (HoaDon hoaDon : list) {
            map.put(hoaDon, service.getTongGia(hoaDon.getId()));
        }
        model.addAttribute("listInfo", listInfo);
        model.addAttribute("map", map);
        model.addAttribute("key", key);
        model.addAttribute("api", MappingConstant.API_HOA_DON);
        return MappingConstant.VIEW_HOA_DON + "/list";
    }

    @GetMapping("/chi-tiet/{id}")
    public String hienThiChiTietHoaDon(@PathVariable("id") Integer id, Model model) {
        HoaDon hd = service.findById(id).data;
        List<HoaDonChiTiet> listHoaDonChiTiet = service.getListHDCTByHoaDonId(id);
        HoaDonResponse hoaDonResponse = new HoaDonResponse(listHoaDonChiTiet, service.getTongGia(id));

        model.addAttribute("data", hoaDonResponse);
        return MappingConstant.VIEW_HOA_DON + "/chi-tiet-hoa-don";
    }

//    @GetMapping("/update/{id}")
//    public String formChiTiet(@PathVariable Integer id, Model model) {
//        if (currentUser() == null || !isAdmin()) {
//            return "redirect:/loi";
//        }
//        if (!model.containsAttribute("rp")) {
//            ResponseObject<HoaDon> rpHD = service.findById(id);
//            ResponseObject<HoaDonReq> data = new ResponseObject<>(
//                    rpHD.isHasError,
//                    new HoaDonReq(rpHD.data.getId(), rpHD.data.khachHang.getId(), rpHD.data.nhanVien.getId()),
//                    rpHD.getMessage()
//                    );
//            model.addAttribute("rp", data);
//        } else {
//            model.addAttribute("rp", model.getAttribute("rp"));
//        }
//        return MappingConstant.VIEW_HOA_DON + "/form-update";
//    }

    @PostMapping("/update")
    public String sua(HoaDonReq hoaDonReq, Model model, RedirectAttributes red) {
        ResponseObject<HoaDonReq> data;
        if (hoaDonReq.idKhachHang == null || hoaDonReq.idNhanVien == null) {
            data = new ResponseObject<HoaDonReq>(true, hoaDonReq, "Vui lòng chọn khách hàng và nhân viên");
            return "redirect:" + MappingConstant.API_HOA_DON + "/update/" + hoaDonReq.getId();
        }
        HoaDon hd = service.findById(hoaDonReq.id).data;
        hd.setKhachHang(khachHangService.findById(hoaDonReq.idKhachHang).data);
        hd.setNhanVien(nhanVienService.findById(hoaDonReq.idNhanVien).data);
        ResponseObject<HoaDon> rpHD = service.update(hd);
        data = new ResponseObject<>(true, hoaDonReq, rpHD.message);
        red.addFlashAttribute("rp", data);
        if (rpHD.isHasError) {
            return "redirect:" + MappingConstant.API_HOA_DON + "/update/" + hoaDonReq.getId();
        }
        return "redirect:" + MappingConstant.API_HOA_DON;
    }


    // Method để export hóa đơn thành PDF
    @GetMapping("/chi-tiet/pdf/{id}")
    public void exportToPDF(@PathVariable("id") Integer id, HttpServletResponse response) throws DocumentException, IOException {
        ResponseObject<String> validateIdHoaDon = validateIdHoaDon(id);
        if (validateIdHoaDon.isHasError){
            response.sendError(HttpStatus.BAD_REQUEST.value());
            return;
        }
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=hoa_don_" + id + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<HoaDonChiTiet> listHoaDonChiTiet = service.getListHDCTByHoaDonId(id);
        HoaDonResponse hoaDonResponse = new HoaDonResponse(listHoaDonChiTiet, service.getTongGia(id));

        // Tạo tài liệu PDF
        Document document = new Document(PageSize.EXECUTIVE);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        // Thêm tiêu đề hóa đơn
        Paragraph title = new Paragraph("MAN FASHION");
        title.setAlignment(1);
        document.add(title);
        document.add(new Paragraph("Chi tiết hóa đơn #" + hoaDonResponse.getHoaDon().getId()));
        document.add(new Paragraph("Nhân viên: " + hoaDonResponse.getHoaDon().getNhanVien().getTen()));
        document.add(new Paragraph("Khách hàng: " + hoaDonResponse.getHoaDon().getKhachHang().getTen()));
        document.add(new Paragraph("Ngày mua: " + hoaDonResponse.getHoaDon().getNgayMuaHang()));
        document.add(new Paragraph("Tổng giá: " + hoaDonResponse.getTongGia()));
        document.add(new Paragraph(" "));

        // Tạo bảng để hiển thị chi tiết sản phẩm
        PdfPTable table = new PdfPTable(7); // Số cột
        table.addCell("STT");
        table.addCell("Sản phẩm");
        table.addCell("Kích thước");
        table.addCell("Màu sắc");
        table.addCell("Số lượng");
        table.addCell("Đơn giá");
        table.addCell("Thành tiền");

        // Duyệt qua list chi tiết hóa đơn
        List<HoaDonChiTiet> listHDCT = hoaDonResponse.getListHDCT();
        for (int i = 0; i < listHDCT.size(); i++) {
            HoaDonChiTiet hdct = listHDCT.get(i);
            table.addCell(String.valueOf(i + 1));
            table.addCell(hdct.getSanPhamChiTiet().getSanPham().getTen());
            table.addCell(hdct.getSanPhamChiTiet().getKichThuoc().getTen());
            table.addCell(hdct.getSanPhamChiTiet().getMauSac().getTen());
            table.addCell(String.valueOf(hdct.getSoLuong()));
            table.addCell(String.valueOf(hdct.getDonGia()));
            table.addCell(String.valueOf(hdct.getSoLuong() * hdct.getDonGia()));
        }

        // Thêm bảng vào tài liệu
        document.add(table);

        document.close();
    }


    public ResponseObject<String> validateIdHoaDon(Object idOb) {
        try {
            if (idOb == null) {
                return new ResponseObject<>(true, "", "Id must not null");
            }
            String id = idOb.toString();
            System.out.println(id);
            if (!id.matches("^-?\\d+$")) {
                return new ResponseObject<>(true, "", "Id must be Integer");
            }
            if (Integer.parseInt(id) <= 0) {
                return new ResponseObject<>(true, "", "Invalid Id");
            }
            if (!service.isExistById(Integer.parseInt(id))) {
                return new ResponseObject<>(true, "", "Id not found");
            }
            List<HoaDonChiTiet> listHDCT = service.getListHDCTByHoaDonId(Integer.parseInt(id));
            if (listHDCT.isEmpty()) {
                return new ResponseObject<>(true, "", "list HDCT empty");
            }
        } catch (NumberFormatException ex) {
            return new ResponseObject<>(true, "", "Invalid Id");
        } catch (RuntimeException ex) {
            return new ResponseObject<>(true, "", "Server error");
        }
        return new ResponseObject<>(false, "", "Id hoa don OK");
    }

}
