package com.eve0.fleetmob.app.eve;

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

    public static String getItemImageURL(final long itemID) {
        return formatUrl(IMAGE_ITEM, itemID);
    }

    public static String getItemIconURL(final long itemID) {
        return formatUrl(ICON_ITEM, itemID);
    }

    public static String getCharacterImageURL(final long itemID) {
        return formatUrl(IMAGE_CHARACTER, itemID);
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

    public void loadItemIcon(final long typeID, final ImageView intoView) {
        p.load(url(ICON_ITEM, typeID)).into(intoView);
    }

    public void loadItemImage(final long typeID, final ImageView intoView) {
        p.load(url(IMAGE_ITEM, typeID)).into(intoView);
    }

    public void loadCharacterImage(final long ownerId, final ImageView intoView) {
        p.load(url(IMAGE_CHARACTER, ownerId)).into(intoView);
    }

    public void loadCharacterIcon(final long ownerId, final ImageView intoView) {
        p.load(url(ICON_CHARACTER, ownerId)).into(intoView);
    }

    public void loadCorporationIcon(final long ownerId, final ImageView intoView) {
        p.load(url(ICON_CORPORATION, ownerId)).into(intoView);

    }

    public void loadCorporationImage(final long ownerId, final ImageView intoView) {
        p.load(url(IMAGE_CORPORATION, ownerId)).into(intoView);
    }

    public void loadAllianceIcon(final long ownerId, final ImageView intoView) {
        p.load(url(ICON_ALLIANCE, ownerId)).into(intoView);
    }

    public void loadAllianceImage(final long ownerId, final ImageView intoView) {
        p.load(url(IMAGE_ALLIANCE, ownerId)).into(intoView);
    }

    public void load(final String uri, final RemoteViews views, int target, int[] widgetIds) {
        p.load(uri).into(views, target, widgetIds);
    }

    private static String formatUrl(final String urlConstant, final long typeID) {
        return String.format(urlConstant, rootURL, Long.toString(typeID));
    }

    private static final String rootURL = "https://image.eveonline.com";
    private final Picasso p;

    EveImages(final Context context) {
        this.p = Picasso.with(context.getApplicationContext());
    }

    private static String url(final String urlConstant, final long typeID) {
        return String.format(urlConstant, rootURL, Long.toString(typeID));
    }

}
