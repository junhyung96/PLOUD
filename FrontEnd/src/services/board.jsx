import API from "../utils/Api";

export const getboardList = async (token, data, success, fail) => {
  return await API(token)
    .get(`board/?page=${data.page}&size=${data.size}&sort=${data.sort}`)
    .then(success)
    .catch(fail);
}

export const searchBoard = async (token, data, success, fail) => {
  return await API(token)
  .get(`board/search?title=${data.title}&page=${data.page}&size=${data.size}&sort=${data.sort}`)
  .then(success)
  .catch(fail)
}


export const createboard = async (token, data, success, fail) => {
  return await API(token)
    .post("board/create", data)
    .then(success)
    .catch(fail)
}


export const getboardDetail = async (token, id, success, fail) => {
  return await API(token)
    .get(`board/${id}`)
    .then(success)
    .catch(fail)
}


export const putboard = async (token, id, data, success, fail) => {
  return await API(token)
    .put(`board/${id}`, data)
    .then(success)
    .catch(fail)
}


export const deleteboard = async (token, id, success, fail) => {
  return await API(token)
    .delete(`board/${id}`)
    .then(success)
    .catch(fail)
}

// 좋아요 기능 /api/board/heart
export const likeboard = async (token, boardId, success, fail) => {
  return await API(token)
    .post('board/heart', boardId)
    .then(success)
    .catch(fail)
}


// 댓글 기능 /api/comment
// 조회 /{boardId}
// 작성 /
// 삭제 /{id}

export const getComment = async (token, boardId, success, fail) => {
  return await API(token)
    .get(`comment/{boardId}?boardId=${boardId}`)
    .then(success)
    .catch(fail)
}


export const postComment = async (token, data, success, fail) => {
  return await API(token)
    .post("comment", data)
    .then(success)
    .catch(fail)
}


export const deleteComment = async (token, commentId, success, fail) => {
  return await API(token)
    .delete(`comment/${commentId}`)
    .then(success)
    .catch(fail)
}
