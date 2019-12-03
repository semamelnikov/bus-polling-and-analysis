package ru.sd.util;

import javax.swing.text.MaskFormatter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class Formatter {
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("dd.MM.yy HH:mm:ss");

    public static Date formatStringToDate(final String date) {
        try {
            return FORMAT.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Date.from(Instant.now());
    }

    public static String formatCoordinates(final String value) {
        return format(value, "##*####");
    }

    public static String formatDate(final String value) {
        return format(value, "##*##*####");
    }

    private static String format(final String value, final String mask) {
        String result = "";
        try {
            MaskFormatter formatter = new MaskFormatter(mask);
            formatter.setValueContainsLiteralCharacters(false);
            result = formatter.valueToString(value);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
}
