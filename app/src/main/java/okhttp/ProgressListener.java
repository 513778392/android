package okhttp;

/**
 * Created by Administrator on 2017/9/1 0001.
 */
public interface  ProgressListener {
    /**
     * 显示进度
     *
     * @param mProgress
     */
    public void onProgress(int mProgress, long contentSize);

    /**
     * 完成状态
     *
     * @param totalSize
     */
    public void onDone(long totalSize);

}
