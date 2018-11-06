package assem.nanaodegree.popularmovies_udacity.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import assem.nanaodegree.popularmovies_udacity.App.AppConfig;
import assem.nanaodegree.popularmovies_udacity.Models.TrailerModel;
import assem.nanaodegree.popularmovies_udacity.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailerHolder> {

    private Context context;
    private ArrayList<TrailerModel> trailersArrayList;
    private AppConfig appConfig;

    public TrailersAdapter(Context context, ArrayList<TrailerModel> trailersArrayList) {
        this.context = context;
        this.trailersArrayList = trailersArrayList;
        appConfig = new AppConfig();
    }

    @NonNull
    @Override
    public TrailerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.item_trailer, parent, false);
        return new TrailerHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerHolder holder, int position) {
        final TrailerModel trailerModelObj = trailersArrayList.get(position);
        holder.trailerTitle.setText(trailerModelObj.getName());
        Picasso.get()
                .load(appConfig.getYOUTUBE_THUMBNAIL_PART_ONE() + trailerModelObj.getKey()
                        + appConfig.getYOUTUBE_THUMBNAIL_PART_TWO())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(holder.trailerImg);

        holder.trailerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(appConfig.getYOUTUBE_WATCH_URL() + trailerModelObj.getKey()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return trailersArrayList.size();
    }

    class TrailerHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_trailer_layout)
        LinearLayout trailerLayout;
        @BindView(R.id.item_trailer_img)
        ImageView trailerImg;
        @BindView(R.id.item_trailer_title)
        TextView trailerTitle;

        private TrailerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

