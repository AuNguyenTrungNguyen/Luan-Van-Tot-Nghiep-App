package luanvan.luanvantotnghiep.Util;

import android.annotation.SuppressLint;
import android.util.Log;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import luanvan.luanvantotnghiep.Model.Element;

public class Helper {
    private static Helper sHelper;

    public static Helper getInstant(){

        if (sHelper == null){
            sHelper = new Helper();
        }

        return sHelper;
    }

    public String handelReactor(String chatThamGia, String chatSanPham, int twoWay) {

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

        if (twoWay == 1){
            phuongTrinh.append(Constraint.SYMBOL_TWO_WAY + " ");
        }else{
            phuongTrinh.append(Constraint.SYMBOL + " ");
        }

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

    //2:H2O --> h2o
    public String handelChemistryInReaction(String chemistry){
        String[] tachChemistry = chemistry.split(":");
        return  tachChemistry[1].toLowerCase().trim();
    }

    @SuppressLint("NewApi")
    public String encodeSHA512Extent(String text, int extent) {
        StringBuilder sb = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            if (extent == 1) {
                md.update(Constraint.SLAT_RANK_EASY.getBytes(StandardCharsets.UTF_8));
            } else if (extent == 2) {
                md.update(Constraint.SLAT_RANK_NORMAL.getBytes(StandardCharsets.UTF_8));
            } else if (extent == 3) {
                md.update(Constraint.SLAT_RANK_DIFFICULT.getBytes(StandardCharsets.UTF_8));
            }
            byte[] bytes = md.digest(text.getBytes(StandardCharsets.UTF_8));
            for (byte aDigest : bytes) {
                //sb.append(Integer.toString((aDigest & 0xff) + 0x100, 16).substring(1));
                sb.append(String.format("%02x", aDigest));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @SuppressLint("NewApi")
    public String encodeSHA512(String text) {
        StringBuilder sb = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(Constraint.SLAT.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(text.getBytes(StandardCharsets.UTF_8));
            for (byte aDigest : bytes) {
                //sb.append(Integer.toString((aDigest & 0xff) + 0x100, 16).substring(1));
                sb.append(String.format("%02x", aDigest));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
