package com.winit.iKonnect.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.winit.common.application.IKonnectApplication;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.iKonnect.R;
import com.winit.iKonnect.databinding.ContentFileBinding;
import com.winit.iKonnect.dataobject.CmspostcontentfileModels;

import java.util.List;

/**
 * Created by Girish Velivela on 5/9/2017.
 */

public class FeedContentAdapter extends RecyclerView.Adapter<FeedContentAdapter.ViewHolder> {

    private Context context;
    private List<CmspostcontentfileModels> cmspostcontentfileModelses;
    private ContentFileBinder contentFileBinder;

    public FeedContentAdapter(Context context, List<CmspostcontentfileModels> cmspostcontentfileModelses){
        this.context = context;
        this.cmspostcontentfileModelses = cmspostcontentfileModelses;
        this.contentFileBinder = new ContentFileBinder();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ContentFileBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.content_file, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CmspostcontentfileModels cmspostcontentfileModels = cmspostcontentfileModelses.get(position);
        holder.bind(cmspostcontentfileModels);
    }

    @Override
    public int getItemCount() {
        if(cmspostcontentfileModelses != null)
            return cmspostcontentfileModelses.size();
        return 0;
    }

    public void refresh(List<CmspostcontentfileModels> cmspostcontentfileModelses) {
        this.cmspostcontentfileModelses = cmspostcontentfileModelses;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ContentFileBinding binding;

        public ViewHolder(ContentFileBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(CmspostcontentfileModels cmspostcontentfileModels) {
            binding.setCmspostcontentfileModels(cmspostcontentfileModels);
            binding.setContentFileBinder(contentFileBinder);
            binding.executePendingBindings();
        }
    }

    public class ContentFileBinder{
        public void viewFile(CmspostcontentfileModels cmspostcontentfileModels){
//            Intent intent = new Intent(context, WebViewActivity.class);
//            intent.putExtra(ConstantExtraKey.WEB_URL, ServiceUrls.FEEDS_DATA + cmspostcontentfileModels.getFileUrl());
            Toast.makeText(context,""+context.getString(R.string.downaloding), Toast.LENGTH_LONG).show();
            IKonnectApplication.download(ServiceUrls.FEEDS_DATA + cmspostcontentfileModels.getFileUrl());
            /*Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(ServiceUrls.FEEDS_DATA + cmspostcontentfileModels.getFileUrl()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);*/
        }
    }

}


