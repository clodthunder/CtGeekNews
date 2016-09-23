package gt.lskj.com.geeknew.gilde;

/**
 * Created by Home on 16/9/22.
 */

public class CustomImageSizeModelFutureStudio implements CustomImageSizeModel {
    String baseImageUrl;

    public CustomImageSizeModelFutureStudio(String baseImageUrl) {
        this.baseImageUrl = baseImageUrl;
    }

    @Override
    public String requestCustomSizeUrl(int width, int height) {
        return baseImageUrl + "?w=" + width + "&h=" + height;
    }
}
