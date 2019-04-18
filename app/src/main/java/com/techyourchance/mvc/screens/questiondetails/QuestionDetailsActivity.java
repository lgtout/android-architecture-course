package com.techyourchance.mvc.screens.questiondetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.techyourchance.mvc.CustomApplication;
import com.techyourchance.mvc.R;
import com.techyourchance.mvc.common.dependencyinjection.ControllerCompositionRoot;
import com.techyourchance.mvc.networking.QuestionDetailsResponseSchema;
import com.techyourchance.mvc.networking.QuestionSchema;
import com.techyourchance.mvc.networking.StackoverflowApi;
import com.techyourchance.mvc.questions.QuestionDetails;
import com.techyourchance.mvc.screens.common.BaseActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionDetailsActivity extends BaseActivity {

    public static final String EXTRA_QUESTION_ID = "EXTRA_QUESTION_ID";
    private QuestionDetailsViewMvc viewMvc;
    private ControllerCompositionRoot compositionRoot;
    private String questionId;
    private StackoverflowApi stackoverflowApi;

    public static void start(Context context, String questionId) {
        Intent intent = new Intent(context, QuestionDetailsActivity.class);
        intent.putExtra(EXTRA_QUESTION_ID, questionId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compositionRoot = new ControllerCompositionRoot(
                ((CustomApplication) getApplication()).getCompositionRoot(), this);
        viewMvc = compositionRoot.getViewMvcFactory()
                .getQuestionDetailsViewMvc(null);
        stackoverflowApi = compositionRoot.getStackoverflowApi();
        questionId = getIntent().getStringExtra(EXTRA_QUESTION_ID);
        setContentView(viewMvc.getRootView());
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewMvc.showProgress();
        fetchQuestionDetails();
    }

    private void fetchQuestionDetails() {
        stackoverflowApi.fetchQuestionDetails(questionId)
                .enqueue(new Callback<QuestionDetailsResponseSchema>() {

                    @Override
                    public void onResponse(Call<QuestionDetailsResponseSchema> call, Response<QuestionDetailsResponseSchema> response) {
                        QuestionDetailsResponseSchema responseSchema = response.body();
                        delay(1000);
                        if (response.isSuccessful()) {
                            bindQuestionDetails(responseSchema.getQuestion());
                        } else {
                            networkCallFailed();
                        }
                    }

                    @Override
                    public void onFailure(Call<QuestionDetailsResponseSchema> call, Throwable t) {
                        networkCallFailed();
                    }
                });
    }

    private void delay(int durationMillis) {
        try {
            Thread.sleep(durationMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void bindQuestionDetails(QuestionSchema question) {
        viewMvc.hideProgress();
        QuestionDetails questionDetails = new QuestionDetails(
                question.getId(), question.getTitle(), question.getBody());
        viewMvc.bindQuestionDetails(questionDetails);
    }

    private void networkCallFailed() {
        Toast.makeText(this, R.string.error_network_call_failed, Toast.LENGTH_SHORT).show();
        viewMvc.hideProgress();
    }

}
