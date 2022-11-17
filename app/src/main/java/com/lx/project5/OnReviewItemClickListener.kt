package com.lx.project5

import android.view.View
import com.lx.project5.PetAdapter
import java.text.FieldPosition

interface OnReviewItemClickListener {

    fun onItemClick(holder: ReviewAdapter.ViewHolder?,view: View?, position: Int)
}