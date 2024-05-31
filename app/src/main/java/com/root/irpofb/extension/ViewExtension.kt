package com.root.irpofb.extension

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.root.irpofb.R
import com.root.irpofb.Utils.CircleDrawable
import com.root.irpofb.ui.activity.BaseActivity
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Locale


fun showToast(context: Context ,message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}


//Set view visibility as visible
fun View.visible() {
    this.visibility = View.VISIBLE
}

//Set view visibility as gone
fun View.gone() {
    this.visibility = View.GONE
}

fun View.setVisibility(visible: Boolean) {
    this.visibility = if (visible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun formatDateToDay(dateString: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("EEEE", Locale.getDefault())

    val date = inputFormat.parse(dateString)
    return outputFormat.format(date)
}

fun calculateNumberOfDays(startDate : String, endDate: String) : Int{
    if (endDate==null||endDate=="null"){
        return 1
    }
    val date1 = LocalDate.parse(startDate)
    val date2 = LocalDate.parse(endDate)

    val daysBetween = ChronoUnit.DAYS.between(date1, date2)
    return daysBetween.toInt()+1
}

fun convertInAmPm(dateString: String) : String{
    val inputFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    val outputFormat = SimpleDateFormat("hh:mm:ss a", Locale.getDefault())

    val date = inputFormat.parse(dateString)
    val formattedTime = outputFormat.format(date)
    return  formattedTime
}

fun convertDateMonthDayFormat(dateString: String) : String{
    val date = LocalDate.parse(dateString)

    val formattedDate = date.format(DateTimeFormatter.ofPattern("dd/MM"))
    return formattedDate
}

fun getCurrentWeekDates(): Pair<String, String> {
    val calendar = Calendar.getInstance()

    // Set the calendar to the current date
    calendar.time = Calendar.getInstance().time

    // Find the first day of the week (Sunday)
    calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
    val startDate = calendar.time

    // Find the last day of the week (Saturday)
    calendar.add(Calendar.DAY_OF_WEEK, 6)
    val endDate = calendar.time

    // Format the dates
    val sdf = java.text.SimpleDateFormat("yyyy-MM-dd")
    val formattedStartDate = sdf.format(startDate)
    val formattedEndDate = sdf.format(endDate)

    return Pair(formattedStartDate, formattedEndDate)
}



//Show keyboard in a fragment
fun Fragment.showKeyboard() {
    (requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(
        InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY
    )
}

fun changeButtonState(view: View, state: Boolean,context : Context) {
    if (state) {
        view.background = ContextCompat.getDrawable(context, R.drawable.bg_button)
    } else {
        view.background = ContextCompat.getDrawable(context, R.drawable.bg_not_selected)
    }
}

//To provide with Fragment Manager via Activity
fun Fragment.supportFragmentManager(): FragmentManager? {
    return (this.activity as BaseActivity)?.supportFragmentManager
}

/*Extension to load Image into ImageView directly via Glide.
If isUserImage is true, load Avatar as Placeholder and Error, else loads generic Image*/
fun ImageView.loadURL(imageURL: String, isUserImage: Boolean, cornerRadius: Int = 0) {
    val minURL = imageURL
    val bestURL = imageURL

    // Create Progress Drawable to use as placeholder.
    val progressDrawable = CircularProgressDrawable(context).apply {
        setColorSchemeColors(R.color.black_text_color)
        centerRadius = 18f
        strokeWidth = 1f
        start()
    }

    if (isUserImage) {
        Glide.with(this).load(bestURL).thumbnail(Glide.with(this).load(minURL))
            .placeholder(R.drawable.irpofb_logo).error(R.drawable.irpofb_logo).into(this)
    } else {
      Glide.with(this).load(bestURL).thumbnail(Glide.with(this).load(minURL))
            .placeholder(progressDrawable).error(R.drawable.irpofb_logo).into(this)
    }
}

fun ImageView.loadUserURL(
    context: Context,
    imageURL: String,
    isUserImage: Boolean,
    cornerRadius: Int = 0,
    fullName: String,
) {
//        //Get complete Image URL using Identifier received.
    val minURL = imageURL
    val bestURL = imageURL

    // Create Progress Drawable to use as placeholder.
    val progressDrawable = CircularProgressDrawable(context).apply {
        setColorSchemeColors(R.color.black_text_color)
        centerRadius = 18f
        strokeWidth = 1f
        start()
    }

    if (isUserImage) {
        Glide.with(this).load(bestURL).thumbnail(Glide.with(this).load(minURL))
            .placeholder(CircleDrawable(context, fullName, 16.0f))
            .error(CircleDrawable(context, fullName, 16.0f)).into(this)
    } else {
        if (cornerRadius > 0) Glide.with(this).load(bestURL)
            .thumbnail(Glide.with(this).load(minURL)).placeholder(progressDrawable)
            .transform(CenterCrop(), RoundedCorners(cornerRadius)).error(
                Glide.with(this).load(R.drawable.irpofb_logo)
                    .transform(CenterCrop(), RoundedCorners(cornerRadius))
            ).error(R.drawable.irpofb_logo).into(this)
        else Glide.with(this).load(bestURL).thumbnail(Glide.with(this).load(minURL))
            .placeholder(progressDrawable).error(R.drawable.irpofb_logo).into(this)
    }
}



fun ImageView.setVideoThumbnail(url: String?) {
    Glide.with(this).asBitmap().load(url).apply(
        RequestOptions().frame((4000 * 3000).toLong())
    ).into(this)
}

