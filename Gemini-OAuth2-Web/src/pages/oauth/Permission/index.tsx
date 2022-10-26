import React from 'react';
import { ProFormText } from '@ant-design/pro-form';
import type { ProColumns } from '@ant-design/pro-table';
import { Image } from 'antd';
import BasePage from '@/components/BasePage';
import OAuthPermissionService from '@/services/gemini-oauth2/oauth/permission';

class OAuthPermission extends React.Component {
  columns: ProColumns<API.OAuth.OAuthPermissionVO>[] = [
    {
      align: 'center',
      dataIndex: 'application.name',
      title: '应用',
      width: 240,
      render: (_, record) => [
        <Image key="avatar" src={record.application.avatar} width={25} height={25} />,
        <a href={record.application.homepage} key="homepage" rel="noreferrer" target="_blank">
          {record.application.name}
        </a>,
      ]
    },
    {
      align: 'center',
      dataIndex: 'permissions',
      search: false,
      title: '权限',
      render: (_, record) => {
        const result: any[] = [];
        record.permissions.forEach(permission => {
          let element = "";
          if (permission.endsWith('_w')) {
            permission.replace('_w', '');
            element = "修改";
          } else {
            element = "读取";
          }

          if (permission.startsWith("email")) {
            element += '电子邮箱';
          } else if (permission.startsWith("mobile")) {
            element += '手机号码';
          }

          result.push(element);
        })
        return result;
      }
    },
  ];

  formItem = () => {
    return (
      <>
        <ProFormText
          label="应用名称"
          name="name"
          placeholder="请输入应用名称"
          rules={[{ max: 16, required: true }]}
          tooltip="最长16位"
          width="lg"
        />
        <ProFormText
          label="头像URL"
          name="avatar"
          placeholder="请输入头像URL"
          rules={[{ max: 128, required: true }]}
          tooltip="最长128位"
          width="lg"
        />
        <ProFormText
          label="描述"
          name="description"
          placeholder="请输入应用描述"
          rules={[{ max: 128, required: true }]}
          tooltip="最长128位"
          width="lg"
        />
        <ProFormText
          label="首页地址"
          name="homepage"
          placeholder="请输入首页地址"
          rules={[{ max: 64, required: true }]}
          tooltip="最长64位"
          width="lg"
        />
        <ProFormText
          label="回调地址"
          name="callback"
          placeholder="请输入回调地址"
          rules={[{ max: 64, required: true }]}
          tooltip="最长64位"
          width="lg"
        />
      </>
    );
  };

  render() {
    return (
      <BasePage<API.OAuth.OAuthPermissionDTO, API.OAuth.OAuthPermissionQuery>
        pageName="我授权的应用"
        service={new OAuthPermissionService()}
        columns={this.columns}
        formItem={this.formItem()}
      />
    );
  }
}

export default OAuthPermission;
