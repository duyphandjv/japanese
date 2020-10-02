package com.phanduy.japanese

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatSpinner
import butterknife.BindView
import butterknife.ButterKnife
import kotlin.random.Random


class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener  {

    @BindView(R.id.image)
    lateinit var image: ImageView

    @BindView(R.id.layoutCover)
    lateinit var layoutCover: LinearLayout

    @BindView(R.id.spinner)
    lateinit var spinner: AppCompatSpinner

    @BindView(R.id.btnNext)
    lateinit var btnNext: Button

    @BindView(R.id.btnShowResult)
    lateinit var btnShowResult: Button

    @BindView(R.id.btnRandom)
    lateinit var btnRandom: Button

    var hashMap : HashMap<String, MutableList<String>> = HashMap()

    lateinit var datas : MutableList<String>
    var currentIndex : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)

        btnShowResult.setOnClickListener(
            {
                layoutCover.visibility = (if(layoutCover.visibility == View.VISIBLE) View.INVISIBLE else View.VISIBLE)
            }
        )

        btnNext.setOnClickListener(
            {
                nextWord()
            }
        )

        btnRandom.setOnClickListener(
            {
                randomWord()
            }
        )

        findImage("a")

        datas = mutableListOf("a", "ka", "sa", "ta", "na", "ha", "ma", "ya", "ra", "wa", "n","All")

        init()

        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, datas)
        // Set layout to use when the list of choices appear
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinner!!.setAdapter(aa)

        spinner!!.onItemSelectedListener = this
        randomWord()

    }

    fun init() {

        hashMap.put("a", mutableListOf("a", "i", "u", "e", "o"))
        hashMap.put("ka", mutableListOf("ka", "ki", "ku", "ke", "ko"))
        hashMap.put("sa", mutableListOf("sa", "shi", "su", "se", "so"))
        hashMap.put("ta", mutableListOf("ta", "chi", "tsu", "te", "to"))
        hashMap.put("na", mutableListOf("na", "ni", "nu", "ne", "no"))
        hashMap.put("ha", mutableListOf("ha", "hi", "fu", "he", "ho"))
        hashMap.put("ma", mutableListOf("ma", "mi", "mu", "me", "mo"))
        hashMap.put("ya", mutableListOf("ya", "yu", "yo"))
        hashMap.put("ra", mutableListOf("ra", "ri", "ru", "re", "ro"))
        hashMap.put("wa", mutableListOf("wa","o(wo)"))
        hashMap.put("n", mutableListOf("n"))
        hashMap.put("All", mutableListOf("a", "i", "u", "e", "o","ka", "ki", "ku", "ke", "ko","sa", "shi", "su", "se", "so","ta", "chi", "tsu", "te", "to"
            ,"na", "ni", "nu", "ne", "no","ha", "hi", "fu", "he", "ho","ma", "mi", "mu", "me", "mo","ya", "yu", "yo","ra", "ri", "ru", "re", "ro","wa","wo","n"))
    }

    fun nextWord()  {

        var current = spinner.selectedItem as String
        val listDatas = hashMap.get(current)
        nextIndex(listDatas!!)

        findImage(getCurrentWord())
    }

    fun randomWord()  {

        var current = spinner.selectedItem as String
        val listDatas = hashMap.get(current)
        currentIndex = randomIndex(listDatas!!)
        var word = listDatas.get(currentIndex)

        findImage(word)
    }

    fun getCurrentWord() : String {
        var current = spinner.selectedItem as String
        val listDatas = hashMap.get(current)
        return listDatas!!.get(currentIndex)
    }

    fun randomIndex(listData : MutableList<String>) : Int {
        var size = listData.size
        if(size <= 1) return 0

        var index = Random.nextInt(size)
        while (index == currentIndex) {
            index = Random.nextInt(size)
        }

        return index
    }



    fun nextIndex(listData : MutableList<String>) {
        var size = listData.size
        if(size <= 1) return

        if(currentIndex < size - 1) {
            currentIndex ++
        } else {
            currentIndex = 0
        }
    }

    fun findImage(txt: String) {
        layoutCover.visibility = View.VISIBLE
        image.setImageDrawable(getDrawable(txt + ".png"))
    }

    fun getDrawable(filename : String) : Drawable {
        return Drawable.createFromResourceStream(resources,
            TypedValue(), resources.getAssets().open(filename), null)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        currentIndex = 0
        var word = getCurrentWord()
        findImage(word)
    }
}
