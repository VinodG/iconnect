package com.winit.iKonnect.adapter;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.winit.common.application.IKonnectApplication;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.iKonnect.BR;
import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.MyDocumentsDO;
import com.winit.iKonnect.ui.activities.MyDocumentsActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Sreekanth.P on 06-12-2017.
 */

public class MyDocumentsAdapter extends RecyclerView.Adapter<MyDocumentsAdapter.ViewHolder> {

    private Context context;
    private ArrayList<MyDocumentsDO> arrayList;
    private MyDocumentsBinder myDocumentsBinder;

    private ImageView ivDocument;

    public MyDocumentsAdapter(Context context, ArrayList<MyDocumentsDO> documentsArrayList) {
        this.context = context;
        this.arrayList = documentsArrayList;
        myDocumentsBinder = new MyDocumentsBinder();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.my_documents_cell, parent, false);

        View view = binding.getRoot();

        ivDocument = (ImageView) view.findViewById(R.id.ivDocument);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MyDocumentsDO myDocumentsDO = arrayList.get(position);
//        IKonnectApplication.setImageUrl(ivDocument, ServiceUrls.DOCUMENTS_URL + myDocumentsDO.FolderPath, R.drawable.profile_pic, true);
        holder.bind(myDocumentsDO);
    }

    @Override
    public int getItemCount() {
        if (arrayList != null && arrayList.size() > 0) {
            return arrayList.size();
        } else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding binding;

        public ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(MyDocumentsDO myDocumentsDO) {
            binding.setVariable(BR.myDocumentsDO, myDocumentsDO);
            binding.setVariable(BR.myDocumentsBinder, myDocumentsBinder);
            binding.executePendingBindings();
        }
    }

    public class MyDocumentsBinder {

        public void downLoadDocuments(String url) {
            IKonnectApplication.download(ServiceUrls.DOCUMENTS_URL + url);
        }
    }


    public void refresh(ArrayList<MyDocumentsDO> documentsArrayList) {
        this.arrayList = documentsArrayList;
        notifyDataSetChanged();
    }
}
