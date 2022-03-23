import { request } from 'umi';

async function get(id: string): Promise<API.ApiResponse<API.User.UserDTO>> {
  return request<API.ApiResponse<API.User.UserDTO>>(`/api/users/${id}`, {
    method: 'get',
  });
}

async function login(
  record: API.User.UserLoginQuery,
): Promise<API.ApiResponse<API.User.UserLoginDTO>> {
  return request<API.ApiResponse<API.User.UserLoginDTO>>(`/api/users/login`, {
    method: 'post',
    data: record,
  });
}

export { get, login };
