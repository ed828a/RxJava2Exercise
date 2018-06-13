package com.dew.edward.rxjavaexe;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class SchedulerActivity extends AppCompatActivity {

//    private Disposable subscription;
    private ProgressBar progressBar;
    private TextView messageArea;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler);

        configureLayout();

    }

    private void configureLayout(){
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        messageArea = (TextView) findViewById(R.id.messageArea);
        button = (Button) findViewById(R.id.scheduleLongRunningOperation);
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Callable<String> callable = new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return doSomethingLong();
                    }
                };

                Observable.fromCallable(callable)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable -> {
                            progressBar.setVisibility(View.VISIBLE);
                            button.setEnabled(false);
                            messageArea.setText(messageArea.getText().toString() + "\n" + "Progressbar set visible.");
                        })
                        .subscribe(getDisposableObserver());
            }
        });
    }

    public String doSomethingLong(){
        SystemClock.sleep(2000);
        return "Hello";
    }

    /**
     * Observer
     * Handles the stream of data:
     */
    private DisposableObserver<String> getDisposableObserver(){

        return new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                messageArea.setText(messageArea.getText().toString() + "\n" + "OnNext.");
            }

            @Override
            public void onError(Throwable e) {
                messageArea.setText(messageArea.getText().toString() + "\n" + "OnError.");
                progressBar.setVisibility(View.INVISIBLE);
                button.setEnabled(true);
                messageArea.setText(messageArea.getText().toString() +"\n" +"Hidding Progressbar \n" );

            }

            @Override
            public void onComplete() {
                messageArea.setText(messageArea.getText().toString() + "\n" + "OnComplete.");
                progressBar.setVisibility(View.INVISIBLE);
                button.setEnabled(true);
                messageArea.setText(messageArea.getText().toString() + "\n" + "Hidding Progressbar. \n");
            }
        };
    }
}
