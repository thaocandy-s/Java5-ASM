package poly.thao.menfashion.color;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import poly.thao.menfashion.entity.MauSac;
import poly.thao.menfashion.model.EntityStatus;
import poly.thao.menfashion.repository.MauSacRepository;
import poly.thao.menfashion.service.MauSacService;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
public class MauSacTest {

    private final Integer DEFAULT_ID_COLOR = 1;

    @Autowired
    private MauSacRepository mauSacRepository;

    private MauSacService mauSacService;

    @BeforeEach
    void setUp() {
        mauSacService = new MauSacService(mauSacRepository);
    }

    @AfterEach
    void tearDown() {
    }

    // ADD
    @Test
    public void testAddMauSacMS_01() { // Thêm màu sắc thành công
        MauSac mauSac = new MauSac(null, "MS001", EntityStatus.ACTIVE, "Den");
        Assertions.assertEquals("Thêm MS thành công", mauSacService.add(mauSac).message);
    }

    @Test
    public void testAddMauSacMS_02() { // Thêm màu sắc thất bại khi để trống trường mã màu sắc
        MauSac mauSac = new MauSac(null, "", EntityStatus.ACTIVE, "Den");
        Assertions.assertEquals("Mã màu sắc cần 5 ký tự: MS+3 số bất kỳ", mauSacService.add(mauSac).message);
    }

    @Test
    public void testAddMauSacMS_03() { // Thêm màu sắc thất bại khi để trống trường tên màu sắc
        MauSac mauSac = new MauSac(null, "MS002", EntityStatus.ACTIVE, "");
        Assertions.assertEquals("Tên màu sắc cần 2-10 ký tự, không số và ký tự đặc biệt", mauSacService.add(mauSac).message);
    }

    @Test
    public void testAddMauSacMS_04() { // Thêm màu sắc thất bại khi trùng mã màu sắc
        MauSac mauSacDuplicateCode = new MauSac(null, "MS001", EntityStatus.ACTIVE, "Hong");
        Assertions.assertEquals("Mã bị trùng", mauSacService.add(mauSacDuplicateCode).message);
    }

    @Test
    public void testAddMauSacMS_05() { // Thêm màu sắc thất bại khi mã màu sắc chứa 4 ký tự
        MauSac mauSac = new MauSac(null, "MS03", EntityStatus.ACTIVE, "Hong");
        Assertions.assertEquals("Mã màu sắc cần 5 ký tự: MS+3 số bất kỳ", mauSacService.add(mauSac).message);
    }

    @Test
    public void testAddMauSacMS_06() { // Thêm màu sắc thất bại khi mã màu sắc chứa 6 ký tự
        MauSac mauSac = new MauSac(null, "MS0004", EntityStatus.ACTIVE, "Hong");
        Assertions.assertEquals("Mã màu sắc cần 5 ký tự: MS+3 số bất kỳ", mauSacService.add(mauSac).message);
    }

    @Test
    public void testAddMauSacMS_07() { // Thêm màu sắc thất bại khi tên màu sắc chứa 1 ký tự
        MauSac mauSac = new MauSac(null, "MS005", EntityStatus.ACTIVE, "a");
        Assertions.assertEquals("Tên màu sắc cần 2-10 ký tự, không số và ký tự đặc biệt", mauSacService.add(mauSac).message);
    }

    @Test
    public void testAddMauSacMS_08() { //Thêm màu sắc thất bại khi tên màu sắc chứa 11 ký tự
        MauSac mauSac = new MauSac(null, "MS006", EntityStatus.ACTIVE, "Mau sac dep");
        Assertions.assertEquals("Tên màu sắc cần 2-10 ký tự, không số và ký tự đặc biệt", mauSacService.add(mauSac).message);
    }

    @Test
    public void testAddMauSacMS_09() { // Thêm màu sắc thất bại khi tên màu sắc chứa số
        MauSac mauSac = new MauSac(null, "MS007", EntityStatus.ACTIVE, "Xanh12");
        Assertions.assertEquals("Tên màu sắc cần 2-10 ký tự, không số và ký tự đặc biệt", mauSacService.add(mauSac).message);
    }

