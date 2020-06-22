package com.green.biz.product;

import java.util.Date;

import lombok.Data;

@Data
public class ProductCommentVO {
	private int comment_seq;
	private int pseq;
	private String content;
	private String writer;
	private Date regdate;
	private Date modifydate;
}