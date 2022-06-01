package com.lib.commui.nova.widget.dialog.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lib.commui.R;
import com.lib.commui.nova.ui.adapter.BaseViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 列表适配器
 */
public class StringArrayAdapter extends RecyclerView.Adapter<StringArrayAdapter.ViewHolder> {

    private List<String> listData;

    private int layoutId;

    private ResultListener<String> listener;

    public StringArrayAdapter(List<String> listData, ResultListener<String> listener) {
        this.listData = listData;
        this.layoutId = R.layout.basic_lib_simple_list_item_text;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View itemView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final String item = listData.get(position);
        holder.updateView(item);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null){
                    listener.onCallback(position, item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public static class ViewHolder extends BaseViewHolder {

        private TextView tvText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvText = itemView.findViewById(R.id.tv_text);
        }

        public void updateView(String item){
            tvText.setText(item);
        }
    }

}
