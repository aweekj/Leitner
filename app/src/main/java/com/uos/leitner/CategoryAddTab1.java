package com.uos.leitner;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.uos.leitner.helper.DatabaseHelper;
import com.uos.leitner.model.Category;

/**
 * Created by HANJU on 2016. 12. 2..
 */

public class CategoryAddTab1 extends Fragment {

    private DatabaseHelper db;
    private EditText categoryNameET;
    private SeekBar goalTimeSB;
    private TextView goalTimeTV;
    private SeekBar levelSB;
    private TextView levelTV;
    private Button saveBTN;
    private Intent intent;
    private int count;

    static int MAX = new MainActivity().getMAX();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.new_category_tab_1, null);

        intent = new Intent(getActivity(), MainActivity.class);

        count = getActivity().getIntent().getIntExtra("count", count);

        db = new DatabaseHelper(getActivity());

        categoryNameET = (EditText) view.findViewById(R.id.categoryNameEditText);

        goalTimeSB = (SeekBar) view.findViewById(R.id.goalTimeSeekBar);
        goalTimeSB.setProgress(25);     // 초기시간으로 25분
        goalTimeTV = (TextView) view.findViewById(R.id.goalTimeValueTextView);
        goalTimeTV.setText(String.valueOf(goalTimeSB.getProgress()));

        goalTimeSB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                goalTimeTV.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                goalTimeTV.setText(goalTimeTV.getText());
            }
        });

        levelSB = (SeekBar) view.findViewById(R.id.levelSeekBar);
        levelSB.setProgress(5);     // 초기레벨로 5단계
        levelTV = (TextView) view.findViewById(R.id.levelValueTextView);
        levelTV.setText(String.valueOf(levelSB.getProgress()));

        levelSB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                levelTV.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                levelTV.setText(levelTV.getText());
            }
        });

        saveBTN = (Button) view.findViewById(R.id.saveButton);
        saveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String categoryName = String.valueOf(categoryNameET.getText());
                int level = levelSB.getProgress();
                int goalTime = goalTimeSB.getProgress();
                int mode = 1; // mode 1 is Leitner Timer

                if(count<MAX) {
                    Category newCategory = new Category(categoryName, level, goalTime, mode);
                    db.createCategory(newCategory);
                    count++;
                    intent.putExtra("count", count);
                    getActivity().finish();
                    startActivity(intent);
                }

                else {
                    Toast.makeText(getActivity(), "생성할 수 있는 카테고리 개수는 5개입니다.", Toast.LENGTH_LONG).show();
                }


            }

        });

        return view;
    }
}