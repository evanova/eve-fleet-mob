package com.eve0.fleetmob.app.util;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RemoteViews;

import com.squareup.picasso.Picasso;


public final class EveImages {

    private static final String IMAGE_CHARACTER = "%s/Character/%s_512.jpg";
    private static final String ICON_CHARACTER = "%s/Character/%s_128.jpg";

    private static final String IMAGE_CORPORATION = "%s/Corporation/%s_256.png";
    private static final String ICON_CORPORATION = "%s/Corporation/%s_128.png";

    private static final String IMAGE_ALLIANCE = "%s/Alliance/%s_256.png";
    private static final String ICON_ALLIANCE = "%s/Alliance/%s_128.png";

    private static final String IMAGE_ITEM = "%s/Render/%s_512.png";
    private static final String ICON_ITEM = "%s/Type/%s_64.png";

    public static void loadItemIcon(final long typeID, final ImageView intoView) {
        p(intoView.getContext()).load(url(ICON_ITEM, typeID)).into(intoView);
    }

    public static void loadItemImage(final long typeID, final ImageView intoView) {
        p(intoView.getContext()).load(url(IMAGE_ITEM, typeID)).into(intoView);
    }

    public static void loadCharacterImage(final long ownerId, final ImageView intoView) {
        p(intoView.getContext()).load(url(IMAGE_CHARACTER, ownerId)).into(intoView);
    }

    public static void loadCharacterIcon(final long ownerId, final ImageView intoView) {
        p(intoView.getContext()).load(url(ICON_CHARACTER, ownerId)).into(intoView);
    }

    public static void loadCorporationIcon(final long ownerId, final ImageView intoView) {
        p(intoView.getContext()).load(url(ICON_CORPORATION, ownerId)).into(intoView);

    }

    public static void loadCorporationImage(final long ownerId, final ImageView intoView) {
        p(intoView.getContext()).load(url(IMAGE_CORPORATION, ownerId)).into(intoView);
    }

    public static void loadAllianceIcon(final long ownerId, final ImageView intoView) {
        p(intoView.getContext()).load(url(ICON_ALLIANCE, ownerId)).into(intoView);
    }

    public static void loadAllianceImage(final long ownerId, final ImageView intoView) {
        p(intoView.getContext()).load(url(IMAGE_ALLIANCE, ownerId)).into(intoView);
    }

    public static String getItemImageURL(final long charID) {
        return formatUrl(IMAGE_ITEM, charID);
    }

    public static String getItemIconURL(final long charID) {
        return formatUrl(ICON_ITEM, charID);
    }

    public static String getCharacterImageURL(final long charID) {
        return formatUrl(IMAGE_CHARACTER, charID);
    }

    public static String getCharacterIconURL(final long charID) {
        return formatUrl(ICON_CHARACTER, charID);
    }

    public static String getCorporationImageURL(final long corpID) {
        return formatUrl(IMAGE_CORPORATION, corpID);
    }

    public static String getCorporationIconURL(final long corpID) {
        return formatUrl(ICON_CORPORATION, corpID);
    }

    public static String getAllianceIconURL(final long allianceID) {
        return formatUrl(ICON_ALLIANCE, allianceID);
    }

    private static String formatUrl(final String urlConstant, final long typeID) {
        return String.format(urlConstant, rootURL, Long.toString(typeID));
    }

    public static void load(final String uri, final Context context, final RemoteViews views, int target, int[] widgetIds) {
        p(context).load(uri).into(views, target, widgetIds);
    }

    private static Picasso p;

    private static final String rootURL = "https://image.eveonline.com";

    private static Picasso p(final Context context) {
        if (null == p) {
            p = Picasso.with(context.getApplicationContext());
        }
        return p;
    }

    private static String url(final String urlConstant, final long typeID) {
        return String.format(urlConstant, rootURL, Long.toString(typeID));
    }

}
