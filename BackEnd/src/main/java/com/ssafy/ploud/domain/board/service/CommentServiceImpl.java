package com.ssafy.ploud.domain.board.service;

import com.ssafy.ploud.common.exception.CustomException;
import com.ssafy.ploud.common.response.ResponseCode;
import com.ssafy.ploud.domain.board.BoardEntity;
import com.ssafy.ploud.domain.board.CommentEntity;
import com.ssafy.ploud.domain.board.dto.request.CommentRequest;
import com.ssafy.ploud.domain.board.dto.response.BoardResponse;
import com.ssafy.ploud.domain.board.dto.response.CommentResponse;
import com.ssafy.ploud.domain.board.repository.CommentRepository;
import com.ssafy.ploud.domain.user.UserEntity;
import com.ssafy.ploud.domain.user.repository.UserRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

  private CommentRepository commentRepository;
  private UserRepository userRepository;

  @Override
  public List<CommentResponse> getCommentsByBoardId(int boardId) {
    List<CommentEntity> commentEntities = commentRepository.findByBoardId(boardId);
    List<CommentResponse> commentResponses = new ArrayList<>();

    for (CommentEntity commentEntity : commentEntities) {
      commentResponses.add(CommentResponse.fromEntity(commentEntity, getNicknameAndProfileImg(commentEntity.getUserId())));
    }
    return commentResponses;
  }

  @Override
  public CommentResponse createComment(CommentRequest commentRequest, String userId) {
    CommentEntity commentEntity = CommentEntity.createComment(commentRequest, userId);
    commentRepository.save(commentEntity);
    return CommentResponse.fromEntity(commentEntity, getNicknameAndProfileImg(commentEntity.getUserId()));
  }

  private Map<String, String> getNicknameAndProfileImg(String userId) {
    UserEntity user = userRepository.findByUserId(userId)
        .orElseThrow(() -> new CustomException(ResponseCode.USER_NOT_FOUND));
    Map<String, String> res = new HashMap<>();
    res.put("nickname", user.getNickname());
    res.put("profileImg", user.getProfileImg());
    return res;
  }

  @Override
  public void deleteComment(int id, String userId) {

    CommentEntity commentEntity = (CommentEntity) commentRepository.findById(id)
        .orElseThrow(() -> new CustomException(ResponseCode.COMMENT_NOT_FOUND));

    if (!commentEntity.getUserId().equals(userId)) {
      throw new CustomException(ResponseCode.NO_PERMISSION);
    }
    commentRepository.deleteById(id);
  }

}
