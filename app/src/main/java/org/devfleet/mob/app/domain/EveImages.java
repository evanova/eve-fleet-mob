package org.devfleet.mob.app.domain;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.widget.ImageView;
import android.widget.RemoteViews;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import javax.inject.Inject;


public final class EveImages {

    private static final String IMAGE_CHARACTER = "%s/Character/%s_512.jpg";
    private static final String ICON_CHARACTER = "%s/Character/%s_128.jpg";

    private static final String IMAGE_CORPORATION = "%s/Corporation/%s_256.png";
    private static final String ICON_CORPORATION = "%s/Corporation/%s_128.png";

    private static final String IMAGE_ALLIANCE = "%s/Alliance/%s_256.png";
    private static final String ICON_ALLIANCE = "%s/Alliance/%s_128.png";

    private static final String IMAGE_ITEM = "%s/Render/%s_512.png";
    private static final String ICON_ITEM = "%s/Type/%s_64.png";

    /*
     * Copyright 2014 Julian Shen
     *
     * Licensed under the Apache License, Version 2.0 (the "License");
     * you may not use this file except in compliance with the License.
     * You may obtain a copy of the License at
     *
     *     http://www.apache.org/licenses/LICENSE-2.0
     *
     * Unless required by applicable law or agreed to in writing, software
     * distributed under the License is distributed on an "AS IS" BASIS,
     * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     * See the License for the specific language governing permissions and
     * limitations under the License.
     */
    /**
     * Created by julian on 13/6/21.
     */
    private static class CircleTransform implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());

            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                source.recycle();
            }

            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);

            float r = size/2f;
            canvas.drawCircle(r, r, r, paint);

            squaredBitmap.recycle();
            return bitmap;
        }

        @Override
        public String key() {
            return "circle";
        }
    }
    private static final Transformation circularTransform = new CircleTransform();

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
        p.load(url(ICON_ITEM, typeID)).transform(circularTransform).into(intoView);
    }

    public void loadItemImage(final long typeID, final ImageView intoView) {
        p.load(url(IMAGE_ITEM, typeID)).into(intoView);
    }

    public void loadCharacterImage(final long ownerId, final ImageView intoView) {
        p.load(url(IMAGE_CHARACTER, ownerId)).into(intoView);
    }

    public void loadCharacterIcon(final long ownerId, final ImageView intoView) {
        p.load(url(ICON_CHARACTER, ownerId)).transform(circularTransform).into(intoView);
    }

    public void loadCorporationIcon(final long ownerId, final ImageView intoView) {
        p.load(url(ICON_CORPORATION, ownerId)).transform(circularTransform).into(intoView);
    }

    public void loadCorporationImage(final long ownerId, final ImageView intoView) {
        p.load(url(IMAGE_CORPORATION, ownerId)).into(intoView);
    }

    public void loadAllianceIcon(final long ownerId, final ImageView intoView) {
        p.load(url(ICON_ALLIANCE, ownerId)).transform(circularTransform).into(intoView);
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
    private static Picasso p;

    @Inject
    public EveImages(final Context context) {
        if (null == p) {
            p = Picasso.with(context.getApplicationContext());
        }
    }

    private static String url(final String urlConstant, final long typeID) {
        return String.format(urlConstant, rootURL, Long.toString(typeID));
    }

}
