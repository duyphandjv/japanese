package com.phanduy.tekotest.adapters

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.phanduy.tekotest.R
import com.phanduy.tekotest.utilities.StringUtils
import com.squareup.picasso.Picasso

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Picasso.get()
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.empty_image)
                .error(R.drawable.empty_image)
                .fit()
                .into(view)
    } else {
        Picasso.get()
                .load(R.drawable.empty_image)
                .centerCrop()
                .fit()
                .into(view)
    }
}

@BindingAdapter("imageFromName")
fun bindImageFromAssest(view: ImageView, name: String?) {
    Picasso.get()
        .load("file:///android_asset/${name}")
        .resize(100, 100)
        .centerCrop()
        .placeholder(R.drawable.empty_image)
        .error(R.drawable.empty_image)
        .into(view)
}

@BindingAdapter("textWithPrice")
fun bindPrice(view: TextView, price: Int) {
    view.text = StringUtils.getPriceString(price)
}

fun getDrawable(filename : String, resources: Resources) : Drawable {
    return Drawable.createFromResourceStream(resources,
        TypedValue(), resources.getAssets().open(filename), null)
}