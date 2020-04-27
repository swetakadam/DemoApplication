package com.example.demoapplication.ui.home

import android.animation.ObjectAnimator
import android.icu.text.DateTimePatternGenerator.PatternInfo.OK
import android.os.Bundle
import android.transition.Transition
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.appcompat.app.AppCompatActivity
import android.view.animation.AnimationUtils
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.daasuu.ei.Ease
import com.daasuu.ei.EasingInterpolator
import com.example.demoapplication.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.tensor_flow_regonization_fragment.*
import kotlinx.android.synthetic.main.view_home_details.*
import android.animation.ObjectAnimator.ofFloat as ofFloat1


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root =
            inflater.inflate(com.example.demoapplication.R.layout.fragment_home, container, false)
        homeViewModel.text.observe(this, Observer {
            //textView.text = it
        })


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //doBounceAnimation(header_content_layout)

        /** Shared content animation  */
        tensor_flow_flower_image.setOnClickListener {
            val extras = FragmentNavigatorExtras(
                it to "tensor_flow_header_image"
            )

            //findNavController().navigate(R.id.action_nav_home_to_tensorFlowRegonizationFragment)
            findNavController().navigate(
                R.id.action_nav_home_to_tensorFlowRegonizationFragment,
                null, // Bundle of args
                null, // NavOptions
                extras
            )
        }

    }

    private fun doBounceAnimation(view: View) {
        val animator = ObjectAnimator.ofFloat(view, "translationY", 0f, 45f, 0f)
        animator.apply {
            interpolator = EasingInterpolator(Ease.EASE_IN_OUT_EXPO)
            repeatCount = 2
            startDelay = 1000
            duration = 1200
        }.start()

    }
}