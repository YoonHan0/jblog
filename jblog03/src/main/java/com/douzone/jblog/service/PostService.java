package com.douzone.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.PostRepository;
import com.douzone.jblog.vo.PostVo;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepository;
	
	public List<PostVo> getPostList(Long no) {
		return postRepository.getPostList(no);
	}

//	public PostVo minValue(List<PostVo> postList) {
//		Long index = postList.get(0).getNo();
//		PostVo vo = new PostVo();
//		
//		for(int i = 0; i < postList.size(); i++) {
//			if(index > postList.get(i).getNo()) {
//				index = postList.get(i).getNo();
//				vo = postList.get(i);
//			}
//		}
//	
//		return vo;
//	}

	public PostVo getRecentlyPost(Long no) {	// 초기값 : 해당 카테고리에서 가장 최근에 포스팅된 글
		PostVo vo = postRepository.getRecentlyPost(no);
		if(vo == null) {
			System.out.println("아무값도 없음");
		}
		return vo;
	}

	public PostVo getPostByNo(Long postNo) {
		return postRepository.getPostByNo(postNo);
	}

}
