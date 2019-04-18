package com.techyourchance.mvc.screens.questiondetails

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.techyourchance.mvc.R
import com.techyourchance.mvc.questions.QuestionDetails
import com.techyourchance.mvc.screens.common.BaseViewMvc

class QuestionDetailsViewMvcImpl(inflater: LayoutInflater, parent: ViewGroup?)
    : BaseViewMvc(), QuestionDetailsViewMvc {
    override fun hideProgress() {
        progress.visibility = View.GONE
    }

    override fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    private val titleView: TextView
    private val bodyView: TextView
    private val progress: ProgressBar

    init {
        setRootView(inflater.inflate(R.layout.layout_question_details, parent, false))
        titleView = findViewById(R.id.title)
        bodyView = findViewById(R.id.body)
        progress = findViewById(R.id.progress)
    }

    override fun bindQuestionDetails(questionDetails: QuestionDetails) =
        with (questionDetails) {
            titleView.text = title
            bodyView.text = Html.fromHtml(body).toString()
        }

}