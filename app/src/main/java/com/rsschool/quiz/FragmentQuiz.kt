package com.rsschool.quiz

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.os.bundleOf
import androidx.core.view.get
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentQuizBinding

class FragmentQuiz : Fragment(R.layout.fragment_quiz) {
    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!
    private lateinit var dataPasser: CheckedResult

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataPasser = context as CheckedResult
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireContext().apply {
            when (arguments?.getInt(WHAT_QUESTION_KEY)) {
                1 -> setTheme(R.style.Theme_Quiz_First)
                2 -> setTheme(R.style.Theme_Quiz_Second)
                3 -> setTheme(R.style.Theme_Quiz_Third)
                4 -> setTheme(R.style.Theme_Quiz_Fourth)
                5 -> setTheme(R.style.Theme_Quiz_Fifth)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val strings = arguments?.getStringArray(STRINGS_KEY)
        val whatRadioClicked = arguments?.getInt(PREVIOUS_RESULT_KEY)
        val whatQuestion = arguments?.getInt(WHAT_QUESTION_KEY)
        if (whatQuestion?.equals(5) == true) {
            binding.nextButton.text = getString(R.string.next_button_text)
        }

//        activity?.window?.statusBarColor = resources.getColor(R.color.black)

        binding.apply {
            toolbar.title = "Question " + whatQuestion.toString()
            question.text = strings?.get(0) ?: ""

            radioGroup.apply {
                //Make option buttons
                if (strings != null) {
                    for (i in 1..strings.lastIndex) {
                        addView(
                            RadioButton(requireContext()).apply {
                                id = i
                                textSize = 17F
                                text = strings[i]
                            }
                        )
                    }


                    if (whatRadioClicked in 1..strings.lastIndex) {
                        radioGroup.findViewById<RadioButton>(whatRadioClicked?:0).isChecked = true
                    } else {
                        clearCheck()
                        nextButton.isEnabled = false
                    }

                    setOnCheckedChangeListener { _, _ ->
                        nextButton.isEnabled = true
                    }
                }
            }

            if (whatQuestion == 1) {
                previousButton.visibility = View.INVISIBLE
                toolbar.navigationIcon = null
            } else previousButton.visibility = View.VISIBLE

            if (whatQuestion != 1) toolbar.setNavigationOnClickListener {
                goToPreviousFragment()
            }
            previousButton.setOnClickListener {
                goToPreviousFragment()
            }

            nextButton.setOnClickListener {
                dataPasser.apply {
                    questionAnswerSaver(radioGroup.checkedRadioButtonId)
                    openFragment(true)
                }
            }
        }
    }

    private fun goToPreviousFragment() {
        dataPasser.apply {
            questionAnswerSaver(binding.radioGroup.checkedRadioButtonId)
            openFragment(false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance(
            previousResult: Int,
            strings: Array<String>,
            whatQuestion: Int
        ): FragmentQuiz {
            return FragmentQuiz().apply {
                arguments = bundleOf(
                    PREVIOUS_RESULT_KEY to previousResult,
                    WHAT_QUESTION_KEY to whatQuestion,
                    STRINGS_KEY to strings
                )
            }
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
        private const val STRINGS_KEY = "STRINGS"
        private const val WHAT_QUESTION_KEY = "WHAT_QUESTION"

    }
}