#ifndef _VIDEO_CLIP_H_
#define _VIDEO_CLIP_H_

#ifdef WIN32
#define inline _inline
#endif

#include <libavcodec/avcodec.h>
#include <libavformat/avformat.h>
#include <libswscale/swscale.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <time.h>

struct _VideoClip {
  AVFormatContext *pFormatCtx;
  int videoStream;
  AVCodecContext *pCodecCtx;
  AVCodec *pCodec;
  struct SwsContext* swsContext;
  int64_t currentFrame;
};
typedef struct _VideoClip VideoClip;

struct _OutputInfo {
  AVFormatContext *formatContext;
  
};
typedef struct _OutputInfo OutputInfo;

void video_clip_static_init();
VideoClip* load_video_clip(char* filename);
void delete_video_clip(VideoClip *vc);
AVFrame* alloc_rgb_frame(int width, int height);
void dealloc_rgb_frame(AVFrame *frame);
int start_decode(VideoClip *vc);
int read_frame(VideoClip *vc, AVFrame *outputFrame);
void seek(VideoClip *vc, int64_t timestamp);
int64_t get_length(VideoClip *vc);
double get_fps(VideoClip *vc);
VideoClip* output_video_clip(char* filename, int width, int height, 
                             int frame_rate);
int write_frame(VideoClip *vc, AVFrame *frame, int width, int height);
void close_output_video(VideoClip *vc);
#endif
