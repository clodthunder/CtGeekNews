package gt.lskj.com.geeknew.gilde;

import android.content.Context;

import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader;

/**
 * Created by Home on 16/9/22.
 */

public class CustomImageSizeUrlLoader extends BaseGlideUrlLoader<CustomImageSizeModel> {
    public CustomImageSizeUrlLoader(Context context) {
        super( context );
    }

    @Override
    protected String getUrl(CustomImageSizeModel model, int width, int height) {
        return model.requestCustomSizeUrl( width, height );
    }
}
