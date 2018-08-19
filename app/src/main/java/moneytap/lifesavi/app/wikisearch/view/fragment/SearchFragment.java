package moneytap.lifesavi.app.wikisearch.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import moneytap.lifesavi.app.wikisearch.R;
import moneytap.lifesavi.app.wikisearch.app.adapter.WikiSearchAdapter;
import moneytap.lifesavi.app.wikisearch.app.base.BaseFragment;
import moneytap.lifesavi.app.wikisearch.app.dependency.annotation.Layout;
import moneytap.lifesavi.app.wikisearch.controller.DataSearchController;
import moneytap.lifesavi.app.wikisearch.model.Page;
import moneytap.lifesavi.app.wikisearch.model.Response;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

@Layout(resId = R.layout.fragment_search)
public class SearchFragment extends BaseFragment {

    @BindView(R.id.search_view)
    public SearchView searchView;

    @BindView(R.id.progress_bar_bottom)
    public ProgressBar progressBarBottom;

    @BindView(R.id.progress_bar_top)
    public ProgressBar progressBarTop;

    @BindView(R.id.image_view_logo)
    public ImageView imageViewLogo;

    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;

    @BindView(R.id.coordinator_layout)
    public CoordinatorLayout coordinatorLayout;

    public Snackbar snackbar;

    public WikiSearchAdapter adapter;


    public int offset = 0;
    public boolean isFetchEnabled = true;
    public String currentSearch = "";
    public List<String> paginatedOffset = new ArrayList<>();

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public int getTheme() {
        return R.style.FullScreenDialog;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new WikiSearchAdapter();
        adapter.listener = new WikiSearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Page page) {
                DetailFragment fragment = DetailFragment.newInstance(page.pageid+"");
                fragment.show(getChildFragmentManager(),"");
            }
        };

        final LinearLayoutManager manager = new LinearLayoutManager(this.getContext());

        recyclerView.setLayoutManager(manager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy > 5 && adapter.getItemCount() > 0 && isFetchEnabled && manager.findLastVisibleItemPosition() == adapter.getItemCount() - 1){
                    submit();
                }
            }
        });

        recyclerView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //handleQuery(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                handleQuery(newText);
                return true;
            }
        });
    }

    public void submit() {
        search(searchView.getQuery().toString());
    }

    public void search(String topic) {

        if(offset == 0){
            adapter.clear();
            setProgressBarVisibility(VISIBLE,INVISIBLE);
            recyclerView.setVisibility(INVISIBLE);

        } else {
            setProgressBarVisibility(INVISIBLE,VISIBLE);
            recyclerView.setVisibility(VISIBLE);

        }

        currentSearch = topicOffsetKey(topic,offset);
        imageViewLogo.setVisibility(INVISIBLE);

        DataSearchController.getInstance().search(topic, offset,currentSearch, new DataSearchController.OnDataChangeListener() {
            @Override
            public void onSuccess(Response response) {

                if(!currentSearch.equals(response.topicOffSetKey) || paginatedOffset.contains(response.topicOffSetKey)){
                    return;
                }


                if(offset == 0){
                    adapter.clear();
                    paginatedOffset.clear();
                }

                if(response.query.pages.isEmpty()){
                    isFetchEnabled = false;
                }

                paginatedOffset.add(response.topicOffSetKey);

                setProgressBarVisibility(INVISIBLE,INVISIBLE);
                recyclerView.setVisibility(VISIBLE);

                offset = response.paginate.gpsoffset;
                adapter.addData(response.query.pages);

                if(offset == 0){
                    isFetchEnabled = false;
                }
            }

            @Override
            public void onError(Throwable e) {
                setProgressBarVisibility(INVISIBLE,INVISIBLE);
                showSnackBar(e.getMessage());
            }
        });
    }

    public void setProgressBarVisibility(int topProgressVisibility,int bottomProgressVisibility){
        progressBarTop.setVisibility(topProgressVisibility);
        progressBarBottom.setVisibility(bottomProgressVisibility);
    }

    public void showSnackBar(String message){
        snackbar = Snackbar.make(coordinatorLayout,message,Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    public void handleQuery(String newText){
        offset = 0;
        isFetchEnabled = true;

        if(TextUtils.isEmpty(newText)){
            recyclerView.setVisibility(INVISIBLE);
            imageViewLogo.setVisibility(VISIBLE);
            setProgressBarVisibility(INVISIBLE,INVISIBLE);
        }

        if(newText.length() > 0){
            search(newText);
        }
    }

    @NonNull
    @Contract(pure = true)
    private String topicOffsetKey(String topic, int offset){
        return topic+"*"+offset;
    }
}
