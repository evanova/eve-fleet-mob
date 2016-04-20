/* Part of this class uses code from DurationFormatter (see license details below).*/

/*
 *
 * Copyright (C) 2002-2005 by MyVietnam.net
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * AllTests copyright notices regarding MyVietnam and MyVietnam CoreLib
 * MUST remain intact in the scripts and source code.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 * Correspondence and Marketing Questions can be sent to:
 * info@MyVietnam.net
 *
 * @author: Minh Nguyen  minhnn@MyVietnam.net
 * @author: Mai  Nguyen  mai.nh@MyVietnam.net
 */
package org.devfleet.mob.app.util;

import android.graphics.Color;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

public final class EveFormat {
    public enum SkillPoint {
        LONG("###,###,###,###");

        private final NumberFormat skillPointsFormat;

        SkillPoint(String pattern) {
            this.skillPointsFormat = new DecimalFormat(pattern);
        }

        public final String format(long sp) {
            return this.skillPointsFormat.format(sp) + " SP";
        }

        public final String format(long sp1, long sp2) {
            return this.skillPointsFormat.format(sp1) + " / " + this.skillPointsFormat.format(sp2) + " SP";
        }

        public static String LONG(long sp1) {
            return SkillPoint.LONG.format(sp1);
        }

        public static String LONG(long sp1, long sp2) {
            return SkillPoint.LONG.format(sp1, sp2);
        }
    }

    public enum Currency {
        SHORT("###,###,###"), MEDIUM("###,###,###,###,###"), LONG("###,###,###,###,###.00");

        private final NumberFormat iskFormat;

        Currency(String pattern) {
            this.iskFormat = new DecimalFormat(pattern);
        }

        public final String format(double isk, String suffix) {
            String f = this.iskFormat.format(isk);
            if (StringUtils.isNotBlank(suffix)) {
                f = f + " " + suffix;
            }
            return f;
        }

        public final String format(double isk) {
            return format(isk, null);
        }

        public static String SHORT(double isk) {
            return SHORT(isk, true);
        }

        public static String SHORT(double isk, boolean suffix) {
            if (isk > 1000000000d) {
                //1Billion
                return (suffix) ? Currency.SHORT.format((long) isk / 1000000000d, "B.ISK") : Currency.SHORT.format((long) isk / 1000000000d, "B");
            }
            if (isk > 1000000d) {
                //1M
                return (suffix) ? Currency.SHORT.format((long) isk / 1000000l, "M.ISK") : Currency.SHORT.format((long) isk / 1000000l, "M");
            }
            return (suffix) ? Currency.SHORT.format((long) isk, "ISK") : Currency.SHORT.format((long) isk, null);
        }

        public static String MEDIUM(double isk) {
            return MEDIUM(isk, true);
        }

        public static String MEDIUM(double isk, boolean suffix) {
            return (suffix) ? Currency.MEDIUM.format((long) isk, "ISK") : Currency.MEDIUM.format((long) isk, null);
        }

        public static String LONG(double isk) {
            return LONG(isk, true);
        }

        public static String LONG(double isk, boolean suffix) {
            return (suffix) ? Currency.LONG.format(isk, "ISK") : Currency.LONG.format(isk, null);
        }
    }

    public enum Number {
        FLOAT(new DecimalFormat("##.##")), LONG(null);

        private final NumberFormat numberFormat;

        Number(final NumberFormat numberFormat) {
            this.numberFormat = numberFormat;
        }

        public static String FLOAT(double value) {
            return FLOAT.numberFormat.format(value);
        }

        public static String LONG(long value) {
            return Long.toString(value);
        }

    }

    public enum DateTime {
        PLAIN("EE dd MMM yyyy HH:mm"),
        YEAR("EEEE dd MMMM yyyy"),
        YEAR_S("EE dd MMM yyyy"),
        MEDIUM("EEEE dd MMMM HH:mm"),
        SHORT("EEE dd MMM HH:mm"),
        SHORT_Y("EEE dd MMM yyyy HH:mm"),
        LONG("EEEE dd MMMM yyyy HH:mm"),
        LONG_S("EEEE dd MMMM yyyy HH:mm ss");

