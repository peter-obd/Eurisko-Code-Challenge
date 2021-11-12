package helpers

import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View

class ClickableTv(private val listener: ClickableTvCallBack ) : ClickableSpan() {

    interface ClickableTvCallBack {
        fun onTvClick(widget: View)
        fun onUpdateDrawState(ds: TextPaint)
    }
    override fun onClick(widget: View) {
        listener.onTvClick(widget)
    }

    override fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)
        listener.onUpdateDrawState(ds)
    }
}