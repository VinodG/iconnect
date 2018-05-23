package com.winit.iKonnect.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.winit.iKonnect.R;
import com.winit.iKonnect.databinding.ToggleButtonLayoutBinding;
import com.winit.iKonnect.dataobject.RadioBTNDO;

import java.util.ArrayList;

/**
 * Created by Girish Velivela on 5/9/2017.
 */

public class RedioBTNAdapter extends RecyclerView.Adapter<RedioBTNAdapter.ViewHolder> {

    private Context context;
    private ArrayList<RadioBTNDO> redioBTNDOs;
    private RedioBTNBinder redioBTNBinder;

    public RedioBTNAdapter(Context context, ArrayList<RadioBTNDO> redioBTNDOs){
        this.context = context;
        this.redioBTNDOs = redioBTNDOs;
        redioBTNBinder = new RedioBTNBinder();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ToggleButtonLayoutBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.toggle_button_layout, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final RadioBTNDO redioBTNDO = redioBTNDOs.get(position);
        holder.bind(redioBTNDO);
    }
    @Override
    public int getItemCount() {
        if(redioBTNDOs != null)
            return redioBTNDOs.size();
        else
        return 0;
    }

    public void refresh(ArrayList<RadioBTNDO> redioBTNDOs) {
        this.redioBTNDOs = redioBTNDOs;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ToggleButtonLayoutBinding binding;

        public ViewHolder(ToggleButtonLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(RadioBTNDO redioBTNDOs) {
            binding.executePendingBindings();
            binding.setRedioBTNDO(redioBTNDOs);
            binding.setRedioBTNBinder(redioBTNBinder);
        }
    }

    public class RedioBTNBinder{

        public void onClick(RadioBTNDO redioBTNDOs){
            if(redioBTNDOs != null){
            }
        }

        public void onSplitTypeChanged(RadioGroup radioGroup,int id){
            Log.d("iKonnect", ""+id);
            RadioBTNDO item = (RadioBTNDO) radioGroup.getTag();
            item.ans = (String) radioGroup.findViewById(id).getTag();
        }
    }
}


