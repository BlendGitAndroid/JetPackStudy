package com.blend.jetpackstudy.databinding;

class BookRatingUtil {

    public static String getRatingString(int rating) {
        switch (rating) {
            case 0:
                return "零星";
            case 1:
                return "一星";
            case 2:
                return "二星";
            case 3:
                return "三星";
        }
        return "";
    }

}
