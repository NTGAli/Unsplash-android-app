package com.example.pic.view.custom

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.widget.*
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import com.example.pic.R


class CustomButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0
) :
    FrameLayout(context, attrs) {

    private var myAttrs: AttributeSet? = null
    enum class Types(var value: Int) {
        Primary(0),
        Secondary(1),
        Success(2),
        Warning(3),
        Danger(4),
        Info(5);
    }

    enum class Size(var value: Int) {
        XL(0),
        LG(1),
        MD(2),
        SM(3),
        XS(4);
    }

    enum class Style(var value: Int) {
        Contained(0),
        Outline(1),
        TextOnly(2)
    }


    // attributes
    var txtButton: String? = null
        set(value) {
            field = value
            textView.text = value
        }

    var type: Types = Types.Primary
        set(value) {
            field = value
            setButtonType(value.value)
            setColorsStates()
        }

    var size: Size = Size.XL
        set(value) {
            field = value
            setViewSize(value.value)
//            setParamsViews()
            setSize()
            setPaddingViews(size.value)
        }

    var btnStyle: Style? = Style.Contained
        set(value) {
            field = value
            setButtonType(type.value)
            setColorsStates()
        }

    var icon: Int = 0
        set(value) {
            field = value
            if (icon != 0) {
                iconOnly(value)
            }
        }

    var iconRight: Int = 0
        set(value) {
            field = value
            if (iconRight != 0)
                imageIcon.setImageResource(iconRight)
        }

    var iconLeft: Int = 0
        set(value) {
            field = value
            if (iconLeft != 0) {
                imageIcon.setImageResource(iconLeft)
                textView.text = txtButton
            }
        }

    var disable: Boolean = false
        set(value) {
            field = value
            setColorsStates()
        }
    var enableProgress: Boolean = false
        set(value) {
            field = value
            if (value) {
                setEnableProgress()
            } else {
                setDisableProgress()
            }
        }


    //variables
    private var btnRadius: Int = 8
    private var textviewSize: Float = 16f
    private var iconWidth: Float = 8f
    private var iconHeight: Float = 8f
    private var progressWidth: Float = 24f
    private var progressHeight: Float = 24f
    private var isProgress: Boolean = false
    private var currentIcon: Int = 0

    private var textStroke: Float? = 1f
    private var backgroundColor: Int? = null
    private var backgroundColorTouched: Int? = null
    private var borderColorTouched: Int? = null
    private var borderColor: Int? = null
    private var disableColor: Int? = null
    private var textDisabled: Int? = null
    private var borderDisabled: Int? = null
    private var textColor: Int? = null
    private var textColorTouched: Int? = null
    private var viewContext: Context = context

    //views
    private lateinit var linearLayout: LinearLayout
    private lateinit var textView: TextView
    private lateinit var imageIcon: ImageView
    private lateinit var progressBar: ProgressBar


    init {
        myAttrs =attrs
        isClickable = true
        try {

            setupButton()


        } catch (e: Exception) {

        }

    }

    private fun setupButton() {

        setDefaultData()
        initViews()
        getAttributes(myAttrs!!)
        setViewSize(size.value)
        setViews()
        applyAttrToViews()
        setButtonType(type.value)
        setColorsStates()
        setSize()
        setPaddingViews(size.value)

    }

    private fun setDefaultData() {
        setBtnStyle(viewContext.resources.getIntArray(R.array.primary_text_only))
    }

    private fun initViews() {
        linearLayout = LinearLayout(context)
        textView = TextView(context)
        imageIcon = ImageView(context)
        progressBar = ProgressBar(context)
    }


    private fun setColorsStates() {
        setBackgroundColorState()
        setTextColorState()
        setIconColorState()
        setProgressColorState()
    }

    private fun setBackgroundColorState() {
        val states = StateListDrawable()
        val shape = GradientDrawable()
        val radius = floatArrayOf(
            btnRadius.dp.toFloat(),
            btnRadius.dp.toFloat(),
            btnRadius.dp.toFloat(),
            btnRadius.dp.toFloat(),
            btnRadius.dp.toFloat(),
            btnRadius.dp.toFloat(),
            btnRadius.dp.toFloat(),
            btnRadius.dp.toFloat()
        )

        shape.shape = GradientDrawable.RECTANGLE
        shape.cornerRadii = radius

        val selectedShape = GradientDrawable()
        selectedShape.shape = GradientDrawable.RECTANGLE
        selectedShape.cornerRadii = radius

        selectedShape.setColor(backgroundColorTouched!!)
        selectedShape.setStroke(textStroke!!.toInt(), borderColorTouched!!)

        if (!disable) {
            shape.setColor(backgroundColor!!)
            shape.setStroke(textStroke!!.toInt(), borderColor!!)

        } else {
            shape.setColor(disableColor!!)
            shape.setStroke(textStroke!!.toInt(), borderDisabled!!)
            imageIcon.setColorFilter(textDisabled!!, android.graphics.PorterDuff.Mode.SRC_IN)
        }


        states.addState(intArrayOf(-android.R.attr.state_enabled), shape)
        if (!disable) {
            states.addState(intArrayOf(android.R.attr.state_selected), selectedShape)
            states.addState(intArrayOf(android.R.attr.state_pressed), selectedShape)

            states.setExitFadeDuration(150)
        }

        background = states
    }

    private fun setIconColorState() {


        if (currentIcon != 0) {

            val states = StateListDrawable()
            val drawable: Drawable =
                ResourcesCompat.getDrawable(resources, currentIcon, null)!!

            val enableDrawable = drawable.constantState?.newDrawable()?.mutate()?.apply {
                colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                    textColor!!,
                    BlendModeCompat.SRC_ATOP
                )
            }
            val pressedDrawable = drawable.constantState?.newDrawable()?.mutate()?.apply {
                colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                    textColorTouched!!,
                    BlendModeCompat.SRC_ATOP
                )
            }

            states.addState(intArrayOf(-android.R.attr.state_enabled), enableDrawable)
            states.addState(intArrayOf(android.R.attr.state_selected), pressedDrawable)
            states.addState(intArrayOf(android.R.attr.state_pressed), pressedDrawable)
            imageIcon.setImageDrawable(states)

        }

    }

    private fun setTextColorState() {
        if (!disable) {
            val myColorStateList = ColorStateList(
                arrayOf(
                    intArrayOf(-android.R.attr.state_pressed),
                    intArrayOf(android.R.attr.state_focused),
                    intArrayOf(android.R.attr.state_pressed)
                ), intArrayOf(
                    textColor!!,
                    Color.GREEN,
                    textColorTouched!!
                )
            )

            textView.setTextColor(myColorStateList)
        } else {
            textView.setTextColor(textDisabled!!)
        }
    }

    private fun setProgressColorState() {
        if (isProgress) {

            val myColorStateList = ColorStateList(
                arrayOf(
                    intArrayOf(-android.R.attr.state_pressed),
                    intArrayOf(android.R.attr.state_focused),
                    intArrayOf(android.R.attr.state_pressed)
                ), intArrayOf(
                    textColor!!,
                    Color.GREEN,
                    textColorTouched!!
                )
            )

            progressBar.indeterminateTintList = myColorStateList
        }
    }

    private fun setPaddingViews(size: Int) {
        when (size) {

            //XL
            0 -> {
                if (iconRight != 0) {
                    setBtnPaddingWithIcon(0.dp, 16.dp, 8.dp, 16.dp, 24.dp, 0.dp, 16.dp, 0.dp)
                } else if (iconLeft != 0) {
                    setBtnPaddingWithIcon(8.dp, 16.dp, 0.dp, 16.dp, 16.dp, 0.dp, 24.dp, 0.dp)
                } else if (icon != 0) {
                    setPadding(16.dp, 16.dp, 16.dp, 16.dp)
                } else if (isProgress) {
                    setPadding(16.dp, 16.dp, 16.dp, 16.dp)
                } else {
                    textView.setPadding(24.dp, 16.dp, 24.dp, 16.dp)
                }
            }
            //LG
            1 -> {
                if (iconRight != 0) {
                    setBtnPaddingWithIcon(0.dp, 14.dp, 8.dp, 14.dp, 20.dp, 0.dp, 12.dp, 0.dp)
                } else if (iconLeft != 0) {
                    setBtnPaddingWithIcon(8.dp, 14.dp, 0.dp, 14.dp, 12.dp, 0.dp, 20.dp, 0.dp)
                } else if (isProgress || icon != 0) {
                    setPadding(12.dp, 12.dp, 12.dp, 12.dp)
                } else {
                    textView.setPadding(20.dp, 14.dp, 20.dp, 14.dp)
                }

            }
            //MD
            2 -> {
                if (iconRight != 0) {
                    setBtnPaddingWithIcon(0.dp, 10.dp, 6.dp, 10.dp, 16.dp, 0.dp, 12.dp, 0.dp)
                } else if (iconLeft != 0) {
                    setBtnPaddingWithIcon(6.dp, 10.dp, 0.dp, 10.dp, 12.dp, 0.dp, 16.dp, 0.dp)
                } else if (isProgress || icon != 0) {
                    setPadding(12.dp, 12.dp, 12.dp, 12.dp)
                } else {
                    textView.setPadding(16.dp, 10.dp, 16.dp, 10.dp)
                }
            }
            //SM
            3 -> {
                if (iconRight != 0) {
                    setBtnPaddingWithIcon(0.dp, 7.dp, 4.dp, 7.dp, 12.dp, 0.dp, 8.dp, 0.dp)
                } else if (iconLeft != 0) {
                    setBtnPaddingWithIcon(4.dp, 7.dp, 0.dp, 7.dp, 8.dp, 0.dp, 12.dp, 0.dp)
                } else if (isProgress || icon != 0) {
                    setPadding(8.dp, 8.dp, 8.dp, 8.dp)
                } else {
                    textView.setPadding(12.dp, 7.dp, 12.dp, 7.dp)
                }
            }
            //XL
            4 -> {
                if (iconRight != 0) {
                    setBtnPaddingWithIcon(0.dp, 3.dp, 2.dp, 3.dp, 8.dp, 0.dp, 4.dp, 0.dp)
                } else if (iconLeft != 0) {
                    setBtnPaddingWithIcon(2.dp, 3.dp, 0.dp, 3.dp, 8.dp, 0.dp, 4.dp, 0.dp)
                } else if (isProgress) {
                    setPadding(4.dp, 4.dp, 4.dp, 4.dp)
                } else {
                    textView.setPadding(8.dp, 3.dp, 8.dp, 3.dp)
                }
            }
        }
    }

    private fun setBtnPaddingWithIcon(
        txtLeft: Int,
        txtTop: Int,
        txtRight: Int,
        txtBottom: Int,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int,
    ) {
        textView.setPadding(txtLeft, txtTop, txtRight, txtBottom)
        setPadding(left, top, right, bottom)
    }

    private fun applyAttrToViews() {
        textView.text = txtButton
    }

    private fun setParamsViews() {
        //params
        // -- parent layout --
        layoutParams = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        ).apply {
            gravity = Gravity.CENTER
        }

        // -- linearLayout --
        linearLayout.layoutParams = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        ).apply {
            gravity = Gravity.CENTER
        }
