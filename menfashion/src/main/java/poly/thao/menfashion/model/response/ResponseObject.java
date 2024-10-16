package poly.thao.menfashion.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseObject<T> {

    public Boolean isHasError;

    public T data;

    public String message;

    public ResponseObject(Boolean isHasError, T data, String message) {
        this.isHasError = isHasError;
        this.data = data;
        this.message = message;
    }



}
