package com.sixgroup.coffeearoundu.ui.splash

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import com.sixgroup.coffeearoundu.LoginActivity
import com.sixgroup.coffeearoundu.databinding.FragmentSplashBinding
import com.sixgroup.coffeearoundu.utils.APP_IMAGE
import com.sixgroup.coffeearoundu.utils.BACKGROUND_FADE
import com.sixgroup.coffeearoundu.utils.TITLE_FADE

class SplashFragment : Fragment() {
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val w = requireActivity().window
        w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        w.statusBarColor = ContextCompat.getColor(requireActivity(), android.R.color.transparent)
        splash()
    }

    private val mRunnable = Runnable {
        val intent = Intent(requireContext(), LoginActivity::class.java)
        val opt = ActivityOptionsCompat.makeSceneTransitionAnimation(
            requireActivity(),
            Pair.create(binding.ivSplash, APP_IMAGE)
        )
        startActivity(intent, opt.toBundle())
        requireActivity().finish()
    }
    private val mHandler = Handler(Looper.getMainLooper())

    private fun splash() {
        binding.main.apply {
            alpha = 0f
            visibility = View.VISIBLE
            animate().apply {
                alpha(1f)
                duration = BACKGROUND_FADE
            }
        }
        binding.ivSplash.apply {
            alpha = 0f
            visibility = View.VISIBLE
            animate().apply {
                alpha(1f)
                duration = TITLE_FADE
                setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        mHandler.postDelayed(mRunnable, TITLE_FADE)
                    }
                })
            }
        }
    }

    companion object {
        fun newInstance() = SplashFragment()
    }
}