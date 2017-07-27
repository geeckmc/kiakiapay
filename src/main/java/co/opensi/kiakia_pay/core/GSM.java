package co.opensi.kiakia_pay.core;


import co.opensi.kiakia_pay.R;

public class GSM {

    public static int IS_MOOV_PHONE = R.string.moov_flooz;
    public static int IS_MTN_PHONE = R.string.mtn_money;
    public static int IS_NOT_VALID_PHONE = -1;

    public static int MTN = -10;
    public static int MOOV = -12;

    public static int BENIN_PHONE_LENGTH = 8;



    public static String[] PREFIXES_MOOV = {"64", "65", "94", "95"};
    public static String[] PREFIXES_MTN = {"67", "66", "97", "96","62","61"};
    // private String phone = "";

    private static boolean isMoovPrefix(String phone) {
        if (phone == null) {
            throw new NullPointerException("phone == null");
        }
        for (String prefix : PREFIXES_MOOV) {
            if (phone.trim().startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isMTNPrefix(String phone) {
        if (phone == null) {
            throw new NullPointerException("phone == null");
        }
        for (String prefix : PREFIXES_MTN) {
            if (phone.trim().startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if a phone number has a valid digits length
     *
     * @param phone number to check
     * @return true if phone number as the valid number of digits. false otherwise
     * @throws NullPointerException if {@code string} is {@code null}.
     */
    private static boolean isPhoneLengthValid(String phone) {
        return isPhoneLengthValid(phone, 8);
    }

    /**
     * Check if a phone number is up to a valid digits length
     *
     * @param phone     number to check
     * @param validSize the required size of digits
     * @return true if phone number as the valid number of digits. false otherwise@throws NullPointerException
     * if {@code string} is {@code null}.
     */
    public static boolean isPhoneLengthValid(String phone, int validSize) {
        if (phone == null) {
            throw new NullPointerException("phone == null");
        }
        return phone.trim().length() == validSize;
    }

    /**
     * Return the GSM service provider to which this number belongs to
     *
     * @param phone to check
     * @return {@link co.opensi.kiakia.m.utils.GSM#IS_NOT_VALID_PHONE} if number does not belong to neither MOOV nor MTN. <br>
     * {@link co.opensi.kiakia.m.utils.GSM#IS_MTN_PHONE} if it belongs to MTN, <br>
     * {@link co.opensi.kiakia.m.utils.GSM#IS_MOOV_PHONE} if it belongs to MOOV <br>
     * @throws NullPointerException if {@code string} is {@code null}.
     */
    public static int getOperator(String phone) {
        if (isPhoneLengthValid(phone)) {
            if (isMoovPrefix(phone)) {
                return IS_MOOV_PHONE;
            } else if (isMTNPrefix(phone)) {
                return IS_MTN_PHONE;
            } else {
                return IS_NOT_VALID_PHONE;
            }
        }
        return IS_NOT_VALID_PHONE;
    }

    public static String getMobileMoneyUssd(String phone, String password, String amount) {
        return "*400*1*" + phone + "*" + phone + "*" + amount + "*" + password + "#";

    }
    public static String getMe2UMobileMoneyUssd(String phone, String password, String amount) {
        return "*400*1*1" + phone + "*" + phone + "*" + amount + "*" + password + "#";

    }

    /**
     * Check if a phone number has a valid prefix
     *
     * @param phone number to check
     * @return true if phone number as the valid prefix. false otherwise
     * @throws NullPointerException if {@code string} is {@code null}.
     */

    private boolean isPhonePrefixValid(String phone) {
        boolean isPrefixValid = false;

        isPrefixValid = (isMoovPrefix(phone) || isMTNPrefix(phone));
        return isPrefixValid;
    }

}