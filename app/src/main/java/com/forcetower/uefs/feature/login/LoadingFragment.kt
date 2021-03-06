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

package com.forcetower.uefs.feature.login

import `in`.uncod.android.bypass.Bypass
import android.content.Intent
import android.os.Bundle
import android.text.Layout
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.AlignmentSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.forcetower.uefs.R
import com.forcetower.uefs.core.injection.Injectable
import com.forcetower.uefs.core.model.unes.Access
import com.forcetower.uefs.core.util.HtmlUtils
import com.forcetower.uefs.core.vm.UViewModelFactory
import com.forcetower.uefs.databinding.FragmentLoadingBinding
import com.forcetower.uefs.feature.home.HomeActivity
import com.forcetower.uefs.feature.shared.UFragment
import com.forcetower.uefs.feature.shared.fadeIn
import com.forcetower.uefs.feature.shared.fadeOut
import com.forcetower.uefs.feature.shared.extensions.provideViewModel
import timber.log.Timber
import javax.inject.Inject

class LoadingFragment : UFragment(), Injectable {
    @Inject
    lateinit var factory: UViewModelFactory

    private lateinit var binding: FragmentLoadingBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var markdown: Bypass

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentLoadingBinding.inflate(inflater, container, false).also {
            binding = it
            binding.btnFirstSteps.setOnClickListener {
                findNavController().navigate(R.id.action_login_loading_to_login_form)
            }
            markdown = Bypass(requireContext(), Bypass.Options())
            setupTermsText()
        }.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = provideViewModel(factory)
        viewModel.getAccess().observe(this, Observer { onReceiveToken(it) })
    }

    private fun onReceiveToken(access: Access?) {
        if (access == null) {
            binding.btnFirstSteps.fadeIn()
            binding.contentLoading.fadeOut()
        } else {
            Timber.d("Connected already")
            if (viewModel.isConnected()) return

            viewModel.setConnected()
            val intent = Intent(context, HomeActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    private fun setupTermsText() {
        val sequence1 = SpannableString(resources.getString(R.string.label_terms_and_conditions_p1))
        sequence1.setSpan(AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, sequence1.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        val sequence2 = SpannableString(markdown.markdownToSpannable(resources.getString(R.string.label_terms_and_conditions_p2), binding.textTermsAndPrivacy, null))
        sequence2.setSpan(AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, sequence2.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        val sequence = TextUtils.concat(sequence1, "\n", sequence2)
        HtmlUtils.setTextWithNiceLinks(binding.textTermsAndPrivacy, sequence)
    }
}