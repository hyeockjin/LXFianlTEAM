package com.lx.project5

import android.view.View
import com.lx.project5.PetAdapter
import java.text.FieldPosition

interface OnPetItemClickListener {

    fun onItemClick(holder: PetAdapter.ViewHolder?,view: View?, position: Int)
}