    @Test
    public void testAddMauSacMS_10() { // Thêm màu sắc thất bại khi tên màu sắc chứa ký tự đặc biệt
        MauSac mauSac = new MauSac(null, "MS008", EntityStatus.ACTIVE, "Xanh@@");
        Assertions.assertEquals("Tên màu sắc cần 2-10 ký tự, không số và ký tự đặc biệt", mauSacService.add(mauSac).message);
    }

//    // UPDATE
    @Test
    void testUpdateMauSacMS_11() { // Sửa màu sắc thành công khi điền các trường hợp lệ
        MauSac mauSac = mauSacService.findByCode("MS001").getData();
        mauSac.setTen("Xanh");
        Assertions.assertEquals("Sửa MS thành công", mauSacService.update(mauSac).getMessage());
    }

    @Test
    void testUpdateMauSacMS_12() { // Sửa màu sắc thành công khi tên màu sắc chứa 2 ký tự tại biên dưới
        MauSac mauSac = mauSacService.findByCode("MS001").getData();
        mauSac.setTen("Do");
        Assertions.assertEquals("Sửa MS thành công", mauSacService.update(mauSac).getMessage());
    }

    @Test
    void testUpdateMauSacMS_13() { // Sửa màu sắc thành công khi tên màu sắc chứa 3 ký tự tại biên dưới
        MauSac mauSac = mauSacService.findByCode("MS001").getData();
        mauSac.setTen("Nau");
        Assertions.assertEquals("Sửa MS thành công", mauSacService.update(mauSac).getMessage());
    }

    @Test
    void testUpdateMauSacMS_14() { // Sửa màu sắc thành công khi tên màu sắc chứa 9 ký tự tại biên dưới
        MauSac mauSac = mauSacService.findByCode("MS001").getData();
        mauSac.setTen("Xanh ngoc");
        Assertions.assertEquals("Sửa MS thành công", mauSacService.update(mauSac).getMessage());
    }

    @Test
    void testUpdateMauSacMS_15() { // Sửa màu sắc thành công khi tên màu sắc chứa 10 ký tự tại biên dưới
        MauSac mauSac = mauSacService.findByCode("MS001").getData();
        mauSac.setTen("Xanh duong");
        Assertions.assertEquals("Sửa MS thành công", mauSacService.update(mauSac).getMessage());
    }

    @Test
    void testUpdateMauSacMS_16() { // Sửa màu sắc thất bại khi để trống trường tên màu sắc
        MauSac mauSac = mauSacService.findByCode("MS001").getData();
        mauSac.setTen("");
        Assertions.assertEquals("Tên màu sắc cần 2-10 ký tự, không số và ký tự đặc biệt", mauSacService.update(mauSac).getMessage());
    }

    @Test
    void testUpdateMauSacMS_17() { // Sửa màu sắc thất bại khi tên màu sắc chứa 1 ký tự tại ngoài biên dưới
        MauSac mauSac = mauSacService.findByCode("MS001").getData();
        mauSac.setTen("A");
        Assertions.assertEquals("Tên màu sắc cần 2-10 ký tự, không số và ký tự đặc biệt", mauSacService.update(mauSac).getMessage());
    }

    @Test
    void testUpdateMauSacMS_18() { // Sửa màu sắc thất bại khi tên màu sắc chứa 11 ký tự tại ngoài biên dưới
        MauSac mauSac = mauSacService.findByCode("MS001").getData();
        mauSac.setTen("Mau sac dep");
        Assertions.assertEquals("Tên màu sắc cần 2-10 ký tự, không số và ký tự đặc biệt", mauSacService.update(mauSac).getMessage());
    }

    @Test
    void testUpdateMauSacMS_19() { // Sửa màu sắc thất bại khi tên màu sắc chứa số
        MauSac mauSac = mauSacService.findByCode("MS001").getData();
        mauSac.setTen("Cam22");
        Assertions.assertEquals("Tên màu sắc cần 2-10 ký tự, không số và ký tự đặc biệt", mauSacService.update(mauSac).getMessage());
    }

    @Test
    void testUpdateMauSacMS_20() { // Sửa màu sắc thất bại khi tên màu sắc chứa ký tự đặc biệt
        MauSac mauSac = mauSacService.findByCode("MS001").getData();
        mauSac.setTen("Cam@@");
        Assertions.assertEquals("Tên màu sắc cần 2-10 ký tự, không số và ký tự đặc biệt", mauSacService.update(mauSac).getMessage());
    }



    // FINDBYID

    @Test
    void testFindByIdMauSacMS_21() { // Tìm kiếm màu săc thành công với id = 1 hợp lệ
        Assertions.assertEquals("Lấy data MS thành công", mauSacService.findById(1).getMessage());
    }


