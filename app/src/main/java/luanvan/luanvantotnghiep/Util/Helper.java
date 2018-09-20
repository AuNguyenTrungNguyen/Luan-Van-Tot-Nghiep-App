package luanvan.luanvantotnghiep.Util;

import luanvan.luanvantotnghiep.Model.Element;

public class Helper {
    private static Helper sHelper;

    public static Helper getInstant(){

        if (sHelper == null){
            sHelper = new Helper();
        }

        return sHelper;
    }

    public String handelReactor(String chatThamGia, String chatSanPham) {

        String[] danhSachCTG = chatThamGia.split("\\+");
        String[] danhSachCSP = chatSanPham.split("\\+");

        StringBuilder phuongTrinh = new StringBuilder();
        for (int i = 0; i < danhSachCTG.length; i++) {
            String[] tachCTG = danhSachCTG[i].split(":");

            String heSo = tachCTG[0].trim();
            if (Integer.parseInt(heSo) == 1){
                heSo = "";
            }
            String chat = tachCTG[1].trim();
            chat = handelText(chat);
            phuongTrinh.append(heSo).append(chat);

            if (i < danhSachCTG.length - 1) {
                phuongTrinh.append(" + ");
            } else {
                phuongTrinh.append(" ");
            }
        }

        phuongTrinh.append(Constraint.SYMBOL + " ");

        for (int i = 0; i < danhSachCSP.length; i++) {
            String[] tachCSP = danhSachCSP[i].split(":");

            String heSo = tachCSP[0].trim();
            if (Integer.parseInt(heSo) == 1){
                heSo = "";
            }

            String chat = tachCSP[1].trim();
            chat = handelText(chat);
            phuongTrinh.append(heSo).append(chat);

            if (i < danhSachCSP.length - 1) {
                phuongTrinh.append(" + ");
            } else {
                phuongTrinh.append(" ");
            }
        }

        return phuongTrinh.toString();
    }

    public String handelText(String textInput) {
        String result = "";

        for (int j = 0; j < textInput.length(); j++) {
            char c = textInput.charAt(j);

            if (c >= '0' && c <= '9') {
                result += "<small><sub>" + c + "</sub></small>";
            } else {
                result += c;
            }
        }

        return result;
    }
}
