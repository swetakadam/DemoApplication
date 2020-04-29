package com.example.demoapplication.ui.tensorflow

import android.graphics.Color
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.transition.TransitionInflater
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.example.demoapplication.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.tensor_flow_regonization_fragment.*

class TensorFlowRegonizationFragment : Fragment() {


    //private lateinit var viewModel: TensorFlowRegonizationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tensor_flow_regonization_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        /*sharedElementEnterTransition = ChangeBounds().apply {
            duration = 750
        }
        sharedElementReturnTransition= ChangeBounds().apply {
            duration = 750
        }*/
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //set the tensorflow detail text
        tensor_flow_detail_text.text = HtmlCompat.fromHtml(
            getString(R.string.tensor_flow_detail_text),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
        tensor_flow_detail_text.movementMethod = ScrollingMovementMethod()

        exploreExtendedFab.setOnClickListener {
            context?.run {
                MaterialAlertDialogBuilder(this)
                    .setTitle(getString(R.string.coming_soon))
                    .setMessage(getString(R.string.coming_soon_detailed_message))
                    .setPositiveButton(getString(R.string.okay)) { _, _ ->
                    }
                    //.setNegativeButton("Cancel", /* listener = */ null)
                    .show()
            }
        }



    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel = ViewModelProviders.of(this).get(TensorFlowRegonizationViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
