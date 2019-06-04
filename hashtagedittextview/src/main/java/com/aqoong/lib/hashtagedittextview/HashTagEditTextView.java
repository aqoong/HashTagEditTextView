package com.aqoong.lib.hashtagedittextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import java.util.ArrayList;

public class HashTagEditTextView extends AppCompatEditText {
    private final String TAG = getClass().getSimpleName();

    private int mItemMaxLength;         //아이템 최대 글자수
    private int mItemMaxCount;          //아이템 최대 개수
    private boolean mAutoPoundSign;     //#자동 입력
    private String strItemMaxLengthMent;//아이템 최대 글자수 초과 멘트
    private String strItemMaxCountMent; //아이템 최대 개수 초과 멘트

    private ArrayList<String> tagList;

    private int currentKeyCode;

    public HashTagEditTextView(Context context) {
        this(context, null);
    }

    public HashTagEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.HashTagEditTextView);
        try{
            mItemMaxLength  = ta.getInt(R.styleable.HashTagEditTextView_itemMaxLength, 5);
            mItemMaxCount   = ta.getInt(R.styleable.HashTagEditTextView_itemMaxCount, 5);
            mAutoPoundSign  = ta.getBoolean(R.styleable.HashTagEditTextView_autoPoundSign, true);
            strItemMaxLengthMent = ta.getString(R.styleable.HashTagEditTextView_itemMaxLengthOverMent);
            strItemMaxCountMent  = ta.getString(R.styleable.HashTagEditTextView_itemMaxCountOverMent);
        }finally {
            ta.recycle();
        }

        initView();
    }

    private void initView(){
        tagList = new ArrayList<>();
        if (strItemMaxLengthMent == null || strItemMaxLengthMent.length() == 0){
            strItemMaxLengthMent = "태그 최대길이 초과";
        }

        if (strItemMaxCountMent == null || strItemMaxCountMent.length() == 0){
            strItemMaxCountMent = "태그 최대개수 초과";
        }

        if(mAutoPoundSign)
            this.append("#");

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.d(TAG, "=====onTextChanged====");
        Log.d(TAG, "charSequence : " + s);
        Log.d(TAG, "start : " + start);
        Log.d(TAG, "before : " + before);
        Log.d(TAG, "count : " + count);

        try {
            if (isItemOverLength()) {
                Toast.makeText(getContext(), "태그의 길이가 깁니다", Toast.LENGTH_LONG).show();
                onKeyDown(KeyEvent.KEYCODE_DEL, new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
                return;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            checkNumberChar(s);

            String lastChar = s.toString().substring(s.length() - 1);
            //checked white space
            if ((lastChar.equals(" ") || lastChar.equals("\n")) && currentKeyCode != KeyEvent.KEYCODE_DEL) {
                if(mItemMaxCount <= getInsertTag().length){
                    Toast.makeText(getContext(), "태그의 개수가 많습니다", Toast.LENGTH_LONG).show();
                    onKeyDown(KeyEvent.KEYCODE_DEL, new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
                    return;
                }

                if(mAutoPoundSign && getLastTag().length() > 1) {
                    this.append("#");
                }else{
                    return;
                }
            }
            else if(lastChar.equals("#") && currentKeyCode != KeyEvent.KEYCODE_DEL){
                if(mItemMaxCount <= getInsertTag().length){
                    Toast.makeText(getContext(), "태그의 개수가 많습니다", Toast.LENGTH_LONG).show();
                    onKeyDown(KeyEvent.KEYCODE_DEL, new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
                    return;
                }
            }
            else{
                currentKeyCode = 0;
            }

        }catch (Exception e){
        }
    }

    private boolean isItemOverLength(){
        if(getLastTag().length() > mItemMaxLength){
            return true;
        }else{
            return false;
        }
    }

    public String[] getInsertTag(){
        String[] temp = this.getText().toString().split("#");
        if(temp.length > 0) {
            String[] result = new String[temp.length - 1];
            for (int i = 0; i < result.length; i++) {
                result[i] = temp[i + 1].trim();
            }
            return result;
        }else{
            return temp;
        }
    }

    public String getLastTag(){
        String[] tagArray = getInsertTag();

        return tagArray[tagArray.length-1].trim();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        currentKeyCode = keyCode;
        if(this.length() == 0){
            this.append("#");
        }

        if(keyCode == KeyEvent.KEYCODE_DEL
                && this.length() <= 1)
        {
            return false;
        }


        return super.onKeyDown(keyCode, event);
    }

    private void checkNumberChar(CharSequence s){
        int tempCount = 0;
        for(int i = 0 ; i < s.length() ; i++){
            if('#' == s.charAt(i)){
                tempCount++;
            }
            if(tempCount > mItemMaxCount){
                Toast.makeText(getContext(), "태그의 개수가 많습니다", Toast.LENGTH_LONG).show();
                onKeyDown(KeyEvent.KEYCODE_DEL, new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
                return;
            }
        }
    }
}
