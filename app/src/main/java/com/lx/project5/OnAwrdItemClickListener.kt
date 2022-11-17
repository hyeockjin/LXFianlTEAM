package com.lx.project5

import android.view.View

interface OnAwrdItemClickListener {

    fun onItemClick(holder: AwrdCommentAdapter.ViewHolder?, view: View?, position: Int)

}