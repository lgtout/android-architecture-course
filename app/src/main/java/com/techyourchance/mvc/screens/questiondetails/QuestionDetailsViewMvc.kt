package com.techyourchance.mvc.screens.questiondetails

import com.techyourchance.mvc.questions.QuestionDetails
import com.techyourchance.mvc.screens.common.ViewMvc

interface QuestionDetailsViewMvc : ViewMvc {
    fun bindQuestionDetails(questionDetails: QuestionDetails)
    fun showProgress()
    fun hideProgress()
}
