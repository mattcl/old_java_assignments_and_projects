#include "video_clip.h"

#define OUTBUF_SIZE 200000
static uint8_t *video_outbuf;

void video_clip_static_init() {
  av_register_all();
  video_outbuf = av_malloc(OUTBUF_SIZE); 
}

VideoClip* load_video_clip(char* filename) {
  unsigned int i;
  VideoClip *vc = malloc(sizeof(VideoClip));
  memset(vc, '\0', sizeof(VideoClip)); 

  if (av_open_input_file(&(vc->pFormatCtx), filename, NULL, 0, NULL) != 0) {
    delete_video_clip(vc);
    return NULL;
  }

  if (av_find_stream_info(vc->pFormatCtx) < 0) {
    delete_video_clip(vc);
    return NULL;
  }
    
  vc->videoStream = 1;

  for (i = 0; i < vc->pFormatCtx->nb_streams; i++) {
    if (vc->pFormatCtx->streams[i]->codec->codec_type == CODEC_TYPE_VIDEO) {
        vc->videoStream = i;
        break;
    }
  }

  if (vc->videoStream == -1) {
    delete_video_clip(vc);
    return NULL;
  }

  vc->pCodecCtx = vc->pFormatCtx->streams[vc->videoStream]->codec;

  vc->pCodec = avcodec_find_decoder(vc->pCodecCtx->codec_id);
  if (vc->pCodec == NULL) {
    delete_video_clip(vc);
    return NULL;
  }

  vc->swsContext = sws_getContext(
      vc->pCodecCtx->width,
      vc->pCodecCtx->height,
      vc->pCodecCtx->pix_fmt,
      vc->pCodecCtx->width,
      vc->pCodecCtx->height,
      PIX_FMT_RGB24,
      SWS_FAST_BILINEAR,
      NULL,
      NULL,
      NULL);

  if (vc->swsContext == NULL) {
    delete_video_clip(vc);
    return NULL;
  }

  return vc;
}

void delete_video_clip(VideoClip *vc) {
  if (vc->pCodecCtx) avcodec_close(vc->pCodecCtx);
  if (vc->pFormatCtx) av_close_input_file(vc->pFormatCtx);
  free(vc);
}

int read_frame(VideoClip *vc, AVFrame *outputFrame) {
  AVFrame *pFrame = avcodec_alloc_frame();
  int frameFinished = 0;
  AVPacket packet;
  while (av_read_frame(vc->pFormatCtx, &packet) >= 0) {
    if (packet.stream_index == vc->videoStream) {
        avcodec_decode_video(vc->pCodecCtx, pFrame, &frameFinished,
                             packet.data, packet.size);
        if (frameFinished) {
            sws_scale(vc->swsContext, pFrame->data, 
                      pFrame->linesize, 0,
                      vc->pCodecCtx->height,
                      outputFrame->data, outputFrame->linesize);
            av_free_packet(&packet);
            break;
        }
    }
    av_free_packet(&packet);
  }
  av_free(pFrame);
  (vc->currentFrame)++;
  return frameFinished;
}

int start_decode(VideoClip *vc) {
  if (avcodec_open(vc->pCodecCtx, vc->pCodec) < 0) {
    fprintf(stderr, "Couldn't initialize codec");
    return 0;
  }

  return 1;
}

void seek(VideoClip *vc, int64_t timestamp) {
   int64_t delta = timestamp - vc->currentFrame; 
   int flags = 0;
   if (delta < 0) flags |= AVSEEK_FLAG_BACKWARD;
   av_seek_frame(vc->pFormatCtx, vc->videoStream, timestamp, flags);
   vc->currentFrame = timestamp;
   avcodec_flush_buffers(vc->pCodecCtx);
}

AVFrame* alloc_rgb_frame(int width, int height) {
  AVFrame *frame = avcodec_alloc_frame();
  uint8_t *buffer;
  int numBytes;
  numBytes=avpicture_get_size(PIX_FMT_RGB24, width, height);
  buffer=(uint8_t *)av_malloc(numBytes*sizeof(uint8_t));
  avpicture_fill((AVPicture *)frame, buffer, PIX_FMT_RGB24,
                 width, height);
  return frame;
}

void dealloc_rgb_frame(AVFrame *frame) {
  av_free(frame->data[0]);
  av_free(frame);
}

int64_t get_length(VideoClip *vc) {
  return vc->pFormatCtx->streams[vc->videoStream]->duration;
}

double get_fps(VideoClip *vc) {
  AVRational* fr = &(vc->pFormatCtx->streams[vc->videoStream]->r_frame_rate);
  return ((double)fr->num)/(fr->den);
}

int start_encode(VideoClip *vc) {
  return 0;
}

