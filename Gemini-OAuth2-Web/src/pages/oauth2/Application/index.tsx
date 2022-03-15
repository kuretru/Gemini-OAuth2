import React from 'react';
import type { ProColumns } from '@ant-design/pro-table';
import { ProFormText } from '@ant-design/pro-form';
import { Image } from 'antd';
import OAuth2ApplicationService from '@/services/gemini-oauth2/oauth2/application';
import BasePage from '@/components/BasePage';

class OAuth2Application extends React.Component {
  columns: ProColumns<API.OAuth2.OAuth2ApplicationDTO>[] = [
    {
      align: 'center',
      dataIndex: 'name',
      title: '名称',
      width: 240,
      render: (text, record) => [
        <Image key="avatar" src={record.avatar} width={25} height={25} />,
        <a href={record.homepage} key="homepage" rel="noreferrer" target="_blank">
          {record.name}
        </a>,
      ],
    },
    {
      align: 'center',
      copyable: true,
      dataIndex: 'description',
      search: false,
      title: '描述',
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
      <BasePage<API.OAuth2.OAuth2ApplicationDTO, API.OAuth2.OAuth2ApplicationQuery>
        pageName="OAuth2应用"
        service={new OAuth2ApplicationService()}
        columns={this.columns}
        formItem={this.formItem()}
      />
    );
  }
}

export default OAuth2Application;
