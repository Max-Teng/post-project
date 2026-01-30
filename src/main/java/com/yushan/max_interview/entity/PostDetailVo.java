package com.yushan.max_interview.entity;

import lombok.Data;

import java.util.List;

@Data
public class PostDetailVo {
    public PostVo postVo;
    public List<CommentVo> commentVoList;
}
