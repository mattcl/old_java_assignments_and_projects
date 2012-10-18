/*
 * File: SMovie.java
 * -----------------------
 */

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import smovielib.Clip;
import smovielib.Effect;
import smovielib.PixelMatrix;
import smovielib.TimelineProgram;
import smovielib.TimelineListener;
import smovielib.Video;

import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GObject;
import acm.graphics.GPoint;
import acm.gui.IntField;
import acm.io.*;

public class SMovie extends TimelineProgram implements TimelineListener {
	private JComboBox effectSelector;
	private JFileChooser fileChooser;
	private JButton addButton;
	private JButton removeButton;
	private JButton addEffectButton, removeEffectButton, previewButton, exportButton;
	private JProgressBar progressBar;
	
	private ArrayList<VideoClip> clips;
	private ArrayList<EffectClip> effects;
	
	private boolean willShowPreview;
	
	public void run() {	
	    setUpInterface();
		getTimeline().addListener(this);
		willShowPreview = false;
		
		clips = new ArrayList<VideoClip>();
		effects = new ArrayList<EffectClip>();
	}
	
	public void actionPerformed(ActionEvent ae) {
	    if(ae.getSource().equals(addButton)) {
	        addClipFromFile();
	    } else if(ae.getSource().equals(removeButton)) {
	        removeSelectedClip();
	    } else if(ae.getSource().equals(previewButton)) {
	        willShowPreview = true;
	    } else if(ae.getSource().equals(addEffectButton)) {
	        effects.add(new EffectClip((Effect) effectSelector.getSelectedItem(), effects.size() + 1));
	        getTimeline().addClip(effects.get(effects.size() - 1));
	    } else if(ae.getSource().equals(removeEffectButton)) {
	        removeSelectedEffect();
	    } else if (ae.getSource().equals(exportButton)) {
	        Thread th = new Thread(new Runnable() {
	            public void run() {
	                exportVideo();
	            }
	        });
	        th.start();
	    }
	}

	public void pointSelected(int frame, int offset) {
	    if(willShowPreview && !clips.isEmpty() && clips.get(clips.size() - 1).getFramePastTheEnd() - 1 > frame) {
	        generatePreview(frame, offset);
	        willShowPreview = false;
	    }
	}

	public void clipMoved(Clip clip, long deltaFrames) {
	    if(clip instanceof VideoClip) {
	        VideoClip vClip = (VideoClip) clip;
	        vClip.adjustBegin(deltaFrames);
	        adjustClipsFromIndex(clips.indexOf(vClip));
	    } else if (clip instanceof EffectClip) {
	        EffectClip eClip = (EffectClip) clip;
	        eClip.adjustStartFrame(deltaFrames);
	    }
	    getTimeline().clipModified(clip);
	    
	}

	public void clipResized(Clip clip, long deltaFrames) {
	    if(clip instanceof VideoClip) {
            VideoClip vClip = (VideoClip) clip;
            vClip.adjustEnd(deltaFrames);
            adjustClipsFromIndex(clips.indexOf(vClip));
        } else if (clip instanceof EffectClip) {
            EffectClip eClip = (EffectClip) clip;
            eClip.adjustNumFrames(deltaFrames);
        }
	    getTimeline().clipModified(clip);
	}
	
	
	// ******** PRIVATE ********* /
	