    @Test
    void testFindByIdMauSacMS_22() { // Tìm kiếm màu sắc thất bại với id = 5 hợp lệ
        Assertions.assertEquals("MS không tồn tại", mauSacService.findById(5).getMessage());
    }

    @Test
    void testFindByIdMauSacMS_23() { // Tìm kiếm màu sắc thành công với id = 2 hợp lệ
        Assertions.assertEquals("MS không tồn tại", mauSacService.findById(2).getMessage());
    }

    @Test
    void testFindByIdMauSacMS_24() { // Tìm kiếm màu sắc thất bại với id = 0
        Assertions.assertEquals("MS không tồn tại", mauSacService.findById(0).getMessage());
    }

    @Test
    void testFindByIdMauSacMS_25() { // Tìm kiếm sản phẩm không thành công với id không tồn tại
        Assertions.assertEquals("MS không tồn tại", mauSacService.findById(20).getMessage());
    }

    @Test
    void testFindByIdMauSacMS_26() { // Tìm kiếm sản phẩm không thành công với id = 100
        Assertions.assertEquals("MS không tồn tại", mauSacService.findById(100).getMessage());
    }

    @Test
    void testFindByIdMauSacMS_27() { // Tìm kiếm màu sắc tht bại với id = -1
        Assertions.assertEquals("MS không tồn tại", mauSacService.findById(-1).getMessage());
    }
    @Test
    void testFindByIdMauSacMS_28() { // Tìm kiếm màu sắc thất bại với id = -100
        Assertions.assertEquals("MS không tồn tại", mauSacService.findById(-100).getMessage());
    }

    @Test
    void testFindByIdMauSacMS_29() { // Tìm kiếm sản phẩm không thành công với id quá giá trị Int
        Assertions.assertEquals("MS không tồn tại", mauSacService.findById((int) 2147483648L).getMessage());
    }

    @Test
    void testFindByIdMauSacMS_30() { // Tìm kiếm sản phẩm không thành công với id cực nhỏ giá trị Int
        Assertions.assertEquals("MS không tồn tại", mauSacService.findById((int) -2147483649L).getMessage());
    }



//    @Test
//    public void updateMauSacMS22() { // Sửa màu sắc thành công khi tên màu sắc chứa 2 ký tự tại biên dưới
//        Optional<MauSac> mauSacOptional = mauSacRepository.findById(DEFAULT_ID_COLOR);
//        if (mauSacOptional.isPresent()) {
//            MauSac mauSac = mauSacOptional.get();
//            mauSac.setTen("Do");
//            Assertions.assertEquals("Sửa MS thành công", mauSacService.update(mauSac).message);
//            Assertions.assertEquals("Do", mauSacService.update(mauSac).data.getTen());
//        } else {
//            Assertions.fail("Không tìm thấy MauSac với ID tương ứng.");
//        }
//    }
//
//    @Test
//    public void updateMauSacMS23() { // Sửa màu sắc thành công khi tên màu sắc chứa 3 ký tự tại biên dưới
//        Optional<MauSac> mauSacOptional = mauSacRepository.findById(DEFAULT_ID_COLOR);
//        if (mauSacOptional.isPresent()) {
//            MauSac mauSac = mauSacOptional.get();
//            mauSac.setTen("Nau");
//            Assertions.assertEquals("Sửa MS thành công", mauSacService.update(mauSac).message);
//            Assertions.assertEquals("Nau", mauSacService.update(mauSac).data.getTen());
//        } else {
//            Assertions.fail("Không tìm thấy MauSac với ID tương ứng.");
//        }
//    }
//
//    @Test
//    public void updateMauSacValidate() {
//        Optional<MauSac> mauSacOptional = mauSacRepository.findById(DEFAULT_ID_COLOR);
//        if (mauSacOptional.isPresent()) {
//            MauSac mauSac = mauSacOptional.get();
//            mauSac.setTen("blue");
//            mauSac.setMa("");
//            Assertions.assertEquals("Mã màu sắc cần 5 ký tự: MS+3 số bất kỳ", mauSacService.update(mauSac).message);
//            mauSac.setTen("blue");
//            mauSac.setMa("MS3");
//            Assertions.assertEquals("Mã màu sắc cần 5 ký tự: MS+3 số bất kỳ", mauSacService.update(mauSac).message);
//        } else {
//            Assertions.fail("Không tìm thấy MauSac với ID tương ứng.");
//        }
//    }

}
