# HashTagEditTextView
[Android CustomView] HashTag Edit TextView

![Alt Text](https://github.com/aqoong/HashTagEditTextView/raw/master/sample.gif)

## How to Use
### .xml setting
```
<com.aqoong.lib.hashtagedittextview.HashTagEditTextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:itemMaxLength="5"                               //Length of tag
        app:itemMaxCount="5"                                //Number of tags
        app:autoPoundSign="true"                            //Enter '#' automatically by blanking.
        app:itemMaxLengthOverMent="The tag is too long." 
        app:itemMaxCountOverMent="Too many tags."
        />
```
### .java
```
    HashTagEditTextView tagTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ...
        String[] hashTagArray = tagTextView.getInsertTag();
        ...
    }
```

## Add HashTagEditTextView Lib
  - project build.gradle
  ```
    allprojects {
      repositories {
        google()
        jcenter()
        ...
        maven { url "https://jitpack.io"}
        ...
      }
    }
  ```
  - app build.gradle  [![](https://jitpack.io/v/aqoong/HashTagEditTextView.svg)](https://jitpack.io/#aqoong/HashTagEditTextView)
  ```
    dependencies {
      ...
      implementation 'com.github.aqoong:HashTagEditTextView:Tag'
      ...
    }
  ```
