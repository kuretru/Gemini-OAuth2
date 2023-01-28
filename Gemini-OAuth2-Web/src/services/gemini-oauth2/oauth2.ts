import { request } from 'umi';

async function isApproved(record: API.OAuth2.OAuth2ApproveQuery): Promise<API.ApiResponse<string>> {
  return request('/api/oauth2/is_approved', {
    method: 'post',
    data: record,
  });
}

async function approve(
  record: API.OAuth2.OAuth2ApproveRequestDTO,
): Promise<API.ApiResponse<string>> {
  return request('/api/oauth2/approve', {
    method: 'post',
    data: record,
  });
}

export { isApproved, approve };
