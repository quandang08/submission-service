package com.amu.service;

import com.amu.entities.Submission;
import com.amu.entities.SubmissionStatus;

import java.util.List;

public interface SubmissionService {

    /**
     * Nộp bài làm cho một task cụ thể.
     *
     * @param taskId     ID của task cần nộp bài.
     * @param githubLink Link GitHub chứa source code bài làm.
     * @param userId     ID của người dùng nộp bài.
     * @return Submission vừa được tạo.
     * @throws Exception nếu task không tồn tại hoặc người dùng không hợp lệ.
     */
    Submission submitTask(Long taskId, String githubLink, Long userId, String jwt) throws Exception;

    /**
     * Lấy chi tiết bài nộp theo submissionId.
     *
     * @param submissionId ID của bài nộp.
     * @return Submission tương ứng.
     * @throws Exception nếu không tìm thấy submission.
     */
    Submission getTaskSubmissionById(Long submissionId) throws Exception;

    /**
     * Lấy danh sách tất cả các bài nộp.
     *
     * @return Danh sách tất cả submissions trong hệ thống.
     */
    List<Submission> getAllSubmissions();

    /**
     * Lấy danh sách các bài nộp theo taskId.
     *
     * @param taskId ID của task cần xem bài nộp.
     * @return Danh sách các submissions của task đó.
     * @throws Exception nếu task không tồn tại.
     */
    List<Submission> getSubmissionsByTaskId(Long taskId) throws Exception;

    /**
     * Admin duyệt hoặc từ chối một bài nộp.
     *
     * @param id     ID của submission cần xử lý.
     * @param status Trạng thái mới: ACCEPTED hoặc REJECTED.
     * @return Submission sau khi được cập nhật trạng thái.
     * @throws Exception nếu submission không tồn tại hoặc trạng thái không hợp lệ.
     */
    Submission acceptOrDeclineSubmission(Long id, SubmissionStatus status) throws Exception;

}
