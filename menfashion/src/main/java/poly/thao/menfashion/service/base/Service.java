package poly.thao.menfashion.service.base;

import poly.thao.menfashion.model.response.ResponseObject;

import java.util.List;

public interface Service<T> {

    public ResponseObject<List<T>> getList();

    public ResponseObject<T> add(T e);

    public ResponseObject<T> update(T e);

    public ResponseObject<T> findById(Integer id);

    public ResponseObject<T> findByCode(String code);

    public ResponseObject<Integer> deleteById(Integer id);

    public ResponseObject<String> deleteByCode(String code);
}
