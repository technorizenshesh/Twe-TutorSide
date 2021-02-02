package com.tech.twe_tutorside.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.tech.twe_tutorside.R;
import com.tech.twe_tutorside.fragments.CompletedFragment;
import com.tech.twe_tutorside.fragments.TodayClassDetailFragment;
import com.tech.twe_tutorside.listner.FragmentListener;
import com.tech.twe_tutorside.model.ClassTodayModal;
import com.tech.twe_tutorside.model.CurrentRequestModal;

import java.util.ArrayList;
import java.util.List;

public class ClassTodayAdapter extends RecyclerView.Adapter<ClassTodayAdapter.ViewHolder> {

    private Context context;
    private List<ClassTodayModal> classTodayModalList = new ArrayList<>();
    FragmentListener listener;

    public ClassTodayAdapter(FragmentListener listener) {
        this.listener = listener;
    }

    public ClassTodayAdapter(Context context, List<ClassTodayModal> classTodayModal) {
        this.context = context;
        this.classTodayModalList = classTodayModal;
    }



    @NonNull
    @Override
    public ClassTodayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_class_today, parent,false);

        ClassTodayAdapter.ViewHolder viewHolder = new ClassTodayAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ClassTodayAdapter.ViewHolder holder, int position) {

        holder.cardTodayInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                ViewGroup mContainer = (ViewGroup) activity.findViewById(R.id.frameContainerRequest);
                mContainer.removeAllViews();
                Fragment myFragment = new TodayClassDetailFragment(listener);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameContainerRequest,
                        myFragment).addToBackStack(null).commit();

            }
        });

    }


    @Override
    public int getItemCount() {
        return 3;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardTodayInit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardTodayInit=itemView.findViewById(R.id.cardTodayInit);


        }
    }

    //here
    public interface OnRecyclerViewItemClickListener {
        void onClick(int parameter);
    }
}
