/*
 * This file is part of the UNES Open Source Project.
 * UNES is licensed under the GNU GPLv3.
 *
 * Copyright (c) 2019.  João Paulo Sena <joaopaulo761@gmail.com>
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

package com.forcetower.uefs.feature.flowchart.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.navigation.fragment.navArgs
import com.forcetower.uefs.core.injection.Injectable
import com.forcetower.uefs.core.vm.UViewModelFactory
import com.forcetower.uefs.databinding.FragmentFlowchartBinding
import com.forcetower.uefs.feature.flowchart.FlowchartViewModel
import com.forcetower.uefs.feature.shared.UFragment
import com.forcetower.uefs.feature.shared.extensions.provideViewModel
import javax.inject.Inject

class FlowchartFragment : UFragment(), Injectable {
    @Inject
    lateinit var factory: UViewModelFactory
    private lateinit var binding: FragmentFlowchartBinding
    private lateinit var viewModel: FlowchartViewModel
    private val args by navArgs<FlowchartFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = provideViewModel(factory)
        return FragmentFlowchartBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val semesters = SemestersFragment.newInstance(args.courseId)
        val fragments = listOf(semesters)
        binding.apply {
            tabLayout.setupWithViewPager(pagerFlowchart)
            pagerFlowchart.adapter = FragmentAdapter(childFragmentManager, fragments)
        }
    }

    private class FragmentAdapter(fm: FragmentManager, val fragments: List<UFragment>) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getCount() = fragments.size
        override fun getItem(position: Int) = fragments[position]
        override fun getPageTitle(position: Int) = fragments[position].displayName
    }
}