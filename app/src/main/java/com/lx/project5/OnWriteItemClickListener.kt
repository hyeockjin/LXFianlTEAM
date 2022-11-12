package com.lx.project5

import android.view.View
import com.lx.project5.PetAdapter
import java.text.FieldPosition

interface OnWriteItemClickListener {

    fun onItemClick(holder: WriteAdapter.ViewHolder?,view: View?, position: Int)
}