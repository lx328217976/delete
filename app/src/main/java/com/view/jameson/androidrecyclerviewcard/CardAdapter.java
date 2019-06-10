package com.view.jameson.androidrecyclerviewcard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.view.jameson.library.CardAdapterHelper;

import java.util.ArrayList;
import java.util.List;

//import jameson.io.library.util.ToastUtils;

/**
 * Created by jameson on 8/30/16.
 */
class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    private List<Integer> mList = new ArrayList<>();
    private CardAdapterHelper mCardAdapterHelper = new CardAdapterHelper();
    private Context mContext;
    private int status = 0;

    public CardAdapter(List<Integer> mList) {
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_card_item, parent, false);
        mContext = parent.getContext();
        mCardAdapterHelper.onCreateViewHolder(parent, itemView);
        return new ViewHolder(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.e("lx2=", "position=" + position);
        holder.sv_view_card_item.scrollTo(0, 0);
        mCardAdapterHelper.onBindViewHolder(holder.itemView, position, getItemCount());
        holder.sv_view_card_item.getLayoutParams().width = 800;
        holder.mImageView.getLayoutParams().height = 1280;
        holder.mImageView.setImageResource(mList.get(position));
        holder.sv_view_card_item.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        status = 1;
                        break;
                    case MotionEvent.ACTION_UP: {
                        status = 2;
                        Log.e("lx2=", holder.sv_view_card_item.getScrollY() + "");
                        if (holder.sv_view_card_item.getScrollY() > 500) {
                            startAnim(holder.ll_parent, holder.sv_view_card_item, position);
                        } else {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Log.e("lx#", "1111");
                                    holder.sv_view_card_item.smoothScrollTo(0, 0);
                                }
                            }, 500);

                            return true;
                        }
                    }
                }
                return false;
            }
        });
        holder.sv_view_card_item.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Log.e("lx@", scrollY + "");
                Log.e("lx@", oldScrollY + "");
                Log.e("lx@", "status=" + status);
                if (scrollY > 500) {
                    holder.sv_view_card_item.scrollTo(0, 500);
                }
                if (status == 2) {
                    if (oldScrollY >= 500) {
                        startAnim(holder.ll_parent, holder.sv_view_card_item, position);
                    } else {
                        Log.e("lx##", "111");
                        holder.sv_view_card_item.smoothScrollTo(0, 0);
                    }
                }
            }
        });
//        holder.mImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.out_to_top);
//                animation.setFillAfter(false);
//                animation.setAnimationListener(new Animation.AnimationListener() {
//                    @Override
//                    public void onAnimationStart(Animation animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animation animation) {
//                        mList.remove(position);
//                        notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animation animation) {
//
//                    }
//                });
//                holder.ll_parent.startAnimation(animation);
//
//
////                ToastUtils.show(holder.mImageView.getContext(), "" + position);
//            }
//        });
    }

    private void startAnim(LinearLayout linearLayout, final ScrollView scrollView, final int _position) {
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.out_to_top);
        animation.setFillAfter(false);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                scrollView.scrollTo(0, 0);
                mList.remove(_position);
                notifyDataSetChanged();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        linearLayout.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mImageView;
        public final LinearLayout ll_parent;
        public final ScrollView sv_view_card_item;

        public ViewHolder(final View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.imageView);
            ll_parent = itemView.findViewById(R.id.ll_parent);
            sv_view_card_item = itemView.findViewById(R.id.sv_view_card_item);
        }

    }

}
