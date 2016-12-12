## ML-MarqueeView<br>
垂直跑马灯<br>
#效果展示<br>
![image](http://img.blog.csdn.net/20161211171718335?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvd2VpeGluXzM2MDM1NDA2/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)

<br>#特点
>使用方便<br>
>可以监听条目的变更<br>
#使用方法
>布局文件```xml
<com.a95.utf_8.super_marquee_textview.MarqueeView
        android:id="@+id/mv"
        android:background="@android:color/darker_gray"
        android:layout_width="match_parent"
        android:layout_height="40dp" />

    <com.a95.utf_8.super_marquee_textview.MarqueeView
        android:layout_marginTop="10dp"
        android:id="@+id/mv2"
        android:background="@drawable/bg"
        android:layout_width="match_parent"
        android:layout_height="40dp" />

    <TextView
        android:id="@+id/tv"
        android:layout_width="match_parent"
        android:layout_height="40dp"
        android:textSize="20sp"
        android:text="1111"
        />```
代码演示
```java
        //获取布局中的控件对象
        mv = (MarqueeView) findViewById(R.id.mv);
        mv2 = (MarqueeView) findViewById(R.id.mv2);
        tv = (TextView) findViewById(R.id.tv);

        //创建内容
        contents = new ArrayList<>();

        contents.add("AAAAAA");
        contents.add("BBBBBB");
        contents.add("CCCCCC");
        contents.add("DDDDDD");
        contents.add("EEEEEE");
        contents.add("FFFFFF");
        contents.add("GGGGGG");
        contents.add("HHHHHH");


        mv.setContents(contents); //设置内容
        mv.setOffsetWidth(100); //设置偏移量 正值往右移动
        mv.setTextColor(Color.RED); //改变字体颜色
        //设置点击事件
        mv.setOnChangeTextListener(new MarqueeView.OnChangeTextListener() {
            @Override
            public void changerText(int position) {
                tv.setText(contents.get(position));
            }
        });
        //开启跑马灯
        mv.start();
        
        mv2.setContents(contents);
        mv2.setTextColor(Color.WHITE);
        mv2.setDuration(500);
        
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //延时开启
                mv2.start();
            }
        },3000);```
