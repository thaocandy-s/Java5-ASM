package poly.thao.menfashion.utils.helper;

import java.util.List;

public class PaginationUtil<T> {
    // Hàm phân trang
    public List<T> paginate(List<T> items, int currentPage, int pageSize) {
        // Tính tổng số phần tử và số trang
        int totalItems = items.size();
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);

        // Đảm bảo currentPage không vượt quá giới hạn
        if (currentPage > totalPages) {
            currentPage = totalPages;
        }
        if (currentPage < 1) {
            currentPage = 1;
        }

        // Tính vị trí bắt đầu và kết thúc
        int fromIndex = (currentPage - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, totalItems);

        // Trả về danh sách con của trang hiện tại
        return items.subList(fromIndex, toIndex);
    }

    // Hàm lấy tổng số trang
    public int getTotalPages(List<T> items, int pageSize) {
        return (int) Math.ceil((double) items.size() / pageSize);
    }
}
