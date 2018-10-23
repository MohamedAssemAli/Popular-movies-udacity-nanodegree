package assem.nanaodegree.popularmovies_udacity.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import assem.nanaodegree.popularmovies_udacity.Models.ReviewModel;
import assem.nanaodegree.popularmovies_udacity.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewHolder> {

    private Context context;
    private ArrayList<ReviewModel> reviewsArrayList;

    public ReviewsAdapter(Context context, ArrayList<ReviewModel> reviewsArrayList) {
        this.context = context;
        this.reviewsArrayList = reviewsArrayList;
    }

    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.item_review, parent, false);
        return new ReviewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder holder, int position) {
        final ReviewModel reviewModelObj = reviewsArrayList.get(position);
        holder.reviewAuthor.setText(reviewModelObj.getAuthor());
        holder.reviewContent.setText(reviewModelObj.getContent());
    }

    @Override
    public int getItemCount() {
        return reviewsArrayList.size();
    }

    class ReviewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_review_author)
        TextView reviewAuthor;
        @BindView(R.id.item_review_content)
        TextView reviewContent;

        private ReviewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
