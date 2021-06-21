package com.rsschool.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentQuizBinding

class FragmentQuiz: Fragment(R.layout.fragment_quiz) {


    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        val view = binding.root

        val strings = arguments?.getStringArray(STRINGS_KEY)
        val whatRadioClicked = arguments?.getInt(PREVIOUS_RESULT_KEY)
        val whatQuestion = arguments?.getInt(WHAT_QUESTION_KEY)
        if (whatQuestion?.equals(5) == true) {
            binding.nextButton.text = "Submit"
        }

        activity?.window?.statusBarColor = getResources().getColor(R.color.black)
        when (whatQuestion) {
            1 -> activity?.setTheme(R.style.Theme_Quiz_First)
            2 -> activity?.setTheme(R.style.Theme_Quiz_Third)
            3 -> activity?.setTheme(R.style.Theme_Quiz_Third)
            4 -> activity?.setTheme(R.style.Theme_Quiz_Fourth)
            5 -> activity?.setTheme(R.style.Theme_Quiz_Fifth)
        }

        binding.toolbar.title = "Question " + whatQuestion.toString()
        binding.question.text = strings?.get(0) ?: ""
        binding.optionOne.text = strings?.get(1) ?: ""
        binding.optionTwo.text = strings?.get(2) ?: ""
        binding.optionThree.text = strings?.get(3) ?: ""
        binding.optionFour.text = strings?.get(4) ?: ""
        binding.optionFive.text = strings?.get(5) ?: ""

        when(whatRadioClicked) {
            1-> binding.optionOne.isChecked = true
            2-> binding.optionTwo.isChecked = true
            3-> binding.optionThree.isChecked = true
            4-> binding.optionFour.isChecked = true
            5-> binding.optionFive.isChecked = true
            else -> {
                binding.radioGroup.clearCheck()
                binding.nextButton.isEnabled = false
            }
        }

        if(whatQuestion == 1) {
            binding.previousButton.visibility = View.INVISIBLE
            binding.toolbar.navigationIcon = null
        }
        else binding.previousButton.visibility = View.VISIBLE
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val whatQuestion = arguments?.getInt(WHAT_QUESTION_KEY)

        if(whatQuestion!= 1) binding.toolbar.setNavigationOnClickListener {
            goToPreviousFragment()
        }
        binding.previousButton.setOnClickListener {
            goToPreviousFragment()

        }
        binding.radioGroup.setOnCheckedChangeListener { _, _ ->
            binding.nextButton.isEnabled = true
        }
        binding.nextButton.setOnClickListener {
            when(binding.radioGroup.checkedRadioButtonId) {
                binding.optionOne.id -> {
                    mainActivity().questionAnswerSaver(1)
                    mainActivity().openFragment(true)
                }

                binding.optionTwo.id -> {
                    mainActivity().questionAnswerSaver(2)
                    mainActivity().openFragment(true)
                }

                binding.optionThree.id -> {
                    mainActivity().questionAnswerSaver(3)
                    mainActivity().openFragment(true)
                }

                binding.optionFour.id -> {
                    mainActivity().questionAnswerSaver(4)
                    mainActivity().openFragment(true)
                }

                binding.optionFive.id -> {
                    mainActivity().questionAnswerSaver(5)
                    mainActivity().openFragment(true)
                }
            }
        }
    }

    private fun goToPreviousFragment() {
        when(binding.radioGroup.checkedRadioButtonId) {
            binding.optionOne.id -> {
                mainActivity().questionAnswerSaver(1)
                mainActivity().openFragment(false)
            }

            binding.optionTwo.id -> {
                mainActivity().questionAnswerSaver(2)
                mainActivity().openFragment(false)
            }

            binding.optionThree.id -> {
                mainActivity().questionAnswerSaver(3)
                mainActivity().openFragment(false)
            }
            binding.optionFour.id -> {
                mainActivity().questionAnswerSaver(4)
                mainActivity().openFragment(false)
            }

            binding.optionFive.id -> {
                mainActivity().questionAnswerSaver(5)
                mainActivity().openFragment(false)
            }
            else -> mainActivity().openFragment(false)

        }
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int, strings: Array<String>, whatQuestion: Int): FragmentQuiz {
            val fragment = FragmentQuiz()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            args.putInt(WHAT_QUESTION_KEY, whatQuestion)
            args.putStringArray(STRINGS_KEY, strings)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
        private const val STRINGS_KEY = "STRINGS"
        private const val WHAT_QUESTION_KEY = "WHAT_QUESTION"

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}