        private final SimpleDateFormat format;

        DateTime(String pattern) {
            this.format = new SimpleDateFormat(pattern);
        }

        public final String format(long dateTime) {
            return this.format.format(dateTime);
        }

        public static String MEDIUM(long dateTime) {
            return DateTime.MEDIUM.format(dateTime);
        }

        public static String SHORT(long dateTime) {
            return DateTime.SHORT.format(dateTime);
        }

        public static String SHORT(long dateTime, boolean withYear) {
            return (withYear) ? DateTime.SHORT_Y.format(dateTime) : DateTime.SHORT.format(dateTime);
        }

        public static String LONG(long dateTime, boolean withSeconds) {
            return (withSeconds) ? DateTime.LONG_S.format(dateTime) : DateTime.LONG.format(dateTime);
        }

        public static String YEAR(long dateTime) {
            return YEAR(dateTime, false);
        }

        public static String YEAR(long dateTime, boolean shortVersion) {
            return (shortVersion) ? DateTime.YEAR_S.format(dateTime) : DateTime.YEAR.format(dateTime);
        }

        public static String PLAIN(long dateTime) {
            return DateTime.PLAIN.format(dateTime);
        }
    }

    public enum Date {
        DAY("dd MMM"), SHORT("EE. dd MM"), MEDIUM("EE. dd MMM yyyy"), LONG("EEEE dd MMMM yyyy");

        private final SimpleDateFormat format;

        Date(String pattern) {
            this.format = new SimpleDateFormat(pattern);
        }

        public final String format(long dateTime) {
            return this.format.format(dateTime);
        }

        public static String DAY(long dateTime) {
            return Date.DAY.format(dateTime);
        }

        public static String SHORT(long dateTime) {
            return Date.SHORT.format(dateTime);
        }

        public static String MEDIUM(long dateTime) {
            return Date.MEDIUM.format(dateTime);
        }

        public static String LONG(long dateTime) {
            return Date.LONG.format(dateTime);
        }
    }

    public enum Duration {
        SHORT("@hh", "@dd @hh"),
        MEDIUM("@hh @mmn", "@dd @hh @mmn"),
        LONG("@hh @mmn @ss", "@dd @hh @mmn @ss");

        private final DurationFormatter shortFormat;
        private final DurationFormatter longFormat;

        Duration(String shortPattern, String longPattern) {
            this.shortFormat = new DurationFormatter(shortPattern);
            this.longFormat = new DurationFormatter(longPattern);
        }

        public String format(long durationMillis) {
            if (durationMillis < (24 * 3600 * 1000)) {
                return shortFormat.format(durationMillis);
            }
            return longFormat.format(durationMillis);
        }

        public static String MEDIUM(long durationMillis) {
            return Duration.MEDIUM.format(durationMillis);
        }

        public static String LONG(long durationMillis) {
            return Duration.LONG.format(durationMillis);
        }

        public static String SHORT(long durationMillis) {
            return Duration.SHORT.format(durationMillis);
        }
    }

    private static final String DEFAULT_CSS = "body {font-family: Verdana, Geneva, Arial, Helvetica, sans-serif; color: white; background-color: transparent;}\n"
            + "a, a:link, a:visited, a:active, a:hover {color: white; font-weight: bold; text-decoration: none;}\n";

    private static final String HTML = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" + "<html>\n" + "<style type=\"text/css\">%s</style>\n" + "<body>%s</body></html>";

    private static final class DurationFormatter {

        public static final long MILLISECONDS_PER_SECOND = 1000;
        public static final long SECONDS_PER_MINUTE = 60;
        public static final long MINUTES_PER_HOUR = 60;
        public static final long HOURS_PER_DAY = 24;

        public static final String PATTERNS[] = { "@ms", "@s", "@m", "@h", "@d" };

