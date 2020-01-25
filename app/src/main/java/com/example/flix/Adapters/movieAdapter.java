package com.example.flix.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flix.DetailActivity;
import com.example.flix.Models.Movie;
import com.example.flix.R;

import org.parceler.Parcels;

import java.util.List;

public class movieAdapter extends RecyclerView.Adapter<movieAdapter.ViewHolder>{

    Context context;
    List<Movie> movies;

    public movieAdapter (Context context, List<Movie> movies)
    {
        this.context=context;
        this.movies=movies;
    }

    //usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //checking that we create a couple times and bind the rest to save memory
        Log.d("MovieAdapter","onCreate");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    //involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter","onBind"+position);
        //get the movie at the passed in position
        Movie movie = movies.get(position);
        //bind the movie data into the VH
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout container;
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;
        ImageView imageButton;
        //ImageView ivProfile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //define the text view
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            imageButton = itemView.findViewById(R.id.imageButton);
            //ivProfile = itemView.findViewById(R.id.ivProfile);
            container = itemView.findViewById(R.id.container);


        }

        public void bind(final Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageUrl;
            imageButton.setEnabled(true);
            if(movie.getRating()<5)
                imageButton.setVisibility(View.GONE);
            else
                imageButton.setVisibility(View.VISIBLE);

            //if in landscape mode
            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                //url = backdrop
                imageUrl = movie.getBackgroundPath();
            }
            else
                imageUrl = movie.getPostPath();


            // 1. Register click listener on the whole row
            Glide.with(context).load(imageUrl).into(ivPoster);
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 2. Navigate to a new activity on tap
                    //passing data from adapter to detail class to display
                    Intent i = new Intent(context, DetailActivity.class);
                    //i.putExtra(DetailsActivity.EXTRA_CONTACT,contact);
                    //ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(context,(View)ivProfile,"profile");
                    //startActivity(intent,options.toBundle());
                    i.putExtra("movie", Parcels.wrap(movie));



                    context.startActivity(i);
                }
            });
        }
    }
}
