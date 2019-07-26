/*
 * This file is part of the UNES Open Source Project.
 * UNES is licensed under the GNU GPLv3.
 *
 * Copyright (c) 2019. João Paulo Sena <joaopaulo761@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.forcetower.utils

import android.util.Log
import androidx.annotation.ColorInt
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {
    var displayName: String = javaClass.simpleName

    fun showSnack(string: String, long: Boolean = false) {
        val activity = activity
        if (activity is BaseActivity) {
            activity.showSnack(string, long)
        } else {
            Log.e("BaseFragment", "Attempt to call show snack on a non BaseActivity attached fragment")
        }
    }

    fun setStatusBarColor(@ColorInt color: Int) {
        val activity = activity
        if (activity is BaseActivity) {
            activity.setStatusBarColor(color)
        } else {
            Log.e("BaseFragment", "Attempt to call show snack on a non BaseActivity attached fragment")
        }
    }
}