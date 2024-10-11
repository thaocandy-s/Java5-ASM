package poly.thao.menfashion.utils.helper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {
    public static int generateRandomId() {
        Random random = new Random();
        return random.nextInt(900) + 100;
    }

    private static final String CHU_CO_DAU = "[áàảãạăắằẳẵặâấầẩẫậđéèẻẽẹêếềểễệíìỉĩịóòỏõọôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬĐÉÈẺẼẸÊẾỀỂỄỆÍÌỈĨỊÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤƯỨỪỬỮỰÝỲỶỸỴ]";

    public static boolean isChuCoDau(String input) {
        Pattern pattern = Pattern.compile(CHU_CO_DAU);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    private static final String PHONE_REGEX = "^0[0-9]{9,14}$";

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && Pattern.matches(PHONE_REGEX, phoneNumber);
    }

    public static String formatDateTime(String sqlDateTime) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(sqlDateTime, inputFormatter);

        return dateTime.format(outputFormatter);
    }
}