	private void exportVideo() {
        try {
            int width = 640;
            int height = 480;
            int frames = 15;
            
            if(clips.size() == 1) {
                Video video = clips.get(0).getVideo();
                width = video.getWidth();
                height = video.getHeight();
                frames = (int) Math.round(video.getFramesPerSecond());
            }
            
            int totalFrames = (int) clips.get(clips.size() - 1).getFramePastTheEnd() - 1;
            progressBar.setMaximum(totalFrames);
            
            Video output = new Video("output.avi", width, height, frames);
            for(VideoClip clip : clips) {
                clip.prepareForExport();
                while(clip.hasNextFrame()) {
                    PixelMatrix frame = clip.getNextFrame();
                    applyEffectsToFrame(frame, clip);
                    output.writeFrame(frame);
                    final VideoClip cl = clip;
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            progressBar.setValue((int) cl.getGlobalCurrentFrame());
                        }
                    });
                }
            }
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }       
    }
	
	private void applyEffectsToFrame(PixelMatrix frame, VideoClip clip) {
        for(EffectClip effect : effects) {
            if(effect.withinClip(clip.getGlobalCurrentFrame())) {
                effect.getEffect().apply(frame, true);
            }
        }
    }
	
	private void generatePreview(int frame, int offset) {
	    PixelMatrix pm = null;
        int i = 0;
        for(; i < clips.size(); i++) {
            if(clips.get(i).getFramePastTheEnd() - 1 > frame) {
                pm = clips.get(i).getMatrixForFrame(frame);
                break;
            }
        }
            
        if(offset-- > 0 && offset < effects.size() && effects.get(offset).withinClip(frame))
            effects.get(offset).getEffect().apply(pm, true);
        
        getTimeline().showPreview(pm, clips.get(i).getLabel());
	}
	
	private void adjustClipsFromIndex(int index) {
	    for(int current = index, previous = current-1; current < clips.size(); previous = current, current++) {
	        if(previous < 0) 
	            clips.get(current).setGlobalStart(1);
	        else 
	            clips.get(current).setGlobalStart(clips.get(previous).getFramePastTheEnd());
	        
	        getTimeline().clipModified(clips.get(current));
	    }
	}
	
	private void removeSelectedClip() {
	    if(clips.isEmpty()) return;
	    Clip clip = getTimeline().getSelectedClip();
	    if(clip instanceof VideoClip) {
	        getTimeline().removeClip(clip);
	        int index = clips.indexOf(clip);
	        clips.remove(clip);
	        adjustClipsFromIndex(index);
	    }
	}
	
	private void removeSelectedEffect() {
	    if(effects.isEmpty()) return;
	    Clip clip = getTimeline().getSelectedClip();
        if(clip instanceof EffectClip) {
            getTimeline().removeClip(clip);
            effects.remove(clip);    
        }
	}
	
	private void addClipFromFile() {
	    int retVal = fileChooser.showOpenDialog(this);
        if(retVal == JFileChooser.APPROVE_OPTION) {
            try {
                Video video = new Video(fileChooser.getSelectedFile().getName());
                VideoClip clip = new VideoClip(video);
                if(clips.size() > 0)
                    clip.setGlobalStart(clips.get(clips.size() - 1).getFramePastTheEnd());
                clips.add(clip);
                getTimeline().addClip(clip);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
	
	private void setUpInterface() {
	    fileChooser = new JFileChooser();
	    fileChooser.setCurrentDirectory(new File("."));
	    
	    addButton = new JButton("Add Clip");
	    removeButton = new JButton("Remove Clip");
	    
	    add(addButton, NORTH);
	    add(removeButton, NORTH);
	    
	    effectSelector = new JComboBox();
        
        effectSelector.addItem(new FlipHorizontalEffect());
        effectSelector.addItem(new FlipVerticalEffect());
        effectSelector.addItem(new NegativeEffect());
	    
        addEffectButton = new JButton("Add Effect");
        removeEffectButton = new JButton("Remove Effect");
        previewButton = new JButton("Preview");
        exportButton = new JButton("Export");
        
        progressBar = new JProgressBar(0,100);
        progressBar.setValue(0);
        
        add(effectSelector, SOUTH);
        add(addEffectButton, SOUTH);
        add(removeEffectButton, SOUTH);
        add(previewButton, SOUTH);
        add(exportButton, SOUTH);
        add(progressBar, SOUTH);
        
        addActionListeners(this);
	}
}
