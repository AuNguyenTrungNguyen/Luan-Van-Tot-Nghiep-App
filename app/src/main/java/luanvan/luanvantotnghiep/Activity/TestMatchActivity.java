package luanvan.luanvantotnghiep.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import luanvan.luanvantotnghiep.Database.ChemistryHelper;
import luanvan.luanvantotnghiep.Model.ChemicalReaction;
import luanvan.luanvantotnghiep.R;
import luanvan.luanvantotnghiep.Util.ChemistrySingle;
import luanvan.luanvantotnghiep.Util.Constraint;

public class TestMatchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_match);
        ChemistryHelper chemistryHelper = ChemistrySingle.getInstance(this);
        List<ChemicalReaction> list = chemistryHelper.getAllChemicalReaction();

        String string = "   aaa   aaa   vvv           ";
        String s[] = string.split(" \\s+ ");

        for (String value : s) {
            Log.i(Constraint.TAG, "onCreate: " + value);
        }
        Log.i(Constraint.TAG, "onCreate: end loop");

//        RecyclerView recyclerView = findViewById(R.id.rv_test_move);
//        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(manager);
//        recyclerView.setHasFixedSize(true);
//
//        List<String> list = new ArrayList<>();
//
//        for (int i = 0; i < 10; i++) {
//            list.add("" + i);
//        }
//
//        MoveAdapter adapter = new MoveAdapter(list);
//        recyclerView.setAdapter(adapter);
    }

    private class MoveAdapter extends RecyclerView.Adapter<MoveAdapter.MoveHolder> {

        private List<String> mListData;
        private int start = -1;
        private int end = -1;

        MoveAdapter(List<String> mListData) {
            this.mListData = mListData;
        }

        @NonNull
        @Override
        public MoveHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.item_match_sentences, viewGroup, false);
            return new MoveHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final MoveHolder moveHolder, final int i) {
            if (start != i){
                moveHolder.layout.setBackgroundColor(Color.BLUE);
            }
            String data = mListData.get(i);
            moveHolder.textView.setText(data);
            moveHolder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    moveHolder.layout.setBackgroundColor(Color.CYAN);
                    end = i;
                    if (start == end){
                        moveHolder.layout.setBackgroundColor(Color.BLUE);
                        start = -1;
                    }else {
                        if (start != -1){
                            Collections.swap(mListData, start, end);
                            notifyItemMoved(start, end);
                            start = end = -1;
                        }else{
                            start = end;
                        }
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mListData.size();
        }

        class MoveHolder extends RecyclerView.ViewHolder {
            public LinearLayout layout;
            public TextView textView;

            MoveHolder(@NonNull View itemView) {
                super(itemView);
                layout = itemView.findViewById(R.id.ln_drag);
                layout.setBackgroundColor(Color.BLUE);
                textView = itemView.findViewById(R.id.tv_drag);
            }
        }
    }
}
