package com.kazuaiko.pokegousan2;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.kazuaiko.pokegousan2.databinding.AdapterFeedItemBinding;

import java.util.List;

/**
 * Created by murata_to on 2016/08/01.
 */
public class FeedItemAdapter extends BaseAdapter {

	private Context context;
	private List<FeedItem> list;

	private AdapterFeedItemBinding binding;

	public FeedItemAdapter(Context context, List<FeedItem> list) {
		this.context = context;
		this.list = list;
	}

	public void setList(List<FeedItem> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_feed_item, parent, false);
			convertView = binding.getRoot();
		}

		binding = DataBindingUtil.bind(convertView);
		binding.title.setText(list.get(position).getTitle());

		return binding.getRoot();
	}
}
