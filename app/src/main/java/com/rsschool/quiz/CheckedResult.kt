package com.rsschool.quiz

interface CheckedResult {
    fun questionAnswerSaver(radioNumber: Int)
    fun openFragment(isNext: Boolean)
    fun restartQuiz()
    fun finishQuiz()
}