VideoClip* output_video_clip(char* filename, int width, int height, 
                             int frame_rate) {

  AVOutputFormat *fmt = guess_format("avi", NULL, NULL);
  if (fmt == NULL) {
    printf("Couldn't guess format\n");
    return NULL;
  }
  VideoClip *vc = malloc(sizeof(VideoClip));
  memset(vc, '\0', sizeof(VideoClip));
  if (!vc) {
    printf("Couldn't alloc videoclip\n");
    return NULL;
  }
  vc->pFormatCtx = avformat_alloc_context();
  vc->pFormatCtx->oformat = fmt;
  strcpy(vc->pFormatCtx->filename, filename);
  AVCodec *avc = avcodec_find_encoder(CODEC_ID_MPEG4);
  if (!avc) {
    printf("Couldn't find encoder\n");
    return NULL;
  }
  AVStream *st = av_new_stream(vc->pFormatCtx, 0);
  if (!st) {
    printf("Couldn't add video stream\n");
    return NULL;
  }
  AVCodecContext *c;
  c = st->codec;
  c->codec_id = CODEC_ID_MPEG4;
  c->codec_type = CODEC_TYPE_VIDEO;
  c->bit_rate = 400000;
  c->width = width;
  c->height = height;
  c->time_base.den = frame_rate;
  c->time_base.num = 1;
  c->gop_size = 12;
  c->pix_fmt = PIX_FMT_YUV420P;

  if (vc->pFormatCtx->flags & AVFMT_GLOBALHEADER)
    c->flags |= CODEC_FLAG_GLOBAL_HEADER;

  if (av_set_parameters(vc->pFormatCtx, NULL) < 0) {
    printf("Couldn't set parameters\n");
    return NULL;
  }

  if (avcodec_open(c, avc) < 0) {
    return NULL;
  }
  vc->videoStream = 0;
  vc->pCodecCtx = c;
  vc->pCodec = avc;

  if (!(fmt->flags & AVFMT_NOFILE)) {
    if (url_fopen(&(vc->pFormatCtx->pb), filename, URL_WRONLY) < 0) {
      return NULL;
    }
  }

  av_write_header(vc->pFormatCtx);
  return vc;
}

int write_frame(VideoClip *vc, AVFrame *frame, int width, int height) {
  struct SwsContext* convertCtx;
  AVFrame *tempFrame = avcodec_alloc_frame();
  if (!tempFrame) {
    fprintf(stderr, "tempFrame");
    return 0;
  }
  int size = avpicture_get_size(vc->pCodecCtx->pix_fmt, vc->pCodecCtx->width, 
                                vc->pCodecCtx->height);
  uint8_t *tempBuf = av_malloc(size);
  if (!tempBuf) {
    fprintf(stderr, "tempBuf: %d, %d, %d, %d", size, vc->pCodecCtx->pix_fmt, vc->pCodecCtx->width, vc->pCodecCtx->height);
    av_free(tempFrame);
    return 0;
  }
  avpicture_fill((AVPicture *)tempFrame, tempBuf, vc->pCodecCtx->pix_fmt,
                 vc->pCodecCtx->width, vc->pCodecCtx->height);
  convertCtx = sws_getContext(width, height,
                              PIX_FMT_RGB24, vc->pCodecCtx->width,
                              vc->pCodecCtx->height, vc->pCodecCtx->pix_fmt,
                              SWS_BICUBIC, NULL, NULL, NULL);
  if (!convertCtx) {
    fprintf(stderr, "Bad convert context");
    return 0;
  }
  sws_scale(convertCtx, frame->data, frame->linesize, 0, 
            vc->pCodecCtx->height, tempFrame->data, tempFrame->linesize);
  int out_size = avcodec_encode_video(vc->pCodecCtx, video_outbuf, OUTBUF_SIZE, 
                                      tempFrame);
  if (out_size <= 0) {
    fprintf(stderr, "out_size = 0");
    return 0;
  }

  AVPacket pkt;
  av_init_packet(&pkt);
  if (vc->pCodecCtx->coded_frame->pts != AV_NOPTS_VALUE) {
    pkt.pts = av_rescale_q(vc->pCodecCtx->coded_frame->pts, vc->pCodecCtx->time_base,
                           vc->pFormatCtx->streams[vc->videoStream]->time_base);
  }

  if (vc->pCodecCtx->coded_frame->key_frame) {
    pkt.flags |= PKT_FLAG_KEY;
  }
  pkt.stream_index = vc->videoStream;
  pkt.data = video_outbuf;
  pkt.size = out_size;
  if (av_interleaved_write_frame(vc->pFormatCtx, &pkt) != 0) {
    fprintf(stderr, "Couldn't write frame");
    return 0;
  }
  (vc->currentFrame)++;
  av_free(tempFrame->data[0]);
  av_free(tempFrame);
  sws_freeContext(convertCtx); 
  return 1;
}

void close_output_video(VideoClip *vc) {
  av_write_trailer(vc->pFormatCtx);
  avcodec_close(vc->pFormatCtx->streams[vc->videoStream]->codec);

  if (!(vc->pFormatCtx->flags & AVFMT_NOFILE)) {
    url_fclose(vc->pFormatCtx->pb);
  }
  av_free(vc->pFormatCtx);
}
