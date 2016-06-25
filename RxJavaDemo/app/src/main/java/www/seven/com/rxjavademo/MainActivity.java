package www.seven.com.rxjavademo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        demo1();
        demo2();

        demo3();
        demo4();
    }
    
    private void demo1() {

        final String tag = "demo1";
        
        String[] text = new String[]{"hello", "world"};

        Log.d(tag, "ui线程=" + Thread.currentThread().getId());

        Observable.from(text)


                // 让订阅者工作在ui线程上
                .observeOn(AndroidSchedulers.mainThread())

                // 让filter和map工作在其他线程上
                .subscribeOn(Schedulers.io())
                
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {

                        Log.d(tag, "filter线程=" + Thread.currentThread().getId());

                        // 和订阅者在同一个线程

                        // 过滤掉 world
                        return s.startsWith("h");
                    }
                })
                
                .map(new Func1<String, String>() {

                    @Override
                    public String call(String s) {

                        Log.d(tag, "map线程=" + Thread.currentThread().getId());

                        // 和订阅者在同一个线程

                        // 给hello 加上一个叹号
                        return s+ "!";
                    }
                })

                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {

                        Log.d(tag, "subscribe线程=" + Thread.currentThread().getId());
                        // 显示 "hello!"
                        Log.d(tag, s);
                    }
                });
    }

    private void demo2() {

        final String tag = "demo2";

        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

                subscriber.onNext("next");
            }
        });

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

                Log.d(tag, "observer + " + s);
            }
        };

        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.d(tag, "subscriber + " + s);
            }
        };

        observable.subscribe(observer);
        observable.subscribe(subscriber);

        Observable.just("me").subscribe(observer);
    }

    private void demo3() {

        String tag = "demo3";

        Action1<String> next = new Action1<String>() {
            @Override
            public void call(String s) {

            }
        };

        Action1<Throwable> error = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        };

        Action0 complete = new Action0() {
            @Override
            public void call() {

            }
        };


        Observable.just("demo3").subscribe(next, error, complete);
    }


    private void demo4() {

        final String tag = "demo4";

        Log.d(tag, "ui线程 = " + Thread.currentThread().getId());

        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

                Log.d(tag, "工作线程 = " + Thread.currentThread().getId());
                subscriber.onNext("next");
                subscriber.onCompleted();
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
        .subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.d(tag, "onCompleted 线程 = " + Thread.currentThread().getId());
            }

            @Override
            public void onError(Throwable e) {
                Log.d(tag, "onError 线程 = " + Thread.currentThread().getId());
            }

            @Override
            public void onNext(String s) {
                Log.d(tag, "onNext 线程 = " + Thread.currentThread().getId());
            }
        });


        Observable.just(1) // IO 线程，由 subscribeOn() 指定
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {

                        Log.d(tag, "map1 线程 = " + Thread.currentThread().getId());
                        return integer+"";
                    }
                }) // 新线程，由 observeOn() 指定
                .observeOn(Schedulers.io())
                .map(new Func1<String, Integer>() {

                    @Override
                    public Integer call(String s) {
                        Log.d(tag, "map2 线程 = " + Thread.currentThread().getId());
                        return Integer.parseInt(s);
                    }
                }) // IO 线程，由 observeOn() 指定
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {

                        Log.d(tag, "onNext1 线程 = " + Thread.currentThread().getId());
                    }
                });  // Android 主线程，由 observeOn() 指定
    }
}
