import smovielib.Clip;
import smovielib.Effect;

/**
 * Implements a clip that corresponds to an effect.
 */
public class EffectClip implements Clip {
    private static final long DEFAULT_LENGTH = 1000;
    private static final String DEFAULT_LABEL = "EFFECT";
    
    private int effectOrder;
    private long length;
    private long globalStart;
    private Effect effect;
    
    public EffectClip(Effect effect, int order) {
        length = DEFAULT_LENGTH;
        globalStart = 1;
        effectOrder = order;
        this.effect = effect;
    }
    
    public boolean withinClip(long frame) {
        return (frame >= globalStart && frame < getFramePastTheEnd());
    }
    
    public void setEffect(Effect effect) {
        this.effect = effect;
    }
    
    public String getLabel() {
        if(effect == null) return "NONE";
		return effect.toString();
	}
	
    public Effect getEffect() {
        return effect;
    }
    
	public int getEffectOrder() {
	    return effectOrder;
	}
	
	public void setEffectOrder(int o) {
	    effectOrder = o;
	}
	
	public void setStartFrame(long start) {
	    globalStart = start;
	}
	
	public void adjustStartFrame(long delta) {
	    globalStart = Math.max(0, globalStart + delta);
	}
	
	public long getStartFrame() {
    // You'll want to change this
	    return globalStart;
	}
	
	public long getFramePastTheEnd() {
	    return globalStart + length + 1;
	}
	
	public void adjustNumFrames(long delta) {
	    length = Math.max(1, length + delta);
	}
	
	public long numFrames() {
		return getFramePastTheEnd() - getStartFrame();
	}
}