        private static final long[] AMOUNTS = { MILLISECONDS_PER_SECOND, SECONDS_PER_MINUTE, MINUTES_PER_HOUR, HOURS_PER_DAY };

        private final String pattern;

        public DurationFormatter(String pattern) {
            this.pattern = pattern;
        }

        public String format(long time) {
            long[] times = new long[5];
            long remain = time;
            for (int i = 0; i < AMOUNTS.length; i++) {
                times[i] = remain % AMOUNTS[i];
                remain = remain / AMOUNTS[i];
            }
            times[4] = (int) remain;

            StringBuilder buffer = new StringBuilder(1024);
            buffer.append(pattern);
            for (int i = 0; i < PATTERNS.length; i++) {
                int start = -1;
                int end = -1;
                while ((start = buffer.toString().indexOf(PATTERNS[i])) > -1) {
                    end = start + PATTERNS[i].length();
                    buffer.replace(start, end, String.valueOf(times[i]));
                }
            }
            return buffer.toString();
        }
    }

    private EveFormat() {
    }

    public static String formatHTML(String disembodiedHTML) {
        return formatHTML(disembodiedHTML, DEFAULT_CSS);
    }

    public static String formatHTML(String disembodiedHTML, String css) {
        String r = cleanHTML(disembodiedHTML);
        r = (StringUtils.isBlank(css)) ? String.format(HTML, DEFAULT_CSS, r) : String.format(HTML, css, r);
        try {
            return new String(r.getBytes(), "utf-8");
        }
        catch (UnsupportedEncodingException e) {
            //unlikely
            return r;
        }
    }

    private static String cleanHTML(final String html) {
        String r = StringUtils.isBlank(html) ? "" : html.trim();
        if (r.isEmpty()) {
            return r;
        }

        r = StringUtils.remove(html, "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");

        //remove ALL the font tags
        //@see http://blogs.msdn.com/b/ericgu/archive/2006/01/30/519462.aspx
        r = r.replaceAll("<font(\\s|\\r|\\n).*?>", "");
        r = r.replaceAll("</?font[^>]*>", "");

        //r = StringUtils.replace(r, "\n", "<br/>\n");
        r = StringUtils.replace(r, "<br>", "<br/>");
        r = StringUtils.remove(r, "shellexec:");
        //is it the shellexec or me or some people can't type?		
        r = StringUtils.replace(r, "http://http://", "http://");
        r = StringUtils.replace(r, "https://https://", "https://");

        return r;
    }

    public static int getDurationColor(long duration) {
        if (duration < 3 * 3600 * 1000) {
            return Color.RED;
        }
        if (duration < 12 * 3600 * 1000) {
            return Color.YELLOW;
        }
        if (duration < 24 * 3600 * 1000) {
            return Color.GREEN;
        }
        return Color.WHITE;
    }

    public static int getLongDurationColor(long duration) {
        if (duration < 24 * 3600 * 1000) {
            return Color.RED;
        }
        if (duration < 2 * 24 * 3600 * 1000) {
            return Color.YELLOW;
        }
        if (duration < 7 * 24 * 3600 * 1000) {
            return Color.GREEN;
        }
        return Color.WHITE;
    }

    public static int getSecurityLevelColor(final float security) {
        if (security > 0.5) {
            return Color.HSVToColor(255, new float[]{180f * security, 100f, 100f});
        }
        if (security > 0.1) {
            return Color.HSVToColor(255, new float[]{Math.max(180f * security - 30f, 0f), 100f, 100f});
        }
        if (security > 0) {
            return Color.HSVToColor(255, new float[]{Math.max(180f * security - 60f, 0f), 100f, 100f});
        }
        return Color.HSVToColor(255, new float[]{0f, 100f, 100f});
    }

    public static int getSecurityStatusColor(final float security) {
        if (security > 0) {
            return Color.LTGRAY;
        }
        if (security > -2.0) {
            return Color.WHITE;
        }
        return getSecurityLevelColor((security + 10) / 20);
    }
}
