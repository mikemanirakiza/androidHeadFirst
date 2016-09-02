package study.pmoreira.bitsandpizzas;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.drawable.Drawable;

public class CaptionedImagesAdapter extends RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder> {

    private String[] captions;
    private int[] imageIds;

    private Listener listener;

    public interface Listener {
        void onClick(int position);
    }

    public CaptionedImagesAdapter(final String[] captions, final int[] imageIds) {
        this.captions = captions;
        this.imageIds = imageIds;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        public ViewHolder(final CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        return new ViewHolder((CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_captioned_image, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        CardView cardView = holder.cardView;

        ImageView infoImage = (ImageView) cardView.findViewById(R.id.info_image);
        infoImage.setImageDrawable(cardView.getResources().getDrawable(imageIds[position]));
        infoImage.setContentDescription(captions[position]);

        TextView infoText = (TextView) cardView.findViewById(R.id.info_text);
        infoText.setText(captions[position]);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (listener != null) { listener.onClick(position); }
            }
        });
    }

    @Override
    public int getItemCount() {
        return captions.length;
    }

    public void setListener(final Listener listener) { this.listener = listener; }
}