//        linearLayout.orientation = LinearLayout.HORIZONTAL


        // -- textView --
        textView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )


        // -- imageView: Icon --
        imageIcon.layoutParams = LinearLayout.LayoutParams(
            iconWidth.toInt().dp,
            iconHeight.toInt().dp
        ).apply {
            gravity = Gravity.CENTER
        }




        //---- progressBar -----
        val progressParams = LayoutParams(
            progressWidth.toInt().dp,
            progressHeight.toInt().dp
        ).apply {
            gravity = Gravity.CENTER
        }

        progressBar.layoutParams = progressParams
    }

    private fun setViews() {

        setParamsViews()

        //addViews
        addView(linearLayout)


        when {
            iconRight != 0 -> {
                linearLayout.addView(textView)
                imageIcon.setImageResource(iconRight)
//                setIconColorState(iconRight)
                linearLayout.addView(imageIcon)
                currentIcon = iconRight
            }
            iconLeft != 0 -> {
                imageIcon.setImageResource(iconLeft)
                linearLayout.addView(imageIcon)
                linearLayout.addView(textView)
                currentIcon = iconLeft
            }
            icon != 0 -> {
                imageIcon.setImageResource(icon)
                linearLayout.addView(imageIcon)
                currentIcon = icon
            }
            isProgress -> {
                linearLayout.addView(textView)
                linearLayout.visibility = GONE
                addView(progressBar)
            }
            else -> {
                linearLayout.addView(textView)

            }
        }

    }

    private fun getAttributes(attrs: AttributeSet) {
        viewContext.withStyledAttributes(attrs, R.styleable.CustomButton) {

            txtButton = getString(R.styleable.CustomButton_buttonText)
            type.value = getInt(R.styleable.CustomButton_type, 0)
            val tt = getInt(R.styleable.CustomButton_type, 0)
            size.value = getInt(R.styleable.CustomButton_size, 0)
            btnStyle?.value = getInt(R.styleable.CustomButton_buttonStyleType, 0)
            icon = getResourceId(R.styleable.CustomButton_icon, 0)
            iconRight = getResourceId(R.styleable.CustomButton_iconRight, 0)
            iconLeft = getResourceId(R.styleable.CustomButton_iconLeft, 0)
            disable = getBoolean(R.styleable.CustomButton_disabled, false)
            isProgress = getBoolean(R.styleable.CustomButton_enableProgress, false)


        }

    }

    private fun iconOnly(icon: Int) {
        textView.visibility = GONE
        progressBar.visibility = GONE
        if (imageIcon.parent == null) {
            linearLayout.addView(imageIcon)
        }
        imageIcon.setImageResource(icon)
        currentIcon = icon
    }

    private fun setEnableProgress() {
        linearLayout.visibility = INVISIBLE
        if (progressBar.parent == null)
            addView(progressBar)
        progressBar.indeterminateTintList = ColorStateList.valueOf(
            textColor!!
        )
        progressBar.visibility = VISIBLE
    }

    private fun setDisableProgress() {
        linearLayout.visibility = VISIBLE
//        if (progressBar.parent != null)
//            removeView(progressBar)
        progressBar.visibility = INVISIBLE
    }

    private fun setSize() {
        textView.textSize = textviewSize
        imageIcon.layoutParams.width = iconWidth.toInt().dp
        imageIcon.layoutParams.height = iconHeight.toInt().dp
    }

    private fun setViewSize(size: Int) {
        when (size) {

            //XL
            0 -> {
                textviewSize = 16f
                btnRadius = 8
                iconWidth = 24f
                iconHeight = 24f
                progressWidth = 24f
                progressHeight = 24f
            }
            //LG
            1 -> {
                textviewSize = 14f
                btnRadius = 8
                iconWidth = 24f
                iconHeight = 24f
                progressWidth = 24f
                progressHeight = 24f

            }
            //MD
            2 -> {
                textviewSize = 14f
                btnRadius = 8
                iconWidth = 16f
                iconHeight = 16f
                progressWidth = 16f
                progressHeight = 16f
            }
            //SM
            3 -> {
                textviewSize = 12f
                btnRadius = 8
                iconWidth = 16f
                iconHeight = 16f
                progressWidth = 16f
                progressHeight = 16f
            }
            //XS
            4 -> {
                textviewSize = 12f
                btnRadius = 4
                iconWidth = 16f
                iconHeight = 16f
                progressWidth = 16f
                progressHeight = 16f
            }
        }
    }

    private fun setButtonType(type: Int = 1) {
//        if (btnStyle == null) btnStyle = Style.Contained
        when (type) {
            //primary
            0 -> {
                when (btnStyle!!.value) {
                    //contained
                    0 -> {
                        setBtnStyle(viewContext.resources.getIntArray(R.array.primary_contained))
                    }
                    //outline
                    1 -> {
                        setBtnStyle(viewContext.resources.getIntArray(R.array.primary_outline))
                    }
                    //textOnly
                    2 -> {
                        setBtnStyle(viewContext.resources.getIntArray(R.array.primary_text_only))
                    }
                }
            }
            //secondary
            1 -> {
                when (btnStyle?.value) {
                    //contained
                    0 -> {
                        setBtnStyle(viewContext.resources.getIntArray(R.array.secondary_contained))
                    }
                    //outline
                    1 -> {
                        setBtnStyle(viewContext.resources.getIntArray(R.array.secondary_outline))
                    }
                    //textOnly
                    2 -> {
                        setBtnStyle(viewContext.resources.getIntArray(R.array.secondary_text_only))
                    }
                }
            }
            //success
            2 -> {
                when (btnStyle?.value) {
                    //contained
                    0 -> {
                        setBtnStyle(viewContext.resources.getIntArray(R.array.success_contained))
                    }
                    //outline
                    1 -> {
                        setBtnStyle(viewContext.resources.getIntArray(R.array.success_outline))
                    }
                    //textOnly
                    2 -> {
                        setBtnStyle(viewContext.resources.getIntArray(R.array.success_text_only))
                    }
                }

            }
            //warning
            3 -> {
                when (btnStyle?.value) {
                    //contained
                    0 -> {
                        setBtnStyle(viewContext.resources.getIntArray(R.array.warning_contained))

                    }
                    //outline
                    1 -> {
                        setBtnStyle(viewContext.resources.getIntArray(R.array.warning_outline))
                    }
                    //textOnly
                    2 -> {
                        setBtnStyle(viewContext.resources.getIntArray(R.array.warning_text_only))
                    }
                }
            }
            //danger
            4 -> {
                when (btnStyle?.value) {
                    //contained
                    0 -> {
                        setBtnStyle(viewContext.resources.getIntArray(R.array.danger_contained))

                    }
                    //outline
                    1 -> {
                        setBtnStyle(viewContext.resources.getIntArray(R.array.danger_outline))
                    }
                    //textOnly
                    2 -> {
                        setBtnStyle(viewContext.resources.getIntArray(R.array.danger_text_only))
                    }
                }
            }
            //info
            5 -> {
                when (btnStyle?.value) {
                    //contained
                    0 -> {
                        setBtnStyle(viewContext.resources.getIntArray(R.array.info_contained))
                    }
                    //outline
                    1 -> {
                        setBtnStyle(viewContext.resources.getIntArray(R.array.info_outline))

                    }
                    //textOnly
                    2 -> {
                        setBtnStyle(viewContext.resources.getIntArray(R.array.info_text_only))
                    }
                }
            }

            else -> {
                setBtnStyle(viewContext.resources.getIntArray(R.array.primary_contained))

            }
        }
    }

    private fun setBtnStyle(intArray: IntArray) {
        textColor = intArray[0]
        textColorTouched = intArray[1]
        backgroundColor = intArray[2]
        borderColor = intArray[3]
        backgroundColorTouched = intArray[4]
        disableColor = intArray[5]
        textDisabled = intArray[6]
        borderDisabled = intArray[7]
        borderColorTouched = intArray[8]
    }


}

