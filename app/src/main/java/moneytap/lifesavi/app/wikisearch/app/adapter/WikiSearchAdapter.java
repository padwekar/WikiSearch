package moneytap.lifesavi.app.wikisearch.app.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import org.jetbrains.annotations.Contract;
import java.util.ArrayList;
import java.util.List;

import moneytap.lifesavi.app.wikisearch.R;
import moneytap.lifesavi.app.wikisearch.model.Page;

public class WikiSearchAdapter extends RecyclerView.Adapter<WikiSearchAdapter.ViewHolder> {

    public interface OnItemClickListener{
        void onItemClick(Page page);
    }

    private List<Page> pageList = new ArrayList<>();
    public OnItemClickListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Page page = getItem(position);

        if(!TextUtils.isEmpty(page.title)){
            holder.textViewTitle.setText(page.title);
        }

        if(page.terms != null && page.terms.description !=null && page.terms.description.size() > 0){
            holder.textViewContent.setText(page.terms.description.get(0));
        }

        if(page.thumbnail!=null && !TextUtils.isEmpty(page.thumbnail.source)){
            Picasso.get().load(page.thumbnail.source).into(holder.imageView);
        } else {
            Picasso.get().load(R.drawable.logo_wiki).into(holder.imageView);

        }

    }

    @Override
    public int getItemCount() {
        return pageList.size();
    }

    public void clear(){
        pageList.clear();
        notifyDataSetChanged();
    }

    public void addData(List<Page> pages){
        pageList.addAll(pages);
        notifyDataSetChanged();
    }

    public List<Page> getData(){
        return pageList;
    }

    @Contract(pure = true)
    private Page getItem(int position){
        return pageList.get(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textViewTitle;
        TextView textViewContent;

         ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            textViewTitle = itemView.findViewById(R.id.text_view_page_title);
            textViewContent = itemView.findViewById(R.id.text_view_content);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        listener.onItemClick(getItem(getAdapterPosition()));
                    }
                }
            });
        }
    }
}
