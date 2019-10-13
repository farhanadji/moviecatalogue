package picodiploma.dicoding.favorite;

import android.view.View;

public class CustomOnItemClickListener implements View.OnClickListener {
    private int position;
    private onItemClickCallback onItemClickCallback;
    public CustomOnItemClickListener(int position, onItemClickCallback onItemClickCallback ){
        this.position = position;
        this.onItemClickCallback = onItemClickCallback;
    }

    @Override
    public void onClick(View v) {
        onItemClickCallback.onItemClicked(v, position);
    }
    public interface onItemClickCallback{
        void onItemClicked(View v, int position);
    }

}