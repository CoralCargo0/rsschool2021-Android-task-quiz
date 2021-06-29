package com.rsschool.quiz

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.QuizResultBinding

class QuizResult: Fragment(R.layout.quiz_result) {
    private var _binding: QuizResultBinding? = null
    private val binding get() = _binding!!
    private lateinit var dataPasser: CheckedResult

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataPasser = context as CheckedResult
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = QuizResultBinding.inflate(inflater, container, false)
        val view = binding.root
        val result = arguments?.getInt(RESULT_KEY)

        binding.textView.text = "Your result: ${result.toString()}%"
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.restart.setOnClickListener {
            dataPasser.restartQuiz()
        }
        binding.exit.setOnClickListener {
            dataPasser.finishQuiz()

        }
        binding.shareButton.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_SUBJECT, "My Kotlin quiz results")
                putExtra(Intent.EXTRA_TEXT, arguments?.getString(RESULT_TEXT_KEY))
            }
            startActivity(emailIntent)
        }
    }
    companion object {

        @JvmStatic
        fun newInstance(result: Int, text: String): QuizResult {
            val fragment = QuizResult()
            val args = Bundle()
            args.putInt(RESULT_KEY, result)
            args.putString(RESULT_TEXT_KEY, text)
            fragment.arguments = args
            return fragment
        }

        private const val RESULT_KEY = "RESULT"
        private const val RESULT_TEXT_KEY = "RESULT_TEXT"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}