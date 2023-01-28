import { request } from 'umi';

async function get(id: string): Promise<API.ApiResponse<API.User.UserDTO>> {
  return request<API.ApiResponse<API.User.UserDTO>>(`/api/users/${id}`, {
    method: 'get',
  });
}

async function getInformation(id: string): Promise<API.ApiResponse<API.User.UserInformationDTO>> {
  return request<API.ApiResponse<API.User.UserInformationDTO>>(`/api/users/${id}/information`, {
    method: 'get',
  });
}

async function updateInformation(
  id: string,
  record: API.User.UserInformationDTO,
): Promise<API.ApiResponse<API.User.UserInformationDTO>> {
  return request<API.ApiResponse<API.User.UserInformationDTO>>(`/api/users/${id}/information`, {
    method: 'put',
    data: record,
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

async function logout(): Promise<API.ApiResponse<string>> {
  return request<API.ApiResponse<string>>(`/api/users/logout`, {
    method: 'post',
    data: {
      id: localStorage.getItem('accessTokenId'),
    },
  });
}

export { get, getInformation, updateInformation, login, logout };
