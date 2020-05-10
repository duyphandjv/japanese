package com.phanduy.japanese

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.TypedValue
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import butterknife.BindView
import butterknife.ButterKnife


class MainActivity : AppCompatActivity() {

    @BindView(R.id.image)
    lateinit var image: ImageView

    @BindView(R.id.txtName)
    lateinit var txtName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)

        txtName.text = "a"

        findImage(txtName.text.toString())

    }

    fun findImage(txt: String) {
        image.setImageDrawable(getDrawable(txt + ".png"))

    }

    fun getDrawable(filename : String) : Drawable {
        return Drawable.createFromResourceStream(resources,
            TypedValue(), resources.getAssets().open(filename), null)
    }
}
