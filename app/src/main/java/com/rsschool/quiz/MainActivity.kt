package com.rsschool.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity(), CheckedResult {
    private val question1: Array<String> = arrayOf("When kotlin first appeared?", "June 22, 2011",
        "July 20, 2010", "July 22, 2011", "July 20, 2011", "June 20, 2011")
    private val question2: Array<String> = arrayOf("Who developed Kotlin?", "JetBrains",
        "Microsoft", "Oracle", "IBM", "Google")
    private val question3: Array<String> = arrayOf("When version 1.0 was released?", "February 16, 2016",
        "February 15, 2016", "February 16, 2015", "February 15, 2016", "January 16, 2016")
    private val question4: Array<String> = arrayOf("Which file extension is used to save Kotlin files?", ".java",
        ".cpp", ".kot", ".kt", ".xml")
    private val question5: Array<String> = arrayOf("All classes in Kotlin classes are by default?", "public",
        "private", "abstract", "sealed", "final")

    private val questions = arrayOf(question1, question2, question3, question4, question5)
    private var checkedRadioNumbers: Array<Int> = arrayOf(0, 0, 0, 0, 0)
    private val rightCheckedRadioNumbers: Array<Int> = arrayOf(3, 1, 2, 4, 5)

    var currentFragment = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openNewFragment(checkedRadioNumbers[currentFragment - 1], question1)
    }

    private fun openNewFragment(checkedRadio: Int, strings: Array<String>) {
        val fragment: Fragment = FragmentQuiz.newInstance(checkedRadio, strings, currentFragment)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }

    override fun openFragment(isNext: Boolean) {
        if (isNext) currentFragment++
        else currentFragment--

        when(currentFragment) {
            6 -> {
                openResultFragment(produceResult())
            }
            else ->  {
                openNewFragment(checkedRadioNumbers[currentFragment - 1], questions[currentFragment - 1])
            }
        }
    }

    private fun produceResult(): Int {
        var sum = 0
        for (i in 0..4) {
            if(checkedRadioNumbers[i] == rightCheckedRadioNumbers[i]) sum++
        }
        return sum * 20
    }

    fun openResultFragment(result: Int) {
        currentFragment = 6
        val string = """
My result: $result% 
             
1) ${questions[0][0]}  
My answer: ${questions[0][checkedRadioNumbers[0]]} 
              
2) ${questions[1][0]}  
My answer: ${questions[1][checkedRadioNumbers[1]]} 

3) ${questions[2][0]}  
My answer: ${questions[2][checkedRadioNumbers[2]]} 

4) ${questions[3][0]}  
My answer: ${questions[3][checkedRadioNumbers[3]]} 
                
5) ${questions[4][0]}  
My answer: ${questions[4][checkedRadioNumbers[4]]} """

        val fragment: Fragment = QuizResult.newInstance(result, string)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }
    override fun onBackPressed() {
        when {
            currentFragment == 6 -> restartQuiz()
            currentFragment != 1 -> openFragment(false)
            else -> super.onBackPressed()
        }
    }

    override fun restartQuiz() {
        checkedRadioNumbers = arrayOf(0, 0, 0, 0, 0)
        currentFragment = 1
        openNewFragment(checkedRadioNumbers[currentFragment - 1], question1)
    }

    override fun finishQuiz() {
        super.onBackPressed()
    }

    override fun questionAnswerSaver(radioNumber: Int) {
        checkedRadioNumbers[currentFragment - 1] = radioNumber
    }
}