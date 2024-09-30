package poly.thao.menfashion.model.request;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginReq {

    @NotBlank(message = "user name không được để trống")
    public String username;

    @NotBlank(message = "password không được để trống")
    public String password;


}
