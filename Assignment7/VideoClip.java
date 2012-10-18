import java.io.IOException;

import smovielib.Clip;
import smovielib.PixelMatrix;
import smovielib.Video;

/**
 * A clip corresponding to part of a Video.
 */
public class VideoClip implements Clip {
    private long begin;
    private long end;
    private long globalStart;
    
    private Video video;
    
    public VideoClip(Video video) {
        this.video = video;
        setClip(1, video.getNumFrames());
        globalStart= 1;
    }
    
    public Video getVideo() {
        return video;
    }
    
    public void prepareForExport() {
        video.seek(begin);
    }
    
    public boolean hasNextFrame() {
        return (video.getFrameIndex() < end + 1);
    }
    
    public PixelMatrix getNextFrame() {
        video.nextFrame();
        return video.getCurrentFrame();
    }
    
    public long getGlobalCurrentFrame() {
        return video.getFrameIndex() - begin + globalStart;
    }
    
	public String getLabel() {
	    return video.getFilename();
	}
	
	public int getEffectOrder() {
		return 0;
	}
	
	public PixelMatrix getMatrixForFrame(long f) {
	    long f_act = f - globalStart + begin;
	    video.nextFrame();
	    video.seek(f_act);
	    return video.getCurrentFrame();
	}
	
	public void adjustBegin(long nFrames) {
	    begin += nFrames;
	    if(begin > end) begin = end;
	    if(begin < 0) begin = 0;
	}
	
	public void adjustEnd(long nFrames) {
	    end += nFrames;
	    if(end < begin) end = begin;
	    if(end > video.getNumFrames()) end = video.getNumFrames();
	}
	
	public void setClip(long begin, long end) {
	   this.begin = begin;
	   this.end = end;
	}
	
	public void setGlobalStart(long n) {
	    globalStart = n;
	}
	
	public long getStartFrame() {
	    return globalStart;
	}
	
	public long getFramePastTheEnd() {
		return getStartFrame() + numFrames();
	}
	
	public long numFrames() {
		return end - begin + 1;
	}
}
