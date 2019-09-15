package com.effirossimotoi.memome.alfa;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

public class IdConversion {
    Context mContext;

    IdConversion(Context context){
        mContext = context;
    }

    public Drawable getIconFromId(int id){
        Drawable drawable;
        switch (id) {
            case 1:
                drawable = ContextCompat.getDrawable(mContext, R.drawable.ic_favourite);
                break;
            case 2:
                drawable = ContextCompat.getDrawable(mContext, R.drawable.ic_work);
                break;
            case 3:
                drawable = ContextCompat.getDrawable(mContext, R.drawable.ic_restaurant);
                break;
            case 4:
                drawable = ContextCompat.getDrawable(mContext, R.drawable.ic_school);
                break;
            case 5:
                drawable = ContextCompat.getDrawable(mContext, R.drawable.ic_travel);
                break;
            case 6:
                drawable = ContextCompat.getDrawable(mContext, R.drawable.ic_shopping);
                break;
            case 7:
                drawable = ContextCompat.getDrawable(mContext, R.drawable.ic_videogames);
                break;
            default:
                drawable = ContextCompat.getDrawable(mContext, R.drawable.ic_icon_light);
                break;
        }
        return drawable;
    }
}
