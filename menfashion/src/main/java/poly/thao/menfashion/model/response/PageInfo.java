package poly.thao.menfashion.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageInfo<T> {

    public List<T> list;

    public int currentPage;

    public int totalPage;

}
