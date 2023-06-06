package com.example.myapp
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import androidx.cardview.widget.CardView


class ImageAdapter(private val context: Context, private val icons: Array<Int>) : BaseAdapter() {

    override fun getCount(): Int = icons.size

    override fun getItem(position: Int): Any? = null

    override fun getItemId(position: Int): Long = 0L

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val cardView: CardView
        val imageView: ImageView
        if (convertView == null) {
            imageView = ImageView(context)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.setImageResource(icons[position])
            imageView.adjustViewBounds = true

            cardView = CardView(context)
            cardView.addView(imageView)
            cardView.radius = 60f
            cardView.cardElevation = 15f
            cardView.layoutParams = ViewGroup.LayoutParams(250, 250)
        } else {
            cardView = convertView as CardView
            imageView = cardView.getChildAt(0) as ImageView
        }

        imageView.setImageResource(icons[position])
        return cardView
    }
}
